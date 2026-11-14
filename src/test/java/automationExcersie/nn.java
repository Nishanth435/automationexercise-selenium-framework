	package automationExcersie;
	import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Driver;
	import java.time.Duration;
	import java.util.ArrayList;
	import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.TimeoutException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.edge.EdgeDriver;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.Readexcel;
	
	
	public class nn {
		 WebDriver driver;
		    WebDriverWait wait;
			List<String> selectedProducts = new ArrayList<>();
			
			// Address data (entered once at signup, reused/verified at checkout)
			String firstName = "Mohan";
			String lastName = "K";
			String address1 = "OMR Road";
			String country = "India";
			String state = "Tamil Nadu";
			String city = "Chennai";
			String zipcode = "600096";
			String mobile = "9934569043";
            @Test(priority = 1)
		    public void URL() {
		        driver = new EdgeDriver();
		        driver.manage().window().maximize();
		        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		        driver.get("https://automationexercise.com/");
		    }
            @Test(priority = 2)

		    public void Login()
		    {
		       driver.findElement(By.xpath("//a[@href='/login']")).click();
		       driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("mnp004@gmail.com");
		       driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("123");
		       driver.findElement(By.xpath("//button[text()='Login']")).click();
		       
		    
		       
		    }
            @Test(priority = 3)

		    public void getproduct() {
		        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        // 1. Open Products page (click once)
		        jsClick(By.xpath("//a[contains(text(),'Products')]"));

		        // 2. Collect product names from the main list only
		        List<WebElement> products = driver.findElements(
		                By.xpath("//div[@class='features_items']//div[@class='productinfo text-center']/p"));

		        List<String> productNames = new ArrayList<>();
		        for (WebElement product : products) {
		            String name = product.getAttribute("textContent").replaceAll("\\s+", " ").trim();
		            if (!name.isEmpty()) {
		                productNames.add(name);
		            }
		        }
		        System.out.println(productNames);

		        // 3. Add the first 3 products to the cart
		        for (int i = 0; i < 3; i++) {
		            String productname = productNames.get(i);  // <-- this was missing
		            selectedProducts.add(productname);

		            jsClick(By.xpath("//div[@class='features_items']//div[@class='productinfo text-center'][p[normalize-space()='"
		                    + productname + "']]//a[contains(@class,'add-to-cart')]"));

		            // wait for the popup, then close it
		            wait.until(ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("//button[text()='Continue Shopping']")));
		            jsClick(By.xpath("//button[text()='Continue Shopping']"));
		        }
		    }
            @Test(priority = 4)

		    public void cartpage()
		    {
		    	wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/view_cart']"))).click();
		    	By cartitems=By.xpath("//td[@class='cart_description']//h4/a");
		    	//By cartitems= By.xpath("//tr[starts-with(@id,'product-')]");
		    	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartitems));
		    	
				// Collect product names from the cart
				List<String> cartNames = new ArrayList<>();
				for (WebElement item : driver.findElements(cartitems)) {
					cartNames.add(item.getAttribute("textContent"));
				}
				//System.out.println("Cart products: " + cartNames);
				System.out.println("Expected: " + selectedProducts);
				System.out.println("Cart    : " + cartNames);
				// Verify count
				Assert.assertEquals(cartNames.size(), selectedProducts.size(),
						"Cart item count does not match the number of products added");

		    }
            @Test(priority = 5)

		    public void ProceedToCheckout() {
		    	
		    	driver.findElement(By.xpath("//a[text()='Proceed To Checkout']")).click();
		    	By delivery = By.id("address_delivery");
				By billing = By.id("address_invoice");
				wait.until(ExpectedConditions.visibilityOfElementLocated(delivery));
		 
				String deliveryText = driver.findElement(delivery).getText();
				String billingText = driver.findElement(billing).getText();
				System.out.println("Delivery address:\n" + deliveryText);
				System.out.println("Billing address:\n" + billingText);
		 
			/*	for (String text : new String[] { deliveryText, billingText }) {
					Assert.assertTrue(text.contains(firstName), "First name missing in address");
					Assert.assertTrue(text.contains(address1), "Address line missing");
					Assert.assertTrue(text.contains(city), "City missing");
					Assert.assertTrue(text.contains(state), "State missing");
					Assert.assertTrue(text.contains(zipcode), "Zip code missing");
					Assert.assertTrue(text.contains(mobile), "Mobile number missing"); 
				}*/
				driver.findElement(By.xpath("//a[text()='Place Order']")).click();

		    }
		    @Test(priority= 6)
            public void paymentcar() throws IOException
            {
		    	Map<String, String> card = Readexcel.readPaymentDetails();

		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='name-on-card']"))).sendKeys(card.get("NameOnCard"));

		    	driver.findElement(By.xpath("//input[@data-qa='card-number']"))
		    	      .sendKeys(card.get("CardNumber"));

		    	driver.findElement(By.xpath("//input[@data-qa='cvc']"))
		    	      .sendKeys(card.get("CVC"));

		    	driver.findElement(By.xpath("//input[@data-qa='expiry-month']"))
		    	      .sendKeys(card.get("ExpiryMonth"));

		    	driver.findElement(By.xpath("//input[@data-qa='expiry-year']"))
		    	      .sendKeys(card.get("ExpiryYear"));
		    	
				driver.findElement(By.xpath("//button[@id='submit']")).click();

		    	
            }

//            @Test(priority = 7)
//
//		    //11.Downloadinvoice
//            public void invoice() throws InterruptedException {
//
//                // 1. Your Downloads folder
//                File folder = new File(System.getProperty("user.home") + "/Downloads");
//
//                // 2. Click Download Invoice
//                jsClick(By.xpath("//a[contains(@href,'download_invoice')]"));
//
//                // 3. Wait 5 seconds for the download
//                Thread.sleep(5000);
//                
//            	jsClick(By.xpath("//a[text()='Continue']"));
//
//
//                // 4. Check the file is there
//                File invoice = new File(folder, "invoice.txt");
//                Assert.assertTrue(invoice.exists(), "Invoice was not downloaded");
//            }
		    public void jsClick(By locator) {
		        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		    }
		    
		  
	
	}
