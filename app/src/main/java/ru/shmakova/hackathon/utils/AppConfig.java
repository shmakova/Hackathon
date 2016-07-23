package ru.shmakova.hackathon.utils;

/**
 * Created by shmakova on 22.07.16.
 */

public interface AppConfig {
    String YANDEX_SPEECH_KIT_API_KEY = "0ac196b0-a88f-4d37-8a09-99baa60e2429";
    String DICTIONARY_BASE_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/";
    String DICTIONARY_API_KEY = "dict.1.1.20160722T193411Z.ac2f42a366da860f.a097013d680347239089e5819b7b4d23d1da7e64";

    String[] menuItems = {
            "Карточки",
            "Проговаривание слов",
            "Слово на слух",
            "Поиск пары",
            "Составление слова",
            "Верно – Неверно",
            "Выбор перевода",
            "Перевод по картинке"
    };
}
