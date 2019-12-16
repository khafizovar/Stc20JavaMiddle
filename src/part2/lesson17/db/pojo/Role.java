package part2.lesson17.db.pojo;

public class Role {
    private final Integer id;
    private final Roles name;
    private final String description;

    public enum Roles {
        ADMINISTRATION,
        CLIENTS,
        BILLING
    }


    public Role(Integer id, Roles name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Role(Roles name, String description) {
        this(-1, name, description);
    }

    public Integer getId() {
        return id;
    }

    public Roles getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != null ? !id.equals(role.id) : role.id != null) return false;
        if (name != role.name) return false;
        return description != null ? description.equals(role.description) : role.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
