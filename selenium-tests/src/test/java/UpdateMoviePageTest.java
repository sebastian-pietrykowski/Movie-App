import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UpdateMoviePageTest {
    private static final ChromeOptions chromeOptions = new ChromeOptions();
    private WebDriver driver;
    private static final String movieListUrl = "http://localhost:4200/movies/list";

    @FindBy(id = "title-input")
    private WebElement titleInput;

    @FindBy(id = "description-input")
    private WebElement descriptionInput;

    @FindBy(id = "length-input")
    private WebElement lengthInMinutesInput;

    @FindBy(id = "release-year-input")
    private WebElement releaseYearInput;

    @BeforeAll
    static void setUpCrudAutomatedTests() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-gpu");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUpDriver() {
        driver = new ChromeDriver(chromeOptions);
        PageFactory.initElements(driver, this);
    }

    @AfterEach
    void tearDownDriver() {
        driver.quit();
    }

    @Test
    void when_enteredCorrectData_then_updateMovie () throws InterruptedException {
        // given
        driver.get(movieListUrl);
        WebElement card = driver.findElement(By.xpath("//div[contains(@class, 'card')]"));
        WebElement updateButtonInList = card.findElement(By.xpath(".//ul/li/div/div/a[contains(text(), 'Update')]"));
        WebElement titleElement = card.findElement(By.xpath(".//div/h5[contains(@class, 'card-title')]"));
        String title = titleElement.getText();

        updateButtonInList.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.urlContains("update"));
        Thread.sleep(1000);
        String newTitle = "Updated title";
        driver.get(driver.getCurrentUrl());

        // when
        titleInput.clear();
        titleInput.sendKeys(newTitle);
        descriptionInput.click();
        Thread.sleep(4000);

        WebElement updateButtonInForm = driver.findElement(By.xpath("//button[contains(text(), 'Update')]"));
        updateButtonInForm.click();
        Thread.sleep(2000);

        // then
        wait.until(ExpectedConditions.urlContains(movieListUrl));
        String updatedTitleXPath = String.format(
                "//*[contains(text(), '%s')]",
                newTitle
        );
        WebElement updatedTitleElement = driver.findElement(By.xpath(updatedTitleXPath));

        Assertions.assertThat(updatedTitleElement.isDisplayed()).isTrue();
    }
}
