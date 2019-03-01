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
import ir.aliprogramer.schoolhomemvvm.ViewModel.AddMarkViewModel;
import ir.aliprogramer.schoolhomemvvm.databinding.DialogAddMarkBinding;
public class AddMarkDialog  extends Dialog{

    Context context;
    int bookId,studentId;
    DialogAddMarkBinding dialogAddMarkBinding;

    public AddMarkDialog(@NonNull Context context,int bookId,int studentId) {
        super(context);
        this.context = context;
        this.bookId=bookId;
        this.studentId=studentId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialogAddMarkBinding= DataBindingUtil.setContentView((Activity) context,R.layout.dialog_add_mark);
        dialogAddMarkBinding= DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_add_mark,null,false);
        this.setContentView(dialogAddMarkBinding.getRoot());
        dialogAddMarkBinding.setAddMark(new AddMarkViewModel(this,bookId,studentId));
    }


    }




