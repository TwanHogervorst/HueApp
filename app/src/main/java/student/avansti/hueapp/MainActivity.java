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

public class MainActivity extends AppCompatActivity implements LampAdapter.OnItemClickListener{

    private List<DLamp> lamps;
    private LampAdapter lampAdapter;
    private SwipeRefreshLayout swiperefresh_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swiperefresh_main = this.findViewById(R.id.swiperefresh_main);
        swiperefresh_main.setOnRefreshListener(this::refreshLamps);

        this.lamps = new ArrayList<>();
        this.lampAdapter = new LampAdapter(this,lamps,this);
        RecyclerView list = findViewById(R.id.recyclerview_main);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(lampAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.refreshLamps();
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.detail,lamps.get(clickedPosition));
        startActivity(intent);
    }

    private void refreshLamps() {
        this.swiperefresh_main.setRefreshing(true);

        new Thread(() -> {
            this.lamps.clear();
            this.lamps.addAll(new PartPhilipsHue("10.0.1.12:80", "newdeveloper").getLamps());
            this.runOnUiThread(() -> {
                this.lampAdapter.notifyDataSetChanged();
                this.swiperefresh_main.setRefreshing(false);
            });
        }).start();
    }
}