package itg8.com.labtestingapp.req_status.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;

import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.genericRv.ViewModel;
import itg8.com.labtestingapp.req_status.model.RequestStatusModel;

public class ReqStatViewModel extends BaseObservable implements ViewModel {

    private Context context;

    public ReqStatViewModel(Context context) {
        this.context = context;
    }

    @Override
    public int layoutId() {
        return R.layout.item_request_status;
    }

    public void showDetail(int position, RequestStatusModel model){
        ((MainActivity)context).showRequestDetail(model);
    }

    @Override
    public void setModel(Object o) {

    }
}
