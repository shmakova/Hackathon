package ru.shmakova.hackathon.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import ru.shmakova.hackathon.App;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.ui.fragments.CardFragment;
import ru.shmakova.hackathon.ui.fragments.MainFragment;
import ru.shmakova.hackathon.ui.fragments.PronunciationFragment;

public class MainActivity extends BaseActivity implements MainFragment.OnMenuItemClickListener {
    private FragmentManager supportFragmentManager;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);
        setContentView(R.layout.activity_main);
        supportFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new MainFragment())
                    .commit();
        }
    }

    /**
     * Opens fragment
     *
     * @param item text of menu item
     */
    @Override
    public void onMenuItemClick(String item) {
        if (item.equals(getString(R.string.cards))) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new CardFragment())
                    .addToBackStack(null)
                    .commit();
            return;
        }
        if (item.equals(getString(R.string.words_pronunciation))) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new PronunciationFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
