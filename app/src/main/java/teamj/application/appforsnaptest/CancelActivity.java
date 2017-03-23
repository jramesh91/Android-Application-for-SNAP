package teamj.application.appforsnaptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import teamj.application.appforsnaptest.BookingActivity;
import teamj.application.appforsnaptest.LoginActivity;
import teamj.application.appforsnaptest.R;
import teamj.application.appforsnaptest.util.DBOperator;

/**
 * Created by Rakesh on 16-11-2016.
 */

public class CancelActivity  extends AppCompatActivity implements View.OnClickListener {
    Button CancelBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);
        CancelBtn = (Button)this.findViewById(R.id.Cancel_request);
        CancelBtn.setOnClickListener(this);


        //copy database file
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

    public void onClick(View v)
    {
        int id=v.getId();

        if (id==R.id.Cancel_request){
            Intent intent = new Intent(this, BookingActivity.class);
            //intent.putExtra("email", email);
            this.startActivity(intent);
        }
    }
}

