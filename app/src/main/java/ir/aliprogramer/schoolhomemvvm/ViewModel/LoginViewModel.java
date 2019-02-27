package ir.aliprogramer.schoolhomemvvm.ViewModel;


import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.widget.Toast;


import ir.aliprogramer.schoolhomemvvm.BR;
public class LoginViewModel extends BaseObservable{
    @Bindable
    public String userName;
    @Bindable
    public String userNameError;
    @Bindable
    public String password;
    @Bindable
    public String passwordError;

    private Context context;

    public LoginViewModel(Context context) {
        this.context = context;
        userNameError="";
        userName="";
        passwordError="";
        password="";
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserNameError(String userNameError) {
        this.userNameError = userNameError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }
    public boolean checkInput(){
        if (getUserName().isEmpty()) {
            setUserNameError("لطفا نام کاربری خود را وارد کنید.");
            notifyPropertyChanged(BR.userNameError);
        }
        if(getPassword().isEmpty()) {
            setPasswordError("لطفا رمز عبور خود را وارد کنید.");
            notifyPropertyChanged(BR.passwordError);
        }
        if(getUserName().isEmpty() || getPassword().isEmpty())
            return false;

        return true;
    }
    public void btnLogin(){
        if(checkInput()){
            Toast.makeText(context, "ok"+getUserName(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, getUserName()+" nok", Toast.LENGTH_LONG).show();
        }
    }
    public void btnRegister(){
        Log.d("reg","click register");
    }
    /* private MutableLiveData<String> userName=new MutableLiveData<>();
    private MutableLiveData<String> userNameError=new MutableLiveData<>();
    private MutableLiveData<String> password=new MutableLiveData<>();
    private MutableLiveData<String> passwordError=new MutableLiveData<>();
    private Context context;
    private MutableLiveData<LoginModel>mutableLiveData;

    public LoginViewModel(Context context) {
        this.context = context;
    }
    public MutableLiveData<LoginModel>getLogin(){
        if(mutableLiveData==null)
            mutableLiveData=new MutableLiveData<>();
        return mutableLiveData;
    }
    public void btnLogin(){
        if (userName.getValue().isEmpty())
            userNameError.setValue("لطفا نام کاربری خود را وارد کنید.");
        if(password.getValue().isEmpty())
            passwordError.setValue("لطفا رمز عبور خود را وارد کنید.");
    }
    public void btnRegister(){
        Log.d("reg","click register");
    }*/
}
