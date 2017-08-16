package net.egordmitriev.cheatsheets.activities;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.artifex.mupdf.fitz.Document;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.libmupdf.PDFView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by egordm on 7-8-2017.
 */

public class PDFActivity extends SearchBarActivity {
	
	private PDFView mDocView;
	private Document mDoc;
	private ImageButton mNextButton;
	private ImageButton mPreviousButton;
	//private Registry mRegistry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initSearchView();
		
		Uri pdf = openPDF();
		final String path = Uri.decode(pdf.getEncodedPath());
		
		mDocView = (PDFView) findViewById(R.id.doc_view_inner);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.doc_wrapper);
		mDocView.setupHandles(layout);
		
		mDoc = Document.openDocument(path);
		mDocView.setDocument(mDoc);
		
		mDocView.setSearchScrollPos(0.35f);
		
/*		mRegistry = new Registry(this);
		
		API.requestCategories(new DataCallback<ArrayList<Category>>() {
			@Override
			public void onData(ArrayList<Category> data) {
				mRegistry.tryPutCategories(data);
				Logger.d("Saved categories");
				List<Category> cats = mRegistry.getCategories();
				Logger.d(API.sGson.toJson(cats));
			}
			
			@Override
			public void onError(Throwable t) {
				Logger.e(String.valueOf(t));
			}
		});*/
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
				mDocView.onSearchNext(mSearchView.getQuery().toString());
			}
		});
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDocView.onSearchPrevious(mSearchView.getQuery().toString());
			}
		});
		
		mPreviousButton.setVisibility(View.GONE);
		mNextButton.setVisibility(View.GONE);
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
		mDocView.onSearchNext(query);
		return true;
	}
	
	@Override
	protected int getLayout() {
		return R.layout.activity_pdf;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main_directional, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}
	
	public Uri openPDF() {
		AssetManager assetManager = getAssets();
		InputStream in = null;
		OutputStream out = null;
		
		File file = new File(getFilesDir(), "git-cs.pdf");
		try {
			in = assetManager.open("git-cs.pdf");
			out = openFileOutput(file.getName(), Context.MODE_PRIVATE);
			
			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}
		return Uri.parse("file://" + getFilesDir() + "/git-cs.pdf");
	}
	
	@Override
	public void finish()
	{
		//  stop the view
		mDocView.finish();
		super.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
