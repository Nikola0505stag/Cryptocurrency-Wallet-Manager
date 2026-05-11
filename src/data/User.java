package data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {

    private String username;
    private String password;
    private double balance;
    private Map<String, Double> crypto;

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        this.balance = 0;
        crypto = new HashMap<>();
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Wrong password inputted!");
        }

        this.password = password;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Wrong username inputted!");
        }

        this.username = username;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Double> getCrypto() {
        return Collections.unmodifiableMap(crypto);
    }

    public void depositMoney(double money) {
        if (money <= 0) {
            throw new IllegalArgumentException("Invalid money - negative!");
        }
        this.balance += money;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(balance, user.balance) == 0 && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(crypto, user.crypto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, balance, crypto);
    }
}
