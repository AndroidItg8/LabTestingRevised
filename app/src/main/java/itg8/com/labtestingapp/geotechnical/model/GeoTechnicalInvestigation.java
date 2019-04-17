package itg8.com.labtestingapp.geotechnical.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import itg8.com.labtestingapp.BR;

public class GeoTechnicalInvestigation extends BaseObservable {
    public static final String EMPTY = "Field is Empty";
    public static final int CATEGORY_MASTER_ID = 10;

//    Q1)Purpose of investigation
//    Ans)Comment Box
//    Q2)Area in (Hectares) to be investigated
//    Ans) Comment Box
//    Q3)Location of site to be investigated
//    Ans) Comment Box
//    Q4)Any  other requirements
//    Ans) Comment Box

    private String purposeOfInvestigation;
    private String area;
    private String locationOfSite;
    private String other;
    private String purposeOfInvestigationErr;
    private String areaErr;
    private String locationOfSiteErr;
    private String otherErr;
    private ArrayList<Integer> questions=new ArrayList<Integer>();


    @Bindable
    public String getPurposeOfInvestigationErr() {
        return purposeOfInvestigationErr;
    }

    public void setPurposeOfInvestigationErr(String purposeOfInvestigationErr) {
        this.purposeOfInvestigationErr = purposeOfInvestigationErr;
        notifyPropertyChanged(BR.purposeOfInvestigationErr);
    }

    @Bindable
    public String getAreaErr() {
        return areaErr;
    }

    public void setAreaErr(String areaErr) {
        this.areaErr = areaErr;
        notifyPropertyChanged(BR.areaErr);
    }

    @Bindable
    public String getLocationOfSiteErr() {
        return locationOfSiteErr;
    }

    public void setLocationOfSiteErr(String locationOfSiteErr) {
        this.locationOfSiteErr = locationOfSiteErr;
        notifyPropertyChanged(BR.locationOfSiteErr);
    }

    @Bindable
    public String getOtherErr() {
        return otherErr;
    }

    public void setOtherErr(String otherErr) {
        this.otherErr = otherErr;
        notifyPropertyChanged(BR.otherErr);
    }

    public String getPurposeOfInvestigation() {
        return purposeOfInvestigation;
    }

    public void setPurposeOfInvestigation(String purposeOfInvestigation) {
        this.purposeOfInvestigation = purposeOfInvestigation;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocationOfSite() {
        return locationOfSite;
    }

    public void setLocationOfSite(String locationOfSite) {
        this.locationOfSite = locationOfSite;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public ArrayList<Integer> getQuestions() {
        return questions;
    }

    public boolean isValid(){
        questions.clear();
        boolean isValid=true;
        if(TextUtils.isEmpty(purposeOfInvestigation)){
            isValid=false;
            setPurposeOfInvestigationErr(EMPTY);
        }else {
            questions.add(1);
        }
        if(TextUtils.isEmpty(area)){
            isValid=false;
            setAreaErr(EMPTY);
        }else {
            questions.add(2);
        }
        if(TextUtils.isEmpty(locationOfSite)){
            isValid=false;
            setLocationOfSiteErr(EMPTY);
        }else {
            questions.add(3);
        }
        if(!TextUtils.isEmpty(other)){
            questions.add(4);
        }

        return isValid;
    }
}
