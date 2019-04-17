package itg8.com.labtestingapp.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.BaseActivity;
import itg8.com.labtestingapp.common.NetworkCall;
import itg8.com.labtestingapp.common.UtilSnackbar;
import okhttp3.ResponseBody;

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
