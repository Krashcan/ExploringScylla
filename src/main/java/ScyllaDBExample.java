import java.util.List;
import client.ScyllaDBClient;
import model.User;

public class ScyllaDBExample {
    public static void main(String[] args) {
        ScyllaDBClient client = new ScyllaDBClient("127.0.0.1", 9042, "my_keyspace");

        // Create the users table
        client.createUserTable();

        // Insert a user
        User user = new User("1", "John Doe", 30);
        client.insertUser(user);

        // Retrieve a user by ID
        User retrievedUser = client.getUserById("1");
        System.out.println("Retrieved User: " + retrievedUser);

        // Retrieve all users
        List<User> allUsers = client.getAllUsers();
        System.out.println("All Users: " + allUsers);

        // Close the client
        client.close();
    }
}

