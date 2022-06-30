package info.stepanoff.trsis.samples;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tests {
    private static WebDriver driver;
    JavascriptExecutor js;

    @Before
    public void startTest(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\dimka\\Downloads\\chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        driver.get("https://localhost:8443/login");
        driver.manage().window().maximize();
        WebElement ent =  driver.findElement(By.id("details-button"));
        ent.click();
        WebElement go =  driver.findElement(By.id("proceed-link"));
        go.click();
    }

    @After
    public void exit(){
        driver.quit();
    }

    @Test
    public void testLogin() throws InterruptedException {
        WebElement log =  driver.findElement(By.id("login"));
        log.sendKeys("89033210681");
        WebElement pas =  driver.findElement(By.id("pass"));
        pas.sendKeys("36613661");
        WebElement Tab =  driver.findElement(By.id("logButton"));
        Tab.click();
        driver.get("https://localhost:8443/toAccount");
        WebElement tel = driver.findElement(By.id("telephone"));
        Assert.assertEquals(tel.getText(), "Телефон: 89033210681");
    }

    @Test
    public void testAddOrder() throws InterruptedException {
        WebElement log =  driver.findElement(By.id("login"));
        log.sendKeys("8999725008");
        WebElement pas =  driver.findElement(By.id("pass"));
        pas.sendKeys("36613661");
        WebElement Tab =  driver.findElement(By.id("logButton"));
        Tab.click();
        driver.get("https://localhost:8443/redirect/1");
        WebElement tel = driver.findElement(By.className("link-button"));
        tel.click();
        WebElement startPlace = driver.findElement(By.id("start_place"));
        startPlace.sendKeys("Архангельск");
        WebElement endPlace = driver.findElement(By.id("end_place"));
        endPlace.sendKeys("Санкт-Петербург");
        WebElement price = driver.findElement(By.id("price"));
        price.sendKeys("7000");
        WebElement dateFrom = driver.findElement(By.id("date_start"));
        dateFrom.sendKeys("2022-05-30T14:00");
        WebElement comment = driver.findElement(By.id("comment"));
        comment.sendKeys("Чистая машина");
        WebElement buttonAdd = driver.findElement(By.id("addButton"));
        buttonAdd.click();
        Thread.sleep(1000);
        driver.get("https://localhost:8443/clientAccount");
        WebElement from = driver.findElement(By.id("startPlace0"));
        Assert.assertEquals(from.getText(), "Откуда: Архангельск");
    }

    /*@Test
    public void test2Delete() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\dimka\\Downloads\\chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        driver.get("http://localhost:8080/schools");
        driver.manage().window().maximize();
        WebElement Str = driver.findElement(By.id("4"));
        Str.findElement(By.id("Del")).click();
        WebElement Tab =  driver.findElement(By.id("4"));
        Assert.assertEquals(Tab.getText(), "4 del");
        driver.quit();
    }

    @Test
    public void test3AddButton() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\dimka\\Downloads\\chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        driver.get("http://localhost:8080/schools");
        driver.manage().window().maximize();
        WebElement Add =  driver.findElement(By.id("inputname"));
        Add.sendKeys("abc");
        driver.findElement(By.id("Add")).click();
        WebElement Tab =  driver.findElement(By.id("4"));
        Assert.assertEquals(Tab.getText(), "4 abc");
        driver.quit();
    }*/
}
