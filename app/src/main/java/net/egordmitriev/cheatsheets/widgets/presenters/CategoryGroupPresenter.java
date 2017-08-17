package net.egordmitriev.cheatsheets.widgets.presenters;

import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import java.util.List;

/**
 * Created by egordm on 17-8-2017.
 */

public class CategoryGroupPresenter extends Presenter {
	public String title;
	public CheatSheet[] children;
	
	public CategoryGroupPresenter(Category category) {
		this(category, category.cheat_sheets);
	}
	
	public CategoryGroupPresenter(Category category, CheatSheet[] cheat_sheets) {
		id = category.id;
		title = category.title;
		children = cheat_sheets;
	}
	
	public static CategoryGroupPresenter[] create(Category[] categories) {
		CategoryGroupPresenter[] ret = new CategoryGroupPresenter[categories.length];
		for(int i = 0; i < categories.length; i++) {
			ret[i] = new CategoryGroupPresenter(categories[i], categories[i].cheat_sheets);
		}
		return ret;
	}
	
	public static CategoryGroupPresenter[] create(List<Category> categories) {
		CategoryGroupPresenter[] ret = new CategoryGroupPresenter[categories.size()];
		for(int i = 0; i < categories.size(); i++) {
			ret[i] = new CategoryGroupPresenter(categories.get(i), categories.get(i).cheat_sheets);
		}
		return ret;
	}
}
