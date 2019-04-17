package itg8.com.labtestingapp.splash.mvvm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import itg8.com.labtestingapp.common.MyApplication;
import itg8.com.labtestingapp.db.tables.SubAdmin;
import itg8.com.labtestingapp.db.tables.SubAdminTest;
import itg8.com.labtestingapp.db.tables.Test;

public class SubAdminController {

    private String response;

    public SubAdminController(String response) {
        this.response = response;
    }

    public List<SubAdmin> getSubAdmin() throws JSONException {
        JSONArray jsonArray=new JSONArray(response);
        List<SubAdmin> subAdmins=new ArrayList<>();
        List<SubAdminTest> subAdminTests=new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject object=jsonArray.getJSONObject(i);
            SubAdmin admin = null;
            if(object.has("User")){
                JSONObject userObject=object.getJSONObject("User");
                 admin=new SubAdmin();
                if(userObject.has("id"))
                    admin.setId(Integer.parseInt(userObject.getString("id")));
                if(userObject.has("address"))
                    admin.setAddress(userObject.getString("address"));
                if(userObject.has("email"))
                    admin.setEmail(userObject.getString("email"));
                if(userObject.has("first_name"))
                    admin.setFirstname(userObject.getString("first_name"));
                if(userObject.has("last_name"))
                    admin.setLastname(userObject.getString("last_name"));
                if(userObject.has("latitude"))
                    admin.setLatitude(userObject.getString("latitude"));
                if(userObject.has("longitude"))
                    admin.setLongitude(userObject.getString("longitude"));
                if(userObject.has("companyname"))
                    admin.setCompanyname(userObject.getString("companyname"));
                subAdmins.add(admin);
            }
            if(object.has("UsersTestmasters") && admin!=null){
                JSONArray jUserTestMaster=object.getJSONArray("UsersTestmasters");
                JSONObject jUserTest=jUserTestMaster.getJSONObject(0);
                SubAdminTest adminTest=new SubAdminTest();
                adminTest.setSub_admin_id(admin.getId());
                adminTest.setTest_id(Integer.parseInt(jUserTest.getString("testmaster_id")));
                subAdminTests.add(adminTest);
            }
        }
        MyApplication.getInstance().saveSubAdminTests(subAdminTests);
        return subAdmins;
    }
}
