package ir.aliprogramer.schoolhomemvvm.View.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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

import java.util.ArrayList;
import java.util.List;

import ir.aliprogramer.schoolhomemvvm.AppPreferenceTools;
import ir.aliprogramer.schoolhomemvvm.Model.CourseModel.Course;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity;
import ir.aliprogramer.schoolhomemvvm.View.Adapter.CourseAdapter;
import ir.aliprogramer.schoolhomemvvm.ViewModel.CoursetViewModel;

public class CourseFragment extends Fragment {
    FragmentManager fragmentManager;
    RecyclerView recyclerView;
    private CoursetViewModel courseViewModel;
    List<Course>courseList=new ArrayList<>();
    CourseAdapter courseAdapter;
    AppPreferenceTools appPreferenceTools;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_course,container,false);
        recyclerView=view.findViewById(R.id.course_recycle);
        appPreferenceTools=new AppPreferenceTools(HomeActivity.getContext());
        courseAdapter=new CourseAdapter(courseList,appPreferenceTools.getUserId(),appPreferenceTools.getType(),fragmentManager);
        recyclerView.setAdapter(courseAdapter);
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
        courseViewModel= ViewModelProviders.of(this).get(CoursetViewModel.class);
        courseViewModel.init();

        courseViewModel.courseLiveData.observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {
                if(courses!=null) {
                    courseList.addAll(courses);
                    courseAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    public void setData(FragmentManager manager){
        this.fragmentManager=manager;
    }
}
