package teamj.application.appforsnaptest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import teamj.application.appforsnaptest.constant.DBConstant;
import teamj.application.appforsnaptest.util.DBOpenHelper;
import teamj.application.appforsnaptest.util.DBOperator;
import teamj.application.appforsnaptest.view.Weather;
import teamj.application.appforsnaptest.view.WeatherAdapter;

/**
 * Created by Rakesh on 30-11-2016.
 */

public class RejectedRequestActivity extends AppCompatActivity {
    //private ListView listView1;
    ArrayList<Weather> weatherList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("Rejected Requests");
        setContentView(R.layout.main);

        ListView listView1 = (ListView) findViewById(R.id.listView1);


        RequestList();
        WeatherAdapter adapter = new WeatherAdapter(this,
                R.layout.listview_item_row,weatherList);

        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RejectedRequestActivity.this,ApproveRejectRequest.class);
                intent.putExtra("Request_id", weatherList.get(position).R_id);
                startActivity(intent);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            Intent intent = new Intent(this, AdminActivity.class);
            //intent.putExtra("email", email);
            this.startActivity(intent);
            return true;


        case R.id.reset:
            Intent intent1 = new Intent(this, LoginActivity.class);
            //intent.putExtra("email", email);
            this.startActivity(intent1);
            finish();
            return true;
            default:
                return(super.onOptionsItemSelected(item));
    }

    }

    public void RequestList() {
        String request_id = null;
        String status = "114";
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        String[] columns = new String[] { status};
        // parameters
        Cursor res = db.rawQuery("SELECT * FROM Request WHERE R_status_id=?", columns);


        while (res.moveToNext()) {// will go in this loop if there is a row with the given credentials
            Weather entry = new Weather(res.getInt(res.getColumnIndex("R_id")), res.getString(res.getColumnIndex("Pick_up_location")), res.getString(res.getColumnIndex("Destination")));

            weatherList.add(entry);
        }

    }
}




