package com.schoolshieldchild.pushnotification;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by root on 1/7/16.
 */
public class Getmessageservice extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        System.out.println("data.getString(\"message\")-------" + data.getString("message"));

//        if (data.getString("message").contains("has a message for you")) {
//
//        } else {
//            sendOfferNotification(getApplicationContext(), data.getString("random_code"),
//                    data.getString("message"),
//                    data.getString("notification_id"),
//                    data.getString("phone_no"));
//        }

    }


//    private void sendOfferNotification(Context context, String RandomCode,
//                                       String message, String string, String phone_no) {
//        NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(NOTIFICATION_SERVICE);
//        Intent intent = new Intent(context, HomeActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("message", message);
//        bundle.putString("RandomCode", RandomCode);
//        bundle.putString("notification_id", string);
//        bundle.putString("phone_no", phone_no);
//        intent.putExtras(bundle);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
//                | Intent.FLAG_ACTIVITY_NEW_TASK);
//        Random r = new Random();
//        int i11 = r.nextInt(1000 - 1) + 1;
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, i11,
//                intent, 0);
//        Notification notification = new NotificationCompat.Builder(context)
//                .setContentTitle("Bizibuddy").setContentText(message)
//                .setSmallIcon(R.drawable.app_icon).setTicker(message)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setContentIntent(pendingIntent).setAutoCancel(true).build();
//        Random r1 = new Random();
//        int i111 = r1.nextInt(1000 - 1) + 1;
//        notificationManager.notify(i111, notification);
//    }

}
