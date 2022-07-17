package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String error_message, long timeoutSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by,error_message, 5);
    }

    public WebElement waitForElementAndClick(By by, String error_message, long timeoutSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String error_message, long timeoutSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeoutSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutSeconds);
        element.clear();
        return element;
    }

    public void assertElementHasText(By by, String expectedText, String error_message){
        WebElement element = driver.findElement(by);
        String actualText = element.getAttribute("text");
        Assert.assertEquals(error_message, actualText, expectedText);
    }

    public void assertElementsMoreThanOne(By by, String error_message){
        Assert.assertTrue(error_message, driver.findElements(by).size() > 1);
    }

    public void assertElementsHasText(By by, String expectedText){
        List<WebElement> listOfElements = driver.findElements(by);
        for (int i = 0; i < listOfElements.size(); i++){
            String actualText = listOfElements.get(i).getAttribute("text").toLowerCase();
            Assert.assertTrue((i+1) + " - элемент из найденных - " + listOfElements.size() + ": не содержит " +
                            "нужный текст: " + expectedText,
                    actualText.contains(expectedText.toLowerCase()));
        }
    }

    public void swipeUp(int timeOfSwipe){
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

    public void swipeUpQuick(){
        swipeUp(200);
    }

    public void swipeToFindElement(By by, String error_message, int max_swipes){
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

    public void swipeElementToLeft(By by, String error_message){
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

    public int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String error_message){
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0){
            String defaultMessage = "An element '" + by.toString() + "' supposed tobe not present. ";
            throw new AssertionError(defaultMessage + "" + error_message);
        }
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage,timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void assertElementPresent(By by, String error_message){
        if (!driver.findElement(by).isDisplayed()){
            String defaultMessage = "An element '" + by.toString() + "' supposed tobe present";
            throw new AssertionError(defaultMessage + "" + error_message);
        }
    }
}
