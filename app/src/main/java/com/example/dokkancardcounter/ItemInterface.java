package com.example.dokkancardcounter;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemInterface {

    @Insert
    void insert(MyDataList items);

    @Delete
    void delete(MyDataList items);

    @Query("UPDATE items SET copies = :copies WHERE ID = :id")
    void update(int id, int copies);

    @Query("SELECT EXISTS (SELECT 1 FROM items WHERE cardID = :cardID)")
    boolean exists(String cardID);

    @Query("SELECT * FROM items")
    List<MyDataList> getItems();
}
