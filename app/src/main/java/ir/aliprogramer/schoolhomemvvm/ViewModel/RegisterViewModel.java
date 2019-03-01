package ir.aliprogramer.schoolhomemvvm.ViewModel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;

import ir.aliprogramer.schoolhomemvvm.BR;
import ir.aliprogramer.schoolhomemvvm.Model.ResponseModel;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.View.Activity.LoginActivity;
import ir.aliprogramer.schoolhomemvvm.WebService.APIClient;
import ir.aliprogramer.schoolhomemvvm.WebService.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends BaseObservable {
    @Bindable
    public String userName;
    @Bindable
    public String userNameError;
    @Bindable
    public String password;
    @Bindable
    public String passwordError;
    @Bindable
    public String confirmPassword;
    @Bindable
    public String confirmPasswordError;
    @Bindable
    public int radio_checked;

    private Activity callingActivity ;

    private APIInterface apiInterface;

    int type=0;

    public RegisterViewModel(Activity callingActivity) {
        this.callingActivity = callingActivity;
        userNameError="";
        userName="";
        passwordError="";
        password="";
        confirmPassword="";
        confirmPasswordError="";
        radio_checked=R.id.parentst;
        apiInterface= APIClient.getClient().create(APIInterface.class);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setUserNameError(String userNameError) {
        this.userNameError = userNameError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setConfirmPasswordError(String confirmPasswordError) {
        this.confirmPasswordError = confirmPasswordError;
    }

    public int getRadio_checked() {
        return radio_checked;
    }

    public void setRadio_checked(int radio_checked) {
        this.radio_checked = radio_checked;
    }

    public boolean checkInput(){
        //clear message in view
        setUserNameError("");
        notifyPropertyChanged(BR.userNameError);
        setPasswordError("");
        notifyPropertyChanged(BR.passwordError);
        setConfirmPasswordError("");
        notifyPropertyChanged(BR.confirmPasswordError);


        if(getRadio_checked()== R.id.teacher)
            type=1;
        if (getUserName().isEmpty() && type==1) {
            setUserNameError("لطفا کد پرسنلی خود را وارد کنید.");
            notifyPropertyChanged(BR.userNameError);
        }
        if (getUserName().isEmpty() && type==0) {
            setUserNameError("لطفا کد ملی دانش آموز خود را وارد کنید. ");
            notifyPropertyChanged(BR.userNameError);
        }
        if(getPassword().isEmpty()) {
            setPasswordError("لطفا رمز عبور خود را وارد کنید.");
            notifyPropertyChanged(BR.passwordError);
        }
        if(getConfirmPassword().isEmpty()) {
            setConfirmPasswordError("لطفا تکرار رمز عبور خود را وارد کنید.");
            notifyPropertyChanged(BR.confirmPasswordError);
        }

        boolean result=getConfirmPassword().equals(getPassword());
        if(!result){
            setPasswordError("رمز عبور با تکرار رمز عبور تطابق ندارد.");
            notifyPropertyChanged(BR.passwordError);
        }
        int length=getPassword().length();
        if(length<7){
            setPasswordError("طول رمزعبور باید حداقل هشت باشد.");
            notifyPropertyChanged(BR.passwordError);
        }
        if(getUserName().isEmpty() || getPassword().isEmpty() || !result || length<7)
            return false;

        return true;
    }


    public void btnRegister(){
        if(checkInput()){
            Call<ResponseModel>modelCall=apiInterface.registerUser(getUserName(),getPassword(),type);
            modelCall.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(response.body().getStatus()==200){
                        Toast.makeText(callingActivity,"ثبت نام شما با موفقیت انجام شد.",Toast.LENGTH_LONG).show();
                        callingActivity.startActivity(new Intent(callingActivity,LoginActivity.class));
                        callingActivity.finish();
                    }else if(response.body().getStatus()==404){
                        String responseRecieved=response.body().getMessage();
                        if(responseRecieved.equals("1")){
                            Toast.makeText(callingActivity,"کد پرسنلی شما در سامانه ثبت نشده یا قبلا ثبت نام کرده اید.",Toast.LENGTH_LONG).show();
                            setUserNameError("کد پرسنلی شما در سامانه ثبت نشده یا قبلا ثبت نام کرده اید.");
                            notifyPropertyChanged(BR.userNameError);
                            return;
                        }else if(responseRecieved.equals("2")){
                            Toast.makeText(callingActivity,"کد ملی شما در سامانه ثبت نشده یا قبلا ثبت نام کرده اید.",Toast.LENGTH_LONG).show();
                            setUserNameError("کد ملی شما در سامانه ثبت نشده یا قبلا ثبت نام کرده اید.");
                            notifyPropertyChanged(BR.userNameError);
                            return;
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(callingActivity,"اتصال اینترنت را بررسی کنید.",Toast.LENGTH_LONG).show();
                    return;
                }
            });
        }
    }
    public void btnCancel(){
        callingActivity.finish();
        callingActivity.startActivity(new Intent(callingActivity, LoginActivity.class));
    }
}
