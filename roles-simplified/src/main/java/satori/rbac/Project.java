package satori.rbac;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Project {

	private String projectName;
	private Set<ProjectRole> projectRoles = new HashSet<ProjectRole>();


	public Project(String projectName) {
		this.projectName = projectName;
	}

	public void addRoleForUser(User user, Role role) {
		ProjectRole projectRole = new ProjectRole(this, user, role);
		projectRoles.add(projectRole);
	}

	public Set<Role> rolesForUser(User user) {
		return projectRoles
				.stream()
				.filter(p -> p.getUser().equals(user))
				.map(p -> p.getRole())
				.collect(Collectors.toSet());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
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
		Project other = (Project) obj;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [projectName=" + projectName + "]";
	}

}
