package itg8.com.labtestingapp.req_status.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.NetworkCall;
import itg8.com.labtestingapp.common.Prefs;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.req_status.model.RequestStatusModel;
import okhttp3.ResponseBody;

public class RequestStatusViewModel extends BaseObservable {

    private Context context;

    public ObservableArrayList<RequestStatusModel> models;
    public GenericAdapter<RequestStatusModel, ReqStatViewModel> genericAdapter;

    public ObservableInt isProgress;
    public ObservableInt isNoData;

    public RequestStatusViewModel(Context context) {
        this.context = context;
        models=new ObservableArrayList<>() ;
        isProgress=new ObservableInt(View.GONE);
        isNoData=new ObservableInt(View.GONE);
        generateRv();
        downloadAllRequestStatus();
    }

    private void generateRv() {
        ReqStatViewModel itemModel = new ReqStatViewModel(context);
        genericAdapter= new GenericAdapter<>(models, itemModel);
    }

    private void downloadAllRequestStatus() {
        isProgress.set(View.VISIBLE);
        Observable<List<RequestStatusModel>> observable=NetworkCall.getController().getAllRequests(Prefs.getString(CommonMethod.USERID));
        Disposable disposable=observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RequestStatusModel>>() {
                    @Override
                    public void accept(List<RequestStatusModel> requestStatusModels) throws Exception {
                        isProgress.set(View.GONE);
                        models.addAll(requestStatusModels);
                        genericAdapter.notifyDataSetChanged();
                        if(models.size()<=0)
                            isNoData.set(View.VISIBLE);
                        else
                            isNoData.set(View.GONE);
                    }
                }, this::showError);
    }

    private void showError(Throwable throwable) {
        throwable.printStackTrace();
        isProgress.set(View.GONE);
    }


    @BindingAdapter(value = {"customReqStatusAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }






}
