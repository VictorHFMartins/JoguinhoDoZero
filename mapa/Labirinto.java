package mapa;

import objetos.SuperObjects;
import personagens.Inimigo;
import personagens.Jogador;
import objetos.Chao;
import objetos.Chave;
import objetos.Porta;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import main.GamePanel;
import mecanica.pathFinder.Interseccao;

public class Labirinto {

    private GamePanel p;
    private List<Inimigo> inimigo = new ArrayList<>();
    private GeradorMapa gerador = new GeradorMapa();
    private MapIndex mI = new MapIndex();

    private int mapaAtual = 0;

    public Jogador getJogador() {
        return p.getJogador();
    }

    public Labirinto(GamePanel p) {
        this.p = p;
        gerador.construirLabirinto(mapaAtual, this, p);
    }

    public void mudarMapa(int novoMapa, Jogador jogador, int x, int y) {
        gerador.construirLabirinto(novoMapa, this, p);
        mapaAtual = novoMapa;
        jogador.setCoordanadas(x, y);
    }

    public void draw(Graphics2D g2d) {
        SuperObjects[][] terreno = gerador.getTerreno();
        for (int linha = 0; linha < p.getLinhas(); linha++) {
            for (int coluna = 0; coluna < p.getColunas(); coluna++) {
                if (terreno[linha][coluna] != null) {
                    terreno[linha][coluna].draw(g2d);
                }
            }
        }

        for (SuperObjects obj : gerador.getObjetos()) {
            obj.draw(g2d);
        }
    }

    public void spawnChave(int x, int y) {

        int tileX = x / p.getTamanhoTile();
        int tileY = y / p.getTamanhoTile();

        Chao chao = new Chao(tileX * p.getTamanhoTile(), tileY * p.getTamanhoTile(), p.getTamanhoTile());
        gerador.getTerreno()[tileY][tileX] = chao;

        Chave chave = new Chave(tileX * p.getTamanhoTile(), tileY * p.getTamanhoTile(), p.getTamanhoTile());
        gerador.getObjetos().add(chave);

        System.out.println("🔑 Chave spawnada em (" + tileX + ", " + tileY + ")");
    }

    public Interseccao[][] gerarGrade() {
        int linhas = p.getLinhas();
        int colunas = p.getColunas();
        Interseccao[][] grade = new Interseccao[linhas][colunas];

        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                boolean parede = false;

                SuperObjects tile = getTerreno()[linha][coluna];
                if (tile != null && tile.isColisao()) {
                    parede = true;
                }

                for (SuperObjects obj : getObjetos()) {
                    if (obj.getX() / p.getTamanhoTile() == coluna &&
                            obj.getY() / p.getTamanhoTile() == linha &&
                            obj.isColisao()) {
                        parede = true;
                        break;
                    }
                }

                grade[linha][coluna] = new Interseccao(linha, coluna, parede);
            }
        }

        return grade;
    }

    public List<Inimigo> getInimigo() {
        return inimigo;
    }

    public SuperObjects[][] getTerreno() {
        return gerador.getTerreno();
    }

    public java.util.List<SuperObjects> getObjetos() {
        return gerador.getObjetos();
    }

    public Porta getPorta() {
        return gerador.getPorta();
    }

    public int getMapaAtual() {
        return mapaAtual;
    }

    public int MaxMap() {
        return mI.mapas.size();
    }

    public String getIndexof(int num) {
        return mI.getMapas(num);
    }
}
