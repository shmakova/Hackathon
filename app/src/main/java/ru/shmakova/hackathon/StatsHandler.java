package ru.shmakova.hackathon;


import java.util.ArrayList;
import java.util.List;

import ru.shmakova.hackathon.model.CardChoice;

public class StatsHandler {
    private List<CardChoice> choices = new ArrayList<>();
    public void add(CardChoice cardChoice){
        choices.add(cardChoice);
    }

    public List<CardChoice> getChoices(){
        return choices;
    }
}
