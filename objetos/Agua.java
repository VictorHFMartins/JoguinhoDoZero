package objetos;

public class Agua extends SuperObjects {
    
    public Agua(int x, int y, int medida) {
        super(x, y, medida);
        this.colisao = true;
        setImagem("/res/tiles/agua.png");
    }
}
