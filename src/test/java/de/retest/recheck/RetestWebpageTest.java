package de.retest.recheck;

import static de.retest.recheck.util.TestUtils.getChromeOptions;
import static de.retest.recheck.util.TestUtils.getTestName;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class RetestWebpageTest {

	static WebDriver driver;
	static Recheck re;

	@BeforeAll
	static void setup() {
		driver = new ChromeDriver( getChromeOptions() );

		re = new RecheckImpl();
	}

	@ParameterizedTest
	@MethodSource( "de.retest.recheck.util.WebPageFactory#getLinks" )
	void allRetestPages( final String link ) throws Exception {
		re.startTest( getTestName( link ) );

		driver.get( link );
		Thread.sleep( 1000 );

		re.check( driver, "check" );

		re.capTest();
	}

	@AfterAll
	static void tearDown() {
		driver.quit();

		re.cap();
	}

}
