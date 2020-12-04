package student.avansti.hueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Dispatcher;
import student.avansti.hueapp.data.DLamp;
import student.avansti.hueapp.parts.PartPhilipsHue;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);

        PartPhilipsHue.getInstance().setBridge( prefs.getString("ip", "localhost"), prefs.getString("username", "newdeveloper"));

        this.setContentView(R.layout.activity_main);
    }


}