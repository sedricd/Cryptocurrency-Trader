package retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rmed177lt on 2/9/16.
 */

public interface ApiInterface {

    @GET("symbols_details")
    Call<List<String>> getSymbols();

//    //delete a checkin
//    @DELETE("checkins/{id}")
//    Call<AssignedEmployee> deleteCheckin(@Body RequestBody body,
//
//    @POST("checkins/checkin_list")
//    Call<CheckinSyncDataModel> getMetaDataOfTopCheckinsMy(@Query("assigned_to") int id,
//                                                       @Body RequestBody body);
//
//    //milestone update
//    @PUT("checkins/{checkin_id}/checkin_updates/{milestone_id}")
//    Call<UpdateOuterJSON> updateMilestone(@Body RequestBody body,
//                                          @Path("checkin_id") int checkin_id,
//                                          @Path("milestone_id") int milestone_id);
//
//    //DrillDown
//    @GET("checkins/summary_checkins")
//    Call <Checkin22> getDrillDownResult(@Query("code_name") String code_name,
//                                        @Query("type") String type,
//                                        @Query("limit") int limit,
//                                        @Query("page") int page_number);




}