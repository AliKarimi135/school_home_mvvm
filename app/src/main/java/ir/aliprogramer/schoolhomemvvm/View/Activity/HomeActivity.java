package ir.aliprogramer.schoolhomemvvm.View.Activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import ir.aliprogramer.schoolhomemvvm.R;
import ir.aliprogramer.schoolhomemvvm.ViewModel.HomeViewModel;
import ir.aliprogramer.schoolhomemvvm.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    static Context  context;
    static Activity  activity;
    private HomeViewModel homeViewModel;
    private ActivityHomeBinding  homeBinding;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this.getApplicationContext();
        activity=this;
        homeBinding= DataBindingUtil.setContentView(this,R.layout.activity_home);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        homeViewModel=new HomeViewModel(this,getSupportFragmentManager());
        homeBinding.setHome(homeViewModel);

    }
    public static Context getContext() {
        return context;
    }

    public static Activity getActivity1() {
        return  activity;
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        homeViewModel.onBackPressed();
    }
}
