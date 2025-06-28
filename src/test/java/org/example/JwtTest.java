package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class JwtTest extends report{
    private WebDriver driver;
    private commonMethods common;
    private JsonLocatorReader locators;
    @BeforeClass
    public void setup() {
        driver = Base.initializeDriver();
        common = new commonMethods(driver);
    }

    @Test(description = "Verify JWT invalidSignature and Payload Value")
    public void verifyJwtTokenInvalid_Signature() throws InterruptedException {
        String url = ConfigReader.get("url");
        String token = ConfigReader.get("token");
        String secret = ConfigReader.get("secret");
        driver.get(url);
        String textJwtTokenXpath = JsonLocatorReader.locatorName("TextJwttoken");
        common.click(By.xpath(textJwtTokenXpath));
        Thread.sleep(2000);
        common.clear(By.xpath(textJwtTokenXpath));
        Thread.sleep(2000);
        common.typeText(By.xpath(textJwtTokenXpath),token);


//        String DecodedPayload = JsonLocatorReader.locatorName("DecodedPayload");
//        common.scrollToElementAndClick(By.xpath(DecodedPayload));
//        Thread.sleep(2000);

        String Decodevalue = JsonLocatorReader.locatorName("Decodedvalue");
        String decodedValue = common.getElementText(By.xpath(Decodevalue));
        System.out.println("Decoded 'c' value is: " + decodedValue);

        String decodedText = common.getElementText(By.xpath(Decodevalue));

        Pattern pattern = Pattern.compile("\"c\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(decodedText);

        if (matcher.find()) {
            int cValue = Integer.parseInt(matcher.group(1));
            System.out.println("Extracted c value: " + cValue);
            Assert.assertEquals(cValue, 3, "Value of 'c' is not 3!");
        } else {
            Assert.fail("Could not find value of 'c' in the payload");
        }

        String invalidSignatureXpath = JsonLocatorReader.locatorName("Invalid_Signature");
        String actualErrorMessage = common.getElementText(By.xpath(invalidSignatureXpath));
        System.out.println("Error message displayed: " + actualErrorMessage);
        Assert.assertTrue(actualErrorMessage.contains("Invalid Signature"), "Expected 'Invalid Signature' message but found: " + actualErrorMessage);



    }



    @Test(description = "Verify JWT Signature and Payload Value")
    public void verifyJwtTokenSignature() throws InterruptedException {
        String url = ConfigReader.get("url");
        String token = ConfigReader.get("token");
        String secret = ConfigReader.get("secret");
        driver.get(url);
        String textJwtTokenXpath = JsonLocatorReader.locatorName("TextJwttoken");
        common.click(By.xpath(textJwtTokenXpath));
        Thread.sleep(2000);
        common.clear(By.xpath(textJwtTokenXpath));
        Thread.sleep(2000);
        common.typeText(By.xpath(textJwtTokenXpath),secret);


//        String DecodedPayload = JsonLocatorReader.locatorName("DecodedPayload");
//        common.scrollToElementAndClick(By.xpath(DecodedPayload));
//        Thread.sleep(2000);


        String invalidSignatureXpath = JsonLocatorReader.locatorName("valid_Signature");
        String actualMessage = common.getElementText(By.xpath(invalidSignatureXpath));
        System.out.println("Error message displayed: " + actualMessage);


        Assert.assertTrue(actualMessage.contains("Invalid Signature") || actualMessage.contains("Please address JWT issues"),
                "Expected error message not found! Actual message: " + actualMessage);


        String payloadXpath = JsonLocatorReader.locatorName("DecodedPayloadEmpty");
        String payloadText = common.getElementText(By.xpath(payloadXpath));


        assert !payloadText.isEmpty() : "Decoded value is empty";




    }

    @AfterClass
    public void quitDriver() {
       driver.quit();
    }
}
