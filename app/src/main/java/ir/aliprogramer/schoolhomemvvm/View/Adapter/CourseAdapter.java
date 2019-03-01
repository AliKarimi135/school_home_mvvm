package ir.aliprogramer.schoolhomemvvm.View.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.aliprogramer.schoolhomemvvm.Model.CourseModel.Course;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.ViewModel.CoursetViewModel;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    int type,studentId;
    List<Course> CourseList;
    FragmentManager manager;
    CoursetViewModel courseViewModel;
    public CourseAdapter(List<Course> courseList,int studentId, int type, FragmentManager manager) {
        this.type = type;
        this.studentId = studentId;
        CourseList = courseList;
        this.manager=manager;
        courseViewModel=new CoursetViewModel();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bookName.setText(CourseList.get(i).getBookName());
        if(type==1)
            viewHolder.className.setText(CourseList.get(i).getClassName());
    }

    @Override
    public int getItemCount() {
        return CourseList.size();
    }

    public void updateAdapterData(List<Course> data) {
        this.CourseList = data;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bookName;
        TextView className;
        AppCompatImageView navImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.book_name);
            navImg=itemView.findViewById(R.id.nav_img);
            className=itemView.findViewById(R.id.class_name);
            if(type==1) {
                className.setVisibility(View.VISIBLE);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if user is teacher get student list else student mark
            if(type==1){
                //sent   class_id and Group_id and bookId to find students
                int classId=CourseList.get(getAdapterPosition()).getClassId();
                int groupId=CourseList.get(getAdapterPosition()).getGroupId();
                int bookId=CourseList.get(getAdapterPosition()).getBookId();
                courseViewModel.getStudentFragment(classId,groupId,bookId,manager,CourseList.get(getAdapterPosition()).getClassName(),CourseList.get(getAdapterPosition()).getBookName());

            }else {
                //send studentId and bookId to find mark students
                courseViewModel.getMarkFragment(manager,studentId,CourseList.get(getAdapterPosition()).getBookId()," ",CourseList.get(getAdapterPosition()).getBookName(),"");
            }
        }
    }
}
