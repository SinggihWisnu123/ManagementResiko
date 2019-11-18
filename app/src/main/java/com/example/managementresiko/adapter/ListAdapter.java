package com.example.managementresiko.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.managementresiko.R;
import com.example.managementresiko.helper.DataKuesioner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 16/11/17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<DataKuesioner> contactList;
    private List<DataKuesioner> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, ahp_resiko,ahp_prosedur,tanggal,no_responden,dot;

        public MyViewHolder(View view) {
            super(view);
            nama = view.findViewById(R.id.nama);
            ahp_resiko = view.findViewById(R.id.ahp_resiko);
            ahp_prosedur = view.findViewById(R.id.ahp_prosedur);
            tanggal = view.findViewById(R.id.tanggal);
            no_responden = view.findViewById(R.id.no_responden);
            dot = view.findViewById(R.id.dot);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public ListAdapter(Context context, List<DataKuesioner> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DataKuesioner contact = contactListFiltered.get(position);
        holder.nama.setText(contact.getNama());
        holder.ahp_resiko.setText(contact.getAhp_resiko());
        holder.ahp_prosedur.setText(contact.getAhp_prosedur());
        holder.tanggal.setText(contact.getTanggal());
        holder.no_responden.setText(contact.getNo_responden());
        holder.dot.setText(Html.fromHtml("&#8226;"));

       }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<DataKuesioner> filteredList = new ArrayList<>();
                    for (DataKuesioner row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNama().toLowerCase().contains(charString.toLowerCase()) || row.getNo_responden().contains(charSequence) || row.getTanggal().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<DataKuesioner>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(DataKuesioner contact);
    }
}