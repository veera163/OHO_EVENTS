package ohopro.com.ohopro.Api;

import ohopro.com.ohopro.domains.SingleEntry;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by home on 1/10/2018.
 */

public interface CalenderSaveApi {
    @Headers("Content-Type: application/json")
    @POST("productCalendar/v2")
    Call<SingleEntry> getUser(@Body String body);
}
