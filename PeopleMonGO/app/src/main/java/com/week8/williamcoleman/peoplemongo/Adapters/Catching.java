package com.week8.williamcoleman.peoplemongo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.week8.williamcoleman.peoplemongo.Models.Users;
import com.week8.williamcoleman.peoplemongo.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by williamcoleman on 11/11/16.
 */

public class Catching extends RecyclerView.Adapter<Catching.CaughtHolder> {
    public ArrayList<Users> pMon;
    private Context context;

    public Catching(ArrayList<Users> pMon, Context context) {
        this.pMon = pMon;
        this.context = context;
    }

    @Override
    public CaughtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(context)
                .inflate(R.layout.catch_view, parent, false);
        return new CaughtHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(CaughtHolder holder, int position) {
        Users users = pMon.get(position);
        holder.bindpMon(users);
    }

    @Override
    public int getItemCount() {
        return pMon.size();
    }

    class CaughtHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.imageView)
        ImageView imageView;
        @Bind(R.id.id_textview)
        TextView idTextView;
        @Bind(R.id.name_textview)
        TextView nameView;

        private String avatarBase;

        public CaughtHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindpMon(Users user) {
            idTextView.setText(user.getUsersID());
            nameView.setText(user.getUserName());

        }
    }
}