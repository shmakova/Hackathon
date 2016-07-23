package ru.shmakova.hackathon.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.shmakova.hackathon.App;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.StatsHandler;
import ru.shmakova.hackathon.model.CardChoice;


public class SettingsFragment extends BaseFragment {

    private StatsHandler statsHandler;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        statsHandler = App.from(context).getStatsHandler();
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        List<CardChoice> choices = statsHandler.getChoices();
        for (int i = 0, size = choices.size(); i < size; i++) {
            CardChoice currentChoice = choices.get(i);
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.CENTER);
            String status = currentChoice.getWord() + " : " +
                    getStatus(currentChoice);

            tv.setText(status);
            ((ViewGroup)view).addView(tv);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    private String getStatus(CardChoice currentChoice) {

        return currentChoice.isSuccess() ? "успех" : "провал";
    }
}
