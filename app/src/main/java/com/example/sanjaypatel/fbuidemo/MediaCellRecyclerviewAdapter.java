package com.example.sanjaypatel.fbuidemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by sanjaypatel on 2017-06-02.
 */

public class MediaCellRecyclerviewAdapter extends RecyclerView.Adapter<MediaCellRecyclerviewAdapter.DSViewHolder>  {
    private List<MediaCell> sanDSList;

    private Context context;

    public MediaCellRecyclerviewAdapter(List<MediaCell> sanPersonList, Context context) {
        this.sanDSList = sanPersonList;
        this.context = context;
    }

    @Override
    public DSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new DSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DSViewHolder holder, int position) {
        try{
            holder.setIsRecyclable(false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.linearLayout.setLayoutParams(layoutParams);
            holder.cardView.setLayoutParams(layoutParams);
            holder.linearLayout.addView(holder.cardView);

            holder.cardView.setCardElevation(50f);
            holder.displayLinear.setOrientation(LinearLayout.VERTICAL);
            holder.displayLinear.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            holder.cardView.addView(holder.displayLinear);
            holder.txtTextSeq.setText(String.valueOf(sanDSList.get(position).getSequence()));
            holder.txtTextSeq.setLayoutParams(layoutParams);

            holder.txtTextCon.setText(sanDSList.get(position).getTxtContent());
            holder.txtTextCon.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            holder.viVideo.setVisibility(View.VISIBLE);



            if (sanDSList.get(position).getMediaCellEnum().toString() .equals("TEXT")){
                holder.displayLinear.setPadding(40,40,40,40);
                holder.displayLinear.addView(holder.txtTextCon);
                holder.txtTextLink.setTextColor(Color.BLACK);
                holder.txtTextLink.setClickable(false);
                holder.txtTextLink.setText(sanDSList.get(position).getUrl());
                holder.displayLinear.addView(holder.txtTextLink);
            }
            if (sanDSList.get(position).getMediaCellEnum().toString().equals("LINK")){
                Log.d("Data: " ,"LINK" + sanDSList.get(position).getMediaCellEnum().toString() );
                holder.txtTextLink.setText(sanDSList.get(position).getUrl());
                holder.txtTextLink.setTextColor(Color.BLUE);
                holder.txtTextLink.setClickable(true);
                holder.txtTextLink.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                holder.displayLinear.addView(holder.txtTextCon);
                holder.displayLinear.addView(holder.txtTextLink);

            }
            if (sanDSList.get(position).getMediaCellEnum().toString() .equals( "IMAGE")){
                Log.d("Data: " ,"IMAGE" + sanDSList.get(position).getMediaCellEnum().toString() );

                Uri uri = Uri.parse(sanDSList.get(position).getUrl());
                Log.d("URI",""+uri);

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
                    ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,5,bytearrayoutputstream);

                    byte[] bytearray = bytearrayoutputstream.toByteArray();

                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(bytearray,0,bytearray.length);
                    holder.image.setImageBitmap(bitmap2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

               // holder.image.setImageURI(uri);
                holder.image.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 500));
                holder.txtTextCon.setText(sanDSList.get(position).getTxtContent());
                holder.displayLinear.addView(holder.txtTextCon);
                holder.displayLinear.addView(holder.image);


            }
            if (sanDSList.get(position).getMediaCellEnum().toString() .equals( "VIDEO")){
                Log.d("Data: " ,"VIDEO" +  sanDSList.get(position).getMediaCellEnum().toString() );
                holder.linearLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
                holder.viVideo.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                Uri uri = Uri.parse(sanDSList.get(position).getUrl());
                Log.d("URI",""+uri);

                holder.viVideo.setVideoURI(uri);
                // holder.viVideo.seekTo(100);
                holder.viVideo.start();
                holder.displayLinear.addView(holder.txtTextCon);
                holder.displayLinear.addView(holder.viVideo);
            }
        }catch (OutOfMemoryError e)
        {
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return sanDSList.size();
    }



    public static class DSViewHolder extends RecyclerView.ViewHolder {


        public TextView txtTextCon,txtTextLink,txtTextSeq;
        public ImageView image;
        public static VideoView viVideo;
        CardView cardView;

        LinearLayout linearLayout,displayLinear;

        public DSViewHolder(final View itemView) {
            super(itemView);

            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);

            // code
            cardView = new CardView(itemView.getContext());

            displayLinear = new LinearLayout(itemView.getContext());
            displayLinear.setId(R.id.displayLinear);
            txtTextSeq = new TextView(itemView.getContext());
            txtTextSeq.setId(R.id.txtTextSeq);
            txtTextCon = new TextView(itemView.getContext());
            txtTextCon.setId(R.id.txtText);
            txtTextLink = new TextView(itemView.getContext());
            txtTextLink.setId(R.id.txtLink);
            txtTextLink.setLinksClickable(true);
            txtTextLink.setTextColor(Color.BLUE);
            txtTextLink.setTextSize(30);
            image = new ImageView(itemView.getContext());
            image.setId(R.id.viewImage);
            viVideo = new VideoView(itemView.getContext());
            viVideo.setId(R.id.viewVideo);

            txtTextLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://google.com";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    itemView.getContext().startActivity(i);
                }
            });


        }
    }



}

