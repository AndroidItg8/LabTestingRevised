package itg8.com.labtestingapp.common;

import android.content.Context;
import android.view.View;

import itg8.com.labtestingapp.MainActivity;

public class CommonMethod {
//    public static final String BASE_URL = "http://labtest.itechgalaxysolutions.com/";
//    public static final String BASE_URL = "http://192.168.0.125/labtest/";
    public static final String BASE_URL = "http://www.indianberg.in/test/";
    public static final String IMAGE_URL=BASE_URL+"img/procatandsubcat/";
    public static final String APP_DB = "lab_test_db";


    public static final String ALL_SET_UP = "everythingIsAwesome";
    public static final String USERID = "UserID";
    public static final String GOOGLE_PLACE_URL = "https://maps.googleapis.com/maps/api/";
    public static final String DESCRIPTION = "descriptionForTestDetail";
    public static final String TITLE = "title";
    public static final String IMAGE_URL_REQ = BASE_URL+"img/userfile/";

    public static void showSnackbar(View view,Context context) {
        UtilSnackbar.showSnakbarOkBtn(view, "Request send Successfully", new UtilSnackbar.OnSnackbarActionClickListener() {
            @Override
            public void onRetryClicked() {
                ((MainActivity)context).onBackPressed();
            }
        });
    }

    public static void showErrorSnackbar(View view, String msg,Context context) {
        UtilSnackbar.showSnakbarOkBtn(view, "Request send Successfully", new UtilSnackbar.OnSnackbarActionClickListener() {
            @Override
            public void onRetryClicked() {
                ((MainActivity)context).onBackPressed();
            }
        });
    }

    public static float addGst(float amt,int percent) {
        return  (amt / 100.0f) * percent;
    }
}
