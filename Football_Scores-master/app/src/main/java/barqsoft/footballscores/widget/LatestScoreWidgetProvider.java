package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import barqsoft.footballscores.service.LatestScoreWidgetIntentService;
import barqsoft.footballscores.service.MyFetchService;

/**
 * Created by smajeti on 10/14/15.
 */
public class LatestScoreWidgetProvider extends AppWidgetProvider {
    private static final String TAG = LatestScoreWidgetProvider.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, MyFetchService.class));
        Log.d(TAG, "onUpdate called");
    }

//    @Override
//    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
//                                          int appWidgetId, Bundle newOptions) {
//        context.startService(new Intent(context, TodayWidgetIntentService.class));
//    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        Log.d(TAG, "onReceive called");
        super.onReceive(context, intent);
        if (MyFetchService.SCORE_DATA_UPDATED.equals(intent.getAction())) {
            context.startService(new Intent(context, LatestScoreWidgetIntentService.class));
        }
    }
}
