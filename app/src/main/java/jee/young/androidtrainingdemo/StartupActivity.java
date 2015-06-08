package jee.young.androidtrainingdemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class StartupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
    }

    public void go_callChild(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void go_lifecycle(View view) {
        Intent intent = new Intent(this, ActivityA.class);
        startActivity(intent);
    }

    public void go_createfragment(View view) {
        Intent intent = new Intent(this, CreateFragmentActivity.class);
        startActivity(intent);
    }



}
