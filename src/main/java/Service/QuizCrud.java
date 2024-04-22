package Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Entities.Questions;
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





   /* @Override
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
    }*/
   public void supprimer(int id) throws SQLException {
       QuestionsCrud qc=new QuestionsCrud();
       try {
           // Supprimer d'abord toutes les questions associées à ce quiz
           List<Questions> questions = qc.recupererParQuizId(id);
           for (Questions question : questions) {
               qc.supprimer(question.getId());
           }

           // Ensuite, supprimer le quiz lui-même
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


    public boolean titreExisteDeja(String titre) {
        try {
            String query = "SELECT COUNT(*) FROM quiz WHERE titre = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, titre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Si count est supérieur à 0, cela signifie que le titre existe déjà
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




}
