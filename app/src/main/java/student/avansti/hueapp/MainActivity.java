package student.avansti.hueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Dispatcher;
import student.avansti.hueapp.data.DLamp;
import student.avansti.hueapp.parts.PartPhilipsHue;

public class MainActivity extends AppCompatActivity implements LampAdapter.OnItemClickListener{

    private List<DLamp> lamps;
    private LampAdapter lampAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lamps = new ArrayList<>();
        lampAdapter = new LampAdapter(this,lamps,this);

        new Thread(() -> {
            this.lamps.addAll(new PartPhilipsHue("10.0.1.12:8000", "newdeveloper").getLamps());
            this.runOnUiThread(() -> {
                this.lampAdapter.notifyDataSetChanged();
            });
        }).start();

        RecyclerView list = findViewById(R.id.recyclerview_main);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(lampAdapter);
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.detail,lamps.get(clickedPosition));
        startActivity(intent);
    }
}