package aliefyaFikriIhsaniJSleepMN.android.request;

import aliefyaFikriIhsaniJSleepMN.android.model.Account;
import retrofit2.Call;
import retrofit2.http.*;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("account/login")
    Call<Account> login (@Query("email") String username, @Query("password") String password);

    @POST("account/register")
    Call<Account> register (@Query("username") String username, @Query("email") String email, @Query("password") String password);
}
