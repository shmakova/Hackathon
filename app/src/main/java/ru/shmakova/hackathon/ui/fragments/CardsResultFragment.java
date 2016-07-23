package ru.shmakova.hackathon.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.ui.activities.MainActivity;

public class CardsResultFragment extends BaseFragment {

    public static final String ARG_WORDS = "words_count";
    public static final String ARG_WORDS_KNOWN = "words_count_known";

    @BindView(R.id.tvResult)
    TextView tvResult;

    @BindView(R.id.btnReturnToMenu)
    Button btnReturnToMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cards_result,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            int totalWords = args.getInt(ARG_WORDS);
            int knownWords = args.getInt(ARG_WORDS_KNOWN);
            tvResult.setText(String.format(getString(R.string.result_message), knownWords, totalWords));
        }
        btnReturnToMenu.setOnClickListener(v -> {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frame_layout, new MainFragment())
                        .commit();
        });



    }
}
