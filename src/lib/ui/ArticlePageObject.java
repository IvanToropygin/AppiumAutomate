package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{
    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text = 'View page in browser']",
            OPTIONS_BTN = "//android.widget.ImageView[@content-desc=\"More options\"]",
            ADD_TO_READING_LIST_BTN = "//*[@text = 'Add to reading list']",
            GOT_IT_BTN = "org.wikipedia:id/onboarding_button",
            LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            OK_BTN = "//*[@text = 'OK']",
            LIST_NAME_TPL = "//*[@text = '{LIST_NAME}']",
            CLOSE_ARTICLE_BTN = "//android.widget.ImageButton[@content-desc = \"Navigate up\"]";
    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    private static String getListXPathByName(String listName){
        return LIST_NAME_TPL.replace("{LIST_NAME}", listName);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE),
                "Cannot find article title on page", 15);
    }

    public String getArticleTitle(){
        return waitForTitleElement().getAttribute("text");
    }


    public void swipesToFooter(){this.swipeToFindElement(By.xpath(FOOTER_ELEMENT),"Cannot find end of article",20);
    }

    public void addArticleToNewList(String newListName){
        this.waitForElementAndClick(By.xpath(OPTIONS_BTN), "Cannot find button to open article options",5);
        this.waitForElementAndClick(By.xpath(ADD_TO_READING_LIST_BTN),"Cannot find option to add article to reading list",5);
        this.waitForElementAndClick(By.id(GOT_IT_BTN),"Cannot find 'GOT IT' tip overlay",5);
        this.waitForElementAndClear(By.id(LIST_NAME_INPUT),"Cannot find input to clear list name",5);
        this.waitForElementAndSendKeys(By.id(LIST_NAME_INPUT), newListName, "Cannot find input to set list name",5);
        this.waitForElementAndClick(By.xpath(OK_BTN),"Cannot find 'OK' button",5);
    }

    public void addArticleToExistingList(String listName){
        this.waitForElementAndClick(By.xpath(OPTIONS_BTN), "Cannot find button to open article options",5);
        this.waitForElementAndClick(By.xpath(ADD_TO_READING_LIST_BTN),"Cannot find option to add article to reading list",5);
        this.waitForElementAndClick(By.xpath(getListXPathByName(listName)),"Cannot find existing list name",5);
    }

    public void closeArticle(){
        this.waitForElementAndClick(By.xpath(CLOSE_ARTICLE_BTN),"Cannot find X button click",5);
    }
}
