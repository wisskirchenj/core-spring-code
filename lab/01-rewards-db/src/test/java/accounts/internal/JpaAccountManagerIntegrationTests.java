package accounts.internal;

import config.AppConfig;
import config.DbConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Spring-driven integration test for the JPA-based account manager
 * implementation. Verifies that the JpaAccountManager works with its underlying
 * components.
 */
@ActiveProfiles("jpa")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class, DbConfig.class })
public class JpaAccountManagerIntegrationTests extends AbstractDatabaseAccountManagerTests {

	@Test
	@Override
	public void testProfile() {
        assertEquals("JPA", accountManager.getInfo(), "JPA expected but found " + accountManager.getInfo());
	}

}
