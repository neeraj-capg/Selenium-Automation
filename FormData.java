package hardikCode;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FormData {

    private static WebDriver driver;
    private static WebDriverWait wait;

    // Locators
    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BTN = By.name("login");

    private static final By ACCESS_LEVEL_LABEL =
            By.id("sp_field_label_level_of_access");

    private static final By REQUIRED_TIME_DROPDOWN =
            By.xpath("//div[@id='s2id_sp_formfield_required_time']/a");

    private static final By ADMIN_ACCESS_RADIO =
            By.xpath("//input[@value='full_admin_access']");

    private static final By ACKNOWLEDGEMENT =
            By.id("label_acknowledgement");

    private static final By BUSINESS_JUSTIFICATION =
            By.id("sp_formfield_business_justification");

    private static final By SUBMIT_BTN =
            By.id("submit-btn");

    private static final By CHECKOUT_BTN =
            By.xpath("//button[contains(@class,'btn-primary')]");

    private static final By REQUEST_STAGE =
            By.xpath("//td[1]/a");

    public static void main(String[] args) {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {

            driver.manage().window().maximize();

            driver.get(
                    "https://dev490712.service-now.com/sp?id=sc_cat_item&sys_id=7a0674352ba9c7102139f9def291bf5d");

            login("neeraj.pandey", "Neo@Code@123_");

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    ACCESS_LEVEL_LABEL));

            selectRequiredTime("3 hrs");

            clickUsingJS(ADMIN_ACCESS_RADIO);

            click(ACKNOWLEDGEMENT);

            enterText(BUSINESS_JUSTIFICATION,
                    "This is Automated Testing scenario");

            click(SUBMIT_BTN);

            click(CHECKOUT_BTN);

            String expectedStage = "Request Time-Limited Admin Role";

            String actualStage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            REQUEST_STAGE))
                    .getText();

            Assert.assertEquals(actualStage, expectedStage);

            System.out.println("Request submitted successfully");

        } finally {
            driver.quit();
        }
    }

    private static void login(String user, String pass) {
        enterText(USERNAME, user);
        enterText(PASSWORD, pass);
        click(LOGIN_BTN);
    }

    private static void selectRequiredTime(String duration) {

        click(REQUIRED_TIME_DROPDOWN);

        By option = By.xpath(
                "//li[normalize-space()='" + duration + "']");

        click(option);
    }

    private static void enterText(By locator, String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .sendKeys(value);
    }

    private static void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator))
                .click();
    }

    private static void clickUsingJS(By locator) {

        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
}