package com.orangehrms.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutPage {

	@FindBy(xpath = "//span[text()=\"Dashboard\"]")
	private WebElement dasBoardBtn;

	@FindBy(className = "oxd-userdropdown-img")
	private WebElement profileImage;

	@FindBy(xpath = "//a[text()='Logout']")
	private WebElement logoutLink;

	public LogoutPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void doLogout() {
		profileImage.click();
		logoutLink.click();
	}

}
