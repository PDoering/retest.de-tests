package de.retest.recheck.util;

import org.openqa.selenium.chrome.ChromeOptions;

import de.retest.recheck.RecheckOptions;

public class TestUtils {
	public static ChromeOptions getChromeOptions() {
		return new ChromeOptions() //
				.addArguments( // 
						"--headless", //
						"--no-sandbox", //
						"--window-size=1200,800" );
	}

	public static RecheckOptions getRecheckOptions() {
		return RecheckOptions.builder() //
				.build();
	}

	public static String getTestName( final String link ) {
		return link.substring( link.indexOf( "//" ) + 2 ).replaceAll( "[\\/\\-]", "." );
	}
}
