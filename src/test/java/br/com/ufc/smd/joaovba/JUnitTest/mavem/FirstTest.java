package br.com.ufc.smd.joaovba.JUnitTest.mavem;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testes com TodoMVC")
public class FirstTest {
	private static WebDriver driver;

	@BeforeAll
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\joaov\\Downloads\\chromedriver_win\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@ParameterizedTest(name = "Teste {index} - Deve ser adicionado a tarefa \"{0}\"")
	@CsvSource({ "Acordar", "Escovar os dentes", "Tomar banho", "Se vestir", "Ir pra faculdade" })
	@DisplayName("CT1 - Adição de tarefas")
	void addTask(String task) {
		driver.get("http://todomvc.com/examples/vanillajs/");
		WebElement todoInput = driver.findElement(By.cssSelector("input.new-todo"));
		todoInput.sendKeys(task + Keys.ENTER);

		WebElement body = driver.findElement(By.cssSelector("body"));
		String text = body.getText();

		Assertions.assertTrue(text.contains(task));
	}

	@ParameterizedTest(name = "Teste {index} - Deve ser completado a {arguments}º tarefa")
	@ValueSource(ints = { 1, 2 })
	@DisplayName("CT2 - Completar tarefas")
	void markSomeTask(int taskIndex) {
		WebElement toggleTask = driver
				.findElement(By.xpath("/html/body/section/section/ul/li[" + taskIndex + "]/div/input"));
		toggleTask.click();

		List<WebElement> completedElements = driver
				.findElements(By.cssSelector("body > section > section > ul > li.completed"));
		WebElement desiredElement = completedElements.get(taskIndex - 1);
		String className = desiredElement.getAttribute("class");
		Assertions.assertEquals("completed", className);
	}

	@ParameterizedTest(name = "Teste {index} - Deve remover a {arguments}º tarefa")
	@ValueSource(ints = { 3, 4 })
	@DisplayName("CT3 - Remover tarefas")
	void removeSomeTask(int taskIndex) {

		WebElement task = driver.findElement(By.xpath("/html/body/section/section/ul/li[" + taskIndex + "]"));
		String textTask = task.getText();

		Actions actions = new Actions(driver);
		actions.moveToElement(task).perform();

		WebElement btnRemoveTask = driver.findElement(
				By.cssSelector("body > section > section > ul > li:nth-child(" + taskIndex + ") > div > button"));
		btnRemoveTask.click();

		WebElement body = driver.findElement(By.cssSelector("body"));
		String text = body.getText();
		Assertions.assertFalse(text.contains(textTask));

	}

	@ParameterizedTest(name = "Teste {index} - Atualizar uma task")
	@CsvSource(value = { "1?Abrir os olhos", "3?Fazer um lanche" }, delimiter = '?')
	@DisplayName("CT4 - Atualizar uma tarefa")
	void z_updateSomeTask(int taskIndex, String newTask) {
		System.out.println(taskIndex + ", " + newTask);
		//TODO Implements the update case test
	}

	@AfterAll
	public static void end() {
		driver.quit();
	}

}
