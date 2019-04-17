package itg8.com.labtestingapp.rating.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;

import itg8.com.labtestingapp.BR;
import itg8.com.labtestingapp.common.UtilSnackbar;

public class RatingModel extends BaseObservable {
    private String userID;
    private String description;
    private String descriptionErr;
    private float rating;
    private int categoryID;

    @Bindable
    public String getDescriptionErr() {
        return descriptionErr;
    }

    public void setDescriptionErr(String descriptionErr) {
        this.descriptionErr = descriptionErr;
        notifyPropertyChanged(BR.descriptionErr);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isValidate(View view) {
        boolean isValid=true;
        if(getRating()<=0){
            UtilSnackbar.showSnakbarOkBtn(view, "Please provide rating", new UtilSnackbar.OnSnackbarActionClickListener() {
                @Override
                public void onRetryClicked() {

                }
            });
            return false;
        }
        if(categoryID<=0){
            UtilSnackbar.showSnakbarOkBtn(view, "Please select category", new UtilSnackbar.OnSnackbarActionClickListener() {
                @Override
                public void onRetryClicked() {

                }
            });
            return false;
        }
        if(TextUtils.isEmpty(description)){
            setDescriptionErr("Please add description");
            return false;
        }
        return true;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryID() {
        return String.valueOf(categoryID);
    }
}
