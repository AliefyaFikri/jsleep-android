package aliefyaFikriIhsaniJSleepMN.android;

import static aliefyaFikriIhsaniJSleepMN.android.MainActivity.accountObject;

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

public class About_Me extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;
    EditText RegisterRenterName, RegisterRenterPhone, RegisterRenterAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        Account account = accountObject;

        mApiService = UtilsApi.getAPIService();
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
/*            RenterNameFill.setText(LoginActivity.getLoggedAccount().store.name);
            RenterAddressFill.setText(LoginActivity.getLoggedAccount().store.address);
            RenterPhoneFill.setText(LoginActivity.getLoggedAccount().store.phoneNumber);
*/
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
/*
        RegisterRenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Renter renter = requestRegisterRenter();
            }
        });


    }
*/

/*    protected Renter requestRegisterRenter(){
        mApiService.registerRenter(accountObject.id, RegisterRenterName.getText().toString(), RegisterRenterAddress.getText().toString(), RegisterRenterPhone.getText().toString()).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(@NonNull Call<Renter> call, @NonNull Response<Renter> response) {
                if (response.isSuccessful()) {
                    Renter renter = response.body();
                    Toast.makeText(mContext, "Register Renter Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(About_Me.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Renter> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
*/}
}