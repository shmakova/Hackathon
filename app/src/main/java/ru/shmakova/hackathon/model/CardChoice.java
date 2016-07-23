package ru.shmakova.hackathon.model;


import java.io.Serializable;

public class CardChoice  implements Serializable{
    private String word;
    private boolean isSuccess;


    public CardChoice(String word, boolean isSuccess) {
        this.word = word;
        this.isSuccess = isSuccess;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }


}
