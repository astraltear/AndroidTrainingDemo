package jee.young.androidtrainingdemo.util;

import java.util.List;
import java.util.Map;

public class StatusTracker {
    private Map<String, String> mStatusMap;
    private List<String> mMethodList;
    private static StatusTracker ourInstance = new StatusTracker();
    private static final String STATUS_SUFFIX = "ed";

    public static StatusTracker getInstance() {
        return ourInstance;
    }
}
