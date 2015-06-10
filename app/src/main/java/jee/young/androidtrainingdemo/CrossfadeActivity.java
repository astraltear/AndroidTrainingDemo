package jee.young.androidtrainingdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class CrossfadeActivity extends ActionBarActivity {

    private boolean mContentLoaded;
    private View mContentView;
    private View mLoadingView;
    private int mShortAnimationDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossfade);

        mContentView = findViewById(R.id.content);
        mLoadingView = findViewById(R.id.loading_spinner);

        mContentView.setVisibility(View.GONE);

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crossfade, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpTo(this,new Intent(this,AnimationMainActivity.class));
                return true;
            
            case R.id.action_toggle:
                mContentLoaded = !mContentLoaded;
                showContentOrLoadingIndicator(mContentLoaded);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showContentOrLoadingIndicator(boolean contentLoaded) {

        final View showView = contentLoaded ? mContentView : mLoadingView;
        final View hideView = contentLoaded ? mLoadingView : mContentView;

        showView.setAlpha(0f);
        showView.setVisibility(View.VISIBLE);

        showView.animate().alpha(1f).setDuration(mShortAnimationDuration).setListener(null);

        hideView.animate().alpha(0f).setDuration(mShortAnimationDuration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                hideView.setVisibility(View.GONE);
            }
        });
    }
}
