package itg8.com.labtestingapp.splash.mvvm;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.db.tables.MainCategory;
import itg8.com.labtestingapp.db.tables.SubCategory;
import itg8.com.labtestingapp.db.tables.Test;
import itg8.com.labtestingapp.splash.CategoryModel;

public class CategoryController {

    private static final String TAG = "CategoryController";
    private String resource;

    public CategoryController(String resource) {
        this.resource = resource;
    }

    public CategoryModel arrageCategories(){
        List<MainCategory> mainCategories=new ArrayList<>();
        List<SubCategory> subCategories=new ArrayList<>();
        List<Test> tests=new ArrayList<>();
        CategoryModel model=new CategoryModel();
        model.setMainCategories(mainCategories);
        model.setSubCategories(subCategories);
        model.setTests(tests);
        try {
            JSONArray jsonArray=new JSONArray(resource);
            JSONArray mJCategories=jsonArray.getJSONArray(0);
            JSONArray mJTests=jsonArray.getJSONArray(1);
            JSONObject obj;
            for (int i=0;i<mJCategories.length(); i++){
                JSONObject objTemp=mJCategories.getJSONObject(i);
                if(objTemp.has("Categorymaster")) {
                    obj = objTemp.getJSONObject("Categorymaster");
                    mainCategories.add(setMainCategory(obj));
                }
                if(objTemp.has("ChildCategory")) {
                    JSONArray mJChildCat = objTemp.getJSONArray("ChildCategory");
                    for (int j = 0; j < mJChildCat.length(); j++) {
                        JSONObject mChildObj = mJChildCat.getJSONObject(j);
                        subCategories.add(setSubCategory(mChildObj));
                    }
                }
            }
            if(mJTests!=null)
                for(int p=0; p<mJTests.length(); p++){
                    JSONObject objTemp=mJTests.getJSONObject(p);
                    if(objTemp.has("Testmaster")){
                        JSONObject testmaster=objTemp.getJSONObject("Testmaster");
                        tests.add(setTest(testmaster));
                    }
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return model;
    }

    private Test setTest(JSONObject testmaster) throws JSONException {
        Test test=new Test();
        if(testmaster.has("id"))
            test.setId(Integer.parseInt(testmaster.getString("id")));
        if(testmaster.has("categorymaster_id"))
            test.setCategoryid(Integer.parseInt(testmaster.getString("categorymaster_id")));
        if(testmaster.has("name"))
            test.setName(testmaster.getString("name"));
        if (testmaster.has("description"))
            test.setDescription(testmaster.getString("description"));
        if(testmaster.has("productprice"))
            test.setProductprice(Float.parseFloat(testmaster.getString("productprice")));
        if(testmaster.has("created"))
            test.setCreated(testmaster.getString("created"));
        if(testmaster.has("image"))
            test.setImage(testmaster.getString("image"));
        return test;
    }

    private SubCategory setSubCategory(JSONObject obj) throws JSONException {
        SubCategory category=new SubCategory();
        if(obj.has("id")){
            category.setId(Integer.parseInt(obj.getString("id")));
        }
        if(obj.has("name")){
            category.setName(obj.getString("name"));
        }
        if(obj.has("parent_id")){
            category.setMaincatid(Integer.parseInt(obj.getString("parent_id")));
        }
        if(obj.has("image")){
            category.setImage(obj.getString("image"));
        }
        if(obj.has("proid")){
            category.setProid(Integer.parseInt(obj.getString("proid")));
        }
        return category;
    }

    private MainCategory setMainCategory(JSONObject obj) throws JSONException {
        MainCategory category=new MainCategory();
        category.setId(Integer.parseInt(obj.getString("id")));
        if(obj.has("image") && !TextUtils.isEmpty(obj.getString("image"))){
            category.setImage(obj.getString("image"));
            getTempFile(MyApplication.getInstance(),CommonMethod.IMAGE_URL+category.getImage());
        }
        if(obj.has("name") && !TextUtils.isEmpty(obj.getString("name"))){
            category.setName(obj.getString("name"));
        }
        if(obj.has("shortby") && !TextUtils.isEmpty(obj.getString("shortby"))){
            category.setShortby(Integer.parseInt(obj.getString("shortby")));
        }
        return category;
    }


    private File getTempFile(Context context, String url) {
        File file=null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
            if(file.exists())
            {
                Log.d(TAG, "getTempFile: FileCreated"+fileName);
            }
        } catch (IOException e) {
            // Error while creating file
            Log.e(TAG, "getTempFile: File Error"+url,e );
            e.printStackTrace();
        }
        return file;
    }
}
