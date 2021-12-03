package com.creasmit.task.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creasmit.task.R;
import com.creasmit.task.vm.TaskViewModel;

import butterknife.BindView;

public class ListTaskFragment extends Fragment {


    @BindView(R.id.tasks)
    RecyclerView tasks;

    private TaskViewModel taskViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.taskViewModel=new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_task, container, false);
    }
}