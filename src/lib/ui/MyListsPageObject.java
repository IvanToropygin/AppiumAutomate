package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject{
    public static final String
            ARTICLE_BY_TITLE_TPL = "//*[@text = '{ARTICLE_TITLE}']",
            LIST_NAME_TPL = "//*[@text = '{LIST_NAME}']";
    private static String getListXPathByName(String listName){
        return LIST_NAME_TPL.replace("{LIST_NAME}", listName);
    }

    private static String getTitleByArticle(String articleTitle){
        return ARTICLE_BY_TITLE_TPL.replace("{ARTICLE_TITLE}", articleTitle);
    }

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openListByName(String listName){
        this.waitForElementAndClick(
                By.xpath(getListXPathByName(listName)),
                "Cannot find '" + listName + "' list",
                15);
    }

    public void swipeLeftArticleToDelete(String articleTitle){
        this.waitForArticleToAppearByTitle(articleTitle);
        this.swipeElementToLeft(
                By.xpath(getTitleByArticle(articleTitle)),
                "Cannot left swipe article");
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle){
        this.waitForElementNotPresent(By.xpath(getTitleByArticle(articleTitle)),
                "Article present: " + articleTitle, 15);
    }

    public void waitForArticleToAppearByTitle(String articleTitle){
        this.waitForElementPresent(By.xpath(getTitleByArticle(articleTitle)),
                "Article not displayed: " + articleTitle, 15);
    }
}
