package ru.shmakova.hackathon.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

import ru.shmakova.hackathon.App;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.ui.fragments.CardFragment;
import ru.shmakova.hackathon.ui.fragments.CardsResultFragment;
import ru.shmakova.hackathon.ui.touch.ChoiceCallback;

import static java.util.Arrays.asList;

public class MainActivity extends BaseActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    private int currentWordIndex = 0;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(getLayoutInflater().inflate(R.layout.activity_main, null));

        List<String> words = asList("Word1", "Word2", "Word3");

        if (savedInstanceState == null) {
            ChoiceCallback callback = new ChoiceCallback() {
                @Override
                public void success(String word) {
                    Log.d(LOG_TAG, "Success " + word);
                    nextFragment(words, this);
                }

                @Override
                public void cancel(String word) {
                    Log.d(LOG_TAG, "Cancel " + word);
                    nextFragment(words, this);
                }
            };
            nextFragment(words, callback);
        }
    }

    private void nextFragment(List<String> words, ChoiceCallback callback) {

        if (currentWordIndex >= words.size()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new CardsResultFragment())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, getWordFragment(currentWordIndex, words.size(), words.get(currentWordIndex), callback))
                    .commit();
            currentWordIndex++;
        }
    }


    private Fragment getWordFragment(int wordNum, int wordsTotal, String word, ChoiceCallback callback) {
        Fragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(CardFragment.ARG_WORD, word);
        args.putInt(CardFragment.ARG_WORD_NUM, wordNum);
        args.putInt(CardFragment.ARG_WORD_NUM_TOTAL, wordsTotal);
        args.putSerializable(CardFragment.ARGS_CALLBACK, callback);
        fragment.setArguments(args);
        return fragment;
    }
}
