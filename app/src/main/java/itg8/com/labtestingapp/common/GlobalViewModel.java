package itg8.com.labtestingapp.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;


import com.google.gson.Gson;

import itg8.com.labtestingapp.lab.model.LabModel;

public class GlobalViewModel extends ViewModel {
    private static final String TAG = "MainGlobalViewModel";
    private MutableLiveData<LabModel> itemViewModel = new MutableLiveData<>() ;





    public void setLabModel(LabModel model){
        Log.d(TAG, "setLabModel: "+new Gson().toJson(model));
        itemViewModel.setValue(model);


    }


    public LiveData<LabModel> getModel(){
        Log.d(TAG, "getLabModel: "+new Gson().toJson(itemViewModel.getValue()));
        return itemViewModel;
    }


}
