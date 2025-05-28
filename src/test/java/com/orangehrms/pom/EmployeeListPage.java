package com.orangehrms.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.orangehrms.generic.BaseClass;

public class EmployeeListPage extends BaseClass {

	PIMPage pimPage;

	public List<WebElement> getEmpAdded(String empName) {
		String xpath = String.format("//div[@class ='oxd-table-card'][.//div[contains(., '%s')]]", empName);
		return driver.findElements(By.xpath(xpath));
	}

	@FindBy(xpath = "//a[contains(text(),'Employee List')]")
	private WebElement allRowsOfEmployeeListTable;

	@FindBy(xpath = "//a[contains(text(),'Employee List')]")
	private WebElement empListBtn;

	@FindBy(xpath = "//nav//ul[contains(@class, 'pagination')]//button[.//i[contains(@class, 'right')]]")
	private WebElement paginationRight;

	public EmployeeListPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void goToEmployeeList() {
		empListBtn.click();
	}

	public void IsEmployeeAddedSuccessfullyInTheList(String firstName, String middleName, String lastName) {

		System.out.println("Verification for below : ");
		System.out.println(firstName + " " + middleName + " " + lastName);

		goToEmployeeList();

		List<WebElement> allMatchingResults = getEmpAdded(firstName);

		boolean paginationNext = false;

		do {
			if (allMatchingResults.size() == 0) {
				
				List<WebElement> paginationRight = driver.findElements(By.xpath("//nav//ul[contains(@class, 'pagination')]//button[.//i[contains(@class, 'right')]]"));
				if (paginationRight.size() >= 1) {
					paginationRight.get(0).click();

					pimPage = new PIMPage(driver);
					pimPage.waitForSpinnerToLoad();
					
					allMatchingResults = getEmpAdded(firstName);

					if (allMatchingResults.size() > 0) {
						break;
					}
					else {
						paginationRight = driver.findElements(By.xpath("//nav//ul[contains(@class, 'pagination')]//button[.//i[contains(@class, 'right')]]"));
						paginationNext = paginationRight.size() >= 1;
					}
				}
				else {
					paginationNext = false;
				}
			}
			else {
				paginationNext = false;
			}

		} while (paginationNext);

		if (!(allMatchingResults.size() == 0)) {

			for (WebElement tempResult : allMatchingResults) {

				JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
				javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", tempResult);

				String tempValue = tempResult.getAttribute("innerText");
				String[] tempNameWithId = tempValue.split("\\n");
				System.out.println("Temp Full Name : " + String.join(" ", tempNameWithId));

				String employeeId = tempNameWithId[0];
				String tempFirstName = tempNameWithId[1].split(" ")[0];
				String tempMiddleName = tempNameWithId[1].split(" ").length > 1 ? tempNameWithId[1].split(" ")[1] : "";
				String tempLastName = tempNameWithId[2];

				System.out.println("Id			: " + employeeId);
				System.out.println("First Name	: " + tempFirstName);
				System.out.println("Middle Name	: " + tempMiddleName);
				System.out.println("Last Name	: " + tempLastName);
				System.out.println("\n");

				if (firstName.equalsIgnoreCase(tempFirstName) && middleName.equalsIgnoreCase(tempMiddleName)
						&& lastName.equalsIgnoreCase(tempLastName)) {
					System.out.println("Name Verified");
					System.out.println("\n\n");
					return;
				}

			}

		} else {
			System.out.println("No rows found!");
		}

	}

}
