package itg8.com.labtestingapp.request.mvvm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import itg8.com.labtestingapp.common.LatLng;

class MapController {

    private String response;

    public MapController(String response) {
        this.response = response;
    }

    public LatLng getLatLng() throws JSONException {
        LatLng latlng=new LatLng();
        JSONObject jsonObject=new JSONObject(response);
        if(jsonObject.has("results")){
            if(jsonObject.get("results") instanceof JSONArray){
                JSONArray jResult=jsonObject.getJSONArray("results");
                if(jResult.length()>0){
                    JSONObject jResObj=jResult.getJSONObject(0);
                    if(jResObj.has("geometry")){
                        JSONObject geomatry=jResObj.getJSONObject("geometry");
                        if(geomatry.has("location")){
                            JSONObject jLoc=geomatry.getJSONObject("location");
                            if(jLoc.has("lat")){
                               latlng.lat=jLoc.getDouble("lat");
                            }
                            if(jLoc.has("lng")){
                                latlng.lng=jLoc.getDouble("lng");
                            }
                        }
                    }
                }
            }
        }
        return latlng;
    }
}
