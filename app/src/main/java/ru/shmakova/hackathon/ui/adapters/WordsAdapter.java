package ru.shmakova.hackathon.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.shmakova.hackathon.R;
import ru.shmakova.hackathon.managers.VocalizerManager;
import timber.log.Timber;

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

        if (v == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_word, parent, false);
        }

        WordsViewHolder viewHolder = new WordsViewHolder(v);
        viewHolder.txtItem.setText(words.get(position));

        v.setOnClickListener(v1 -> {
            String item = (String) getItem(position);
            Timber.d(item);
            VocalizerManager.getInstance().vocalize(item);
        });

        return v;
    }

    static class WordsViewHolder {
        @BindView(R.id.word)
        TextView txtItem;

        WordsViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}