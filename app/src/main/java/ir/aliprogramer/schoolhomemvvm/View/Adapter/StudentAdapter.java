package ir.aliprogramer.schoolhomemvvm.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.aliprogramer.schoolhomemvvm.Model.StudentModel.StudentResponse;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.ViewModel.CoursetViewModel;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    List<StudentResponse> studentList;
    int bookId;
    String className,bookName;
    FragmentManager manager;
    CoursetViewModel courseViewModel;
    public StudentAdapter( List<StudentResponse> studentList, int bookId, FragmentManager manager,String className,String bookName) {
        this.studentList = studentList;
        this.bookId = bookId;
        this.manager=manager;
        this.className=className;
        this.bookName=bookName;
        courseViewModel=new CoursetViewModel();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_student,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(studentList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        AppCompatImageView navImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.student_name);
            navImg=itemView.findViewById(R.id.nav_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            courseViewModel.getMarkFragment(manager,studentList.get(getAdapterPosition()).getStudentId(),bookId,className,bookName,studentList.get(getAdapterPosition()).getName());
        }
    }
}