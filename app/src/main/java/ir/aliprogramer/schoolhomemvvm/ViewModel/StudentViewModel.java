package ir.aliprogramer.schoolhomemvvm.ViewModel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import ir.aliprogramer.schoolhomemvvm.AppPreferenceTools;
import ir.aliprogramer.schoolhomemvvm.Model.CourseModel.Course;
import ir.aliprogramer.schoolhomemvvm.Model.StudentModel.StudentResponse;
import ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity;
import ir.aliprogramer.schoolhomemvvm.View.Activity.LoginActivity;
import ir.aliprogramer.schoolhomemvvm.WebService.APIClientProvider;
import ir.aliprogramer.schoolhomemvvm.WebService.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentViewModel extends ViewModel {

    private Activity callingActivity ;
    //private Context context ;

    private APIInterface apiInterface;


    private APIClientProvider clientProvider;

    public String title="";

    public MutableLiveData<List<StudentResponse>> studentLiveData;

    public int statusStudentList() {
        if(studentLiveData==null)
            return 1;
        ((HomeActivity)callingActivity).changeToolBarText(getTitle());
        return 2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void init(int classId, int bookId, int groupId, String className, String bookName){
        this.callingActivity = HomeActivity.getActivity1();

        clientProvider = new APIClientProvider();
        apiInterface = clientProvider.getService();
        if(studentLiveData==null)
            studentLiveData=new MutableLiveData<>();
        //homeViewModel = new HomeViewModel();
        //homeViewModel.setTitle(bookName+"\n"+" کلاس "+ className);
        setTitle(bookName+"\n"+" کلاس "+ className);
        ((HomeActivity)callingActivity).changeToolBarText(getTitle());

        Call<List<StudentResponse>> listCall = apiInterface.getStudents(classId, bookId, groupId);
        ((HomeActivity)callingActivity).showProgressDialog();
        listCall.enqueue(new Callback<List<StudentResponse>>() {
            @Override
            public void onResponse(Call<List<StudentResponse>> call, Response<List<StudentResponse>> response) {

                ((HomeActivity)callingActivity).hideProgressDialog();
                if ( response.code() == 401 || response.code() == 400) {
                    Toast.makeText(callingActivity, "لطفا مجدد وارد برنامه شوید.", Toast.LENGTH_LONG).show();
                    callingActivity.startActivity(new Intent(callingActivity,LoginActivity.class));
                    callingActivity.finish();
                    return;
                }
                if (response.isSuccessful()) {
                   studentLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<StudentResponse>> call, Throwable t) {
                ((HomeActivity)callingActivity).hideProgressDialog();
                Toast.makeText(callingActivity, "اتصال اینترنت را بررسی کنید.", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}
