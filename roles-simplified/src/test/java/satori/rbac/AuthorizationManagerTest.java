package satori.rbac;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

public class AuthorizationManagerTest {

	AuthorizationManager test;
	User user;
	Role baseUserRole;
	Role advancedUserRole;
	Role adminUserRole;
	Role superHeroRole;


	@Before
	public void setup() throws Exception {
		test = new AuthorizationManager();

		baseUserRole = new Role("BASE_USER");
		advancedUserRole = new Role("ADVANCED_USER");
		adminUserRole = new Role("ADMIN_USER");
		superHeroRole = new Role("SUPER_HERO");

		// all users have BASE_USER role by default
		user = new User("username");
		user.getRoles().add(baseUserRole);
	}

	@Test
	public void isAuthorized_defaultRoles_true() throws Exception {
		assertThat(test.isAuthorized(user, baseUserRole), is(true));
	}

	@Test
	public void isAuthorized_defaultRoles_false() throws Exception {
		assertThat(test.isAuthorized(user, advancedUserRole), is(false));
		assertThat(test.isAuthorized(user, adminUserRole), is(false));
		assertThat(test.isAuthorized(user, superHeroRole), is(false));
	}

	@Test
	public void isAuthorized_true() throws Exception {
		user.getRoles().add(superHeroRole);

		assertThat(test.isAuthorized(user, superHeroRole), is(true));
	}

	@Test
	public void isAuthorized_false() throws Exception {
		user.getRoles().add(superHeroRole);

		assertThat(test.isAuthorized(user, adminUserRole), is(false));
	}

	@Test
	public void isAuthorized_multipleRoles_true() throws Exception {
		user.getRoles().add(superHeroRole);

		assertThat(test.isAuthorized(user, adminUserRole, superHeroRole), is(true));
	}

	@Test
	public void isAuthorized_multipleRoles_false() throws Exception {
		user.getRoles().add(superHeroRole);

		assertThat(test.isAuthorized(user, adminUserRole, advancedUserRole), is(false));
	}
}
