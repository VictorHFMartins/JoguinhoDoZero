package mecanica.pathFinder;

import java.util.Objects;

public class Interseccao {
    public int x, y, distanciaReal, custoEstimado, custoTotal;
    public boolean isParede;

    public Interseccao pai;

    public Interseccao(int x, int y, boolean isParede) {
        this.x = x;
        this.y = y;
        this.distanciaReal = 0;
        this.custoEstimado = 0;
        this.custoEstimado = 0;
        this.pai = null;
        this.isParede = isParede;
    }

    public void calcularCustos(Interseccao pai, Interseccao destino) {
        this.pai = pai;
        this.distanciaReal = pai.distanciaReal + 1;
        this.custoEstimado = Math.abs(destino.x - x) + Math.abs(destino.y - y);
        this.custoTotal = distanciaReal + custoEstimado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if ((o == null) || getClass() != o.getClass())
            return false;
        Interseccao no = (Interseccao) o;
        return x == no.x && y == no.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
