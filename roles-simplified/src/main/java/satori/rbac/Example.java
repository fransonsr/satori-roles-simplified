package satori.rbac;

import java.util.EnumSet;

public class Example {

	public static final Role BASE_ROLE = new Role("BASE_ROLE");
	public static final Role ADVANCED_ROLE = new Role("ADVANCED_ROLE");
	public static final Role ADMIN_ROLE = new Role("ADMIN_ROLE");
	public static final Role PROJECT_ADMIN_ROLE = new Role("PROJECT_ADMIN_ROLE");
	public static final Role SUPER_HERO_ROLE = new Role("SUPER_HERO_ROLE");
	static {
		BASE_ROLE.setPermissions(EnumSet.of(Permission.UserRead));
		ADVANCED_ROLE.setPermissions(EnumSet.of(Permission.UserRead, Permission.ProjectRead));
		ADMIN_ROLE.setPermissions(EnumSet.allOf(Permission.class));
		PROJECT_ADMIN_ROLE.setPermissions(EnumSet.of(Permission.ProjectCreate, Permission.ProjectRead, Permission.ProjectUpdate, Permission.ProjectDelete, Permission.ProjectRoleAssign));
	}

	private AuthorizationManager authorizationManager;

	public AuthorizationManager getAuthorizationManager() {
		return authorizationManager;
	}

	public void setAuthorizationManager(AuthorizationManager authorizationManager) {
		this.authorizationManager = authorizationManager;
	}

	/**
	 * Assign role to user under the authority of currentUser.
	 * @param user target user
	 * @param role role to assign
	 * @param currentUser current user
	 * @throws RuntimeException if currentUser is not authorized to assign a role
	 */
	public void assignRole(User user, Role role, User currentUser) {
		if (authorizationManager.isAuthorized(currentUser, Permission.UserRoleAssign)) {
			user.getRoles().add(role);
		}
		else {
			throw new RuntimeException("Unauthorized");
		}
	}

	/**
	 * Assign project role to user under the authority of currentUser.
	 * @param user target user
	 * @param project project
	 * @param role role to assign
	 * @param currentUser current user
	 * @throws RuntimeException if currentUser is not authorized to assign a role
	 */
	public void assignProjectRole(User user, Project project, Role role, User currentUser) {
		if (authorizationManager.isAuthorized(currentUser, project, Permission.ProjectRoleAssign)) {
			project.addRoleForUser(user, role);
		}
		else {
			throw new RuntimeException("Unauthorized");
		}
	}

}
