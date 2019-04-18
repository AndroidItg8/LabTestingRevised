package itg8.com.labtestingapp.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import io.reactivex.Observable;
import itg8.com.labtestingapp.lab.model.LabModel;
import itg8.com.labtestingapp.lab.mvvm.LabItemViewModel;

public class GlobalViewModel extends ViewModel {

    private MutableLiveData<LabModel> itemViewModel = new MutableLiveData<>() ;


    public void setLabModel(LabModel model){
        itemViewModel.setValue(model);


    }

    public LiveData<LabModel> getModel(){
        return itemViewModel;
    }


}
