package com.example.sanjaypatel.fbuidemo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public class AddMediaCellActivity extends AppCompatActivity {
    TextView txtSeq;
    EditText etCon,etUrl;
    Spinner spType;
    Button btnLoad;
    CardView cardView;
    LinearLayout linearLayout,linearLayoutMain;
    VideoView videoView;
    ImageView imageView;
    TextInputLayout txtInputCon,txtInputUrl;

    LinearLayout.LayoutParams layoutParams;
    private static final int MY_MENU_1 = Menu.FIRST;
    private static final int MY_MENU_2 = Menu.FIRST + 1 ;
    MediaCell mediaCell;
    Boolean isValidURL = false;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_media_cell);
        //setContentView(R.layout.activity_add_media_cell);
        Bundle bundle = getIntent().getExtras();
        //linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        linearLayoutMain = new LinearLayout(getApplicationContext());
        linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        cardView = new CardView(getApplicationContext());
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

        cardView.setPadding(50,50,50,50);
        linearLayout.setPadding(25,25,25,25);
        linearLayoutMain.setPadding(25,25,25,25);
        linearLayoutMain.setBackgroundColor(Color.parseColor("#08557E"));
        cardView.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        cardView.setCardElevation(25f);

        videoView = new VideoView(getApplicationContext());
        imageView = new ImageView(getApplicationContext());

        linearLayoutMain.setLayoutParams(layoutParams);
        linearLayout.setLayoutParams(layoutParams);
        cardView.setLayoutParams(layoutParams);


        txtSeq = new TextView(getApplicationContext());
        txtInputCon = new TextInputLayout(AddMediaCellActivity.this);
        txtInputUrl = new TextInputLayout(AddMediaCellActivity.this);
        etCon = new EditText(getApplicationContext());
        etUrl = new EditText(getApplicationContext());

        btnLoad = new Button(getApplicationContext());
        spType = new Spinner(getApplicationContext());

        imageView = new ImageView(getApplicationContext());
        videoView = new VideoView(getApplicationContext());


        txtSeq.setId(R.id.txtTextSeq);
        etCon.setId(R.id.etTEXT);
        etUrl.setId(R.id.etURL);
        btnLoad.setId(R.id.btnLoad);
        spType.setId(R.id.spType);
        imageView.setId(R.id.etIMAGE);
        videoView.setId(R.id.etVIDEO);
        spType.setBackgroundResource(R.drawable.custom_spinner);

        spType.setPopupBackgroundResource(R.drawable.custom_popup_spinner);
        spType.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 100));


        String[] Media_Type= { "TEXT","LINK" ,"IMAGE","VIDEO"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.select_dialog_singlechoice, Media_Type);

        spType.setAdapter(arrayAdapter);



        txtSeq.setText(String.valueOf(bundle.getInt("seq")));
        txtSeq.setTextSize(25);

        etCon.setHint("Enter Content");
        etUrl.setHint("Enter URL");
        btnLoad.setText("Load Image");
        imageView.setVisibility(View.INVISIBLE);
        videoView.setVisibility(View.INVISIBLE);

        addChild();




        btnLoad.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (spType.getSelectedItemPosition()== 2){
                    Toast.makeText(getApplicationContext(),"Image",Toast.LENGTH_SHORT).show();
                    Intent intent_upload = new Intent(Intent.ACTION_PICK);
                    intent_upload.setType("image/*");
                    startActivityForResult(intent_upload,1);


                }else  if (spType.getSelectedItemPosition() == 3){
                    Toast.makeText(getApplicationContext(),"Video",Toast.LENGTH_SHORT).show();
                    Intent intent_upload = new Intent(Intent.ACTION_PICK);
                    intent_upload.setType("video/*");
                    startActivityForResult(intent_upload,2);
                }
            }
        });



        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 ){
                    Toast.makeText(getApplicationContext(),"Text",Toast.LENGTH_SHORT).show();

                    etUrl.setVisibility(View.VISIBLE);

                    btnLoad.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.INVISIBLE);

                }else if (position == 1){
                    Toast.makeText(getApplicationContext(),"Link",Toast.LENGTH_SHORT).show();
                    etUrl.setVisibility(View.VISIBLE);

                    btnLoad.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.INVISIBLE);

                } if (position == 2){
                    Toast.makeText(getApplicationContext(),"Image",Toast.LENGTH_SHORT).show();
                    etCon.setVisibility(View.VISIBLE);
                    etUrl.setVisibility(View.GONE);
                    btnLoad.setVisibility(View.VISIBLE);
                    etUrl.setHint("Image Path");
                    btnLoad.setText("Load Image");
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
                    imageView.setPadding(50,50,50,50);

                }else  if (position == 3 ){
                    Toast.makeText(getApplicationContext(),"Video",Toast.LENGTH_SHORT).show();
                    etCon.setVisibility(View.VISIBLE);
                    etUrl.setVisibility(View.GONE);
                    etUrl.setHint("Video Path");
                    btnLoad.setVisibility(View.VISIBLE);
                    btnLoad.setText("Load Video");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    private void removeChild(){
        linearLayout.removeAllViews();
    }

    private void addChild(){
        linearLayout.addView(txtSeq);
        linearLayout.addView(spType);
        txtInputCon.addView(etCon);
        linearLayout.addView(txtInputCon);
        txtInputUrl.addView(etUrl);
        linearLayout.addView(txtInputUrl);
        linearLayout.addView(btnLoad);
        linearLayout.addView(imageView);
        linearLayout.addView(videoView);
        cardView.addView(linearLayout);
        linearLayoutMain.addView(cardView);
        setContentView(linearLayoutMain,layoutParams);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.addmedia, menu);//Menu Resource, Menu
        MenuItem menuItem = menu.add(0, MY_MENU_1 , 0, "ADD");
        MenuItem menuItem2 = menu.add(0, MY_MENU_2 , 0, "Cancel");
        menuItem.setIcon(android.R.drawable.ic_menu_save);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem2.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        menuItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == 1){

            if(resultCode == RESULT_OK){
                try {


                    videoView.setVisibility(View.GONE);
                    String st = data.getData().toString();
                    imageView.setVisibility(View.VISIBLE);
                    //    linearLayout.addView(imageView,layoutParams);

                    imageView.setImageURI(data.getData());
                    etUrl.setText(st);
                }catch (OutOfMemoryError e){
                    etCon.setError("Please select image");
                }catch (Exception e){

                }

            }
        }
        else if (requestCode == 2){

            if(resultCode == RESULT_OK){
                try {
                    imageView.setVisibility(View.GONE);
                    String st = data.getData().toString();
                    videoView.setVisibility(View.VISIBLE);
                    //  linearLayout.addView(videoView,layoutParams);
                    videoView.setVideoURI(data.getData());
                    videoView.start();
                    etUrl.setText(st);
                }catch (OutOfMemoryError e){
                    etCon.setError("Please select image");

                }catch (Exception e){

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MY_MENU_1:



                if (spType.getSelectedItem().toString().equals("Text")){
                    mediaCell = new MediaCell(etUrl.getText().toString(),etCon.getText().toString(),Integer.parseInt(txtSeq.getText().toString()),MediaCellEnum.TEXT);
                    mediaCell.save();
                    Toast.makeText(getApplicationContext(),"Save..",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else if (spType.getSelectedItem().toString().equals("LINK")){

                    new Connection().execute();

                    mediaCell = new MediaCell(etUrl.getText().toString(),etCon.getText().toString(),Integer.parseInt(txtSeq.getText().toString()),MediaCellEnum.LINK);
                }else if (spType.getSelectedItem().toString().equals("IMAGE")){
                    mediaCell = new MediaCell(etUrl.getText().toString(),etCon.getText().toString(),Integer.parseInt(txtSeq.getText().toString()),MediaCellEnum.IMAGE);
                    mediaCell.save();
                    Toast.makeText(getApplicationContext(),"Save..",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else if (spType.getSelectedItem().toString().equals("VIDEO")){
                    mediaCell = new MediaCell(etUrl.getText().toString(),etCon.getText().toString(),Integer.parseInt(txtSeq.getText().toString()),MediaCellEnum.VIDEO);
                    mediaCell.save();
                    Toast.makeText(getApplicationContext(),"Save..",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else
                {
                    mediaCell = new MediaCell(etUrl.getText().toString(),etCon.getText().toString(),Integer.parseInt(txtSeq.getText().toString()),MediaCellEnum.TEXT);
                    mediaCell.save();
                    Toast.makeText(getApplicationContext(),"Save..",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }


                return true;

            case MY_MENU_2:
                Toast.makeText(getApplicationContext(),"Cancle..",Toast.LENGTH_SHORT).show();
                Intent intentMain=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intentMain);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private class Connection extends AsyncTask {

        @Override
        protected Object doInBackground(Object... arg0) {
            isConnectedToServer();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(isValidURL) {
                mediaCell.save();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }else{
                etUrl.setError("Please enter valid URL");
            }
        }
    }

    public boolean isConnectedToServer() {
        try{
            URL myUrl = new URL(etUrl.getText().toString());
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(3500);
            connection.connect();
            isValidURL = true;
            Log.d("HTTPCLIENT", "SUCCCESS");

//            Toast.makeText(getApplicationContext(),"Save..",Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            isValidURL = false;
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddMediaCellActivity.this, MainActivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intent);
    }


}
