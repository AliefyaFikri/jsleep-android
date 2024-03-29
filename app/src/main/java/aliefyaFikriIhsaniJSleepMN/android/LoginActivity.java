package aliefyaFikriIhsaniJSleepMN.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import aliefyaFikriIhsaniJSleepMN.android.model.Account;
import aliefyaFikriIhsaniJSleepMN.android.request.BaseApiService;
import aliefyaFikriIhsaniJSleepMN.android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Kelas LoginActivity merupakan kelas yang berfungsi untuk melakukan login atau tampilan pertama
 *
 * @author  Aliefya Fikri Ihsani
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    static Account accountObject;

    BaseApiService mApiService;
    EditText username,password;
    Context mContext;

    /**
     * Method onCreate digunakan untuk menginisialisasi komponen-komponen yang ada pada layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);

        TextView register = findViewById(R.id.CreateAcc);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        Button buttonLogin = findViewById((R.id.loginButton));
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestLogin();
            }
        });

    }

/*    protected Account requestAccount(){
        mApiService.getAccount(3 ).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()){
                    Account account = response.body();
                    System.out.println(account.toString());
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "No Account Id=0", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }*/

    /**
     * Method requestLogin digunakan untuk melakukan request login ke backend
     */
    protected Account requestLogin(){
        mApiService.login(username.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()){
                    Account account = response.body();
                    LoginActivity.accountObject = account;
                    Toast.makeText(mContext, "Login berhasil", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(login);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Password atau email salah", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}