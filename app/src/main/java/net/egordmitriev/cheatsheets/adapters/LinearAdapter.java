package net.egordmitriev.cheatsheets.adapters;

import android.database.DataSetObserver;
import android.view.ViewGroup;

import net.egordmitriev.cheatsheets.widgets.ViewHolder;

/**
 * Created by egordm on 17-8-2017.
 */

public abstract class LinearAdapter<T, H extends ViewHolder<T>> {
	public void registerDataSetObserver(DataSetObserver observer) {
		
	}
	
	public void unregisterDataSetObserver(DataSetObserver observer) {
		
	}
	
	public abstract int getCount();
	
	public abstract H onCreateViewHolder(ViewGroup parent);
	
	public abstract void onBindViewHolder(H holder, int position);
	
	
}
