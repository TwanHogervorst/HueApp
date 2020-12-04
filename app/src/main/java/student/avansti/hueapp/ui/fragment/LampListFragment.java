package student.avansti.hueapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import student.avansti.hueapp.R;
import student.avansti.hueapp.ui.adapter.LampAdapter;
import student.avansti.hueapp.viewmodels.LampViewModel;

public class LampListFragment extends Fragment {

    private LampViewModel lampViewModel;
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
        view.findViewById(R.id.imageView2).setOnClickListener(this::onClick);

        this.lampViewModel = new ViewModelProvider(this.requireActivity()).get(LampViewModel.class);
        this.lampViewModel.getLamps().observe(getViewLifecycleOwner(), lampList -> {
            this.lampAdapter.notifyDataSetChanged();
            this.swiperefresh_main.setRefreshing(false);
        });

        this.lampViewModel.getSelectedLamp().observe(getViewLifecycleOwner(), selectedLamp -> {
            if(selectedLamp != null) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_lampListFragment_to_lampDetailFragment);
            }
        });

        this.swiperefresh_main = view.findViewById(R.id.swiperefresh_main);
        this.swiperefresh_main.setOnRefreshListener(this::refreshLamps);

        this.lampAdapter = new LampAdapter(this.lampViewModel);
        RecyclerView list = view.findViewById(R.id.recyclerview_main);
        list.setLayoutManager(new LinearLayoutManager(this.getContext()));
        list.setAdapter(this.lampAdapter);

        this.refreshLamps();
    }

    public void onClick(View view){
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_lampListFragment_to_lampSettingsFragment);
    }

    private void refreshLamps() {
        this.swiperefresh_main.setRefreshing(true);
        this.lampViewModel.refreshLamps();
    }
}