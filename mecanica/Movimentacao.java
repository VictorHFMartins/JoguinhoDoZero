package mecanica;

import mapa.Labirinto;
import objetos.Chave;
import objetos.Porta;
import objetos.SuperObjects;
import personagens.Jogador;
import java.util.ArrayList;

import java.awt.geom.Ellipse2D;

public class Movimentacao {
    public Direcao direcao = Direcao.PARADO;
    public int nextX, nextY;

    public Movimentacao() {

    }

    public enum Direcao {
        CIMA, BAIXO, ESQUERDA, DIREITA, DIAGONAL, PARADO
    }

    public enum Colisao {
        CIMA, BAIXO, ESQUERDA, DIREITA, LIVRE
    }

    public void update(Labirinto labirinto, Jogador jogador, Teclado tc) {
        int dx = 0;
        int dy = 0;
        if (tc.cima)
            dy -= jogador.getSpeed();
        if (tc.baixo)
            dy += jogador.getSpeed();
        if (tc.esquerda)
            dx -= jogador.getSpeed();
        if (tc.direita)
            dx += jogador.getSpeed();

        jogador.atualizarDirecao(tc.cima, tc.baixo, tc.esquerda, tc.direita);

        int nextX = jogador.getX();
        int nextY = jogador.getY();

        Ellipse2D.Double passoX = new Ellipse2D.Double(nextX + dx + 6, nextY + 6, jogador.getWidth() - 12,
                jogador.getHeight() - 12);
        boolean colisaoX = false;

        for (SuperObjects obj : labirinto.getObjetos()) {
            if (obj.isColisao() && passoX.intersects(obj.getBounds())) {
                colisaoX = true;
                break;
            }
        }
        if (!colisaoX) {
            for (SuperObjects[] linha : labirinto.getTerreno()) {
                for (SuperObjects tile : linha) {
                    if (tile != null && tile.isColisao() && passoX.intersects(tile.getBounds())) {
                        colisaoX = true;
                        break;
                    }
                }
                if (colisaoX)
                    break;
            }
        }

        if (!colisaoX) {
            nextX += dx;
        }

        Ellipse2D.Double passoY = new Ellipse2D.Double(nextX + 6, nextY + dy + 6, jogador.getWidth() - 12,
                jogador.getHeight() - 12);
        boolean colisaoY = false;

        for (SuperObjects obj : labirinto.getObjetos()) {
            if (obj.isColisao() && passoY.intersects(obj.getBounds())) {
                colisaoY = true;
                break;
            }
        }
        if (!colisaoY) {
            for (SuperObjects[] linha : labirinto.getTerreno()) {
                for (SuperObjects tile : linha) {
                    if (tile != null && tile.isColisao() && passoY.intersects(tile.getBounds())) {
                        colisaoY = true;
                        break;
                    }
                }
                if (colisaoY)
                    break;
            }
        }

        if (!colisaoY) {
            nextY += dy;
        }

        jogador.setX(nextX);
        jogador.setY(nextY);

        for (SuperObjects obj : new ArrayList<>(labirinto.getObjetos())) {
            if (obj instanceof Interagivel) {
                if (jogador.getBounds().intersects(obj.getBounds())) {
                    ((Interagivel) obj).interagir(jogador);
                }
            }
            if (obj instanceof Porta) {
                ((Porta) obj).resetarInteracao(jogador);
            }
        }
        labirinto.getObjetos().removeIf(obj -> obj instanceof Chave && ((Chave) obj).isColetado());

        this.nextX = nextX;
        this.nextY = nextY;
    }
}
