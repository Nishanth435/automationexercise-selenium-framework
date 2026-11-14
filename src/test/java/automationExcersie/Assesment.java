package automationExcersie;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.Readexcel;

public class Assesment {
	WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor ts;
    
    //login data
    String username="Test1";
    String email ="mnp029@gmail.com";
    String pass ="123";
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

    //1.Browser Launch
    @BeforeClass
    public void OpenBrowser() {
    	driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
    }
    //2.Signup
    @Test(priority = 1)
    public void createAccount() {
    	driver.get("https://automationexercise.com/");
    	driver.findElement(By.xpath("//a[@href='/login']")).click();
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys(username);
    	driver.findElement(By.xpath("(//input[@type='email'])[2]")).sendKeys(email);
    	driver.findElement(By.xpath("//button[contains(text(),'Signup')]")).click();
    	driver.findElement(By.xpath("//label[@for='id_gender1']")).click();
    	
    	wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='id_gender1']"))).click();
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='password']"))).sendKeys(pass);
    	

    	//DOB
    	 selectDropdown(By.id("days"), "6");
    	 selectDropdown(By.id("months"), "November");
    	 selectDropdown(By.id("years"), "2000");
    	 driver.findElement(By.xpath("//label[@for='newsletter']")).click();
    	 driver.findElement(By.xpath("//input[@type='checkbox' and @name='optin']")).click();
    	
    	 
    	 driver.findElement(By.id("first_name")).sendKeys("Mohan");
         driver.findElement(By.id("last_name")).sendKeys("K");
         driver.findElement(By.id("address1")).sendKeys("OMR Road");
         selectDropdown(By.id("country"),"India");
         driver.findElement(By.id("state")).sendKeys("Tamil Nadu");
         driver.findElement(By.id("city")).sendKeys("Chennai");
         driver.findElement(By.id("zipcode")).sendKeys("600096");
         driver.findElement(By.id("mobile_number")).sendKeys("9934569043");
         driver.findElement(By.xpath("//button[(text()='Create Account')]")).click();
         String actual = driver.findElement(By.xpath("//h2[@data-qa='account-created']")).getText();
         Assert.assertEquals(actual, "ACCOUNT CREATED!");
         jsClick(By.xpath("//a[text()='Continue']"));

         //driver.findElement(By.xpath("//a[text()='Continue']")).click();
     	driver.findElement(By.xpath("//a[@href='/logout']")).click();

    	}
    //LOGIN
    @Test(priority= 2 ,dependsOnMethods = "createAccount")
    public void Login()
    {
       driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
       driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(pass);
       driver.findElement(By.xpath("//button[text()='Login']")).click();
       
    }
    
    //4 Get	all	product	names & 5
    
    @Test(priority=3,dependsOnMethods ="Login" )
    public void getproduct() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Open Products page (click once)
        jsClick(By.xpath("//a[contains(text(),'Products')]"));

        // 2. Collect product names from the main list only
        List<WebElement> products = driver.findElements(By.xpath("//div[@class='features_items']//div[@class='productinfo text-center']/p"));

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
            String productname = productNames.get(i);   // <-- this was missing
            selectedProducts.add(productname);


            jsClick(By.xpath("//div[@class='features_items']//div[@class='productinfo text-center'][p[normalize-space()='"
                    + productname + "']]//a[contains(@class,'add-to-cart')]"));

            // wait for the popup, then close it
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[text()='Continue Shopping']")));
            jsClick(By.xpath("//button[text()='Continue Shopping']"));
        }
    }
    
    //6.Go to	Cart
    @Test(priority=4 ,dependsOnMethods = "getproduct")
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
		/*for (String expected : selectedProducts) {
			Assert.assertTrue(cartNames.contains(expected), "Product missing in cart: " + expected);
		}*/

    }
   //8. Fill address details & 9.Place	Order
    @Test(priority=5 , dependsOnMethods = "cartpage")
    public void ProceedToCheckout() {
    	
    	driver.findElement(By.xpath("//a[text()='Proceed To Checkout']")).click();
    	By delivery = By.id("address_delivery");
		By billing = By.id("address_invoice");
		wait.until(ExpectedConditions.visibilityOfElementLocated(delivery));
 
		String deliveryText = driver.findElement(delivery).getText();
		String billingText = driver.findElement(billing).getText();
		System.out.println("Delivery address:\n" + deliveryText);
		System.out.println("Billing address:\n" + billingText);
 
		/* for (String text : new String[] { deliveryText, billingText }) {
			Assert.assertTrue(text.contains(firstName), "First name missing in address");
			Assert.assertTrue(text.contains(address1), "Address line missing");
			Assert.assertTrue(text.contains(city), "City missing");
			Assert.assertTrue(text.contains(state), "State missing");
			Assert.assertTrue(text.contains(zipcode), "Zip code missing");
			Assert.assertTrue(text.contains(mobile), "Mobile number missing"); 
	}*/
		driver.findElement(By.xpath("//a[text()='Place Order']")).click();
    }
    //10.	Enter	payment/card	details
    @Test(priority= 6,dependsOnMethods = "ProceedToCheckout")
    public void paymentcart() throws IOException
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
    
    @Test(priority = 7, dependsOnMethods = "paymentcart")

    //11.Downloadinvoice
    public void invoice() throws InterruptedException {

        // 1. Your Downloads folder
        File folder = new File(System.getProperty("user.home") + "/Downloads");

        // 2. Click Download Invoice
        jsClick(By.xpath("//a[contains(@href,'download_invoice')]"));

        Thread.sleep(5000);
        
    	jsClick(By.xpath("//a[text()='Continue']"));


        // 4. Check the file is there
        File invoice = new File(folder, "invoice.txt");
        Assert.assertTrue(invoice.exists(), "Invoice was not downloaded");
    }
    //12.logout
    
    
    @Test(priority=9, dependsOnMethods = "invoice")
    public void logout() {
		driver.findElement(By.partialLinkText("Logout")).click();
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("/login"), "Not redirected to login page");
		
		boolean loginHeading = driver.findElement(By.xpath("//h2[text()='Login to your account']")).isDisplayed();
		Assert.assertTrue(loginHeading, "Login heading not visible");

    }
    
    // part 2
    @Test(priority=10, dependsOnMethods = "logout")
    public void verifyFileUpload() throws IOException
    {
	    driver.get("https://the-internet.herokuapp.com/upload");
	    
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
    
    //dropdown element
    public void selectDropdown(By locator, String value) {
	    Select s = new Select(driver.findElement(locator));
	    s.selectByVisibleText(value);
    }
    public void jsClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
 
}
