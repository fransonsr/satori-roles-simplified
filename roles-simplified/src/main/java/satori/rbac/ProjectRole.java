package satori.rbac;

public class ProjectRole {

	private final Project project;
	private final User user;
	private final Role role;

	public ProjectRole(Project project, User user, Role role) {
		this.project = project;
		this.user = user;
		this.role = role;
	}

	public Project getProject() {
		return project;
	}

	public User getUser() {
		return user;
	}

	public Role getRole() {
		return role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		ProjectRole other = (ProjectRole) obj;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectRole [project=" + project + ", user=" + user + ", role=" + role + "]";
	}

}
