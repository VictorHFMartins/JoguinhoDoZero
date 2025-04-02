package personagens;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import objetos.SuperObjects;

public class Jogador extends SuperObjects {
    GamePanel p;
    private BufferedImage[] spritesCima = new BufferedImage[3];
    private BufferedImage[] spritesBaixo = new BufferedImage[3];
    private BufferedImage[] spritesEsquerda = new BufferedImage[3];
    private BufferedImage[] spritesDireita = new BufferedImage[3];
    private String direcao = "baixo";
    private int animFrame = 0;
    private int animDelay = 10;
    private int animCount = 0;
    private boolean andando = false;

    private int speed = 4;
    private int key = 0;

    public int getKey() {
        return key;
    }

    public void setKey() {
        key++;
    }

    public void resetKey() {
        key = 0;
    }

    public boolean hasKey() {
        if (key > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Jogador(int x, int y, int medida) {
        super(x, y, medida);
    }

    public void carregarSprites() {
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

    public void atualizarDirecao(boolean cima, boolean baixo, boolean esquerda, boolean direita) {
        andando = cima || baixo || esquerda || direita;

        if (cima)
            direcao = "cima";
        else if (baixo)
            direcao = "baixo";
        else if (esquerda)
            direcao = "esquerda";
        else if (direita)
            direcao = "direita";
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setPlayerPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Dimension getPlayerPosition() {
        return new Dimension(x, y);
    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage[] atual = spritesBaixo;

        switch (direcao) {
            case "cima":
                atual = spritesCima;
                break;
            case "baixo":
                atual = spritesBaixo;
                break;
            case "esquerda":
                atual = spritesEsquerda;
                break;
            case "direita":
                atual = spritesDireita;
                break;
        }

        if (andando) {
            animCount++;
            if (animCount >= animDelay) {
                animCount = 0;
                animFrame = (animFrame + 1) % 3;
            }
        } else {
            animFrame = 0;
        }

        g2d.drawImage(atual[animFrame], x, y, medida, medida, null);
    }
}
