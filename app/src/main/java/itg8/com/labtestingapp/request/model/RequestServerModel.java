
package itg8.com.labtestingapp.request.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestServerModel implements Parcelable {

    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("Test")
    @Expose
    private List<CardTest> test = null;
    public final static Parcelable.Creator<RequestServerModel> CREATOR = new Creator<RequestServerModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RequestServerModel createFromParcel(Parcel in) {
            return new RequestServerModel(in);
        }

        public RequestServerModel[] newArray(int size) {
            return (new RequestServerModel[size]);
        }

    };

    protected RequestServerModel(Parcel in) {
        this.stateId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.cityId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.test, (CardTest.class.getClassLoader()));
    }

    public RequestServerModel() {
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<CardTest> getTest() {
        return test;
    }

    public void setTest(List<CardTest> test) {
        this.test = test;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(stateId);
        dest.writeValue(cityId);
        dest.writeValue(userId);
        dest.writeList(test);
    }

    public int describeContents() {
        return 0;
    }


    public static class CardTest {
        @SerializedName("testid")
        @Expose
        private int testId;
        @SerializedName("qty")
        @Expose
        private int qty;

        public int getTestId() {
            return testId;
        }

        public void setTestId(int testId) {
            this.testId = testId;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }
    }
}
