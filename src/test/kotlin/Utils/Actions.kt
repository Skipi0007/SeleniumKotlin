package Utils

import org.openqa.selenium.By

class Actions(
    private val driver: Driver
) {
    fun clickElement(element: By) {
        /**
         * TODO: replace with better key for storing initialized drivers.
         * TODO: For example if you have more than 1 driver, let's say you wanna make test where 2 different users involved,
         * TODO: then you won't be able to define which one is related to needed user
         */
        driver.drivers[1].findElement(element).click()
    }
}