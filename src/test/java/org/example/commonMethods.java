package org.example;

import org.openqa.selenium.*;

public class commonMethods {


    WebDriver driver;

    public commonMethods(WebDriver driver) {

        this.driver = driver;
    }


    public void typeText(By locator, String text) {
        driver.findElement(locator).sendKeys(text);
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }
    public void clear(By locator) {
        driver.findElement(locator).clear();
    }

    public void scrollToElementAndClick(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);  // Wait half a second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
    }


    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getElementText(By locator) {
        try {
            return driver.findElement(locator).getText();
        } catch (NoSuchElementException e) {
            return null; // or return "", depending on your preference
        }
    }


    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

}












