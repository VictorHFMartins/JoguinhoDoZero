package mapa;

import objetos.*;
import personagens.Inimigo;
import main.GamePanel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GeradorMapa {
    private SuperObjects[][] terreno;
    private List<SuperObjects> objetos = new ArrayList<>();
    private Porta porta;
    private Inimigo inimigo;
    SuperObjects obj;

    public void construirLabirinto(int mapa, Labirinto maze, GamePanel p) {
        objetos.clear();
        porta = null;
        inimigo = null;
        terreno = new SuperObjects[p.getLinhas()][p.getColunas()];

        MapIndex mI = new MapIndex();
        TileIndex tI = new TileIndex();

        carregarTerreno(mI.getMapas(mapa), mapa, maze, p, tI);
        carregarObjetos(mI.getObjetos(mapa), mapa, maze, p, tI);
    }

    public SuperObjects getObj() {
        return obj;
    }

    private void carregarTerreno(String caminho, int mapa, Labirinto maze, GamePanel p, TileIndex tI) {
        try {
            InputStream is = getClass().getResourceAsStream(caminho);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int linha = 0;
            while (linha < p.getLinhas()) {
                String l = br.readLine();
                String[] nums = l.split(" ");
                for (int coluna = 0; coluna < p.getColunas(); coluna++) {
                    int tipo = Integer.parseInt(nums[coluna]);
                    int x = coluna * p.getTamanhoTile();
                    int y = linha * p.getTamanhoTile();
                    SuperObjects tile = tI.criarTerreno(tipo, x, y, p.getTamanhoTile());
                    terreno[linha][coluna] = tile;
                }
                linha++;
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar terreno: " + caminho);
            e.printStackTrace();
        }
    }

    private void carregarObjetos(String caminho, int mapa, Labirinto labirinto, GamePanel p, TileIndex tI) {
        if (caminho == null)
            return;

        try {
            InputStream is = getClass().getResourceAsStream(caminho);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int linha = 0;
            while (linha < p.getLinhas()) {
                String l = br.readLine();
                String[] nums = l.split(" ");
                for (int coluna = 0; coluna < p.getColunas(); coluna++) {
                    int tipo = Integer.parseInt(nums[coluna]);
                    if (tipo == 0)
                        continue;

                    int x = coluna * p.getTamanhoTile();
                    int y = linha * p.getTamanhoTile();
                    obj = tI.criarObjeto(tipo, x, y, p.getTamanhoTile(), labirinto);

                    if (obj instanceof Porta) {
                        porta = (Porta) obj;
                    }
                    if (obj instanceof Inimigo) {
                        inimigo = (Inimigo) obj;
                        labirinto.getInimigo().add(inimigo);
                    }
                    if (obj != null) {
                        objetos.add(obj);
                    }
                }
                linha++;
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar objetos: " + caminho);
            e.printStackTrace();
        }
    }

    public SuperObjects[][] getTerreno() {
        return terreno;
    }

    public List<SuperObjects> getObjetos() {
        return objetos;
    }

    public Porta getPorta() {
        return porta;
    }
}
