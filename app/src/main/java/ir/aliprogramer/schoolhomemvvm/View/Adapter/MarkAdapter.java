package ir.aliprogramer.schoolhomemvvm.View.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import ir.aliprogramer.schoolhomemvvm.Model.MarkModel.Mark;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.View.Dialog.EditAndDeleteMarkDialog;


public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.ViewHolder> {
    List<Mark> markList;
   int type,studentId;
    String StMonth[]={" ","فروردین","اردیبیهشت","خرداد","تیر","مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند"};
    public MarkAdapter(List<Mark> markList,int type,int studentId) {
        this.markList = markList;
        this.type=type;
        this.studentId=studentId;
    }
    public void updateAdapterData(List<Mark> data) {
        this.markList = data;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_mark,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mark1.setText(markList.get(i).getMark()+" ");
        viewHolder.description.setText(markList.get(i).getDescription());
        viewHolder.day.setText(markList.get(i).getDay()+" ");
        viewHolder.month.setText(StMonth[markList.get(i).getMonth()]);
    }

    @Override
    public int getItemCount() {
        return markList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mark1,description,day,month;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mark1=itemView.findViewById(R.id.mark1);
            description=itemView.findViewById(R.id.description);
            day=itemView.findViewById(R.id.day);
            month=itemView.findViewById(R.id.month);
            if(type==1) {
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            EditAndDeleteMarkDialog markDialog=new EditAndDeleteMarkDialog(view.getContext(),studentId,markList.get(getAdapterPosition()).getId(),markList.get(getAdapterPosition()).getMark(),markList.get(getAdapterPosition()).getDescription(),getAdapterPosition());
            markDialog.show();
            Window window = markDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }



}
