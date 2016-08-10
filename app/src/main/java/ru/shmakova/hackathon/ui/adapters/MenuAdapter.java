package ru.shmakova.hackathon.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.shmakova.hackathon.R;

/**
 * Created by shmakova on 23.07.16.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private List<String> menuItems;
    private MenuViewHolder.OnItemCLickListener onItemClickListener;

    public MenuAdapter(Context context, MenuViewHolder.OnItemCLickListener onItemClickListener) {
        this.menuItems = Arrays.asList(context.getApplicationContext().getResources()
                .getStringArray(R.array.menu_items));
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(convertView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        String menuItem = menuItems.get(position);
        holder.menuItemText.setText(menuItem);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.menu_item_text)
        TextView menuItemText;

        @NonNull
        private OnItemCLickListener listener;

        public interface OnItemCLickListener {
            void onItemClick(String text);
        }

        MenuViewHolder(View itemView, @NonNull OnItemCLickListener OnItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = OnItemClickListener;
        }

        @OnClick(R.id.menu_item)
        void onMenuItemClick(View view) {
            TextView textView = (TextView) view.findViewById(R.id.menu_item_text);
            listener.onItemClick(textView.getText().toString());
        }
    }
}