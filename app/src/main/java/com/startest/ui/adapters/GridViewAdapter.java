package com.startest.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.startest.threads.DownloadImageTask;
import com.startest.webservices.models.ImageModel;
import java.util.ArrayList;
import activities.ui.startest.com.ui.R;

public class GridViewAdapter extends ArrayAdapter {
	private Context context;
	private int layoutResourceId;
	private ArrayList<ImageModel> data = new ArrayList<ImageModel>();

	public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageModel> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	public ArrayList<ImageModel> getData()
	{
		return data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) row.findViewById(R.id.image);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		ImageModel item = (ImageModel) data.get(position);
		//holder.imageTitle.setText(item.getTitle());
//		holder.image.setImageBitmap(item.getImage());
		new DownloadImageTask(holder.image).execute(item.getImage());
		return row;
	}

	public static class ViewHolder {
		public ImageView image;
	}


}