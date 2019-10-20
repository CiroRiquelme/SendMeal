package com.example.sendmeal.Utilidades;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sendmeal.AltaPlatosActivity;
import com.example.sendmeal.HomeActivity;
import com.example.sendmeal.R;
import com.example.sendmeal.dao.PlatoRepository;

public class MyReceiver extends BroadcastReceiver {

    public static final String EVENTO_01="EVENTO_01";
    private static final String CANAL_MENSAJES_ID = "1";
    Context contexto;


    @Override
    public void onReceive(Context context, Intent intent) {
        contexto=context;

        Integer position = intent.getExtras().getInt("indice");

        Intent destino = new Intent(context, AltaPlatosActivity.class);
        destino.putExtra("indiceOferta",position);
        destino.setAction("OFERTA");

        Integer desPos = destino.getExtras().getInt("indiceOferta");


        //Leer la utilidad de la FLAG
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 ,destino, PendingIntent.FLAG_UPDATE_CURRENT);

        createNotificationChannel();
        NotificationCompat.Builder mBuilder = new
                NotificationCompat.Builder(context,CANAL_MENSAJES_ID)
                .setSmallIcon(R.drawable.hamburguesa)
                .setContentTitle("SendMeal")
                .setContentText("Oferta en plato "+ PlatoRepository.getInstance().getListaPlatos().get(desPos).getTitulo()+ " Aprovecha!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);



        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(99,mBuilder.build());


    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            CharSequence name = "canal 1";
            String description= "el canal 1";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=
                    new NotificationChannel(CANAL_MENSAJES_ID, name, importance);
                    channel.setDescription(description);
                    NotificationManager notificationManager = contexto.getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
        }
    }
}
