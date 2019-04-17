package itg8.com.labtestingapp.ndt;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import itg8.com.labtestingapp.BR;

import java.util.ArrayList;

public class NDTModel  extends BaseObservable {

    public static final int CATEGORY_MASTER_ID=11;

    //    Q1) Type of test to be conducted
    //1)Rebound Hammer Test- RH Test
    //2)Ultrasonic Pulse Velocity- UPV Test
    //3)Combined Method UPV &amp; RH Test
    //4)Concrete Cover Measurement by Laser Based Instt
    //    Q2)Type of structure on which test is to be conducted
    //    Ans) comment box
    //    Q3)Tentative no. of locations to be tested
    //    Ans) Comment Box
    //    Q4)Location of site to be tested
    //    Ans)Comment Box
    //    Q5) Any other requirements
    //    Ans) Comment Box

    private String typeOfScript;
    private String[] typeOfScriptData=new String[]{"Rebound Hammer Test- RH Test","Ultrasonic Pulse Velocity- UPV Test","Combined Method UPV &amp; RH Test","Concrete Cover Measurement by Laser Based Instt"};
    private String typeOfStructure;
    private String tentativeLocation;
    private String locationSite;
    private String requirement;
    private String errTypeOfScript;
    private String errTypeOfStructure;
    private String errTentativeLocation;
    private String errLocationSite;
    private String errRequirement;
    public static  final int typeOfScriptQ=5;
    public static final int typeOfStructureQ=6;
    public static final int tentativeLocationQ=7;
    private static final int locationSiteQ=8;
    private static final int requirementQ=9;

    public Integer[] questions={typeOfScriptQ,typeOfStructureQ,tentativeLocationQ,locationSiteQ,requirementQ};

    @Bindable
    public String getErrTypeOfScript() {
        return errTypeOfScript;
    }

    public void setErrTypeOfScript(String errTypeOfScript) {
        this.errTypeOfScript = errTypeOfScript;
        notifyPropertyChanged(BR.errTypeOfScript);
    }

    @Bindable
    public String getErrTypeOfStructure() {
        return errTypeOfStructure;
    }

    public void setErrTypeOfStructure(String errTypeOfStructure) {
        this.errTypeOfStructure = errTypeOfStructure;
        notifyPropertyChanged(BR.errTypeOfStructure);
    }

    @Bindable
    public String getErrTentativeLocation() {
        return errTentativeLocation;
    }

    public void setErrTentativeLocation(String errTentativeLocation) {
        this.errTentativeLocation = errTentativeLocation;
        notifyPropertyChanged(BR.errTentativeLocation);
    }

    @Bindable
    public String getErrLocationSite() {
        return errLocationSite;
    }

    public void setErrLocationSite(String errLocationSite) {
        this.errLocationSite = errLocationSite;
        notifyPropertyChanged(BR.errLocationSite);
    }

    @Bindable
    public String getErrRequirement() {
        return errRequirement;
    }

    public void setErrRequirement(String errRequirement) {
        this.errRequirement = errRequirement;
        notifyPropertyChanged(BR.errRequirement);
    }

    public String getTypeOfScript() {
        return typeOfScript;
    }

    public void setTypeOfScript(String typeOfScript) {
        this.typeOfScript = typeOfScript;
    }

    public String[] getTypeOfScriptData() {
        return typeOfScriptData;
    }

    public void setTypeOfScriptData(String[] typeOfScriptData) {
        this.typeOfScriptData = typeOfScriptData;
    }

    public String getTypeOfStructure() {
        return typeOfStructure;
    }

    public void setTypeOfStructure(String typeOfStructure) {
        this.typeOfStructure = typeOfStructure;
    }

    public String getTentativeLocation() {
        return tentativeLocation;
    }

    public void setTentativeLocation(String tentativeLocation) {
        this.tentativeLocation = tentativeLocation;
    }

    public String getLocationSite() {
        return locationSite;
    }

    public void setLocationSite(String locationSite) {
        this.locationSite = locationSite;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public boolean validate() {
        boolean isValid=true;
        if(TextUtils.isEmpty(getTypeOfScript())){
            isValid=false;
            setErrTypeOfScript("Field is Empty");
        }
        if(TextUtils.isEmpty(getTypeOfStructure())){
            isValid=false;
            setErrTypeOfStructure("Field is Empty");
        }
        if(TextUtils.isEmpty(getTentativeLocation())){
            isValid=false;
            setErrTentativeLocation("Field is Empty");
        }
        if(TextUtils.isEmpty(getLocationSite())){
            isValid=false;
            setErrLocationSite("Field is Empty");
        }
        if(TextUtils.isEmpty(getRequirement())){
            isValid=false;
            setErrRequirement("Field is Empty");
        }
        return isValid;
    }
}
