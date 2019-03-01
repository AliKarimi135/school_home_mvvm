package ir.aliprogramer.schoolhomemvvm.ViewModel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
    private Context context ;

    private APIInterface apiInterface;
    private APIClientProvider clientProvider;


    public MutableLiveData<List<Mark>> markLiveData=new MutableLiveData<>();
    public List<Mark>markList;
    public MarkViewModel() {
       context=HomeActivity.getContext();
    }
    public void init(String bookName,String className,String studentName,int bookId,int studentId){
        this.callingActivity = HomeActivity.getActivity1();

        clientProvider = new APIClientProvider();
        apiInterface = clientProvider.getService();

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

        ((HomeActivity)callingActivity).changeToolBarText(title);
        //homeViewModel.setTitle(title);
        if(markLiveData.getValue()!=null)
            return;
        Call<List<Mark>> listMark=apiInterface.getMarks(bookId,studentId);
        ((HomeActivity)callingActivity).showProgressDialog();
        listMark.enqueue(new Callback<List<Mark>>() {

            @Override
            public void onResponse(Call<List<Mark>> call, Response<List<Mark>> response) {
                ((HomeActivity)callingActivity).hideProgressDialog();
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
                ((HomeActivity)callingActivity).hideProgressDialog();
                Toast.makeText(callingActivity, "اتصال اینترنت را بررسی کنید.", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}

