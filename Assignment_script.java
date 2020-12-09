package Task1;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class script {
	String ChromePath = "C:\\Driver\\chromedriver.exe";
	String baseUrl = "https://www.amazon.in/";
	String baseUrl2 = "https://www.flipkart.com/";
	public WebDriver driver;
	public String product = "Apple iPhone 11 Pro Max";
	public String amazonPrice;
	public String flipkartPrice;

	@BeforeTest
	public void launchBrowser() {
		System.out.println("Launching the browser");
		System.setProperty("webdriver.chrome.driver","D:\\myself\\Selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// maximize window
		driver.manage().window().maximize();
		driver.get(baseUrl);
		System.out.println(driver.getCurrentUrl());
	}

	@Test(priority = 1)
	public void verifyHomepageTitle() {
		String expectedTitle = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(priority = 2)
	public void searchProduct() {
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys(product);
		searchBox.sendKeys(Keys.ENTER);
	}

	@Test(priority = 3)
	public void amazonCost() {
		List<WebElement> searchlist = driver
				.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
		for (int i = 0; i < searchlist.size(); i++) {

			if (searchlist.get(i).getText().contains(product)) {
				searchlist.get(i).click();
				break;
			}
		}
		Set<String> ids = driver.getWindowHandles();
		Iterator<String> it = ids.iterator();
		String parent = it.next();
		String child = it.next();
		driver.switchTo().window(child);
		WebElement a_Price = driver.findElement(By.id("priceblock_ourprice"));
		String AmazonPrice1 = a_Price.getText();
		amazonPrice = AmazonPrice1.substring(1);
		System.out.println("Amazon price for " + product + " is : " + amazonPrice + " Only");

	}

	@Test(priority = 4)
	public void navigateToFlipkart() {
		driver.get(baseUrl2);
		String expectedTitle1 = "Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!";
		String actualTitle1 = driver.getTitle();
		Assert.assertEquals(actualTitle1, expectedTitle1);
	}

	@Test(priority = 5)
	public void searchProductFlipkart() {
		WebElement popup_Closebtn = driver.findElement(By.xpath("//button[@class='_2KpZ6l _2doB4z']"));
		popup_Closebtn.click();
		WebElement searchinput = driver.findElement(By.xpath("//input[@class='_3704LK']"));
		searchinput.sendKeys(product);
		searchinput.sendKeys(Keys.ENTER);

	}

	@Test(priority = 6)
	public void flipkartCost() {
		List<WebElement> searchlist = driver.findElements(By.xpath("//div[@class='_4rR01T']"));
		for (int i = 0; i < searchlist.size(); i++) {

			if (searchlist.get(i).getText().contains(product)) {
				searchlist.get(i).click();
				break;
			}
		}
		Set<String> ids = driver.getWindowHandles();
		Iterator<String> it1 = ids.iterator();
		String parent1 = it1.next();
		String child1 = it1.next();
		String child2 = it1.next();
		driver.switchTo().window(child2);
		WebElement a_Price = driver.findElement(By.xpath("//div[@class='_30jeq3 _16Jk6d']"));
		String FlipkartPrice1 = a_Price.getText();
		flipkartPrice = FlipkartPrice1.substring(1);
		System.out.println("Flipkart price for " + product + " is : " + flipkartPrice + " Only");

	}

	@Test(priority = 6)
	public void priceCompare() {

		String str1 = flipkartPrice.replaceAll(",", "");
		str1 = str1.substring(1);
		String str2 = amazonPrice.replaceAll(",", "");
		str2 = str2.substring(1);
		float Aprice = Float.parseFloat(str2);
		float Fprice = Float.parseFloat(str1);

		if (Aprice > Fprice) {
			System.out.println("Flipkart Price is lesser than Amazon's Price");
		} else {
			System.out.println("Amazon Price is lesser than Flipkart's Price");

		}

	}

	@AfterTest
	public void terminateBrowser() {
		driver.quit();

	}

}
