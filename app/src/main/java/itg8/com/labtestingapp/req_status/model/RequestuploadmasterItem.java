package itg8.com.labtestingapp.req_status.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RequestuploadmasterItem implements Parcelable {

	@SerializedName("filename")
	private String filename;

	@SerializedName("created")
	private String created;

	@SerializedName("requestmaster_id")
	private String requestmasterId;

	@SerializedName("id")
	private String id;

	public void setFilename(String filename){
		this.filename = filename;
	}

	public String getFilename(){
		return filename;
	}

	public void setCreated(String created){
		this.created = created;
	}

	public String getCreated(){
		return created;
	}

	public void setRequestmasterId(String requestmasterId){
		this.requestmasterId = requestmasterId;
	}

	public String getRequestmasterId(){
		return requestmasterId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}



	@Override
 	public String toString(){
		return 
			"RequestuploadmasterItem{" + 
			"filename = '" + filename + '\'' + 
			",created = '" + created + '\'' + 
			",requestmaster_id = '" + requestmasterId + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.filename);
		dest.writeString(this.created);
		dest.writeString(this.requestmasterId);
		dest.writeString(this.id);
	}

	public RequestuploadmasterItem() {
	}

	protected RequestuploadmasterItem(Parcel in) {
		this.filename = in.readString();
		this.created = in.readString();
		this.requestmasterId = in.readString();
		this.id = in.readString();
	}

	public static final Parcelable.Creator<RequestuploadmasterItem> CREATOR = new Parcelable.Creator<RequestuploadmasterItem>() {
		@Override
		public RequestuploadmasterItem createFromParcel(Parcel source) {
			return new RequestuploadmasterItem(source);
		}

		@Override
		public RequestuploadmasterItem[] newArray(int size) {
			return new RequestuploadmasterItem[size];
		}
	};
}