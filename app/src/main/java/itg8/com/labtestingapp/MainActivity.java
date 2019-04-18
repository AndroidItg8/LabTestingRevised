package itg8.com.labtestingapp;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.cart.CartFragment;
import itg8.com.labtestingapp.common.BaseActivity;
import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.GlobalViewModel;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.common.NetworkCall;
import itg8.com.labtestingapp.common.Prefs;
import itg8.com.labtestingapp.db.repository.SubCategoryRepository;
import itg8.com.labtestingapp.db.tables.MainCategory;
import itg8.com.labtestingapp.db.tables.SubCategory;
import itg8.com.labtestingapp.db.tables.Test;
import itg8.com.labtestingapp.geotechnical.GeoTechnicalFragment;
import itg8.com.labtestingapp.home.HomeFragment;
import itg8.com.labtestingapp.lab.LabFragment;
import itg8.com.labtestingapp.lab.LabSelectionListener;
import itg8.com.labtestingapp.lab.model.LabModel;
import itg8.com.labtestingapp.login.LoginActivity;
import itg8.com.labtestingapp.login.LoginFragment;
import itg8.com.labtestingapp.ndt.NDTFragment;
import itg8.com.labtestingapp.rating.RatingFragment;
import itg8.com.labtestingapp.req_status.RequestStatusDetailFragment;
import itg8.com.labtestingapp.req_status.RequestStatusFragment;
import itg8.com.labtestingapp.req_status.model.RequestStatusModel;
import itg8.com.labtestingapp.req_status.mvvm.ReqStatusDetailViewModel;
import itg8.com.labtestingapp.request.FileUploadFragment;
import itg8.com.labtestingapp.request.RequestFragment;
import itg8.com.labtestingapp.request.model.RequestModel;
import itg8.com.labtestingapp.splash.SplashActivity;
import itg8.com.labtestingapp.subcategory.SubCategoryFragment;
import itg8.com.labtestingapp.survey.SurveyFragment;
import itg8.com.labtestingapp.test.TestListFragment;
import itg8.com.labtestingapp.type.TypeFragment;
import itg8.com.labtestingapp.widget.BadgeDrawable;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private static final int RC_EXTERNAL_STORAGE = 1022;
    private static final int RC_LOCATION = 1023;
    private static final int RC_LOGIN = 1222;
    private GoogleSignInClient mGoogleSignInClient;
    private RequestModel requestModel;
    private String uploadedFile;
    private boolean isPick;
    private RequestFinalListener requestFinalListener;
    private Integer subAdminID;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private String fragment;
    private LayerDrawable icon;
    private SubCategoryRepository repository;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private boolean hasRequest = false;
    private int categoryMasterId;
    private ArrayList<Integer> questionList;
    private ArrayList<String> ans;
    private PermissionLocationCallbackListener locationListener;
    private PermissionStorageCallbackListener storageListener;
    private LabSelectionListener labSelectedListner;
    private GlobalViewModel viewModel;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!Prefs.getBoolean(CommonMethod.ALL_SET_UP, false)) {
            startActivity(new Intent(this, SplashActivity.class));
            this.finish();
            return;
        }

        initNavigation();

        repository = ViewModelProviders.of(this).get(SubCategoryRepository.class);

        fm = getSupportFragmentManager();

        createGlobalViewModel();
        initMenus();
    }

    private void createGlobalViewModel() {
        viewModel = ViewModelProviders.of(this)
                .get(GlobalViewModel.class);
    }

    @Override
    public LoginButton getLoginButton() {
        return null;
    }

    @Override
    public void canfinish() {
        invalidMenu();
    }

    private void initNavigation() {
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        openFragmentAsPerMenuItem(menuItem);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

       actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }

        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();

    }

    private void openFragmentAsPerMenuItem(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_req_status) {
            openRequestStatusFragment();
        } else if (menuItem.getItemId() == R.id.nav_home) {
            backToStackHome();
        } else if (menuItem.getItemId() == R.id.nav_surway) {
            openRatingFragment();
        } else if (menuItem.getItemId() == R.id.nav_update) {
            updateApp();
        }
    }


    private void updateApp() {
        Prefs.remove(CommonMethod.ALL_SET_UP);
        startActivity(new Intent(this, SplashActivity.class));
        this.finish();
    }

    private void openRatingFragment() {
        startFragment(RatingFragment.newInstance("", ""));
    }

    public void openRequestStatusFragment() {
        if (Prefs.contains(CommonMethod.USERID)) {
            startFragment(RequestStatusFragment.newInstance());
        } else {
//            startFragment(LoginFragment.newInstance("1", ""));
            startLoginActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidMenu();
    }

    private void initMenus() {
        fragment = HomeFragment.class.getSimpleName();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, HomeFragment.newInstance("", "")).commitAllowingStateLoss();
    }

    public void startFragment(MainCategory o) {
        repository.getSubCategory(o.getId()).observe(this, new android.arch.lifecycle.Observer<List<SubCategory>>() {
            @Override
            public void onChanged(@Nullable List<SubCategory> list) {
                if (list != null && list.size() > 0) {
                    startFragment(SubCategoryFragment.newInstance(o.getId()));
                } else {
                    if (o.getId() == 11) {
                        startFragment(NDTFragment.newInstance("", ""));
                    } else if (o.getId() == 10) {
                        startFragment(GeoTechnicalFragment.newInstance("", ""));
                    } else if (o.getId() == 12) {
                        startFragment(SurveyFragment.newInstance("", ""));
                    }
                }
            }
        });

    }

    public void startFragment(Fragment fragment) {
        this.fragment = fragment.getClass().getSimpleName();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment, this.fragment)
                .addToBackStack(this.fragment)
                .commitAllowingStateLoss();
    }


    public void requestFragment() {
        invalidateOptionsMenu();
        startFragment(RequestFragment.newInstance("", ""));
    }

    public void fileRequestFragment(RequestModel requestModel) {
        this.requestModel = requestModel;
        hideKeyboard();
        startFragment(FileUploadFragment.newInstance("", ""));
    }


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void setPermissionCallback(PermissionStorageCallbackListener listener) {
        this.storageListener = listener;
        checkPermission();
    }

    public void setPermissionCallbackForLocation(PermissionLocationCallbackListener listener) {
        this.locationListener = listener;
        checkPermissionLocation();
    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    private void checkPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            storageListener.onPermissionGranted();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rc_grant_ext_storage),
                    RC_EXTERNAL_STORAGE, perms);
        }
    }

    @AfterPermissionGranted(RC_LOCATION)
    private void checkPermissionLocation() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            locationListener.onPermissionGranted();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rc_grant_ext_storage),
                    RC_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        if (list.contains(Manifest.permission.ACCESS_COARSE_LOCATION) || list.contains(Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (locationListener != null)
                locationListener.onPermissionGranted();
        } else if (list.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (storageListener != null) {
                storageListener.onPermissionGranted();
            }
        }
//        if (listener != null)
//            listener.onPermissionGranted();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        if (list.contains(Manifest.permission.ACCESS_COARSE_LOCATION) || list.contains(Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (locationListener != null)
                locationListener.onPermissionDenied();
        } else if (list.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (storageListener != null) {
                storageListener.onPermissionGranted();
            }
        }

    }

    public void typeFragment(String uploadedFile) {
        this.uploadedFile = uploadedFile;
        //TODO APP Call Type Fragment
        startFragment(TypeFragment.newInstance("", ""));

    }

    public void paymentFragment(boolean isPick, RequestFinalListener requestFinalListener) {
        this.isPick = isPick;
        //TODO APP Payment fragment
        this.requestFinalListener = requestFinalListener;
        saveAll();
    }

    private void saveAll() {


        MyApplication.getInstance().initTestForCheckout();

        Observable<ResponseBody> observable = NetworkCall.getController().saveRequest(Prefs.getString(CommonMethod.USERID), String.valueOf(requestModel.getCity()),
                MyApplication.getInstance().getAllSelectedTestList(),
                MyApplication.getInstance().getAllSelectedTestQuantityList(),
                MyApplication.getInstance().getAllSelectedTestTotalList(),
                requestModel.getAddress(),
                requestModel.getPincode(),
                isPick ? 1 : 2,
                subAdminID,
                uploadedFile,
                MyApplication.getInstance().getTotal() + (isPick ? 50 : 0),
                isPick ? 50 : 0);

        Disposable d = observable.flatMap(new Function<ResponseBody, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(ResponseBody responseBody) throws Exception {
                String res = responseBody.string();
                Integer status = 0;
                if (res != null) {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.has("flag")) {
                        status = jsonObject.getInt("flag");
                    }
                }
                return Observable.just(status);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (o instanceof Integer) {
                            int i = (int) o;
                            if (i == 1) {
                                MyApplication.getInstance().clearCart();
                                invalidateOptionsMenu();
                                requestFinalListener.onFinalFinished();
                            } else
                                requestFinalListener.onFinalSaveFailed("Fail to send your request..");
                        }
                    }
                }, throwable -> requestFinalListener.onFinalSaveFailed(throwable.getMessage()));

    }

    public void setSelectedSubAdmin(Integer subAdminID) {
        this.subAdminID = subAdminID;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);

        //If you want to add your ActionItem programmatically you can do this too. You do the following:
