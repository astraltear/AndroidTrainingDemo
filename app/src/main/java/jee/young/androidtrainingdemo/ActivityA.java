package jee.young.androidtrainingdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import jee.young.androidtrainingdemo.util.StatusTracker;
import jee.young.androidtrainingdemo.util.Utils;


public class ActivityA extends ActionBarActivity {

    String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    private StatusTracker mStatusTracker = StatusTracker.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        mActivityName = getString(R.string.activity_a);
        mStatusView = (TextView) findViewById(R.id.status_view_a);
        mStatusAllView = (TextView) findViewById(R.id.status_view_all_a);


        mStatusTracker.setStatus(mActivityName,getString(R.string.on_create));

        Utils.printStatus(mStatusView,mStatusAllView);
    }
}
