package aliefyaFikriIhsaniJSleepMN.android.request;

import aliefyaFikriIhsaniJSleepMN.android.model.Account;
import aliefyaFikriIhsaniJSleepMN.android.model.BedType;
import aliefyaFikriIhsaniJSleepMN.android.model.City;
import aliefyaFikriIhsaniJSleepMN.android.model.Facility;
import aliefyaFikriIhsaniJSleepMN.android.model.Payment;
import aliefyaFikriIhsaniJSleepMN.android.model.Renter;
import aliefyaFikriIhsaniJSleepMN.android.model.Room;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface ini berfungsi untuk mendeklarasikan semua method yang akan digunakan
 * untuk mengakses API
 *
 * @author Aliefya Fikri Ihsani
 * @version 1.0
 */

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("account/login")
    Call<Account> login (@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> register  (@Query("name") String name, @Query("email") String email, @Query("password") String password);

    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id, @Query("name") String name, @Query("address") String address, @Query("phoneNumber") String phoneNumber);

    @POST("account/{id}/topUp")
    Call<Account> topUp(@Path("id") int id, @Query("balance") int balance);

    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int id);

    @GET("room/{id}/renter")
    Call<List<Room>> getRoomByRenter (@Path("id") int id, @Query("page") int page, @Query("pageSize") int pageSize);

    @POST("room/create")
    Call<Room> createRoom (@Query("accountId") int accountId, @Query("name") String name, @Query("size") int size, @Query("price") double price, @Query("facility") ArrayList<Facility> facility, @Query("city") City city, @Query("address") String address, @Query("bedType") BedType bedType);

    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom (@Query("page") int page, @Query("pageSize") int pageSize);

    @POST("payment/createPayment")
    Call<Payment> createPayment (@Query("buyerId") int buyerId, @Query("renterId") int sellerId, @Query("roomId") int roomId, @Query("from") String from, @Query("to") String to);

    @POST("payment/{id}/cancel")
    Call<Boolean> cancelPayment (@Path("id") int id);

    @POST("payment/{id}/accept")
    Call<Boolean> acceptPayment (@Path("id") int id);

    @GET("payment/get")
    Call<Payment> getPayment (@Query("buyerId") int buyerId, @Query("renterId") int renterId, @Query("roomId") int roomId);
}
