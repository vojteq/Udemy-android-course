package com.example.shoppinglist.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.ListActivity;
import com.example.shoppinglist.MainActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.data.DatabaseHandler;
import com.example.shoppinglist.model.Item;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    LayoutInflater layoutInflater;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        databaseHandler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_layout, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.productNameTextView.setText(item.getProductName());
        holder.quantityTextView.setText(MessageFormat.format("Quantity: {0}", item.getQuantity()));
        holder.dateTextView.setText(MessageFormat.format("Added on: {0}", item.getDate()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public int id;
        public TextView productNameTextView;
        public TextView quantityTextView;
        public TextView dateTextView;
        public Button deleteButton;
        public Button editButton;


        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            productNameTextView = itemView.findViewById(R.id.product_name_text_view);
            quantityTextView = itemView.findViewById(R.id.quantity_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button);

            deleteButton.setOnClickListener(this);
            editButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId()) {
                case R.id.edit_button:
//                    TODO edit
                    editItem(position);
                    break;
                case R.id.delete_button:
                    deleteItem(position);
                    break;
            }
        }
    }

    private void deleteItem(final int position) {
        builder = new AlertDialog.Builder(context);
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.confirmation_popup, null);

        Button buttonNo = view.findViewById(R.id.confirmation_no_button);
        Button buttonYes = view.findViewById(R.id.confirmation_yes_button);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.deleteItem(itemList.get(position));
                itemList.remove(position);
                notifyItemRemoved(position);

                alertDialog.dismiss();
                if (databaseHandler.getCount() == 0) {
                    context.startActivity(new Intent(context, MainActivity.class));
                    //TODO end activity?
                }
            }
        });
    }

    private void editItem(final int position) {
        builder = new AlertDialog.Builder(context);
        layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.edit_popup, null);
        final Item item = itemList.get(position);

        final EditText editTextProductName = view.findViewById(R.id.edit_item_name_edit_text);
        final EditText editTextQuantity = view.findViewById(R.id.edit_item_quantity_edit_text);
        Button buttonDismiss = view.findViewById(R.id.edit_item_dismiss_button);
        Button buttonSave = view.findViewById(R.id.edit_item_save_button);

        editTextProductName.setText(item.getProductName());
        editTextQuantity.setText(String.valueOf(item.getQuantity()));

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextProductName.getText().toString().isEmpty()
                        && !editTextQuantity.getText().toString().isEmpty()) {
                    item.setProductName(editTextProductName.getText().toString().trim());
                    item.setQuantity(Integer.parseInt(editTextQuantity.getText().toString()));
                    databaseHandler.updateItem(item);
                    notifyItemChanged(position);

                    alertDialog.dismiss();
                }
                else
                    Snackbar.make(view, "Fill all fields", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
