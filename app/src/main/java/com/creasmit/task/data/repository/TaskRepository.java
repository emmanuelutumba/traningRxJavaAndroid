package com.creasmit.task.data.repository;

import android.content.Context;

import com.creasmit.task.data.model.Task;
import com.creasmit.task.data.repository.local.LocalService;
import com.creasmit.task.data.repository.local.TaskDao;
import com.creasmit.task.data.repository.remote.RemoteService;
import com.creasmit.task.data.repository.remote.TaskService;
import com.creasmit.task.utils.HttpResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;


public class TaskRepository {
    private TaskService taskService;
    private TaskDao taskDao;
    public BehaviorSubject<List<Task>> holderDataTask = BehaviorSubject.createDefault(new ArrayList<>());

    public TaskRepository(Context context) {
        this.taskService = RemoteService.retrofit().create(TaskService.class);
        this.taskDao = LocalService.newInstance(context).task();
    }

    public BehaviorSubject<List<Task>> getDatas() {
        if (this.holderDataTask.getValue().size() == 0) {
            this.fetchAll();
        }
        return this.holderDataTask;
    }

    private void fetchAll() {
        this.taskService.getList().subscribe(listTask -> {
            holderDataTask.onNext(listTask);
            if (listTask != null && !listTask.isEmpty()) {
                this.insertAll(listTask);
            }
        });
    }


    public Observable<HttpResponse<Task>> add(Task task) {
        Observable<HttpResponse<Task>> httpResponseObservable = Observable.create(emitter -> {
            this.taskService.add(task).subscribe(taskHttpResponse -> {
                emitter.onNext(taskHttpResponse);
                if (taskHttpResponse.getCode().equals("200"))
                    fetchAll();
            });
        });
        return httpResponseObservable;
    }


    public Observable<HttpResponse<Task>> delete(long id) {
        Observable<HttpResponse<Task>> httpResponseObservable = Observable.create(emitter -> {
            this.taskService.delete(id).subscribe(taskHttpResponse -> {
                emitter.onNext(taskHttpResponse);
                if (taskHttpResponse.getCode().equals("200"))
                    fetchAll();
            });
        });
        return httpResponseObservable;
    }

    private void insertAll(List<Task> tasks) {
        LocalService.databaseWriteExecutor.execute(() -> {
            try {
                this.taskDao.deleteAll();
                this.taskDao.insertAll(tasks);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
