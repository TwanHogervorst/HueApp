package student.avansti.hueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
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

        PartPhilipsHue.getInstance().setBridge("192.168.1.43:80", "newdeveloper");

        this.setContentView(R.layout.activity_main);
    }


}