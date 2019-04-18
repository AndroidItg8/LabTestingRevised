package itg8.com.labtestingapp.lab.model;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import itg8.com.labtestingapp.BR;

public class LabModel extends BaseObservable implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("companyname")
    @Expose
    private String companyname;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("totalamount_withouttax")
    @Expose
    private Integer totalamountWithouttax;
    @SerializedName("taxtype")
    @Expose
    private String taxtype;
    @SerializedName("taxamount")
    @Expose
    private Integer taxamount;
    public final static Parcelable.Creator<LabModel> CREATOR = new Creator<LabModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LabModel createFromParcel(Parcel in) {
            return new LabModel(in);
        }

        public LabModel[] newArray(int size) {
            return (new LabModel[size]);
        }

    }
            ;

    protected LabModel(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.companyname = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
        this.totalamountWithouttax = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.taxtype = ((String) in.readValue((String.class.getClassLoader())));
        this.taxamount = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public LabModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;

    }

    public Integer getTotalamountWithouttax() {
        return totalamountWithouttax;
    }

    public void setTotalamountWithouttax(Integer totalamountWithouttax) {
        this.totalamountWithouttax = totalamountWithouttax;
    }

    public String getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }

    public Integer getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(Integer taxamount) {
        this.taxamount = taxamount;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyname);
        dest.writeValue(address);
        dest.writeValue(totalamountWithouttax);
        dest.writeValue(taxtype);
        dest.writeValue(taxamount);
    }

    public int describeContents() {
        return 0;
    }
}
