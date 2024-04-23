package Entity;

public class Ressource {
    private int id ;
    private String titre , type , url ;


    public Ressource(){

    }

    public Ressource(String titre, String type, String url) {
        this.titre = titre;
        this.type = type;
        this.url = url;
    }

    public Ressource(int id, String titre, String type, String url) {
        this.id = id;
        this.titre = titre;
        this.type = type;
        this.url = url;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Ressource{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
