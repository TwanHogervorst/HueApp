package student.avansti.hueapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import codes.side.andcolorpicker.converter.IntegerHSLColorConverter;
import codes.side.andcolorpicker.group.PickerGroup;
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar;
import codes.side.andcolorpicker.model.IntegerColor;
import codes.side.andcolorpicker.model.IntegerHSLColor;
import codes.side.andcolorpicker.model.IntegerRGBColor;
import codes.side.andcolorpicker.view.picker.ColorSeekBar;
import student.avansti.hueapp.data.DLamp;
import student.avansti.hueapp.parts.PartLog;
import student.avansti.hueapp.parts.PartPhilipsHue;
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
        HSLColorPickerSeekBar hueSeekBar = findViewById(R.id.hueSeekBar);
        HSLColorPickerSeekBar satSeekBar = findViewById(R.id.satSeekBar);
        HSLColorPickerSeekBar ligSeekBar = findViewById(R.id.ligSeekBar);

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
        image.setColorFilter(lamp.state.getColor().asAndroidColor().toArgb());
        PickerGroup<IntegerHSLColor> group = new PickerGroup<>();
        group.registerPicker(hueSeekBar);
        group.registerPicker(satSeekBar);
        group.registerPicker(ligSeekBar);

        group.addListener(new HSLColorPickerSeekBar.DefaultOnColorPickListener() {
            @Override
            public void onColorChanged(@NotNull ColorSeekBar<IntegerHSLColor> picker, @NotNull IntegerHSLColor color, int value) {
                float[] hsl = new float[] {
                        color.getFloatH(),
                        color.getFloatS(),
                        color.getFloatL()
                };

                image.setColorFilter(ColorUtils.HSLToColor(hsl));
            }

            @Override
            public void onColorPicked(@NotNull ColorSeekBar<IntegerHSLColor> picker, @NotNull IntegerHSLColor color, int value, boolean fromUser) {

                new Thread(() -> {
                    PartPhilipsHue partPhilipsHue = new PartPhilipsHue("192.168.1.43:80", "newdeveloper");

                    float[] hsl = new float[] {
                            color.getFloatH(),
                            color.getFloatS(),
                            color.getFloatL()
                    };

                    partPhilipsHue.setLampColor(lamp.id,Color.fromRgbInt(ColorUtils.HSLToColor(hsl)));
                }).start();

            }
        });

        float[] hsl = new float[3];
        ColorUtils.colorToHSL(lamp.state.getColor().rgbAsInt(), hsl);

        IntegerHSLColor hslColor = new IntegerHSLColor();
        hslColor.setFloatH(hsl[0]);
        hslColor.setFloatS(hsl[1]);
        hslColor.setFloatL(hsl[2]);

        group.setColor(hslColor);

    }
}
