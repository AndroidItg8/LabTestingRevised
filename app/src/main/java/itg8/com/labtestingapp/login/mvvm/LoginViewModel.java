package itg8.com.labtestingapp.login.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.GenericSpinnerAdapter;
import itg8.com.labtestingapp.common.NetworkCall;
import itg8.com.labtestingapp.common.Prefs;
import itg8.com.labtestingapp.common.SpinnerGenericModel;
import itg8.com.labtestingapp.common.SpinnerItemSelect;
import itg8.com.labtestingapp.common.UtilSnackbar;
import itg8.com.labtestingapp.db.tables.City;
import itg8.com.labtestingapp.db.tables.State;
import itg8.com.labtestingapp.login.LoginActivity;
import itg8.com.labtestingapp.login.model.LoginModel;
import itg8.com.labtestingapp.login.model.RegistrationModel;
import itg8.com.labtestingapp.request.model.RequestModel;
import itg8.com.labtestingapp.request.mvvm.RequestViewModel;
import itg8.com.labtestingapp.splash.mvvm.StateCityController;
import okhttp3.ResponseBody;
import itg8.com.labtestingapp.BR;

public class LoginViewModel extends BaseObservable {
    public ObservableBoolean progressReg;
    public ObservableInt progressLogin;
    public ObservableInt registerVisibility;
    public ObservableInt loginVisibility;
    public ObservableInt isLoginView;
    public ObservableInt isRegView;
    private Context context;
    public LoginModel loginModel;
    public RegistrationModel regModel;
    private String mParam1;
    public ObservableList<SpinnerGenericModel> states;
    public static ObservableList<SpinnerGenericModel> cities;

    public SpinnerItemSelect.OnItemSelectListener cityListener = new SpinnerItemSelect.OnItemSelectListener() {
        @Override
        public void onItemSelect(String id) {
            regModel.setState(Integer.parseInt(id));
            downloadCitites(id);
        }
    };

    public LoginViewModel(Context context) {
        this.context = context;
        progressReg = new ObservableBoolean(false);
        progressLogin = new ObservableInt(View.GONE);
        registerVisibility = new ObservableInt(View.VISIBLE);
        loginVisibility = new ObservableInt(View.VISIBLE);
        isLoginView = new ObservableInt(View.VISIBLE);
        isRegView = new ObservableInt(View.GONE);
        loginModel = new LoginModel();
        states = new ObservableArrayList<>();
        cities = new ObservableArrayList<>();
        regModel = new RegistrationModel();
        setAllStateToGenericModel();
    }

