package objetos;

public class Parede extends SuperObjects {

    public Parede(int x, int y, int medida) {
        super(x, y, medida);
        this.colisao = true;
        setImagem("/res/tiles/parede1.jpg");
    }
}
