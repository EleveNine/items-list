package com.elevenine.babyneeds.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elevenine.babyneeds.R;
import com.elevenine.babyneeds.data.db.ItemEntity;
import com.elevenine.babyneeds.databinding.ListRowBinding;
import com.elevenine.babyneeds.databinding.PopupBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ItemEntity> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;
    private ItemViewModel viewModel;

    public RecyclerViewAdapter(Context context, ItemViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setItemsList(List<ItemEntity> list){
        itemList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListRowBinding binding = ListRowBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemEntity item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        if(itemList == null)
            return 0;
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ListRowBinding listRowBinding;
        private final ViewGroup parent;

        public ImageButton deleteButton;
        public ImageButton editButton;
        public int id;

        public ViewHolder(ListRowBinding listRowBinding, ViewGroup parent) {
            super(listRowBinding.getRoot());
            this.listRowBinding = listRowBinding;
            this.parent = parent;

            deleteButton = listRowBinding.deleteButton;
            editButton = listRowBinding.editButton;

            deleteButton.setOnClickListener(this);
            editButton.setOnClickListener(this);
        }

        public void bind(ItemEntity item) {
            listRowBinding.setItemEntity(item);
            listRowBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int pos;

            switch (v.getId()) {
                case R.id.edit_button:
                    pos = getAdapterPosition();
                    ItemEntity itemToEdit = itemList.get(pos);
                    editItem(itemToEdit, parent);
                    break;
                case R.id.delete_button:
                    pos = getAdapterPosition();
                    ItemEntity itemToDelete = itemList.get(pos);
                    deleteItem(itemToDelete);
                    break;
            }
        }

        private void editItem(final ItemEntity newItem, final ViewGroup parent) {

            PopupBinding popupBinding;
            Button saveButton;
            final EditText itemName;
            final EditText itemQty;
            final EditText itemDescription;
            TextView titleTextView;

            builder = new AlertDialog.Builder(parent.getContext());
            inflater = LayoutInflater.from(parent.getContext());

            popupBinding = PopupBinding.inflate(inflater, parent, false);
            popupBinding.setNewItem(newItem);

            View view = popupBinding.getRoot();

            itemName = popupBinding.itemEditText;
            itemQty = popupBinding.itemQtyEditText;
            itemDescription = popupBinding.itemDescriptionEditText;
            saveButton = popupBinding.saveButton;
            titleTextView = popupBinding.title;

            //fill fields of the alert dialog popup with the already saved data
            titleTextView.setText(R.string.edit_item_text);
            saveButton.setText("Update");

            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!itemName.getText().toString().trim().isEmpty()
                            && !itemDescription.getText().toString().trim().isEmpty()
                            && !itemQty.getText().toString().trim().isEmpty()) {

                        //update the item
                        newItem.setItemName(itemName.getText().toString());
                        newItem.setItemQuantity(Integer.parseInt(itemQty.getText().toString()));
                        newItem.setItemDescription(itemDescription.getText().toString());

                        viewModel.updateItem(newItem);
                        alertDialog.dismiss();
                    }
                    else
                        Snackbar.make(v, "Fields are empty!",
                                Snackbar.LENGTH_SHORT)
                        .show();
                }
            });
        }

        private void deleteItem(final ItemEntity itemEntity) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_delete_pop, null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.deleteItem(itemEntity);
                    itemList.remove(getAdapterPosition());

                    alertDialog.dismiss();
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
    }
}
