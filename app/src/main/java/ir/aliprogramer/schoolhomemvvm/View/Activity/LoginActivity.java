package ir.aliprogramer.schoolhomemvvm.View.Activity;




import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.ViewModel.LoginViewModel;
import ir.aliprogramer.schoolhomemvvm.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding loginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel=new LoginViewModel(this);
        if(savedInstanceState!=null){
            loginViewModel.getData(savedInstanceState);
            Log.d("saveData3","getData");
        }
       loginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
       loginBinding.setLogin(loginViewModel);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        loginViewModel.setDataField();
        Log.d("saveData2","setDataField");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        loginViewModel.saveData(outState);
        Log.d("saveData1","saveData");
        super.onSaveInstanceState(outState);
    }
}
