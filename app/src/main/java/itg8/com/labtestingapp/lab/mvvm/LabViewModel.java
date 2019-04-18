package itg8.com.labtestingapp.lab.mvvm;


import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.lab.model.LabModel;

public class LabViewModel extends BaseObservable {
    private static final String TAG = "LabViewModel";
    private final Context context;
    public GenericAdapter<LabModel, LabItemViewModel> genericAdapter;


    public ObservableArrayList<LabModel> models;

    public GenericAdapter.OnItemClickListner listner = new GenericAdapter.OnItemClickListner() {
        @Override
        public void onItemClicked(Object o) {

            Log.d(TAG, "onItemClicked : " + new Gson().toJson((LabModel) o));
             ((MainActivity)context).selectedLabBranch(((LabModel)o));

        }
    };

    public LabViewModel(Context context, List<LabModel> labList) {
        this.context = context;
        models = new ObservableArrayList<>();
        generateRvContent();
        models.addAll(labList);
        genericAdapter.notifyDataSetChanged();

    }

    @BindingAdapter(value = {"customLabAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView,GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);


    }

    private void generateRvContent() {
        LabItemViewModel itemModel = new LabItemViewModel();
        itemModel.setListener(listner);
        genericAdapter= new GenericAdapter<>(models, itemModel);
    }


}
