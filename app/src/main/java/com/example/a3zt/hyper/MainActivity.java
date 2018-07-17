package com.example.a3zt.hyper;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.example.a3zt.hyper.ProductClass.Items;
import com.example.a3zt.hyper.Retrofit.ApiUtlis;
import com.example.a3zt.hyper.Retrofit.UserService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private StaggeredGridView gridView;
    private GridAdapter gridAdapter;
    private UserService userService;

    private  ProgressDialog progressDialog;
    private Parcelable state;
    private int index;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitComponent();


        getProducts(10);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    Items items= (Items) parent.getItemAtPosition(position-1);
                    Items items2= (Items) parent.getItemAtPosition(position);
                    Items items3= (Items) parent.getItemAtPosition(position+1);
                    showDialog(items.getImage().getUrl(),items2.getImage().getUrl(),items3.getImage().getUrl());
                }
                else {
                    Items items2= (Items) parent.getItemAtPosition(position);
                    Items items3= (Items) parent.getItemAtPosition(position+1);
                    showDialog("",items2.getImage().getUrl(),items3.getImage().getUrl());
                }


            }
        });



        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if ((firstVisibleItem+visibleItemCount)==totalItemCount
                        &&totalItemCount!=0)
                {
                    state = gridView.onSaveInstanceState();
                    index = totalItemCount;
                    getProducts(totalItemCount+10);
                }
            }
        });

    }

    private void getProducts(final int count) {
        Call<List<Items>> call=userService.Products(count);

        call.enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(Call<List<Items>> call, Response<List<Items>> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    gridAdapter=new GridAdapter(MainActivity.this,response.body());
                    gridView.setAdapter(gridAdapter);
                    gridView.smoothScrollToPosition(index);
                    if (state!=null)
                    gridView.onRestoreInstanceState(state);
                }
            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {
                Log.d("Code","E   "+t.getMessage());
            }
        });

    }

    private void InitComponent() {

        progressDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);

        //region ImageLoader
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        //endregion
        userService= ApiUtlis.getUserService();
        gridView=findViewById(R.id.grid_view);
    }

    private void showDialog(final String Url1, final String Url2, final String Url3)
    {
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.view_dialog);

        final ImageView imageBigPicture,imageSmallPicture1,imageSmallPicture2,imageSmallPicture3;
        final ProgressBar progressBar;
        imageBigPicture=dialog.findViewById(R.id.pic);
        imageSmallPicture1=dialog.findViewById(R.id.pic1);
        imageSmallPicture2=dialog.findViewById(R.id.pic2);
        imageSmallPicture3=dialog.findViewById(R.id.pic3);
        progressBar=dialog.findViewById(R.id.progress);

        ShowImage(imageBigPicture,Url2,progressBar);
        imageSmallPicture2.setBackground(getResources().getDrawable(R.drawable.border_shape));

        if (Url1.isEmpty())
        {
            imageSmallPicture1.setVisibility(View.GONE);
        }

        imageSmallPicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImage(imageBigPicture,Url1,progressBar);
                imageSmallPicture1.setBackground(getResources().getDrawable(R.drawable.border_shape));
                imageSmallPicture3.setBackground(null);
                imageSmallPicture2.setBackground(null);
            }
        });
        imageSmallPicture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImage(imageBigPicture,Url2,progressBar);
                imageSmallPicture2.setBackground(getResources().getDrawable(R.drawable.border_shape));
                imageSmallPicture1.setBackground(null);
                imageSmallPicture3.setBackground(null);
            }
        });
        imageSmallPicture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImage(imageBigPicture,Url3,progressBar);
                imageSmallPicture3.setBackground(getResources().getDrawable(R.drawable.border_shape));
                imageSmallPicture1.setBackground(null);
                imageSmallPicture2.setBackground(null);
            }
        });


        dialog.show();

    }

    private void ShowImage(ImageView imageView, String Url, final ProgressBar progressBar)
    {
        ImageLoader.getInstance().displayImage(Url
                , imageView, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
    }

}
