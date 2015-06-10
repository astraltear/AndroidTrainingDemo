package jee.young.androidtrainingdemo;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LayoutChangeActivity extends ActionBarActivity {

    private ViewGroup mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_change);

        mContainerView = (ViewGroup) findViewById(R.id.container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout_change, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpTo(this,new Intent(this,AnimationMainActivity.class));
                return true;

            case R.id.action_add_item:
                findViewById(android.R.id.empty).setVisibility(View.GONE);
                additem();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void additem() {
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.list_item_example, mContainerView, false);

        ((TextView) newView.findViewById(android.R.id.text1)).setText(COUNTRIES[(int) (Math.random() * COUNTRIES.length)]);

        newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainerView.removeView(newView);

                if (mContainerView.getChildCount()==0){
                    findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                }
            }
        });

        mContainerView.addView(newView,0);
    }


    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain",
            "Austria", "Russia", "Poland", "Croatia", "Greece",
            "Ukraine",
    };
}
