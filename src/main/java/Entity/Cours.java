package Entity;

public class Cours {
    private int id , duree , nb_chapitre ;
    private String titre, description , status ;

    public Cours(){

    }
    public Cours(int id, int duree, int nb_chapitre, String titre, String description, String status) {
        this.id = id;
        this.duree = duree;
        this.nb_chapitre = nb_chapitre;
        this.titre = titre;
        this.description = description;
        this.status = status;
    }

    public Cours(int duree, int nb_chapitre, String titre, String description, String status) {
        this.duree = duree;
        this.nb_chapitre = nb_chapitre;
        this.titre = titre;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getNbrchapitre() {
        return nb_chapitre;
    }

    public void setNbrchapitre(int nb_chapitre) {
        this.nb_chapitre = nb_chapitre;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", duree=" + duree +
                ", nb_chapitre=" + nb_chapitre +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
