package ru.shmakova.hackathon.ui.touch;


import java.io.Serializable;

public interface ChoiceCallback extends Serializable{
    void success(String word);
    void cancel(String word);

}
