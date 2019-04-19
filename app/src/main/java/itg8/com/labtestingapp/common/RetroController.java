package itg8.com.labtestingapp.common;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import itg8.com.labtestingapp.db.tables.Test;
import itg8.com.labtestingapp.lab.model.LabModel;
import itg8.com.labtestingapp.req_status.model.RequestStatusModel;
import itg8.com.labtestingapp.request.model.RequestModel;
import itg8.com.labtestingapp.request.model.RequestServerModel;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetroController {

    @GET("Categorymasters/appcategorylist")
    Observable<ResponseBody> downloadCategory();


    //    User[username]:8087890052
//    User[first_name]:test f name
//    User[last_name]:test l name
//    User[email]:bysocials@itechgalaxy.com
//    User[password]:12345678
//    User[cpassword]:12345678
//    User[referenceby]:
//    User[registerby]:1
//    User[authkey]:hjhuy6456567586ygsdfg




//    User[first_name] :Rajesh
//    User[last_name] :Rao
//    User[username] :8087890177
//    User[email] :aakas1h@gmail.com

//    User[statemaster_id] :23
//    User[citymaster_id] :2716
//
// User[password] :123456
//    User[referenceby] :TCS_4563
//    User[user_group_id] :2
//    //User[cpassword] :123456
    @FormUrlEncoded
    @POST("appregister")
    Observable<ResponseBody> postRegister(@Field("User[username]") String mobile,
                                          @Field("User[first_name]") String firstName,
                                          @Field("User[last_name]") String lName,
                                          @Field("User[email]") String email,
                                          @Field("User[password]") String password,
                                          @Field("User[cpassword]") String cpassword,
                                          @Field("User[referenceby]") String referal,
                                          @Field("User[statemaster_id]") int stateId,
                                          @Field("User[citymaster_id]") int cityId,
                                          @Field("User[user_group_id]") int type);

    @FormUrlEncoded
    @POST("jsons_login")
    Observable<ResponseBody> login(@Field("User[username]") String username, @Field("User[password]") String password);

    @Multipart
    @POST("Requestuploadmasters/uploadfile")
    Observable<ResponseBody> upload(@Part MultipartBody.Part file);


    @GET("getsubadminlist")
    Observable<ResponseBody> downloadSubAdmin();

    //    Requestmaster[user_id]:20
//    Requestmaster[citymaster_id]:124
//    RequestmastersTestmaster[0][testmaster_id]:1
//    Requestmaster[address]:Nagpur gayatri namgar
//    Requestmaster[pincode]:440022
//    Requestmaster[pickstatus]:1
//    Requestmaster[sybadminid]:19
//    Requestuploadmaster[0][filename]:2018_12_24_1459.jpg
//    Requestuploadmaster[1][filename]:7405.jpg
//    Requestuploadmaster[2][filename]:uplodedfile14114.jpg
//    Requestmaster[totalamount]:25000
    @FormUrlEncoded
    @POST("Requestmasters/add")
    Observable<ResponseBody> saveRequest(@Field("Requestmaster[user_id]") String userID,
                                         @Field("Requestmaster[citymaster_id]") String city,
                                         @FieldMap Map<String, Integer> testList,
                                         @FieldMap Map<String, Integer> quantity,
                                         @FieldMap Map<String, Float> totalAmountForProduct,
                                         @Field("Requestmaster[address]") String address,
                                         @Field("Requestmaster[pincode]") String pincode,
                                         @Field("Requestmaster[pickstatus]") int pickStatus,
                                         @Field("Requestmaster[sybadminid]") int adminID,
                                         @Field("Requestuploadmaster[0][filename]") String fileName,
                                         @Field("Requestmaster[totalamount]") String totalAmt,
                                         @Field("Requestmaster[pickupamount]") float pickAmount
    );

    @GET("Requestmasters/myrequest")
    Observable<List<RequestStatusModel>> getAllRequests(@Query("user_id") String userID);

    @FormUrlEncoded
    @POST("Feedbackmasters/add")
    Observable<ResponseBody> storeRating(@Field("Feedbackmaster[user_id]") String userID,
                                         @Field("Feedbackmaster[feedback]") String feedback,
                                         @Field("Feedbackmaster[point]") int point,
                                         @Field("Feedbackmaster[feedbackcategorymaster_id]") String queID);

    //    Callbackmaster[user_id]:2
//    Callbackmaster[categorymaster_id]:10
////Callbackmaster[from]:2
//    Questionanswermaster[0][questionmaster_id]:5
//    Questionanswermaster[0][answer]:Concrete Cover Measurement by Laser Based Instt,Combined Method UPV & RH Test
    @FormUrlEncoded
    @POST("Callbackmasters/callbackappadd")
    Observable<ResponseBody> sendRequest(@Field("Callbackmaster[user_id]") String userID,
                                         @Field("Callbackmaster[categorymaster_id]") int id,
                                         @FieldMap Map<String, Integer> questions,
                                         @FieldMap Map<String, String> answers);


    @GET("Feedbackcategorymasters/categorylist")
    Observable<ResponseBody> downloadQuestions();

    @GET("Statemasters/getstatelist")
    Observable<ResponseBody> downloadStates();

    @GET("Citymasters/getcitylist")
    Observable<ResponseBody> downloadCities(@Query("stateid") String id);

    /**
     * {
     *     "state_id": 23,
     *     "city_id": 2716,
     *     "user_id": 43,
     *     "Test": [
     *         {
     *             "testid": 1,
     *             "qty": 2
     *         },
     *         {
     *             "testid": 4,
     *             "qty": 2
     *         },
     *         {
     *             "testid": 8,
     *             "qty": 2
     *         }
     *     ]
     * }
     *
     */

    @POST("Requestmasters/requestprice")
    Observable<List<LabModel>> postRequestTOServer(@Body RequestServerModel serverModel);

}
