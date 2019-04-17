package itg8.com.labtestingapp.db.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.db.AppDatabase;
import itg8.com.labtestingapp.db.dbo.CountryDao;
import itg8.com.labtestingapp.db.tables.City;
import itg8.com.labtestingapp.db.tables.Country;

public class CountyRepository {
    private CountryDao countryDao;
    private LiveData<List<Country>> countries;
    private Disposable disposable;

    public CountyRepository(Context context) {
        AppDatabase database=AppDatabase.getDatabase(context);
        countryDao=database.getCountries();
        countries=countryDao.getCountries();
    }


    public LiveData<List<Country>> getCountries() {
        return countries;
    }

    public void  insertCountries(List<Country> countries){
       disposable = Observable.fromIterable(clearAndSave(countries))
                .flatMap((Function<Country, Observable<Integer>>) country -> Observable.just(storeCounty(country)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Integer->{},Throwable::printStackTrace,() -> {});
    }

    @NonNull
    private List<Country> clearAndSave(List<Country> mainCategories) {
//        countryDao.clear();
        return mainCategories;
    }

    private Integer storeCounty(Country country) {
        if(countryDao.getData(country.getId())!=null){
            countryDao.delete(country.getId());
        }
        return (int)countryDao.insertCountries(country)[0];
    }
}
