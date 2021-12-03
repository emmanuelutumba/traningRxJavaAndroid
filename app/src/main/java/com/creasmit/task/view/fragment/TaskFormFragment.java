package com.creasmit.task.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.creasmit.task.R;
import com.creasmit.task.data.model.Task;
import com.creasmit.task.databinding.FragmentTaskFormBinding;
import com.creasmit.task.vm.TaskViewModel;

import butterknife.BindView;

public class TaskFormFragment extends Fragment {

    @BindView(R.id.add)
    Button addBtn;

    private Task task;
    private TaskViewModel taskViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTaskFormBinding fragmentTaskFormBinding = FragmentTaskFormBinding.inflate(inflater, container, false);
        this.task = new Task();
        fragmentTaskFormBinding.setTask(this.task);

        View view = fragmentTaskFormBinding.getRoot();
        bindEvents();
        return view;
    }

    public void bindEvents() {

        this.taskViewModel.getError().observe(requireActivity(), message -> {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
        });

        this.taskViewModel.getSuccess().observe(requireActivity(), message -> {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
        });


        this.addBtn.setOnClickListener(view -> {
            if (!this.task.getDescription().isEmpty() && !this.task.getDelay().isEmpty()) {
                this.taskViewModel.add(this.task);
            } else {
                Toast.makeText(requireContext(), "Renseigner tout les champs", Toast.LENGTH_LONG).show();
            }
        });
    }


}