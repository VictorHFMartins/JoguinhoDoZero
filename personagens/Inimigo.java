package personagens;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import objetos.SuperObjects;
import mapa.Labirinto;
import mecanica.pathFinder.Interseccao;
import mecanica.pathFinder.PathFinder;

public class Inimigo extends SuperObjects {

    private BufferedImage[] spritesCima = new BufferedImage[3];
    private BufferedImage[] spritesBaixo = new BufferedImage[3];
    private BufferedImage[] spritesEsquerda = new BufferedImage[3];
    private BufferedImage[] spritesDireita = new BufferedImage[3];

    private int frame = 0;
    private int contAnim = 0;
    private Direcao direcao = Direcao.DIREITA;

    private int xInicial, xFinal;
    private boolean vivo = true;
    public static int totalMonstros = 0;

    private Labirinto labirinto;

    public enum Direcao {
        CIMA,
        BAIXO,
        ESQUERDA,
        DIREITA
    }

    public Inimigo(int x, int y, int medida, Labirinto labirinto) {
        super(x, y, medida);
        this.labirinto = labirinto;
        this.xInicial = x;
        this.xFinal = x + medida * 4;
        this.colisao = true;
        totalMonstros++;
        carregarSprites();
    }

    private void carregarSprites() {
        try {
            for (int i = 0; i < 3; i++) {
                spritesCima[i] = ImageIO.read(getClass().getResourceAsStream("/res/jogador/PCostas" + i + ".png"));
                spritesBaixo[i] = ImageIO.read(getClass().getResourceAsStream("/res/jogador/PFrente" + i + ".png"));
                spritesEsquerda[i] = ImageIO
                        .read(getClass().getResourceAsStream("/res/jogador/PEsquerda" + i + ".png"));
                spritesDireita[i] = ImageIO
                        .read(getClass().getResourceAsStream("/res/jogador/PDireita" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Jogador jogador) {
        if (!vivo)
            return;

        Rectangle areaJogador = jogador.getBounds();

        int distancia = 250;
        Rectangle deteccao = new Rectangle(x - distancia, y - distancia, medida + distancia * 2,
                medida + distancia * 2);

        if (deteccao.intersects(areaJogador)) {
            perseguir(labirinto);
        } else {
            patrulhar();
        }

        animar();
    }

    public boolean temColisao(int novoX, int novoY, int medida, SuperObjects ignorar) {
        Rectangle boundsNovo = new Rectangle(novoX, novoY, medida, medida);

        for (SuperObjects[] linha : labirinto.getTerreno()) {
            for (SuperObjects obj : linha) {
                
                if (obj != null && obj.isColisao() && boundsNovo.intersects(obj.getBounds())) {
                    return true;
                }
            }
        }

        for (SuperObjects obj : labirinto.getObjetos()) {
            if (obj == ignorar)
                continue;
            if (obj.isColisao() && boundsNovo.intersects(obj.getBounds())) {
                return true;
            }
            if (obj.isColisao() && boundsNovo.intersects(labirinto.getJogador().getBounds())) {
                return true;
            }
        }

        return false;
    }

    private void patrulhar() {
        int velocidade = 1;
        int novoX = x + (direcao == Direcao.DIREITA ? velocidade : -velocidade);

        if (!temColisao(novoX, y, medida, this)) {
            x = novoX;
        } else {
            direcao = (direcao == Direcao.DIREITA) ? Direcao.ESQUERDA : Direcao.DIREITA;
        }

        if (x >= xFinal)
            direcao = Direcao.ESQUERDA;
        else if (x <= xInicial)
            direcao = Direcao.DIREITA;

    }

    public void perseguir(Labirinto labirinto) {
        if (labirinto == null || labirinto.getJogador() == null)
            return;

        Jogador jogador = labirinto.getJogador();

        // Pega a posição do jogador e inimigo na grade
        int jogadorCentroX = jogador.getY() + jogador.getWidth() / 2;
        int jogadorCentroY = jogador.getX() + jogador.getHeight() / 2;
        int destinoX = jogadorCentroY / medida;
        int destinoY = jogadorCentroX / medida;
        int inicioX = this.getY() / medida;
        int inicioY = this.getX() / medida;

        System.out.println(
                "[DEBUG] Jogador [" + destinoX + "," + destinoY + "] | Inimigo [" + inicioX + "," + inicioY + "]");

        Interseccao[][] grade = labirinto.gerarGrade();

        
        if (!isDentroDaGrade(inicioX, inicioY, grade) || !isDentroDaGrade(destinoX, destinoY, grade)) {
            System.out.println("[ERRO] Posição fora da grade.");
            return;
        }

        Interseccao inicio = grade[inicioX][inicioY];
        Interseccao fim = grade[destinoX][destinoY];

        if (fim.isParede) {
            System.out.println("[ERRO] Destino está em uma parede!");
            return;
        }

        List<Interseccao> caminho = PathFinder.encontrarCaminho(grade, inicio, fim);

        if (caminho.size() > 1) {
            Interseccao proximo = caminho.get(1); // Caminho[0] é a posição atual
            int alvoX = proximo.y * medida;
            int alvoY = proximo.x * medida;

            int dx = Integer.compare(alvoX, this.x);
            int dy = Integer.compare(alvoY, this.y);

            int velocidade = 1;
            int novoX = this.x + dx * velocidade;
            int novoY = this.y + dy * velocidade;

            if (!temColisao(novoX, this.y, medida, this) && !temColisao(this.x, novoY, medida, this)) {
                this.x = novoX;
                this.y = novoY;
            } else {
                System.out.println("[DEBUG] Colisão detectada, não movimenta.");
            }
        }
    }

    private boolean isDentroDaGrade(int x, int y, Interseccao[][] grade) {
        return x >= 0 && y >= 0 && x < grade.length && y < grade[0].length;
    }

    private void animar() {
        contAnim++;
        if (contAnim > 15) {
            frame = (frame + 1) % 3;
            contAnim = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!vivo)
            return;

        BufferedImage spriteAtual = null;
        switch (direcao) {
            case CIMA:
                spriteAtual = spritesCima[frame];
                break;
            case BAIXO:
                spriteAtual = spritesBaixo[frame];
                break;
            case ESQUERDA:
                spriteAtual = spritesEsquerda[frame];
                break;
            case DIREITA:
            default:
                spriteAtual = spritesDireita[frame];
                break;
        }

        if (spriteAtual != null) {
            g2d.drawImage(spriteAtual, x, y, medida, medida, null);
        }
    }

    public void morrer() {
        vivo = false;
        totalMonstros--;
        if (totalMonstros <= 0) {
            labirinto.spawnChave(this.x, this.y);
        }
    }
}
