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
import android.widget.TextView;

import java.util.Date;

import teamj.application.appforsnaptest.constant.DBConstant;
import teamj.application.appforsnaptest.util.DBOpenHelper;
import teamj.application.appforsnaptest.util.DBOperator;

/**
 * Created by Rakesh on 10-12-2016.
 */

public class ApproveRejectRequest  extends AppCompatActivity {
    int request_id;
    RadioGroup status;
    TextView request_details;
    TextView request_header;
    Button next_button;
    StringBuffer requestID_Display = new StringBuffer();
    Boolean stat = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reject_layout);
        request_id = getIntent().getIntExtra("Request_id", -1);
        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        status = (RadioGroup) findViewById(R.id.radiostatus);
        request_details = (TextView) findViewById(R.id.Request_Detail);
        request_header = (TextView) findViewById(R.id.Request_ID);
        request_details.setText(getRequest_details(request_id));
        request_header.setText(requestID_Display);
        System.out.println("The number entered is" +request_id);
        System.out.println("The data inside the text view is /n"+request_details);
        next_button = (Button) findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat = getCheckedRadio();
                if(stat)
                {
                    changeStatusToApproved(request_id);
                    Intent intent = new Intent(v.getContext(),AssignDriver.class);
                    intent.putExtra("R_id", request_id);
                    startActivity(intent);
                }
                else
                {
                    changeStatusToRejected(request_id);
                    Intent intent = new Intent(v.getContext(),RejectARequest.class);
                    intent.putExtra("R_id", request_id);
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
            statusStr.append("\n");
            statusStr.append("Status: ");
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
                statusStr.append("\n");
                statusStr.append("Remarks : ");
                System.out.print("THe reason here is" +res.getString(res.getColumnIndex("Remarks")));
                statusStr.append(res.getString(res.getColumnIndex("Remarks")));

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
                statusStr.append("No Show");
            }
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
        if(selectedtext.contains("Ap"))
            return true;
        else
            return false;

    }
    public  void changeStatusToApproved(int req_id)
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
        initialValues.put("R_status_id", "112");


        db.update("Request", initialValues,"R_id = " +request_id,null);


    }

    public  void changeStatusToRejected(int req_id)
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
        initialValues.put("R_status_id", "114");



        db.update("Request", initialValues,"R_id = " +request_id,null);


    }
}
