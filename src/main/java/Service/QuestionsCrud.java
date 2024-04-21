package Service;

import Entities.Questions;
import Entities.Quiz;
import Utile.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionsCrud implements CrudInterface<Questions>{
    static Connection connection;

    public QuestionsCrud() {
        connection = Database.getInstance().getConnection();
    }

    @Override
    public void ajouter(Questions questions) throws SQLException {
        try {
            String req = "INSERT INTO `question` (`quiz_id`,`question`,`choix1`,`choix2`,`choix3`,`reponse`) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, questions.getIdQuiz());
            ps.setString(2, questions.getQuestion());
            ps.setString(3, questions.getChoix1());
            ps.setString(4, questions.getChoix2());
            ps.setString(5, questions.getChoix3());
            ps.setString(6, questions.getReponse());
            ps.executeUpdate();
            System.out.println("Question added successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Questions questions) throws SQLException {
        try {
            String req = "UPDATE `question` SET `quiz_id`=?, `question`=?, `choix1`=?, `choix2`=?, `choix3`=?, `reponse`=? WHERE `id`=?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, questions.getIdQuiz());
            ps.setString(2, questions.getQuestion());
            ps.setString(3, questions.getChoix1());
            ps.setString(4, questions.getChoix2());
            ps.setString(5, questions.getChoix3());
            ps.setString(6, questions.getReponse());

            ps.executeUpdate();
            System.out.println("Question updated successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        try {
            String req = "DELETE FROM `question` WHERE `id`=?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Question with id " + id + " deleted successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Questions> recuperer() throws SQLException {
        List<Questions> q = new ArrayList<>();
        try {
            String req = "SELECT * FROM `question`";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(req);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idQuiz = resultSet.getInt("quiz_id");
                String question = resultSet.getString("question");
                String choix1 = resultSet.getString("choix1");
                String choix2 = resultSet.getString("choix2");
                String choix3 = resultSet.getString("choix3");
                String reponse = resultSet.getString("reponse");
               Questions questions=new Questions(question,choix1,choix2,choix3,reponse,id,idQuiz);
                q.add(questions);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return q;
    }
}
