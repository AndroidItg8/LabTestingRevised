package itg8.com.labtestingapp.req_status.model;

import com.google.gson.annotations.SerializedName;

public class RequestmastersTestmaster{

	@SerializedName("created")
	private String created;

	@SerializedName("testmaster_id")
	private String testmasterId;

	@SerializedName("requestmaster_id")
	private String requestmasterId;

	@SerializedName("id")
	private String id;

	public void setCreated(String created){
		this.created = created;
	}

	public String getCreated(){
		return created;
	}

	public void setTestmasterId(String testmasterId){
		this.testmasterId = testmasterId;
	}

	public String getTestmasterId(){
		return testmasterId;
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
			"RequestmastersTestmaster{" + 
			"created = '" + created + '\'' + 
			",testmaster_id = '" + testmasterId + '\'' + 
			",requestmaster_id = '" + requestmasterId + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}