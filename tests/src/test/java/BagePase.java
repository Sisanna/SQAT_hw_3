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


class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }
    
    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    } 
    
    public String getBodyText() {
        WebElement bodyElement = this.waitAndReturnElement(By.tagName("body"));
        return bodyElement.getText();
    }    

    public ResultPage navigate(By locator) {        
        this.waitAndReturnElement(locator).click();
        return new ResultPage(this.driver);
    }

    public void login(String username, String password) {
        this.waitAndReturnElement(By.xpath("//*[@id=\"login\"]/div[1]")).click();
        this.waitAndReturnElement(By.xpath("//*[@id=\"login_name\"]")).sendKeys(username);
        this.waitAndReturnElement(By.xpath("//*[@id=\"login_password\"]")).sendKeys(password);
        this.waitAndReturnElement(By.xpath("//*[@id=\"login_submit\"]")).click();
    }

    public void logout() {
        this.waitAndReturnElement(By.xpath("//*[@id=\"login\"]/div[1]/span[2]/a")).click();
    }
}
