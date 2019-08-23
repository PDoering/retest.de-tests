package de.retest.recheck;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class RetestWebpageTest {

	static WebDriver driver;
	static Recheck re;

	@BeforeAll
	static void setup() {
		final var opts = new ChromeOptions() //
				.addArguments( // 
						"--headless", //
						"--no-sandbox", //
						"--window-size=1200,800" );
		driver = new ChromeDriver( opts );

		final var recheckOptions = RecheckOptions.builder() //
				.reportUploadEnabled( true ) //
				.build();
		re = new RecheckImpl( recheckOptions );
	}

	@ParameterizedTest
	@MethodSource( "de.retest.recheck.util.WebPageFactory#getLinks" )
	void allRetestPages( final String link ) throws Exception {
		re.startTest( link.substring( link.indexOf( "//" ) + 2 ).replaceAll( "[\\/\\-]", "." ) );

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
