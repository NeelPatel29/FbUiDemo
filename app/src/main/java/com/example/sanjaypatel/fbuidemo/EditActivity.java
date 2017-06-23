package com.example.sanjaypatel.fbuidemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    EditText editContent,editUrl;
    Button save,loadVideo;
    CardView cardView;
    LinearLayout linearLayout,linearLayoutMain;
    LinearLayout.LayoutParams layoutParams;
    ImageView imageView;
    VideoView videoView;
    TextInputLayout txtInputCon,txtInputUrl;
    Spinner spinner;
    MediaCellEnum mediaCellEnum ;
    String idData,contentData,urlData,url;
    private static final int MY_MENU_1 = Menu.FIRST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editContent = new EditText(getApplicationContext());
        editUrl = new EditText(getApplicationContext());
        save = new Button(getApplicationContext());
        txtInputCon = new TextInputLayout(EditActivity.this);
        txtInputUrl = new TextInputLayout(EditActivity.this);
        //load = new Button(getApplicationContext());
        loadVideo = new Button(getApplicationContext());
        imageView = new ImageView(getApplicationContext());
        videoView = new VideoView(getApplicationContext());
        spinner = new Spinner(getApplicationContext());
        linearLayout = new LinearLayout(getApplicationContext());
        linearLayoutMain = new LinearLayout(getApplicationContext());
        cardView = new CardView(getApplicationContext());
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        cardView.setPadding(50,50,50,50);
        linearLayout.setPadding(25,25,25,25);
        linearLayoutMain.setPadding(25,25,25,25);
        linearLayoutMain.setBackgroundColor(Color.parseColor("#08557E"));
        cardView.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        cardView.setCardElevation(25f);

        linearLayoutMain.setLayoutParams(layoutParams);
        linearLayout.setLayoutParams(layoutParams);
        cardView.setLayoutParams(layoutParams);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        editContent.setHint("Enter Content");
        editUrl.setHint("Enter URL");
        imageView.setPadding(50,50,50,50);
        videoView.setPadding(50,50,50,50);

        txtInputCon.addView(editContent);
        linearLayout.addView(txtInputCon);
        txtInputUrl.addView(editUrl);
        linearLayout.addView(txtInputUrl);
        linearLayout.addView(loadVideo);
        linearLayout.addView(imageView);
        linearLayout.addView(videoView);
        cardView.addView(linearLayout);
        linearLayoutMain.addView(cardView);



        loadVideo.setText("Upload Data");
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add(0,"Load");
        spinnerArray.add(1,"Save");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spinnerArray);
        spinner.setAdapter(spinnerAdapter);
        Intent intent = getIntent();
        //idData = String.valueOf(intent.getIntExtra("idData", 0));
        idData = String.valueOf(intent.getLongExtra("idData", 0));

        contentData = intent.getStringExtra("content");
        url = intent.getStringExtra("url");
        int sequence = intent.getIntExtra("sequence", 1);
        mediaCellEnum = (MediaCellEnum) intent.getSerializableExtra("mediaType");

        videoView.start();

        if (mediaCellEnum.toString() == "TEXT") {
            editContent.setText(contentData);
            editUrl.setVisibility(View.VISIBLE);
            editUrl.setText(url);
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);

            loadVideo.setVisibility(View.GONE);
            //load.setVisibility(View.GONE);

        } else if (mediaCellEnum.toString() == "LINK") {
            editContent.setText(contentData);
            editUrl.setText(url);
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
            loadVideo.setVisibility(View.GONE);
            // load.setVisibility(View.GONE);
        } else if (mediaCellEnum.toString() == "IMAGE") {
            editContent.setText(contentData);
            editUrl.setText(url);
            imageView.setImageURI(Uri.parse(url));
            editUrl.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);

        } else if (mediaCellEnum.toString() == "VIDEO") {
            editContent.setText(contentData);
            editUrl.setText(url);
            videoView.setVideoURI(Uri.parse(url));
            imageView.setVisibility(View.GONE);
            editUrl.setVisibility(View.GONE);
            //load.setVisibility(View.GONE);
        }


        loadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaCellEnum.toString() == "IMAGE"){
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setType("image/*");
                    startActivityForResult(intent1,1);
                }else{
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setType("video/*");
                    startActivityForResult(intent1,1);
                }
            }
        });

        setContentView(linearLayoutMain,layoutParams);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuItem menuItem = menu.add(0, MY_MENU_1 , 0, "Update");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setTitle("Update");

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MY_MENU_1:
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                String editTextData = editContent.getText().toString();
                String etUrl = editUrl.getText().toString();

                if (mediaCellEnum.toString() == "TEXT"){
                    long l = Long.parseLong(idData);
                    MediaCell mediaCell = MediaCell.findById(MediaCell.class, l);
                    mediaCell.setTxtContent(editTextData);
                    mediaCell.setUrl(etUrl);
                    mediaCell.save();

                }else if (mediaCellEnum.toString() == "LINK"){
                    long l = Long.parseLong(idData);
                    MediaCell mediaCell = MediaCell.findById(MediaCell.class, l);
                    mediaCell.setUrl(editUrl.getText().toString());
                    mediaCell.setTxtContent(editTextData);
                    mediaCell.save();

                }else if(mediaCellEnum.toString() == "IMAGE"){
                   /* if(urlData == null){
                        urlData = editUrl.getText().toString();
                        imageView.setImageURI(Uri.parse(url));

                    }*/

                    long l = Long.parseLong(idData);
                    MediaCell mediaCell = MediaCell.findById(MediaCell.class, l);
                    mediaCell.setUrl(editUrl.getText().toString());
                    mediaCell.setTxtContent(editTextData);
                    mediaCell.save();



                }else if(mediaCellEnum.toString() == "VIDEO"){

//                    if(urlData == null){
//                        urlData = editUrl.getText().toString();
//                        videoView.setVideoURI(Uri.parse(url));
//                        videoView.start();
//                    }
                    long l = Long.parseLong(idData);
                    MediaCell mediaCell = MediaCell.findById(MediaCell.class, l);
                    mediaCell.setUrl(editUrl.getText().toString());
                    mediaCell.setTxtContent(editTextData);
                    mediaCell.save();


                }


                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Uri uri = data.getData();
            urlData = data.getData().toString();
            editUrl.setText(urlData);
            // st = data.getData().toString();
            if(mediaCellEnum.toString() == "IMAGE"){

                imageView.setImageURI(uri);
            }
            else if(mediaCellEnum.toString() == "VIDEO"){
                videoView.setVideoURI(data.getData());
                videoView.start();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditActivity.this,MainActivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intent);
    }
}
