package ru.shmakova.hackathon.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daprlabs.cardstack.SwipeDeck;

import java.util.List;

import butterknife.BindView;
import ru.shmakova.hackathon.App;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.managers.DataManager;
import ru.shmakova.hackathon.ui.activities.MainActivity;
import ru.shmakova.hackathon.ui.adapters.WordsAdapter;
import ru.shmakova.hackathon.utils.AppConfig;
import ru.yandex.speechkit.SpeechKit;

/**
 * Created by shmakova on 23.07.16.
 */

public class PronunciationFragment extends BaseFragment {
    @BindView(R.id.swipe_deck)
    SwipeDeck cardStack;

    private MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechKit.getInstance().configure(getContext(), AppConfig.YANDEX_SPEECH_KIT_API_KEY);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pronunciation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        activity = ((MainActivity) getActivity());
        final List<String> words = DataManager.getInstance().getPreferenceManager().getRandomWords(10);
        cardStack = (SwipeDeck) view.findViewById(R.id.swipe_deck);

        final WordsAdapter adapter = new WordsAdapter(words, App.getContext());
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });
    }
}
