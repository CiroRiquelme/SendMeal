package com.example.sendmeal.dao.db;

import android.content.Context;

import androidx.room.Room;

public class DBClient {

    private static DBClient DB = null;

    private PedidosDB pedidosDB;

    private DBClient(Context ctx){
        pedidosDB = Room.databaseBuilder(ctx,
                PedidosDB.class, "obrapp-db").allowMainThreadQueries().build();
    }

    public synchronized static DBClient getInstance(Context ctx){
        if(DB==null){
            DB = new DBClient(ctx);
        }
        return DB;
    }

    public PedidosDB getPedidosDB() {
        return pedidosDB;
    }
}
