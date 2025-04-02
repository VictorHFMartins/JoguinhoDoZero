package objetos;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public abstract class SuperObjects {
    GamePanel p;
    protected int x, y;
    protected int medida;
    protected boolean colisao;
    protected BufferedImage imagem;

    public SuperObjects(int x, int y, int medida) {
        this.x = x;
        this.y = y;
        this.medida = medida;
        this.colisao = false;
    }

    public void setImagem(String caminho) {
        try {
            var stream = getClass().getResourceAsStream(caminho);
            if (stream == null) {
                System.err.println("❌ Imagem não encontrada: " + caminho);
                return;
            }
            imagem = ImageIO.read(stream);
        } catch (IOException e) {
            System.err.println("❌ Erro ao carregar imagem: " + caminho);
            e.printStackTrace();
        }
    }

    public boolean isColisao() {
        return colisao;
    }

    public void setColisao(boolean colisao) {
        this.colisao = colisao;
    }

    public void setCoordanadas(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, medida, medida);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return medida;
    }

    public int getHeight() {
        return medida;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(Graphics2D g2d) {
        if (imagem != null) {
            g2d.drawImage(imagem, x, y, medida, medida, null);
        } else {
            g2d.setColor(java.awt.Color.RED);
            g2d.fillRect(x, y, medida, medida);
        }
    }
}
