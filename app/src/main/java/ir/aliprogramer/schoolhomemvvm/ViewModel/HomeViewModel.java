package ir.aliprogramer.schoolhomemvvm.ViewModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    ProgressDialog progressDialog ;

    public HomeViewModel() {
        context=HomeActivity.getContext();
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(context.getApplicationContext());
        }
        //appPreferenceTools=new AppPreferenceTools(callingActivity.getApplicationContext());
    }

    public HomeViewModel(Activity callingActivity, FragmentManager fragmentManager) {
        this.callingActivity = callingActivity;
        this.fragmentManager = fragmentManager;
        this.appPreferenceTools=new AppPreferenceTools(callingActivity);
        setTitle("آقای "+appPreferenceTools.getUserName());
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this.callingActivity);
        }
        CourseFragment courseFragment=new CourseFragment();
        courseFragment.setData(fragmentManager);

        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout,courseFragment,"courseFragment");
        transaction.commit();
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    /* Show progress dialog. */
    public void showProgressDialog()
    {
        // Set progress dialog display message.
        progressDialog.setMessage("لطفا منتظر بمانید...");

        // The progress dialog can not be cancelled.
        progressDialog.setCancelable(false);

      //  progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
        // Show it.
       //progressDialog.show();
    }

    /* Hide progress dialog. */
    public void hideProgressDialog()
    {
        // Close it.
       // progressDialog.hide();
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
