package ru.shmakova.hackathon.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        List<String> menuItems = Arrays.asList(AppConfig.menuItems);
        MenuAdapter menuAdapter = new MenuAdapter(menuItems, new MenuAdapter.MenuViewHolder.OnItemCLickListener() {
            @Override
            public void onItemClick(int position) {
                ((MainActivity)getActivity()).menuClicked(position);
            }
        });
        recyclerView.setAdapter(menuAdapter);
    }


}
