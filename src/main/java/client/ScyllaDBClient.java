package client;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class ScyllaDBClient {
    private final CqlSession session;

    public ScyllaDBClient(String contactPoint, int port, String keyspace) {
        session = CqlSession.builder()
                            .addContactPoint(new InetSocketAddress(contactPoint, port))
                            .withLocalDatacenter("datacenter1")  // Ensure this matches your ScyllaDB datacenter name
                            .build();
        createKeyspace(keyspace);
        session.execute("USE " + keyspace);
    }
    public void createKeyspace(String keyspace) {
        String query = "CREATE KEYSPACE IF NOT EXISTS " + keyspace +
                " WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};";
        session.execute(query);
    }

    public void createUserTable() {
        String query = "CREATE TABLE IF NOT EXISTS users ("
                + "id text PRIMARY KEY, "
                + "name text, "
                + "age int);";
        session.execute(query);
    }

    public void insertUser(User user) {
        String query = "INSERT INTO users (id, name, age) VALUES (?, ?, ?);";
        PreparedStatement preparedStatement = session.prepare(query);
        session.execute(preparedStatement.bind(user.getId(), user.getName(), user.getAge()));
    }

    public User getUserById(String id) {
        String query = "SELECT id, name, age FROM users WHERE id = ?;";
        PreparedStatement preparedStatement = session.prepare(query);
        ResultSet resultSet = session.execute(preparedStatement.bind(id));
        Row row = resultSet.one();
        if (row != null) {
            return new User(row.getString("id"), row.getString("name"), row.getInt("age"));
        } else {
            return null;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, age FROM users;";
        ResultSet resultSet = session.execute(query);
        for (Row row : resultSet) {
            users.add(new User(row.getString("id"), row.getString("name"), row.getInt("age")));
        }
        return users;
    }

    public void close() {
        session.close();
    }
}

