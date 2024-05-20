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


class AdvancedSearchPage extends BasePage {
    private By advancedSearchBarBy = By.xpath("//*[@id=\"search-input\"]");
    
    public AdvancedSearchPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://animeaddicts.hu/search.php");
    }    
    
    public ResultPage advancedSearch(String searchQuery) {        
        this.waitAndReturnElement(advancedSearchBarBy).sendKeys(searchQuery + "\n");
        return new ResultPage(this.driver);
    }
}
