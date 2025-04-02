package mecanica;

import java.awt.event.*;

import main.GamePanel;
import main.GamePanel.EstadoJogo;

public class Teclado implements KeyListener {
    Movimentacao movimentacao;
    GamePanel p;
    public boolean cima, baixo, esquerda, direita;

    public Teclado(Movimentacao movimentacao, GamePanel p) {
        this.movimentacao = movimentacao;
        this.p = p;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                cima = true;
                break;
            case KeyEvent.VK_S:
                baixo = true;
                break;
            case KeyEvent.VK_A:
                esquerda = true;
                break;
            case KeyEvent.VK_D:
                direita = true;
                break;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (p.getEstadoJogo() == EstadoJogo.JOGANDO) {
                p.setEstadoJogo(EstadoJogo.PAUSADO);
            } else if (p.getEstadoJogo() == EstadoJogo.PAUSADO) {
                p.setEstadoJogo(EstadoJogo.JOGANDO);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                cima = false;
                break;
            case KeyEvent.VK_S:
                baixo = false;
                break;
            case KeyEvent.VK_A:
                esquerda = false;
                break;
            case KeyEvent.VK_D:
                direita = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

}
