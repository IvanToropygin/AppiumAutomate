import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;
import java.util.Locale;

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
    public void tearDown() {driver.quit();}

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
            Assert.assertTrue((i+1) + " - элемент из найденных - " + listOfElements.size() + ": не содержит нужный текст: " + expectedText,
                    actualText.contains(expectedText.toLowerCase()));
        }
    }

}