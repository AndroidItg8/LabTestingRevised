package itg8.com.labtestingapp.req_status.mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import id.zelory.compressor.Compressor;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.db.repository.CityRepository;
import itg8.com.labtestingapp.db.tables.City;
import itg8.com.labtestingapp.db.tables.Test;
import itg8.com.labtestingapp.req_status.model.RequestStatusModel;
import itg8.com.labtestingapp.req_status.model.Testmaster;

public class ReqStatusDetailViewModel extends BaseObservable {

    private static final String TAG = "ReqStatusDetailViewMode";

    private Context context;
    private Fragment fragment;
    public RequestStatusModel model;
    public ObservableField<String> cityname;
    public ObservableField<String> status;
    public ObservableField<String> imgPath;
    public GenericAdapter<Test, TestItemViewModel> genericAdapter;
    private ObservableArrayList<Test> tests;


    public ReqStatusDetailViewModel(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        tests=new ObservableArrayList<>();
        cityname = new ObservableField<>();
        status = new ObservableField<>();
        imgPath = new ObservableField<>();
        generateRvContent();
    }

    public void setModel(RequestStatusModel model) {
        this.model = model;
        initRelatedData();
    }

    private void initRelatedData() {
        initStatus();
        setCityName();
        initImgFile();
        initTests();
    }

    private void initTests() {
        if (model.getTests() != null)
            for (Testmaster t :
                    model.getTests()) {
                if (t != null) {
                    Test tempTest = new Test();
                    tempTest.setId(Integer.parseInt(t.getId()));
                    tempTest.setCreated(t.getCreated());
                    tempTest.setProductprice(Float.parseFloat(t.getProductprice()));
                    tempTest.setDescription(t.getDescription());
                    tempTest.setName(t.getName());
                    tempTest.setCategoryid(Integer.parseInt(t.getCategorymasterId()));
                    tests.add(tempTest);
                }
            }
        genericAdapter.notifyDataSetChanged();
    }

    private void initImgFile() {
        if (model.getRequestuploadmaster() != null && model.getRequestuploadmaster().size() > 0) {
            Log.i(TAG, "initImgFile: "+model.getRequestuploadmaster().get(0).getFilename());
            imgPath.set(CommonMethod.IMAGE_URL_REQ + model.getRequestuploadmaster().get(0).getFilename());
        }
    }

    private void setCityName() {
        CityRepository repository = ViewModelProviders.of(fragment).get(CityRepository.class);
        repository.getCityByID(model.getRequestmaster().getCitymasterId()).observe(fragment, new Observer<City>() {
            @Override
            public void onChanged(@Nullable City city) {
                cityname.set(city.getName());
            }
        });
    }

    private void initStatus() {
        if (model.getRequestmaster().getApprovedbyadmin().equalsIgnoreCase("1")) {
            if (model.getRequestmaster().getAcceptbysubadmin().equalsIgnoreCase("1")) {
                status.set("Processing");
            } else {
                status.set("Pending for Processing");
            }
        } else {
            status.set("Pending");
        }
    }

    @BindingAdapter({"img_path_detail"})
    public static void setImageFile(ImageView imageView, ObservableField<String> imgPath) {
        if (imgPath != null && !TextUtils.isEmpty(imgPath.get())) {
            Picasso.get().load(imgPath.get()).fit().into(imageView);
        }
    }



    private void generateRvContent() {
        TestItemViewModel itemModel = new TestItemViewModel();
        genericAdapter = new GenericAdapter<>(tests, itemModel);
    }

    @BindingAdapter(value = {"customTestAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }


}
