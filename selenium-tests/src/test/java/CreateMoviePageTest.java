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

public class CreateMoviePageTest {
    private static final ChromeOptions chromeOptions = new ChromeOptions();
    private WebDriver driver;
    private static final String baseUrl = "http://localhost:4200";
    private static final String movieListUrl = "http://localhost:4200/movies/list";
    private static final String createMovieUrl = "http://localhost:4200/movies/create";

    @FindBy(xpath = "//button[contains(text(), 'Create')]")
    private WebElement createButton;

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
    void when_enteredCorrectData_then_createMovie () throws InterruptedException {
        // given
        Movie movie = Movie.builder()
                .title("Avatar")
                .description("A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.")
                .lengthInMinutes(162)
                .releaseYear(2009)
                .build();
        driver.get(createMovieUrl);

        // when
        titleInput.sendKeys(movie.title());
        descriptionInput.sendKeys(movie.description());
        lengthInMinutesInput.sendKeys(movie.lengthInMinutes().toString());
        releaseYearInput.sendKeys(movie.releaseYear().toString());
        createButton.click();

        // then
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.urlToBe(movieListUrl));

        String createdTitleXPath = String.format("//h5[contains(text(), '%s')]", movie.title());
        WebElement createdTitle = driver.findElement(By.xpath(createdTitleXPath));

        Assertions.assertThat(createdTitle.isDisplayed()).isTrue();
    }

    @Test
    void when_enteredIncorrectTitleData_then_createButtonIsDisabled () throws InterruptedException {
        // given
        Movie movie = Movie.builder()
                .title("A")
                .description("A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.")
                .lengthInMinutes(162)
                .releaseYear(2009)
                .build();
        driver.get(createMovieUrl);

        // when
        titleInput.sendKeys(movie.title());
        descriptionInput.sendKeys(movie.description());
        lengthInMinutesInput.sendKeys(movie.lengthInMinutes().toString());
        releaseYearInput.sendKeys(movie.releaseYear().toString());

        // then
        Assertions.assertThat(createButton.isEnabled()).isFalse();
    }

    @Test
    void when_enteredIncorrectReleaseYearData_then_createButtonIsDisabled () throws InterruptedException {
        // given
        Movie movie = Movie.builder()
                .title("Avatar")
                .description("A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.")
                .lengthInMinutes(162)
                .releaseYear(20)
                .build();
        driver.get(createMovieUrl);

        // when
        titleInput.sendKeys(movie.title());
        descriptionInput.sendKeys(movie.description());
        lengthInMinutesInput.sendKeys(movie.lengthInMinutes().toString());
        releaseYearInput.sendKeys(movie.releaseYear().toString());

        // then
        Assertions.assertThat(createButton.isEnabled()).isFalse();
    }
}
