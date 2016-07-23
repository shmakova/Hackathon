package ru.shmakova.hackathon.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ru.shmakova.hackathon.App;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.ui.fragments.ContentFragment;

public class MainActivity extends BaseActivity {



    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(getLayoutInflater().inflate(R.layout.activity_main, null));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ContentFragment())
                    .commit();
        }
    }
}
