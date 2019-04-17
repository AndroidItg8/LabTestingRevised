package itg8.com.labtestingapp.common;


import android.os.Parcel;
import android.os.Parcelable;

public class SpinnerGenericModel implements Parcelable {

    private String id;

    public SpinnerGenericModel() {
    }

    public SpinnerGenericModel(String text, String value) {

        this.id = text;
        this.value = value;
    }

    public String getId() {

        return id;
    }

    public void setId(String text) {
        this.id = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.value);
    }

    protected SpinnerGenericModel(Parcel in) {
        this.id = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<SpinnerGenericModel> CREATOR = new Parcelable.Creator<SpinnerGenericModel>() {
        @Override
        public SpinnerGenericModel createFromParcel(Parcel source) {
            return new SpinnerGenericModel(source);
        }

        @Override
        public SpinnerGenericModel[] newArray(int size) {
            return new SpinnerGenericModel[size];
        }
    };
}