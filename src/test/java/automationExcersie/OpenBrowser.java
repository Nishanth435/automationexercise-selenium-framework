package automationExcersie;

import java.sql.Driver;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.TimeoutException;    
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class OpenBrowser {
    WebDriver driver;
    WebDriverWait wait;
    @Test(priority = 1)
    public void URL() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://automationexercise.com/");
    }
    @Test(priority =2)
    public void Login()
    {
       driver.findElement(By.xpath("//a[@href='/login']")).click();
       driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("mnp005@gmail.com");
       driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("123");
       driver.findElement(By.xpath("//button[text()='Login']")).click();
       
    
       
    }
    @Test(priority =3 )
    public void getproduct()
    {
    	wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/view_cart']"))).click();
    }

}
