package aliefyaFikriIhsaniJSleepMN.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import aliefyaFikriIhsaniJSleepMN.android.model.Account;
import aliefyaFikriIhsaniJSleepMN.android.request.BaseApiService;
import aliefyaFikriIhsaniJSleepMN.android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Kelas RegisterActivity digunakan untuk mendaftarkan akun pengguna baru
 */

public class RegisterActivity extends AppCompatActivity {

    BaseApiService mApiService;
    EditText name,email,password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        name = findViewById(R.id.NewUsername);
        email = findViewById(R.id.NewEmail);
        password = findViewById(R.id.NewPassword);

        Button register = findViewById(R.id.RegisterButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Account account = requestRegister();
            }
        });
    }

    protected Account requestRegister(){
        mApiService.register(name.getText().toString(), email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account = response.body();
                    Toast.makeText(mContext, "Register berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Register gagal", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}