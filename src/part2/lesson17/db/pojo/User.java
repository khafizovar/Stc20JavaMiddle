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
}
