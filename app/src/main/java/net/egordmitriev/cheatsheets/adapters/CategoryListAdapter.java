package net.egordmitriev.cheatsheets.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.listeners.ExpansionArrowListener;
import net.egordmitriev.cheatsheets.pojo.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    protected List<Category> mCategories;

    public CategoryListAdapter(List<Category> categories) {
        mCategories = categories;
    }

    @Override
    public CategoryListAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.CategoryViewHolder holder, int position) {
        holder.onBind(mCategories.get(position));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.expandable)
        ExpandableLayout mExpandableLayout;

        @BindView(R.id.header_title)
        TextView mTitle;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mExpandableLayout.setOnExpansionUpdateListener(new ExpansionArrowListener(ButterKnife.findById(itemView, R.id.expandable_arrow)));
        }

        public void onBind(Category category) {
            mTitle.setText(Html.fromHtml(category.title));
        }

        @OnClick(R.id.header)
        public void onClickHeader(View view) {
            if(mExpandableLayout.isExpanded()) {
                view.setSelected(false);
                mExpandableLayout.collapse();
            }else {
                view.setSelected(true);
                mExpandableLayout.expand();
            }
        }
    }
}
