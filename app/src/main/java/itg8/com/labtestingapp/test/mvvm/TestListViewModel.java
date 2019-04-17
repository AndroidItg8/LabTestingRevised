package itg8.com.labtestingapp.test.mvvm;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.common.genericRv.BaseClickListner;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.db.repository.TestRepository;
import itg8.com.labtestingapp.db.tables.Test;
import itg8.com.labtestingapp.test.TestDetailActivity;

public class TestListViewModel extends BaseObservable {

    private Context context;
    public ObservableArrayList<Test> tests;
    TestRepository repository;

    public OnTestItemListener listener=new OnTestItemListener() {


        @Override
        public void onItemClicked(Object o) {
            openTestDetailActivity((Test)o);
        }

        @Override
        public void onTestIncrease(Test test, Integer position) {
            if(tests.get(position).getItemCartSize()<30) {
                tests.get(position).setItemCartSize(tests.get(position).getItemCartSize()+1);
                MyApplication.getInstance().addTestInCart(tests.get(position));
                genericAdapter.notifyItemChanged(position);
                ((MainActivity) context).invalidateOptionsMenu();
            }
        }

        @Override
        public void onTestDescrease(Test test, Integer position) {
//            if (tests.get(position).getItemCartSize() > 0) {
                MyApplication.getInstance().removeTestInCart(tests.get(position));
                tests.get(position).setItemCartSize(tests.get(position).getItemCartSize()-1);
               genericAdapter.notifyItemChanged(position);

            ((MainActivity) context).invalidateOptionsMenu();
//            }
        }
    };

    public GenericAdapter<Test, TestItemViewModel> genericAdapter;

    private void openTestDetailActivity(Test test) {
        //TODO APP: testDetailActivity pass test id
        Intent intent = new Intent(context, TestDetailActivity.class);
        intent.putExtra(CommonMethod.DESCRIPTION,test);
        ((MainActivity)context).startActivity(intent);
    }

    public TestListViewModel(Application context, Fragment fragment, int id) {
        this.context = fragment.getContext();
        tests=new ObservableArrayList<>();
        generateRvContent();
        repository=ViewModelProviders.of(fragment).get(TestRepository.class);
        repository.getTest(id).observe(fragment, new Observer<List<Test>>() {
            @Override
            public void onChanged(@Nullable List<Test> testList) {
                tests.addAll(testList);
                genericAdapter.notifyDataSetChanged();
            }
        });
    }


    private void generateRvContent() {
        TestItemViewModel itemModel = new TestItemViewModel(false);
        itemModel.setListener(listener);
        genericAdapter= new GenericAdapter<>(tests, itemModel);
    }


    @BindingAdapter(value = {"customTestAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    public interface OnTestItemListener extends BaseClickListner {
        void onTestIncrease(Test test, Integer position);
        void onTestDescrease(Test test, Integer position);
    }


}
