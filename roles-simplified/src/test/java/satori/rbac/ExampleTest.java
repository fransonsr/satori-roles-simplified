package satori.rbac;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExampleTest {

	private Example example;
	private AuthorizationManager authorizationManager;
	private User user;
	private User adminUser;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() throws Exception {
		example = new Example();
		authorizationManager = new AuthorizationManager();
		example.setAuthorizationManager(authorizationManager);

		user = testUser("user");
		adminUser = testUser("admin");
		adminUser.getRoles().add(Example.ADMIN_ROLE);
	}

	private User testUser(String username) {
		User user = new User(username);
		user.getRoles().add(Example.BASE_ROLE);
		return user;
	}

	@Test
	public void assignRole_true() throws Exception {
		example.assignRole(user, Example.SUPER_HERO_ROLE, adminUser);

		assertThat(user.getRoles(), containsInAnyOrder(Example.BASE_ROLE, Example.SUPER_HERO_ROLE));
	}

	@Test
	public void assignRole_notAuthorized() throws Exception {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Unauthorized");

		example.assignRole(user, Example.SUPER_HERO_ROLE, user);
	}
}
