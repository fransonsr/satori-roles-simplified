package satori.rbac;

import java.util.Set;

import com.google.common.collect.Sets;

public class AuthorizationManager {

	/**
	 * Authorizes a User if it has any of the specified permissions.
	 * @param user user to authorize
	 * @param permissions set of permissions required
	 * @return true if the user is authorized based on the set of required permissions
	 */
	public boolean isAuthorized(User user, Permission... permissions) {
		Set<Permission> intersection = Sets.intersection(user.getPermissions(), Sets.newHashSet(permissions));
		return !intersection.isEmpty();
	}

	/**
	 * Authorizes the user if it has any of the required permissions in the union of the set of
	 * user roles and project-level roles assigned to the user.
	 * @param user user to authorize
	 * @param project project to authorize against
	 * @param permissions set of required permissions
	 * @return true if the user is authorized based on the set of permissions required
	 */
	public boolean isAuthorized(User user, Project project, Permission... permissions) {
		Set<Permission> actualPermissions = Sets.newHashSet();
		actualPermissions.addAll(user.getPermissions());
		actualPermissions.addAll(project.permissionsForUser(user));

		Set<Permission> intersection = Sets.intersection(actualPermissions, Sets.newHashSet(permissions));
		return !intersection.isEmpty();
	}

}
