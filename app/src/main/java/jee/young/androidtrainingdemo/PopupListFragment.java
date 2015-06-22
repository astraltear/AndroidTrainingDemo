package jee.young.androidtrainingdemo;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;



public class PopupListFragment extends ListFragment implements View.OnClickListener {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> items = new ArrayList<String>();

        for (int i=0; i < SampleData.CHEESES.length ; i++ ) {
            items.add(SampleData.CHEESES[i]);
        }

        setListAdapter(new PopupAdapter(items));
    }

    @Override
    public void onListItemClick(ListView listview, View v, int position, long id) {
        String item = (String) listview.getItemAtPosition(position);
        Toast.makeText(getActivity(),"Item Clicked:"+item,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(final View v) {

        v.post(new Runnable() {
            @Override
            public void run() {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {
        final PopupAdapter adapter = (PopupAdapter)getListAdapter();

        final String item = (String) view.getTag();

        PopupMenu popup = new PopupMenu(getActivity(), view);
        popup.getMenuInflater().inflate(R.menu.popup,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_remove:
                        adapter.remove(item);
                        return true;
                }
                return false;
            }
        });

        popup.show();
    }

    class PopupAdapter extends ArrayAdapter<String>{

        public PopupAdapter(ArrayList<String> items) {
            super(getActivity(), R.layout.list_item, android.R.id.text1, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            View popupButton = view.findViewById(R.id.button_popup);

            popupButton.setTag(getItem(position));

            popupButton.setOnClickListener(PopupListFragment.this);

            return view;
        }
    }
}
