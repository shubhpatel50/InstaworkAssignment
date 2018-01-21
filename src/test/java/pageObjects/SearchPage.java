package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;



public class SearchPage {

	
	public WebDriver driver;
	
	By searchbox=By.name("q");
	
	//****Method to Initialize WebDriver Object****
	
	public SearchPage(WebDriver driver) {
	
		this.driver=driver;
		
	}


	//****Method to Enter SearchText into Search Box****
	
	public WebElement setSearchText()
	{
			 
			return driver.findElement(searchbox);
 	   	
	}

	//****Method to Enter SearchText into Search Box****
	
	public WebElement searchResultbtn()
	{
			 
			return driver.findElement(searchbox);
 	  		
	}

	//****Method to Validate Title of the Page****
	
	public String valTitle(String ExpTitle)
	{
		String ActTitle=driver.getTitle().toLowerCase();
		Assert.assertTrue(ActTitle.contains(ExpTitle));
		return ActTitle;
	}


	


	
	
}
