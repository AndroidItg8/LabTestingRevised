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
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
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
import itg8.com.labtestingapp.common.SpinnerGenericModel;
import itg8.com.labtestingapp.common.SpinnerItemSelect;
import itg8.com.labtestingapp.db.repository.CityRepository;
import itg8.com.labtestingapp.db.repository.StateRepository;
import itg8.com.labtestingapp.db.tables.City;
import itg8.com.labtestingapp.db.tables.State;
import itg8.com.labtestingapp.db.tables.SubAdmin;
import itg8.com.labtestingapp.request.model.RequestModel;
import itg8.com.labtestingapp.splash.mvvm.StateCityController;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestViewModel extends BaseObservable implements MainActivity.PermissionLocationCallbackListener {

    private static final String TAG = "RequestViewModel";

    private Context context;
    private RequestModel requestModel;
    public ObservableList<SpinnerGenericModel> states;
    public ObservableBoolean progress;
    public static ObservableList<SpinnerGenericModel> cities;
    private boolean hasPermission = false;
    private String googleKey;
    private Disposable d;

    private LatLng latLng;
    static Fragment fragment;
    Application application;


    public SpinnerItemSelect.OnItemSelectListener cityListener=new SpinnerItemSelect.OnItemSelectListener() {
        @Override
        public void onItemSelect(String id) {
            downloadCitites(id);
        }
    };



    public RequestViewModel(Application context, Fragment fragment) {
        this.application = context;
        this.context = fragment.getContext();
        this.fragment = fragment;
        progress=new ObservableBoolean(false);
        requestModel = new RequestModel();
        states = new ObservableArrayList<>();
        cities = new ObservableArrayList<>();
        googleKey = context.getString(R.string.app_google_key);
        setAllStateToGenericModel();

        ((MainActivity) this.context).setPermissionCallbackForLocation(this);
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
        if(MyApplication.getInstance().isLoggingNeeded)
            builder.addInterceptor(interceptor);

        OkHttpClient client=builder.build();
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
        Log.d(TAG, "apply: "+latLng.lng+" "+latLng.lat);
        NearestSubAdminController controller1 = new NearestSubAdminController(latLng, MyApplication.getInstance(),fragment);
        controller1.getSubAdmin(latLng, MyApplication.getInstance().getAllSelectedTestListInt(),new OnIDAvailListener() {

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

        Observable<ResponseBody> responseBody= NetworkCall.getController().downloadStates();
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

        Observable<ResponseBody> responseBody= NetworkCall.getController().downloadCities(id);
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

    }

    public void onNextClicked(View view) {
        if (requestModel.validate()) {
            ((MainActivity) context).fileRequestFragment(requestModel);
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
    public static void setCustomError(TextInputLayout layout,String error){
        layout.setError(error);
    }

    @BindingAdapter(value = {"customEntriesState", "customEntriesStateModel","customListener"}, requireAll = false)
    public static void bindSpinnerAdapter(Spinner spinner, ObservableList<SpinnerGenericModel> allContriesObs, RequestModel model, SpinnerItemSelect.OnItemSelectListener listener) {
        spinner.setAdapter(new GenericSpinnerAdapter(spinner.getContext(), allContriesObs));
        SpinnerItemSelect itemDate = new SpinnerItemSelect(model, "CountryCode");
        itemDate.setOnItemAvailListener(listener);
        spinner.setOnItemSelectedListener(itemDate);
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
                if(cities!=null) {
                    for (City city :
                            cities) {
                        models.add(new SpinnerGenericModel(String.valueOf(city.getId()), city.getName()));
                    }

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
}
