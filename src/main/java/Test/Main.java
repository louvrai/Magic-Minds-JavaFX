package Test;

import Entities.Command;
import Entities.Comment;
import Entities.Produit;
import Services.CartFileManager;
import Services.CommandCRUD;
import Services.CommentCRUD;
import Services.ProduitCRUD;
import Utils.MyDB;
import com.sun.source.tree.NewArrayTree;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner =new Scanner(System.in);
    Produit p=new Produit(202,19.9,"amazoon","good","img1","img2","img3","cat");
        ProduitCRUD mod=new ProduitCRUD();
       LocalDate currentDate = LocalDate.now();

        //Comment c=new Comment(1,32,3,"this my first comment",currentDate);
       // CommentCRUD go=new CommentCRUD();
         // go.ajouter(c);
        // Test adding products
       // CartFileManager.addProduct(101, 2);  // Add 2 units of product 101
       // CartFileManager.addProduct(102, 5);  // Add 5 units of product 102

        // Display cart after adding products
       // System.out.println("Cart after additions:");
        CommandCRUD cr=new CommandCRUD();
        System.out.println(cr.afficherAll());

    }
}