package FrontendTests;

import org.testng.annotations.Test;

import org.testng.Assert;

public class VerifyBalance extends BaseTest {

	private static final double getMemberBalance = 0.00;

	@Test(priority = 1, description = "Verifying Balance", groups = "BabibetUI")
	public void verifyBalanceBabibet() throws InterruptedException {

		acceptCookies();
		loginToBabibet();
		closePopup();
		Assert.assertEquals(getUserBalance(), getMemberBalance, 0.01, "The balances do not match");
	}
}
