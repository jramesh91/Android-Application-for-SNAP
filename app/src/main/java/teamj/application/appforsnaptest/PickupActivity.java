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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Date;

import teamj.application.appforsnaptest.constant.DBConstant;
import teamj.application.appforsnaptest.util.DBOpenHelper;
import teamj.application.appforsnaptest.util.DBOperator;

/**
 * Created by Rakesh on 12-12-2016.
 */

public class PickupActivity  extends AppCompatActivity {
    int request_id;
    RadioGroup status;
    TextView request_details;
    TextView request_header;
    Button next_button;
    StringBuffer requestID_Display = new StringBuffer();
    Boolean stat = false;
    String email;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);
        request_id = getIntent().getIntExtra("Request_id", -1);
        email = getIntent().getStringExtra("email");
        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        status = (RadioGroup) findViewById(R.id.radiostatus_pickup);
        request_details = (TextView) findViewById(R.id.Request_Detail_Pickup);
        request_header = (TextView) findViewById(R.id.Request_ID_Pickup);
        request_details.setText(getRequest_details(request_id));
        request_header.setText(requestID_Display);
        next_button = (Button) findViewById(R.id.next_button_Pickup);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat = getCheckedRadio();
                if(stat)
                {
                    changeStatusToPickedUp(request_id);
                    Intent intent = new Intent(v.getContext(),DriverFirstActivity.class);
                    intent.putExtra("Request_id", request_id);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }
                else
                {
                    changeStatusToNoShow(request_id);
                    Intent intent = new Intent(v.getContext(),DriverFirstActivity.class);
                    intent.putExtra("Request_id", request_id);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }
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
            Intent intent = new Intent(this, DriverFirstActivity.class);
            this.startActivity(intent);
            return true;


        case R.id.reset:
            Intent intent1 = new Intent(this, LoginActivity.class);
            this.startActivity(intent1);
            finish();
            return true;
            default:
                return(super.onOptionsItemSelected(item));
              }

    }

    public String getRequest_details(int request_id) {
        StringBuffer statusStr = new StringBuffer();
        requestID_Display.append("Request ID: ");

        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        String[] columns = new String[] {Integer.toString(request_id)}; // parameters
        Cursor res = db.rawQuery("SELECT * FROM request where R_id = ?",columns);
        while (res.moveToNext()) { // will go in this loop if there is a row with the given credentials

            requestID_Display.append(Integer.toString(res.getInt(res.getColumnIndex("R_id"))));
            statusStr.append("\n");
            statusStr.append("From: ");
            statusStr.append(res.getString(res.getColumnIndex("Pick_up_location")));
            statusStr.append("\n");
            statusStr.append("To: ");
            statusStr.append(res.getString(res.getColumnIndex("Destination")));
            statusStr.append("\n");
            statusStr.append("Time: ");
            statusStr.append(res.getString(res.getColumnIndex("R_Date")));
            statusStr.append("\n");
            statusStr.append("Number of Passengers: ");
            statusStr.append(res.getString(res.getColumnIndex("No_of_passangers")));
        }
        return statusStr.toString();
    }
    public boolean getCheckedRadio(){
        int radioButtonID = status.getCheckedRadioButtonId();
        //System.out.println("The number is "+radioButtonID);
        View radioButton = status.findViewById(radioButtonID);
        int idx = status.indexOfChild(radioButton);
        RadioButton r = (RadioButton)  status.getChildAt(idx);
        String selectedtext = r.getText().toString();
        if(selectedtext.contains("Pick"))
            return true;
        else
            return false;

    }

    public  void changeStatusToPickedUp(int req_id)
    {
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        Long x = System.currentTimeMillis();
        Date date = new Date(x);

        String[] columns = new String[] {String.valueOf(req_id)};

        // parameters
        //Cursor res = db.rawQuery("SELECT * FROM Request WHERE User_id=?", columns);
        ContentValues initialValues = new ContentValues();
        initialValues.put("R_status_id", "116");


        db.update("Request", initialValues,"R_id = " +request_id,null);


    }

    public  void changeStatusToNoShow(int req_id)
    {
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        Long x = System.currentTimeMillis();
        Date date = new Date(x);

        String[] columns = new String[] {String.valueOf(req_id)};

        // parameters
        //Cursor res = db.rawQuery("SELECT * FROM Request WHERE User_id=?", columns);
        ContentValues initialValues = new ContentValues();
        initialValues.put("R_status_id", "117");


        db.update("Request", initialValues,"R_id = " +request_id,null);


    }
}