package student.avansti.hueapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import student.avansti.hueapp.data.DLamp;
import student.avansti.hueapp.parts.PartPhilipsHue;

public class LampListFragment extends Fragment implements LampAdapter.OnItemClickListener {

    private List<DLamp> lamps;
    private LampAdapter lampAdapter;
    private SwipeRefreshLayout swiperefresh_main;

    public LampListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_lamp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.swiperefresh_main = view.findViewById(R.id.swiperefresh_main);
        this.swiperefresh_main.setOnRefreshListener(this::refreshLamps);

        this.lamps = new ArrayList<>();
        this.lampAdapter = new LampAdapter(this.getContext(), this.lamps,this);
        RecyclerView list = view.findViewById(R.id.recyclerview_main);
        list.setLayoutManager(new LinearLayoutManager(this.getContext()));
        list.setAdapter(this.lampAdapter);

        this.refreshLamps();
    }

    @Override
    public void onItemClick(int clickedPosition) {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_lampListFragment_to_lampDetailFragment);
    }

    private void refreshLamps() {
        this.swiperefresh_main.setRefreshing(true);

        new Thread(() -> {
            this.lamps.clear();
            this.lamps.addAll(PartPhilipsHue.getInstance().getLamps());
            this.getActivity().runOnUiThread(() -> {
                this.lampAdapter.notifyDataSetChanged();
                this.swiperefresh_main.setRefreshing(false);
            });
        }).start();
    }
}