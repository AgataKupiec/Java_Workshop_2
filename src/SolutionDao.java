import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;

public class SolutionDao {
    
    private static final String URL = "jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "coderslab";
    
    private static final String CREATE_SOLUTION_QUERY =
            "INSERT INTO solutions(created, updated, description, exercise_id, user_id) VALUES (?, ?, ?, ?, ?)";
    private static final String CREATE_SOLUTION_PRIMARY_QUERY =
            "INSERT INTO solutions(created, exercise_id, user_id) VALUES (?, ?, ?)";
    private static final String READ_SOLUTION_QUERY =
            "SELECT * FROM solutions where id = ?";
    private static final String UPDATE_SOLUTION_QUERY =
            "UPDATE solutions SET created = ?, updated = ?, description = ?, exercise_id = ?, user_id = ? where id = ?";
    private static final String DELETE_SOLUTION_QUERY =
            "DELETE FROM solutions WHERE id = ?";
    private static final String FIND_ALL_SOLUTIONS_QUERY =
            "SELECT * FROM solutions";
    private static final String FIND_ALL_SOLUTIONS_BY_USER_QUERY =
            "SELECT * FROM solutions WHERE user_id = ?";
    private static final String FIND_ALL_SOLUTIONS_BY_EXERCISE_QUERY =
            "SELECT * FROM solutions WHERE exercise_id = ?";
    private static final String FIND_EMPTY_SOLUTIONS_BY_USER_QUERY =
            "SELECT * FROM solutions WHERE user_id = ? AND description = '';";
    
    
    public Solution create(Solution solution) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     conn.prepareStatement(CREATE_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setTimestamp(1, new Timestamp(solution.getCreated().getTime()));
            statement.setTimestamp(2, new Timestamp(solution.getUpdated().getTime()));
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getExercise_id());
            statement.setInt(5, solution.getUser_id());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Solution createPrimary(Solution solution) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     conn.prepareStatement(CREATE_SOLUTION_PRIMARY_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setTimestamp(1, new Timestamp(solution.getCreated().getTime()));
            statement.setInt(2, solution.getExercise_id());
            statement.setInt(3, solution.getUser_id());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public Solution read(int solutionID) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(READ_SOLUTION_QUERY)) {
            statement.setInt(1, solutionID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getTimestamp("created"));
                solution.setUpdated(resultSet.getTimestamp("updated"));
                solution.setDescription(resultSet.getString("description"));
                solution.setExercise_id(resultSet.getInt("exercise_id"));
                solution.setUser_id(resultSet.getInt("user_id"));
                return solution;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void update(Solution solution) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(UPDATE_SOLUTION_QUERY)) {
            statement.setTimestamp(1, new Timestamp(solution.getCreated().getTime()));
            statement.setTimestamp(2, new Timestamp(solution.getUpdated().getTime()));
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getExercise_id());
            statement.setInt(5, solution.getUser_id());
            statement.setInt(6, solution.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int solutionID) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(DELETE_SOLUTION_QUERY)) {
            statement.setInt(1, solutionID);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Solution[] addToArray(Solution g, Solution[] solutions) {
        Solution[] tmpSolutions = Arrays.copyOf(solutions, solutions.length + 1);
        tmpSolutions[solutions.length] = g;
        return tmpSolutions;
    }
    
    public Solution[] findAll() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_SOLUTIONS_QUERY)) {
            Solution[] solutions = new Solution[0];
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getTimestamp("created"));
                solution.setUpdated(resultSet.getTimestamp("updated"));
                solution.setDescription(resultSet.getString("description"));
                solution.setExercise_id(resultSet.getInt("exercise_id"));
                solution.setUser_id(resultSet.getInt("user_id"));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Solution[] findAllByUserId(int userId) {
        return findSolutions(userId, FIND_ALL_SOLUTIONS_BY_USER_QUERY);
    }
    
    public Solution[] findSolutions(int userId, String query) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query)) {
            Solution[] solutions = new Solution[0];
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getTimestamp("created"));
                solution.setUpdated(resultSet.getTimestamp("updated"));
                solution.setDescription(resultSet.getString("description"));
                solution.setExercise_id(resultSet.getInt("exercise_id"));
                solution.setUser_id(resultSet.getInt("user_id"));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public Solution[] findAllByUserIdWithoutSolution(int userId) {
        return findSolutions(userId, FIND_EMPTY_SOLUTIONS_BY_USER_QUERY);
    }
    
    public Solution[] findAllByExerciseId(int exerciseId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_SOLUTIONS_BY_EXERCISE_QUERY)) {
            Solution[] solutions = new Solution[0];
            statement.setInt(1, exerciseId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getTimestamp("created"));
                solution.setCreated(resultSet.getTimestamp("updated"));
                solution.setDescription(resultSet.getString("description"));
                solution.setExercise_id(resultSet.getInt("exercise_id"));
                solution.setUser_id(resultSet.getInt("user_id"));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
