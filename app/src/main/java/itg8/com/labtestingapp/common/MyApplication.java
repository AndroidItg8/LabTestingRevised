package itg8.com.labtestingapp.common;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.db.repository.SubAdminTestRepository;
import itg8.com.labtestingapp.db.tables.SubAdminTest;
import itg8.com.labtestingapp.db.tables.Test;
import okhttp3.internal.platform.Platform;

/**
 * Created by swapnilmeshram on 15/03/18.
 */
@ReportsCrashes(formUri = "", mailTo = "steponesolutions.01@gmail.com", mode = ReportingInteractionMode.SILENT)
public class MyApplication extends Application {



    private static final String PREF_NAME="PARENT_APP";
    private static final String DB_NAME = "ParentApp";
    private static final int DEFAULT_REFRESH_MINUTES=8;
    private static final String PREF_TEST_CART = "TestCartPref";
    private int refreshMinutes=0;
    private static MyApplication mInstance;
    Set<Integer> notificationIDS=new HashSet<>();
    public boolean isLoggingNeeded;
    //TODO version: change when new api changes arrives
    public static final int VERSION=1;

    HashMap<Integer,Test> tests=new HashMap<>();

    List<String> imagesNotFound=new ArrayList<>();
    private List<String> filteredLocations;
    private List<Test> allSelectedTest;

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Stetho.initializeWithDefaults(this);
//        DebugOverlay.with(this).install();

        isLoggingNeeded=true;

        mInstance=this;
        mInstance.initPref();
        mInstance.initNetworkCall();
    }

    private void initNetworkCall() {
        new NetworkCall.NetworkBuilder().build();
    }


    private void initPicassoCache() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }



    public String getMyString(int resId){
        return getString(resId);
    }

    private void initPref() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(MODE_PRIVATE)
                .setPrefsName(PREF_NAME)
                .setUseDefaultSharedPreference(false)
                .build();
    }


    public void saveSubAdminTests(List<SubAdminTest> subAdminTests) {
        SubAdminTestRepository repository=new SubAdminTestRepository(this);
        repository.saveStates(subAdminTests);
    }

    public void initTestForCheckout() {
        //TODO APP get all selected test from cart;
         allSelectedTest=new ArrayList<>();
        initTestCart();
        for (Map.Entry t :
                tests.entrySet()) {
            allSelectedTest.add((Test) t.getValue());
        }
    }



    public List<Integer> getAllSelectedTestListInt() {
        //TODO APP get all selected test from cart;
       List<Integer> ids=new ArrayList<>();
        for (Map.Entry<Integer,Test> t:
             tests.entrySet()) {
            ids.add(t.getKey());
        }

        return ids;
    }

    public Map<String,Integer> getAllSelectedTestList() {
        //TODO APP get all selected test from cart;
        Map<String,Integer> tests=new HashMap<>();
        for(int i=0; i<allSelectedTest.size();i++){
            tests.put("RequestmastersTestmaster["+i+"][testmaster_id]",allSelectedTest.get(i).getId());
        }

        return tests;
    }


    public Map<String,Integer> getAllSelectedTestQuantityList() {
        //TODO APP get all selected test from cart;
        Map<String,Integer> tests=new HashMap<>();
        for(int i=0; i<allSelectedTest.size();i++){
            tests.put("RequestmastersTestmaster["+i+"][qty]",allSelectedTest.get(i).getItemCartSize());
        }
        return tests;
    }
    public Map<String,Float> getAllSelectedTestTotalList() {
        //TODO APP get all selected test from cart;
        Map<String,Float> tests=new HashMap<>();
        for(int i=0; i<allSelectedTest.size();i++){
            tests.put("RequestmastersTestmaster["+i+"][testtotalamount]",allSelectedTest.get(i).getItemCartSize()*allSelectedTest.get(i).getProductprice());
        }
        return tests;
    }





    public boolean isTestInCart(Test model) {
        initTestCart();
        return tests.containsKey(model.getId());
    }

    public void  addTestInCart(Test test){
        initTestCart();
        tests.put(test.getId(),test);
        Prefs.putString(PREF_TEST_CART,new Gson().toJson(tests));
    }

    public void removeTestInCart(Test test){
        initTestCart();
        Test test1=tests.get(test.getId());
//        for (Map.Entry<Integer,Test> t :
//                tests.entrySet()) {
//            if (t.getKey()==test.getId()){
//                test1=t.getValue();
//                break;
//            }
//        }
//        if(test1!=null){
//            tests.remove(test1.getId());
//        }

        if(test1!=null){
            if(test.getItemCartSize()>1){
                test1.setItemCartSize(test1.getItemCartSize()-1);
            }else {
                tests.remove(test1.getId());
            }
        }

        Prefs.putString(PREF_TEST_CART,new Gson().toJson(tests));
//        initTestCart();
    }

    private void initTestCart() {
        if(Prefs.contains(PREF_TEST_CART)) {
            tests.clear();
            tests.putAll(new Gson().fromJson(Prefs.getString(PREF_TEST_CART), new TypeToken<HashMap<Integer,Test>>() {
            }.getType()));
        }
    }

    public void clearCart(){
        Prefs.remove(PREF_TEST_CART);
        tests.clear();
    }

    public List<Test> getCartTest(){
        initTestCart();
        List<Test> testArray=new ArrayList<>();
        for(Map.Entry<Integer,Test> t: tests.entrySet()){
            testArray.add(t.getValue());
        }
        return testArray;
    }

    public String getTotal() {
        float amt=0;
        initTestCart();
        for (Map.Entry<Integer,Test> t :
                tests.entrySet()) {
            amt+=t.getValue().getProductprice()*t.getValue().getItemCartSize();
        }

        amt=amt+CommonMethod.addGst(amt,18);

        return String.valueOf(amt);
    }

    public int getTestCartSize(Test test) {
        initTestCart();
        if(tests.containsKey(test.getId())){
            if(tests.get(test.getId())!=null)
                return tests.get(test.getId()).getItemCartSize();
        }
        return 0;
    }

    public String getTotalWithoutGst() {
        float amt=0;
        initTestCart();
        for (Map.Entry<Integer,Test> t :
                tests.entrySet()) {
            amt+=t.getValue().getProductprice()*t.getValue().getItemCartSize();
        }
        return String.valueOf(amt);
    }
}
