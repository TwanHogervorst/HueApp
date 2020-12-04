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
    private PickerGroup<IntegerHSLColor> colorPickerGroup = new PickerGroup<>();

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
        HSLColorPickerSeekBar hueSeekBar = findViewById(R.id.hueSeekBar);
        HSLColorPickerSeekBar satSeekBar = findViewById(R.id.satSeekBar);
        HSLColorPickerSeekBar ligSeekBar = findViewById(R.id.ligSeekBar);

        name.setText(" Lamp name: " + lamp.name);
        lastInstall.setText(" Version: " + lamp.swversion);
        type.setText(" Type: " + lamp.type);
        modelID.setText(" ModelID: " + lamp.modelid);

        if (lamp.state.on) {
            state.setText("ON");
            image.setColorFilter(lamp.state.getColor().asAndroidColor().toArgb());
        }else{
            state.setText("OFF");
            image.setColorFilter(android.graphics.Color.WHITE);
        }

        colorPickerGroup.registerPicker(hueSeekBar);
        colorPickerGroup.registerPicker(satSeekBar);
        colorPickerGroup.registerPicker(ligSeekBar);

        float[] hsl = new float[3];
        ColorUtils.colorToHSL(this.lamp.state.getColor().rgbAsInt(), hsl);

        IntegerHSLColor hslColor = new IntegerHSLColor();
        hslColor.setFloatH(hsl[0]);
        hslColor.setFloatS(hsl[1]);
        hslColor.setFloatL(hsl[2]);

        colorPickerGroup.setColor(hslColor);

        colorPickerGroup.addListener(new HSLColorPickerSeekBar.DefaultOnColorPickListener() {
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

                if(fromUser) {
                    float[] hsl = new float[] {
                            color.getFloatH(),
                            color.getFloatS(),
                            color.getFloatL()
                    };

                    DetailActivity.this.setLampColor(Color.fromRgbInt(ColorUtils.HSLToColor(hsl)));
                }

            }
        });
    }

    public void onClick(View view){
        lamp.state.on = !lamp.state.on;
        if (lamp.state.on){
            state.setText("ON");

            new Thread(() -> {
                PartPhilipsHue partPhilipsHue = new PartPhilipsHue("10.0.1.12:80", "newdeveloper");
                partPhilipsHue.setLampPowerState(this.lamp, true);
                this.lamp = partPhilipsHue.getLampById(this.lamp.id);

                this.runOnUiThread(() -> {
                    this.image.setColorFilter(lamp.state.getColor().asAndroidColor().toArgb());
                });
            }).start();
        }else{
            image.setColorFilter(android.graphics.Color.WHITE);
            state.setText("OFF");

            new Thread(() -> {
                PartPhilipsHue partPhilipsHue = new PartPhilipsHue("10.0.1.12:80", "newdeveloper");
                partPhilipsHue.setLampPowerState(this.lamp, false);
                this.lamp = partPhilipsHue.getLampById(this.lamp.id);
            }).start();
        }
    }

    private void setLampColor(Color color) {
        new Thread(() -> {
            PartPhilipsHue partPhilipsHue = new PartPhilipsHue("10.0.1.12:80", "newdeveloper");
            partPhilipsHue.setLampColor(this.lamp, color);
            this.lamp = partPhilipsHue.getLampById(this.lamp.id);
        }).start();

        float[] hsl = new float[3];
        ColorUtils.colorToHSL(color.rgbAsInt(), hsl);

        IntegerHSLColor hslColor = new IntegerHSLColor();
        hslColor.setFloatH(hsl[0]);
        hslColor.setFloatS(hsl[1]);
        hslColor.setFloatL(hsl[2]);

        colorPickerGroup.setColor(hslColor);

        this.image.setColorFilter(color.asAndroidColor().toArgb());
        this.state.setText("ON");
    }
}
