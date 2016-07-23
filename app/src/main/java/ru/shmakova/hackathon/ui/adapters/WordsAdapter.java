package ru.shmakova.hackathon.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.shmakova.hackathon.R;

/**
 * Created by shmakova on 23.07.16.
 */

public class WordsAdapter extends BaseAdapter {
    private List<String> words;
    private Context context;

    public WordsAdapter(List<String> words, Context context) {
        this.words = words;
        this.context = context;
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Object getItem(int position) {
        return words.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
            v = inflater.inflate(R.layout.item_word, parent, false);
        }

        ((TextView) v.findViewById(R.id.word)).setText(words.get(position));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = (String) getItem(position);
                Log.i("MainActivity", item);
            }
        });

        return v;
    }
}