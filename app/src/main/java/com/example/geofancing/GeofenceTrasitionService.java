package com.example.geofancing;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class GeofenceTrasitionService extends IntentService {
    float sum = 0;
    SharedPref sharedPref;
    Context context;
    TextView mTvWallet;
    public static final int GEOFENCE_NOTIFICATION_ID = 0;
    private static final String TAG = GeofenceTrasitionService.class.getSimpleName();

    public GeofenceTrasitionService(Context context) {
        super(TAG);
        this.context = context;
        sharedPref = SharedPref.getInstance(context);
    }

    public GeofenceTrasitionService() {
        super(TAG);
        this.context = context;
        sharedPref = SharedPref.getInstance(context);
    }



    // Handle errors
    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve the Geofencing intent
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "GeofencingEvent error " + geofencingEvent.getErrorCode());
        } else {
            int transaction = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);
            if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_REQ_ID)) {
                Log.d(TAG, "You are inside Tacme");
                Toast.makeText(this, "Inside Geofench.", Toast.LENGTH_SHORT).show();
                addwalet(10);
            } if (transaction == Geofence.GEOFENCE_TRANSITION_EXIT && geofence.getRequestId().equals(Constants.GEOFENCE_REQ_ID)){
                Log.d(TAG, "You are outside Tacme");
                addwalet(-10);
                Toast.makeText(this, "Outside Geofench.", Toast.LENGTH_SHORT).show();
            }
            String geofenceTransitionDetails = getGeofenceTrasitionDetails(transaction, geofences);

            sendNotification(geofenceTransitionDetails);

        }
    }

    private void addwalet(int i) {
        sum = sharedPref.getWallet_balance();
        sum = sum + i;
        Log.d(TAG, "addwalet: " + sum);
        sharedPref.setWallet_balance(sum);

        sendBroadcastMessage(sum);
    }

    public static final String ACTION_LOCATION_BROADCAST = GeofenceTrasitionService.class.getName() + "LocationBroadcast";



    private void sendBroadcastMessage(float sum) {

        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra("sum", sum);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    // Create a detail message with Geofences received
    private String getGeofenceTrasitionDetails(int geoFenceTransition, List<Geofence> triggeringGeofences) {
        // get the ID of each geofence triggered
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesList.add(geofence.getRequestId());
        }

        String status = null;
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER)
            status = "Entering ";
        else if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT)
            status = "Exiting ";
        return status + TextUtils.join(", ", triggeringGeofencesList);
    }

    // Send a notification
    private void sendNotification(String msg) {
        Log.i(TAG, "sendNotification: " + msg);

        // Intent to start the main Activity
        Intent notificationIntent = GeofancingActivity.makeNotificationIntent(
                getApplicationContext(), msg
        );

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Creating and sending Notification
        NotificationManager notificatioMng =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificatioMng.notify(
                GEOFENCE_NOTIFICATION_ID,
                createNotification(msg, notificationPendingIntent));
    }

    // Create a notification
    private Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setColor(Color.RED)
                .setContentTitle(msg)
                .setContentText("Geofence Notification!")
                .setContentIntent(notificationPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }
}