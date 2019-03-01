package ir.aliprogramer.schoolhomemvvm.ViewModel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;

import ir.aliprogramer.schoolhomemvvm.BR;
import ir.aliprogramer.schoolhomemvvm.Model.MarkModel.Mark;
import ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity;
import ir.aliprogramer.schoolhomemvvm.View.Activity.LoginActivity;
import ir.aliprogramer.schoolhomemvvm.WebService.APIClientProvider;
import ir.aliprogramer.schoolhomemvvm.WebService.APIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity.getContext;

public class EditAndDeleteMarkViewModel extends BaseObservable {
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

    int bookId,studentId,Itemposition;
    String oldMark,oldDescription;
    Dialog dialog;
    public EditAndDeleteMarkViewModel(Dialog dialog, int bookId, int studentId, int mark, String description,int Itemposition) {
        this.mark =mark+"";
        this.description = description;
        oldMark=mark+"";
        oldDescription=description;
        markError = "";
        descriptionError = "";
        this.bookId=bookId;
        this.studentId=studentId;
        this.dialog=dialog;
        this.Itemposition=Itemposition;
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
        if(getMark().equals(oldMark) && getDescription().equals(oldDescription)){
            dialog.dismiss();
            return false;
        }
        return true;
    }
    public void update() {
        if (checkInput()) {
            updateMark(bookId, Integer.parseInt(getMark()), getDescription(), Itemposition);
            dialog.dismiss();
        }else {
            return;
        }
    }
    public void delete() {
        deleteMark(bookId,Itemposition);
        dialog.dismiss();
        return;
    }
    public void cancel() {
        dialog.dismiss();
        return;
    }

    private void updateMark(int id, final int mark, final String descriptionSt, final int Itemposition) {
        Call<ResponseBody> bodyCall = apiInterface.updateMark(id, mark, descriptionSt);
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 401 || response.code() == 400) {
                    Toast.makeText(context, "نمره ویرایش نشد.لطفا مجدد وارد برنامه شوید.", Toast.LENGTH_LONG).show();
                    (context).startActivity(new Intent(context, LoginActivity.class));
                    callingActivity.finish();
                    return;
                }
                if (response.isSuccessful()) {

                    Mark m = markViewModel.markLiveData.getValue().get(Itemposition);
                    m.setMark(mark);
                    m.setDescription(descriptionSt);
                    markViewModel.markLiveData.getValue().remove(Itemposition);
                    markViewModel.markLiveData.getValue().add(Itemposition, m);
                    //updateAdapterData(markList);
                    Toast.makeText(context, "ویرایش نمره با موفقیت انجام شد.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "ویرایش نمره انجام نشد.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "اتصال اینترنت را بررسی کنید.", Toast.LENGTH_LONG).show();
                return;
            }
        });

    }

    private void deleteMark( int id, final int Itemposition) {
        Call<ResponseBody> bodyCall = apiInterface.destroyMark(id);
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 401 || response.code() == 400) {
                    Toast.makeText(context, "نمره حذف نشد.لطفا مجدد وارد برنامه شوید.", Toast.LENGTH_LONG).show();
                    callingActivity.startActivity(new Intent(context, LoginActivity.class));
                    callingActivity.finish();
                    return;
                }
                if (response.isSuccessful()) {
                    markViewModel.markLiveData.getValue().remove(Itemposition);
                    //updateAdapterData(markList);
                    Toast.makeText(context, "نمره با موفقیت حذف شد", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "نمره حذف نشد", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "اتصال اینترنت را بررسی کنید.", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}
