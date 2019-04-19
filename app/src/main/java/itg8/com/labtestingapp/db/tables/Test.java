package itg8.com.labtestingapp.db.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import itg8.com.labtestingapp.BR;


@Entity
public class Test  extends BaseObservable implements Parcelable {

    @PrimaryKey(autoGenerate = false)
    public int id;

    private String name;

    private String description;

    private float productprice;

    private String created;

    private int categoryid;

    private String image;

    @Ignore

    private int itemCartSize;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Bindable
    public int getItemCartSize() {
        return itemCartSize;
    }

    public void decreaseQty(){
        if(itemCartSize>0){
            setItemCartSize(--itemCartSize);
        }
    }
    public void increaseQty(){
        if(itemCartSize<30){
            setItemCartSize(++itemCartSize);
        }
    }

    public void setItemCartSize(int itemCartSize) {
        this.itemCartSize = itemCartSize;
        notifyPropertyChanged(BR.itemCartSize);
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }



    public Test() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getProductprice() {
        return productprice;
    }
    public String getProductpricetext(){
        return String.valueOf(productprice);
    }

    public void setProductprice(float productprice) {
        this.productprice = productprice;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String toString() {
        return name+":"+categoryid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeFloat(this.productprice);
        dest.writeString(this.created);
        dest.writeInt(this.categoryid);
        dest.writeInt(this.itemCartSize);
    }

    protected Test(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.productprice = in.readFloat();
        this.created = in.readString();
        this.categoryid = in.readInt();
        this.itemCartSize = in.readInt();
    }

    public static final Parcelable.Creator<Test> CREATOR = new Parcelable.Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel source) {
            return new Test(source);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };


}
