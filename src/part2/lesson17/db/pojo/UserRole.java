package part2.lesson17.db.pojo;

/**
 * @author KhafizovAR on 03.12.2019.
 * @project Stc20JavaMiddle
 */
public class UserRole {
    private final Integer id;
    private final Integer userId;
    private final Integer roleId;


    public UserRole(Integer id, Integer userId, Integer roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole(Integer userId, Integer roleId) {
        this(-1, userId, roleId);
    }

    public Integer getId() {
        return id;
    }


    public Integer getUserId() {
        return userId;
    }


    public Integer getRoleId() {
        return roleId;
    }


    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (id != null ? !id.equals(userRole.id) : userRole.id != null) return false;
        if (!userId.equals(userRole.userId)) return false;
        return roleId.equals(userRole.roleId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + userId.hashCode();
        result = 31 * result + roleId.hashCode();
        return result;
    }
}
