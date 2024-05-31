package accounts.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accounts.AccountManager;

public abstract class AbstractAccountManager implements AccountManager {

	protected final Logger logger;

	protected AbstractAccountManager() {
		logger = LoggerFactory.getLogger(getClass());
		logger.info("Created {} account-manager", getInfo());
	}

	@Override
	public String getInfo() {
		String myClassName = getClass().getSimpleName();
		int ix = myClassName.indexOf("AccountManager");
		return ix == -1 ? "UNKNOWN" : myClassName.substring(0, ix).toUpperCase();
	}
}
