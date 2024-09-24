package fcm.fcm.Entity;

public class LoginRequest {
    private String email;
    private String password;

    // 기본 생성자
    public LoginRequest() {
    }

    // 생성자 (선택 사항)
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter 및 Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString 메서드 (디버깅용, 선택 사항)
    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
