package vojteq.android.courselistfragment.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtil {

    private Activity activity;
    private float dpWidth;
    private float dpHeight;

    public ScreenUtil(Activity activity) {
        this.activity = activity;

        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float density = activity.getResources().getDisplayMetrics().density;
        dpHeight = metrics.heightPixels / density;
        dpWidth = metrics.widthPixels / density;
    }

    public float getDpWidth() {
        return dpWidth;
    }

    public float getDpHeight() {
        return dpHeight;
    }
}
