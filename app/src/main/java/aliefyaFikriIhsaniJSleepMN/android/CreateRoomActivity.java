package aliefyaFikriIhsaniJSleepMN.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import aliefyaFikriIhsaniJSleepMN.android.model.BedType;
import aliefyaFikriIhsaniJSleepMN.android.model.City;
import aliefyaFikriIhsaniJSleepMN.android.model.Facility;
import aliefyaFikriIhsaniJSleepMN.android.model.Room;
import aliefyaFikriIhsaniJSleepMN.android.request.BaseApiService;
import aliefyaFikriIhsaniJSleepMN.android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Kelas CreateRoomActivity merupakan kelas yang berfungsi untuk membuat room baru
 *
 * @author Aliefya Fikri Ihsani
 * @version 1.0
 */

public class CreateRoomActivity extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;

    EditText NameRoom, PriceRoom, AddressRoom, SizeRoom;

    CheckBox cbAC, cbRefrigerator, cbWifi, cbBathub, cbBalcony, cbRestaurant, cbSwimmingPool, cbFitnessCenter;

    Spinner spinnerBed, spinnerCity;

    Button createButton, cancelButton;

    ArrayList<Facility> listFacility = new ArrayList<>();

    /**
     * Method onCreate merupakan method yang pertama kali dijalankan pada saat kelas ini dibuat
     * dan digunakan juga untuk menginisialisasi komponen-komponen yang ada pada layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerBed = findViewById(R.id.spinnerBed);
        spinnerCity.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_dropdown_item, City.values()));
        spinnerBed.setAdapter((new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_dropdown_item, BedType.values())));

        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(v -> { createRoomRequest(); });

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> { finish(); });

    }

    /**
     * Method createRoomRequest digunakan untuk mengirimkan request ke backend untuk membuat room baru
     */


     protected Room createRoomRequest() {

         NameRoom = findViewById(R.id.NameRoom);
         PriceRoom = findViewById(R.id.PriceRoom);
         AddressRoom = findViewById(R.id.AddressRoom);
         SizeRoom = findViewById(R.id.SizeRoom);

         CheckBox cbAC = findViewById(R.id.cbAC);
         CheckBox cbRefrigerator = findViewById(R.id.cbRefrigerator);
         CheckBox cbWifi = findViewById(R.id.cbWifi);
         CheckBox cbBathub = findViewById(R.id.cbBathub);
         CheckBox cbBalcony = findViewById(R.id.cbBalcony);
         CheckBox cbRestaurant = findViewById(R.id.cbRestaurant);
         CheckBox cbSwimmingPool = findViewById(R.id.cbSwimmingPool);
         CheckBox cbFitness = findViewById(R.id.cbFitness);

         double price = Double.parseDouble(PriceRoom.getText().toString());
         int size = Integer.parseInt(SizeRoom.getText().toString());

         spinnerCity = findViewById(R.id.spinnerCity);
         spinnerBed = findViewById(R.id.spinnerBed);
         City city = City.valueOf(spinnerCity.getSelectedItem().toString());
         BedType bedType = BedType.valueOf(spinnerBed.getSelectedItem().toString());

         if (cbAC.isChecked()) {
             listFacility.add(Facility.AC);
         }
         if (cbRefrigerator.isChecked()) {
             listFacility.add(Facility.Refrigerator);
         }
         if (cbWifi.isChecked()) {
             listFacility.add(Facility.WiFi);
         }
         if (cbBathub.isChecked()) {
             listFacility.add(Facility.Bathub);
         }
         if (cbBalcony.isChecked()) {
             listFacility.add(Facility.Balcony);
         }
         if (cbRestaurant.isChecked()) {
             listFacility.add(Facility.Restaurant);
         }
         if (cbSwimmingPool.isChecked()) {
             listFacility.add(Facility.SwimmingPool);
         }
         if (cbFitness.isChecked()) {
             listFacility.add(Facility.FitnessCenter);
         }

         mApiService.createRoom(LoginActivity.accountObject.id, NameRoom.getText().toString(), size, price, listFacility, city, AddressRoom.getText().toString(), bedType).enqueue(new Callback<Room>() {
             @Override
             public void onResponse(Call<Room> call, Response<Room> response) {
                 if (response.isSuccessful()) {
                     Intent intent = new Intent(CreateRoomActivity.this, MainActivity.class);
                     startActivity(intent);
                     Toast.makeText(mContext, "Create room success", Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<Room> call, Throwable t) {
                    Toast.makeText(mContext, "Fail to create room", Toast.LENGTH_SHORT).show();
                 System.out.println(t);

             }
         });

         return null;

     }
}