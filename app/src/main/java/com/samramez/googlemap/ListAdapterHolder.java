package com.samramez.googlemap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by samramez on 9/7/15.
 */
public class ListAdapterHolder extends RecyclerView.Adapter<ListAdapterHolder.ViewHolder> {

    private final FragmentActivity mActivity;
    private final List<RowInformation> mImageUrlList = new ArrayList<RowInformation>();
    OnItemClickListener mItemClickListener;
    private ArrayList<String> imageURLs = new ArrayList<String>();

    public ListAdapterHolder(FragmentActivity mActivity, ArrayList<String> mImageUrls) {

        this.mActivity = mActivity;
        this.imageURLs = mImageUrls;

        Log.e("***ListAdapterHolder***", "Image Array size is: " + imageURLs.size());

        // Adds needed info to mImageUrlList List
        createImageObjectList(imageURLs);
    }

    /**
     * Builds structure of a single row of the RecycleView by using custom_row.xml
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {

        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        // This view represents the LinearLayout in custom_row.xml
        final View sView = mInflater.inflate(R.layout.custom_row, parent, false);

        ViewHolder mViewHolder = new ViewHolder(sView);
        return mViewHolder;
    }


    /**
     * We set the data which corresponds to the current row in the RecycleView
     */
    @Override
    public void onBindViewHolder(ViewHolder holder , int position) {


        holder.mImageView.setImageBitmap(mImageUrlList.get(position).getImageObject());

//        holder.vId.setText("ID: " + mImageUrlList.get(position).getImageObject()  );
//        holder.vName.setText("Name: " + mImageUrlList.get(position).getName());
//        holder.vSex.setText("Sex: " + mImageUrlList.get(position).getSex());
//        holder.vAge.setText("Age: " + mImageUrlList.get(position).getAge());
    }

    @Override
    public int getItemCount() {
        return mImageUrlList.size();
    }


    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        TextView vName, vSex, vId, vAge;
        ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.picImageView);
//            vId = (TextView) view.findViewById(R.id.list_id);
//            vName = (TextView) view.findViewById(R.id.list_name);
//            vSex = (TextView) view.findViewById(R.id.list_sex);
//            vAge = (TextView) view.findViewById(R.id.list_age);
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
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    /* ==========This Part is not necessary========= */

    /**
     * Create list of image objects
     */
    private void createImageObjectList(ArrayList<String> imageUrlList) {

        for (int i = 0; i < imageUrlList.size(); i++) {

            final RowInformation mRowInformation = new RowInformation();

            Bitmap d = loadImageFromWebOperations( imageUrlList.get(i) );

            mRowInformation.setImageObject(d);

//            mRowInformation.setName("Name " + i);
//            mRowInformation.setSex((i % 2) == 0 ? "M" : "F");
//            mRowInformation.setAge(randInt(14, 50));


            mImageUrlList.add(mRowInformation);
        }
    }


    public Bitmap loadImageFromWebOperations(String url) {
        try {
            //ImageView i = (ImageView)findViewById(R.id.image);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
