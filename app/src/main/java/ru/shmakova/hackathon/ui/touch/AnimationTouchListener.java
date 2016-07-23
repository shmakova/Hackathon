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
import ru.shmakova.hackathon.network.DictionaryService;
import ru.shmakova.hackathon.network.ServiceGenerator;
import ru.shmakova.hackathon.network.models.Lookup;
import ru.shmakova.hackathon.utils.AppConfig;

public class AnimationTouchListener implements View.OnTouchListener {

    private static String LOG_TAG = AnimationTouchListener.class.getName();
    private static float MAX_ANGLE = 25;
    private static int ROLL_BACK_DURATION = 200;
    private final ChoiceCallback callback;

    private float actionTrashHold;

    private float toDragViewStartX;
    private float screenCenterX;

    private float x0;
    private boolean isAnimationLocked;

    private View btnCancel;
    private View btnOk;
    private String word;

    public AnimationTouchListener(WindowManager manager, View rootView, @NonNull ChoiceCallback callback) {
        Display display = manager.getDefaultDisplay();
        btnCancel = rootView.findViewById(R.id.btnCancel);
        btnOk = rootView.findViewById(R.id.btnOk);
        btnCancel.setAlpha(0);
        btnOk.setAlpha(0);

        TextView tvWord =(TextView)rootView.findViewById(R.id.tvCardWord);
        word = (String) (tvWord.getText());
        tvWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DictionaryService service = ServiceGenerator.createService(DictionaryService.class);
                Map<String, String> map = new HashMap<>();
                map.put("key", AppConfig.DICTIONARY_API_KEY);
                map.put("lang","en-ru");
                map.put("text",word);
                Call<Lookup> call = service.lookup(map);
                call.enqueue(new Callback<Lookup>() {
                    @Override
                    public void onResponse(Call<Lookup> call, Response<Lookup> response) {
                        Log.d(LOG_TAG, "Response : " + response.body());

                        ((TextView)rootView.findViewById(R.id.tvCardWordTranslate)).setText(response.body().def.get(0).tr.get(0).text);

                    }

                    @Override
                    public void onFailure(Call<Lookup> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });

        Point size = new Point();
        display.getSize(size);
        this.callback = callback;
        this.screenCenterX = size.x / 2;
        this.actionTrashHold = screenCenterX/2 + screenCenterX/4;
        this.isAnimationLocked = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (toDragViewStartX == 0) {
                    toDragViewStartX = v.getX();
                }
                x0 = event.getRawX();
                break;

            case MotionEvent.ACTION_MOVE:
                if(isAnimationLocked){
                    return true;
                }
                float viewPivot = v.getX() + v.getPivotX();
                float dX = x0 - event.getRawX();

                if(viewPivot > screenCenterX + actionTrashHold){
                    successCallback();
                }else if(viewPivot < screenCenterX - actionTrashHold){
                    cancelCallback();
                }
                else{
                    v.animate()
//                        .alpha(1-maptoAlfa(viewPivot))
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
        callback.success(word);
    }

    private void cancelCallback() {
        isAnimationLocked = true;
        callback.cancel(word);
    }

    private float maptoAlfaOk(float viewPivot) {
        if(viewPivot < screenCenterX){
            return 0;
        }else{
            return maptoAlfa(viewPivot);
        }
    }
    private float maptoAlfaCancel(float viewPivot) {
        if(viewPivot > screenCenterX){
            return 0;
        }else{
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
