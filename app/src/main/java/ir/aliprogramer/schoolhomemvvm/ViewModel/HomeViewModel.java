package ir.aliprogramer.schoolhomemvvm.ViewModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.WindowManager;

import ir.aliprogramer.schoolhomemvvm.AppPreferenceTools;
import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.View.Activity.HomeActivity;
import ir.aliprogramer.schoolhomemvvm.View.Fragment.CourseFragment;
import ir.aliprogramer.schoolhomemvvm.BR;
public class HomeViewModel extends BaseObservable {
    @Bindable
    public String title;

    private Activity callingActivity ;

    private Context context ;


    private AppPreferenceTools appPreferenceTools;

    private FragmentManager fragmentManager;



    public HomeViewModel( ) {
        context=HomeActivity.getContext();
        this.callingActivity = HomeActivity.getActivity1();
    }

    public HomeViewModel(Activity callingActivity, FragmentManager fragmentManager) {
        this.callingActivity = callingActivity;
        this.fragmentManager = fragmentManager;
        this.appPreferenceTools=new AppPreferenceTools(callingActivity);
        setTitle("آقای "+appPreferenceTools.getUserName());

        CourseFragment courseFragment=new CourseFragment();
        courseFragment.setData(fragmentManager);
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout,courseFragment,"courseFragment");
        transaction.commit();
    }
    @Bindable
    public void setTitle(String title2) {
        this.title = title2;
        notifyPropertyChanged(BR.title);
        Log.d("title","change title"+title);
    }
    @Bindable
    public String getTitle() {
        return title;
    }


    /* Show progress dialog. */
    public void showProgressDialog(ProgressDialog progressDialog )
    {
        // Set progress dialog display message.
        progressDialog.setMessage("لطفا منتظر بمانید...");

        // The progress dialog can not be cancelled.
        progressDialog.setCancelable(false);


       progressDialog.show();
    }

    /* Hide progress dialog. */
    public void hideProgressDialog(ProgressDialog progressDialog )
    {
        // Close it.
       progressDialog.hide();
    }
    public void onBackPressed(){
        if(fragmentManager.getBackStackEntryCount()>0) {
            fragmentManager.popBackStack();
        }else{
            //callingActivity.onBackPressed();
            appPreferenceTools.removeAllPrefs();
            callingActivity.finish();
        }
    }
}
