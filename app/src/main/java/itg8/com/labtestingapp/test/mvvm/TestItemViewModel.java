package itg8.com.labtestingapp.test.mvvm;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.db.tables.Test;

public class TestItemViewModel extends BaseObservable implements itg8.com.labtestingapp.common.genericRv.ViewModel<Test> {
    public Test model;
    private TestListViewModel.OnTestItemListener listner;
    public ObservableField<String> buttonText;
    public ObservableBoolean isFromCart;
    private static final String TAG = "TestItemViewModel";

    public static final String BTN_ADD = "Add";
    public static final String BTN_REMOVE = "Remove";

    public TestItemViewModel(boolean isCart) {
        buttonText = new ObservableField<>("Add");
        isFromCart=new ObservableBoolean(isCart);
    }

    private void checkIfModelAvailInCart(Test test) {
        if (MyApplication.getInstance().isTestInCart(test)) {
            test.setItemCartSize(MyApplication.getInstance().getTestCartSize(test));
        } else {
            test.setItemCartSize(0);
        }
    }

    public void onAddClick(Test test, Integer position) {
        if (listner != null)
            listner.onTestIncrease(test, position);
    }

    public void onRemoveClick(Test test, Integer position) {
        if (listner != null)
            listner.onTestDescrease(test, position);
    }

    @Override
    public int layoutId() {
        return R.layout.item_test_layout;
    }

    @Override
    public void setModel(Test test) {
        model = test;
        checkIfModelAvailInCart(test);
    }

    public void setOnTestClickListener(Test test) {
        if (listner != null) {
            listner.onItemClicked(test);
        }
    }


    public void setListener(TestListViewModel.OnTestItemListener listner) {
        this.listner = listner;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        final  String imageUrlFull= CommonMethod.IMAGE_URL+imageUrl;
        Log.d(TAG, "loadImage: "+imageUrlFull);
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



}
