package satori.rbac;

public class Example {

	public static final Role BASE_ROLE = new Role("BASE_ROLE");
	public static final Role ADVANCED_ROLE = new Role("ADVANCED_ROLE");
	public static final Role ADMIN_ROLE = new Role("ADMIN_ROLE");
	public static final Role SUPER_HERO_ROLE = new Role("SUPER_HERO_ROLE");

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
		if (authorizationManager.isAuthorized(currentUser, ADMIN_ROLE)) {
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
		if (authorizationManager.isAuthorized(currentUser, project, ADMIN_ROLE)) {
			project.addRoleForUser(user, role);
		}
		else {
			throw new RuntimeException("Unauthorized");
		}
	}

}
