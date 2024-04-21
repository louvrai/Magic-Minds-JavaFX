package Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Entities.Quiz;
import Utile.Database;


public class QuizCrud implements CrudInterface<Quiz> {
    static Connection connection;

    public QuizCrud() {
        connection = Database.getInstance().getConnection();
    }
    @Override
    public void ajouter(Quiz quiz) throws SQLException {
        try {
            String req = "INSERT INTO `quiz` (`titre`,`temp`,`nbquestion`) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(req);

            ps.setString(1, quiz.getTitre());

            ps.setInt(2, quiz.getTemp());
            ps.setInt(3, quiz.getNb_question());


            ps.executeUpdate();
            System.out.println("Quiz added successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Quiz quiz) throws SQLException {
        try {
            String req = "UPDATE `quiz` SET `titre`=?, `temp`=?, `nbquestion`=? WHERE `id`=?";
            PreparedStatement ps = connection.prepareStatement(req);

            ps.setString(1, quiz.getTitre());

            ps.setInt(2, quiz.getTemp());
            ps.setInt(3, quiz.getNb_question());

            ps.executeUpdate();
            System.out.println("Quiz updated successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }





    @Override
    public void supprimer(int id) throws SQLException {
        try {
            String req = "DELETE FROM `quiz` WHERE `id`=?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Quiz with id " + id + " deleted successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public List<Quiz> recuperer() throws SQLException {
        List<Quiz> q = new ArrayList<>();
        try {
            String req = "SELECT * FROM `quiz`";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(req);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titre = resultSet.getString("titre");
                int temp = resultSet.getInt("temp");
                int nb_question = resultSet.getInt("nbquestion");



                Quiz quiz = new Quiz(id,titre,temp,nb_question);
                q.add(quiz);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return q;
    }






}
