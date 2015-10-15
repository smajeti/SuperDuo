package barqsoft.footballscores.service;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresAdapter;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.widget.LatestScoreWidgetProvider;

/**
 * Created by smajeti on 10/14/15.
 */
public class LatestScoreWidgetIntentService extends IntentService {

    private static final String SORT_ORDER = DatabaseContract.scores_table.TIME_COL + " ASC";

    public LatestScoreWidgetIntentService() {
        super(LatestScoreWidgetIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve all of the Today widget ids: these are the widgets we need to update
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, LatestScoreWidgetProvider.class));

        // Get today's data from the ContentProvider
        Uri latestScoresUri = DatabaseContract.scores_table.buildScoreWithDate();
        String[] formatedTodayDateStr = new String[1];
        formatedTodayDateStr[0] = getFormatedTodayDate(this);

        Cursor data = getContentResolver().query(latestScoresUri, null, null, formatedTodayDateStr, SORT_ORDER);
        if (data == null) {
            return;
        }
        if (!data.moveToFirst()) {
            data.close();
            return;
        }

        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String home_name = "N/A";
        String away_name = "N/A";
        String matchTimeStr = "N/A";
        String score = "N/A";
        while (!data.isAfterLast()) {
            home_name = data.getString(ScoresAdapter.COL_HOME);
            away_name = data.getString(ScoresAdapter.COL_AWAY);
            matchTimeStr = data.getString(ScoresAdapter.COL_MATCHTIME);
            score = Utilies.getScores(data.getInt(ScoresAdapter.COL_HOME_GOALS), data.getInt(ScoresAdapter.COL_AWAY_GOALS));

            String hourStr = matchTimeStr.substring(0, matchTimeStr.lastIndexOf(':'));
            int matchTimeHour = Integer.parseInt(hourStr);
            if (matchTimeHour >= currentHour) {
                break;
            }

            data.moveToNext();
        }

        // Perform this loop procedure for each Today widget
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.latest_score_widget_small);

            views.setTextViewText(R.id.home_name, home_name);
            views.setTextViewText(R.id.away_name, away_name);
            views.setTextViewText(R.id.score_textview, score);
            views.setTextViewText(R.id.match_time_textview, matchTimeStr);

            // Content Descriptions for RemoteViews were only added in ICS MR1
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            //    setRemoteContentDescription(views, "Latest Football Scores");
            //}

            // Create an Intent to launch MainActivity
            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget_layout_id, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        data.close();

    }

//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
//    private void setRemoteContentDescription(RemoteViews views, String description) {
//        views.setContentDescription(R.id.widget_icon, description);
//    }

    private static String getFormatedTodayDate(Context context) {
        Date currentDate = new Date(System.currentTimeMillis());// + 86400000);
        SimpleDateFormat mformat = new SimpleDateFormat(context.getString(R.string.date_format));
        return mformat.format(currentDate);
    }
}
