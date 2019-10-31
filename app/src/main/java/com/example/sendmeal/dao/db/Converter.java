package com.example.sendmeal.dao.db;

import androidx.room.TypeConverter;

import com.example.sendmeal.domain.EstadoPedido;
import com.example.sendmeal.domain.Pedido;

import java.util.Date;

public class Converter {
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public String estadoPedidoToString(EstadoPedido estadoPedido){
        return estadoPedido.toString();
    }

    @TypeConverter
    public EstadoPedido stringToEstadoPedido(String estadoPedidoString){
        return EstadoPedido.valueOf(estadoPedidoString);
    }



}
