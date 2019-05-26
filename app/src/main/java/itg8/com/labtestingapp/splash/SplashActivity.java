package itg8.com.labtestingapp.splash;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.databinding.ActivitySplashBinding;
import itg8.com.labtestingapp.splash.mvvm.SplashViewModel;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {


    private boolean mVisible;

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_splash);
        setViewModel();
        mVisible = true;
        hide();

    }

    private void setViewModel() {
        SplashViewModel model=new SplashViewModel(this,MyApplication.getInstance(), getSnackbar());
        binding.setSplashViewModel(model);
    }

    private Snackbar getSnackbar() {
        return Snackbar.make(binding.progressbar, "", Snackbar.LENGTH_INDEFINITE);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

    }



}
