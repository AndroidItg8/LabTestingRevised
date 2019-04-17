package itg8.com.labtestingapp.req_status.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import itg8.com.labtestingapp.db.tables.Test;

public class RequestStatusModel extends BaseObservable implements Parcelable {

	@SerializedName("Requestmaster")
	private Requestmaster requestmaster;

	@SerializedName("Requestuploadmaster")
	private List<RequestuploadmasterItem> requestuploadmaster;

	@SerializedName("Testmaster")
	private List<Testmaster> tests;

	public List<Testmaster> getTests() {
		return tests;
	}

	public void setTests(List<Testmaster> tests) {
		this.tests = tests;
	}



	public void setRequestmaster(Requestmaster requestmaster){
		this.requestmaster = requestmaster;
	}

	public Requestmaster getRequestmaster(){
		return requestmaster;
	}

	public void setRequestuploadmaster(List<RequestuploadmasterItem> requestuploadmaster){
		this.requestuploadmaster = requestuploadmaster;
	}

	public List<RequestuploadmasterItem> getRequestuploadmaster(){
		return requestuploadmaster;
	}



	@Override
 	public String toString(){
		return 
			"RequestStatusModel{" + 
			"requestmaster = '" + requestmaster + '\'' + 
			",requestuploadmaster = '" + requestuploadmaster + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.requestmaster, flags);
		dest.writeList(this.requestuploadmaster);
	}

	public RequestStatusModel() {
	}

	protected RequestStatusModel(Parcel in) {
		this.requestmaster = in.readParcelable(Requestmaster.class.getClassLoader());
		this.requestuploadmaster = new ArrayList<RequestuploadmasterItem>();
		in.readList(this.requestuploadmaster, RequestuploadmasterItem.class.getClassLoader());
	}

	public static final Parcelable.Creator<RequestStatusModel> CREATOR = new Parcelable.Creator<RequestStatusModel>() {
		@Override
		public RequestStatusModel createFromParcel(Parcel source) {
			return new RequestStatusModel(source);
		}

		@Override
		public RequestStatusModel[] newArray(int size) {
			return new RequestStatusModel[size];
		}
	};
}