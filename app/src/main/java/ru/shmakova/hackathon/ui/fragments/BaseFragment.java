package ru.shmakova.hackathon.ui.fragments;

/**
 * Created by shmakova on 23.07.16.
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.shmakova.hackathon.App;


@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class BaseFragment extends Fragment {

    private Handler mainThreadHandler;
    private Unbinder viewBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainThreadHandler = App.get(context).applicationComponent().mainThreadHandler();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinder = ButterKnife.bind(this, view);
    }


    private boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

    @Override
    public void onDestroyView() {
        if (viewBinder != null) {
            viewBinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
