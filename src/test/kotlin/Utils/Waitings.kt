package Utils

import org.awaitility.Awaitility
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit

class Waitings{
    fun waitUntilElementIsDisplayed(driver: WebDriver, selector: By){
        Awaitility.await().atMost(50, TimeUnit.SECONDS).until(driver.findElement(selector)::isDisplayed)
    }

    // you don't really need it, you can search element by text right in the selector definition using Xpath
    fun awaitWithText(driver: WebDriver, selector: By){
        Awaitility.await().atMost(90, TimeUnit.SECONDS).until(driver.findElement(selector)::isDisplayed)
    }
}