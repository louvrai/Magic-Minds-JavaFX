package tn.esprit;

import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Création d'un événement pour tester les opérations CRUD
        Evenement event = new Evenement(727, 50, "magiic", "Une fête ",
                "Place du marché", "Fête", parseDate("2024-04-01"), parseDate("2024-05-01"));

        // Création du service pour manipuler les événements
        ServiceEvenement serviceEvenement = new ServiceEvenement();

        // Ajout de l'événement à la base de données
        serviceEvenement.add(event);
       System.out.println("Event added: " + event);

        // Récupération de tous les événements
        System.out.println("All events: " + serviceEvenement.getAll());

        // Mise à jour de l'événement
        //event.setNom("Nouveau nom pour l'événement");
       // serviceEvenement.update(event);
       // System.out.println("Event updated: " + event);

        // Suppression de l'événement
     //serviceEvenement.delete(event);
     //  System.out.println("Event deleted: " + event);

    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); // Gérer l'exception de parsing
            return null;
        }
    }
}
