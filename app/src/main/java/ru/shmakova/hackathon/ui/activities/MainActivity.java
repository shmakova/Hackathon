package ru.shmakova.hackathon.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ru.shmakova.hackathon.App;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.ui.fragments.CardFragment;
import ru.shmakova.hackathon.ui.fragments.MainFragment;
import ru.shmakova.hackathon.ui.fragments.PronunciationFragment;

public class MainActivity extends BaseActivity  {

    private static final String LOG_TAG = MainActivity.class.getName();

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        // setContentView(R.layout.activity_main)?
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
        // В таком случае и переопределять не надо.
        super.onResume();
    }

    /**
     * Opens fragment
     * @param position
     */
    public void onMenuItemClick(int position) {
        // Тут лучше наверно было бы оперировать некими ID.
        // Что если у вас кнопки поменяются местами, или список станет гридом?
        switch (position) {
            case 0:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame_layout, new CardFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame_layout, new PronunciationFragment())
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }
}
