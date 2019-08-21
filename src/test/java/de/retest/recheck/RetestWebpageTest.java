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
		final var chromeOpts = new ChromeOptions();
		chromeOpts.addArguments( "--headless", "--no-sandbox", "--window-size=1200,800" );
		driver = new ChromeDriver( chromeOpts );

		final var recheckOpts = RecheckOptions.builder() //
				.reportUploadEnabled( true ) //
				.build();
		re = new RecheckImpl( recheckOpts );
	}

	@ParameterizedTest
	@ValueSource( strings = { "https://retest.de", "https://retest.de/devtesters", "https://retest.de/managers",
			"https://retest.de/product-overview", "https://retest.de/recheck-web-chrome-extension",
			"https://retest.de/recheck-open-source", "https://retest.de/rehub", "https://retest.de/review",
			"https://retest.de/request-demo", "https://retest.de/faqs", "https://retest.de/about-us",
			"https://retest.de/team", "https://retest.de/jobs", "https://retest.de/news",
			"https://retest.de/contact-us", "https://assets.retest.org/releases/review.html",
			"https://www.retest.de/feature-unbreakable-selenium", "http://garkbit.prod.cloud.retest.org/dashboard" } )
	void test( final String link ) throws Exception {
		re.startTest( "retest-de" );

		driver.get( link );
		Thread.sleep( 1000 );

		re.check( driver, getCheckName( link ) );

		re.capTest();
	}

	String getCheckName( final String link ) {
		final String checkName = link.substring( link.lastIndexOf( "/" ) + 1 );
		return checkName.equals( "retest.de" ) ? "index" : checkName;
	}

	@AfterAll
	static void tearDown() {
		driver.quit();

		re.cap();
	}

}
