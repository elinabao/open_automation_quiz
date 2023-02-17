package cn.cucumber.runner;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;

public class TestTestng{

    public WebDriver driver;
    public String baseUrl ="https://www.ianzhang.cn/bing/";
    public String pageIndex = "2";

    /*Create a webDriver for Chrome*/
    @BeforeClass
    public void connectBrowser()
    {
        driver = new ChromeDriver();
        driver.get(baseUrl);

        //dismiss the alert box
        driver.switchTo().alert().accept();
    }

    /*Search key word from Homepage*/
    @Test
    public void test1() throws Exception{
        String keyWord ="包娜";
        //Homepage
        driver.switchTo().frame(returnIframe(driver));

        try{
            Thread.sleep(3000);
            WebElement submitButton = driver.findElement(By.xpath("//*[@id='search_icon']"));
            WebElement input = driver.findElement(By.xpath("//*[@id='sb_form_q'][@class='sb_form_q']"));
            input.sendKeys(keyWord);
            submitButton.click();
        }catch (org.openqa.selenium.NoSuchElementException ex){
            System.out.println("失败：首页加载失败");
        }

        //Click subpage in Search result page
        try{
            //Navigate to iframe
            Thread.sleep(5000);
            driver.switchTo().frame(returnIframe(driver));
			/* String js = "window.scrollTo(0,document.body.scrollHeight)";
			((JavascriptExecutor) driver).executeAsyncScript(js);*/
            WebElement subPage=driver.findElement(By.xpath("//*[@class='b_pag']/nav/ul/li["+pageIndex+"]"));
            subPage.click();
        }catch (org.openqa.selenium.NoSuchElementException ex){
            System.out.println("失败：搜索结果页面未找到分页");
        }

        //Display the result
        try{
            Thread.sleep(5000);
            //Navigate to iframe
            driver.switchTo().frame( returnIframe(driver));
            List<WebElement> resultDiv = driver.findElements(By.xpath("//*[@id='b_results']/li[@class='b_algo']/div[1]/a"));
            if(resultDiv.size() !=0){
                //Display the sum of result
                showSearchResult(resultDiv);
            }

        }catch (org.openqa.selenium.NoSuchElementException ex){
            System.out.println("失败：遍历测试结果时出错了");
        }
    }

    /*Search key word from search result page*/
    @Test
    public void test2() throws Exception{
        String keyWord="Selenium";
        //Back to the previous page
        driver.navigate().back();
        driver.switchTo().frame(returnIframe(driver));

        //Find the search box
        try {
            Thread.sleep(3000);

            WebElement input = driver.findElement(By.xpath("//*[@class ='b_searchbox'][@id='sb_form_q']"));
            input.clear();
            input.sendKeys(keyWord);

            WebElement submitButton = driver.findElement(By.xpath("//*[@class='b_searchboxSubmit']"));
            submitButton.click();
        }catch (org.openqa.selenium.NoSuchElementException ex){
            System.out.println("失败：搜索页面加载失败");
        }

        //Click subpage in Search result page
        try{
            //Navigate to iframe
            Thread.sleep(5000);
            driver.switchTo().frame(returnIframe(driver));
			/* String js = "window.scrollTo(0,document.body.scrollHeight)";
			((JavascriptExecutor) driver).executeAsyncScript(js);*/
            WebElement subPage=driver.findElement(By.xpath("//*[@class='b_pag']/nav/ul/li["+pageIndex+"]"));
            subPage.click();
        }catch (org.openqa.selenium.NoSuchElementException ex){
            System.out.println("失败：搜索结果页面未找到分页");
        }

        //Display the result
        try{
            Thread.sleep(5000);
            //Navigate to iframe
            driver.switchTo().frame( returnIframe(driver));
            List<WebElement> resultDiv = driver.findElements(By.xpath("//*[@id='b_results']/li[@class='b_algo']/div[1]/a"));
            if(resultDiv.size() !=0){
                //Display the sum of result
                showSearchResult(resultDiv);
            }

        }catch (org.openqa.selenium.NoSuchElementException ex){
            System.out.println("失败：遍历测试结果时出错了");
        }
    }
    @AfterClass
    public  void quitBrowser(){
        System.out.print("quitBrowser");
        driver.quit();
    }
    public void showSearchResult(List<WebElement> searchResult){

        //Display the result list
        Iterator iterator1 = searchResult.iterator();
        System.out.println("结果列表");
        while (iterator1.hasNext()){
            WebElement item = (WebElement) iterator1.next();
            String name = item.getText();
            String href =item.getAttribute("href");
            System.out.println(name+"\t--->"+href);
        }

        //Display the sum of website
        Map m1=new HashMap();
        for(WebElement echoE:searchResult){
            //获取网站名
            String [] urlList = echoE.getAttribute("href").split("/");
            String domainKey= urlList[2];

            //如果网站存在计数增加一个，不存在则创建
            if (m1.get(domainKey)==null){
                m1.put(domainKey,1);
            }else{
                m1.put(domainKey,(int)m1.get(domainKey)+1);
            }
        }

        System.out.println("结果统计");
        Set set1 = m1.keySet();
        Iterator iterator2 = set1.iterator();
        while (iterator2.hasNext()){
            String key = (String) iterator2.next();
            int value = (int)m1.get(key);
            System.out.println(key+"\t--->"+value);
        }
    }
    public static WebElement  returnIframe(WebDriver driver){
        driver.switchTo().defaultContent();
        WebElement iframe = driver.findElement(By.xpath("//*[@name='bing']"));
        return iframe;
    }
}
