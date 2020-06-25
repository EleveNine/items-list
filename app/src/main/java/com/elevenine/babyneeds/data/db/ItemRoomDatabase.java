package com.elevenine.babyneeds.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ItemEntity.class}, version = 1, exportSchema = false)
public abstract class ItemRoomDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();

    private static volatile ItemRoomDatabase INSTANCE;

    //Create ThreadPool to run database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ItemRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ItemRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, "item_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
