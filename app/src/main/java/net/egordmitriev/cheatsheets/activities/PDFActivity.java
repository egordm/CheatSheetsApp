package net.egordmitriev.cheatsheets.activities;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artifex.mupdf.fitz.Document;

import net.egordmitriev.cheatsheets.CheatSheetsApp;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
import net.egordmitriev.cheatsheets.utils.DataCallback;
import net.egordmitriev.cheatsheets.widgets.CustomLoaderView;
import net.egordmitriev.libmupdf.PDFView;
import net.egordmitriev.loaderview.LoaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static net.egordmitriev.cheatsheets.activities.DetailActivity.CHEATSHEET_KEY;

/**
 * Created by egordm on 7-8-2017.
 */

public class PDFActivity extends SearchBarActivity {
	
	@BindView(R.id.loaderview)
	CustomLoaderView mLoaderView;
	
	@BindView(R.id.doc_wrapper)
	protected RelativeLayout mDocWrapper;
	
	protected CheatSheet mCheatSheet;
	protected PDFView mDocView;
	
	private ImageButton mNextButton;
	private ImageButton mPreviousButton;
	//private Registry mRegistry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoaderView.setState(LoaderView.STATE_LOADING);
		int id = -1;
		if(getIntent() != null) {
			mCheatSheet = getIntent().getParcelableExtra(CHEATSHEET_KEY);
		}
		if (mCheatSheet == null) {
			finish();
			return;
		}
		CheatSheetsApp.getRegistry().updateCheatSheetsUsed(mCheatSheet.id);
		
		mSearchView.setQueryHint("Search in " + mCheatSheet.title);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		initSearchView();
		
		API.requestPDF(getCallback(), mCheatSheet.id);
	}
	
	private void initSearchView() {
		LinearLayout.LayoutParams searchButtonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
		
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, typedValue, true);
		int padding_side = (int) getResources().getDimension(R.dimen.spacing_quarter);
		
		mPreviousButton = new ImageButton(this);
		mNextButton = new ImageButton(this);
		mPreviousButton.setBackgroundResource(typedValue.resourceId);
		mNextButton.setBackgroundResource(typedValue.resourceId);
		mPreviousButton.setImageResource(R.drawable.ic_previous);
		mNextButton.setImageResource(R.drawable.ic_next);
		mPreviousButton.setPadding(padding_side, 0, padding_side, 0);
		mNextButton.setPadding(padding_side, 0, padding_side, 0);
		
		((LinearLayout) mSearchView.findViewById(R.id.search_plate)).addView(mPreviousButton, searchButtonParams);
		((LinearLayout) mSearchView.findViewById(R.id.search_plate)).addView(mNextButton, searchButtonParams);
		
		mPreviousButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDocView.onSearchPrevious(mSearchView.getQuery().toString());
			}
		});
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDocView.onSearchNext(mSearchView.getQuery().toString());
			}
		});
		
		mPreviousButton.setVisibility(View.GONE);
		mNextButton.setVisibility(View.GONE);
	}
	
	private void initPdfView(Uri data) {
		mDocView = new PDFView(this);
		mDocView.setupHandles(mDocWrapper);
		mDocView.setSearchScrollPos(0.35f);
		
		Document doc = Document.openDocument(Uri.decode(data.getEncodedPath()));
		mDocView.setDocument(doc);
		mDocWrapper.removeAllViews();
		mDocWrapper.addView(mDocView);
	}
	
	@Override
	public boolean onQueryTextChange(String newText) {
		int visibility = TextUtils.isEmpty(newText) ? View.GONE : View.VISIBLE;
		mPreviousButton.setVisibility(visibility);
		mNextButton.setVisibility(visibility);
		return super.onQueryTextChange(newText);
	}
	
	@Override
	public boolean onQueryTextSubmit(String query) {
		if(mDocView == null) return false;
		mDocView.onSearchNext(query);
		return true;
	}
	
	@Override
	protected int getLayout() {
		return R.layout.activity_pdf;
	}
	
	@Override
	public void finish() {
		if(mDocView != null)
			mDocView.finish();
		super.finish();
	}
	
	private DataCallback<Uri> getCallback() {
		return new DataCallback<Uri>() {
			@Override
			public void init() {
				super.init();
				Button retryButton = ButterKnife.findById(mLoaderView, R.id.loaderview_retry);
				retryButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (retry()) mLoaderView.setState(LoaderView.STATE_LOADING);
					}
				});
			}
			
			@Override
			public void onData(Uri data) {
				initPdfView(data);
				mLoaderView.setState(LoaderView.STATE_IDLE);
			}
			
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				TextView errorText = ButterKnife.findById(mLoaderView, R.id.loaderview_errormsg);
				errorText.setText(t.getMessage());
				mLoaderView.setState(LoaderView.STATE_ERROR);
				
			}
		};
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	
}
