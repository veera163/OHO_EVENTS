package ohopro.com.ohopro.Api;

import java.util.List;

import ohopro.com.ohopro.domains.CalenderEntryRes;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by home on 1/2/2018.
 */

public interface CalenderEntryApi {
    @GET
    Call<CalenderEntryRes> getData(@Url String url);
}
