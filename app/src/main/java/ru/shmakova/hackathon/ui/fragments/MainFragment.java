package ru.shmakova.hackathon.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.ui.activities.MainActivity;
import ru.shmakova.hackathon.ui.adapters.MenuAdapter;
import ru.shmakova.hackathon.utils.AppConfig;
import timber.log.Timber;


public class MainFragment extends BaseFragment {
    @BindView(R.id.main_menu)
    RecyclerView recyclerView;
    @BindView(R.id.main_coordinator_container)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private MainActivity activity;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        activity = ((MainActivity)getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        List<String> menuItems = Arrays.asList(AppConfig.menuItems);
        MenuAdapter menuAdapter = new MenuAdapter(menuItems, new MenuAdapter.MenuViewHolder.OnItemCLickListener() {
            @Override
            public void onItemClick(int position) {
                activity.menuClicked(position);
            }
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
    public void onResume() {
        super.onResume();
    }
}
