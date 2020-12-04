package student.avansti.hueapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import codes.side.andcolorpicker.group.PickerGroup;
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar;
import codes.side.andcolorpicker.model.IntegerHSLColor;
import codes.side.andcolorpicker.view.picker.ColorSeekBar;
import student.avansti.hueapp.data.DLamp;
import student.avansti.hueapp.data.DLampState;
import student.avansti.hueapp.parts.PartPhilipsHue;

public class LampDetailFragment extends Fragment {

    private DLamp lamp;
    private ImageView image;
    private TextView state;
    private PickerGroup<IntegerHSLColor> colorPickerGroup = new PickerGroup<>();

    public LampDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lamp_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.image = view.findViewById(R.id.imageView_detail);
        this.lamp = new DLamp(); // todo: Get lamp
        this.image.setOnClickListener(this::onClick);

        TextView name = view.findViewById(R.id.name);
        TextView lastInstall = view.findViewById(R.id.lastInstall);
        TextView type = view.findViewById(R.id.type);
        TextView modelID = view.findViewById(R.id.modelID);
        this.state = view.findViewById(R.id.state);
        HSLColorPickerSeekBar hueSeekBar = view.findViewById(R.id.hueSeekBar);
        HSLColorPickerSeekBar satSeekBar = view.findViewById(R.id.satSeekBar);
        HSLColorPickerSeekBar ligSeekBar = view.findViewById(R.id.ligSeekBar);

        name.setText(" Lamp name: " + this.lamp.name);
        lastInstall.setText(" Version: " + this.lamp.swversion);
        type.setText(" Type: " + this.lamp.type);
        modelID.setText(" ModelID: " + this.lamp.modelid);

        if (this.lamp.state.on) {
            this.state.setText("ON");
            this.image.setColorFilter(this.lamp.state.getColor().asAndroidColor().toArgb());
            this.image.setImageResource(R.drawable.ic_lamp_on);
        }else{
            this.state.setText("OFF");
            this.image.setColorFilter(android.graphics.Color.WHITE);
            this.image.setImageResource(R.drawable.ic_lamp_off);
        }

        this.colorPickerGroup.registerPicker(hueSeekBar);
        this.colorPickerGroup.registerPicker(satSeekBar);
        this.colorPickerGroup.registerPicker(ligSeekBar);

        float[] hsl = new float[3];
        ColorUtils.colorToHSL(this.lamp.state.getColor().rgbAsInt(), hsl);

        IntegerHSLColor hslColor = new IntegerHSLColor();
        hslColor.setFloatH(hsl[0]);
        hslColor.setFloatS(hsl[1]);
        hslColor.setFloatL(hsl[2]);

        this.colorPickerGroup.setColor(hslColor);

        this.colorPickerGroup.addListener(new HSLColorPickerSeekBar.DefaultOnColorPickListener() {
            @Override
            public void onColorChanged(@NotNull ColorSeekBar<IntegerHSLColor> picker, @NotNull IntegerHSLColor color, int value) {
                float[] hsl = new float[] {
                        color.getFloatH(),
                        color.getFloatS(),
                        color.getFloatL()
                };

                LampDetailFragment.this.image.setColorFilter(ColorUtils.HSLToColor(hsl));
            }

            @Override
            public void onColorPicked(@NotNull ColorSeekBar<IntegerHSLColor> picker, @NotNull IntegerHSLColor color, int value, boolean fromUser) {

                if(fromUser) {
                    float[] hsl = new float[] {
                            color.getFloatH(),
                            color.getFloatS(),
                            color.getFloatL()
                    };

                    LampDetailFragment.this.setLampColor(Color.fromRgbInt(ColorUtils.HSLToColor(hsl)));
                }

            }
        });
    }

    public void onClick(View view){
        new Thread(() -> {
            PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
            partPhilipsHue.setLampPowerState(this.lamp, !this.lamp.state.on);
            this.lamp = partPhilipsHue.getLampById(this.lamp.id);

            this.getActivity().runOnUiThread(() -> {
                if(this.lamp.state.on) {
                    this.state.setText("ON");
                    this.image.setColorFilter(lamp.state.getColor().asAndroidColor().toArgb());
                    this.image.setImageResource(R.drawable.ic_lamp_on);
                }
                else {
                    state.setText("OFF");
                    this.image.setColorFilter(android.graphics.Color.WHITE);
                    this.image.setImageResource(R.drawable.ic_lamp_off);
                }
            });
        }).start();
    }

    private void setLampColor(Color color) {
        new Thread(() -> {
            PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
            partPhilipsHue.setLampColor(this.lamp, color);
            this.lamp = partPhilipsHue.getLampById(this.lamp.id);

            this.getActivity().runOnUiThread(() -> {
                if(this.lamp.state.on) {
                    this.state.setText("ON");
                    this.image.setColorFilter(lamp.state.getColor().asAndroidColor().toArgb());
                    this.image.setImageResource(R.drawable.ic_lamp_on);
                }
                else {
                    state.setText("OFF");
                    this.image.setColorFilter(android.graphics.Color.WHITE);
                    this.image.setImageResource(R.drawable.ic_lamp_off);
                }
            });
        }).start();

        float[] hsl = new float[3];
        ColorUtils.colorToHSL(color.rgbAsInt(), hsl);

        IntegerHSLColor hslColor = new IntegerHSLColor();
        hslColor.setFloatH(hsl[0]);
        hslColor.setFloatS(hsl[1]);
        hslColor.setFloatL(hsl[2]);

        colorPickerGroup.setColor(hslColor);
    }
}