package student.avansti.hueapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public static final String detail = "Philips lamp";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        PhilipsLamp lamp = (PhilipsLamp) getIntent().getSerializableExtra(detail);

        TextView name = findViewById(R.id.name);
        TextView state = findViewById(R.id.state);
        TextView color = findViewById(R.id.color);
        TextView lastInstall = findViewById(R.id.lastInstall);
        TextView type = findViewById(R.id.type);
        TextView modelID = findViewById(R.id.modelID);

        name.setText(lamp.getLampName());
        state.setText(lamp.getState());
        color.setText(lamp.getColor());
        lastInstall.setText(lamp.getLastinstall());
        type.setText(lamp.getType());
        modelID.setText(lamp.getModelID());

    }
}
