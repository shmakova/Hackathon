package ru.shmakova.hackathon.managers;

/**
 * Created by shmakova on 22.07.16.
 */

public class DataManager {
    private static DataManager INSTANCE = null;
    private PreferenceManager preferenceManager;

    public DataManager() {
        this.preferenceManager = new PreferenceManager();
        preferenceManager.saveWordsFromFile();
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }

        return INSTANCE;
    }

    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

}
