package in.purestudy.app.purestudytask.retrofitAPI;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kailash on 06-Dec-17.
 */

public interface RequestInterface {
    @GET("data/jsondata")
    Call<String> getJSON();
}
