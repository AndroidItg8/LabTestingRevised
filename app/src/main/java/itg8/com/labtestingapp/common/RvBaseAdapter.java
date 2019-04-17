package itg8.com.labtestingapp.common;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import itg8.com.labtestingapp.common.genericRv.GenericAdapter;

public class RvBaseAdapter extends BaseObservable {

    @BindingAdapter(value = {"customTestAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }
}
