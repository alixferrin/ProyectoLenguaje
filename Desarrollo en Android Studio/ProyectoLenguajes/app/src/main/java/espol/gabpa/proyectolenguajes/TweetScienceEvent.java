package espol.gabpa.proyectolenguajes;

public class TweetScienceEvent {

    private int id;
    private String texto;
    private int cantidadOcurrencias;

    public TweetScienceEvent(int id, String tuit, int cantidad){
        this.id = id;
        this.texto = tuit;
        this.cantidadOcurrencias = cantidad;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getCantidadOcurrencias() {
        return cantidadOcurrencias;
    }

    public void setCantidadOcurrencias(int cantidadOcurrencias) {
        this.cantidadOcurrencias = cantidadOcurrencias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
