package itg8.com.labtestingapp.home.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.db.repository.MainCategoryRepository;
import itg8.com.labtestingapp.db.tables.MainCategory;
import itg8.com.labtestingapp.db.tables.SubCategory;
import itg8.com.labtestingapp.home.HomeFragment;

public class HomeViewModel extends BaseObservable {

    private static final String TAG = "HomeViewModel";

    private Context context;
    private MainCategoryRepository repository;
    public ObservableArrayList<MainCategory> models;

    public GenericAdapter.OnItemClickListner listner=new GenericAdapter.OnItemClickListner() {
        @Override
        public void onItemClicked(Object o) {
          if(context!=null){
              ((MainActivity)context).startFragment(((MainCategory)o));
          }
        }
    };



    public HomeViewModel(Context context, Fragment homeFragment) {
        this.context = context;
        models=new ObservableArrayList<>();
        repository=ViewModelProviders.of(homeFragment).get(MainCategoryRepository.class);
        repository.getMainCategory().observe(homeFragment, new Observer<List<MainCategory>>() {
            @Override
            public void onChanged(@Nullable List<MainCategory> mainCategories) {
                if(mainCategories!=null)
                    models.addAll(mainCategories);
                else
                    Log.d(TAG, "onChanged: mainCategories is null");
            }
        });

    }

    @BindingAdapter(value = {"customSaveMaterialAdapter", "customListener"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, ObservableArrayList<MainCategory> models, GenericAdapter.OnItemClickListner listner) {
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        CategoryViewModel itemModel = new CategoryViewModel(recyclerView.getContext());
        itemModel.setListener(listner);
        recyclerView.setAdapter(new GenericAdapter<>(models, itemModel));
    }


}
