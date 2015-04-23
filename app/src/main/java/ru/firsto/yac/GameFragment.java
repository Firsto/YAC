package ru.firsto.yac;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.firsto.yac.Util.Notation;

/**
 * Created by razor on 16.04.15.
 * Main Game Fragment
 */
public class GameFragment extends ListFragment {
    public static final String TAG = "GameFragment";

    private ItemPool mItemPool = ItemPool.get();
    private Game mGame = Game.get();
    private ListView itemListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

//        setListAdapter(new ItemAdapter(mItemPool.allItems()));
        setListAdapter(new ItemAdapter(mItemPool.availableItems()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

//        Item selectedItem = (Item) mItemPool.item(position+1);
        Item selectedItem = getModel(position);

        if (mItemPool.upItemLevel(position + 1, mGame)) {
            mItemPool.availableItems();
            updateList();

        } else {
            Toast.makeText(getActivity(), "Недостаточно ресурсов для повышения уровня " + selectedItem.getName() + ". Осталось "
                            + Notation.get(selectedItem.getPrice() - mGame.getResources()) + " и " + selectedItem.getTime(mGame.getResources(), mGame.getIncome()),
                    Toast.LENGTH_SHORT).show();
        }

    }

    protected void updateList() {
        ((ItemAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private Item getModel(int position) {
        return (((ItemAdapter) getListAdapter()).getItem(position));
    }

    class ItemAdapter extends ArrayAdapter<Item> {

        private LayoutInflater mInflater;

        ItemAdapter(ArrayList<Item> list) {
            super(getActivity(), R.layout.item_layout, list);
            mInflater = LayoutInflater.from(getActivity());
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder;
            View row = convertView;
            if (row == null) {
                row = mInflater.inflate(R.layout.item_layout, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) row.findViewById(R.id.item_image);
                holder.nameView = (TextView) row.findViewById(R.id.item_name);
                holder.levelView = (TextView) row.findViewById(R.id.item_level);
                holder.bonusView = (TextView) row.findViewById(R.id.item_bonus);
                holder.costView = (TextView) row.findViewById(R.id.item_price);
                holder.remainingView = (TextView) row.findViewById(R.id.item_remaining);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            Item item = getModel(position);

            holder.imageView.setImageResource(android.R.drawable.ic_media_play);
            holder.nameView.setText(item.getName());
            holder.levelView.setText("Level: " + String.valueOf(item.getLevel()));
            holder.bonusView.setText("Bonus: " + Notation.get(item.getBonus()));
            holder.costView.setText("Cost: " + Notation.get(Math.round(item.getPrice())));
            holder.remainingView.setText(item.getTime(mGame.getResources(), mGame.getIncome()));

            return row;
        }

        class ViewHolder {
            public ImageView imageView;
            public TextView nameView, levelView, costView, bonusView, remainingView;
        }
    }
}
