package ru.shmakova.hackathon.ui.touch;


import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.model.CardChoice;
import ru.shmakova.hackathon.network.DictionaryService;
import ru.shmakova.hackathon.network.ServiceGenerator;
import ru.shmakova.hackathon.network.models.Lookup;
import ru.shmakova.hackathon.utils.AppConfig;

public class AnimationTouchListener implements View.OnTouchListener {

    private static String LOG_TAG = AnimationTouchListener.class.getName();
    private static float MAX_ANGLE = 25;
    private static int ROLL_BACK_DURATION = 200;
    private final ChoiceCallback callback;
    private TextView tvWord;

    private float actionTrashHold;

    private float toDragViewStartX;
    private float screenCenterX;

    private float x0;
    private boolean isAnimationLocked;

    private View btnCancel;
    private View btnOk;
    private View rootView;
    private String word;

    public AnimationTouchListener(WindowManager manager, View rootView, @NonNull ChoiceCallback callback) {
        Display display = manager.getDefaultDisplay();
        this.rootView = rootView;
        this.btnCancel = rootView.findViewById(R.id.btnCancel);
        this.btnOk = rootView.findViewById(R.id.btnOk);
        btnCancel.setAlpha(0);
        btnOk.setAlpha(0);

        tvWord = (TextView) rootView.findViewById(R.id.tvCardWord);



        Point size = new Point();
        display.getSize(size);
        this.callback = callback;
        this.screenCenterX = size.x / 2;
        this.actionTrashHold = screenCenterX / 2 + screenCenterX / 4;
        this.isAnimationLocked = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        word = (String) tvWord.getText();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (toDragViewStartX == 0) {
                    toDragViewStartX = v.getX();
                }
                x0 = event.getRawX();
                break;

            case MotionEvent.ACTION_MOVE:
                if (isAnimationLocked) {
                    return true;
                }
                float viewPivot = v.getX() + v.getPivotX();
                float dX = x0 - event.getRawX();

                if (viewPivot > screenCenterX + actionTrashHold) {
                    successCallback();
                } else if (viewPivot < screenCenterX - actionTrashHold) {
                    cancelCallback();
                } else {
                    v.animate()
                            .x(toDragViewStartX - dX)
                            .rotation(maptoRotation(viewPivot))
                            .setDuration(0)
                            .start();
                    btnOk.animate()
                            .alpha(maptoAlfaOk(viewPivot))
                            .setDuration(0)
                            .start();
                    btnCancel.animate()
                            .alpha(maptoAlfaCancel(viewPivot))
                            .setDuration(0)
                            .start();
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                rollback(v);
                break;
            default:
                return false;
        }
        return true;
    }

    private void successCallback() {
        isAnimationLocked = true;
        callback.result(new CardChoice(word, true));
    }

    private void cancelCallback() {
        isAnimationLocked = true;
        callback.result(new CardChoice(word, false));

    }

    private float maptoAlfaOk(float viewPivot) {
        if (viewPivot < screenCenterX) {
            return 0;
        } else {
            return maptoAlfa(viewPivot);
        }
    }

    private float maptoAlfaCancel(float viewPivot) {
        if (viewPivot > screenCenterX) {
            return 0;
        } else {
            return maptoAlfa(viewPivot);
        }
    }

    private void rollback(View v) {
        isAnimationLocked = false;
        v.animate()
                .x(toDragViewStartX)
                .rotation(0)
                .alpha(1)
                .setDuration(ROLL_BACK_DURATION)
                .start();
        btnOk.animate()
                .alpha(0)
                .setDuration(ROLL_BACK_DURATION)
                .start();
        btnCancel.animate()
                .alpha(0)
                .setDuration(ROLL_BACK_DURATION)
                .start();
    }

    private float maptoRotation(float pivotFromCenter) {
        return -(((screenCenterX - pivotFromCenter) / screenCenterX) * MAX_ANGLE);
    }

    private float maptoAlfa(float pivotFromCenter) {
        return Math.abs((screenCenterX - pivotFromCenter) / screenCenterX);
    }

}
