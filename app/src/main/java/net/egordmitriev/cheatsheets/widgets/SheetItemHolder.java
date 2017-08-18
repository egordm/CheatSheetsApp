package net.egordmitriev.cheatsheets.widgets;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.CheatSheetsApp;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public class SheetItemHolder extends RecyclerViewHolder<CheatSheet> {
	
	@BindView(R.id.title)
	TextView mTitleView;
	
	@BindView(R.id.local_status)
	ImageView mLocalStatusView;
	
	@BindView(R.id.pdf)
	ImageView mPdf;
	
	public SheetItemHolder(Activity activity, View view) {
		super(activity, view);
	}
	
	@Override
	public void onBind(CheatSheet data) {
		mData = data;
		mTitleView.setText(data.title);
		mPdf.setVisibility(mData.type == 1 ? View.VISIBLE : View.GONE);
		mLocalStatusView.setImageResource(mData.isLocal() ? R.drawable.ic_local : R.drawable.ic_cloud);
		onUpdate();
	}
	
	@OnClick(R.id.wrapper)
	public void onClick() {
		CheatSheetsApp.openCheatSheet(mActivity, mData);
	}
	
	public static View inflate(LayoutInflater inflater, ViewGroup parent, boolean bind) {
		View view = inflater.inflate(R.layout.cheatsheet_item, parent, false);
		if (bind) parent.addView(view);
		return view;
	}
	
	
	public void onUpdate() {
		mLocalStatusView.setImageResource(mData.isLocal() ? R.drawable.ic_local : R.drawable.ic_cloud);
	}
}
