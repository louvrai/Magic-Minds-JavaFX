package Entity;



import java.util.ArrayList;
import java.util.List;

public class Categorie {
    private int id , nbr_chapitre ,nbr_cours;
    private String titre ,description,image;
    private List<Cours> coursList = new ArrayList<>();
    public Categorie(){

    }
    public Categorie(int id, String titre, String description, String image) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.image = image;
    }

    public Categorie(String titre, String description, String image) {
        this.titre = titre;
        this.description = description;
        this.image = image;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNbr_chapitre() {
        return nbr_chapitre;
    }

    public int getNbr_cours() {
        return nbr_cours;
    }

    public List<Cours> getCoursList() {
        return coursList;
    }

    public void setCoursList(List<Cours> coursList) {
        this.coursList = coursList;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

