package satori.rbac;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.EnumSet;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

public class AuthorizationManagerTest {

	AuthorizationManager test;
	User user;
	User admin;
	User projectAdmin;
	Project project;

	Role baseRole;
	Role advancedRole;
	Role adminRole;
	Role projectAdminRole;
	Role superHeroRole;

	@Before
	public void setup() throws Exception {
		test = new AuthorizationManager();

		baseRole = new Role("BASE_ROLE");
		baseRole.setPermissions(Sets.newHashSet(Permission.UserRead));

		advancedRole = new Role("ADVANCED_ROLE");
		advancedRole.setPermissions(Sets.newHashSet(Permission.ProjectRead));

		adminRole = new Role("ADMIN_ROLE");
		adminRole.setPermissions(EnumSet.allOf(Permission.class));

		projectAdminRole = new Role("PROJECT_ADMIN");
		projectAdminRole.setPermissions(EnumSet.of(Permission.ProjectCreate, Permission.ProjectRead, Permission.ProjectUpdate, Permission.ProjectDelete, Permission.ProjectRoleAssign, Permission.UserRead, Permission.UserRoleAssign));

		superHeroRole = new Role("SUPER_HERO");

		// all users have BASE_USER role by default
		user = new User("username");
		user.getRoles().add(baseRole);

		admin = new User("admin");
		admin.getRoles().add(adminRole);

		project = new Project("project");
		projectAdmin = new User("project admin");
		project.addRoleForUser(projectAdmin, projectAdminRole);
	}

	@Test
	public void isAuthorized_defaultRoles_true() throws Exception {
		assertThat(test.isAuthorized(user, Permission.UserRead), is(true));
	}

	@Test
	public void isAuthorized_defaultRoles_false() throws Exception {
		assertThat(test.isAuthorized(user, Permission.UserCreate), is(false));
		assertThat(test.isAuthorized(user, Permission.UserUpdate), is(false));
		assertThat(test.isAuthorized(user, Permission.UserDelete), is(false));
		assertThat(test.isAuthorized(user, Permission.UserRoleAssign), is(false));
		assertThat(test.isAuthorized(user, Permission.ProjectCreate), is(false));
		assertThat(test.isAuthorized(user, Permission.ProjectRead), is(false));
		assertThat(test.isAuthorized(user, Permission.ProjectUpdate), is(false));
		assertThat(test.isAuthorized(user, Permission.ProjectDelete), is(false));
		assertThat(test.isAuthorized(user, Permission.ProjectRoleAssign), is(false));
	}

	@Test
	public void isAuthorized_true() throws Exception {
		user.getRoles().add(advancedRole);

		assertThat(test.isAuthorized(user, Permission.ProjectRead), is(true));
	}

	@Test
	public void isAuthorized_false() throws Exception {
		user.getRoles().add(advancedRole);

		assertThat(test.isAuthorized(user, Permission.ProjectCreate), is(false));
	}

	@Test
	public void isAuthorized_multipleRoles_true() throws Exception {
		assertThat(test.isAuthorized(user, Permission.UserRead, Permission.ProjectRead), is(true));
	}

	@Test
	public void isAuthorized_multipleRoles_false() throws Exception {
		assertThat(test.isAuthorized(user, Permission.UserUpdate, Permission.UserUpdate), is(false));
	}

	@Test
	public void isAuthorized_project_true() throws Exception {
		assertThat(test.isAuthorized(projectAdmin, project, Permission.ProjectUpdate), is(true));
	}

	@Test
	public void isAuthorized_project_false() throws Exception {
		assertThat(test.isAuthorized(projectAdmin, project, Permission.UserCreate), is(false));
	}

	@Test
	public void isAuthorized_project_true_userRole() throws Exception {
		assertThat(test.isAuthorized(admin, project, Permission.ProjectUpdate), is(true));
	}
}
