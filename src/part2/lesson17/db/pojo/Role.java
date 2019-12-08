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
}
