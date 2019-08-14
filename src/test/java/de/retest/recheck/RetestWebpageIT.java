package de.retest.recheck;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class RetestWebpageIT {

	WebDriver driver;
	Recheck re;

	@BeforeEach
	void setup() {
		final ChromeOptions opts = new ChromeOptions();
		opts.addArguments( "--headless", "--no-sandbox", "--window-size=1200,800" );
		driver = new ChromeDriver( opts );
		re = new RecheckImpl();
	}

	@ParameterizedTest
	@ValueSource( strings = { "https://retest.de", "https://retest.de/devtesters", "https://retest.de/managers",
			"https://retest.de/product-overview", "https://retest.de/recheck-web-chrome-extension",
			"https://retest.de/recheck-open-source", "https://retest.de/rehub", "https://retest.de/review",
			"https://retest.de/request-demo", "https://retest.de/faqs", "https://retest.de/about-us",
			"https://retest.de/team", "https://retest.de/jobs", "https://retest.de/news",
			"https://retest.de/contact-us", "https://assets.retest.org/releases/review.html",
			"https://www.retest.de/feature-unbreakable-selenium", "http://garkbit.prod.cloud.retest.org/dashboard" } )
	void index( final String link ) throws Exception {
		re.startTest( "retest-de" );

		driver.get( link );
		Thread.sleep( 1000 );

		final String checkName = link.substring( link.lastIndexOf( "/" ) + 1 );
		re.check( driver, checkName.equals( "retest.de" ) ? "index" : checkName );

		re.capTest();
	}

	@AfterEach
	void tearDown() {
		driver.quit();

		// Produce the result file.
		re.cap();
	}

}
