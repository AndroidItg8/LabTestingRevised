package itg8.com.labtestingapp.login;


import android.os.Bundle;

import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.facebook.login.widget.LoginButton;


import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.BaseActivity;

public class LoginActivity extends BaseActivity {


    private ProgressBar progressBar;
    private FrameLayout frameLayout;

    private static final String TAG = "LoginActivity";
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar=findViewById(R.id.progressbar);
        frameLayout=findViewById(R.id.container);
        startLoginFragment();
    }

    @Override
    public void canfinish() {
        finish();
    }


    private void startLoginFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,LoginFragment.newInstance("2","")).commitAllowingStateLoss() ;
    }


    public void setLoginButton(LoginButton loginButton) {
        this.loginButton = loginButton;
        initFacebookClient();
    }

    @Override
    public LoginButton getLoginButton() {
        return loginButton;
    }
}
