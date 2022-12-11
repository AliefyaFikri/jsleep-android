package aliefyaFikriIhsaniJSleepMN.android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import aliefyaFikriIhsaniJSleepMN.android.model.Payment;
import aliefyaFikriIhsaniJSleepMN.android.request.BaseApiService;
import aliefyaFikriIhsaniJSleepMN.android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Kelas PaymentActivity digunakan untuk menampilkan halaman pembayaran
 * halaman ini akan ditampilkan setelah user memilih kamar yang akan dipesan
 *
 * @author Aliefya Fikri Ihsani
 * @version 1.0
 */

public class PaymentActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButtonFrom, dateButtonTo;

    private int index = 0;
    protected static String from, to;

    double roomPrice = DetailRoomActivity.room.price.price;
    double accountBalance = LoginActivity.accountObject.balance;
    long numDays = 0;
    protected static ArrayList<Payment> paymentList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        BaseApiService mApiService = UtilsApi.getApiService();
        Context mContext = this;

        if(paymentList == null){
            paymentList = new ArrayList<>();
        }

        dateButtonFrom = findViewById(R.id.datePickerFrom);
        dateButtonTo = findViewById(R.id.datePickerTo);
        initDatePicker();

        from = getTodaysDate(0);
        dateButtonFrom.setText(from);
        to = getTodaysDate(1);
        dateButtonTo.setText(to);

        dateButtonFrom.setOnClickListener(v -> {
            index = 1;
            datePickerDialog.show();
        });

        dateButtonTo.setOnClickListener(v -> {
            index = 2;
            datePickerDialog.show();
        });

        Button makebookbutton = findViewById(R.id.saveButton_date);
        makebookbutton.setOnClickListener(view -> {
            numDays = calcDays(from, to);
            requestBooking(LoginActivity.accountObject.id, LoginActivity.accountObject.renter.id, DetailRoomActivity.room.id, formatDate(from), formatDate(to));
        });

        updatePrice();
        TextView balanceText = findViewById(R.id.BalanceFill_Payment);
        String balanceString = NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(accountBalance);
        balanceText.setText(balanceString);

    }

    /**
     * Method getTodaysDate ini digunakan untuk mendapatkan tanggal hari ini
     */
    private String getTodaysDate(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, offset);
        int year =cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH);
        month = month + 1;
        int day =cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    /**
     * Method initDatePicker ini digunakan untuk membuat string tanggal
     */
    private void initDatePicker() {

        Context mContext = this;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                if(index == 1){
                    from = date;
                    dateButtonFrom.setText(from);
                }else if(index == 2){
                    String tempTo = to;
                    to = date;
                    if(calcDays(from, to) >= 1 ){
                        dateButtonTo.setText(to);
                        updatePrice();
                    }else{
                        to = tempTo;
                        Toast.makeText(mContext, "Min. 1 day of stay", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };


        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 30L *24*60*60*1000);
    }

    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        switch (month){
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
        }
        return null;
    }

    public String formatDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        Date fDate = null;
        try {
            fDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd");
        assert fDate != null;
        return sdfFormat.format(fDate);
    }

    public long calcDays(String before, String after){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        Date dateBefore = null;
        Date dateAfter = null;
        try {
            dateBefore = sdf.parse(before);
            dateAfter = sdf.parse(after);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeDiff = Math.abs(dateAfter.getTime() - dateBefore.getTime());
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        return daysDiff;
    }

    public void updatePrice(){
        TextView priceText = findViewById(R.id.PriceFill_Payment);
        String priceCurrency = NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(roomPrice * calcDays(from, to));
        priceText.setText(priceCurrency);
    }

    protected Payment requestBooking(int buyerId, int renterId, int roomId, String from, String to){

        BaseApiService mApiService = UtilsApi.getApiService();
        Context mContext = this;

        mApiService.createPayment(buyerId, renterId, roomId,from, to).enqueue(new Callback<Payment>() {

            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    Payment payment;
                    payment = response.body();
                    System.out.println(payment.toString());
                    paymentList.add(payment);
                    Toast.makeText(mContext, "Booking Successful", Toast.LENGTH_LONG).show();
                    Intent move = new Intent(PaymentActivity.this, DetailRoomActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                if(roomPrice * numDays  > accountBalance){
                    Toast.makeText(mContext, "Please top up first", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mContext, "Please book another date", Toast.LENGTH_LONG).show();
                }
            }
        });
        return null;
    }


}

