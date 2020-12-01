package student.avansti.hueapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import student.avansti.hueapp.data.DLamp;

public class DetailActivity extends AppCompatActivity {

    public static final String detail = "Philips lamp";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DLamp lamp = (DLamp) getIntent().getSerializableExtra(detail);

        TextView name = findViewById(R.id.name);
        TextView state = findViewById(R.id.state);
        TextView color = findViewById(R.id.color);
        TextView lastInstall = findViewById(R.id.lastInstall);
        TextView type = findViewById(R.id.type);
        TextView modelID = findViewById(R.id.modelID);

        name.setText(lamp.name);
        state.setText(lamp.state.toJson());
        int rgb = Color.HSVToColor(new float[] {
                (float)Utility.map(lamp.state.hue, 0, 65535, 0, 360),
                (float)Utility.map(lamp.state.sat,0, 254,0,1),
                (float) Utility.map(lamp.state.bri,1,254,0, 1)
        });

        color.setText(Color.valueOf(rgb).toString());
        lastInstall.setText(lamp.swversion);
        type.setText(lamp.type);
        modelID.setText(lamp.modelid);

    }
}
