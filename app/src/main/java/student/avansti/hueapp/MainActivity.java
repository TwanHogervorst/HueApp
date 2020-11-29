package student.avansti.hueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LampAdapter.OnItemClickListener{

    private ArrayList<PhilipsLamp> lamps;
    private LampAdapter lampAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lamps = new ArrayList<>();
        lamps.add(new PhilipsLamp("lamp","on", "red", "30 nov", "lightbulb", "6789"));
        lamps.add(new PhilipsLamp("lamp two","off", "yellow", "29 nov", "lightbulb", "1234"));
        lampAdapter = new LampAdapter(this,lamps,this);

        RecyclerView list = findViewById(R.id.recyclerview_main);
        list.setAdapter(lampAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.detail,lamps.get(clickedPosition));
        startActivity(intent);
    }
}