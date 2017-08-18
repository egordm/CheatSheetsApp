package net.egordmitriev.cheatsheets.widgets;

import android.app.Activity;
import android.text.Html;
import android.view.View;

import net.egordmitriev.cheatsheets.widgets.presenters.CategoryGroupPresenter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public class RecentsGroupHolder extends CategoryGroupHolder {
	
	public RecentsGroupHolder(Activity activity, View view) {
		super(activity, view);
	}
	
	@Override
	public void onBind(CategoryGroupPresenter data) {
		mExpandableLayout.setExpansion(1);
		mTitle.setText(Html.fromHtml(data.title));
		mAdapter.setAll(new ArrayList<>(Arrays.asList(data.children)));
	}
}
