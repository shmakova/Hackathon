package ru.shmakova.hackathon.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.ui.activities.MainActivity;
import ru.shmakova.hackathon.ui.adapters.MenuAdapter;


public class MainFragment extends BaseFragment {
    @BindView(R.id.main_menu)
    RecyclerView recyclerView;
    @BindView(R.id.main_coordinator_container)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.btnSettings)
    ImageButton btnSettings;

    private OnMenuItemClickListener onMenuItemClickListener;

    public interface OnMenuItemClickListener {
        void onMenuItemClick(String item);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        btnSettings.setOnClickListener(v ->{
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_frame_layout, new SettingsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        List<String> menuItems = Arrays.asList(getResources().getStringArray(R.array.menu_items));

        MenuAdapter menuAdapter = new MenuAdapter(menuItems, text -> {
            onMenuItemClickListener.onMenuItemClick(text);
        });

        recyclerView.setAdapter(menuAdapter);
    }


    private void setupToolbar() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof OnMenuItemClickListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    OnMenuItemClickListener.class.getName());
        }

        onMenuItemClickListener = (OnMenuItemClickListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onMenuItemClickListener = null;
    }
}
