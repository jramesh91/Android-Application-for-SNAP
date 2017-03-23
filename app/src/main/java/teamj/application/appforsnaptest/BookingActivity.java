package teamj.application.appforsnaptest;

/**
 * Created by Rakesh on 12-11-2016.
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import teamj.application.appforsnaptest.constant.DBConstant;
import teamj.application.appforsnaptest.constant.SQLCommand;
import teamj.application.appforsnaptest.util.DBOpenHelper;
import teamj.application.appforsnaptest.util.DBOperator;

public class BookingActivity  extends AppCompatActivity implements View.OnClickListener  {

    Button mBookButton;
    int requesToPass;
    Button mCancelButton;
    public static String loginID;
    private EditText mSource;
    private EditText mDestination;
    private EditText mSourceZip;
    private EditText mDestinationZip;
    private Spinner numberOfPassenger;
    String passengerCount;
    String email;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        mSource = (EditText) findViewById(R.id.source_edittext);
        mDestination = (EditText) findViewById(R.id.destination_edittext);
        mSourceZip = (EditText) findViewById(R.id.Source_Zip_edittext);
        mDestinationZip = (EditText) findViewById(R.id.DestinationZip_edittext);
        mBookButton = (Button) this.findViewById(R.id.book_btn);
        mBookButton.setOnClickListener(this);
        mCancelButton = (Button) this.findViewById(R.id.cancel_btn);
        mCancelButton.setOnClickListener(this);
        numberOfPassenger = (Spinner) findViewById(R.id.spinner);
        numberOfPassenger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        email = getIntent().getStringExtra("email");

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
            Intent intent = new Intent(this, SNAPActivity.class);
            intent.putExtra("email", email);
            this.startActivity(intent);
            return true;


        case R.id.reset:
            Intent intent1 = new Intent(this, LoginActivity.class);
            //intent.putExtra("email", email);
            this.startActivity(intent1);
            finish();
            return true;


        default:
            return super.onOptionsItemSelected(item);
    }

    }

    public void onClick(View v)
    {
        int id=v.getId();

        if (id==R.id.book_btn){
            passengerCount = String.valueOf(numberOfPassenger.getSelectedItem());
            createRequest();
            Intent intent = new Intent(this, RequestStatus.class);
            intent.putExtra("email", email);
            System.out.println("The request ID is"+requesToPass);
            intent.putExtra("R_id", requesToPass);
            this.startActivity(intent);
        }else if (id==R.id.goCancel_btn){
            Intent intent = new Intent(this, CancelActivity.class);
            this.startActivity(intent);
        }
    }
    public void createRequest()
    {
        StringBuffer sourcebuffer = new StringBuffer();
        StringBuffer destBuffer = new StringBuffer();
        String source = mSource.getText().toString();
        String destination = mDestination.getText().toString();
        String source_zip = mSourceZip.getText().toString();
        String dest_zip = mDestinationZip.getText().toString();
        sourcebuffer.append(source);
        sourcebuffer.append(" - ");
        sourcebuffer.append(source_zip);
        destBuffer.append(destination);
        destBuffer.append(" - ");
        destBuffer.append(dest_zip);
        String mSource = sourcebuffer.toString();
        String mDestination = destBuffer.toString();
        InsertIntoRequestTable(mSource,mDestination);

    }

    public void InsertIntoRequestTable(String source, String destination)
    {
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        Long x = System.currentTimeMillis();
        Date date = new Date(x);

        String admin = getAdmin();

        Random r = new Random();

        int requestId = r.nextInt(1000);
        requesToPass = requestId;

        String[] columns = new String[] {String.valueOf(requestId), source, destination,"113",date.toString(),email, admin, "1"};
        // parameters


        ContentValues initialValues = new ContentValues();
        initialValues.put("R_id", requestId); // the execution is different if _id is 2
        initialValues.put("Pick_up_location", source);
        initialValues.put("Destination", destination);
        initialValues.put("R_status_id", "113");
        initialValues.put("R_Date", date.toString().substring(0,20));
        initialValues.put("User_id", email);
        initialValues.put("No_of_passangers", passengerCount);

        int id = (int) db.insert("Request", null, initialValues);
        Toast.makeText(getBaseContext(), "Your request ID is" +id, Toast.LENGTH_LONG).show();

       /* while (res.moveToNext()) { // will go in this loop if there is a row with the given credentials
            userRole = res.getString(0);
        }*/
        //Log.d("The insert code is ",res.getString(0));
    }
    public String getAdmin() {
        String admin = "Student";
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        //String[] columns = new String[] {}; // parameters
        Cursor res = db.rawQuery("SELECT Admin_id FROM admin",null);
        while (res.moveToNext()) { // will go in this loop if there is a row with the given credentials
            admin = res.getString(0);
        }

        return admin;
    }



}
