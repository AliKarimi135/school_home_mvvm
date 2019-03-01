package ir.aliprogramer.schoolhomemvvm.ViewModel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import ir.aliprogramer.schoolhomemvvm.Model.MarkModel.Mark;
import ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity;
import ir.aliprogramer.schoolhomemvvm.View.Activity.LoginActivity;
import ir.aliprogramer.schoolhomemvvm.WebService.APIClientProvider;
import ir.aliprogramer.schoolhomemvvm.WebService.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkViewModel extends ViewModel {

    private Activity callingActivity ;

    private APIInterface apiInterface;
    private APIClientProvider clientProvider;

    private HomeViewModel homeViewModel;

    public MutableLiveData<List<Mark>> markLiveData=new MutableLiveData<>();
    public List<Mark>markList;
    public MarkViewModel() {
    }
    public void init(String bookName,String className,String studentName,int bookId,int studentId){
        this.callingActivity = HomeActivity.getActivity1();

        clientProvider = new APIClientProvider();
        apiInterface = clientProvider.getService();

        homeViewModel = new HomeViewModel();

        String title="";
        title+=bookName;
        if(!className.equals(" ")) {
            title += " کلاس ";
            title += className;
        }
        if(!studentName.equals("")) {
            title+="\n";
            title += " آقای ";
            title += studentName;
        }else{
            title="نمرات کتاب ";
            title+=bookName;
        }
        homeViewModel.setTitle(title);

        Call<List<Mark>> listMark=apiInterface.getMarks(bookId,studentId);
        homeViewModel.showProgressDialog();
        listMark.enqueue(new Callback<List<Mark>>() {

            @Override
            public void onResponse(Call<List<Mark>> call, Response<List<Mark>> response) {
                homeViewModel.hideProgressDialog();
                if ( response.code() == 401 || response.code() == 400) {
                    Toast.makeText(callingActivity, "لطفا مجدد وارد برنامه شوید.", Toast.LENGTH_LONG).show();
                    callingActivity.startActivity(new Intent(callingActivity,LoginActivity.class));
                    callingActivity.finish();
                    return;
                }
                if (response.isSuccessful()) {
                    markList=response.body();
                    Collections.reverse(markList);
                    markLiveData.setValue(markList);
                }
            }

            @Override
            public void onFailure(Call<List<Mark>> call, Throwable t) {
                homeViewModel.hideProgressDialog();
                Toast.makeText(callingActivity, "اتصال اینترنت را بررسی کنید.", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}

