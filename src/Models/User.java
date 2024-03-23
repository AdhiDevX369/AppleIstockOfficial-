package Models;

public class User {
    private int userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String street;
    private String city;
    private String country;
    private String position;
    private String nic;
    private String name;
    private String address;
    private double totalIncome;
    private String state;
    // Constructors, getters, setters
    public User(int userId, String username, String password, String firstName, String lastName,
            String mobile, String email, String street, String city, String country,
            String position, String nic) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.street = street;
        this.city = city;
        this.country = country;
        this.position = position;
        this.nic = nic;
    }
    public User(int userId, String username, String password, String position, String name, String address, String email, String mobile, String nic) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.position = position;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.nic = nic;
        this.position = position;

    }
    public User(int userId, String name, String state, String nic) {
        this.userId = userId;
        this.name = name;
        this.nic = nic;
        this.state = state;
    }
    public User() {
    }
    public double getTotalIncome() {
        return totalIncome;
    }
    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getMobile() {
        return mobile;
    }
    public String getEmail() {
        return email;
    }
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }
    public String getCountry() {
        return country;
    }
    public String getPosition() {
        return position;
    }
    public String getNic() {
        return nic;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setNic(String nic) {
        this.nic = nic;
    }
}
