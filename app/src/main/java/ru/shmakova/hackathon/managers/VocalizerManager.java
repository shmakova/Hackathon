package ru.shmakova.hackathon.managers;

import ru.shmakova.hackathon.utils.AppConfig;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;
import timber.log.Timber;

import static ru.shmakova.hackathon.App.getContext;

/**
 * Created by shmakova on 23.07.16.
 */

public class VocalizerManager implements VocalizerListener {
    private static VocalizerManager INSTANCE = null;
    private Vocalizer vocalizer;


    public VocalizerManager() {
        SpeechKit.getInstance().configure(getContext(), AppConfig.YANDEX_SPEECH_KIT_API_KEY);
    }

    public void vocalize(String text) {
        Timber.d("Click!");
        resetVocalizer();
        vocalizer = Vocalizer.createVocalizer(Vocalizer.Language.ENGLISH, text, true, Vocalizer.Voice.ERMIL);
        vocalizer.setListener(VocalizerManager.this);
        vocalizer.start();
    }

    public static VocalizerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VocalizerManager();
        }

        return INSTANCE;
    }


    private void resetVocalizer() {
        if (vocalizer != null) {
            vocalizer.cancel();
            vocalizer = null;
        }
    }

    @Override
    public void onSynthesisBegin(Vocalizer vocalizer) {
        Timber.d("Synthesis begin");
    }

    @Override
    public void onSynthesisDone(Vocalizer vocalizer, Synthesis synthesis) {
        Timber.d("Synthesis done");
    }

    @Override
    public void onPlayingBegin(Vocalizer vocalizer) {
        Timber.d("Playing begin");
    }

    @Override
    public void onPlayingDone(Vocalizer vocalizer) {
        Timber.d("Playing done");
    }

    @Override
    public void onVocalizerError(Vocalizer vocalizer, ru.yandex.speechkit.Error error) {
        Timber.d("Error occurred " + error.getString());
        resetVocalizer();
    }

}
