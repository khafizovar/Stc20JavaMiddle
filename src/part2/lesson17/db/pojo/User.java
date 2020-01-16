package part2.lesson17.db.pojo;


import java.time.LocalDate;

public class User {
    private final Integer id;
    private final String name;
    private final LocalDate birthday;
    private final String loginId;
    private final String city;
    private final String email;
    private final String description;


    public User(Integer id, String name, LocalDate birthday, String loginId, String city, String email, String description) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.loginId = loginId;
        this.city = city;
        this.email = email;
        this.description = description;
    }

    public User(String name, LocalDate birthday, String loginId, String city, String email, String description) {
        this(-1, name,  birthday, loginId, city, email, description);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", loginId='" + loginId + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (loginId != null ? !loginId.equals(user.loginId) : user.loginId != null) return false;
        if (city != null ? !city.equals(user.city) : user.city != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return description != null ? description.equals(user.description) : user.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (loginId != null ? loginId.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
