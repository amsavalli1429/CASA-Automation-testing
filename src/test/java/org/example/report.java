package org.example;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.*;
import org.testng.Assert;

import java.lang.reflect.Method;

public class report {

    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void beforeSuite() throws Exception {
        String reportsDir = "src/test/resources/reports/";
        java.nio.file.Files.createDirectories(java.nio.file.Paths.get(reportsDir));
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String reportPath = reportsDir + "ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void setup(Method method) {
        test = extent.createTest(method.getName());
    }

    @Test
    public void testPass() {
        test.info("Starting testPass");
        Assert.assertTrue(true);
        test.pass("Test passed");
    }



    @AfterMethod
    public void tearDown(org.testng.ITestResult result) {
        if (result.getStatus() == org.testng.ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == org.testng.ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else if (result.getStatus() == org.testng.ITestResult.SKIP) {
            test.skip("Test skipped");
        }
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("Flushing report");
        extent.flush();
    }
}
