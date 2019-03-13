package ir.aliprogramer.schoolhomemvvm.ViewModel;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.aliprogramer.schoolhomemvvm.AppPreferenceTools;
import ir.aliprogramer.schoolhomemvvm.BR;
import ir.aliprogramer.schoolhomemvvm.Model.MarkModel.Mark;
import ir.aliprogramer.schoolhomemvvm.Model.MarkModel.MarkResponse;
import ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity;
import ir.aliprogramer.schoolhomemvvm.View.Activity.LoginActivity;
import ir.aliprogramer.schoolhomemvvm.View.Adapter.MarkAdapter;
import ir.aliprogramer.schoolhomemvvm.View.Fragment.MarkFragment;
import ir.aliprogramer.schoolhomemvvm.WebService.APIClientProvider;
import ir.aliprogramer.schoolhomemvvm.WebService.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity.getContext;

public class AddMarkViewModel extends BaseObservable {
    @Bindable
    public String mark;
    @Bindable
    public String description;
    @Bindable
    public String markError;
    @Bindable
    public String descriptionError;

    private Activity callingActivity;

    private Context context;

    private APIInterface apiInterface;

    private MarkViewModel markViewModel;

    private APIClientProvider clientProvider;

    int bookId,studentId;
    Dialog dialog;
    List<Mark> markList;
    MutableLiveData<List<Mark>> markLiveData;
    MarkAdapter markAdapter;
    public AddMarkViewModel(Dialog dialog, int bookId, int studentId, List<Mark> markList, MutableLiveData<List<Mark>> markLiveData, MarkAdapter markAdapter) {
        mark = "";
        description = "";
        markError = "";
        descriptionError = "";
        this.bookId=bookId;
        this.studentId=studentId;
        this.dialog=dialog;
        this.markList=markList;
        this.markAdapter=markAdapter;
        this.markLiveData=markLiveData;
        this.callingActivity = HomeActivity.getActivity1();
        context = getContext();
        markViewModel = new MarkViewModel();
        clientProvider = new APIClientProvider();
        apiInterface = clientProvider.getService();
    }


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMarkError(String markError) {
        this.markError = markError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

    public boolean checkInput() {
        //clear message in view
        setMarkError("");
        notifyPropertyChanged(BR.markError);
        setDescriptionError("");
        notifyPropertyChanged(BR.descriptionError);

        if (getMark().isEmpty()) {
            setMarkError("لطفا نمره را وارد کنید.");
            notifyPropertyChanged(BR.markError);
        }
        if (getDescription().isEmpty()) {
            setDescriptionError("لطفا توضیحاتی در مورد نمره وارد کنید.");
            notifyPropertyChanged(BR.descriptionError);
        }
        if (getMark().isEmpty() || getDescription().isEmpty())
            return false;
        int markNumber = Integer.parseInt(mark);
        if (!(markNumber >= 0 && markNumber <= 20)) {
            setMarkError("نمره بین صفر تا بیست وارد کنید.");
            notifyPropertyChanged(BR.markError);
            return false;
        }

        return true;
    }

    public void add() {
        if (checkInput()) {
            saveMark(Integer.parseInt(mark), getDescription(), bookId, studentId);
            dialog.dismiss();
        }else {
          return;
        }
    }

    public void cancel() {
        dialog.dismiss();
        return;
    }

    private void saveMark(final int mark, final String descriptionSt, int bookId, int studentId) {

        Call<MarkResponse> listCall = apiInterface.setMark(bookId, studentId, mark, descriptionSt);
        listCall.enqueue(new Callback<MarkResponse>() {
            @Override
            public void onResponse(Call<MarkResponse> call, Response<MarkResponse> response) {
                if (response.code() == 401 || response.code() == 400) {
                    Toast.makeText(getContext(), "نمره ثبت نشد.لطفا مجدد وارد برنامه شوید.", Toast.LENGTH_LONG).show();
                    callingActivity.startActivity(new Intent(callingActivity, LoginActivity.class));
                    callingActivity.finish();
                    return;
                }
                if (response.isSuccessful()) {
                    Mark m = new Mark();
                    m.setId(response.body().getId());
                    m.setMark(mark);
                    m.setDescription(descriptionSt);
                    m.setMonth(response.body().getMonth());
                    m.setDay(response.body().getDay());

                    markList.add(0,m);
                    markLiveData.getValue().add(0,m);
                    markAdapter.notifyDataSetChanged();

                    Toast.makeText(getContext(), "نمره با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "نمره ثبت نشد", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MarkResponse> call, Throwable t) {
                Toast.makeText(getContext(), "اتصال اینترنت را بررسی کنید.", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}
