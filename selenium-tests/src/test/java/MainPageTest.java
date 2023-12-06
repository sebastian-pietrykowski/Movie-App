import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {

    private static final ChromeOptions chromeOptions = new ChromeOptions();
    private WebDriver driver;
    private static final String baseUrl = "http://localhost:4200";
    private static final String movieListUrl = "http://localhost:4200/movies/list";
    private static final String createMovieUrl = "http://localhost:4200/movies/create";

    @FindBy(xpath = "//a[contains(text(),'Home')]")
    private WebElement homeNavigationButton;

    @FindBy(xpath = "//ul[contains(@class, 'navbar-nav')]/li/a[contains(text(), 'Create movie')]")
    private WebElement createMovieNavigationButton;

    @FindBy(xpath = "//div[contains(@class, 'container')]//a[contains(text(), 'Create movie')]")
    private WebElement createMovieButton;

    @FindBy(xpath = "//div[contains(@class, 'card')]")
    private WebElement card;

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
    void when_enteredHomePage_then_checkPageTitle () {
        // given
        driver.get(baseUrl);
        String expectedTitle = "MovieFrontend";

        // when
        String actualTitle = driver.getTitle();

        // then
        Assertions.assertThat(actualTitle).isEqualTo(expectedTitle);
    }

    @Test
    void when_clickedHomeNavigationButton_then_navigateToMovieListPage() {
        // given
        driver.get(baseUrl);

        // when
        homeNavigationButton.click();

        // then
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertThat(actualUrl).isEqualTo(movieListUrl);
    }

    @Test
    void when_clickedCreateMovieNavigationButton_then_navigateCreateMoviePage() {
        // given
        driver.get(baseUrl);

        // when
        createMovieNavigationButton.click();

        // then
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertThat(actualUrl).isEqualTo(createMovieUrl);
    }

    @Test
    void when_clickedCreateMovieButton_then_navigateCreateMoviePage() {
        // given
        driver.get(baseUrl);

        // when
        createMovieButton.click();

        // then
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertThat(actualUrl).isEqualTo(createMovieUrl);
    }

    @Test
    void when_clickedDelete_then_deletesMovie() throws InterruptedException {
        // given
        driver.get(movieListUrl);
        WebElement deleteButtonInList = card.findElement(
                By.xpath(".//ul/li/div/div/button[contains(text(), 'Delete')]")
        );
        WebElement titleElement = card.findElement(
                By.xpath(".//div/h5[contains(@class, 'card-title')]")
        );
        String title = titleElement.getText();

        // when
        deleteButtonInList.click();
        Thread.sleep(1000);

        String titleXPath = String.format(
                "//*[contains(text(), '%s')]",
                title
        );
        Assertions.assertThatThrownBy(() ->  driver.findElement(By.xpath(titleXPath)).isDisplayed())
                .isInstanceOf(NoSuchElementException.class);
    }
}
