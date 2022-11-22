package aliefyaFikriIhsaniJSleepMN.android.request;

import aliefyaFikriIhsaniJSleepMN.android.model.Account;
import retrofit2.Call;
import retrofit2.http.*;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);
}
