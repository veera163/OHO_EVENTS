package ohopro.com.ohopro.Api;

import java.util.List;

import ohopro.com.ohopro.domains.ProductAvailabityRes;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by home on 12/28/2017.
 */

public interface ProductAvailabilityApi {

    @GET
    Call<List<ProductAvailabityRes>> getData(@Url String url);
}
