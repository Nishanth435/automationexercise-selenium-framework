package automationExcersie;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateAccount {
    WebDriver driver;
    @Test(priority = 1)
    public void URL() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://automationexercise.com/");
    }

    @Test(priority = 2)
    public void createAccount() {
        //driver.findElement(By.xpath("//a[contains(text(),'Signup')]")).click();
    	driver.findElement(By.xpath("//a[@href='/login']")).click();
    	driver.findElement(By.xpath("//input[@type='text']")).sendKeys("Nishanth");
    	driver.findElement(By.xpath("(//input[@type='email'])[2]")).sendKeys("demo123@gmail.com");
    	//driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys("test@gmail.com");
    	driver.findElement(By.xpath("//label[@for='id_gender1']")).click();
    	driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123");

    	// Select Day
    	WebElement day = driver.findElement(By.xpath("//select[@id='days']"));
    	Select selectDay = new Select(day);
    	selectDay.selectByVisibleText("6");

    	// Select Month
    	WebElement month = driver.findElement(By.xpath("//select[@id='months']"));
    	Select selectMonth = new Select(month);
    	selectMonth.selectByVisibleText("November");

    	// Select Year
    	WebElement year = driver.findElement(By.xpath("//select[@id='years']"));
    	Select selectYear = new Select(year);
    	selectYear.selectByVisibleText("2000");
    	
    	 selectDropdown(By.id("days"), "6");
    	 selectDropdown(By.id("months"), "November");
    	 selectDropdown(By.id("years"), "2000");
    	
    	
    	}
    public void selectDropdown(By locator, String value) {
	    Select s = new Select(driver.findElement(locator));
	    s.selectByVisibleText(value);
    }
   
}

//wait.until(ExpectedConditions.elementToBeClickable(By.id("dismiss-button"))).click();
//WebElement frame = driver.findElement( By.xpath("//iframe[@id='aswift_0' or @id='aswift_1' or @id='aswift_2' or @id='aswift_3' or  @id='aswift_4']"));
try {
    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
            By.cssSelector("iframe[id^='aswift']")));
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@id='dismiss-button']"))).click();
} catch (TimeoutException e) {
    System.out.println("No ad");
} finally {
    driver.switchTo().defaultContent();
}
