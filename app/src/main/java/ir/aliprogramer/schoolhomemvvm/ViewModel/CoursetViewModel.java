package ir.aliprogramer.schoolhomemvvm.ViewModel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import ir.aliprogramer.schoolhomemvvm.AppPreferenceTools;
import ir.aliprogramer.schoolhomemvvm.Model.CourseModel.Course;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity;
import ir.aliprogramer.schoolhomemvvm.View.Activity.LoginActivity;
import ir.aliprogramer.schoolhomemvvm.View.Fragment.MarkFragment;
import ir.aliprogramer.schoolhomemvvm.View.Fragment.StudentFragment;
import ir.aliprogramer.schoolhomemvvm.WebService.APIClientProvider;
import ir.aliprogramer.schoolhomemvvm.WebService.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursetViewModel extends ViewModel {

    int userId,classId,type;

    private Activity callingActivity ;

    private Context context;

    private APIInterface apiInterface;

    private AppPreferenceTools appPreferenceTools;

    private APIClientProvider clientProvider;

    //private HomeViewModel homeViewModel;

    public MutableLiveData<List<Course>>courseLiveData=new MutableLiveData<>();

   public CoursetViewModel() {
       context=HomeActivity.getContext();
    }

    public void init() {
        this.callingActivity = HomeActivity.getActivity1();
        context=HomeActivity.getContext();
        appPreferenceTools = new AppPreferenceTools(context);
        clientProvider = new APIClientProvider();
        apiInterface = clientProvider.getService();

        //homeViewModel = new HomeViewModel();
        //homeViewModel.setTitle( "آقای " + appPreferenceTools.getUserName());
        ((HomeActivity)callingActivity).changeToolBarText("آقای " + appPreferenceTools.getUserName());
            type = appPreferenceTools.getType();
            userId = appPreferenceTools.getUserId();
            classId = appPreferenceTools.getClassId();
            Call<List<Course>> listCall;

            if (type == 1) {
                listCall = apiInterface.getCourse(userId, 1);
            } else {
                listCall = apiInterface.getCourse(classId, 0);
            }

        ((HomeActivity)callingActivity).showProgressDialog();
            listCall.enqueue(new Callback<List<Course>>() {
                @Override
                public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                    ((HomeActivity)callingActivity).hideProgressDialog();

                    if (response.code() == 401 || response.code() == 400) {
                        Toast.makeText(context, "لطفا مجدد وارد برنامه شوید.", Toast.LENGTH_LONG).show();
                        context.startActivity(new Intent(context, LoginActivity.class));
                        callingActivity.finish();
                        return;
                    }
                    if (response.isSuccessful()) {
                        courseLiveData.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Course>> call, Throwable t) {
                    ((HomeActivity)callingActivity).hideProgressDialog();
                    Toast.makeText(context, "اتصال اینترنت را بررسی کنید.", Toast.LENGTH_LONG).show();
                    return;
                }
            });

    }

    public void getStudentFragment(int classId,int groupId,int bookId, FragmentManager manager, String className, String bookName){
        StudentFragment studentFragment=new StudentFragment();
        studentFragment.setData(classId,groupId,bookId,manager,className,bookName);
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.frame_layout,studentFragment,"studentFragment");
        transaction.addToBackStack("addstudentFragment");
        transaction.commit();
    }

    public void getMarkFragment(FragmentManager manager,int studentId,int bookId,String className, String bookName,String studentName){
        MarkFragment markFragment=new MarkFragment();
        markFragment.setData(studentId, bookId,className, bookName,studentName);
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.frame_layout,markFragment,"markFragment");
        transaction.addToBackStack("addmarkFragment");
        transaction.commit();
    }
}
