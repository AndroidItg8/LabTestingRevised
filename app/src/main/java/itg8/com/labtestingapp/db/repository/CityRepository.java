package itg8.com.labtestingapp.db.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.common.OnFinishInsertListener;
import itg8.com.labtestingapp.db.AppDatabase;
import itg8.com.labtestingapp.db.dbo.CityDao;
import itg8.com.labtestingapp.db.tables.City;

public class CityRepository extends AndroidViewModel {

    private static final String TAG = "CityRepository";
    private CityDao dao;
    private LiveData<List<City>> cities;

    public CityRepository(Application application) {
        super(application);
        AppDatabase database=AppDatabase.getDatabase(application);
        dao=database.getCities();
    }

    public LiveData<List<City>> getStateWiseCity(int stateID){
        cities=dao.getAllCity(stateID);
        return getCities();
    }

    public LiveData<List<City>> getCities() {
        return cities;
    }

    public void insert(List<City> cities,OnFinishInsertListener listener){
        Observable
                .fromIterable(clearAndSave(cities))
                .flatMap(city -> Observable.just(saveCity(city)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(id->{printID((Long) id);},Throwable::printStackTrace,() -> listener.onFinish());
    }

    private void printID(Long l){
        Log.d(TAG, "printID: "+l);
    }

    @NonNull
    private List<City> clearAndSave(List<City> mainCategories) {
        return mainCategories;
    }

    private Object saveCity(City city) {
        if(dao.getCity(city.getId())!=null){
            dao.delete(city.getId());
        }
            return dao.insertCity(city)[0];
    }

    public LiveData<City> getCityByID(String id) {
        return dao.getCity(Integer.parseInt(id));
    }
}
