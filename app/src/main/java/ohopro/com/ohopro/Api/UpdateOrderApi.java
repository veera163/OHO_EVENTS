package ohopro.com.ohopro.Api;

import ohopro.com.ohopro.domains.UpdateOrderRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by home on 1/11/2018.
 */

public interface UpdateOrderApi {

    @Headers("Content-Type: application/json")
    @POST("OrderHistory/")
    Call<UpdateOrderRes> getUser(@Body String body);
}
