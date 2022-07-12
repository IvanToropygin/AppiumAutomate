import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "and80");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\itori\\OneDrive\\Desktop\\AppiumAutomate" +
                "\\JavaAppiumAutomation\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find searchInputOnSearchPage",
                5);

        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Island')]"),
                "Cannot find searching result",
                15);
    }

    @Test
    public void testCancelSearch(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find search_close_btn",
                5);

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X btn still present on Page",
                5);
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find searchInputOnSearchPage",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Island')]"),
                "Cannot find searchInputOnMainPage",
                5);

        WebElement titleElement = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_subtitle_text"),
                "Cannot find article title",
                15);

        String titleExpected = "Island in Indonesia, Southeast Asia";
        String titleActual = titleElement.getAttribute("text");

        Assert.assertEquals(
                "unexpected title",
                titleActual,
                titleExpected
        );
    }

    @Test
    public void testClearSearch(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find searchInputOnSearchPage",
                5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Field don't cleared",
                5);
    }

    @Test
    public void testSearchTitleTextCompare(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find searchInputOnMainPage",
                5);
        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Text in input field don't compare");
    }

    @Test
    public void testSearchingArticlesAndCancel(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find searchInputOnSearchPage",
                5);

        assertElementsMoreThanOne(
                By.className("android.widget.FrameLayout"),
                "wrong count articles");

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Field don't cleared",
                5);
    }

    @Test
    public void testArticlesContainsSearchingText(){
        String search = "Java";

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search,
                "Cannot find searchInputOnSearchPage",
                5);

        assertElementsHasText(
                By.id("org.wikipedia:id/page_list_item_title"),
                search);
    }

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find searchInputOnSearchPage",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = 'Appium']"),
                "Cannot find 'Appium' in search",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        swipeToFindElement(By.xpath("//*[@text = 'View page in browser']"),
                "Cannot find end of article title", 20);
    }

    @Test
    public void saveFirstArticleToMyList(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search Input On Search Page",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Island')]"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text = 'Add to reading list']"),
                "Connot find option to add article to reading list",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'GOT IT' tip overlay",
                5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set list name",
                5);

        String nameOfList = "Learning Java";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfList
                , "Cannot find input to set list name",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text = 'OK']"),
                "Cannot find 'OK' button",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc = \"Navigate up\"]"),
                "Cannot find X button click",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc=\"My lists\"]"),
                "Cannot find My list navigation button click",
                5);


        waitForElementAndClick(
                By.xpath("//*[@text = '" + nameOfList + "']"),
                "Cannot find '" + nameOfList + "' list",
                7);

        waitForElementPresent(
                By.xpath("//*[@class = 'android.widget.LinearLayout']//*[contains(@text, 'island')]"),
                "List not opened");

        swipeElementToLeft(
                By.xpath("//*[@class = 'android.widget.LinearLayout']//*[contains(@text, 'island')]"),
                "Cannot left swipe article");

        waitForElementNotPresent(
                By.xpath("//*[@class = 'android.widget.LinearLayout']//*[contains(@text, 'island')]"),
                "Article not deleted",
                5);
    }

    @Test
    public void testAmountOfNotEmptySearch(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        String searchLine = "linkin park discography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search Input On Search Page",
                5);

        String searchResultsLocator = "//*[@resource-id = 'org.wikipedia:id/fragment_feed_feed']//*[@resource-id = 'org.wikipedia:id/search_container']";

        waitForElementPresent(
                By.xpath(searchResultsLocator),
                "Cannot find request: " + searchLine,
                15);

        int amountSearchResults = getAmountOfElements(By.xpath(searchResultsLocator));

        Assert.assertTrue("We not found results", amountSearchResults > 0);
    }

    @Test
    public void testAmountOfEmptySearch(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        String searchLine = "java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search Input On Search Page",
                5);

        String searchResultsLocator = "//*[@resource-id = 'org.wikipedia:id/fragment_feed_feed']//*[@resource-id = 'org.wikipedia:id/search_container']";
        String emptyResultLabel = "//*[@text = 'No results found']";

        waitForElementPresent(
                By.xpath(emptyResultLabel),
                "Cannot find empty result label by request: " + searchLine,
                5);

        assertElementNotPresent(
                By.xpath(searchResultsLocator),
                "Searching results not empty");
    }

    @Test
    public void testChangeScreenOrientationOnSearchResult(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        String searchLine = "java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search Input On Search Page",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Island')]"),
                "Cannot find 'Island'",
                5);

        String titleBeforeRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title after click",
                15);

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String titleAfterRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title after rotation",
                15);

        Assert.assertEquals("Title not compare after rotation", titleBeforeRotation, titleAfterRotation);

        driver.rotate(ScreenOrientation.PORTRAIT);

        String titleAfterSecondRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title after second rotation",
                15);

        Assert.assertEquals("Title not compare after rotation", titleBeforeRotation, titleAfterSecondRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        String searchLine = "java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search Input On Search Page",
                5);

        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Island')]"),
                "Cannot find 'Island'",
                5);

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Island')]"),
                "Cannot find 'Island' after returning from background");
    }

    @Test
    public void saveTwoArticlesToMyList(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search Input On Search Page",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Island')]"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text = 'Add to reading list']"),
                "Connot find option to add article to reading list",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'GOT IT' tip overlay",
                5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set list name",
                5);

        String nameOfList = "HomeWork";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfList
                , "Cannot find input to set list name",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text = 'OK']"),
                "Cannot find 'OK' button",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc = \"Navigate up\"]"),
                "Cannot find X button click",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search Input On Search Page",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'High-level')]"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                20);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                "Cannot find button to open article options",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text = 'Add to reading list']"),
                "Connot find option to add article to reading list",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, '" + nameOfList + "')]"),
                "Cannot find reading list :"+ nameOfList,
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc = \"Navigate up\"]"),
                "Cannot find X button click",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc=\"My lists\"]"),
                "Cannot find My list navigation button click",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/item_container"),
                "Cannot find '" + nameOfList + "' list",
                15);

        waitForElementPresent(
                By.xpath("//*[@class = 'android.widget.LinearLayout']//*[contains(@text, 'island')]"),
                "List not opened");

        swipeElementToLeft(
                By.xpath("//*[@class = 'android.widget.LinearLayout']//*[contains(@text, 'island')]"),
                "Cannot left swipe article");

        waitForElementPresent(
                By.xpath("//*[@class = 'android.widget.LinearLayout']//*[contains(@text, 'high-level')]"),
                "Cannot find second Article after first Article Deleted deleted");

        String expectedText = "JavaScript";
        waitForElementAndClick(
                By.xpath("//*[@text = '" + expectedText + "']"),
                "Cannot find text 'high-level'"
                , 15);

        String actualText = waitForElementAndGetAttribute(
                By.xpath("//*[@text = '" + expectedText + "']"),
                "text",
                "Cannot find Article :" + expectedText,
                5);

        Assert.assertEquals("Article not compare", expectedText, actualText);
    }

    @Test
    public void assertTitle(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Input On Main Page",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search Input On Search Page",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Island')]"),
                "Cannot find searchInputOnMainPage",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find 'Java' after click on Title");
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by,error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expectedText, String error_message){
        WebElement element = driver.findElement(by);
        String actualText = element.getAttribute("text");
        Assert.assertEquals(error_message, actualText, expectedText);
    }

    private void assertElementsMoreThanOne(By by, String error_message){
       Assert.assertTrue(error_message, driver.findElements(by).size() > 1);
    }

    private void assertElementsHasText(By by, String expectedText){
        List<WebElement> listOfElements = driver.findElements(by);
        for (int i = 0; i < listOfElements.size(); i++){
            String actualText = listOfElements.get(i).getAttribute("text").toLowerCase();
            Assert.assertTrue((i+1) + " - элемент из найденных - " + listOfElements.size() + ": не содержит " +
                            "нужный текст: " + expectedText,
                    actualText.contains(expectedText.toLowerCase()));
        }
    }

    protected void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width/2, start_y = (int)(size.height * 0.8), end_y = (int)(size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeToFindElement(By by, String error_message, int max_swipes){
        int aiready_swiped = 0;
        while (driver.findElements(by).size() == 0){
            if (aiready_swiped > max_swipes){
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message,
                        0);
                return;
            }

            swipeUpQuick();
            ++aiready_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message){
        WebElement element = waitForElementPresent(by, error_message, 10);
        int left_x = element.getLocation().getX(), right_x = left_x + element.getSize().getWidth(),
        upper_y = element.getLocation().getY(), lower_y = upper_y + element.getSize().getHeight(),
        middle_y = (upper_y + lower_y)/2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(150)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message){
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0){
            String defaultMessage = "An element '" + by.toString() + "' supposed tobe not present";
            throw new AssertionError(defaultMessage + "" + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage,timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private void assertElementPresent(By by, String error_message){
        if (!driver.findElement(by).isDisplayed()){
            String defaultMessage = "An element '" + by.toString() + "' supposed tobe present";
            throw new AssertionError(defaultMessage + "" + error_message);
        }
    }
}