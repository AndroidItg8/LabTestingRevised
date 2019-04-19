package itg8.com.labtestingapp.cart.mvvm;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.db.tables.Test;
import itg8.com.labtestingapp.test.TestDetailActivity;
import itg8.com.labtestingapp.test.mvvm.TestItemViewModel;
import itg8.com.labtestingapp.test.mvvm.TestListViewModel;

public class CartViewModel {

    ObservableArrayList<Test> tests;
    private Context context;
    public GenericAdapter<Test, TestItemViewModel> genericAdapter;

    public ObservableField<String> totalAmt;
    public ObservableField<String> totalProductPrice;

    public CartViewModel(Context context) {
        this.context = context;
        tests=new ObservableArrayList<>();
        MyApplication.getInstance().initTestForCheckout();
        totalAmt=new ObservableField<>(MyApplication.getInstance().getTotalWithoutGst());
        totalProductPrice=new ObservableField<>(MyApplication.getInstance().getTotal());
        tests.addAll(MyApplication.getInstance().getCartTest());
        generateRvContent();
    }

    private void setTotalPrice(){
        totalProductPrice.set(MyApplication.getInstance().getTotal());
        totalAmt.set(MyApplication.getInstance().getTotalWithoutGst());
    }

    public TestListViewModel.OnTestItemListener listener=new TestListViewModel.OnTestItemListener() {


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
            setTotalPrice();
        }



        @Override
        public void onTestDescrease(Test test, Integer position) {
//            MyApplication.getInstance().removeTestInCart(test);
//            tests.clear();
//            tests.addAll(MyApplication.getInstance().getCartTest());
//            genericAdapter.notifyItemChanged(position);
            MyApplication.getInstance().removeTestInCart(tests.get(position));
            tests.get(position).setItemCartSize(tests.get(position).getItemCartSize()-1);
            if(tests.get(position).getItemCartSize()==0){
                tests.remove((int)position);
                genericAdapter.notifyItemRemoved(position);
                genericAdapter.notifyItemRangeChanged(position,tests.size());
            }else {
                genericAdapter.notifyItemChanged(position);
            }
            ((MainActivity) context).invalidateOptionsMenu();
            setTotalPrice();
        }
    };

    private void openTestDetailActivity(Test test) {
        //TODO APP: testDetailActivity pass test id
        Intent intent = new Intent(context, TestDetailActivity.class);
//        intent.putExtra(CommonMethod.DESCRIPTION,test.getDescription());
//        intent.putExtra(CommonMethod.TITLE,test.getName());
        intent.putExtra(CommonMethod.DESCRIPTION,test);
        ((MainActivity)context).startActivity(intent);
    }


    private void generateRvContent() {
        TestItemViewModel itemModel = new TestItemViewModel(true);
        itemModel.setListener(listener);
        genericAdapter= new GenericAdapter<>(tests, itemModel);
    }
    @BindingAdapter(value = {"customTestAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    public void checkoutClick(View view){
        ((MainActivity)context).setLoginPage();
    }







}
