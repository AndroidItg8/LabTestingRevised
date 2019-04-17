package itg8.com.labtestingapp.survey.model;

import android.arch.persistence.room.util.StringUtil;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.gms.common.util.Strings;

import itg8.com.labtestingapp.BR;
import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.geotechnical.model.GeoTechnicalInvestigation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static itg8.com.labtestingapp.geotechnical.model.GeoTechnicalInvestigation.EMPTY;

public class SurveyModel  extends BaseObservable {
    private static final String SEPERATOR = ",";
    private static final int CATEGORY_MASTER_ID = 12;

    public ObservableBoolean isProgress=new ObservableBoolean(false);

//    Q1) Type of survey to be conducted(Multiple choose options)
//1)Detailed topography survey
//2)Road survey
//3)Transmission line survey
//4)DGPS survey
//5)GPS survey
//    Q2)Area to be Surveyed
//    Ans)Comment Box
//    Q3)Location of site to be surveyed
//    Ans) Comment Box
//    Q4) Any other requirements
//    Ans) Comment Box

    private String[] surveyTypeList = new String[]{"Detailed topography survey",
            "Road survey",
            "Transmission line survey",
            "DGPS survey", "GPS survey"};

    private String survey;
    private String area;
    private String location;
    private String other;
    private String surveyErr;
    private String areaErr;
    private String locationErr;

    public ObservableArrayList<String> checkedVal=new ObservableArrayList<>();

    public ArrayList<Integer> que=new ArrayList<>();

    @Bindable
    public String getSurveyErr() {
        return surveyErr;
    }

    public void setSurveyErr(String surveyErr) {
        this.surveyErr = surveyErr;
        notifyPropertyChanged(BR.surveyErr);
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
    public String getLocationErr() {
        return locationErr;
    }

    public void setLocationErr(String locationErr) {
        this.locationErr = locationErr;
        notifyPropertyChanged(BR.locationErr);
    }

    public void addChecked(String val){
        checkedVal.add(val);
    }

    public void removeChecked(String val){
        checkedVal.remove(val);
    }

    public String[] getSurveyTypeList() {
        return surveyTypeList;
    }


    public void setSurveyTypeList(String[] surveyTypeList) {
        this.surveyTypeList = surveyTypeList;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }


    @BindingAdapter({"values","dataToSet"})
    public static void bindCheckboxChanged(CheckBox checkBox, String val,ObservableArrayList<String> checkedVal) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkedVal.add(val);
                }else {
                    checkedVal.remove(val);
                }
            }
        });
    }

    public void sendRequest(View view){
        boolean isValid=true;
        ArrayList<String> ans=new ArrayList<>();
        if(checkedVal.size()<=0)
        {
            setSurveyErr(EMPTY);
            isValid=false;
        }else {
            setSurvey();
            que.add(10);
            ans.add(survey);
        }
        if(TextUtils.isEmpty(area)){
            setAreaErr(EMPTY);
            isValid=false;
        }else {
            que.add(11);
            ans.add(area);
        }
        if(TextUtils.isEmpty(location)){
            setLocationErr(location);
            isValid=false;
        }else {
            que.add(12);
            ans.add(location);
        }

        if(!TextUtils.isEmpty(other)){
            que.add(13);
            ans.add(other);
        }
        if(isValid){
            isProgress.set(true);
            ((MainActivity) view.getContext()).sendRequest(SurveyModel.CATEGORY_MASTER_ID, que, ans, new MainActivity.OnRequestCallbackListener() {
                @Override
                public void onSuccess() {
                    isProgress.set(false);
                    CommonMethod.showSnackbar(view,view.getContext());
                }

                @Override
                public void onFailure(String msg) {
                    isProgress.set(false);
                    CommonMethod.showErrorSnackbar(view,msg,view.getContext());
                }
            });
        }
    }

    private void setSurvey() {
        StringBuilder csvBuilder = new StringBuilder();

        for(String city : checkedVal){
            csvBuilder.append(city);
            csvBuilder.append(SEPERATOR);
        }
        String csv = csvBuilder.toString();
        survey = csv.substring(0, csv.length() - SEPERATOR.length());
    }

}
