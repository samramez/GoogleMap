package com.samramez.googlemap;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by samramez on 9/7/15.
 */
public class ListAdapterHolder extends RecyclerView.Adapter<ListAdapterHolder.ViewHolder> {


    private final FragmentActivity mActivity;

    private final List<RowInfo> imagesRowInfo = new ArrayList<RowInfo>();

    OnItemClickListener mItemClickListener;

//    private ArrayList<Double> latLongAttr = new ArrayList<Double>();

    private ArrayList<Bitmap> imageObjectS = new ArrayList<Bitmap>();


    /**
     * Constructor
     */
    public ListAdapterHolder(FragmentActivity mActivity, ArrayList<Bitmap> imageObjects) {

        this.mActivity = mActivity;
        this.imageObjectS = imageObjects;

        // Adds needed info to imagesRowInfo List
        createImageObjectList(imageObjectS);

    }


    /**
     * Create list of image objects
     */
    private void createImageObjectList(ArrayList<Bitmap> imageObjects) {

        for (int i = 0; i < imageObjects.size(); i++) {

            final RowInfo mRowInfo = new RowInfo();

            mRowInfo.setImageObject(imageObjects.get(i));

//            mRowInfo.setName("Name " + i);
//            mRowInfo.setSex((i % 2) == 0 ? "M" : "F");
//            mRowInfo.setAge(randInt(14, 50));


            imagesRowInfo.add(mRowInfo);
        }

        Log.e("***ListAdapterHolder***", "ImageRowInfo size is: " + imagesRowInfo.size());
    }



    public static double[] arrayListToDoubleArray(List<Double> doubles)
    {
        double[] ret = new double[doubles.size()];
        Iterator<Double> iterator = doubles.iterator();
        for(int i = 0; i < ret.length; i++){
            ret[i] = iterator.next().doubleValue();
        }
        return ret;
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

        holder.mImageView.setImageBitmap(imagesRowInfo.get(position).getImageObject());

//        holder.vId.setText("ID: " + imagesRowInfo.get(position).getImageObject()  );
//        holder.vName.setText("Name: " + imagesRowInfo.get(position).getName());
//        holder.vSex.setText("Sex: " + imagesRowInfo.get(position).getSex());
//        holder.vAge.setText("Age: " + imagesRowInfo.get(position).getAge());
    }

    @Override
    public int getItemCount() {
        return imagesRowInfo.size();
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


    /*
     * Snippet from http://stackoverflow.com/a/363692/1008278
     */
    public static int randInt(int min , int max) {
        final Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /* ==========This Part is not necessary========= */
}
