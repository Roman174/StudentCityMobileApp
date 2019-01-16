package com.susu.studentcity.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susu.studentcity.R;
import com.susu.studentcity.models.database.Hostel;

public class HostelListAdapter extends RecyclerView.Adapter<HostelListAdapter.ViewHolder>
        implements View.OnClickListener {

    private Hostel[] hostels;
    private LayoutInflater inflater;
    private Context context;

    private onItemClickListener itemClickListener;

    public HostelListAdapter(Context context, Hostel[] hostels, onItemClickListener itemClickListener) {
        this.context  = context;
        this.hostels  = hostels;
        this.inflater = LayoutInflater.from(context);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public HostelListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup container, int i) {
        View itemList = inflater.inflate(R.layout.item_of_hostel_list, container,
                false);

        return new ViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelListAdapter.ViewHolder viewHolder,
                                 final int position) {
        TextView hostelName    = viewHolder.itemView.findViewById(R.id.hostelName);
        TextView hostelAddress = viewHolder.itemView.findViewById(R.id.hostelAddress);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hostel hostel = hostels[position];
                itemClickListener.onItemClick(hostel);
            }
        });

        Hostel hostel = hostels[position];
        setText(hostelName, hostel.getTitle());
        setText(hostelAddress, hostel.getAddress());
    }

    private void setText(TextView textView, String text) {
        String stub = context.getString(R.string.missing);
        if(!TextUtils.isEmpty(text)) {
            textView.setText(text);
        } else {
            textView.setText(stub);
        }
    }

    @Override
    public int getItemCount() {
        return hostels.length;
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Нажатие", Snackbar.LENGTH_LONG).show();
    }

    public Hostel getItem(int position) {
        return hostels[position];
    }

    public void showMessage(String message) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public interface onItemClickListener {
        void onItemClick(Hostel hostel);
    }
}
