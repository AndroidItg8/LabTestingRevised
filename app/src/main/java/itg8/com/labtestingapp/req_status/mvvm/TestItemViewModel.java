package itg8.com.labtestingapp.req_status.mvvm;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.db.tables.Test;
import itg8.com.labtestingapp.test.mvvm.TestListViewModel;

public class TestItemViewModel extends BaseObservable implements itg8.com.labtestingapp.common.genericRv.ViewModel<Test> {
    public Test model;
    private TestListViewModel.OnTestItemListener listner;
    public ObservableField<String> buttonText;

    public static final String BTN_ADD="Add";
    public static final String BTN_REMOVE="Remove";

    public TestItemViewModel() {
        buttonText=new ObservableField<>("Add");
    }




    @Override
    public int layoutId() {
        return R.layout.item_test_layout_detail;
    }

    @Override
    public void setModel(Test test) {
        model=test;
    }

    public void setOnTestClickListener(Test test){
        if(listner!=null){
            listner.onItemClicked(test);
        }
    }



    public void setListener(TestListViewModel.OnTestItemListener listner) {
        this.listner = listner;
    }
}
