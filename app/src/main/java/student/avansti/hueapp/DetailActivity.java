package student.avansti.hueapp;

import android.os.Bundle;
import android.util.Log;
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
        ImageView image = findViewById(R.id.imageView_detail);
        HSLColorPickerSeekBar hueSeekBar = findViewById(R.id.hueSeekBar);
        HSLColorPickerSeekBar satSeekBar = findViewById(R.id.satSeekBar);
        HSLColorPickerSeekBar ligSeekBar = findViewById(R.id.ligSeekBar);

        name.setText("Lamp name: " + lamp.name);
        state.setText(lamp.state.toJson());

        color.setText(lamp.state.getColor().toString());

        image.setColorFilter(lamp.state.getColor().asAndroidColor().toArgb());
        lastInstall.setText("Version: " + lamp.swversion);
        type.setText("Type: " + lamp.type);
        modelID.setText("ModelID: " + lamp.modelid);

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
