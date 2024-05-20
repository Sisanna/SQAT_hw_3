import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;


class MainPage extends BasePage {
    private By searchBarBy = By.xpath("//*[@id=\"search-text\"]");
    
    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://animeaddicts.hu/");
    } 
    
    public ResultPage search(String searchQuery) {        
        this.waitAndReturnElement(searchBarBy).sendKeys(searchQuery + "\n");
        return new ResultPage(this.driver);
    }
}
