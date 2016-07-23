package ru.shmakova.hackathon.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.shmakova.hackathon.App;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.model.WordsContainer;
import ru.shmakova.hackathon.ui.fragments.CardFragment;
import ru.shmakova.hackathon.ui.fragments.CardsResultFragment;
import ru.shmakova.hackathon.ui.fragments.MainFragment;
import ru.shmakova.hackathon.ui.touch.ChoiceCallback;

public class MainActivity extends BaseActivity implements ChoiceCallback {

    private static final String LOG_TAG = MainActivity.class.getName();

    private int currentWordIndex;
    private WordsContainer allWords;
    private List<String> currentCheckWords;
    private List<String> currentWords;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        ObjectMapper mapper = new ObjectMapper();
        try {
            allWords = mapper.readValue(getResources().openRawResource(R.raw.words), WordsContainer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(getLayoutInflater().inflate(R.layout.activity_main, null));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new MainFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    public void menuClicked(int position) {
        switch (position) {
            case 0:
                nextFragment(getCurrentWords(), true);
                break;
        }
    }

    private void nextFragment(List<String> words, boolean putToStack) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (currentWordIndex >= words.size()) {
            ft.replace(R.id.main_frame_layout, new CardsResultFragment());
        } else {
            ft.replace(R.id.main_frame_layout, getWordFragment(currentWordIndex, words.size(), words.get(currentWordIndex)));
            currentWordIndex++;
        }
        if(putToStack){
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    private Fragment getWordFragment(int wordNum, int wordsTotal, String word) {
        Fragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(CardFragment.ARG_WORD, word);
        args.putInt(CardFragment.ARG_WORD_NUM, wordNum);
        args.putInt(CardFragment.ARG_WORD_NUM_TOTAL, wordsTotal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void success(String word) {
        nextFragment(getCurrentWords(), false);
    }

    @Override
    public void cancel(String word) {
        nextFragment(getCurrentWords(), false);
    }

    public void reset() {
        currentWordIndex = 0;
        currentWords = null;
    }

    public List<String> getCurrentWords() {
        if (currentWords == null) {
            return randomWords(allWords.getEn(), 10);
        } else {
            return currentWords;
        }
    }

    private List<String> randomWords(List<String> allWords, int count) {
        List<String> result = new ArrayList<>();
        int allWordsSize = allWords.size();
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
