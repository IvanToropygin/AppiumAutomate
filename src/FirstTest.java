import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;
    protected void setUp() throws Exception {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }


    @Test
    public void testClearSearch(){
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find searchInputOnMainPage",
                5);

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find searchInputOnSearchPage",
                5);

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Field don't cleared",
                5);
    }

    @Test
    public void testSearchTitleTextCompare(){
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find searchInputOnMainPage",
                5);
        MainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Text in input field don't compare");
    }

    @Test
    public void testSearchingArticlesAndCancel(){
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find searchInputOnMainPage",
                5);

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find searchInputOnSearchPage",
                5);

        MainPageObject.assertElementsMoreThanOne(
                By.className("android.widget.FrameLayout"),
                "wrong count articles");

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Field don't cleared",
                5);
    }

    @Test
    public void testArticlesContainsSearchingText(){
        String search = "Java";

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find searchInputOnMainPage",
                5);

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search,
                "Cannot find searchInputOnSearchPage",
                5);

        MainPageObject.assertElementsHasText(
                By.id("org.wikipedia:id/page_list_item_title"),
                search);
    }

    @Test
    public void testSaveTwoArticlesToMyList(){
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search Input On Search Page",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Island')]"),
                "Cannot find searchInputOnMainPage",
                5);

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text = 'Add to reading list']"),
                "Connot find option to add article to reading list",
                5);

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'GOT IT' tip overlay",
                5);

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set list name",
                5);

        String nameOfList = "HomeWork";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfList
                , "Cannot find input to set list name",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text = 'OK']"),
                "Cannot find 'OK' button",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc = \"Navigate up\"]"),
                "Cannot find X button click",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search Input On Search Page",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'High-level')]"),
                "Cannot find searchInputOnMainPage",
                5);

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                20);

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text = 'Add to reading list']"),
                "Connot find option to add article to reading list",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, '" + nameOfList + "')]"),
                "Cannot find reading list :"+ nameOfList,
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc = \"Navigate up\"]"),
                "Cannot find X button click",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc=\"My lists\"]"),
                "Cannot find My list navigation button click",
                5);

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/item_container"),
                "Cannot find '" + nameOfList + "' list",
                15);

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@class = 'android.widget.LinearLayout']//*[contains(@text, 'island')]"),
                "List not opened");

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@class = 'android.widget.LinearLayout']//*[contains(@text, 'island')]"),
                "Cannot left swipe article");

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@class = 'android.widget.LinearLayout']//*[contains(@text, 'high-level')]"),
                "Cannot find second Article after first Article Deleted deleted");

        String expectedText = "JavaScript";
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text = '" + expectedText + "']"),
                "Cannot find text 'high-level'"
                , 15);

        String actualText = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@text = '" + expectedText + "']"),
                "text",
                "Cannot find Article :" + expectedText,
                5);

        assertEquals("Article not compare", expectedText, actualText);
    }

    @Test
    public void testAssertTitle(){
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search Input On Search Page",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Island')]"),
                "Cannot find searchInputOnMainPage",
                5);

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find 'Java' after click on Title");
    }
}