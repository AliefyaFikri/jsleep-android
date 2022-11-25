package aliefyaFikriIhsaniJSleepMN.android.request;

public class UtilsApi {
    public static final String BASE_URL_API = "http://192.168.18.22:8080/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
