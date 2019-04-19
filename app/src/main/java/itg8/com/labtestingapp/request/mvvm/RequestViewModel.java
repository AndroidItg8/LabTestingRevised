package itg8.com.labtestingapp.request.mvvm;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.R;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.GenericSpinnerAdapter;
import itg8.com.labtestingapp.common.LatLng;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.common.NetworkCall;
import itg8.com.labtestingapp.common.PlaceController;
import itg8.com.labtestingapp.common.Prefs;
import itg8.com.labtestingapp.common.SpinnerGenericModel;
import itg8.com.labtestingapp.common.SpinnerItemSelect;
import itg8.com.labtestingapp.common.genericRv.GenericAdapter;
import itg8.com.labtestingapp.db.repository.CityRepository;
import itg8.com.labtestingapp.db.tables.City;
import itg8.com.labtestingapp.db.tables.State;
import itg8.com.labtestingapp.db.tables.Test;
import itg8.com.labtestingapp.lab.LabFragment;
import itg8.com.labtestingapp.lab.model.LabModel;

import itg8.com.labtestingapp.lab.mvvm.LabItemViewModel;

import itg8.com.labtestingapp.request.model.RequestModel;
import itg8.com.labtestingapp.request.model.RequestServerModel;
import itg8.com.labtestingapp.splash.mvvm.StateCityController;
import itg8.com.labtestingapp.test.mvvm.TestItemViewModel;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestViewModel extends BaseObservable implements MainActivity.PermissionLocationCallbackListener {

    private static final String TAG = "RequestViewModel";

    private Context context;
    private RequestModel requestModel;
    public LabItemViewModel model;


    public ObservableList<SpinnerGenericModel> states;
    public static ObservableList<SpinnerGenericModel> cities;
    public ObservableBoolean progress;
    public ObservableBoolean isAllSet;
    public ObservableBoolean isWorkOrder;
    public ObservableBoolean button;
    public ObservableBoolean isBranch;
    private boolean hasPermission = false;
    private String googleKey;
    private Disposable d;
    private LatLng latLng;
    static Fragment fragment;
    public GenericAdapter<Test, TestItemViewModel> genericAdapters;
    public   ObservableArrayList<Test> tests;



    public SpinnerItemSelect.OnItemSelectListener cityListener = new SpinnerItemSelect.OnItemSelectListener() {
        @Override
        public void onItemSelect(String id) {
            requestModel.setState(Integer.parseInt(id));
            downloadCitites(id);
        }
    };


    public RequestViewModel(Application context, Fragment fragment) {
        this.context = fragment.getContext();
        this.fragment = fragment;
        tests = new ObservableArrayList<>();
        generateRvContent();

        progress = new ObservableBoolean(false);
        isAllSet = new ObservableBoolean(false);
        isWorkOrder = new ObservableBoolean(false);
        requestModel = new RequestModel();
        isBranch = new ObservableBoolean(false);
        states = new ObservableArrayList<>();
        cities = new ObservableArrayList<>();
        googleKey = context.getString(R.string.app_google_key);


        setAllStateToGenericModel();
        ((MainActivity) this.context).setPermissionCallbackForLocation(this);
    }

    private void setCardsItem() {
        tests.addAll(getCardsItem());
        genericAdapters.notifyDataSetChanged();
        isAllSet.set(true);
    }

    private List<Test> getCardsItem() {
        if (MyApplication.getInstance().getCartTest() != null)
            return MyApplication.getInstance().getCartTest();
        else
            return null;
    }


    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 6) {
            callWebServiceForLatLng(s.toString());
        }
    }

    private void callWebServiceForLatLng(String s) {
        progress.set(true);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.MINUTES);
        if (MyApplication.getInstance().isLoggingNeeded)
            builder.addInterceptor(interceptor);

        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(CommonMethod.GOOGLE_PLACE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        PlaceController controller = retrofit.create(PlaceController.class);
        String component = String.format("postal_code:%s", s);
        Observable<ResponseBody> observable = controller.getLatLng(component, false, googleKey);
        d = observable.flatMap(new Function<ResponseBody, Observable<LatLng>>() {
            @Override
            public Observable<LatLng> apply(ResponseBody responseBody) throws Exception {
                MapController mapController = new MapController(responseBody.string());
                return Observable.just(mapController.getLatLng());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<LatLng>() {
            @Override
            public void accept(LatLng latLng) throws Exception {
                getID(latLng);
            }
        }, this::setError);


    }


    private void setError(Throwable throwable) {
        throwable.printStackTrace();
        progress.set(false);
    }

    private void getID(LatLng latLng) {
        Log.d(TAG, "apply: " + latLng.lng + " " + latLng.lat);
        NearestSubAdminController controller1 = new NearestSubAdminController(latLng, MyApplication.getInstance(), fragment);
        controller1.getSubAdmin(latLng, MyApplication.getInstance().getAllSelectedTestListInt(), new OnIDAvailListener() {

            @Override
            public void onIDAvail(Integer integer) {
                selectedSubAdmin(integer);
            }
        });
    }

    private void selectedSubAdmin(Integer subAdminID) {
        ((MainActivity) context).setSelectedSubAdmin(subAdminID);
        progress.set(false);
    }


//    private void latlngAvail(LatLng o) {
//        (())
//    }

    private void setAllStateToGenericModel() {

        Observable<ResponseBody> responseBody = NetworkCall.getController().downloadStates();
        Disposable disposable = responseBody.flatMap(new Function<ResponseBody, Observable<List<State>>>() {
            @Override
            public Observable<List<State>> apply(ResponseBody responseBody) throws Exception {
                StateCityController controller = new StateCityController(responseBody.string());
                return Observable.just(controller.getStateOnline());
            }
        }).flatMap(new Function<List<State>, Observable<List<SpinnerGenericModel>>>() {
            @Override
            public Observable<List<SpinnerGenericModel>> apply(List<State> states) throws Exception {
                List<SpinnerGenericModel> models = new ArrayList<>();
                for (State state :
                        states) {
                    models.add(new SpinnerGenericModel(String.valueOf(state.getId()), state.getName()));
                }
                return Observable.just(models);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SpinnerGenericModel>>() {
                    @Override
                    public void accept(List<SpinnerGenericModel> spinnerGenericModels) throws Exception {
                        RequestViewModel.this.states.addAll(spinnerGenericModels);
                    }
                }, this::showError);
    }


    private void downloadCitites(String id) {

        Observable<ResponseBody> responseBody = NetworkCall.getController().downloadCities(id);
        Disposable disposable = responseBody.flatMap(new Function<ResponseBody, Observable<List<City>>>() {
            @Override
            public Observable<List<City>> apply(ResponseBody responseBody) throws Exception {
                StateCityController controller = new StateCityController(responseBody.string());
                return Observable.just(controller.getCityOnline());
            }
        }).flatMap(new Function<List<City>, Observable<List<SpinnerGenericModel>>>() {
            @Override
            public Observable<List<SpinnerGenericModel>> apply(List<City> states) throws Exception {
                List<SpinnerGenericModel> models = new ArrayList<>();
                for (City state :
                        states) {
                    models.add(new SpinnerGenericModel(String.valueOf(state.getId()), state.getName()));
                }
                return Observable.just(models);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SpinnerGenericModel>>() {
                    @Override
                    public void accept(List<SpinnerGenericModel> spinnerGenericModels) throws Exception {
                        cities.clear();
                        cities.addAll(spinnerGenericModels);
                    }
                }, this::showError);
    }


    private void showError(Throwable throwable) {
        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void onNextClicked(View view) {
        if (!isBranch.get()) {
            if (requestModel.validate()) {
                //  ((MainActivity) context).fileRequestFragment(requestModel);
                if (MyApplication.getInstance().getCartTest() != null && MyApplication.getInstance().getCartTest().size() > 0) {
                    RequestServerModel model = new RequestServerModel();

                    model.setCityId(requestModel.getCity());
                    model.setStateId(requestModel.getState());
                    model.setUserId(Integer.parseInt(Prefs.getString(CommonMethod.USERID)));
                    List<RequestServerModel.CardTest> cardTests = new ArrayList<>();
                    for (Test test : MyApplication.getInstance().getCartTest()) {
                        RequestServerModel.CardTest cardTest = new RequestServerModel.CardTest();
                        cardTest.setTestId(test.id);
                        cardTest.setQty(test.getItemCartSize());
                        cardTests.add(cardTest);
                    }
                    model.setTest(cardTests);
                    Log.d(TAG, "onNextClicked: " + new Gson().toJson(model));
                    sedRequestDataToServer(model);
                }
            }
        } else {
            Log.d(TAG, "onNextClicked: else part");

        }
    }

    private void sedRequestDataToServer(RequestServerModel model) {
        progress.set(true);
        Observable<List<LabModel>> observable = NetworkCall.getController().postRequestTOServer(model);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<LabModel>>() {
                    @Override
                    public void accept(List<LabModel> labModelList) throws Exception {
                        progress.set(false);
                        callFragment(labModelList);
                    }
                }, this::showError);


    }


    private void callFragment(List<LabModel> labModelList) {
        if (labModelList.size() == 1) {
            ((MainActivity) context).getViewModel().setLabModel(labModelList.get(0));
            Log.d(TAG, "callFragment: " + new Gson().toJson(((MainActivity) context).getViewModel().getModel().getValue()));
            setLabModel(((MainActivity) context).getViewModel().getModel().getValue());
        } else {
            ((MainActivity) this.context).startFragment(LabFragment.newInstance(labModelList));
        }

    }

    //TODO App get current location


    public RequestModel getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(RequestModel requestModel) {
        this.requestModel = requestModel;
    }


    @BindingAdapter({"error"})
    public static void setCustomError(TextInputLayout layout, String error) {
        layout.setError(error);
    }


    @BindingAdapter(value = {"customEntriesState", "customEntriesStateModel", "customListener"}, requireAll = false)
    public static void bindSpinnerAdapter(Spinner spinner, ObservableList<SpinnerGenericModel> allContriesObs, RequestModel model, SpinnerItemSelect.OnItemSelectListener listener) {
        spinner.setAdapter(new GenericSpinnerAdapter(spinner.getContext(), allContriesObs));
        SpinnerItemSelect itemDate = new SpinnerItemSelect(model, "CountryCode");

        spinner.setOnItemSelectedListener(itemDate);
        itemDate.setOnItemAvailListener(listener);

//        listener.onItemSelect(model.setState(Integer.parseInt(id)));
    }

    @BindingAdapter(value = {"customEntriesCity", "customEntriesCityModel"}, requireAll = false)
    public static void bindCitySpinnerAdapter(Spinner spinner, ObservableList<SpinnerGenericModel> allContriesObs, RequestModel model) {
        spinner.setAdapter(new GenericSpinnerAdapter(spinner.getContext(), allContriesObs));
        SpinnerItemSelect itemDate = new SpinnerItemSelect(model, "city");
        itemDate.setOnItemAvailListener(id -> model.setCity(Integer.parseInt(id)));
        spinner.setOnItemSelectedListener(itemDate);
    }

    private static void getAllCities(String id, Context context) {


        CityRepository repository = ViewModelProviders.of(fragment).get(CityRepository.class);
        repository.getStateWiseCity(Integer.parseInt(id)).observe(fragment, new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                List<SpinnerGenericModel> models = new ArrayList<>();
                if (cities != null) {
                    for (City city : cities) {
                        models.add(new SpinnerGenericModel(String.valueOf(city.getId()), city.getName())); }
                    RequestViewModel.cities.clear();
                    RequestViewModel.cities.addAll(models);
                }
            }
        });

    }

    @Override
    public void onPermissionGranted() {
        hasPermission = true;
    }

    @Override
    public void onPermissionDenied() {
        hasPermission = false;
    }


    public void setLabModel(LabModel labModel) {
        if (labModel != null) {
            isBranch.set(true);
            progress.set(false);
            setCardsItem();


        }

    }

    @BindingAdapter(value = {"customAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);


    }


    private void generateRvContent() {
        TestItemViewModel itemModel = new TestItemViewModel(true);
        itemModel.setListener(null);
        genericAdapters = new GenericAdapter<>(tests, itemModel);
    }

    public void onWorkOrder(View view) {
//    ((MainActivity)context).startFragment();
        Log.d(TAG, "onWorkOrder: ");
    }

}
