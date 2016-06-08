package satori.rbac;

import java.util.HashSet;
import java.util.Set;

public class AuthorizationManager {

	/**
	 * Authorizes a User if it contains any of the specified roles.
	 * @param user user to authorize
	 * @param roles set of allowed Role
	 * @return true if user is authorized based on the set of allowed Role
	 */
	public boolean isAuthorized(User user, Role... roles) {
		Set<Role> actualRoles = user.getRoles();
		return isAuthorized(actualRoles, roles);
	}

	/**
	 * Authorizes a User if it contains any of the specified roles in the
	 * union of the set of user roles and project-level roles for the user.
	 * @param user user to authorize
	 * @param project project to authorize against
	 * @param roles set of allowed Role
	 * @return true if the user is authorized based on the set of allowed Role and
	 * any project roles the user may have
	 */
	public boolean isAuthorized(User user, Project project, Role... roles) {
		Set<Role> actualRoles = new HashSet<>();
		actualRoles.addAll(user.getRoles());
		actualRoles.addAll(project.rolesForUser(user));

		return isAuthorized(actualRoles, roles);
	}

	private boolean isAuthorized(Set<Role> actualRoles, Role[] roles) {
		for (Role role : roles) {
			if (actualRoles.contains(role)) {
				return true;
			}
		}

		return false;
	}

}
