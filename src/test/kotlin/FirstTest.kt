import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.WebDriverWait
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class FirstTest {

    @Test
    fun openWebPage() {
        System.setProperty("webdriver.chrome.driver", "C:\\Git\\SeleniumGradle\\src\\main\\kotlin\\drivers\\chromedriver.exe")

        val driver: WebDriver = ChromeDriver()
        driver.manage().window().size = Dimension(1280, 720)
        val category: Array<String> = arrayOf("Computers", "Electronics", "Apparel", "Digital downloads", "Books", "Jewelry", "Gift Cards")
        val subCatComputers: Array<String> = arrayOf("Desktops",  "Notebooks", " Software")
        val subCatElectronics: Array<String> = arrayOf("Camera & photo",  "Cell phones", "Others")
        data class Selectors (
            val menu: String = "top-menu",
            val subCategory: String = "sub-category-item"
            )

        driver.get("https://demo.nopcommerce.com/")
        WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.presenceOfElementLocated(By.id("pollanswers-1")))
        val searchBox = driver.findElement(By.id("pollanswers-1"))
        val element = driver.findElement(By.cssSelector("[class='top-menu']"))
        searchBox.click()
        driver.close()

//        element.findElement(By.partialLinkText("Computers")).click()
//        element.findElement(By.xpath("./li[1]")).click()
//        System.out.println("element" + element)
    }


    }

}