package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Base {
    public static WebDriver driver;

    // Absolute path for screenshots folder inside your project root
    private static final String SCREENSHOTS_DIR = System.getProperty("user.dir")
            + File.separator + "src" + File.separator + "test" + File.separator + "resources"
            + File.separator + "target" + File.separator + "screenshots" + File.separator;

    public static WebDriver initializeDriver() {
        String browser = ConfigReader.get("browser");
        if (browser == null) {
            throw new RuntimeException("Browser type not specified in config.");
        }
        browser = browser.toLowerCase();

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void takeScreenshot(WebDriver driver, String name) {
        try {
            // Create directory if not exists
            File dir = new File(SCREENSHOTS_DIR);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                System.out.println("Screenshots directory created: " + created);
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String safeName = name.replaceAll("[^a-zA-Z0-9]", "_");
            String filePath = SCREENSHOTS_DIR + safeName + "_" + timestamp + ".png";

            Files.copy(srcFile.toPath(), Paths.get(filePath));
            System.out.println("Screenshot saved: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanReportDirectory(String path) {
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                boolean deleted = file.delete();
                System.out.println("Deleted " + file.getName() + ": " + deleted);
            }
        }
    }
}
