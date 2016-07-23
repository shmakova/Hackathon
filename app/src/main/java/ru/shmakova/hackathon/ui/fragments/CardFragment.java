package ru.shmakova.hackathon.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.managers.DataManager;
import ru.shmakova.hackathon.model.CardChoice;
import ru.shmakova.hackathon.model.WordsContainer;
import ru.shmakova.hackathon.network.DictionaryService;
import ru.shmakova.hackathon.network.ServiceGenerator;
import ru.shmakova.hackathon.network.models.Lookup;
import ru.shmakova.hackathon.ui.touch.AnimationTouchListener;
import ru.shmakova.hackathon.ui.touch.ChoiceCallback;
import ru.shmakova.hackathon.utils.AppConfig;

public class CardFragment extends BaseFragment implements ChoiceCallback {

    private static final String LOG_TAG = CardFragment.class.getName();

    private int currentWordIndex;
    private ArrayList<CardChoice> cardChoices;
    private List<String> currentWords;

    private FragmentManager fm;

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

        cardChoices = new ArrayList<>();
        fm = getFragmentManager();

        currentWords = DataManager.getInstance().getPreferenceManager().getRandomWords(10);
        return result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pbWordsProgress.setMax(currentWords.size());
        pbWordsProgress.setProgress(currentWordIndex);
        tvWord.setText(currentWords.get(currentWordIndex));

        vCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction()
                        .replace(R.id.main_frame_layout, new MainFragment())
                        .commit();
            }
        });


        tvWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DictionaryService service = ServiceGenerator.createService(DictionaryService.class);
                Map<String, String> map = new HashMap<>();
                map.put("key", AppConfig.DICTIONARY_API_KEY);
                map.put("lang", "en-ru");
                map.put("text", currentWords.get(currentWordIndex));
                Call<Lookup> call = service.lookup(map);
                call.enqueue(new Callback<Lookup>() {
                    @Override
                    public void onResponse(Call<Lookup> call, Response<Lookup> response) {
                        tvCardWordTranslate.setText(response.body().def.get(0).tr.get(0).text);
                    }
                    @Override
                    public void onFailure(Call<Lookup> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
        toDrag.setOnTouchListener(
                new AnimationTouchListener((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE), toDrag, this)
        );

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

    @Override
    public void result(CardChoice choice) {
        cardChoices.add(choice);
        nextStep();
    }

    private List<String> randomWords(List<String> allWords, int count) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            addWord(allWords, result);
        }
        return result;
    }

    private void addWord(List<String> all, List<String> result) {
        if (all.size() == result.size()) {
            return;
        }
        int random = (int) (Math.random() * all.size());
        String pickedWord = all.get(random);
        if (!result.contains(pickedWord)) {
            result.add(pickedWord);
        } else {
            addWord(all, result);
        }
    }
}
