package cn.ianzhang.automation;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SampleTest {
    AndroidDriver driver;
    @BeforeClass
    public void init() throws MalformedURLException{
        DesiredCapabilities capabilities =new DesiredCapabilities();
        capabilities.setCapability("platformName","Andorid");
        capabilities.setCapability("automationName","uiautomator2");
        capabilities.setCapability("platformVersion","7.1");
        capabilities.setCapability("deviceName","127.0.0.1:62001");
        capabilities.setCapability("app","/Users/helloworld/Downloads/app-debug-1.0.0.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }
    @Test
    public void  startApp() throws InterruptedException {

        //断言页面正确打开
        Assert.assertEquals(driver.getPageSource().contains("Hello Ian"),true);

        //点击下一页按钮
        driver.findElement(By.id("cn.ianzhang.android:id/button_first")).click();
        Thread.sleep(600);

        //提取资源文本并显示
        String str = driver.getPageSource().toString();
        System.out.println(str);

        //断言页面资源
        Assert.assertEquals(driver.getPageSource().contains("Second Page"),true);

        //返回上一页面
        driver.findElement(By.id("cn.ianzhang.android:id/button_second")).click();
        Thread.sleep(300);

    }

    @AfterClass
    public void quit(){
        driver.quit();
    }
}
