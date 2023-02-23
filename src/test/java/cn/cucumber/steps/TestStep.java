package cn.cucumber.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class TestStep {
    static WebDriver driver=null;
    static {
        driver = new ChromeDriver();
    }

    @Given("进入页面")
    public void openSite() throws InterruptedException {
        driver.get("https://templates.jinshuju.net/detail/Dv9JPD");
        driver.manage().window().maximize();
        Thread.sleep(3000);
    }

    @When("选中 连续生产\\/开工类企事业单 代码（{string}）截图并提交")
    public void clickRadio(String string) throws InterruptedException {
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframe);

        WebElement item = driver.findElement(By.xpath("//input[@name='field_1' and @value='ffwi']"));
        if(!item.isSelected()){
            item.click();
        }
        Thread.sleep(500);
        //截图
        pageScreenShotAs(driver,"firstPage");
        //提交
        WebElement button = driver.findElement(
                By.xpath("//*[@class='published-form__footer-buttons']"));
        button.click();

    }
    @When("填写第二页表单截图并提交")
    public void 填写第二页表单截图并提交(Map<String,String> map) throws InterruptedException {
        driver.switchTo().defaultContent();
        try{
            Thread.sleep(2000);
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
        }catch (Exception e){
            System.out.println("失败：iframe未定位到");
        }

        String user = map.get("user");
        String telephone = map.get("telephone");
        //当前年份第一天
        Calendar currCal = Calendar.getInstance();
        int CurrentYear = currCal.get(Calendar.YEAR);

        try{
            //申请日期
            String js = "var ele = document.getElementsByClassName(\"ant-input\")[0];ele.readOnly=false;ele.value = \""+CurrentYear+"-1-1\"" ;
            ((JavascriptExecutor) driver).executeScript(js);
        }catch (Exception e) {
            System.out.println("失败：赋值失败");
        }
        //申请人
        WebElement userInput = driver.findElement(By.xpath("//*[@data-api-code='field_19']"))
                .findElement(By.tagName("input"));
        userInput.sendKeys(user);
        //联系电话
        driver.findElement(By.xpath("//div[@data-api-code='field_20']"))
                .findElement(By.tagName("input")).sendKeys(telephone);
        //截图
        pageScreenShotAs(driver,"secondPage");
        //点击下一步
        WebElement button = driver.findElement(
                By.xpath("//div[@class='published-form__footer center']/div/button[2]"));
        button.click();
    }
    @When("填写第三页表单截图并提交")
    public void 填写第三页表单截图并提交(Map<String,String> map) {
        driver.switchTo().defaultContent();
        try{
            Thread.sleep(2000);
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
        }catch (Exception e){
            System.out.println("失败：iframe");
        }

        String company = map.get("company");
        String number = map.get("number");
        String charge = map.get("charge");
        String telephone = map.get("telephone");
        String hubie = map.get("hubie");

        //报备单位
        driver.findElement(
                        By.xpath("//div[@data-api-code='field_23']"))
                .findElement(By.tagName("input")).sendKeys(company);
        //到岗人数
        driver.findElement(
                        By.xpath("//div[@data-api-code='field_24']"))
                .findElement(By.tagName("input")).sendKeys(number);
        try{
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String CurrentDate = f.format(date);

            //报备日期
            String js = "var ele = document.getElementsByClassName(\"ant-input\")[1];ele.readOnly=false;ele.value = \""+CurrentDate+"\"" ;
            ((JavascriptExecutor) driver).executeScript(js);

        }catch ( Exception e){
            System.out.println("失败：赋值报备日期失败");
        }

        //湖北返岗人数
        driver.findElement(
                        By.xpath("//div[@data-api-code='field_26']"))
                .findElement(By.tagName("input")).sendKeys(hubie);
        //单位负责人
        driver.findElement(
                        By.xpath("//div[@data-api-code='field_27']"))
                .findElement(By.tagName("input")).sendKeys(charge);
        //联系方式
        driver.findElement(
                        By.xpath("//div[@data-api-code='field_28']"))
                .findElement(By.tagName("input")).sendKeys(telephone);

        try{
            //防控方案
            String jsStr = "var kw = document.getElementsByTagName(\"textarea\")[0]; kw.setHTML(\"Test case\");";
            ((JavascriptExecutor) driver).executeScript(jsStr);
        }catch (Exception e){
            System.out.println("失败：防控方案失败");
        }


        //截图
        pageScreenShotAs(driver,"thirdPage");
        //提交
        WebElement button = driver.findElement(
                By.xpath("//div[@class='published-form__footer center']/div/button[2]"));
        button.click();

    }
    @Then("验证提交成功")
    public void 验证提交成功() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        WebElement iframe =  driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframe);
        //验证提交成功
        String message =driver.findElement(By.xpath("//div[@class='message']")).getText();
        Assert.assertEquals("提交成功",message);
    }

    @After()
    public  void quitBrowser(){
        driver.quit();
    }

    /*截图并保存*/
    public static void pageScreenShotAs(WebDriver driver,String imgName){

//        long date = System.currentTimeMillis();
//        String path = String.valueOf(date);
        String curPath = System.getProperty("user.dir");
        String path = imgName+".png";
        String screenPath = curPath+"/"+path;
        try{
            File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            org.apache.commons.io.FileUtils.copyFile(screen,new File(screenPath));
        }catch (Exception e){
            Assert.assertFalse(true,"截图失败");
        }
    }










}
