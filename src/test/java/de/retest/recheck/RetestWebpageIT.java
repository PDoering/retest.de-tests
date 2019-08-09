package de.retest.recheck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/*
 * Simple recheck-web showcase for a Chrome-based integration test. See other *IT classes for more examples.
 */
class RetestWebpageIT {

	WebDriver driver;
	Recheck re;
	List<String> links;

	@BeforeEach
	void setup() {
		final ChromeOptions opts = new ChromeOptions();
		opts.addArguments( "--headless", "--no-sandbox", "--window-size=1200,800" );
		driver = new ChromeDriver( opts );

		try {
			links = Files.readAllLines( Paths.get(
					"/home/eo/IdeaProjects/retest.de-tests/src/test/java/de/retest/testutils/Webpage-all-links.txt" ) );
		} catch ( final IOException e ) {
			e.printStackTrace();
		}

		re = new RecheckImpl();
	}

	@Test
	void index() throws Exception {
		re.startTest( "retest-de" );

		for ( final String link : links ) {

			driver.get( link );
			Thread.sleep( 1000 );

			final String checkName = link.substring( link.lastIndexOf( "/" ) + 1 );

			re.check( driver, checkName.equals( "retest.de" ) ? "index" : checkName );
		}

		re.capTest();

	}

	@AfterEach
	void tearDown() {
		driver.quit();

		// Produce the result file.
		re.cap();
	}

}
