package net.egordmitriev.cheatsheets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.listeners.ExpansionArrowListener;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.widgets.SheetItemHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

//https://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465
    //http://www.journaldev.com/9942/android-expandablelistview-example-tutorial

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    protected RecyclerView mRecyclerView;
    protected final List<Category> mAllCategories;
    protected List<Category> mCategories;
    protected Context mContext;

    public CategoryListAdapter(Context context, RecyclerView recyclerView , List<Category> categories) {
        mContext = context;
        mRecyclerView = recyclerView;
        mAllCategories = categories;
        mCategories = new ArrayList<Category>() {{addAll(mAllCategories);}};
    }

/*    public void onQuery(String query) {
        mCategories.clear();
        for(Category category : mAllCategories) {
            if(category.matchesString(query)) {
                mCategories.add(category);
            }
        }
        notifyDataSetChanged();
    }*/

    @Override
    public CategoryListAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.CategoryViewHolder holder, int position) {
        holder.onBind(position, mAllCategories.get(position));
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

        @BindView(R.id.expandable_contents)
        LinearLayout mSheetsList;

        List<SheetItemHolder> mViewHolders = new ArrayList<>();

        private int mPosition;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mExpandableLayout.setOnExpansionUpdateListener(new ExpansionListener(ButterKnife.findById(itemView, R.id.expandable_arrow)));
        }

        public void onBind(int position, Category category) {
            mPosition = position;
            mTitle.setText(Html.fromHtml(category.title));
            boolean even = false;
            mSheetsList.removeAllViews();
           /* for(CheatSheet sheet : category.cheat_sheets) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.cheatsheet_item, mSheetsList, false);
                SheetItemHolder viewHolder = new SheetItemHolder(mAcview);
                viewHolder.onBind(sheet);
                view.setBackgroundResource(even ? R.color.tableEven : R.color.tableUneven);
                view.setTag(viewHolder);
                mSheetsList.addView(view);
                even = !even;
            }*/
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

        class ExpansionListener extends ExpansionArrowListener {

            public ExpansionListener(View arrow) {
                super(arrow);
            }

            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                if(state != mPreviousState) {
                    if(state == ExpandableLayout.State.EXPANDED) {
                        mRecyclerView.smoothScrollToPosition(mPosition);
                    }
                }
                super.onExpansionUpdate(expansionFraction, state);

            }
        }
    }
}
