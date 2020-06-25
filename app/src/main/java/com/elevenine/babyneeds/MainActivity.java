package com.elevenine.babyneeds;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elevenine.babyneeds.model.ItemFoo;
import com.elevenine.babyneeds.ui.ListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQty;
    private EditText itemColor;
    private EditText itemSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        //check if db is not empty -> start from ListActivity
        byPassActivity();


    }

    private void byPassActivity() {
    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        babyItem = view.findViewById(R.id.item_editText);
        itemQty = view.findViewById(R.id.itemQty_editText);
        itemColor = view.findViewById(R.id.itemDescription_editText);
        saveButton = view.findViewById(R.id.save_button);

        builder.setView(view);
        dialog = builder.create(); //create the alertDialog
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!babyItem.getText().toString().trim().isEmpty()
                && !itemColor.getText().toString().trim().isEmpty()
                && !itemQty.getText().toString().trim().isEmpty()
                && !itemSize.getText().toString().trim().isEmpty())
                    saveItem(v);

                else
                    Snackbar.make(v, "Empty fields are not allowed!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void saveItem(View view) {
        //Todo: save each item to db
        ItemFoo item = new ItemFoo();
        item.setItemName(babyItem.getText().toString().trim());
        item.setItemColor(itemColor.getText().toString().trim());
        item.setItemSize(Integer.parseInt(itemSize.getText().toString().trim()));
        item.setItemQuantity(Integer.parseInt(itemQty.getText().toString().trim()));


        Snackbar.make(view, "Item added!", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
