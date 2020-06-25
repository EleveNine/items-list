package com.elevenine.babyneeds.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elevenine.babyneeds.R;
import com.elevenine.babyneeds.data.db.ItemEntity;
import com.elevenine.babyneeds.databinding.ActivityListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";
    private ItemViewModel itemViewModel;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton floatingActionButton;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQty;
    private EditText itemDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);

        recyclerView = binding.recyclerview;
        binding.setLifecycleOwner(this);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this, itemViewModel);
        recyclerView.setAdapter(recyclerViewAdapter);

        itemViewModel.getAllItems().observe(this, new Observer<List<ItemEntity>>() {
            @Override
            public void onChanged(List<ItemEntity> itemEntities) {
                recyclerViewAdapter.setItemsList(itemEntities);
            }
        });

        floatingActionButton = binding.fabList;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });
    }

    private void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        babyItem = view.findViewById(R.id.item_editText);
        itemQty = view.findViewById(R.id.itemQty_editText);
        itemDescription = view.findViewById(R.id.itemDescription_editText);
        saveButton = view.findViewById(R.id.save_button);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!babyItem.getText().toString().trim().isEmpty()
                        && !itemDescription.getText().toString().trim().isEmpty()
                        && !itemQty.getText().toString().trim().isEmpty())
                    saveItem(v);

                else
                    Snackbar.make(v, "Empty fields are not allowed!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void saveItem(View view) {
        ItemEntity item = new ItemEntity();
        item.setItemName(babyItem.getText().toString().trim());
        item.setItemDescription(itemDescription.getText().toString().trim());
        item.setItemQuantity(Integer.parseInt(itemQty.getText().toString().trim()));

        //convert Timestamp to a readable form
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(java.lang.System.currentTimeMillis()));
        item.setDateItemAdded(formattedDate);

        itemViewModel.insertItem(item);

        Snackbar.make(view, "Item added!", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();

            }
        }, 500);

    }
}
