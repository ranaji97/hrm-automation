package com.orangehrms.testscript;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.orangehrms.generic.BaseClass;
import com.orangehrms.pom.LoginPage;
import com.orangehrms.pom.LogoutPage;
import com.utility.ListnerImpl;

@Listeners(ListnerImpl.class)
public class LoginPageTest extends BaseClass {

	LoginPage loginPage;
	LogoutPage logoutPage;

	@Test(priority = 1, description = "Login with invalid password")
	public void doFailedLogin() throws InterruptedException {
		loginPage = new LoginPage(driver);
		driver.get("https://opensource-demo.orangehrmlive.com");
		loginPage.doLogin("Admin", "wrongPassword");
		String expectedText = "Invalid credentials";
		String actualText = driver.findElement(By.className("oxd-alert-content-text")).getText();
		System.out.println(actualText + " and " + expectedText);
		Assert.assertTrue(actualText.equals("wrer"));

	}

	@Test(priority = 2, description = "Login with valid username and valid password")
	public void doLogin() throws InterruptedException {
		loginPage = new LoginPage(driver);
		driver.get("https://opensource-demo.orangehrmlive.com");
		loginPage.doLogin("Admin", "admin123");
		String actualUrl = driver.getCurrentUrl();
		String expectedUrl = "dashboard";
		System.out.println(actualUrl + " and " + expectedUrl);
		Assert.assertTrue(actualUrl.contains(expectedUrl));
	}

	@Test(priority = 3, description = "Logs Out")
	public void doLogout() {
		logoutPage = new LogoutPage(driver);
		logoutPage.doLogout();
	}

}