//        new ActionItemBadgeAdder().act(this).menu(menu).title(R.string.sample_2).itemDetails(0, SAMPLE2_ID, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(bigStyle, 1);
        MenuItem itemCart = menu.findItem(R.id.action_cart);
        icon = (LayerDrawable) itemCart.getIcon();
        MenuItem itemLogout = menu.findItem(R.id.action_logout);

        if (Prefs.contains(CommonMethod.USERID))
            itemLogout.setVisible(true);
        else
            itemLogout.setVisible(false);

        getNoticationCount();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            startCartFragment();
//            testDrawer();
        } else if (item.getItemId() == R.id.action_logout) {
            removeLogout();
        }

//        if (actionBarDrawerToggle.isDrawerIndicatorEnabled()
//                && actionBarDrawerToggle.onOptionsItemSelected(item))
//            return true;
        switch (item.getItemId()) {
            case android.R.id.home:
              onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void testDrawer() {
//        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
//            mDrawerLayout.closeDrawer(Gravity.RIGHT);
//        }
//        else {
//            mDrawerLayout.openDrawer(Gravity.RIGHT);
//        }
//    }

    private void removeLogout() {
        Prefs.remove(CommonMethod.USERID);
        signOut();
        invalidateOptionsMenu();
    }

    private void startCartFragment() {
        startFragment(CartFragment.newInstance("", ""));
    }

    public void setBadgeCount(int count) {
        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(this);
        }

//        if (count > 0) {
//        Prefs.putInt(CommonMethod.NOTIFICATION_COUNT, count);
        badge.setCount(String.valueOf(count));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
//        }else {
//
//        }
    }


    private void getNoticationCount() {
        List<Test> tests = MyApplication.getInstance().getCartTest();
        if (tests.size() > 0) {
            setBadgeCount(tests.size());
        }
    }


    public void startTestFragment(SubCategory o) {
        startFragment(TestListFragment.newInstance(o.getId(), ""));
    }

    public void invalidMenu() {
        invalidateOptionsMenu();
    }

    public void setLoginPage() {
        if (!Prefs.contains(CommonMethod.USERID)) {
//            startFragment(LoginFragment.newInstance("", ""));
            startLoginActivity();
        } else {
            requestFragment();
        }
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void showRequestDetail(RequestStatusModel model) {
        startFragment(RequestStatusDetailFragment.newInstance(model, ""));
    }

    public void backToStackHome() {
        getSupportFragmentManager().popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode==RC_LOGIN){
//            if(resultCode==RESULT_OK){
////                sendRequest(categoryMasterId,questionList,ans);
//            }
//        }else{
//            Fragment fragment = fm.findFragmentByTag(FileUploadFragment.class.getSimpleName());
//            if (fragment != null) {
//                fragment.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//    }

    public void sendRequest(int categoryMasterId, ArrayList<Integer> questionList, ArrayList<String> ans, OnRequestCallbackListener listener) {
        this.categoryMasterId = categoryMasterId;
        this.questionList = questionList;
        this.ans = ans;
        if (!Prefs.contains(CommonMethod.USERID)) {
            hasRequest = true;
            startActivityForResult(new Intent(this, LoginActivity.class), RC_LOGIN);
            return;
        }

        Map<String, Integer> fieldsQ = new HashMap<>();
        Map<String, String> fieldsA = new HashMap<>();
        for (int i = 0; i < questionList.size(); i++) {
            fieldsQ.put("Questionanswermaster[" + i + "][questionmaster_id]", questionList.get(i));
            fieldsA.put("Questionanswermaster[" + i + "][answer]", ans.get(i));
        }


        Observable<ResponseBody> observable = NetworkCall.getController().sendRequest(Prefs.getString(CommonMethod.USERID), categoryMasterId, fieldsQ, fieldsA);
        Disposable s = observable.flatMap(new Function<ResponseBody, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(ResponseBody responseBody) throws Exception {
                String response = responseBody.string();
//               {"msg":"Your Call Back Request Successfully Plased.","flag":1}
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("flag")) {
                    String flag = jsonObject.getString("flag");
                    if (flag != null && flag.equalsIgnoreCase("1")) {
                        return Observable.just("1");
                    } else
                        return Observable.just(jsonObject.getString("msg"));
                }
                return Observable.just("-1");

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        hasRequest = false;
                        String res = (String) o;
                        if (TextUtils.isDigitsOnly(res)) {
                            if (res.equalsIgnoreCase("-1")) {
                                listener.onFailure("Error storing data");
                            } else {
                                listener.onSuccess();
                            }
                        }
                    }
                }, throwable -> {
                    listener.onFailure(throwable.getMessage());
                });
    }

    public void carryOnRequest() {

    }

    public void selectedLabBranch(LabModel o) {
        if (o!=null) {
            onBackPressed();
            viewModel.setLabModel(o);
            labSelectedListner.onLabSelected(o);


        }

    }

    public void setLabSelectedListner(LabSelectionListener labSelectedListner) {
        this.labSelectedListner = labSelectedListner;
    }

    public interface PermissionLocationCallbackListener {
        void onPermissionGranted();

        void onPermissionDenied();
    }

    public interface PermissionStorageCallbackListener {
        void onPermissionGranted();

        void onPermissionDenied();
    }

    public interface OnRequestCallbackListener {
        void onSuccess();

        void onFailure(String msg);
    }

}
