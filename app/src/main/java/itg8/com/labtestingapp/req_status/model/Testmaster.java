package itg8.com.labtestingapp.req_status.model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

public class Testmaster extends BaseObservable {

	@SerializedName("created")
	private String created;

	@SerializedName("RequestmastersTestmaster")
	private RequestmastersTestmaster requestmastersTestmaster;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private String id;

	@SerializedName("categorymaster_id")
	private String categorymasterId;

	@SerializedName("productprice")
	private String productprice;

	public void setCreated(String created){
		this.created = created;
	}

	public String getCreated(){
		return created;
	}

	public void setRequestmastersTestmaster(RequestmastersTestmaster requestmastersTestmaster){
		this.requestmastersTestmaster = requestmastersTestmaster;
	}

	public RequestmastersTestmaster getRequestmastersTestmaster(){
		return requestmastersTestmaster;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCategorymasterId(String categorymasterId){
		this.categorymasterId = categorymasterId;
	}

	public String getCategorymasterId(){
		return categorymasterId;
	}

	public void setProductprice(String productprice){
		this.productprice = productprice;
	}

	public String getProductprice(){
		return productprice;
	}

	@Override
 	public String toString(){
		return 
			"Testmaster{" + 
			"created = '" + created + '\'' + 
			",requestmastersTestmaster = '" + requestmastersTestmaster + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",categorymaster_id = '" + categorymasterId + '\'' + 
			",productprice = '" + productprice + '\'' + 
			"}";
		}
}