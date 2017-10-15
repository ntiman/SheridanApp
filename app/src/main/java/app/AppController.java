package app;

/**
 * Created by 1 on 06.12.2016.
 */

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application{

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue RequestQueue;

    private static AppController Instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
    }

    public static synchronized AppController getInstance() {
        return Instance;
    }

    public RequestQueue getRequestQueue() {
        if (RequestQueue == null) {
            RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return RequestQueue;
    }

    public void cancelPendingRequests(Object tag) {
        if (RequestQueue != null) {
            RequestQueue.cancelAll(tag);
        }
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

}
