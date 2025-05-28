package com.utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.orangehrms.generic.BaseClass;

public class ListnerImpl extends BaseClass implements ITestListener {

	ExtentReports report;
	ExtentTest test;

	@Override
	public void onStart(ITestContext context) {
		// Configures the ExtentReports instance
		ExtentSparkReporter htmlReport = new ExtentSparkReporter("./Report/report.html");
		htmlReport.config().setDocumentTitle("Oragne-HRM-Test-Report");
		htmlReport.config().setTheme(Theme.DARK);
		htmlReport.config().setReportName("Oragne-HRM-Test-Report");
		// Initializes the ExtentReports instance
		report = new ExtentReports();
		// Attach thr report else it will not at all add any thing to the report
		report.attachReporter(htmlReport);
		report.setSystemInfo("Base-Browser", "chrome");
		report.setSystemInfo("Base-OS", "Windows");
		report.setSystemInfo("url", "https://www.orangehrm.com/");
		report.setSystemInfo("Reporter Name", "Raja Kumar");
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testMethodName = result.getMethod().getMethodName();
		test = report.createTest(testMethodName);
		Reporter.log("[ Test script execution starts from here. ]");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testMethodName = result.getMethod().getMethodName();
		test.log(Status.PASS, testMethodName + " PASSED");
		Reporter.log(testMethodName + " [ Test script executed successfully. ]");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testMethodName = result.getMethod().getMethodName();
		String name = result.getName();

		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			File dest = new File("./ScreenShot/" + name + ".png");
			FileUtils.copyFile(src, dest);
			test.log(Status.FAIL, "Test Failed. Screenshot saved: " + dest.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		test.log(Status.FAIL, testMethodName + " FAILED");
		test.log(Status.FAIL, result.getThrowable());
		Reporter.log(testMethodName + " [ Test script execution failed. ]");
	}

//	@Override
//	public void onTestSkipped(ITestResult result) {
//		String testMethodName = result.getMethod().getMethodName();
//		test.log(Status.SKIP, testMethodName + " SKIPPED");
//		test.log(Status.SKIP, result.getThrowable());
//		Reporter.log(testMethodName + " [ Test script execution got skipped. ]");
//	}

	@Override
	public void onFinish(ITestContext context) {
		// Flushes the ExtentReports instance, finalizing the report
		report.flush();
	}

}
