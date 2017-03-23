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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import teamj.application.appforsnaptest.constant.DBConstant;
import teamj.application.appforsnaptest.util.DBOpenHelper;
import teamj.application.appforsnaptest.util.DBOperator;

/**
 * Created by Rakesh on 12-12-2016.
 */

public class RejectARequest extends AppCompatActivity implements View.OnClickListener  {

    Button mConfirm;
    int request_id;
    EditText request_reason;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject);

        mConfirm = (Button) this.findViewById(R.id.confirmReject_btn);
        mConfirm.setOnClickListener(this);
        request_id = getIntent().getIntExtra("R_id",0);
        System.out.println("The R ID is "+request_id);
        request_reason = (EditText) findViewById(R.id.reject_reason);



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
            this.startActivity(intent1);
            finish();
            return true;
            default:
                return(super.onOptionsItemSelected(item));




    }

    }


    public void onClick(View v)
    {
        int id=v.getId();

        if (id==R.id.confirmReject_btn){

            updateReason(request_reason.getText().toString());
            Intent intent = new Intent(this, AdminActivity.class);
            this.startActivity(intent);
        }
    }

    public void updateReason( String reason)
    {
        System.out.println("The reason is "+reason);
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        Long x = System.currentTimeMillis();
        Date date = new Date(x);

        String[] columns = new String[] {String.valueOf(request_id)};

        // parameters
        //Cursor res = db.rawQuery("SELECT * FROM Request WHERE User_id=?", columns);
        ContentValues initialValues = new ContentValues();
        /*initialValues.put("R_status_id", "114");*/
        initialValues.put("Remarks",reason);

        int r = db.update("Request", initialValues,"R_id = " +request_id,null);
        System.out.println("the count of the update is "+r);

    }

    /*public String getRequest_details(int request_id) {
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
            statusStr.append("\n");
            statusStr.append("Status : ");
            String stat = res.getString(res.getColumnIndex("R_status_id"));
            if(stat.contains("112"))
            {
                statusStr.append("Approved");
            }
            else if(stat.contains("113"))
            {
                statusStr.append("Pending");
            }
            else if(stat.contains("114"))
            {
                statusStr.append("Rejected");
            }
            else if(stat.contains("115"))
            {
                statusStr.append("Cancelled");
            }
            else if(stat.contains("116"))
            {
                statusStr.append("Picked Up");
            }
            else if(stat.contains("117"))
            {
                statusStr.append("Picked Up");
            }
        }
        return statusStr.toString();
    }
    public String getRequest_details_with_email(String email)
    {
        StringBuffer statusStr = new StringBuffer();
        requestID_Display.append("Request ID: ");

        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        String[] columns = new String[] {email}; // parameters
        Cursor res = db.rawQuery("SELECT * FROM request where User_id = ?",columns);
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
    }*/

}
