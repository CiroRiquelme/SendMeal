package com.example.sendmeal.dao.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sendmeal.domain.ItemsPedido;

import java.util.List;

@Dao
public interface ItemsPedidoDao {

    @Query("SELECT * FROM itemspedido")
    List<ItemsPedido> getAll();

    @Query("SELECT * FROM ItemsPedido WHERE id_pedido_item=:idPedido")
    List<ItemsPedido> getAllFromPedido(int idPedido);

    @Insert
    void insert(ItemsPedido itemsPedido);


    @Insert
    void insertAllList(List<ItemsPedido> itemsPedido);

    @Insert
    void insertAll(ItemsPedido... itemsPedido);

    @Delete
    void delete(ItemsPedido itemsPedido);

    @Update
    void actualizar(ItemsPedido itemsPedido);
}
