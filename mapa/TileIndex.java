package mapa;

import objetos.*;
import personagens.Inimigo;

public class TileIndex {

    public SuperObjects criarTerreno(int tipo, int x, int y, int medida) {
        switch (tipo) {
            case 0:
                return new Chao(x, y, medida);
            case 1:
                return new Parede(x, y, medida);
            default:
                return null;
        }
    }

    public SuperObjects criarObjeto(int tipo, int x, int y, int medida, Labirinto labirinto) {
        switch (tipo) {
            case 2:
                return new Chave(x, y, medida);
            case 3:
                return new Inimigo(x, y, medida, labirinto);
            case 9:
                return new Porta(x, y, medida, labirinto);
            default:
                return null;
        }
    }
}
