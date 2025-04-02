package mecanica.pathFinder;
import java.util.*;

public class PathFinder {

    public static List<Interseccao> encontrarCaminho(Interseccao[][] grade, Interseccao inicio, Interseccao fim) {
        Set<Interseccao> fechados = new HashSet<>();
        PriorityQueue<Interseccao> abertos = new PriorityQueue<>(Comparator.comparingInt(n -> n.custoTotal));

        inicio.distanciaReal = 0;
        inicio.custoEstimado = Math.abs(inicio.x - fim.x) + Math.abs(inicio.y - fim.y);
        inicio.custoTotal = inicio.distanciaReal + inicio.custoEstimado;
        abertos.add(inicio);

        while (!abertos.isEmpty()) {
            Interseccao atual = abertos.poll();
            if (atual.equals(fim)) {
                return reconstruirCaminho(atual);
            }

            fechados.add(atual);

            for (Interseccao vizinho : getVizinhos(atual, grade)) {
                if (fechados.contains(vizinho) || vizinho.isParede) continue;

                int gTentativo = atual.distanciaReal + 1;
                if (!abertos.contains(vizinho) || gTentativo < vizinho.distanciaReal) {
                    vizinho.pai = atual;
                    vizinho.distanciaReal = gTentativo;
                    vizinho.custoEstimado = Math.abs(vizinho.x - fim.x) + Math.abs(vizinho.y - fim.y);
                    vizinho.custoTotal = vizinho.distanciaReal + vizinho.custoEstimado;
                
                    if (!abertos.contains(vizinho)) abertos.add(vizinho);
                }
            }
        }
        return Collections.emptyList(); 
    }

    private static List<Interseccao> reconstruirCaminho(Interseccao fim) {
        List<Interseccao> caminho = new ArrayList<>();
        Interseccao atual = fim;
    
        Set<Interseccao> visitados = new HashSet<>();
    
        while (atual != null) {
            if (visitados.contains(atual)) {
                System.out.println("⚠️ Detected loop in path reconstruction!");
                break;
            }
    
            caminho.add(atual);
            visitados.add(atual);
            atual = atual.pai;
        }
    
        Collections.reverse(caminho);
        return caminho;
    }

    private static List<Interseccao> getVizinhos(Interseccao no, Interseccao[][] grade) {
        List<Interseccao> vizinhos = new ArrayList<>();
        int linha = no.x;
        int coluna = no.y;

        if (linha > 0) vizinhos.add(grade[linha - 1][coluna]);       
        if (linha < grade.length - 1) vizinhos.add(grade[linha + 1][coluna]); 
        if (coluna > 0) vizinhos.add(grade[linha][coluna - 1]);     
        if (coluna < grade[0].length - 1) vizinhos.add(grade[linha][coluna + 1]); 

        return vizinhos;
    }
}
