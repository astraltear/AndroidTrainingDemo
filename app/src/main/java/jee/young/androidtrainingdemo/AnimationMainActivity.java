package jee.young.androidtrainingdemo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class AnimationMainActivity extends ListActivity {


    public class Sample{
        private CharSequence title;
        private Class<? extends Activity> activityClass;

        public Sample(int titleResId, Class<? extends Activity> activityClass) {
            this.title = getResources().getString(titleResId);
            this.activityClass = activityClass;
        }

        @Override
        public String toString() {
            return title.toString();
        }
    }

    private static Sample[] mSamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main);

        mSamples = new Sample[]{
                new Sample(R.string.title_crossfade,CrossfadeActivity.class),
                new Sample(R.string.title_card_flip,CardFlipActivity.class),
                new Sample(R.string.title_layout_changes,LayoutChangeActivity.class),

        };


        setListAdapter(new ArrayAdapter<Sample>(this,android.R.layout.simple_list_item_1,android.R.id.text1,mSamples));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(  new Intent( AnimationMainActivity.this, mSamples[position].activityClass ));
    }
}
