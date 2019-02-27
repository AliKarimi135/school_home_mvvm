package ir.aliprogramer.schoolhomemvvm.View.Activity;



import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.arch.lifecycle.ViewModelProviders;

import ir.aliprogramer.schoolhomemvvm.Model.LoginModel;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.ViewModel.LoginViewModel;
import ir.aliprogramer.schoolhomemvvm.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding loginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       loginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
       loginViewModel=new LoginViewModel(this);
       loginBinding.setLogin(loginViewModel);
        /*loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getLogin().observe(this, new Observer<LoginModel>() {
            @Override
            public void onChanged(@Nullable LoginModel loginModel) {

            }
        });*/
    }
}
