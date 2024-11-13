package user;

public class UserCredentialsForUpdate {
    private String email;
    private String name;

    public UserCredentialsForUpdate(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static UserCredentialsForUpdate fromUser(User user) {
        return new UserCredentialsForUpdate(user.getEmail(), user.getName());
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}