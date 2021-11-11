package Utils

import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

class Driver {

    val drivers = mutableListOf<WebDriver>()

    fun initializeDriver(): WebDriver {
        System.setProperty("webdriver.chrome.driver", """/Users/antonvinogradov/Documents/Repos/SeleniumKotlin/src/main/kotlin/drivers/chromedriver""")
        val driver: WebDriver = ChromeDriver()
        driver.manage().window().size = Dimension(1280, 720)
        drivers.add(driver)
        return driver
    }
}