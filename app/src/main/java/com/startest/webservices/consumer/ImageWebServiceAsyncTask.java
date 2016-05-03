package com.startest.webservices.consumer;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.google.gson.Gson;
import com.startest.ui.adapters.GridViewAdapter;
import com.startest.webservices.models.ImageModel;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import activities.ui.startest.com.ui.R;

/**
 * Created by s on 28/04/16.
 */
public class ImageWebServiceAsyncTask extends AsyncTask<String, String, String> {

    public static final String URL = "https://gist.githubusercontent.com/maclir/f715d78b49c3b4b3b77f/raw/8854ab2fe4cbe2a5919cea97d71b714ae5a4838d/items.json";
    private static final String TAG = "ImageWebAsyncTask";

    private GridView gridView;

    public ImageWebServiceAsyncTask(GridView gridView)
    {
        this.gridView = gridView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        StringBuffer lineBuffer = null;
        try{
            java.net.URL url = new java.net.URL(URL);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            lineBuffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                lineBuffer.append(line);
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }

        if(lineBuffer != null) {
            return lineBuffer.toString();
        }
        else
        {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {

        if(s != null) {
            GridViewAdapter gridAdapter = (GridViewAdapter) gridView.getAdapter();
            gridAdapter.getData().clear();
            ImageModel[] modelArray = (ImageModel[]) new Gson().fromJson(s, ImageModel[].class);
            List<ImageModel> models = Arrays.asList(modelArray);
            gridAdapter.getData().addAll(models);
            gridAdapter.notifyDataSetChanged();
        }
//        gridView.setAdapter(gridAdapter);
        super.onPostExecute(s);
    }
}