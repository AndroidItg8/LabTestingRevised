package itg8.com.labtestingapp.request.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import itg8.com.labtestingapp.BR;

public class RequestModel extends BaseObservable {
    private static final String INVALID_ADDRESS="Please enter address";
    private static final String INVALID_CITY="Please select city";
    private static final String INVALID_PINCODE="Please enter pincode";

    private String address;
    private String addressErr;
    private int city;
    private String cityErr;
    private String pincode;
    private String pincodeErr;
    private String lat;
    private String lng;

    @Bindable
    public String getAddressErr() {
        return addressErr;
    }

    public void setAddressErr(String addressErr) {
        this.addressErr = addressErr;
        notifyPropertyChanged(BR.addressErr);
    }

    @Bindable
    public String getCityErr() {
        return cityErr;
    }

    public void setCityErr(String cityErr) {
        this.cityErr = cityErr;
        notifyPropertyChanged(BR.cityErr);
    }

    @Bindable
    public String getPincodeErr() {
        return pincodeErr;
    }

    public void setPincodeErr(String pincodeErr) {
        this.pincodeErr = pincodeErr;
        notifyPropertyChanged(BR.pincodeErr);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public boolean validate(){
        boolean isValid=true;
        if(TextUtils.isEmpty(address))
        {
            isValid=false;
            setAddressErr(INVALID_ADDRESS);
        }if(TextUtils.isEmpty(pincode)){
            isValid=false;
            setPincodeErr(INVALID_PINCODE);
        }
        return isValid;
    }
}
