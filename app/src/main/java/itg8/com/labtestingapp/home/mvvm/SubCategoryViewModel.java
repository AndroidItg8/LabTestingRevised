package itg8.com.labtestingapp.home.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.common.genericRv.ViewModel;
import itg8.com.labtestingapp.db.tables.SubCategory;

public class SubCategoryViewModel extends BaseObservable implements ViewModel<SubCategory> {

    private final Context context;
    private GenericAdapter.OnItemClickListner listner;
    public SubCategory model;

    public SubCategoryViewModel(Context context) {
        this.context = context;
    }

    public void setListner(GenericAdapter.OnItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public int layoutId() {
        return R.layout.item_sub_category_menus;
    }

    @Override
    public void setModel(SubCategory subCategory) {
        this.model=subCategory;
    }


    public void setOnItemClick(int position,SubCategory subCategory){
        if(listner!=null){
            listner.onItemClicked(subCategory);
        }
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

    public void onItemClicked(View view){
        if(listner!=null)
            listner.onItemClicked(model);
    }


}
