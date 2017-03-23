package teamj.application.appforsnaptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import teamj.application.appforsnaptest.util.DBOperator;

/**
 * Created by Rakesh on 22-11-2016.
 */

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{
    Button pending;
    Button approved;
    Button rejected;
    Button pickedUp;
    Button noShow;
    EditText requestSearch;
    int reqSearch;
    Button mReport;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_first);
        pending = (Button) this.findViewById(R.id.gopending_btn);
        approved = (Button) this.findViewById(R.id.beapproved);
        rejected = (Button) this.findViewById(R.id.berejected);
        pickedUp = (Button) this.findViewById(R.id.pickup);
        noShow = (Button) this.findViewById(R.id.NoShowList);
        mReport = (Button) this.findViewById(R.id.graphReport);
        requestSearch = (EditText) this.findViewById(R.id.requestSearch);
        requestSearch.setOnClickListener(this);
        pending.setOnClickListener(this);
        approved.setOnClickListener(this);
        rejected.setOnClickListener(this);
        pickedUp.setOnClickListener(this);
        noShow.setOnClickListener(this);
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

    public void onClick(View v)
    {
        int id=v.getId();

        if (id==R.id.gopending_btn){
            Intent intent = new Intent(this, PendingRequestActivity.class);
            this.startActivity(intent);
        }else if (id==R.id.beapproved){
            Intent intent = new Intent(this, ApprovedRequestList.class);
            this.startActivity(intent);
        }
        else if (id==R.id.berejected){
            Intent intent = new Intent(this, RejectedRequestActivity.class);
            this.startActivity(intent);
        }
        else if (id==R.id.pickup){
            Intent intent = new Intent(this, PickedupListActivity.class);
            this.startActivity(intent);
        }
        else if (id==R.id.NoShowList){
            Intent intent = new Intent(this, NoShowListActivity.class);
            this.startActivity(intent);
        }
        else if (id==R.id.requestSearch){
            reqSearch = Integer.parseInt(requestSearch.getText().toString());
            System.out.println("The number entered is" +reqSearch);
            Intent intent = new Intent(this, ApproveRejectRequest.class);
            intent.putExtra("Request_id", reqSearch);
            this.startActivity(intent);
        }
        else if (id==R.id.graphReport){

        }
    }
}
