package itg8.com.labtestingapp.req_status.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Requestmaster implements Parcelable {

	@SerializedName("pincode")
	private String pincode;

	@SerializedName("address")
	private String address;

	@SerializedName("totalamount")
	private String totalamount;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("citymaster_id")
	private String citymasterId;

	@SerializedName("sybadminid")
	private String sybadminid;

	@SerializedName("created")
	private String created;

	@SerializedName("approvedbyadmin")
	private String approvedbyadmin;

	@SerializedName("id")
	private String id;

	@SerializedName("requestorderid")
	private String requestorderid;

	@SerializedName("acceptbysubadmin")
	private String acceptbysubadmin;

	@SerializedName("pickstatus")
	private String pickstatus;

	public void setPincode(String pincode){
		this.pincode = pincode;
	}

	public String getPincode(){
		return pincode;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setTotalamount(String totalamount){
		this.totalamount = totalamount;
	}

	public String getTotalamount(){
		return totalamount;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setCitymasterId(String citymasterId){
		this.citymasterId = citymasterId;
	}

	public String getCitymasterId(){
		return citymasterId;
	}

	public void setSybadminid(String sybadminid){
		this.sybadminid = sybadminid;
	}

	public String getSybadminid(){
		return sybadminid;
	}

	public void setCreated(String created){
		this.created = created;
	}

	public String getCreated(){
		return created;
	}

	public void setApprovedbyadmin(String approvedbyadmin){
		this.approvedbyadmin = approvedbyadmin;
	}

	public String getApprovedbyadmin(){
		return approvedbyadmin;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setRequestorderid(String requestorderid){
		this.requestorderid = requestorderid;
	}

	public String getRequestorderid(){
		return requestorderid;
	}

	public void setAcceptbysubadmin(String acceptbysubadmin){
		this.acceptbysubadmin = acceptbysubadmin;
	}

	public String getAcceptbysubadmin(){
		return acceptbysubadmin;
	}

	public void setPickstatus(String pickstatus){
		this.pickstatus = pickstatus;
	}

	public String getPickstatus(){
		return pickstatus;
	}



	@Override
 	public String toString(){
		return 
			"Requestmaster{" + 
			"pincode = '" + pincode + '\'' + 
			",address = '" + address + '\'' + 
			",totalamount = '" + totalamount + '\'' + 
			",user_id = '" + userId + '\'' + 
			",citymaster_id = '" + citymasterId + '\'' + 
			",sybadminid = '" + sybadminid + '\'' + 
			",created = '" + created + '\'' + 
			",approvedbyadmin = '" + approvedbyadmin + '\'' + 
			",id = '" + id + '\'' + 
			",requestorderid = '" + requestorderid + '\'' + 
			",acceptbysubadmin = '" + acceptbysubadmin + '\'' + 
			",pickstatus = '" + pickstatus + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.pincode);
		dest.writeString(this.address);
		dest.writeString(this.totalamount);
		dest.writeString(this.userId);
		dest.writeString(this.citymasterId);
		dest.writeString(this.sybadminid);
		dest.writeString(this.created);
		dest.writeString(this.approvedbyadmin);
		dest.writeString(this.id);
		dest.writeString(this.requestorderid);
		dest.writeString(this.acceptbysubadmin);
		dest.writeString(this.pickstatus);
	}

	public Requestmaster() {
	}

	protected Requestmaster(Parcel in) {
		this.pincode = in.readString();
		this.address = in.readString();
		this.totalamount = in.readString();
		this.userId = in.readString();
		this.citymasterId = in.readString();
		this.sybadminid = in.readString();
		this.created = in.readString();
		this.approvedbyadmin = in.readString();
		this.id = in.readString();
		this.requestorderid = in.readString();
		this.acceptbysubadmin = in.readString();
		this.pickstatus = in.readString();
	}

	public static final Parcelable.Creator<Requestmaster> CREATOR = new Parcelable.Creator<Requestmaster>() {
		@Override
		public Requestmaster createFromParcel(Parcel source) {
			return new Requestmaster(source);
		}

		@Override
		public Requestmaster[] newArray(int size) {
			return new Requestmaster[size];
		}
	};
}