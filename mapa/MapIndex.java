package mapa;

import java.util.ArrayList;

public class MapIndex {
    public ArrayList<String> mapas = new ArrayList<>();
    public ArrayList<String> objetos = new ArrayList<>();

    public MapIndex() {
        initMap();
    }

    public void initMap() {
        mapas.add("/res/mapas/Map01.txt");
        objetos.add("/res/mapObjetos/Map01.txt");
        mapas.add("/res/mapas/Map02.txt");
        objetos.add("/res/mapObjetos/Map02.txt");
        mapas.add("/res/mapas/Map03.txt");
        objetos.add("/res/mapObjetos/Map03.txt");
        mapas.add("/res/mapas/Map04.txt");
        objetos.add("/res/mapObjetos/Map04.txt");
        mapas.add("/res/mapas/Map05.txt");
        objetos.add("/res/mapObjetos/Map05.txt");
        mapas.add("/res/mapas/Map06.txt");
        objetos.add("/res/mapObjetos/Map06.txt");
        mapas.add("/res/mapas/Map07.txt");
        objetos.add("/res/mapObjetos/Map07.txt");
        mapas.add("/res/mapas/Map08.txt");
        objetos.add("/res/mapObjetos/Map08.txt");
        mapas.add("/res/mapas/Map09.txt");
        objetos.add("/res/mapObjetos/Map09.txt");
        mapas.add("/res/mapas/Map10.txt");
        objetos.add("/res/mapObjetos/Map10.txt");
    }

    public String getMapas(int num) {
        if (num >= 0 && num < mapas.size()) {
            return mapas.get(num);
        }
        return null;
    }

    public String getObjetos(int num) {
        if (num >= 0 && num < objetos.size())
            return objetos.get(num);
        return null;
    }
}
