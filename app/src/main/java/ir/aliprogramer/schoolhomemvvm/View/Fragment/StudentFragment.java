package ir.aliprogramer.schoolhomemvvm.View.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.aliprogramer.schoolhomemvvm.Model.StudentModel.StudentResponse;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.View.Adapter.StudentAdapter;
import ir.aliprogramer.schoolhomemvvm.ViewModel.StudentViewModel;

public class StudentFragment extends Fragment {
    int classId,groupId,bookId;
    String className,bookName;
    RecyclerView recyclerView;
    List<StudentResponse> studentList=new ArrayList<>();
    FragmentManager manager;
    StudentAdapter studentAdapter;
    StudentViewModel studentViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_student,container,false);
        recyclerView=view.findViewById(R.id.student_recycle);
        studentAdapter=new StudentAdapter(studentList, bookId, manager, className, bookName);
        recyclerView.setAdapter(studentAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            classId=savedInstanceState.getInt("classId");
            groupId=savedInstanceState.getInt("groupId");
            bookId=savedInstanceState.getInt("bookId");
            className=savedInstanceState.getString("className");
            bookName=savedInstanceState.getString("bookName");
        }
        studentViewModel= ViewModelProviders.of(this).get(StudentViewModel.class);
        studentViewModel.init(classId,bookId,groupId,className,bookName);
        studentViewModel.studentLiveData.observe(this, new Observer<List<StudentResponse>>() {
            @Override
            public void onChanged(@Nullable List<StudentResponse> studentResponses) {
                if(studentResponses!=null) {
                    studentList.addAll(studentResponses);
                    studentAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("classId",classId);
        outState.putInt("groupId",groupId);
        outState.putInt("bookId",bookId);
        outState.putString("className",className);
        outState.putString("bookName",bookName);
    }

    public void setData(int classId, int groupId, int bookId, FragmentManager manager,String className,String bookName){
        this.classId=classId;
        this.groupId=groupId;
        this.bookId=bookId;
        this.manager=manager;
        this.className=className;
        this.bookName=bookName;
    }
}
