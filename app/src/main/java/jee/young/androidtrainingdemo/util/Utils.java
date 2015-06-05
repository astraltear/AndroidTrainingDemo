package jee.young.androidtrainingdemo.util;

import android.os.Handler;
import android.widget.TextView;

import java.util.List;


public class Utils {
        List<String> listMethods = mStatusTracker.getMethodList();

        private static StatusTracker mStatusTracker = StatusTracker.getInstance();

        public static void printStatus(final TextView viewMethods, final TextView viewStatus) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    StringBuilder stringBuilder = new StringBuilder();
                    List<String> listMethods = mStatusTracker.getMethodList();
                    for (String method : listMethods) {
                        stringBuilder.insert(0, method + "\r\n");
                    }

                    if(viewMethods != null){
                        viewMethods.setText(stringBuilder.toString());
                    }

                    StringBuilder sbStatus = new StringBuilder();

                    for (String key:mStatusTracker.keySet()) {
                        sbStatus.insert(0, key + ":" + mStatusTracker.getStatus(key) + "\n");
                    }

                    if (viewStatus != null){
                        viewStatus.setText(sbStatus.toString());
                    }
                }
            },750);
    }
}
