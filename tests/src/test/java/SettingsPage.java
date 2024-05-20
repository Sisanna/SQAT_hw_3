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

class SettingsPage extends BasePage {
    private By nameInputBy = By.xpath("//*[@id=\"postmodify\"]/table/tbody/tr[7]/td[2]/input");
    private By addressInputBy = By.xpath("//*[@id=\"postmodify\"]/table/tbody/tr[8]/td[2]/input");
    private By birthYearDropDownBy = By.xpath("//*[@id=\"postmodify\"]/table/tbody/tr[9]/td[2]/div[1]/a");
    //private By birthYearSelectionBy = By.xpath("//*[@id=\"postmodify\"]/table/tbody/tr[9]/td[2]/div[1]/div/ul/li[25]");
    private By bioAreaBy = By.xpath("//*[@id=\"biography\"]");
    private By passwordInputBy = By.xpath("//*[@id=\"postmodify\"]/table/tbody/tr[22]/td/input[1]");
    private By modifyButtonBy = By.xpath("//*[@id=\"postmodify\"]/table/tbody/tr[22]/td/input[2]");
    
    public SettingsPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://animeaddicts.hu/user.php?userfunction");
    }

    public void fillOut(String name, String address, String birthYear, String bio, String password) {
        this.waitAndReturnElement(nameInputBy).sendKeys(name);
        this.waitAndReturnElement(addressInputBy).sendKeys(address);

        WebElement birthYearDropDown = this.waitAndReturnElement(birthYearDropDownBy);
        birthYearDropDown.click();        
        WebElement birthYearSelection = this.waitAndReturnElement(By.xpath("//*[@id='postmodify']/table/tbody/tr[9]/td[2]/div[1]/div/ul/li[contains(text(), '" + birthYear + "')]"));
        birthYearSelection.click();

        this.waitAndReturnElement(bioAreaBy).sendKeys(bio);        
        this.waitAndReturnElement(passwordInputBy).sendKeys(password);
        this.waitAndReturnElement(modifyButtonBy).click();
    }
}
