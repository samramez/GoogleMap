package com.samramez.googlemap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    private ArrayList<Double> latLongAttr = new ArrayList<Double>();

    /**
     * Constructor
     */
    public ListAdapterHolder(FragmentActivity mActivity, ArrayList<Double> mLatLongAttr) {

        this.mActivity = mActivity;
        this.latLongAttr = mLatLongAttr;

        //Log.e("***ListAdapterHolder***", "Image Array size is: " + latLongAttr.size());

        new loadImageFromWeb()
                .execute(latLongAttr.get(0),latLongAttr.get(1));

        // Adds needed info to imagesRowInfo List
        //createImageObjectList(imageObjects);

        try {
            Thread.sleep(10000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }


    }

//    public class loadImageFromWeb extends AsyncTask<String, Void, Void> {
//
//        @Override
//        protected Void doInBackground(String... params) {
//
//            Log.e("***ListAdapterHolder***", "params size is: " + params.length);
//            for (String param : params) {
//                try {
//                    //ImageView i = (ImageView)findViewById(R.id.image);
//                    Bitmap bitmap = BitmapFactory.decodeStream(
//                            (InputStream) new URL(param).getContent()
//                    );
//
//                    imageObjects.add(bitmap);
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                    //return null;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    //return null;
//                }
//
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            //super.onPostExecute(aVoid);
//
//            Log.e("***ListAdapterHolder***", "ImageObjects size is: " + imageObjects.size());
//            createImageObjectList(imageObjects);
//        }
//
//    }


    public class loadImageFromWeb extends AsyncTask<Double, Void, Void> {

        protected ArrayList<String> imageArrayList = new ArrayList();

        private ArrayList<Bitmap> imageObjects = new ArrayList<Bitmap>();

        @Override
        protected Void doInBackground(Double... latLng) {

            String urlString = "https://api.instagram.com/v1/media/search?lat=";
            urlString += latLng[0] + "&lng=" ;
            urlString += latLng[1];
            urlString += "&access_token=" + "50325870.c3c3973.f6ea1a578d3e44e987d5db2fcf0349da";

            // Getting Json response from url http request
            String result = getJsonResponse(urlString);

            imageArrayList = getImagesLink(result);

            Log.e("***ListAdapterHolder***", "URL Array size is: " + imageArrayList.size());

            /**
             * Getting the images
             */

            for (String link : arrayListToString(imageArrayList)) {
                try {
                    //ImageView i = (ImageView)findViewById(R.id.image);
                    Bitmap bitmap = BitmapFactory.decodeStream(
                            (InputStream) new URL(link).getContent()
                    );

                    imageObjects.add(bitmap);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    //return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    //return null;
                }

            }

            Log.e("***ListAdapterHolder***", "Number of Images are: " + imageObjects.size());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);

            Log.e("***ListAdapterHolder***", "ImageObjects size is: " + imageObjects.size());
            createImageObjectList(imageObjects);

//            latLongAttr = null;
//
//            imageObjects = null;
//
//            imageArrayList = null;

        }

    }

    /**
     * Gets url attribute and returns the JSON result in a string format
     */
    public String getJsonResponse(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    /**
     * @return ArrayList of Images Links
     */
    private ArrayList<String> getImagesLink(String result){

        ArrayList<String> imageArray = new ArrayList<String>();

        //ArrayList imageArrayList = new ArrayList();
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("data"); // get data object

            for(int i=0 ; i < data.length() ; i++){
                String imageLink = ((JSONObject) data.get(i))
                        .getJSONObject("images")
                        .getJSONObject("low_resolution")
                        .getString("url");

                imageArray.add(imageLink.replace("\\", ""));

                //Log.d(ASYNC_TAG, imageArrayList.get(0).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageArray;
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

    /**
     * Method to convert ArrayList<String> to String[]
     */
    private String[] arrayListToString(ArrayList<String> list){

        String[] stringArray = new String[list.size()];
        stringArray = list.toArray(stringArray);
        return stringArray;
    }

//    /**
//     * Method to convert ArrayList<Double> to double[]
//     */
//    private double[] arrayListToDoubleArray(ArrayList list){
//
//        double[] doubleArray = new double[list.size()];
//        doubleArray = list.toArray(doubleArray);
//        return doubleArray;
//    }


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
