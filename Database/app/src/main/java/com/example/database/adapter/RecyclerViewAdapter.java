package com.example.database.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.ContactInfoActivity;
import com.example.database.R;
import com.example.database.model.Contact;
import com.example.database.util.Util;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Contact> contactList;

    public RecyclerViewAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Contact contact = contactList.get(position);
        viewHolder.contactName.setText(contact.getName());
        viewHolder.phoneNumber.setText(contact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView contactName;
        public TextView phoneNumber;
        public ImageView iconButton;
        public final int id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.getId();
            contactName = itemView.findViewById(R.id.name);
            phoneNumber = itemView.findViewById(R.id.phone_number);
            iconButton = itemView.findViewById(R.id.icon_button);
            itemView.setOnClickListener(this);
            iconButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Contact contact = contactList.get(position);

//            Log.d("clicked", "onClick: " + contact.getName());
            if (view.getId() == R.id.icon_button) {
                Log.d("clicked", "onClick: " + "calling " + contact.getName());
                startContactInfoActivity(contact, view);
            } else {
                Log.d("clicked", "onClick: " + contact.getName());
            }
            Log.d("id", "onClick: " + view.getId());
        }

        private void startContactInfoActivity(Contact contact, View view) {
//            DateFormat dateFormat= DateFormat.getDateInstance(DateFormat.FULL, Locale.FRANCE);
            String now = DateFormat.getDateInstance().format(new Date());

            Intent intent = new Intent(context, ContactInfoActivity.class);
            intent.putExtra(Util.KEY_NAME, contact.getName());
            intent.putExtra(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());
            intent.putExtra(Util.TIME, now);
            context.startActivity(intent);
        }
    }
}
