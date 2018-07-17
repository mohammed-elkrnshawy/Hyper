package com.example.a3zt.hyper;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a3zt.hyper.ProductClass.Items;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

class GridAdapter extends BaseAdapter {

    private List<Items> mProductList;
    private Context mContext;

    public GridAdapter(Context context,List<Items> productList) {
        mContext = context;
        mProductList=productList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mProductList.indexOf(getItem(position));
    }


    class ViewHolder
    {
        ProgressBar progressBar;
        ImageView imageView;
        TextView textView_Price,textView_Description;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder=null;


        LayoutInflater mIFlater=(LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View row = null;

            row=mIFlater.inflate(R.layout.view_item,parent,false);
            holder=new ViewHolder();

            holder.imageView=row.findViewById(R.id.image);
            holder.textView_Price=row.findViewById(R.id.text_Price);
            holder.textView_Description=row.findViewById(R.id.text_Description);
            holder.progressBar=row.findViewById(R.id.progress);


            holder.textView_Description.setText(mProductList.get(position).getProductDescription());
            holder.textView_Price.setText("$"+mProductList.get(position).getPrice());

            holder.imageView.getLayoutParams().height = mProductList.get(position).getImage().getHeight();
            holder.imageView.requestLayout();


        final ViewHolder finalHolder = holder;
        ImageLoader.getInstance().displayImage(mProductList.get(position).getImage().getUrl()
                    , holder.imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    finalHolder.progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    finalHolder.progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    finalHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    finalHolder.progressBar.setVisibility(View.VISIBLE);
                }
            });


        return row;
    }


}