# 💰 Cryptocurrency Wallet Manager

A multi-threaded client-server application designed to simulate a personal cryptocurrency investment portfolio. Users can register, manage their balance, and trade cryptocurrencies using real-time market data.

## 🚀 Features

- **Multi-threaded Server**: Handles multiple clients concurrently using a thread pool.
- **User Authentication**: Secure registration and login system.
- **Database Integration**: Uses **MySQL** for robust data persistence (Users and Balances).
- **CoinAPI Integration**: Fetches real-time cryptocurrency prices and asset information.
- **Smart Caching**: API responses are cached for 30 minutes to optimize performance and reduce API call limits.
- **Error Handling**: Custom exceptions and detailed logging for server-side errors.

---

## 🛠️ Database Setup

The application uses a MySQL database named `Register_schema`. 

1. **Create the Schema:**
   ```sql
   CREATE DATABASE Register_schema;
   USE Register_schema;
    ```
2. **Create the table**
    ```sql
        CREATE TABLE Users (
            id INT AUTO_INCREMENT PRIMARY KEY,
            username VARCHAR(50) UNIQUE NOT NULL,
            password VARCHAR(255) NOT NULL,
            balance DOUBLE DEFAULT 0.0
        );

    ```
3. **Safe Updates**: To allow bulk deletes during testing, you may need to run:
    ```sql
        SET SQL_SAFE_UPDATES = 0;
    ```

---

## 💻 Command Reference

Once connected, you can use the following commands:

| Command | Description | Example |
| :--- | :--- | :--- |
| register --username=<name> --password=<password> | Register a new account | register --username=Nikola --password=123 |
| login --username=<name> --password=<password> | Log in to your account | login --username=Nikola --password=123 |
| deposit-money=<amount> | Deposit USD into your wallet | deposit-money=1005.50 |
| print --account-details | View your username and balance | print --account-details |
| list-offerings | Show available cryptocurrencies | list-offerings |
| buy --offering=<ID> --money=<amount> | Purchase a cryptocurrency | buy --offering=BTC --money=500 |
| sell --offering=<ID> | Sell all units of a specific asset | sell --offering=BTC |
| get-wallet-summary | Current active investments |	get-wallet-summary |
| quit | Disconnect from the server | quit |

--- 

## 🔧 Technical Details
- **Core:** Java 25
- **Database:** MYSQL 
- **Networking:** Java Sockets (TCP)
- **Regex:** Advanced command parsing to ensure strict input validation.
- **Testing:** Comprehensive JUnit 5 suite for Commands, Refactoring logic, and JDBC operations.

---

## ⚙️ Configuration
To connect the server to your local database, update the credentials in src/helper/MyJDBC.java:

``` Java
    private static final String URL = "jdbc:mysql://localhost:3306/Register_schema";
    private static final String USER = "your_root_user";
    private static final String PASSWORD = "your_password";

```

---

## 📝 Design Patterns used

- **Command Pattern:** Encapsulates requests as objects, allowing for easy expansion of new features.
- **Singleton/Helper:** Centralized database access via MyJDBC.
- **Custom Exceptions:** Specific error types like NegativeDepositException and ZeroDepositException for clean flow control.

