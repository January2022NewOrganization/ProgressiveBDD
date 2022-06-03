package base;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class HookClass {

	BaseClass baseClass;
	
	@Before
	public void setup() {
		baseClass = new BaseClass();
		baseClass.setUp();
	}
	
	@After
	public void closingBrowser() {
		baseClass.closingBrowser();
	}
}
