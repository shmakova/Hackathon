package ru.shmakova.hackathon.ui.touch;


import java.io.Serializable;

import ru.shmakova.hackathon.model.CardChoice;

public interface ChoiceCallback extends Serializable{
    void result(CardChoice choice);
}
