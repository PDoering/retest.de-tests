package de.retest.recheck;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class RetestWebpageTest {

	static WebDriver driver;
	static Recheck re;

	@BeforeAll
	static void setup() {
		final ChromeOptions opts = new ChromeOptions();
		opts.addArguments( "--headless", "--no-sandbox", "--window-size=1200,800" );
		driver = new ChromeDriver( opts );
		re = new RecheckImpl();

		System.setProperty( de.retest.recheck.Properties.REHUB_REPORT_UPLOAD_ENABLED, "true" );
		re = new RecheckImpl( RecheckOptions.builder().reportUploadEnabled( true ).build() );
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

	@AfterAll
	static void tearDown() {
		driver.quit();

		re.cap();
	}

}
