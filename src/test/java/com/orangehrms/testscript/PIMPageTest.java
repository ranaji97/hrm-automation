package com.orangehrms.testscript;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.orangehrms.generic.BaseClass;
import com.orangehrms.pom.EmployeeListPage;
import com.orangehrms.pom.LoginPage;
import com.orangehrms.pom.LogoutPage;
import com.orangehrms.pom.PIMPage;
import com.utility.EmployeeDetails;
import com.utility.ListnerImpl;

@Listeners(ListnerImpl.class)
public class PIMPageTest extends BaseClass {
	LoginPage loginPage;
	LogoutPage logoutPage;
	PIMPage pimPage;
	EmployeeListPage employeeListPage;

	@Test(priority = 1, description = "Add Employee")
	public void additionOfEmp() throws InterruptedException {
		pimPage = new PIMPage(driver);
		loginPage = new LoginPage(driver);
		logoutPage = new LogoutPage(driver);
		employeeListPage = new EmployeeListPage(driver);

		driver.get("https://opensource-demo.orangehrmlive.com");
		loginPage.doLogin("Admin", "admin123");
		pimPage.clickOnPIMModule();

		List<EmployeeDetails> employeeDetails = new ArrayList<>();

		employeeDetails.add(new EmployeeDetails("Michael", "Philip", "Miller"));
		employeeDetails.add(new EmployeeDetails("Tracy", "Tyler", "Evans"));
		employeeDetails.add(new EmployeeDetails("Munroe", "T", "Doer"));

		for (EmployeeDetails employeeDetail : employeeDetails) {
			pimPage.addNewEmployee(employeeDetail.getFirstName(), employeeDetail.getMiddleName(),
					employeeDetail.getLastName());
		}

		for (EmployeeDetails employeeDetail : employeeDetails) {
			employeeListPage.IsEmployeeAddedSuccessfullyInTheList(employeeDetail.getFirstName(),
					employeeDetail.getMiddleName(), employeeDetail.getLastName());
		}
		
		pimPage.clickOnDashboardModule();
		logoutPage.doLogout();
	}

}
