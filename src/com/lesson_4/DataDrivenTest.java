package com.lesson_4;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;


@RunWith(value = Parameterized.class)
public class DataDrivenTest {
    WebDriver tutBy;

    String emailAddress;
    String password;
    String userName;

    public DataDrivenTest(String addr, String passwd, String name) {
        this.emailAddress = addr;
        this.password = passwd;
        this.userName = name;
    }

    @Parameters
    public static Collection testData() {
        return Arrays.asList(
                new Object[][]
                        {{"seleniumtests@tut.by", "123456789zxcvbn", "Selenium Test"},
                                {"seleniumtests2@tut.by", "123456789zxcvbn", "Selenium Test"}});
    }


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        tutBy = new ChromeDriver();
        tutBy.get("https://tut.by");
        tutBy.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws Exception {
        tutBy.close();
    }

    @Test
    public void login() {
        tutBy.findElement(By.cssSelector("a[data-target-popup='authorize-form']")).click();
        tutBy.findElement(By.name("login")).sendKeys(emailAddress);
        tutBy.findElement(By.name("password")).sendKeys(password);
        tutBy.findElement(By.className("auth__enter")).click();

        WebElement content = tutBy.findElement(By.className("uname"));

        // Explicit Wait
        WebDriverWait explicitWait = new WebDriverWait(tutBy, 10);
        explicitWait.pollingEvery(Duration.ofSeconds(2));
        explicitWait.until(ExpectedConditions.visibilityOf(content));
        assertEquals(content.getText(), this.userName);
    }
}