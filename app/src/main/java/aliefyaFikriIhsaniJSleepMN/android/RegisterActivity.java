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
import retrofit2.*;


public class RegisterActivity extends AppCompatActivity {

    BaseApiService mApiService;
    EditText username,email,password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.NewUsername);
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
        mApiService.register(username.getText().toString(), email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account;
                    account = response.body();
                    Toast.makeText(mContext, "Register berhasil", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(login);
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