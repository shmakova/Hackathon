package ru.shmakova.hackathon.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.ui.touch.AnimationTouchListener;
import ru.shmakova.hackathon.ui.touch.ChoiceCallback;

import static java.util.Arrays.asList;

public class CardFragment extends BaseFragment {

    private static final String LOG_TAG = CardFragment.class.getName();

    public static final String ARG_WORD = "word";
    public static final String ARG_WORD_NUM = "word_num";
    public static final String ARG_WORD_NUM_TOTAL = "word_num_total";

    private ChoiceCallback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_cards, container, false);

        ProgressBar pbWordsProgress = (ProgressBar) result.findViewById(R.id.pbWordsProgress);

        View toDrag = result.findViewById(R.id.toDrag);

        TextView tvWord = (TextView) toDrag.findViewById(R.id.tvCardWord);
        int wordNum;
        int wordsTotal;
        String word;

        Bundle args = getArguments();
        if (args != null) {
            wordNum = args.getInt(ARG_WORD_NUM);
            wordsTotal = args.getInt(ARG_WORD_NUM_TOTAL);
            word = args.getString(ARG_WORD);

            callback = (ChoiceCallback) getActivity();

            Log.d(LOG_TAG,ARG_WORD_NUM + " " + ARG_WORD_NUM_TOTAL);
            pbWordsProgress.setMax(wordsTotal);
            pbWordsProgress.setProgress(wordNum);
            tvWord.setText(word);
        }

        toDrag.setOnTouchListener(
                new AnimationTouchListener((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE), toDrag, callback)
        );

        return result;
    }
}
