 package testCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.SearchPage;


public class SearchEngine{

	public static WebDriver driver;
	public Properties prop;

	@Parameters("browser")
	@BeforeTest
	
	//****Method to Get BrowserName from XML File and Open it****
	
	public void setUp(String browser) throws IOException

	{

		if (browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","C://geckodriver.exe");
			driver = new FirefoxDriver();
		} 
		else if (browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","C://chromedriver.exe");
			driver = new ChromeDriver();
		}
		else
		{
			throw new IllegalArgumentException("The Browser Type is Undefined");
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}



	@Parameters({ "searchText", "searchURL" })	
	@Test
	//****TestNG Test to Search the SearchText in SearchBox****
	public void SearchPageNavigation(String SearchText,String SearchURL) throws IOException
	{
	   try
	      {
		prop= new Properties();
		FileInputStream fis=new FileInputStream("C:\\Users\\Dell\\InstaworkAssignment\\src\\test\\java\\resources\\data.properties");
		prop.load(fis);

		driver.get(prop.getProperty("url"));
		SearchPage sp=new SearchPage(driver);
	        sp.valTitle("google");
		

		System.out.println("Navigated to Google Search Page");

		sp.setSearchText().sendKeys(SearchText);
		sp.setSearchText().submit();

		//****wait until the google page shows the result****
		
		WebElement myDynamicElement = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));


		System.out.println("Navigated to Search Result Page");


		List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));
		int urlPos=1;
		Boolean flag=false;

		//****Code to get list of All links on Search Result Page****
		for (WebElement webElement : findElements)
		{
			String resultUrl=webElement.getAttribute("href");
			if (resultUrl.equalsIgnoreCase(SearchURL))
			{
				flag=true;

				break;
			}
			urlPos=urlPos+1;
		}

		if(flag)
		{
			System.out.println("Searched URL found at Position: "+urlPos);
			System.out.println("Test Passed");
			Assert.assertTrue(true);
			
		}
		else
		{
			System.out.println("Searched URL not found");
			System.out.println("Test Failed");
			Assert.assertTrue(false);
			
		}
		}
		
		catch(Exception exception)
		{
			
			if(exception.toString().contains("NoSuchElementException"))
			{
				String[] errorMsg = exception.getMessage().split(":");
				System.out.println(errorMsg[0]);
				
			}
			
			else if(exception.toString().contains("FileNotFoundException"))
			{
				String[] errorMsg = exception.getMessage().split(":");
				System.out.println(errorMsg[1]);
				
			}
			
			else
			{
				String[] errorMsg = exception.getMessage().split(":");
				System.out.println(errorMsg[0]);
				
				
			}
			
		}
		
	}

	//****TestNG Test to perform the TearDown Activity****
	@AfterTest
	public void teardown()
	{

		driver.close();
		driver=null;
		
	}

	

}
