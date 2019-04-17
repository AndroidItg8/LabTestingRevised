package itg8.com.labtestingapp.login.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import itg8.com.labtestingapp.BR;

public class LoginModel extends BaseObservable {
    private static final String ENTER_USERNAME = "Please enter mobile number";
    private static final String ENTER_PASSWORD = "Please enter password";
    private static final String INVALID_USERNAME="please enter valid mobile number";
    private static final String INVALID_PASSWORD="Password must be minimum 6 character long";
    String userNameErr;
    String passwordErr;
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bindable
    public String getUserNameErr() {
        return userNameErr;
    }

    public void setUserNameErr(String userNameErr) {
        this.userNameErr = userNameErr;
        notifyPropertyChanged(BR.userNameErr);
    }

    @Bindable
    public String getPasswordErr() {
        return passwordErr;
    }

    public void setPasswordErr(String passwordErr) {
        this.passwordErr = passwordErr;
        notifyPropertyChanged(BR.passwordErr);
    }

    public boolean validate(){
        boolean isValid=true;
        if(TextUtils.isEmpty(username)) {
            setUserNameErr(ENTER_USERNAME);
            isValid=false;
        }if(TextUtils.isEmpty(password)){
            setPasswordErr(ENTER_PASSWORD);
            isValid=false;
        }if(!TextUtils.isEmpty(username) && username.length()<10){
            setUserNameErr(INVALID_USERNAME);
            isValid=false;
        }if(!TextUtils.isEmpty(password) && password.length()<6){
            setPasswordErr(INVALID_PASSWORD);
            isValid=false;
        }
        return isValid;
    }
}
