package objetos;

import mapa.Labirinto;
import mecanica.Interagivel;
import personagens.Jogador;

public class Porta extends SuperObjects implements Interagivel {
    Labirinto labirinto;
    private boolean interagindo = false;

    public Porta(int x, int y, int medida, Labirinto labirinto) {
        super(x, y, medida);
        this.labirinto = labirinto;
        setImagem("/res/obj/door.png");
        this.colisao = true;
    }

    @Override
    public void interagir(Jogador jogador) {
        if (!interagindo) {
            interagindo = true;

            int proximoMapa = labirinto.getMapaAtual() + 1;

            if (jogador.hasKey() && proximoMapa < labirinto.MaxMap()) {
                labirinto.mudarMapa(proximoMapa, jogador, 55, 55);
                jogador.resetKey();
                System.out.println("Você passou de fase!");
            } else {
                System.out.println("🚪 A porta está trancada. Encontre a chave.");
            }
        }
    }

    public void resetarInteracao(Jogador jogador) {
        if (!jogador.getBounds().intersects(this.getBounds())) {
            interagindo = false;
        }
    }
}
