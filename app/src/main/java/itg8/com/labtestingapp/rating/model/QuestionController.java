package itg8.com.labtestingapp.rating.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import itg8.com.labtestingapp.common.SpinnerGenericModel;

public class QuestionController {

    private String res;

    public QuestionController(String res) {
        this.res = res;
    }

    public List<SpinnerGenericModel> getModels() throws JSONException {
        List<SpinnerGenericModel> models=new ArrayList<>();
        JSONArray array=new JSONArray(res);
        for(int i=0; i<array.length(); i++){
            JSONObject object=array.getJSONObject(i);
            if(object.has("Feedbackcategorymaster")){
                JSONObject feedbackCat=object.getJSONObject("Feedbackcategorymaster");
                SpinnerGenericModel model=new SpinnerGenericModel();
                if(feedbackCat.has("id")){
                    model.setId(feedbackCat.getString("id"));
                }
                if(feedbackCat.has("name")){
                    model.setValue(feedbackCat.getString("name"));
                }
                models.add(model);
            }
        }
        return models;
    }
}
