package itg8.com.labtestingapp.splash;

import java.util.List;

import itg8.com.labtestingapp.db.tables.MainCategory;
import itg8.com.labtestingapp.db.tables.SubCategory;
import itg8.com.labtestingapp.db.tables.Test;

public class CategoryModel {
    List<MainCategory> mainCategories;
    List<SubCategory> subCategories;
    List<Test> tests;

    public List<MainCategory> getMainCategories() {
        return mainCategories;
    }

    public void setMainCategories(List<MainCategory> mainCategories) {
        this.mainCategories = mainCategories;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }


}
