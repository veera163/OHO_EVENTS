package ohopro.com.ohopro.Api;

import android.util.Log;

import org.json.JSONObject;

import java.util.List;

import ohopro.com.ohopro.domains.SingleDateRes;
import ohopro.com.ohopro.domains.SingleEntry;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by home on 1/5/2018.
 */

public interface SingleDateApi {

    @Headers("Content-Type: application/json")
    @POST("productCalendar/getCalendarEntries")
    Call<SingleEntry> getUser(@Body String body);
}
