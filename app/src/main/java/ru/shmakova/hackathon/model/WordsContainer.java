package ru.shmakova.hackathon.model;

import java.util.List;

public class WordsContainer {
    private List<String> ru;
    private List<String> en;

    public List<String> getRu() {
        return ru;
    }

    public void setRu(List<String> ru) {
        this.ru = ru;
    }

    public List<String> getEn() {
        return en;
    }

    public void setEn(List<String> en) {
        this.en = en;
    }
}
