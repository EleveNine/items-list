package com.elevenine.babyneeds.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.elevenine.babyneeds.data.db.ItemEntity;
import com.elevenine.babyneeds.data.db.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository itemRepository;

    private LiveData<ItemEntity> mItem;
    private LiveData<List<ItemEntity>> mAllItems;
    private LiveData<Integer> mItemsCount;

    public ItemViewModel(@NonNull Application application) {
        super(application);

        itemRepository = new ItemRepository(application);
        mAllItems = itemRepository.getAllItems();
    }

    public void insertItem(final ItemEntity itemEntity){
        itemRepository.insertItem(itemEntity);
    }

    public LiveData<ItemEntity> getItem(ItemEntity item){
        return itemRepository.getItem(item);
    }

    public LiveData<List<ItemEntity>> getAllItems(){
        return mAllItems;
    }

    public void updateItem(ItemEntity itemEntity){
        itemRepository.updateItem(itemEntity);
    }

    public void deleteItem(ItemEntity itemEntity){
        itemRepository.deleteItem(itemEntity);
    }

    public LiveData<Integer> getItemsCount(){
        return itemRepository.getItemsCount();
    }
}
