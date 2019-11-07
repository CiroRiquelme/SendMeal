package com.example.sendmeal.dao.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sendmeal.domain.ItemsPedido;
import com.example.sendmeal.domain.Pedido;


@Database(entities = {Pedido.class, ItemsPedido.class}, version = 2)
public abstract class PedidosDB extends RoomDatabase {

    public abstract PedidoDao pedidoDao();
    public abstract ItemsPedidoDao itemsPedidoDao();

}
