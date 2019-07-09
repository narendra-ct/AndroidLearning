package com.example.multipleviewsrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.multipleviewsrecyclerview.model.NonCuratedItem;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ONNClickListener {

    private static final String TAG = "MainActivity";

    private NonCuratedItem newItem = new NonCuratedItem();
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();

        //set interface or allocate interface
        // getSupportActionBar().setTitle("Page Title");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
        // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_name) {
            navigateToNext();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private void navigateToNext() {

        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: ");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(this, newItem, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onAddImageClick() {
        Toast.makeText(this,"onAddImageClick",Toast.LENGTH_LONG).show();
        Options options = Options.init()
                .setRequestCode(100)                                                 //Request code for activity results
                .setCount(6)                                                         //Number of images to restict selection count
                .setFrontfacing(false)                                                //Front Facing camera on start
                .setImageQuality(ImageQuality.HIGH)                                  //Image Quality
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)           //Orientaion
                .setPath("/pix/images")                                              //Custom Path For Image Storage
                .setPreSelectedUrls(newItem.getImages() != null ? newItem.getImages() : new ArrayList<>());

        Pix.start(MainActivity.this, options);
    }

    @Override
    public void onDeleteImageClick(Integer index) {
        Toast.makeText(this,"onDeleteImageClick" + index,Toast.LENGTH_LONG).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            Log.d(TAG, "returnValue " + returnValue);

            // update in UI
            newItem = adapter.getUpdatedItem();
            newItem.setImages(returnValue);
            adapter.setUpdatedNewItem(newItem);
            adapter.notifyDataSetChanged();

        }
    }
}
