package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {
    @Test
    public void testChangeScreenOrientationOnSearchResult(){
        String searchLine = "Java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring("Island");
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String titleBeforeRotation = articlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String titleAfterRotation = articlePageObject.getArticleTitle();
        assertEquals("Title not compare after rotation", titleBeforeRotation, titleAfterRotation);
        this.rotateScreenPortrait();
        String titleAfterSecondRotation = articlePageObject.getArticleTitle();
        assertEquals("Title not compare after rotation", titleBeforeRotation, titleAfterSecondRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground(){
        String searchLine = "Java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForSearchResult("Island");
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult("Island");
    }
}
