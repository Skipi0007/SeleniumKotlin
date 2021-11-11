package selectors

import org.openqa.selenium.By

open class MainPageSelectorProvider {
    val searchButton: By = By.cssSelector("button.search-box-button")
    val searchInput: By = By.cssSelector("input.search-box-text")
}