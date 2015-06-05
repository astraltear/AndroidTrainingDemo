package jee.young.androidtrainingdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ActivityA extends ActionBarActivity {

    String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    private StatusTracker

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
    }
}
