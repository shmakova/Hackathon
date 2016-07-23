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
import ru.shmakova.hackathon.ui.fragments.MainFragment;
import ru.shmakova.hackathon.ui.touch.ChoiceCallback;
import timber.log.Timber;

import static java.util.Arrays.asList;

public class MainActivity extends BaseActivity implements ChoiceCallback{

    private List<String> words = asList("Word1", "Word2", "Word3");
    private static final String LOG_TAG = MainActivity.class.getName();

    private int currentWordIndex = 0;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(getLayoutInflater().inflate(R.layout.activity_main, null));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new MainFragment())
                    .commit();
        }
    }

    public void menuClicked(int position){
        switch (position){
            case 0:
                nextFragment(words);
                break;
        }
        Timber.d("Position click!" + position);
    }

    private void nextFragment(List<String> words) {

        if (currentWordIndex >= words.size()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new CardsResultFragment())
                    .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, getWordFragment(currentWordIndex, words.size(), words.get(currentWordIndex)))
                    .commitAllowingStateLoss();
            currentWordIndex++;
        }
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
        nextFragment(words);
    }

    @Override
    public void cancel(String word) {
        nextFragment(words);
    }
}
