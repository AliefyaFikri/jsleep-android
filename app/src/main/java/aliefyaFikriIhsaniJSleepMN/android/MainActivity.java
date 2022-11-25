package aliefyaFikriIhsaniJSleepMN.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import aliefyaFikriIhsaniJSleepMN.android.model.Account;
import aliefyaFikriIhsaniJSleepMN.android.model.Room;

public class MainActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    public static Account accountObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> listOfNames = new ArrayList<>();
        String run = null;
        try{
            InputStream inputname = getAssets().open("randomRoomList.json");
            int size = inputname.available();
            byte[] newByte = new byte[size];
            inputname.read(newByte);
            inputname.close();
            run = new String(newByte, StandardCharsets.UTF_8);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        
        Type listRoomType = new TypeToken<ArrayList<Room>>() { }.getType();
        ArrayList<Room> roomList = new Gson().fromJson(run, listRoomType);
        for (Room room: roomList){
            listOfNames.add(room.name);
        }

        ListView listView = findViewById(R.id.ListViewMain);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfNames);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menu){
        if (menu.getItemId() == R.id.person_button) {
            Intent aboutMeIntent = new Intent(MainActivity.this, About_Me.class);
            startActivity(aboutMeIntent);
            return true;
        }
        else if(menu.getItemId() == R.id.addbox_button){
            Intent createRoomIntent = new Intent(MainActivity.this, CreateRoomActivity.class);
            startActivity(createRoomIntent);
            return true;
        }
        else{
            return super.onOptionsItemSelected(menu);
        }


    }




}