package ru.shmakova.hackathon.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.shmakova.hackathon.App;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.StatsHandler;
import ru.shmakova.hackathon.managers.DataManager;
import ru.shmakova.hackathon.model.CardChoice;
import ru.shmakova.hackathon.network.DictionaryService;
import ru.shmakova.hackathon.network.ServiceGenerator;
import ru.shmakova.hackathon.network.models.Lookup;
import ru.shmakova.hackathon.ui.touch.AnimationTouchListener;
import ru.shmakova.hackathon.ui.touch.ChoiceCallback;
import ru.shmakova.hackathon.utils.AppConfig;
import timber.log.Timber;

public class CardFragment extends BaseFragment implements ChoiceCallback {

    private static final String LOG_TAG = CardFragment.class.getName();
    public static final String ARG_CURRENT_COUNTER = "currentCounter";
    public static final String ARG_WORDS = "words";

    private int currentWordIndex;
    private ArrayList<CardChoice> cardChoices;
    private List<String> currentWords;

    private FragmentManager fm;
    private StatsHandler handler;

    @BindView(R.id.tvCardWord)
    TextView tvWord;

    @BindView(R.id.pbWordsProgress)
    ProgressBar pbWordsProgress;

    @BindView(R.id.toDrag)
    View toDrag;

    @BindView(R.id.cancel)
    View vCancel;

    @BindView(R.id.tvCardWordTranslate)
    TextView tvCardWordTranslate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_cards, container, false);

        handler = App.from(getContext()).getStatsHandler();
        fm = getFragmentManager();

        cardChoices = new ArrayList<>();
        currentWords = DataManager.getInstance().getPreferenceManager().getRandomWords(10);
        return result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pbWordsProgress.setMax(currentWords.size());
        pbWordsProgress.setProgress(currentWordIndex);
        tvWord.setText(currentWords.get(currentWordIndex));

        vCancel.setOnClickListener(v -> {
            // Я бы избегал управления фрагментами напрямую из фрагментов, если речь не о дочерних фрагментах.
            // Посмотрите паттер Mediator
                fm.beginTransaction()
                        .replace(R.id.main_frame_layout, new MainFragment())
                        .commit();
        });


        tvWord.setOnClickListener(v -> {
            // Создаем каждый раз?
            DictionaryService service = ServiceGenerator.createService(DictionaryService.class);
            Map<String, String> map = new HashMap<>();
            map.put("key", AppConfig.DICTIONARY_API_KEY);
            map.put("lang", "en-ru");
            map.put("text", currentWords.get(currentWordIndex));
            Call<Lookup> call = service.lookup(map);
            call.enqueue(new Callback<Lookup>() {
                @Override
                public void onResponse(Call<Lookup> call, Response<Lookup> response) {
                    String firstTranslation = response.body().def.get(0).tr.get(0).text;
                    tvCardWordTranslate.setText(firstTranslation);
                }

                @Override
                public void onFailure(Call<Lookup> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        });
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        toDrag.setOnTouchListener(new AnimationTouchListener(windowManager, toDrag, this));

    }

    @Override
    public void result(CardChoice choice) {
        cardChoices.add(choice);
        handler.add(choice);
        nextStep();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_CURRENT_COUNTER, currentWordIndex);
        outState.putStringArrayList(ARG_WORDS, (ArrayList<String>) currentWords);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState  != null){
            currentWordIndex = savedInstanceState.getInt(ARG_CURRENT_COUNTER);
            currentWords = savedInstanceState.getStringArrayList(ARG_WORDS);
            System.out.println();
        }

    }

    private void nextStep() {
        currentWordIndex++;
        if (currentWordIndex < currentWords.size()) {
            tvCardWordTranslate.setText("");
            pbWordsProgress.setProgress(currentWordIndex);
            tvWord.setText(currentWords.get(currentWordIndex));
        } else {

            Fragment fragment = new CardsResultFragment();
            Bundle args = new Bundle();
            args.putInt(CardsResultFragment.ARG_WORDS, currentWords.size());

            int successWords = getKnowsWords();
            args.putInt(CardsResultFragment.ARG_WORDS_KNOWN, successWords);
            fragment.setArguments(args);
            fm.beginTransaction()
                    .replace(R.id.main_frame_layout, fragment)
                    .commit();
        }
    }

    private int getKnowsWords() {
        int counter = 0;
        for (int i = 0; i < cardChoices.size(); i++) {
            if (cardChoices.get(i).isSuccess()) {
                counter++;
            }
        }
        return counter;
    }

}
