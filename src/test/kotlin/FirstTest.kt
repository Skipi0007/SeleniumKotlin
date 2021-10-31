import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class FirstTest {

    @Test
    fun firstTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\Git\\SeleniumGradle\\src\\main\\kotlin\\drivers\\chromedriver.exe")

        val driver: WebDriver = ChromeDriver()
        driver.manage().window().size = Dimension(1280, 720)
        val category: Array<String> = arrayOf("Computers", "Electronics", "Apparel")
//        , "Digital downloads", "Books", "Jewelry", "Gift Cards"

        val subCatComputers: Array<String> = arrayOf("Desktops",  "Notebooks", "Software")
        val subCatElectronics: Array<String> = arrayOf("Camera & photo",  "Cell phones", "Others")
        val subCatApparel: Array<String> = arrayOf("Shoes",  "Clothing", "Accessories")
        val subCategory= arrayOf<Array<String>>(subCatComputers, subCatElectronics, subCatApparel)
        val Selectors : Array<String> = arrayOf("top-menu", "category-grid")
        val wait = WebDriverWait(driver, 10)

        driver.get("https://demo.nopcommerce.com/")
        fun asserter (categoryName:Array<String>, assertValues:Array<Array<String>>){
            var mainCounter = 0
            while (mainCounter < categoryName.size){

                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("top-menu")))
                var element = driver.findElement(By.className("top-menu"))
                element.findElement(By.partialLinkText(categoryName[mainCounter])).click()
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("category-grid")))
                element = driver.findElement(By.className("category-grid"))
                var counter = 0

                    while (counter < assertValues[mainCounter].size) {
                        var assElement = element.findElement(By.partialLinkText(assertValues[mainCounter][counter])).getText()
                        Assert.assertEquals(assElement, assertValues[mainCounter][counter])
                        System.out.println(assElement+" eq "+assertValues[mainCounter][counter])
                        ++counter
                    }
                ++mainCounter
            }
        }
        asserter(category ,subCategory)
        driver.close()
    }

    @Test
    fun secondTest() {
        
    }

    }

