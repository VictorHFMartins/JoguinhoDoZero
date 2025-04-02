package objetos;

import java.awt.Graphics2D;
import mecanica.Interagivel;
import personagens.Jogador;

public class Chave extends SuperObjects implements Interagivel {

    public Chave(int x, int y, int medida) {
        super(x, y, medida);
        this.colisao = false;
        setImagem("/res/obj/keyTile.png");
    }

    @Override
    public void interagir(Jogador jogador) {
        jogador.setKey();
        if(jogador.hasKey()) {
            this.coletado = true;
            System.out.println("🔑 Chave coletada!");
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
            g2d.drawImage(imagem, x, y, medida, medida, null);
    }

    private boolean coletado = false;

    public boolean isColetado() {
        return coletado;
    }
}