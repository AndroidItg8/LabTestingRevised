package itg8.com.labtestingapp.test.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.db.tables.Test;

public class TestDetailViewModel extends BaseObservable {

    private Context context;
    public Test test;

    public TestDetailViewModel(Context context) {
        this.context = context;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void onTestIncrease() {
        if (test.getItemCartSize() < 30) {
            test.setItemCartSize(test.getItemCartSize() + 1);
            MyApplication.getInstance().addTestInCart(test);
        }
    }

    public void onTestDescrease() {
        if (test.getItemCartSize() > 0) {
            MyApplication.getInstance().removeTestInCart(test);
            test.setItemCartSize(test.getItemCartSize() - 1);
        }
    }
}
