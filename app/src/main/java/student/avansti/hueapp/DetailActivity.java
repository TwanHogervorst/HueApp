package student.avansti.hueapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import student.avansti.hueapp.data.DLamp;
import student.avansti.hueapp.data.DLampState;

public class DetailActivity extends AppCompatActivity {

    public static final String detail = "Philips lamp";
    private DLamp lamp = new DLamp();
    private ImageView image;
    private TextView state;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image = findViewById(R.id.imageView_detail);
        lamp = (DLamp) getIntent().getSerializableExtra(detail);

        TextView name = findViewById(R.id.name);
        TextView lastInstall = findViewById(R.id.lastInstall);
        TextView type = findViewById(R.id.type);
        TextView modelID = findViewById(R.id.modelID);
        state = findViewById(R.id.state);
        image.setColorFilter(Color.WHITE);

        name.setText(" Lamp name: " + lamp.name);
        lastInstall.setText(" Version: " + lamp.swversion);
        type.setText(" Type: " + lamp.type);
        modelID.setText(" ModelID: " + lamp.modelid);
        if (lamp.state.on) {
            state.setText("ON");
        }else{
            state.setText("OFF");
        }
    }

    public void onClick(View view){
        lamp.state.on = !lamp.state.on;
        if (lamp.state.on){
            int rgb = Color.HSVToColor(new float[] {
                    (float)Utility.map(lamp.state.hue, 0, 65535, 0, 360),
                    (float)Utility.map(lamp.state.sat,0, 254,0,1),
                    (float)Utility.map(lamp.state.bri,1,254,0, 1)
            });

            image.setColorFilter(rgb);
            state.setText("ON");
        }else{
            image.setColorFilter(Color.WHITE);
            state.setText("OFF");
        }
    }
}
