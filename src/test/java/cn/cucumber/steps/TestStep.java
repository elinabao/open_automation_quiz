package cn.cucumber.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;


import java.io.File;
//import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
    public void clickRadio(String string) {
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframe);

        WebElement item = driver.findElement(By.xpath("//input[@name='field_1' and @value='ffwi']"));
        if(!item.isSelected()){
            item.click();
        }
        //截图
        // pageScreenShotAs(driver);
        //提交
        WebElement button = driver.findElement(
                By.xpath("//*[@class='published-form__footer-buttons']"));
        button.click();

    }
    @When("填写第二页表单截图并提交")
    public void 填写第二页表单截图并提交(Map<String,String> map) throws InterruptedException {
        //driver.switchTo().defaultContent();
        System.out.println("失败：iframe00000");
        try{
           // driver.switchTo().defaultContent();
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            //String src = iframe.getAttribute("class");
            driver.switchTo().frame(iframe);
            System.out.println("失败：iframe Src:"+iframe.getText());
        }catch (Exception e){
            System.out.println("失败：iframe");
        }

        String date = map.get("date");
        String user = map.get("user");
        String telephone = map.get("telephone");

        try{
            //申请日期
            driver.findElement(By.xpath("//div[@data-api-code='field_18']"))
                    .findElement(By.tagName("input")).sendKeys(date);
        }catch (Exception e){
            System.out.println("失败：赋值申请日期失败");
        }

            //申请人
            WebElement userInput = driver.findElement(By.xpath("//*[@data-api-code='field_19']"))
                    .findElement(By.tagName("input"));
            userInput.sendKeys(user);

        //联系电话
        driver.findElement(By.xpath("//div[@data-api-code='field_20']"))
                .findElement(By.tagName("input")).sendKeys(telephone);
        //截图

        //点击下一步
        //提交
        WebElement button = driver.findElement(
                By.xpath("//div[@class='published-form__footer center']/div/button[2]"));
        button.click();
    }
    @When("填写第三页表单截图并提交")
    public void 填写第三页表单截图并提交(Map<String,String> map) {
        driver.switchTo().defaultContent();
        System.out.println("失败：iframe00000");
        try{
            Thread.sleep(3000);

            WebElement iframe = driver.findElement(By.tagName("iframe"));
            //String src = iframe.getAttribute("class");
            driver.switchTo().frame(iframe);
           // System.out.println("失败：iframe Src:"+iframe.getText());
        }catch (Exception e){
            System.out.println("失败：iframe");
        }

        String company = map.get("company");
        String number = map.get("number");
        String charge = map.get("charge");
        String caseName = map.get("caseName");
        String telephone = map.get("telephone");
        String hubie = map.get("hubie");

        //获取当前日期
        SimpleDateFormat format = new SimpleDateFormat("YYY-M-dd");
        Date dataS = new Date(System.currentTimeMillis());
        String data = format.format(dataS);

        //报备单位
        driver.findElement(
                        By.xpath("//div[@data-api-code='field_23']"))
                .findElement(By.tagName("input")).sendKeys(company);
        //到岗人数
        driver.findElement(
                        By.xpath("//div[@data-api-code='field_24']"))
                .findElement(By.tagName("input")).sendKeys(number);
        try{
            //报备日期
            driver.findElement(
                            By.xpath("//div[@data-api-code='field_25']"))
                    .findElement(By.tagName("input")).sendKeys(data);
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
        //防控方案
        driver.findElement(
                        By.xpath("//div[@data-api-code='field_29]"))
                .findElement(By.tagName("textarea")).sendKeys(caseName);

        //截图
        //提交
        WebElement button = driver.findElement(
                By.xpath("//div[@class='published-form__footer center']/div/button[2]"));
        button.click();

    }
    @Then("验证提交成功")
    public void 验证提交成功() {
        // Write code here that turns the phrase above into concrete actions
        driver.switchTo().defaultContent();
        WebElement iframe = driver.findElement(By.xpath("//*[@title='template-preview']"));
        driver.switchTo().frame(iframe);
        String message =driver.findElement(By.xpath("//div[@class='message']")).getText();
        Assert.assertEquals("提交成功",message);
    }

    @After()
    public  void quitBrowser(){
        //driver.quit();
    }

    /*截图并保存*/
    public static void pageScreenShotAs(WebDriver driver,String imgName){
        File srcfile=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        /*try {
            FileUtils.copyFile(srcfile,new File("src/test/resources/"+imgName+".png"));
        }catch (IOException e){
            e.printStackTrace();
        }*/

    }










}
