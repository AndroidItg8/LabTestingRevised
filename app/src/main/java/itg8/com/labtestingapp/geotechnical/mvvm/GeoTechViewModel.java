package itg8.com.labtestingapp.geotechnical.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.UtilSnackbar;
import itg8.com.labtestingapp.geotechnical.model.GeoTechnicalInvestigation;

public class GeoTechViewModel extends BaseObservable {

    public ObservableBoolean isProgress;
    public GeoTechnicalInvestigation model;
    private Context context;

    public GeoTechViewModel(Context context) {
        this.context = context;
        isProgress=new ObservableBoolean(false);
        model=new GeoTechnicalInvestigation();
    }

    @BindingAdapter({"error"})
    public static void setError(TextInputLayout layout, String err){
        layout.setError(err);
    }

    public void sendRequest(View view){
        if(model.isValid()){
            ArrayList<String> ans=new ArrayList<>();
            ans.add(model.getPurposeOfInvestigation());
            ans.add(model.getArea());
            ans.add(model.getLocationOfSite());
            ans.add(model.getOther());
            isProgress.set(true);
            ((MainActivity) context).sendRequest(GeoTechnicalInvestigation.CATEGORY_MASTER_ID, model.getQuestions(), ans, new MainActivity.OnRequestCallbackListener() {
                @Override
                public void onSuccess() {
                    isProgress.set(false);
                    CommonMethod.showSnackbar(view,context);
                }

                @Override
                public void onFailure(String msg) {
                    isProgress.set(false);
                    CommonMethod.showErrorSnackbar(view,msg,context);
                }
            });

        }
    }






}
