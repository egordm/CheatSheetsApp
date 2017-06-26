package net.egordmitriev.cheatsheets.adapters;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import net.egordmitriev.cheatsheets.pojo.MatchableModel;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by EgorDm on 15-Jun-2017.
 */

public abstract class AdvancedRecyclerAdapter<TD extends MatchableModel, TVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<TVH> {

    protected SortedList<TD> mData = new SortedList<TD>(getClassOfB(), new SortedList.Callback<TD>() {
        @Override
        public int compare(TD o1, TD o2) {
            return 0;
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(TD oldItem, TD newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(TD item1, TD item2) {
            return item1 == item2;
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }
    });

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void replaceAll(List<TD> models) {
        for (int i = mData.size() - 1; i >= 0; i--) {
            final TD model = mData.get(i);
            if (!models.contains(model)) {
                mData.remove(model);
            } else {
                models.remove(model);
            }
        }
        if(mData.size() == models.size()) return; //Arrays are identical. Nothing to add
        mData.addAll(models);
    }

    public void add(TD model) {
        mData.add(model);
    }

    public void remove(TD model) {
        mData.remove(model);
    }

    public void add(List<TD> models) {
        mData.addAll(models);
    }

    public void remove(List<TD> models) {
        mData.beginBatchedUpdates();
        for (TD model : models) {
            mData.remove(model);
        }
        mData.endBatchedUpdates();
    }

    public Class<TD> getClassOfB() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();

        return (Class<TD>) superclass.getActualTypeArguments()[0];
    }
}
