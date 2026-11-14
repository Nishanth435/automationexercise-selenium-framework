package automationExcersie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FileUploadTest {
	WebDriver driver ;
	TakesScreenshot ts;
	

	@Test
	public void setup() throws IOException {
		driver = new EdgeDriver();
		// Step 1: Open the upload page
	    driver.get("https://the-internet.herokuapp.com/upload");
		//driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	@Test
	public void verifyFileUpload() throws IOException
	{
		// Step 2: Select the local file
	    File file = new File("src/test/resources/data/CardDetails.xlsx");
	    driver.findElement(By.id("file-upload")).sendKeys(file.getAbsolutePath());
	    driver.findElement(By.id("file-submit")).click();
	    String expected = "File Uploaded!";
	    String actual = driver.findElement(By.tagName("h3")).getText().trim();
	    Assert.assertEquals(actual, expected, "Success message mismatch");
	    
	    
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
	    File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File desc= new File("src/test/resources/Screenshot/screenshot_" + time + ".jpg");
		FileHandler.copy(source,desc);
		
		driver.quit();
	    }
	
}