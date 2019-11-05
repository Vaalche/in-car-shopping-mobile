package bg.tusofia.valentinborisov.dipl.clients;


import org.json.JSONObject;

import java.util.List;

import bg.tusofia.valentinborisov.dipl.responses.ProductsResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface CarShoppingClient {

    @GET("/getProductsByBrandName/{brandName}")
    Call<List<ProductsResponse>> getProductsByBrandName(@Path("brandName") String brandName);

    @GET
    Call<ResponseBody> getNearbyPlaces(@Url String url, @Query("ll") String location, @Query("radius") Long radius, @Query("query") String query, @Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("v") String version);
}