    @Bindable
    public LoginModel getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
        notifyPropertyChanged(BR.loginModel);
    }

    @Bindable
    public RegistrationModel getRegModel() {
        return regModel;
    }


    public void setRegModel(RegistrationModel regModel) {
        this.regModel = regModel;
        notifyPropertyChanged(BR.regModel);
    }

    private void hideProgress(ObservableInt progress, ObservableInt view) {
        progress.set(View.GONE);
        view.set(View.VISIBLE);
    }

    private void showProgress(ObservableInt progress, ObservableInt view) {
        progress.set(View.VISIBLE);
        view.set(View.GONE);
    }

    public void onLoginViewClick(View view) {
        isLoginView.set(View.VISIBLE);
        isRegView.set(View.GONE);
    }

    public void onRegViewClick(View view) {
        isRegView.set(View.VISIBLE);
        isLoginView.set(View.GONE);
    }

    public void registerBtnClick(View view) {
        if (regModel.validate()) {
            progressReg.set(true);
            Observable<ResponseBody> observable = NetworkCall.getController().postRegister(regModel.getMobile(),
                    regModel.getFname(),
                    regModel.getLname(),
                    regModel.getEmail(),
                    regModel.getPassword(), regModel.getPassword(), TextUtils.isEmpty(regModel.getReferenceBy()) ? "" : regModel.getReferenceBy(), regModel.getState(), regModel.getCity(),2);
            Disposable d = observable.flatMap(new Function<ResponseBody, ObservableSource<?>>() {
                @Override
                public ObservableSource<?> apply(ResponseBody responseBody) throws Exception {
                    String res = responseBody.string();
                    progressReg.set(false);

                    if (res != null) {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.has("flag")) {
                            String flag = jsonObject.getString("flag");
                            if (flag != null && flag.equalsIgnoreCase("1")) {
                                return Observable.just(jsonObject.getString("userid"));
                            } else {
                                return Observable.just(jsonObject.getString("msg"));
                            }
                        }
                    }
                    return Observable.just("-1");
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if (o instanceof String) {
                                if (o.toString().equalsIgnoreCase("-1")) {
                                    errorLogin(view);
                                } else if (TextUtils.isDigitsOnly(o.toString())) {
                                    successLogin(o.toString());
                                } else {
                                    errorLogin(view, o.toString());
                                }
                            }
                        }
                    }, t->showError(t));
        }
    }

    public void onFbClick(View view) {
        ((LoginActivity) context).signInFacebook();
    }

    public void onGPlusClick(View view) {
        if (context instanceof LoginActivity)
            ((LoginActivity) context).signInGoogle();
        else
            ((MainActivity) context).signInGoogle();
    }

    public void onTwitterClick(View view) {

    }

    public void onLoginBtnClick(View view) {
        if (loginModel.validate()) {
            Observable<ResponseBody> observable = NetworkCall.getController().login(loginModel.getUsername(), loginModel.getPassword());
            Disposable d = observable.flatMap(new Function<ResponseBody, ObservableSource<?>>() {
                @Override
                public ObservableSource<?> apply(ResponseBody responseBody) throws Exception {
//                {"msg":"Login Success","flag":"1","userid":"19","groupid":"4"}
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    if (jsonObject.has("flag")) {
                        String flag = jsonObject.getString("flag");
                        if (flag != null && flag.equalsIgnoreCase("1")) {
                            return Observable.just(jsonObject.getString("userid"));
                        }
                    }
                    return Observable.just("-1");
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if (o instanceof String) {
                                if (o.toString().equalsIgnoreCase("-1")) {
                                    errorLogin(view);
                                } else {
                                    successLogin(o.toString());
                                }
                            }
                        }
                    }, throwable -> {
                        errorLogin(view, throwable.getMessage());
                    });
            //t-> {errorLogin(view);});
        }
    }

    private void errorLogin(View view) {
        UtilSnackbar.showSnakbarTypeWithMessage(view, "Invalid Credentials", new UtilSnackbar.OnSnackbarActionClickListener() {
            @Override
            public void onRetryClicked() {

            }
        });
    }

    private void errorLogin(View view, String msg) {
        UtilSnackbar.showSnakbarTypeWithMessage(view, msg, new UtilSnackbar.OnSnackbarActionClickListener() {
            @Override
            public void onRetryClicked() {

            }
        });
    }

    @BindingAdapter("error")
    public static void setError(TextInputEditText editText, String msg) {
        editText.setError(msg);
    }

    private void successLogin(String s) {
        Prefs.putString(CommonMethod.USERID, s);
        if (context instanceof MainActivity) {
            //TODO App Next Screen afer login
            if (TextUtils.isEmpty(mParam1))
                ((MainActivity) context).requestFragment();
            else if (mParam1.equalsIgnoreCase("1"))
                ((MainActivity) context).openRequestStatusFragment();
            else if (mParam1.equalsIgnoreCase("2"))
                ((MainActivity) context).carryOnRequest();
        } else if (context instanceof LoginActivity) {
            ((LoginActivity) context).setResultOk();
        }
    }


    public void setWhereFrom(String mParam1) {
        this.mParam1 = mParam1;
    }

    @BindingAdapter(value = {"customEntriesState", "customEntriesStateModel", "customListener"}, requireAll = false)
    public static void bindSpinnerAdapter(Spinner spinner, ObservableList<SpinnerGenericModel> allContriesObs, RegistrationModel model, SpinnerItemSelect.OnItemSelectListener listener) {
        spinner.setAdapter(new GenericSpinnerAdapter(spinner.getContext(), allContriesObs));
        SpinnerItemSelect itemDate = new SpinnerItemSelect(model, "CountryCode");
        spinner.setOnItemSelectedListener(itemDate);
        itemDate.setOnItemAvailListener(listener);

//        listener.onItemSelect(model.setState(Integer.parseInt(id)));
    }

    @BindingAdapter(value = {"customEntriesCity", "customEntriesCityModel"}, requireAll = false)
    public static void bindCitySpinnerAdapter(Spinner spinner, ObservableList<SpinnerGenericModel> allContriesObs, RegistrationModel model) {
        spinner.setAdapter(new GenericSpinnerAdapter(spinner.getContext(), allContriesObs));
        SpinnerItemSelect itemDate = new SpinnerItemSelect(model, "city");
        itemDate.setOnItemAvailListener(id -> model.setCity(Integer.parseInt(id)));
        spinner.setOnItemSelectedListener(itemDate);
    }

    private void setAllStateToGenericModel() {
        progressReg.set(true);
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
                        progressReg.set(false);

                        states.addAll(spinnerGenericModels);
                    }
                }, t-> showError(t));
    }


    private void downloadCitites(String id) {
        progressReg.set(true);
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
                progressReg.set(false);

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
                        progressReg.set(false);

                        cities.clear();
                        cities.addAll(spinnerGenericModels);
                    }
                }, this::showError);
    }


    private void showError(Throwable throwable) {
        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        progressReg.set(false);

    }

     public void onStateClick(View view){
         ((MainActivity)context).openDialogueFragment(states);

     }

     public void onCityClicked(View view){
         ((MainActivity)context).openDialogueFragment(cities);





     }
}