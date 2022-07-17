package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
    private static final String
    SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
    SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
    SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id = 'org.wikipedia:id/page_list_item_container']" +
            "//*[contains(@text, '{SUBSTRING}')]",
    SEARCH_CANCEL_BTN = "org.wikipedia:id/search_close_btn",
    EMPTY_RESULT_LABEL = "//*[@text = 'No results found']",
    SEARCH_RESULT_LOCATOR = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[@resource-id = 'org.wikipedia:id/page_list_item_container']";

    public  SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    /*<TEMPLATES METHODS*/
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /*<TEMPLATES METHODS*/

    public void initSearchInput(){
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find search init on Main-page",
                5);
        this.waitForElementPresent(By.xpath(SEARCH_INPUT), "Cannot find search input On Search-page");
    }

    public void typeSearchLine(String searchLine){
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), searchLine,
                "Cannot find  and type in searchInput", 5);
    }

    public void waitForSearchResult(String substring){
        this.waitForElementPresent(By.xpath(getResultSearchElement(substring)),
                "Cannot find search result with: " + substring);
    }

    public void clickByArticleWithSubstring(String substring){
        this.waitForElementAndClick(By.xpath(getResultSearchElement(substring)),
                "Cannot find and click search result with: " + substring, 10);
    }

    public void waitForCancelBtnToAppear(){
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BTN),"Cannot find search_close_btn", 5);
    }

    public void waitForCancelBtnToDisappear(){
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BTN),"Cancel Btn is still displayed", 5);
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BTN), "Cannot find and click search_close_btn",
                5);
    }

    public int getAmountOfFoundArticles(){

        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_LOCATOR),
                "Cannot find request.",
                15);

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_LOCATOR));
    }

    public void waitForEmptyResultLabel(){
        this.waitForElementPresent(By.xpath(EMPTY_RESULT_LABEL), "Cannot find empty result label", 15);
    }

    public void assertNoResultOfSearch(){
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_LOCATOR), "Search result must be 0");
    }
}
