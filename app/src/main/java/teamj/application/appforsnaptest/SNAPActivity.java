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
import android.widget.Button;
import android.widget.Toast;

import teamj.application.appforsnaptest.constant.DBConstant;
import teamj.application.appforsnaptest.util.*;
import teamj.application.appforsnaptest.BookingActivity;

/**
 * Created by Rakesh on 12-11-2016.
 */

public class SNAPActivity extends AppCompatActivity implements View.OnClickListener {

    Button bookingBtn,CancelBtn;
    String email;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_layout);
        email = getIntent().getStringExtra("email");
        //String name = getUserName(email);
        if(email!=null)
        Toast.makeText(this, "Hey,"+getUserName(email), Toast.LENGTH_LONG).show();
        bookingBtn = (Button)this.findViewById(R.id.goBook_btn);
        bookingBtn.setOnClickListener(this);
        CancelBtn = (Button)this.findViewById(R.id.goCancel_btn);
        CancelBtn.setOnClickListener(this);


        //copy database file
        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            Intent intent = new Intent(this, SNAPActivity.class);
            intent.putExtra("email", email);
            this.startActivity(intent);

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
        int request_id = getRequestID(email);
        System.out.println("The request ID here is " +request_id);


        if (id==R.id.goBook_btn){
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra("email", email);
            this.startActivity(intent);
        }else if (id==R.id.goCancel_btn){
            Intent intent = new Intent(this, UserRequestList.class);
            intent.putExtra("email", email);
            intent.putExtra("R_id", request_id);
            this.startActivity(intent);
        }
    }

    public int getRequestID(String email)
    {
        int admin = 0;
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        String[] columns = new String[] {email}; // parameters
        Cursor res = db.rawQuery("SELECT R_id FROM request where user_id = ?",columns);
        while (res.moveToNext()) { // will go in this loop if there is a row with the given credentials
            admin = res.getInt(0);
        }

        return admin;
    }
    public String getUserName(String email)
    {
        String userName = "Name";
        Context context = getBaseContext();
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        DBOpenHelper dbHelp = new DBOpenHelper(context,path,2);
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        String[] columns = new String[] {email}; // parameters
        Cursor res = db.rawQuery("SELECT Used_first FROM User where user_id = ?",columns);
        while (res.moveToNext()) { // will go in this loop if there is a row with the given credentials
            userName = res.getString(0);
        }

        return userName;
    }
}
