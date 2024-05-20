import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.*;  

import java.net.URL;
import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainPageTest {
    public WebDriver driver;

    private By hoveredElementBy = By.xpath("//*[@id=\"slide\"]/li[1]/div[6]");
    private By tooltipBy = By.xpath("//*[@id=\"dhtmlpointer\"]");   // There's 2 solutions based on the cursor's position. This is the one that can be found while auto-hovering - the other one is dhtmltooltip
    
    @Before
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
    }

    @Test
    public void testPageTitle() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.login("testella", "asd123qwe");

        String title = driver.getTitle();
        assertTrue(title.contains("AnimeAddicts"));    
    }

    @Test
    public void testPageTitleWithoutLogin() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Wait until document.readyState is complete
        wait.until((ExpectedCondition<Boolean>) wd ->
            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
        );

        // Wait for specific elements that indicate the page has fully loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        // Retrieve the title using JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String title = (String) js.executeScript("return document.title;");
        assertFalse(title.contains("AnimeAddicts"));    // It won't get the title...

        // ... since it's not accessible without login
        String pageSource = driver.getPageSource();
        assertFalse(pageSource.contains("title"));
    
    }

    @Test
    public void testStaticCoworkersPage() {
        MainPage mainPage = new MainPage(this.driver);
        ResultPage coworkersPage = mainPage.navigate(By.xpath("//*[@id=\"bottom-menu\"]/ul/li[1]"));
        String bodyText = coworkersPage.getBodyText();
        assertTrue(bodyText.contains("Adminyonok"));
    }

    @Test
    public void testStaticPages() {
        String[] bottomPages = {"Statisztika", "Linkek", "Impresszum", "Jognyilatkozat"};  
        String[] expectedResults = {"Oldal statisztika", "Bittorentes anime", "Az AnimeAddicts mint fansub csapat:", "ArtisJus"};
        
        
        MainPage mainPage = new MainPage(this.driver); 
        mainPage.login("testella", "asd123qwe");

        for (int i = 0; i < 4; i++) {   
            mainPage = new MainPage(this.driver);     
            ResultPage page = mainPage.navigate(By.xpath("//div[@id='bottom-menu']//a[contains(text(), '" + bottomPages[i] + "')]"));
            String bodyText = page.getBodyText();
            assertTrue(bodyText.contains(expectedResults[i]));

            driver.navigate().back();
        }
    }    

    @Test
    public void testHistory() {     
        MainPage mainPage = new MainPage(this.driver);

        ResultPage statisticsPage = mainPage.navigate(By.xpath("//*[@id=\"bottom-menu\"]/ul/li[2]"));
        String title = driver.getTitle();
        assertTrue(title.contains("Statisztika"));

        ResultPage visitorStatiscticsPage = statisticsPage.navigate(By.xpath("//*[@id=\"right\"]/div[1]/div[2]/div[3]"));
        title = driver.getTitle();
        assertTrue(title.contains("Statisztika (alap)"));
    }

    @Test
    public void testHovering() {
        MainPage mainPage = new MainPage(this.driver);
        WebElement hoveredElement = mainPage.waitAndReturnElement(hoveredElementBy);

        Actions actions = new Actions(driver);
        actions.moveToElement(hoveredElement).perform();

        WebElement tooltip = mainPage.waitAndReturnElement(tooltipBy);
        assertTrue(tooltip.isDisplayed());
    }

    @Test
    public void testCorrectLogin() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.login("testella", "asd123qwe");
        assertTrue(mainPage.getBodyText().contains("Bejelentkezve: testella"));

        WebElement logoutButton = mainPage.waitAndReturnElement((By.xpath("//*[@id=\"login\"]/div[1]")));
        assertTrue(logoutButton.isDisplayed());
    }

    // DO NOT RUN THESE OFTEN: AFTER 5 ATTEMPTS LOGIN IS RESTRICTED FOR AN HOUR!!!
    @Test
    public void testInvalidUsernameLogin() {        
        MainPage mainPage = new MainPage(this.driver);
        mainPage.login("idegen", "asd123qwe");
        assertTrue(mainPage.getBodyText().contains("ERROR 601"));
    }

    @Test
    public void testInvalidPasswordLogin() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.login("testella", "password");
        assertTrue(mainPage.getBodyText().contains("ERROR 601"));
    }
    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
