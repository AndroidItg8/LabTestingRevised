package itg8.com.labtestingapp.lab.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.common.genericRv.ViewModel;

import itg8.com.labtestingapp.lab.model.LabModel;

public class LabItemViewModel extends BaseObservable implements ViewModel<LabModel> {
    private Context context;
    private GenericAdapter.OnItemClickListner listner;
    public LabModel model;

    public LabItemViewModel() {
    }



    @Override
    public int layoutId() {
        return R.layout.item_rv_lab;
    }

    @Override
    public void setModel(LabModel labModel) {
        this.model = labModel;
    }

    @Bindable
    public LabModel getModel() {
        return model;
    }

    public void setListener(GenericAdapter.OnItemClickListner listner) {
        this.listner = listner;
    }

    public void onItemClicked(int position, LabModel labModel){
        if(listner!=null)
            listner.onItemClicked(labModel);
    }




}
