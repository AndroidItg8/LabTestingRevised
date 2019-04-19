package itg8.com.labtestingapp.login.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Patterns;

import itg8.com.labtestingapp.BR;

public class RegistrationModel extends BaseObservable {
    private static final String INVALID_FNAME="Please enter first name";
    private static final String INVALID_LNAME="Please enter last name";
    private static final String INVALID_EMAIL="Please enter email address";
    private static final String INVALID_EMAIL_VAL="Please enter valid email id";
    private static final String ENTER_USERNAME = "Please enter mobile number";
    private static final String ENTER_PASSWORD = "Please enter password";
    private static final String INVALID_USERNAME="please enter valid mobile number";
    private static final String INVALID_PASSWORD="Password must be minimum 6 character long";
    private static final String INVALID_STATE="Please select your valid state";
    private static final String INVALID_CITY="Please select your valid city";

    private String fname;
    private String fnameErr;
    private String lname;
    private String lnameErr;
    private String email;
    private String emailErr;
    private String mobile;
    private String mobileErr;
    private String password;
    private String passwordErr;
    private int state;
    private int city;
    private String referenceBy;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }



    @Bindable
    public String getStateErr() {
        return stateErr;
    }

    public void setStateErr(String stateErr) {
        this.stateErr = stateErr;
        notifyPropertyChanged(BR.stateErr);
    }

    @Bindable
    public String getCityErr() {
        return cityErr;
    }

    public void setCityErr(String cityErr) {
        this.cityErr = cityErr;
        notifyPropertyChanged(BR.cityErr);
    }

    private String stateErr;
    private String cityErr;


    public String getReferenceBy() {
        return referenceBy;
    }

    public void setReferenceBy(String referenceBy) {
        this.referenceBy = referenceBy;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    @Bindable
    public String getFnameErr() {
        return fnameErr;
    }


    @Bindable
    public String getLnameErr() {
        return lnameErr;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFnameErr(String fnameErr) {
        this.fnameErr = fnameErr;
        notifyPropertyChanged(BR.fnameErr);
    }

    public void setLname(String lname) {
        this.lname = lname;

    }

    public void setLnameErr(String lnameErr) {
        this.lnameErr = lnameErr;
        notifyPropertyChanged(BR.lnameErr);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
    public String getEmailErr() {
        return emailErr;
    }

    public void setEmailErr(String emailErr) {
        this.emailErr = emailErr;
        notifyPropertyChanged(BR.emailErr);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Bindable
    public String getMobileErr() {
        return mobileErr;
    }

    public void setMobileErr(String mobileErr) {
        this.mobileErr = mobileErr;
        notifyPropertyChanged(BR.mobileErr);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if(TextUtils.isEmpty(mobile)) {
            setMobileErr(ENTER_USERNAME);
            isValid=false;
        }if(TextUtils.isEmpty(password)){
            setPasswordErr(ENTER_PASSWORD);
            isValid=false;
        }if(!TextUtils.isEmpty(mobile) && mobile.length()<10){
            setMobileErr(INVALID_USERNAME);
            isValid=false;
        }if(!TextUtils.isEmpty(password) && password.length()<6){
            setPasswordErr(INVALID_PASSWORD);
            isValid=false;
        }if(TextUtils.isEmpty(fname)){
            setFnameErr(INVALID_FNAME);
            isValid=false;
        }if(TextUtils.isEmpty(lname)){
            setLnameErr(INVALID_LNAME);
            isValid=false;
        }if(TextUtils.isEmpty(email)){
            setEmailErr(INVALID_EMAIL);
            isValid=false;
        }if(!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            setEmailErr(INVALID_EMAIL_VAL);
            isValid=false;
        }
        return isValid;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }
}
