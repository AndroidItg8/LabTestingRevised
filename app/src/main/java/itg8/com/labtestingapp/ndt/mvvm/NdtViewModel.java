package itg8.com.labtestingapp.ndt.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.ndt.NDTModel;

public class NdtViewModel extends BaseObservable {
    private Context context;
    public ObservableBoolean isProgress;

    private NDTModel model;



    public NdtViewModel(Context context,NDTModel model) {
        this.context = context;
        isProgress=new ObservableBoolean(false);
        this.model = model;
    }


    public NDTModel getModel() {
        return model;
    }

    @BindingAdapter({"dataModel"})
    public static void setAdapter(Spinner spinner, NDTModel model){
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (spinner.getContext(), android.R.layout.simple_spinner_item,
                        model.getTypeOfScriptData()); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setTypeOfScript(spinnerArrayAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @BindingAdapter({"error"})
    public static void setError(TextInputLayout layout,String err){
        layout.setError(err);
    }

    public void sendRequest(View view){

        if(model.validate()) {
            ArrayList<Integer> questionList = new ArrayList<Integer>(Arrays.asList(model.questions));
            ArrayList<String> ans = new ArrayList<>();
            ans.add(model.getTypeOfScript());
            ans.add(model.getTypeOfStructure());
            ans.add(model.getTentativeLocation());
            ans.add(model.getLocationSite());
            ans.add(model.getRequirement());
            isProgress.set(true);
            ((MainActivity) context).sendRequest(NDTModel.CATEGORY_MASTER_ID, questionList, ans, new MainActivity.OnRequestCallbackListener() {
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
