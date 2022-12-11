package aliefyaFikriIhsaniJSleepMN.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import aliefyaFikriIhsaniJSleepMN.android.model.Facility;
import aliefyaFikriIhsaniJSleepMN.android.model.Invoice;
import aliefyaFikriIhsaniJSleepMN.android.model.Payment;
import aliefyaFikriIhsaniJSleepMN.android.model.Room;
import aliefyaFikriIhsaniJSleepMN.android.request.BaseApiService;
import aliefyaFikriIhsaniJSleepMN.android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Kelas DetailRoomActivity merupakan kelas yang berfungsi untuk menampilkan detail dari kamar yang
 * dipilih dari MainActivity.
 *
 * @author Aliefya Fikri Ihsani
 * @version 1.0
 */

public class DetailRoomActivity extends AppCompatActivity {

    protected static Room room;
    protected Payment payment;
    LinearLayout layoutStart, layoutPay, layoutButton, layoutText;
    CheckBox cbAC, cbRefrigerator, cbWifi, cbBathub, cbBalcony, cbRestaurant, cbSwimmingPool, cbFitnessCenter;
    ArrayList<Facility> listOfFacility;

    /**
     * Method onCreate merupakan method yang pertama kali dijalankan pada saat kelas ini dibuat
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        room = MainActivity.roomList.get(MainActivity.selectedPos);
        listOfFacility = room.facility;
        cbAssign();
        cbSet();

        String price = NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(room.price.price);
        String address = room.address + ", " + room.city;

        TextView nameText = findViewById(R.id.NameFill_DR);
        nameText.setText(room.name);

        TextView bedText = findViewById(R.id.BedFill_DR);
        bedText.setText(room.bedType.toString());

        TextView sizeText = findViewById(R.id.SizeFill_DR);
        sizeText.setText(room.size + " m\u00B2");

        TextView priceText = findViewById(R.id.PriceFill_DR);
        priceText.setText(price);

        TextView addressText = findViewById(R.id.addressAns_detail);
        addressText.setText(address);

        requestGetPayment(LoginActivity.accountObject.id, LoginActivity.accountObject.renter.id, room.id);
    }

    public void cbAssign(){
        cbAC = findViewById(R.id.AC_detail);
        cbBalcony = findViewById(R.id.Balcony_detail);
        cbBathub = findViewById(R.id.Bathtub_detail);
        cbFitnessCenter = findViewById(R.id.Fitness_detail);
        cbRefrigerator = findViewById(R.id.Refrigerator_detail);
        cbRestaurant = findViewById(R.id.Restaurant_detail);
        cbSwimmingPool = findViewById(R.id.SwimmingPool_detail);
        cbWifi = findViewById(R.id.Wifi_detail);
    }

    public void cbSet(){
        System.out.println(listOfFacility);

        for(Facility facility: listOfFacility){
            if (facility == Facility.AC){
                cbAC.setChecked(true);
            }
            else if(facility == Facility.Balcony){
                cbBalcony.setChecked(true);
            }
            else if(facility == Facility.Bathub) {
                cbBathub.setChecked(true);
            }
            else if(facility == Facility.FitnessCenter){
                cbFitnessCenter.setChecked(true);
            }
            else if(facility == Facility.Refrigerator){
                cbRefrigerator.setChecked(true);
            }
            else if(facility == Facility.Restaurant){
                cbRestaurant.setChecked(true);
            }
            else if(facility == Facility.SwimmingPool){
                cbSwimmingPool.setChecked(true);
            }
            else if(facility == Facility.WiFi){
                cbWifi.setChecked(true);
            }
        }
    }

    protected void mainAlgorithm(){
        layoutStart = findViewById(R.id.linearStart_detail);
        layoutPay = findViewById(R.id.linearPayment_detail);
        layoutButton = findViewById(R.id.LinearButton_DR);
        layoutText = findViewById(R.id.TextLinear_DR);
        layoutStart.setVisibility(View.GONE);
        layoutPay.setVisibility(View.GONE);
        TextView infoText = findViewById(R.id.TextInfo_DR);
        TextView statusText = findViewById(R.id.TextStatus_DR);
        if(payment != null && payment.status == Invoice.PaymentStatus.WAITING){
            layoutStart.setVisibility(View.INVISIBLE);
            layoutPay.setVisibility(View.VISIBLE);
            layoutButton.setVisibility(View.VISIBLE);
            layoutText.setVisibility(View.INVISIBLE);
            Button payButton = findViewById(R.id.PayButton_DR);
            Button cancelButton = findViewById(R.id.CancelButton_DR);

            payButton.setOnClickListener(view -> {
                requestAcceptPayment(payment.id);
            });

            cancelButton.setOnClickListener(view -> {
                requestCancelPayment(payment.id);
            });

            String info = "You have booked this room for " +
                    simpleCalcDays(payment.from, payment.to) +
                    " Day(s).\nfrom: " +
                    simpleDateFormat(payment.from) +
                    " to: " +
                    simpleDateFormat(payment.to);
            infoText.setText(info);

        }

        else if (payment != null && payment.status == Invoice.PaymentStatus.SUCCESS){
            layoutStart.setVisibility(View.GONE);
            layoutPay.setVisibility(View.VISIBLE);
            layoutButton.setVisibility(View.GONE);
            layoutText.setVisibility(View.VISIBLE);


            String info = "You have booked this room for " +
                    simpleCalcDays(payment.from, payment.to) +
                    " Day(s).\nfrom: " +
                    simpleDateFormat(payment.from) +
                    " to: " +
                    simpleDateFormat(payment.to);
            infoText.setText(info);
            String status = "Payment status: " + payment.status;
            statusText.setText(status);

        }

        else {

            layoutStart.setVisibility(View.VISIBLE);
            layoutPay.setVisibility(View.INVISIBLE);
            layoutButton.setVisibility(View.INVISIBLE);
            layoutText.setVisibility(View.INVISIBLE);
            Button backButtton = findViewById(R.id.BackButton_DR);
            backButtton.setOnClickListener(view -> {
                Intent mainIntent = new Intent(DetailRoomActivity.this, MainActivity.class);
                startActivity(mainIntent);
            });


            Button bookButtton = findViewById(R.id.BookButton_DR);
            bookButtton.setOnClickListener(view -> {
                Intent bookIntent = new Intent(DetailRoomActivity.this, PaymentActivity.class);
                startActivity(bookIntent);
            });
        }
    }



    public long simpleCalcDays(Date before, Date after){
        long timeDiff = Math.abs(after.getTime() - before.getTime());
        return TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
    }



    public String simpleDateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        assert date != null;
        return sdf.format(date);
    }



    protected Boolean requestAcceptPayment(int id){

        BaseApiService mApiService = UtilsApi.getApiService();
        Context mContext = this;

        mApiService.acceptPayment(id).enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Payment berhasil dilakukan", Toast.LENGTH_LONG).show();
                    Intent move = new Intent(DetailRoomActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }


            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(mContext, "Gagal untuk melakukan Payment", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }



    protected Boolean requestCancelPayment(int id){

        BaseApiService mApiService = UtilsApi.getApiService();
        Context mContext = this;

        mApiService.cancelPayment(id).enqueue(new Callback<Boolean>() {


            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Booking Canceled", Toast.LENGTH_LONG).show();
                    Intent move = new Intent(DetailRoomActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }


            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(mContext, "Error can't cancel booking", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }



    protected Payment requestGetPayment(int buyerId, int renterId, int roomId){

        BaseApiService mApiService = UtilsApi.getApiService();

        mApiService.getPayment(buyerId, renterId, roomId).enqueue(new Callback<Payment>() {


            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    payment = response.body();
                    mainAlgorithm();
                }
            }


            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                System.out.println(t);
                mainAlgorithm();
            }
        });
        return null;
    }
}