package satori.rbac;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class User {

	private String username;
	private Set<Role> roles = new HashSet<Role>();

	public User(String username) {
		this.username = username;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public Set<Permission> getPermissions() {
		return roles.stream()
			.flatMap(r -> r.getPermissions().stream())
			.collect(Collectors.toSet());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", roles=" + roles + "]";
	}


}
