package ir.aliprogramer.schoolhomemvvm.View.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ir.aliprogramer.schoolhomemvvm.ViewModel.RegisterViewModel;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    //private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding registerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding= DataBindingUtil.setContentView(this,R.layout.activity_register);
        //registerViewModel =new RegisterViewModel(this);
        registerBinding.setRegister(new RegisterViewModel(this));
    }
}
