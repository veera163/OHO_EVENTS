package ohopro.com.ohopro.Api;

import java.util.List;

import ohopro.com.ohopro.domains.ComboRes;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by home on 1/2/2018.
 */

public interface ComboProductApi {
    @GET
    Call<List<ComboRes>> getData(@Url String url);
}
