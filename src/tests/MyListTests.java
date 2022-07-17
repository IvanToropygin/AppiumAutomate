package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();

        String articleTitle = articlePageObject.getArticleTitle();
        String nameOfList = "Learning Java";

        articlePageObject.addArticleToNewList(nameOfList);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openListByName(nameOfList);
        myListsPageObject.swipeLeftArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticlesToMyList(){
        String searchLine = "Java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();

        String firstArticleTitle = articlePageObject.getArticleTitle();
        String nameOfList = "HomeWork";

        articlePageObject.addArticleToNewList(nameOfList);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring("High-level");
        articlePageObject.waitForTitleElement();
        String secondArticleTitle = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToExistingList(nameOfList);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openListByName(nameOfList);
        myListsPageObject.swipeLeftArticleToDelete(firstArticleTitle);

        myListsPageObject.waitForArticleToDisappearByTitle(firstArticleTitle);
        myListsPageObject.waitForArticleToAppearByTitle(secondArticleTitle);
    }
}
