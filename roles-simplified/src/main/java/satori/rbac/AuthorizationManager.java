package satori.rbac;

public class AuthorizationManager {

	/**
	 * Authorizes a User if it contains any of the specified roles.
	 * @param user user to authorize
	 * @param roles set of allowed Role
	 * @return true if user is authorized based on the set of allowed Role
	 */
	public boolean isAuthorized(User user, Role... roles) {
		for (Role role : roles) {
			if (user.getRoles().contains(role)) {
				return true;
			}
		}

		return false;
	}

}
