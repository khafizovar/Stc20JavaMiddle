package part2.lesson15.db.pojo;

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
}
