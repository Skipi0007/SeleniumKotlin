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
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class FirstTest {

    object elements {
        val mainMenu: String = "top-menu"
        val category: String = "category-grid"
        val toCart: String = "product-box-add-to-cart-button"
        val toCartProd: String = "add-to-cart-button-1"
        val notifCont: String = "bar-notification-container"
        val notify: String = "bar-notification"
        val product: String = "product-name"
        val productBox: String = "products-wrapper"
        val prodTitle: String = "product-title"
        val viewedProd: String = "block-recently-viewed-products"
        val price: String = "actual-price"
        val totalPrice: String = "value-summary"
        val customerPrice: String = "customerCurrency"
        val close: String = "close"
    }

    fun awaitor(driver:WebDriver = ChromeDriver(), selector: String){
        await().atMost(50, TimeUnit.SECONDS).until(driver.findElement(By.className(selector))::isDisplayed)
    }
    fun awaitWithText(driver:WebDriver = ChromeDriver(), selector: String){
        await().atMost(90, TimeUnit.SECONDS).until(driver.findElement(By.partialLinkText(selector))::isDisplayed)
    }

    fun clickByClassName ( selector: String ){
        ChromeDriver().findElement(By.className(selector)).click()
    }

    fun driver() : WebDriver {
        System.setProperty("webdriver.chrome.driver", """/Users/antonvinogradov/Documents/Repos/SeleniumKotlin/src/main/kotlin/drivers/chromedriver""")
        var driver: WebDriver = ChromeDriver()
        driver.manage().window().size = Dimension(1280, 720)
        return driver
    }

    var driver = driver()


    @Test
    fun firstTest() {
        val category: Array<String> = arrayOf("Computers", "Electronics", "Apparel")
        val subCatComputers: Array<String> = arrayOf("Desktops",  "Notebooks", "Software")
        val subCatElectronics: Array<String> = arrayOf("Camera & photo",  "Cell phones", "Others")
        val subCatApparel: Array<String> = arrayOf("Shoes",  "Clothing", "Accessories")
        val subCategory= arrayOf<Array<String>>(subCatComputers, subCatElectronics, subCatApparel)

        driver.get("https://demo.nopcommerce.com/")
        fun asserter (categoryName:Array<String>, assertValues:Array<Array<String>>){
            var mainCounter = 0
            while (mainCounter < categoryName.size){

                var element = driver.findElement(By.className(elements.mainMenu))
                element.findElement(By.partialLinkText(categoryName[mainCounter])).click()
                awaitor(driver, elements.category)

                element = driver.findElement(By.className(elements.category))
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
        val HTC:String = "HTC One Mini Blue"

        driver.get("https://demo.nopcommerce.com/cell-phones")
        awaitWithText(driver, HTC)
        var product = driver.findElement(By.partialLinkText(HTC))
        product = product.findElement(By.ByXPath("./../.."))
        product.findElement(By.className(elements.toCart)).click()
        awaitor(driver, elements.notifCont)

        driver.get("https://demo.nopcommerce.com/cart")
        awaitor(driver, elements.product)
        val cartProduct = driver.findElement(By.className(elements.product)).getText()
        Assert.assertEquals(cartProduct, "HTC One Mini Blue")
        System.out.println(cartProduct+" eq HTC One Mini Blue")
        driver.quit()
    }

    @Test
    fun thirdTest() {
        val PC:String = "Build your own computer"
        val errorsTexts: Array<String> = arrayOf("Please select RAM", "Please select HDD")

        driver.get("https://demo.nopcommerce.com/desktops")
        awaitor(driver, elements.productBox)
        var product = driver.findElement(By.partialLinkText(PC))
        product = product.findElement(By.ByXPath("./../.."))
        product.findElement(By.className(elements.toCart)).click()
        sleep(3000)
        driver.findElement(By.id(elements.toCartProd)).click()
        awaitor(driver, elements.notifCont)
        var notify = driver.findElement(By.className(elements.notify))
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
        val HTC:String = "HTC One Mini Blue"
        val secondHTC:String = "HTC One M8 Android L 5.0 Lollipop"
        var sumTotal:BigDecimal? = null

        fun preiceCollectror(selector: String, price:BigDecimal?): BigDecimal {
            var product = driver.findElement(By.partialLinkText(selector))
            product = product.findElement(By.ByXPath("./../.."))
            var productPrice = product.findElement(By.className(elements.price)).getText().split("$")
            var productPriceInt = productPrice[1].toBigDecimal()
            product.findElement(By.className(elements.toCart)).click()
            awaitor(driver, elements.notifCont)
            product = driver.findElement(By.className(elements.notify))
            product.findElement(By.className(elements.close)).click()

            if(price == null){
                return productPriceInt
            } else {
                return(price+productPriceInt)
            }
        }

        driver.get("https://demo.nopcommerce.com/cell-phones")
        awaitor(driver, elements.productBox)
        sumTotal=preiceCollectror(HTC, sumTotal)
        sumTotal=preiceCollectror(secondHTC, sumTotal)

        driver.get("https://demo.nopcommerce.com/cart")
        driver.findElement(By.partialLinkText(HTC)).isDisplayed
        driver.findElement(By.partialLinkText(secondHTC)).isDisplayed
        awaitor(driver, elements.totalPrice)
        var webSumm = driver.findElement(By.className(elements.totalPrice)).getText().split("$")
        var webSummInt = webSumm[1].toBigDecimal()
        Assert.assertEquals(webSummInt, sumTotal)
        System.out.println("$webSummInt eq $sumTotal")
        var dropDown:WebElement = driver.findElement(By.id(elements.customerPrice))
        dropDown.click()
        await().atMost(50, TimeUnit.SECONDS).until(dropDown.findElement(By.ByXPath("""./option[2]"""))::isDisplayed)

        dropDown.findElement(By.ByXPath("""./option[2]""")).click()

        sumTotal*=0.86.toBigDecimal()
        sumTotal=sumTotal.multiply(BigDecimal(1)).setScale(2, RoundingMode.HALF_UP)
        sleep(3000);

        webSumm = driver.findElement(By.className(elements.totalPrice)).getText().split("???")
        webSummInt = webSumm[1].toBigDecimal()
        Assert.assertEquals(webSummInt, sumTotal)
        System.out.println("$webSummInt eq $sumTotal")
        driver.quit()
    }

    @Test
    fun fifthTest() {
        val HTC:String = "HTC One Mini Blue"

        driver.get("https://demo.nopcommerce.com/cell-phones")
        driver.findElement(By.partialLinkText(HTC)).click()
        awaitor(driver, elements.prodTitle)
        driver.get("https://demo.nopcommerce.com/cell-phones")
        awaitor(driver, elements.viewedProd)
        val product = driver.findElement(By.className(elements.viewedProd))
        val viewedProduct = product.findElement(By.partialLinkText(HTC)).getText()
        Assert.assertEquals(viewedProduct, HTC)
        driver.quit()
    }

}

