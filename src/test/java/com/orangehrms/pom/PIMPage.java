package com.orangehrms.pom;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrms.generic.BaseClass;

public class PIMPage extends BaseClass {

	private static WebDriverWait wait;

	@FindBy(xpath = "//a//span[contains(.,'Dashboard')]")
	private WebElement dashBoardModule;

	@FindBy(xpath = "//div[contains(@class, 'sidepanel-body')]//li[.//a[contains(@href, 'viewPimModule')]]")
	private WebElement PIMModuleBtn;

	@FindBy(partialLinkText = "Add Employee")
	private WebElement addEmployeeLinkText;

	@FindBy(xpath = "//div[contains(@class,'orangehrm-header-container')]//button")
	private WebElement addBtn;

	@FindBy(xpath = "//div[contains(@class, 'employee-form')]//div[contains(@class, 'label')][./label[normalize-space() = 'Employee Full Name']]//following-sibling::div//input[@placeholder = 'First Name']")
	private WebElement firstNameFld;

	@FindBy(xpath = "//div[contains(@class, 'employee-form')]//div[contains(@class, 'label')][./label[normalize-space() = 'Employee Full Name']]//following-sibling::div//input[@placeholder = 'Middle Name']")
	private WebElement middleNameFld;

	@FindBy(xpath = "//div[contains(@class, 'employee-form')]//div[contains(@class, 'label')][./label[normalize-space() = 'Employee Full Name']]//following-sibling::div//input[@placeholder = 'Last Name']")
	private WebElement lastNameFld;

	@FindBy(xpath = "//button[normalize-space()='Save']")
	private WebElement saveBtn;

	@FindBy(xpath = "//div[contains(@class, 'loading-spinner')]")
	private WebElement loadingSpinner;

	@FindBy(xpath = "//div[contains(@class, 'employee-form')]//div[contains(@class, 'label')][./label[normalize-space() = 'Employee Id']]//following-sibling::div//input")
	private WebElement empIdFld;

	@FindBy(xpath = "//div[contains(@class, 'toast-container')]//p[contains(., 'Successfully Saved')]")
	private WebElement successDialogContainer;

	public PIMPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void clickOnPIMModule() {
		PIMModuleBtn.click();
	}

	public void clickOnDashboardModule() {
		dashBoardModule.click();
	}

	public void goToAddEmployee() {
		addEmployeeLinkText.click();
	}

	public void addNewEmployee(String firstName, String middleName, String lastName) {
		goToAddEmployee();
		firstNameFld.sendKeys(firstName);
		middleNameFld.sendKeys(middleName);
		lastNameFld.sendKeys(lastName);

		saveBtn.click();

		try {

			List<WebElement> empIdFldExistsError = driver.findElements(By.xpath(
					"//div[contains(@class, 'employee-form')]//div[contains(@class, 'label')][./label[normalize-space() = 'Employee Id']]//following-sibling::span[contains(., 'Employee Id already exists')]"));
			if (empIdFldExistsError.size() >= 1) {
				Random random = new Random();
				int id = 100000 + random.nextInt(900000);

				empIdFld.clear();
				empIdFld.sendKeys(String.valueOf(id));
				
				saveBtn.click();
			}
		} catch (NoSuchElementException e) {
			System.out.println("Employee Id already exists did not occur. Not required to update id");
		}

		try {
			waitForSpinnerToLoad();
		} catch (Exception e) {
		}

	}

	public void waitForSpinnerToLoad() {

		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.invisibilityOf(loadingSpinner));
	}

}
