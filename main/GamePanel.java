package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.*;

import mapa.Labirinto;
import mecanica.Movimentacao;
import mecanica.Teclado;
import personagens.Inimigo;
import personagens.Jogador;

public class GamePanel extends JPanel implements Runnable {

    private int tamanhoTile = 50;
    private int linhas = 12;
    private int colunas = 16;
    private Dimension screenSize = tamanhoTela(getLarguraTela(), getAlturaTela());

    private Thread gameThread;
    public EstadoJogo estado = EstadoJogo.JOGANDO;

    private Jogador jogador;

   

    private Labirinto labirinto;
    Movimentacao move;
    private Teclado tc;

    public GamePanel() {

        setPreferredSize(screenSize);
        setBackground(Color.BLACK);
        setFocusable(true);
        setDoubleBuffered(true);

        jogador = new Jogador(55, 55, tamanhoTile);
        jogador.carregarSprites();
        labirinto = new Labirinto(this);
        move = new Movimentacao();
        tc = new Teclado(move, this);

        addKeyListener(tc);
        requestFocusInWindow();
    }
    
    public Jogador getJogador() {
        return jogador;
    }

    public int getTamanhoTile() {
        return tamanhoTile;
    }

    public int getAlturaTela() {
        return linhas * tamanhoTile;
    }

    public int getLarguraTela() {
        return colunas * tamanhoTile;
    }

    public static Dimension tamanhoTela(int altura, int largura) {

        Dimension tamanhoTela = new Dimension(altura, largura);
        return tamanhoTela;
    }

    public enum EstadoJogo {
        INICIO,
        JOGANDO,
        PAUSADO,
        GAMEOVER,
        VITORIA
    }

    public void setEstadoJogo(EstadoJogo estado) {
        this.estado = estado;
    }

    public EstadoJogo getEstadoJogo() {
        return estado;
    }

    public void startGameLoop() {
        if (estado == EstadoJogo.JOGANDO) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    @Override
    public void run() {
        while (estado != EstadoJogo.GAMEOVER && estado != EstadoJogo.VITORIA) {
            if (estado == EstadoJogo.JOGANDO) {
                update();
            }
            repaint();

            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        move.update(labirinto, jogador, tc);
        for (Inimigo monstro : labirinto.getInimigo()) {
            monstro.update(jogador);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        labirinto.draw(g2d);
        jogador.draw(g2d);

        if (estado == EstadoJogo.PAUSADO) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            g2d.drawString("Jogo Pausado", getWidth() / 2 - 120, getHeight() / 2);
        }
    }

    public int getColunas() {
        return colunas;
    }

    public int getLinhas() {
        return linhas;
    }
}
