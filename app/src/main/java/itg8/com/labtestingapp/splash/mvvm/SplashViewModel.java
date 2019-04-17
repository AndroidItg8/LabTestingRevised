package itg8.com.labtestingapp.splash.mvvm;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import org.json.JSONException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.MainActivity;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.common.NetworkCall;
import itg8.com.labtestingapp.common.OnFinishInsertListener;
import itg8.com.labtestingapp.common.Prefs;
import itg8.com.labtestingapp.db.repository.CityRepository;
import itg8.com.labtestingapp.db.repository.MainCategoryRepository;
import itg8.com.labtestingapp.db.repository.StateRepository;
import itg8.com.labtestingapp.db.repository.SubAdminRespository;
import itg8.com.labtestingapp.db.repository.SubCategoryRepository;
import itg8.com.labtestingapp.db.repository.TestRepository;
import itg8.com.labtestingapp.db.tables.SubAdmin;
import itg8.com.labtestingapp.db.tables.SubCategory;
import itg8.com.labtestingapp.splash.CategoryModel;
import itg8.com.labtestingapp.splash.SplashActivity;
import okhttp3.ResponseBody;

public class SplashViewModel extends ViewModel {

    public ObservableInt progress;
    public ObservableInt proceedButton;
    public ObservableInt progressVisibility;
    public ObservableBoolean isCityFinish;
    public ObservableBoolean isCategoryFinish;
    public ObservableBoolean isSubCategoryFinish;
    public ObservableBoolean isStateFinish;
    public ObservableBoolean isTestFinish;

    public static final String init="Initialising...";
    public static final String finish="Completed";


//    splashViewModel.isCityFinish && splashViewModel.isCategoryFinish && splashViewModel.isSubCategoryFinish && splashViewModel.isStateFinish && splashViewModel.isTestFinish

    private Context context;
    private Application application;

    StateCityController controller;

    CompositeDisposable disposable=new CompositeDisposable();

    public SplashViewModel(Context context, Application application) {
        this.context = context;
        this.application = application;
        progress=new ObservableInt();
        initAllObservable();

        downloadContent();
    }

    private void initAllObservable() {
        isCategoryFinish=new ObservableBoolean(false);
        isCityFinish=new ObservableBoolean(false);
        isStateFinish=new ObservableBoolean(false);
        isSubCategoryFinish=new ObservableBoolean(false);
        isTestFinish=new ObservableBoolean(false);
        proceedButton=new ObservableInt(View.GONE);
        progressVisibility=new ObservableInt(View.VISIBLE);
    }

    private void downloadContent() {
        controller=new StateCityController(context);
        downloadCities();
        donwloadState();
//        downloadCountry();
        downloadMenus();
        downloadSubAdmin();
    }

    private void downloadSubAdmin() {
        Observable<ResponseBody> observable= NetworkCall.getController().downloadSubAdmin();
        Disposable d=observable.flatMap(this::setSubAdmin).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::saveSubAdmin, this::failToDownload);

        disposable.add(d);
    }

    private void saveSubAdmin(List<SubAdmin> subAdmins) {
        SubAdminRespository respository=new SubAdminRespository(context);
        respository.saveSubAdmin(subAdmins);
    }

    private Observable<List<SubAdmin>> setSubAdmin(ResponseBody responseBody) throws IOException, JSONException {
        SubAdminController controller=new SubAdminController(responseBody.string());
        return Observable.just(controller.getSubAdmin());
    }

    private void downloadMenus() {
        Observable<ResponseBody> observable= NetworkCall.getController().downloadCategory();
        Disposable d=observable.flatMap(this::setCategory).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::saveCategories, this::failToDownload);

        disposable.add(d);
    }

    private void failToDownload(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void saveCategories(CategoryModel model) {
        MainCategoryRepository repository1=new MainCategoryRepository(MyApplication.getInstance());
        repository1.insert(model.getMainCategories(), new OnFinishInsertListener() {
            @Override
            public void onFinish() {
                isCategoryFinish.set(true);
            }
        });
        SubCategoryRepository repository2=new SubCategoryRepository(MyApplication.getInstance());
        repository2.insert(model.getSubCategories(), new OnFinishInsertListener() {
            @Override
            public void onFinish() {
                isSubCategoryFinish.set(true);
            }
        });
        TestRepository repository3=new TestRepository(MyApplication.getInstance());
        repository3.insert(model.getTests(), new OnFinishInsertListener() {
            @Override
            public void onFinish() {
                isTestFinish.set(true);
            }
        });

    }

    private Observable<CategoryModel> setCategory(ResponseBody responseBody) throws IOException {
        CategoryController controller=new CategoryController(responseBody.string());
        return Observable.just(controller.arrageCategories());
    }

    private void downloadCountry() {
        proceedButton.set(View.VISIBLE);
        progressVisibility.set(View.GONE);
    }

    public void onProceedClick(View view){
        Prefs.putBoolean(CommonMethod.ALL_SET_UP,true);
        ((SplashActivity)context).startActivity(new Intent(context,MainActivity.class));
        ((SplashActivity)context).finish();
    }

    private void donwloadState() {
        StateRepository repository=new StateRepository(MyApplication.getInstance());
        try {
            repository.saveStates(controller.getState(), new OnFinishInsertListener() {
                @Override
                public void onFinish() {
                    isStateFinish.set(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private void downloadCities() {
        CityRepository repository=new CityRepository(MyApplication.getInstance());
        try {
            repository.insert(controller.getCities(), new OnFinishInsertListener() {
                @Override
                public void onFinish() {
                    isCityFinish.set(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }


}
