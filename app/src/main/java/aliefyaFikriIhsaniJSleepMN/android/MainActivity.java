package aliefyaFikriIhsaniJSleepMN.android;

import static aliefyaFikriIhsaniJSleepMN.android.LoginActivity.accountObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import aliefyaFikriIhsaniJSleepMN.android.model.Room;
import aliefyaFikriIhsaniJSleepMN.android.request.BaseApiService;
import aliefyaFikriIhsaniJSleepMN.android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Kelas MainActivity digunakan untuk menampilkan daftar kamar yang tersedia
 * halaman ini merupakan yang ditampilkan setelah user login
 *
 * @author Aliefya Fikri Ihsani
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    public static int selectedPos;
    public static List<Room> roomList = new ArrayList<>();


    EditText Show_Number;
    ListView listView;
    int page = 0;
    int pageSize = 10;

    /**
     * Method onCreate merupakan method yang pertama kali dijalankan pada saat kelas ini dibuat
     * dan digunakan juga untuk menginisialisasi komponen-komponen yang ada pada layout
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseApiService mApiService = UtilsApi.getApiService();
        Context mContext = this;

        Button prevbutton = findViewById(R.id.prevbutton);
        Button nextbutton = findViewById(R.id.nextbutton);
        Button gobutton = findViewById(R.id.gobutton);



        prevbutton.setOnClickListener(v -> {
            if (page > 0) {
                page--;
                Show_Number.setText(String.valueOf(page));
                getRoomList();
            }
        });
        nextbutton.setOnClickListener(v -> {
            page++;
            getRoomList();
        });
        gobutton.setOnClickListener(v -> {
            page = Integer.parseInt(Show_Number.getText().toString());
            getRoomList();
        });

        ListView listView = findViewById(R.id.ListViewMain);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPos = position;
            Intent move = new Intent(mContext, DetailRoomActivity.class);
            startActivity(move);
        });


        /*
        ArrayList<String> listOfNames = new ArrayList<>();
        String run = null;
        try{
            InputStream inputname = getAssets().open("randomRoomList.json");
            int size = inputname.available();
            byte[] newByte = new byte[size];
            inputname.read(newByte);
            inputname.close();
            run = new String(newByte, "UTF-8");
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
    */
    }

    /**
     * Method getRoomList digunakan untuk mengambil data dari backend terkait daftar kamar yang ada
     */

    protected ArrayList<Room> getRoomList() {

        BaseApiService mApiService = UtilsApi.getApiService();
        Context mContext = this;

        Show_Number = findViewById(R.id.Show_Number);
        Show_Number.setText(String.valueOf(page+1));
        listView = findViewById(R.id.ListViewMain);
        String pageNum = Show_Number.getText().toString();
        int pageNumInt = Integer.parseInt(pageNum);

        String pageSizeString = Show_Number.getText().toString();
        int pageSize = 0;
        if (pageSizeString.equals("1")) {
            pageSize = 10;
        } else {
            pageSize = Integer.parseInt(pageSizeString);
        }

        mApiService.getAllRoom(page, pageSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                roomList = response.body();
                if (roomList != null) {
                    ArrayAdapter<Room> adapter = new ArrayAdapter<Room>(mContext, R.layout.list_of_rooms, roomList);
                    listView.invalidateViews();
                    listView.setAdapter(adapter);
                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to Get All Room", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }



    /*
protected List<Room> requestRoom(int page, int pageSize) {

    BaseApiService mApiService = UtilsApi.getApiService();
    Context mContext = this;

    if (page < 0){
        Toast.makeText(mContext, "Can't be made", Toast.LENGTH_SHORT).show();
        return null;
    }
    mApiService.getAllRoom(page, pageSize).enqueue(new Callback<List<Room>>() {
        @Override
        public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
            List<Room> rooms;
            rooms = response.body();
            RoomsAdapter roomsAdapter = new RoomsAdapter(MainActivity.this, (ArrayList<Room>) rooms);
            ListView roomlists = (ListView) findViewById(R.id.list_of_rooms);
            roomlists.setAdapter(roomsAdapter);
            Toast.makeText(mContext, "Berhasil", Toast.LENGTH_SHORT).show();

            roomlists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DetailRoomActivity.currentRoom = (Room) roomlists.getAdapter().getItem(position);
                    Intent move = new Intent(MainActivity.this, DetailRoomActivity.class);
                    startActivity(move);

                }
            });
        }

        @Override
        public void onFailure(Call<List<Room>> call, Throwable t) {
            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            System.out.println(t);
        }
    });
    return null;
}
    */



    public static Room getRoom(){
        return roomList.get(selectedPos);
    }

    /**
     * Method onCreateOptionsMenu digunakan untuk membuat menu pada activity ini
     * @param menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu, menu);
        if(accountObject.renter== null){
            menu.findItem(R.id.addbox_button).setVisible(false);
        }


        return (super.onCreateOptionsMenu(menu));
    }

    /**
     * Method onOptionsItemSelected digunakan untuk mengintent tombol agar masuk ke activity lain
     * @param menu
     */

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
        else if(menu.getItemId() == R.id.search_button){
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            return super.onOptionsItemSelected(menu);
        }


    }




}