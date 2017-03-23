package teamj.application.appforsnaptest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;

import teamj.application.appforsnaptest.constant.DBConstant;
import teamj.application.appforsnaptest.util.DBOpenHelper;
import teamj.application.appforsnaptest.util.DBOperator;

/**
 * Created by Rakesh on 11-12-2016.
 */

public class AssignDriver  extends AppCompatActivity {
    RadioGroup driver_selected;
    Button confirm;
    int request_id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        request_id = getIntent().getIntExtra("R_id",request_id);
        driver_selected = (RadioGroup) findViewById(R.id.selectDriver);
        confirm = (Button) findViewById(R.id.Continue_to_list);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDriver = getCheckedRadio();
                String Driver_id = findDriverDetails(selectedDriver);
                String Vehicle_id = findVehicleID(Driver_id);
                updateReservationTable(Driver_id, Vehicle_id, String.valueOf(request_id));
                    Intent intent = new Intent(v.getContext(),PendingRequestActivity.class);
                    startActivity(intent);

            }
        });

        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public String getCheckedRadio(){
        int radioButtonID = driver_selected.getCheckedRadioButtonId();
        View radioButton = driver_selected.findViewById(radioButtonID);
        int idx = driver_selected.indexOfChild(radioButton);
        RadioButton r = (RadioButton)  driver_selected.getChildAt(idx);
        String selectedtext = r.getText().toString();
        System.out.println("The selected value is " +selectedtext);
        return selectedtext;

    }
    public String findDriverDetails(String name)
    {
        String statusStr = null;
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        String[] columns = new String[] {name}; // parameters
        Cursor res = db.rawQuery("SELECT user_id FROM user where Used_first = ?",columns);
        while (res.moveToNext()) {
            // will go in this loop if there is a row with
            statusStr = res.getString(0);
        }
        return statusStr;
    }

    public String findVehicleID(String user_id)
    {
        String statusStr = null;
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        String[] columns = new String[] {user_id}; // parameters
        Cursor res = db.rawQuery("SELECT V_id FROM vehicle where User_id = ?",columns);
        while (res.moveToNext()) {

            statusStr = res.getString(0);
        }
        return statusStr;

    }
    public void updateReservationTable(String driver_id,String V_id, String request_id)
    {
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("R_id", request_id);
        initialValues.put("V_id", V_id);
        initialValues.put("User_id", driver_id);
        db.insert("Reservation", null, initialValues);



    }

}
