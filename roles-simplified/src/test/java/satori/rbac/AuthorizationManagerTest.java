package satori.rbac;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

public class AuthorizationManagerTest {

	AuthorizationManager test;
	User user;
	Project project;

	Role baseRole;
	Role advancedRole;
	Role adminRole;
	Role superHeroRole;


	@Before
	public void setup() throws Exception {
		test = new AuthorizationManager();

		baseRole = new Role("BASE_USER");
		advancedRole = new Role("ADVANCED_USER");
		adminRole = new Role("ADMIN_USER");
		superHeroRole = new Role("SUPER_HERO");

		// all users have BASE_USER role by default
		user = new User("username");
		user.getRoles().add(baseRole);

		project = new Project("project");
	}

	@Test
	public void isAuthorized_defaultRoles_true() throws Exception {
		assertThat(test.isAuthorized(user, baseRole), is(true));
	}

	@Test
	public void isAuthorized_defaultRoles_false() throws Exception {
		assertThat(test.isAuthorized(user, advancedRole), is(false));
		assertThat(test.isAuthorized(user, adminRole), is(false));
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

		assertThat(test.isAuthorized(user, adminRole), is(false));
	}

	@Test
	public void isAuthorized_multipleRoles_true() throws Exception {
		user.getRoles().add(superHeroRole);

		assertThat(test.isAuthorized(user, adminRole, superHeroRole), is(true));
	}

	@Test
	public void isAuthorized_multipleRoles_false() throws Exception {
		user.getRoles().add(superHeroRole);

		assertThat(test.isAuthorized(user, adminRole, advancedRole), is(false));
	}

	@Test
	public void isAuthorized_project_true() throws Exception {
		project.addRoleForUser(user, adminRole);

		assertThat(test.isAuthorized(user, project, adminRole), is(true));
	}

	@Test
	public void isAuthorized_project_false() throws Exception {
		assertThat(test.isAuthorized(user, project, adminRole), is(false));
	}

	@Test
	public void isAuthorized_project_true_userRole() throws Exception {
		user.getRoles().add(adminRole);

		assertThat(test.isAuthorized(user, project, adminRole), is(true));
	}
}
