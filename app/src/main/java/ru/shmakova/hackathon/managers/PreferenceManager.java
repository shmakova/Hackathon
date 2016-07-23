package ru.shmakova.hackathon.managers;

import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.shmakova.hackathon.App;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.model.WordsContainer;

/**
 * Created by shmakova on 23.07.16.
 */

public class PreferenceManager {
    private static final String WORDS_KEY = "300";
    private SharedPreferences sharedPreferences;


    public PreferenceManager() {
        this.sharedPreferences = App.getSharedPreferences();
    }

    public void saveWordsFromFile() {
        if (sharedPreferences.getStringSet(WORDS_KEY, null) == null) {
            ObjectMapper mapper = new ObjectMapper();
            WordsContainer wordsContainer = null;

            try {
                wordsContainer = mapper.readValue(App.getContext().getResources().openRawResource(R.raw.words), WordsContainer.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (wordsContainer != null) {
                Set<String> wordsSet = new HashSet<String>(wordsContainer.getEn());
                editor.putStringSet(WORDS_KEY, wordsSet);
            }

            editor.apply();
        }
    }


    /**
     * Gets all words
     * @return
     */
    public List<String> getWords() {
        Set<String> wordsSet = sharedPreferences.getStringSet(WORDS_KEY, null);

        if (wordsSet == null) {
            saveWordsFromFile();
            wordsSet = sharedPreferences.getStringSet(WORDS_KEY, null);
        }

        List<String> wordList = new ArrayList<>();
        wordList.addAll(wordsSet);
        return wordList;
    }

    /**
     * Gets random words
     * @param count
     * @return
     */
    public List<String> getRandomWords(int count) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            addWord(getWords(), result);
        }
        return result;
    }

    /**
     * Adds word
     * @param all
     * @param result
     */
    private void addWord(List<String> all, List<String> result) {
        if (all.size() == result.size()) {
            return;
        }

        int random = (int) (Math.random() * all.size());
        String pickedWord = all.get(random);

        if (!result.contains(pickedWord)) {
            result.add(pickedWord);
        } else {
            addWord(all, result);
        }
    }
}