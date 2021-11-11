package selectors

import org.openqa.selenium.By

class ComputersPageSelectorProvider: MainPageSelectorProvider() {
    val desktopsIcon: By = By.cssSelector("a[href='/desktops']")
}