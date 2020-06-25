package com.elevenine.babyneeds.data.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {
    private ItemDao itemDao;
    private LiveData<ItemEntity> mItem;
    private LiveData<List<ItemEntity>> mAllItems;
    private LiveData<Integer> mItemsCount;

    public ItemRepository(Application application){
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        itemDao = db.itemDao();
        mAllItems = itemDao.getAllItems();
    }

    public void insertItem(final ItemEntity itemEntity){
        if(itemEntity != null)
            // Write operations are performed only on the background threads
            ItemRoomDatabase.databaseWriteExecutor.execute(() -> {
                itemDao.insertItem(itemEntity);
            });
    }

    public LiveData<ItemEntity> getItem(ItemEntity item){
        return itemDao.getItem(item.getId());
    }

    public LiveData<List<ItemEntity>> getAllItems(){
        return mAllItems;
    }

    public void updateItem(ItemEntity itemEntity){
        ItemRoomDatabase.databaseWriteExecutor.execute(() -> {
            itemDao.updateItem(itemEntity);
        });
    }

    public void deleteItem(ItemEntity itemEntity){
        ItemRoomDatabase.databaseWriteExecutor.execute(() -> {
            itemDao.deleteItem(itemEntity);
        });
    }

    public LiveData<Integer> getItemsCount(){
        return itemDao.getItemsCount();
    }
}
