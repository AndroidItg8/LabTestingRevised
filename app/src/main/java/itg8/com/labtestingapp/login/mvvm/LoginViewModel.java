package itg8.com.labtestingapp.login.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.NetworkCall;
import itg8.com.labtestingapp.common.Prefs;
import itg8.com.labtestingapp.common.UtilSnackbar;
import itg8.com.labtestingapp.login.LoginActivity;
import itg8.com.labtestingapp.login.model.LoginModel;
import itg8.com.labtestingapp.login.model.RegistrationModel;
import okhttp3.ResponseBody;
import itg8.com.labtestingapp.BR;

public class LoginViewModel extends BaseObservable {
    public ObservableInt progressReg;
    public ObservableInt progressLogin;
    public ObservableInt registerVisibility;
    public ObservableInt loginVisibility;
    public ObservableInt isLoginView;
    public ObservableInt isRegView;
    private Context context;

    private LoginModel loginModel;
    private RegistrationModel regModel;
    private String mParam1;

    public LoginViewModel(Context context) {
        this.context = context;
        progressReg=new ObservableInt(View.GONE);
        progressLogin=new ObservableInt(View.GONE);
        registerVisibility=new ObservableInt(View.VISIBLE);
        loginVisibility=new ObservableInt(View.VISIBLE);
        isLoginView=new ObservableInt(View.VISIBLE);
        isRegView=new ObservableInt(View.GONE);
        loginModel=new LoginModel();
        regModel=new RegistrationModel();
    }

    @Bindable
    public LoginModel getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
        notifyPropertyChanged(BR.loginModel);
    }

    @Bindable
    public RegistrationModel getRegModel() {
        return regModel;
    }


    public void setRegModel(RegistrationModel regModel) {
        this.regModel = regModel;
        notifyPropertyChanged(BR.regModel);
    }

    private void hideProgress(ObservableInt progress, ObservableInt view){
        progress.set(View.GONE);
        view.set(View.VISIBLE);
    }

    private void showProgress(ObservableInt progress,ObservableInt view){
        progress.set(View.VISIBLE);
        view.set(View.GONE);
    }

    public void onLoginViewClick(View view){
        isLoginView.set(View.VISIBLE);
        isRegView.set(View.GONE);
    }

    public void onRegViewClick(View view){
        isRegView.set(View.VISIBLE);
        isLoginView.set(View.GONE);
    }

    public void registerBtnClick(View view){
        if(regModel.validate()){
            Observable<ResponseBody> observable =NetworkCall.getController().postRegister(regModel.getMobile(),
                    regModel.getFname(),
                    regModel.getLname(),
                    regModel.getEmail(),
                    regModel.getPassword(),regModel.getPassword(),TextUtils.isEmpty(regModel.getReferenceBy())?"":regModel.getReferenceBy(),0,null);
            Disposable d=observable.flatMap(new Function<ResponseBody, ObservableSource<?>>() {
                @Override
                public ObservableSource<?> apply(ResponseBody responseBody) throws Exception {
                    String res=responseBody.string();
                    if(res!=null) {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.has("flag")) {
                            String flag = jsonObject.getString("flag");
                            if (flag != null && flag.equalsIgnoreCase("1")) {
                                return Observable.just(jsonObject.getString("userid"));
                            }else {
                                return Observable.just(jsonObject.getString("msg"));
                            }
                        }
                    }
                        return Observable.just("-1");
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if (o instanceof String) {
                                if (o.toString().equalsIgnoreCase("-1")) {
                                    errorLogin(view);
                                } else if(TextUtils.isDigitsOnly(o.toString())){
                                    successLogin(o.toString());
                                }else {
                                    errorLogin(view,o.toString());
                                }
                            }
                        }
                    },Throwable::printStackTrace);
        }
    }

    public void onFbClick(View view){
        ((LoginActivity)context).signInFacebook();
    }

    public void onGPlusClick(View view){
        if(context instanceof LoginActivity)
            ((LoginActivity)context).signInGoogle();
        else
            ((MainActivity)context).signInGoogle();
    }

    public void onTwitterClick(View view){

    }

    public void onLoginBtnClick(View view){
        if(loginModel.validate()) {
            Observable<ResponseBody> observable = NetworkCall.getController().login(loginModel.getUsername(), loginModel.getPassword());
            Disposable d = observable.flatMap(new Function<ResponseBody, ObservableSource<?>>() {
                @Override
                public ObservableSource<?> apply(ResponseBody responseBody) throws Exception {
//                {"msg":"Login Success","flag":"1","userid":"19","groupid":"4"}
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    if (jsonObject.has("flag")) {
                        String flag = jsonObject.getString("flag");
                        if (flag != null && flag.equalsIgnoreCase("1")) {
                            return Observable.just(jsonObject.getString("userid"));
                        }
                    }
                    return Observable.just("-1");
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if (o instanceof String) {
                                if (o.toString().equalsIgnoreCase("-1")) {
                                    errorLogin(view);
                                } else {
                                    successLogin(o.toString());
                                }
                            }
                        }
                    },throwable -> {errorLogin(view);});
                            //t-> {errorLogin(view);});
        }
    }

    private void errorLogin(View view) {
        UtilSnackbar.showSnakbarTypeWithMessage(view, "Invalid Credentials", new UtilSnackbar.OnSnackbarActionClickListener() {
            @Override
            public void onRetryClicked() {

            }
        });
    }
    private void errorLogin(View view,String msg) {
        UtilSnackbar.showSnakbarTypeWithMessage(view, msg, new UtilSnackbar.OnSnackbarActionClickListener() {
            @Override
            public void onRetryClicked() {

            }
        });
    }

    @BindingAdapter("error")
    public static void setError(TextInputEditText editText,String msg){
        editText.setError(msg);
    }

    private void successLogin(String s) {
        Prefs.putString(CommonMethod.USERID, s);
        if(context instanceof MainActivity) {
            //TODO App Next Screen afer login
            if (TextUtils.isEmpty(mParam1))
                ((MainActivity) context).requestFragment();
            else if (mParam1.equalsIgnoreCase("1"))
                ((MainActivity) context).openRequestStatusFragment();
            else if (mParam1.equalsIgnoreCase("2"))
                ((MainActivity) context).carryOnRequest();
        }else if(context instanceof LoginActivity){
            ((LoginActivity)context).setResultOk();
        }
    }


    public void setWhereFrom(String mParam1) {
        this.mParam1 = mParam1;
    }
}
