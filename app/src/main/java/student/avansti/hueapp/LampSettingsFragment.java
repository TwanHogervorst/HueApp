package student.avansti.hueapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import student.avansti.hueapp.parts.PartPhilipsHue;

public class LampSettingsFragment extends Fragment {

    private EditText entry_username;
    private EditText entry_ip;

    public LampSettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lamp_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        entry_username = view.findViewById(R.id.username);
        entry_ip = view.findViewById(R.id.ip);
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences prefs = this.requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (!entry_username.getText().toString().equals("")) {
            editor.putString("username", entry_username.getText().toString());
        }
        if (!entry_ip.getText().toString().equals("")) {
            editor.putString("ip", entry_ip.getText().toString());
        }
        editor.apply();
        PartPhilipsHue.getInstance().setBridge( prefs.getString("ip", "localhost"), prefs.getString("username", "newdeveloper"));

    }
}
