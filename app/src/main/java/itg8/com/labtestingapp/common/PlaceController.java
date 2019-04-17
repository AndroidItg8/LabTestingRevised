package itg8.com.labtestingapp.common;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceController {

    @GET("geocode/json")
    Observable<ResponseBody> getLatLng(@Query("components") String component, @Query("sensor")  boolean isSensor,
                                       @Query("key") String key);

}
