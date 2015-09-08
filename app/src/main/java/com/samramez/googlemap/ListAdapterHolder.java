package com.samramez.googlemap;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by samramez on 9/7/15.
 */
public class ListAdapterHolder extends RecyclerView.Adapter<ListAdapterHolder.ViewHolder> {


    private final FragmentActivity mActivity;
    private final List<UserDetails> mUserDetails = new ArrayList<UserDetails>();
    OnItemClickListener mItemClickListener;

    public ListAdapterHolder(FragmentActivity mActivity) {
        this.mActivity = mActivity;
        createUserDetails();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.custom_row, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder , int position) {
        holder.vId.setText("ID: " + mUserDetails.get(position).getId()  );
        holder.vName.setText("Name: " + mUserDetails.get(position).getName());
        holder.vSex.setText("Sex: " + mUserDetails.get(position).getSex());
        holder.vAge.setText("Age: " + mUserDetails.get(position).getAge());
    }

    @Override
    public int getItemCount() {
        return mUserDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView vName, vSex, vId, vAge;

        public ViewHolder(View view) {
            super(view);
            vId = (TextView) view.findViewById(R.id.list_id);
            vName = (TextView) view.findViewById(R.id.list_name);
            vSex = (TextView) view.findViewById(R.id.list_sex);
            vAge = (TextView) view.findViewById(R.id.list_age);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    /* ==========This Part is not necessary========= */

    /**
     * Create Random Users
     */
    private void createUserDetails() {
        for (int i = 0; i < 100; i++) {
            final UserDetails mDetails = new UserDetails();
            mDetails.setId(i);
            mDetails.setName("Name " + i);
            mDetails.setSex((i % 2) == 0 ? "M" : "F");
            mDetails.setAge(randInt(14, 50));
            mUserDetails.add(mDetails);
        }
    }

    /*
     * Snippet from http://stackoverflow.com/a/363692/1008278
     */
    public static int randInt(int min , int max) {
        final Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /* ==========This Part is not necessary========= */
}
