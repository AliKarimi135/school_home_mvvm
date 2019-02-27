package ir.aliprogramer.schoolhomemvvm.View.Activity;




import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }
}
