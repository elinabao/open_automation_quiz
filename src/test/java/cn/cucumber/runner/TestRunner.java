package cn.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = {"src/test/resources/test.feature"},
        glue ={"cn.cucumber.steps"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
