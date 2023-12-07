package com.example.movie.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieSeleniumTest {
    private static final ChromeOptions chromeOptions = new ChromeOptions();
    private WebDriver driver;
    private static final String movieListUrl = "http://localhost:4200/movies/list";
    private static final String createMovieUrl = "http://localhost:4200/movies/create";
    private static final String baseUrl = "http://localhost:4200";

    @FindBy(xpath = "//a[contains(text(),'Home')]")
    private WebElement homeNavigationButton;

    @FindBy(xpath = "//ul[contains(@class, 'navbar-nav')]/li/a[contains(text(), 'Create movie')]")
    private WebElement createMovieNavigationButton;

    @FindBy(xpath = "//div[contains(@class, 'container')]//a[contains(text(), 'Create movie')]")
    private WebElement createMovieButton;

    @FindBy(xpath = "//div[contains(@class, 'card')]")
    private WebElement card;

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
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-dev-shm-usage");
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
    @Order(1)
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

        Thread.sleep(1000);
        String createdTitleXPath = String.format("//h5[contains(text(), '%s')]", movie.title());
        WebElement createdTitle = driver.findElement(By.xpath(createdTitleXPath));

        Assertions.assertThat(createdTitle.isDisplayed()).isTrue();
    }

    @Test
    @Order(2)
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
    @Order(3)
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

    @Test
    @Order(4)
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
    @Order(5)
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
    @Order(6)
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
    @Order(7)
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
    @Order(8)
    void when_enteredCorrectData_then_updateMovie () throws InterruptedException {
        // given
        driver.get(movieListUrl);
        Thread.sleep(1000);
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
        Thread.sleep(1000);

        // when
        titleInput.clear();
        titleInput.sendKeys(newTitle);
        descriptionInput.click();
        Thread.sleep(1000);

        WebElement updateButtonInForm = driver.findElement(By.xpath("//button[contains(text(), 'Update')]"));
        updateButtonInForm.click();
        Thread.sleep(1000);

        // then
        wait.until(ExpectedConditions.urlContains(movieListUrl));
        String updatedTitleXPath = String.format(
                "//*[contains(text(), '%s')]",
                newTitle
        );
        WebElement updatedTitleElement = driver.findElement(By.xpath(updatedTitleXPath));
        Thread.sleep(1000);

        Assertions.assertThat(updatedTitleElement.isDisplayed()).isTrue();
    }

    @Test
    @Order(9)
    void when_clickedDelete_then_deletesMovie() throws InterruptedException {
        // given
        driver.get(movieListUrl);
        Thread.sleep(1000);
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
