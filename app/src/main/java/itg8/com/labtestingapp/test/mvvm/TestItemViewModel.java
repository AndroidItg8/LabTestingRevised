package itg8.com.labtestingapp.test.mvvm;

import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.db.tables.Test;

public class TestItemViewModel extends BaseObservable implements itg8.com.labtestingapp.common.genericRv.ViewModel<Test> {
    public Test model;
    private TestListViewModel.OnTestItemListener listner;
    public ObservableField<String> buttonText;
    public ObservableBoolean isFromCart;

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
}