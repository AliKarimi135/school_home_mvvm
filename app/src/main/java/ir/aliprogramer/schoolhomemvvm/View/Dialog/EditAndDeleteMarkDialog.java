package ir.aliprogramer.schoolhomemvvm.View.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity;
import ir.aliprogramer.schoolhomemvvm.ViewModel.EditAndDeleteMarkViewModel;
import ir.aliprogramer.schoolhomemvvm.databinding.DialogEditMarkBinding;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAndDeleteMarkDialog extends Dialog{
        private Context context;

        int bookId, mark, Itemposition,studentId;
        String description;//description
        DialogEditMarkBinding binding;
        public EditAndDeleteMarkDialog(@NonNull Context context,int studentId,int id, int mark, String desc, int Itemposition) {
            super(context);
            this.context = context;
            this.bookId = id;
            this.mark = mark;
            this.description = desc;
            this.studentId = studentId;
            this.Itemposition = Itemposition;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            //binding= DataBindingUtil.setContentView(HomeActivity.getActivity1(), R.layout.dialog_edit_mark);
            binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_edit_mark,null,false);
            //this.setContentView(R.layout.dialog_edit_mark);
            this.setContentView(binding.getRoot());
            binding.setEditMark(new EditAndDeleteMarkViewModel(this,bookId,studentId,mark,description,Itemposition));
        }



}
