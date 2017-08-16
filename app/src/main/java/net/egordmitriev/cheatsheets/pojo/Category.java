package net.egordmitriev.cheatsheets.pojo;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Category extends MatchableModel {
	public boolean temp;
	public int id;
	public String title;
	public String description;
	public List<CheatSheet> cheat_sheets;
	
	public Category() {
	}
	
	public Category(int id, String title, String description, List<CheatSheet> cheat_sheets) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.cheat_sheets = cheat_sheets;
	}
	
	public Category(String title, String description, List<CheatSheet> cheat_sheets) {
		this.title = title;
		this.description = description;
		this.cheat_sheets = cheat_sheets;
	}
	
	@Override
	protected List<String> getSearchableStrings(boolean recursive) {
		List<String> ret = new ArrayList<String>() {{
			add(title);
			add(description);
		}};
		if (recursive) {
			for (CheatSheet cheatSheet : cheat_sheets) {
				ret.addAll(cheatSheet.getSearchableStrings(false));
			}
		}
		return ret;
	}
	
	public CheatSheet getCheatSheet(int id) {
		for (CheatSheet cheatSheet : cheat_sheets) {
			if (cheatSheet.id == id) {
				return cheatSheet;
			}
		}
		return null;
	}
	
	public static CheatSheet getCheatSheet(int id, List<Category> categories) {
		for (Category category : categories) {
			CheatSheet temp = category.getCheatSheet(id);
			if (temp != null) return temp;
		}
		return null;
	}
	
	public Category cloneMe() {
		Category ret = new Category(id, title, description, (List<CheatSheet>) ((ArrayList<CheatSheet>)cheat_sheets).clone());
		ret.temp = temp;
		return ret;
	}
	
	public Category applyQuery(String query) {
		if(TextUtils.isEmpty(query) || matchesString(query, false)) {
			return this;
		}
		List<CheatSheet> results = new ArrayList<>();
		for (CheatSheet cheatSheet : cheat_sheets) {
			if(cheatSheet.applyQuery(query)) results.add(cheatSheet);
		}
		if(results.isEmpty()) return null;
		Category ret = cloneMe();
		ret.cheat_sheets = results;
		return ret;
	}
	
}
