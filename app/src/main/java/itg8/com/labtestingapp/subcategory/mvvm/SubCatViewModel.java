package itg8.com.labtestingapp.subcategory.mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.db.repository.SubCategoryRepository;
import itg8.com.labtestingapp.db.tables.SubCategory;
import itg8.com.labtestingapp.home.mvvm.SubCategoryViewModel;

public class SubCatViewModel extends BaseObservable {
    private Context context;
    private SubCategoryRepository repository;
    public ObservableArrayList<SubCategory> models;

    private static final String TAG = "SubCatViewModel";

    public GenericAdapter.OnItemClickListner listner=new GenericAdapter.OnItemClickListner() {
        @Override
        public void onItemClicked(Object o) {
            if(context!=null){
                ((MainActivity)context).startTestFragment(((SubCategory)o));
            }
        }
    };



    public SubCatViewModel(Context context, Fragment homeFragment,int id) {
        this.context = context;
        models=new ObservableArrayList<>();
        repository=ViewModelProviders.of(homeFragment).get(SubCategoryRepository.class);
        repository.getSubCategory(id).observe(homeFragment, new Observer<List<SubCategory>>() {
            @Override
            public void onChanged(@Nullable List<SubCategory> mainCategories) {
                if(mainCategories!=null)
                    models.addAll(mainCategories);
                else
                    Log.d(TAG, "onChanged: mainCategories is null");
            }
        });

    }

    @BindingAdapter(value = {"customSaveMaterialAdapter", "customListener"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, ObservableArrayList<SubCategory> models, GenericAdapter.OnItemClickListner listner) {
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        SubCategoryViewModel itemModel = new SubCategoryViewModel(recyclerView.getContext());
        itemModel.setListner(listner);
        recyclerView.setAdapter(new GenericAdapter<>(models, itemModel));
    }



}
