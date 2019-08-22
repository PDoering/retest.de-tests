// Here you can implement ignore rules for recheck in javascript.
// Please do not delete this file, even if it is empty.

// You can implement either of these two functions:
// function matches(element) {}
// function matches(element, diff) {}

// You can find more details and example rules at: 
// https://retest.github.io/docs/recheck/how-ignore-works-in-recheck/

function matches(element, diff) {
	// make clip: expected="rect(0px, 0px, 0px, 0px)", actual="rect(0px 0px 0px 0px)" equal
	if (diff && diff.key == "clip") {
		return diff.expected.replace(/,/g, '') === diff.actual.replace(/,/g, '');
	}
	return false;
}
