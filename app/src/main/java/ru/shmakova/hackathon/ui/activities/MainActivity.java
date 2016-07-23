package ru.shmakova.hackathon.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import ru.shmakova.hackathon.App;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.managers.DataManager;
import ru.shmakova.hackathon.ui.fragments.CardFragment;
import ru.shmakova.hackathon.ui.fragments.CardsResultFragment;
import ru.shmakova.hackathon.ui.fragments.MainFragment;
import ru.shmakova.hackathon.ui.fragments.PronunciationFragment;
import ru.shmakova.hackathon.ui.touch.ChoiceCallback;

public class MainActivity extends BaseActivity implements ChoiceCallback {

    private static final String LOG_TAG = MainActivity.class.getName();

    private int currentWordIndex;
    private List<String> allWords;
    private List<String> currentCheckWords;
    private List<String> currentWords;
    private DataManager dataManager;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);
        dataManager = DataManager.getInstance();

        allWords = dataManager.getPreferenceManager().getWords();

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

    /**
     * Opens fragment
     * @param position
     */
    public void onMenuItemClick(int position) {
        switch (position) {
            case 0:
                nextFragment(getCurrentWords(), true);
                break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame_layout, new PronunciationFragment())
                        .commit();
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

        if (putToStack){
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

    /**
     * Gets current words
     * @return
     */
    public List<String> getCurrentWords() {
        if (currentWords == null) {
            return dataManager.getPreferenceManager().getRandomWords(10);
        } else {
            return currentWords;
        }
    }
}
