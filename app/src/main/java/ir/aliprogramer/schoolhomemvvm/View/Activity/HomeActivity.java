package ir.aliprogramer.schoolhomemvvm.View.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.aliprogramer.schoolhomemvvm.R;

public class HomeActivity extends AppCompatActivity {

    static Context  context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this.getApplicationContext();
        setContentView(R.layout.activity_home);

    }
    public static Context getContext() {
        return context;
    }
}
