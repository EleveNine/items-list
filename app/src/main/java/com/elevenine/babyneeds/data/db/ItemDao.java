package com.elevenine.babyneeds.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insertItem(ItemEntity itemEntity);

    @Query("SELECT * FROM item_table WHERE id=:itemId")
    LiveData<ItemEntity> getItem(int itemId);

    @Query("SELECT * from item_table ORDER BY id DESC")
    LiveData<List<ItemEntity>> getAllItems();

    @Update
    void updateItem(ItemEntity itemEntity);

    @Delete
    void deleteItem(ItemEntity itemEntity);

    @Query("SELECT COUNT(*) FROM item_table")
    LiveData<Integer> getItemsCount();
}
