package br.com.fiap.ProjetoMedicaoAutomatica.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "br.com.fiap.ProjetoMedicaoAutomatica.steps",
        tags = "@regressivo",
        plugin = {"html:target/cucumber-reports.html"}
)
public class TestRunner {
}