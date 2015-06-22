package jee.young.androidtrainingdemo;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import jee.young.androidtrainingdemo.content.ContentItem;


public class ShareActionActivity extends ActionBarActivity {

    private final ArrayList<ContentItem> mItems = getSampleContent();

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_action);

        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setOnPageChangeListener(mOnPageChangeListener);
        vp.setAdapter(mPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share_action, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_share);

        mShareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(shareItem);

        int currentViewPagerItem = ((ViewPager) findViewById(R.id.viewpager)).getCurrentItem();
        setShareIntent(currentViewPagerItem);

        return super.onCreateOptionsMenu(menu);
    }



    private final PagerAdapter mPagerAdapter = new PagerAdapter() {

        LayoutInflater minflater;

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(minflater == null) {
                minflater = LayoutInflater.from(ShareActionActivity.this);
            }

            final ContentItem item = mItems.get(position);

            switch ( item.contentType ){
                case ContentItem.CONTENT_TYPE_TEXT: {
                    TextView tv = (TextView) minflater.inflate(R.layout.item_text, container, false);

                    tv.setText(item.contentResourceId);
                    container.addView(tv);
                    return tv;
                }

                case ContentItem.CONTENT_TYPE_IMAGE: {
                    ImageView iv = (ImageView) minflater.inflate(R.layout.item_image, container, false);
                    iv.setImageURI(item.getContentUri());
                    container.addView(iv);
                    return iv;

                }
            }
            return null;
        }
    };

    private void setShareIntent(int position){
        if(mShareActionProvider != null) {
            ContentItem item = mItems.get(position);
            Intent shareIntent = item.getShareIntent(ShareActionActivity.this);

            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setShareIntent(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    static ArrayList<ContentItem> getSampleContent() {
        ArrayList<ContentItem> items = new ArrayList<>();

        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "01.png"));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "02.png"));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "03.png"));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, R.string.share_text));

        return items;
    }


}
