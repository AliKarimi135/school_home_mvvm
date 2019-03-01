package ir.aliprogramer.schoolhomemvvm.View.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.aliprogramer.schoolhomemvvm.AppPreferenceTools;
import ir.aliprogramer.schoolhomemvvm.Model.MarkModel.Mark;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.View.Adapter.MarkAdapter;
import ir.aliprogramer.schoolhomemvvm.View.Dialog.AddMarkDialog;
import ir.aliprogramer.schoolhomemvvm.ViewModel.MarkViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkFragment extends Fragment {
    int studentId;
    int bookId;
    String className,bookName,studentName="";
    List<Mark> markList=new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton btnAddMark;
    AppPreferenceTools appPreferenceTools;
    MarkAdapter markAdapter;
    MarkViewModel markViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mark,container,false);
        recyclerView=view.findViewById(R.id.mark_recycle);
        btnAddMark=view.findViewById(R.id.add_mark);
        appPreferenceTools=new AppPreferenceTools(getContext());
        markAdapter=new MarkAdapter(markList,appPreferenceTools.getType(),studentId);
        recyclerView.setAdapter(markAdapter);
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

    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            studentId=savedInstanceState.getInt("studentId");
            bookId=savedInstanceState.getInt("bookId");
            className=savedInstanceState.getString("className");
            bookName=savedInstanceState.getString("bookName");
            studentName=savedInstanceState.getString("studentName");
        }
        markViewModel= ViewModelProviders.of(this).get(MarkViewModel.class);
       // if(markViewModel.markLiveData.getValue()==null) {
            markViewModel.init(bookName, className, studentName, bookId, studentId);
            //Log.d("mark","init mark list");
       //}

        if(appPreferenceTools.getType()==0){
            btnAddMark.setVisibility(View.INVISIBLE);
        }
        btnAddMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMarkDialog markDialog = new AddMarkDialog(getActivity(),bookId,studentId);
                markDialog.show();
                Window window = markDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        markViewModel.markLiveData.observe(this, new Observer<List<Mark>>() {
            @Override
            public void onChanged(@Nullable List<Mark> marks) {
                markList.addAll(marks);
                markAdapter.notifyDataSetChanged();
            }
        });

    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("studentId",studentId);
        outState.putInt("bookId",bookId);
        outState.putString("className",className);
        outState.putString("bookName",bookName);
        outState.putString("studentName",studentName);
    }

    public void setData(int studentId, int bookId,String className,String bookName,String studentName){
        this.studentId=studentId;
        this.bookId=bookId;
        this.className=className;
        this.bookName=bookName;
        this.studentName=studentName;
    }
}