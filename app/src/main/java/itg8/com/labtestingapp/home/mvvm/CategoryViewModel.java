package itg8.com.labtestingapp.home.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.common.genericRv.ViewModel;
import itg8.com.labtestingapp.db.tables.MainCategory;

public class CategoryViewModel extends BaseObservable implements ViewModel<MainCategory> {

    private static final String TAG = "CategoryViewModel";

    private final Context context;
    private  static File directory;
    private GenericAdapter.OnItemClickListner listner;
    public MainCategory model;

    public CategoryViewModel(Context context) {
        this.context=context;
        directory = context.getCacheDir();

    }

    public void setListener(GenericAdapter.OnItemClickListner listner) {
        this.listner = listner;

    }

    @Override
    public int layoutId() {
        return R.layout.item_category_menus;
    }

    @Override
    public void setModel(MainCategory category) {
        this.model=category;
    }

    public void onImageClick(int position, MainCategory category){
        if(listner!=null)
            listner.onItemClicked(category);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        final  String imageUrlFull=CommonMethod.IMAGE_URL+imageUrl;
        if(imageUrl!=null) {
            Picasso.get()
                    .load(imageUrlFull)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(view, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                            Picasso.get()
                                    .load(imageUrlFull)
                                    .into(view);
                        }
                    });
        }
    }

//    public void onItemClicked(View view){
//        if(listner!=null)
//            listner.onItemClicked(this.model);
//    }

    public void onItemClicked(final MainCategory model){
        if(listner!=null)
            listner.onItemClicked(model);
    }



}
