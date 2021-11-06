import org.awaitility.Awaitility.await
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.AfterAll
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
import java.lang.Thread.sleep
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class FirstTest {

    fun avaitor(driver:WebDriver = ChromeDriver(), selector: String){
        await().atMost(50, TimeUnit.SECONDS).until(driver.findElement(By.className(selector))::isDisplayed)
    }
    fun awaitWithText(driver:WebDriver = ChromeDriver(), selector: String){
        await().atMost(90, TimeUnit.SECONDS).until(driver.findElement(By.partialLinkText(selector))::isDisplayed)
    }


    @Test
    fun firstTest() {
        System.setProperty("webdriver.chrome.driver", """/Users/antonvinogradov/Documents/Repos/SeleniumKotlin/src/main/kotlin/drivers/chromedriver""")
        val driver: WebDriver = ChromeDriver()
        driver.manage().window().size = Dimension(1280, 720)
        val category: Array<String> = arrayOf("Computers", "Electronics", "Apparel")

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
                avaitor(driver, "category-grid")

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
        driver.quit()
    }

    @Test
    fun secondTest() {
        System.setProperty("webdriver.chrome.driver", """/Users/antonvinogradov/Documents/Repos/SeleniumKotlin/src/main/kotlin/drivers/chromedriver""")
        val driver: WebDriver = ChromeDriver()
        driver.manage().window().size = Dimension(1280, 720)
        val wait = WebDriverWait(driver, 5000)
        val HTC:String = "HTC One Mini Blue"

        driver.get("https://demo.nopcommerce.com/cell-phones")
        awaitWithText(driver, HTC)
        var product = driver.findElement(By.partialLinkText(HTC))
        product = product.findElement(By.ByXPath("./../.."))
        product.findElement(By.className("product-box-add-to-cart-button")).click()
        avaitor(driver, "bar-notification-container")

        driver.get("https://demo.nopcommerce.com/cart")
        avaitor(driver, "product-name")
        val cartProduct = driver.findElement(By.className("product-name")).getText()
        Assert.assertEquals(cartProduct, "HTC One Mini Blue")
        System.out.println(cartProduct+" eq HTC One Mini Blue")
        driver.quit()
    }

    @Test
    fun thirdTest() {
        System.setProperty("webdriver.chrome.driver", """/Users/antonvinogradov/Documents/Repos/SeleniumKotlin/src/main/kotlin/drivers/chromedriver""")
        val driver: WebDriver = ChromeDriver()
        driver.manage().window().size = Dimension(1280, 720)
        val wait = WebDriverWait(driver, 5000)
        val PC:String = "Build your own computer"
        val errorsTexts: Array<String> = arrayOf("Please select RAM", "Please select HDD")

        driver.get("https://demo.nopcommerce.com/desktops")
        avaitor(driver, "products-wrapper")
        var product = driver.findElement(By.partialLinkText(PC))
        product = product.findElement(By.ByXPath("./../.."))
        product.findElement(By.className("product-box-add-to-cart-button")).click()
        Thread.sleep(5000);
        driver.findElement(By.id("add-to-cart-button-1")).click()
        avaitor(driver, "bar-notification-container")
        var notify = driver.findElement(By.className("bar-notification"))
        val firstTextError = notify.findElement(By.ByXPath(".//p[1]")).getText()
        val secondTextError = notify.findElement(By.ByXPath(".//p[2]")).getText()
        Assert.assertEquals(firstTextError, errorsTexts[0])
        Assert.assertEquals(secondTextError, errorsTexts[1])
        System.out.println("${firstTextError} eq ${errorsTexts[0]}")
        System.out.println("${secondTextError} eq ${errorsTexts[1]}")
        driver.quit()
    }

    @Test
    fun fourthTest() {
        System.setProperty("webdriver.chrome.driver", """/Users/antonvinogradov/Documents/Repos/SeleniumKotlin/src/main/kotlin/drivers/chromedriver""")
        val driver: WebDriver = ChromeDriver()
        driver.manage().window().size = Dimension(1280, 720)
        val wait = WebDriverWait(driver, 5000)
        val HTC:String = "HTC One Mini Blue"
        val secondHTC:String = "HTC One M8 Android L 5.0 Lollipop"
        var sumTotal:BigDecimal? = null

        fun preiceCollectror(selector: String, price:BigDecimal?): BigDecimal {
            var product = driver.findElement(By.partialLinkText(selector))
            product = product.findElement(By.ByXPath("./../.."))
            var productPrice = product.findElement(By.className("actual-price")).getText().split("$")
            var productPriceInt = productPrice[1].toBigDecimal()
            product.findElement(By.className("product-box-add-to-cart-button")).click()
            avaitor(driver, "bar-notification-container")
            product = driver.findElement(By.className("bar-notification"))
            product.findElement(By.className("close")).click()

            if(price == null){
                return productPriceInt
            } else {
                return(price+productPriceInt)
            }
        }

        driver.get("https://demo.nopcommerce.com/cell-phones")
        avaitor(driver, "products-wrapper")
        sumTotal=preiceCollectror(HTC, sumTotal)
        sumTotal=preiceCollectror(secondHTC, sumTotal)

        driver.get("https://demo.nopcommerce.com/cart")
        driver.findElement(By.partialLinkText(HTC)).isDisplayed
        driver.findElement(By.partialLinkText(secondHTC)).isDisplayed
        avaitor(driver, "value-summary")
        var webSumm = driver.findElement(By.className("value-summary")).getText().split("$")
        var webSummInt = webSumm[1].toBigDecimal()
        Assert.assertEquals(webSummInt, sumTotal)
        System.out.println("$webSummInt eq $sumTotal")
        var dropDown:WebElement = driver.findElement(By.id("customerCurrency"))
        dropDown.click()
        await().atMost(50, TimeUnit.SECONDS).until(dropDown.findElement(By.ByXPath("""./option[2]"""))::isDisplayed)

        dropDown.findElement(By.ByXPath("""./option[2]""")).click()

        sumTotal*=0.86.toBigDecimal()
        sumTotal=sumTotal.multiply(BigDecimal(1)).setScale(2, RoundingMode.HALF_UP)
        sleep(3000);

        webSumm = driver.findElement(By.className("value-summary")).getText().split("€")
        webSummInt = webSumm[1].toBigDecimal()
        Assert.assertEquals(webSummInt, sumTotal)
        System.out.println("$webSummInt eq $sumTotal")
        driver.quit()
    }

    @Test
    fun fifthTest() {

        System.setProperty("webdriver.chrome.driver", """/Users/antonvinogradov/Documents/Repos/SeleniumKotlin/src/main/kotlin/drivers/chromedriver""")
        val driver: WebDriver = ChromeDriver()
        driver.manage().window().size = Dimension(1280, 720)
        val wait = WebDriverWait(driver, 5000)
        val HTC:String = "HTC One Mini Blue"

        driver.get("https://demo.nopcommerce.com/cell-phones")
        driver.findElement(By.partialLinkText(HTC)).click()
        avaitor(driver, "product-title")
        driver.get("https://demo.nopcommerce.com/cell-phones")
        avaitor(driver, "block-recently-viewed-products")
        val product = driver.findElement(By.className("block-recently-viewed-products"))
        val viewedProduct = product.findElement(By.partialLinkText(HTC)).getText()
        Assert.assertEquals(viewedProduct, HTC)
        driver.quit()
    }

}

