package ir.aliprogramer.schoolhomemvvm.ViewModel;


import android.app.Activity;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.widget.Toast;


import ir.aliprogramer.schoolhomemvvm.AppPreferenceTools;
import ir.aliprogramer.schoolhomemvvm.BR;
import ir.aliprogramer.schoolhomemvvm.Model.UserModel.UserResponse;
import ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity;
import ir.aliprogramer.schoolhomemvvm.View.Activity.RegisterActivity;
import ir.aliprogramer.schoolhomemvvm.WebService.APIClient;
import ir.aliprogramer.schoolhomemvvm.WebService.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseObservable{
    @Bindable
    public String userName;
    @Bindable
    public String userNameError;
    @Bindable
    public String password;
    @Bindable
    public String passwordError;

    private Activity callingActivity ;

    private APIInterface apiInterface;

    private AppPreferenceTools appPreferenceTools;

    public LoginViewModel(Activity activity) {
        this.callingActivity  = activity;
        userNameError="";
        userName="";
        passwordError="";
        password="";
        apiInterface= APIClient.getClient().create(APIInterface.class);
        appPreferenceTools=new AppPreferenceTools(callingActivity);
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
        //clear message in view
        setUserNameError("");
        notifyPropertyChanged(BR.userNameError);
        setPasswordError("");
        notifyPropertyChanged(BR.passwordError);

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
                Call<UserResponse>userResponseCall=apiInterface.loginUser(getUserName(),getPassword());
                userResponseCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if(response.code()==401){
                            Toast.makeText(callingActivity,"نام کاربری یا رمز عبور شما اشتباه است.",Toast.LENGTH_LONG).show();
                            return;
                        }else if(response.code()==200){
                            appPreferenceTools.saveUserAuthenticationInfo(response.body());
                            callingActivity.startActivity(new Intent(callingActivity,HomeActivity.class));
                            callingActivity.finish();
                        }else{
                            Log.d("errorRoute",response.code()+""+response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(callingActivity,"اتصال اینترنت را بررسی کنید.",Toast.LENGTH_LONG).show();
                        return;
                    }
                });
        }

    }
    public void btnRegister(){
        callingActivity.finish();
        callingActivity.startActivity(new Intent(callingActivity,RegisterActivity.class));
    }

}
