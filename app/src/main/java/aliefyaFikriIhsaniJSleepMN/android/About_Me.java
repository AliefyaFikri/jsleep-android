package aliefyaFikriIhsaniJSleepMN.android;

import static aliefyaFikriIhsaniJSleepMN.android.LoginActivity.accountObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import aliefyaFikriIhsaniJSleepMN.android.model.Account;
import aliefyaFikriIhsaniJSleepMN.android.model.Renter;
import aliefyaFikriIhsaniJSleepMN.android.request.BaseApiService;
import aliefyaFikriIhsaniJSleepMN.android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Kelas About Me digunakan untuk menampilkan informasi akun pengguna, serta informasi renter
 * jika pengguna memiliki renter
 *
 * @author  Aliefya Fikri Ihsani
 * @version 1.0
 */

public class About_Me extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;
    EditText RegisterRenterName, RegisterRenterPhone, RegisterRenterAddress;

    /**
     * Method onCreate digunakan untuk menginisialisasi komponen-komponen yang ada pada layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        Account account = accountObject;

        mApiService = UtilsApi.getApiService();
        mContext = this;

        TextView username = findViewById(R.id.Name);
        TextView email = findViewById(R.id.Email);
        TextView balance = findViewById(R.id.Balance);

        username.setText(account.name);
        email.setText(account.email);
        balance.setText(String.valueOf(account.balance));
        Button RegisterRenterButton = findViewById(R.id.ButtonRegisterRenter);

        EditText TopUp = findViewById(R.id.AmountFill);
        Button TopUpButton = findViewById(R.id.TopUpButton);

        Button RegisterRenter = findViewById(R.id.RegisterRenter);
        CardView registerRenterCard = findViewById(R.id.RegisterRenterCardView);
        RegisterRenterName = findViewById(R.id.RegisterRenterName);
        RegisterRenterAddress = findViewById(R.id.RegisterRenterAddress);
        RegisterRenterPhone = findViewById(R.id.RegisterPhoneNumber);

        Button CancelRenterButton = findViewById(R.id.ButtonCancelRenter);
        TextView RenterNameText = findViewById(R.id.RenterNameText);
        TextView RenterAddressText = findViewById(R.id.RenterAddressText);
        TextView RenterPhoneText = findViewById(R.id.RenterPhoneNumberText);
        TextView RenterNameFill = findViewById(R.id.RenterNameFill);
        TextView RenterAddressFill = findViewById(R.id.RenterAddressFill);
        TextView RenterPhoneFill = findViewById(R.id.RenterPhoneNumberFill);

        if (account.renter == null) {
            RegisterRenter.setVisibility(View.VISIBLE);
            registerRenterCard.setVisibility(View.INVISIBLE);
        } else {
            RegisterRenter.setVisibility(View.INVISIBLE);
            registerRenterCard.setVisibility(View.VISIBLE);
            RegisterRenterName.setVisibility(View.INVISIBLE);
            RegisterRenterAddress.setVisibility(View.INVISIBLE);
            RegisterRenterPhone.setVisibility(View.INVISIBLE);
            RegisterRenterButton.setVisibility(View.INVISIBLE);
            CancelRenterButton.setVisibility(View.INVISIBLE);
            RenterNameFill.setText(account.renter.username);
            RenterAddressFill.setText(account.renter.address);
            RenterPhoneFill.setText(account.renter.phoneNumber);

        }

        RegisterRenter.setOnClickListener(v -> {
            RegisterRenter.setVisibility(View.INVISIBLE);
            registerRenterCard.setVisibility(View.VISIBLE);
            RenterNameText.setVisibility(View.INVISIBLE);
            RenterAddressText.setVisibility(View.INVISIBLE);
            RenterPhoneText.setVisibility(View.INVISIBLE);
            RenterNameFill.setVisibility(View.INVISIBLE);
            RenterAddressFill.setVisibility(View.INVISIBLE);
            RenterPhoneFill.setVisibility(View.INVISIBLE);
        });

        CancelRenterButton.setOnClickListener(v -> {
            RenterNameText.setText("");
            RenterAddressText.setText("");
            RenterPhoneText.setText("");
            registerRenterCard.setVisibility(View.INVISIBLE);
            RegisterRenter.setVisibility(View.VISIBLE);
        });

        RegisterRenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Renter renter = requestRegisterRenter();
            }
        });

        TopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(TopUp.getText().toString());
                requestTopUp(amount);
            }
        });


    }

    /**
     * Method requestRegisterRenter digunakan untuk mengirimkan request register renter ke backend
     */

    protected Renter requestRegisterRenter(){
    mApiService.registerRenter(accountObject.id, RegisterRenterName.getText().toString(), RegisterRenterAddress.getText().toString(), RegisterRenterPhone.getText().toString()).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(@NonNull Call<Renter> call, @NonNull Response<Renter> response) {
                if (response.isSuccessful()) {

                    Renter renter = response.body();
                    accountObject.renter = renter;
                    Toast.makeText(mContext, "Register Renter Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(About_Me.this, MainActivity.class);
                    startActivity(intent);
                } else {

                        Toast.makeText(mContext, "Fail to Register Renter" , Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<Renter> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Fail to Register Renter", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
        return null;

    }

    /**
     * Method requestTopUp digunakan untuk mengirimkan request top up ke backend
     */

    protected void requestTopUp(int amount) {
        mApiService.topUp(accountObject.id, amount).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call, @NonNull Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account = response.body();
                    accountObject.balance = account.balance;
                    Toast.makeText(mContext, "Top Up Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(About_Me.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Fail to Top Up", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Fail to Top Up", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });

    }



}