package io.github.vkb24312.MapGenerator;

import java.awt.*;
import java.util.Random;

public class Map {
    //<editor-fold desc="Initializations">
    public Map(){
        initMethod = 0;
        size = new Dimension(300, 300);
        initCoords();
        makeMap();
    }

    public Map(long seed){
        size = new Dimension(300, 300);
        initMethod = 1;
        random.setSeed(seed);
        initCoords();
        makeMap();
    }

    public Map(Dimension size){
        initMethod = 2;
        this.size = size;
        initCoords();
        makeMap();
    }

    public Map(Dimension size, long seed){
        initMethod = 3;
        random.setSeed(seed);
        this.size = size;
        initCoords();
        makeMap();
    }

    public Map(int width, int height){
        initMethod = 4;
        size = new Dimension(width, height);
        initCoords();
        makeMap();
    }

    public Map(int width, int height, long seed){
        initMethod = 5;
        random.setSeed(seed);
        size = new Dimension(width, height);
        initCoords();
        makeMap();
    }
    //</editor-fold>

    private Random random = new Random();

    private Dimension size = new Dimension(300, 300);

    private Coord[][] coords = new Coord[size.width][size.height];

    private int initMethod;

    private void initCoords(){
        coords = new Coord[size.width][size.height];
        for (int x = 0; x < size.width; x++) {
            coords[x] = new Coord[size.height];
            for (int y = 0; y < size.height; y++) {
                coords[x][y] = new Coord();
            }
        }
    }

    private void makeMap(){
        //<editor-fold desc="Heightmap generator">
        int i = 0;
        do {
            System.out.println("Starting a new map");
            for (int x = 0; x < size.width; x++) {
                for (int y = 0; y < size.height; y++) {
                    int grad = (random.nextInt(11) - 5);
                    if (x == 0) {

                        if (y == 0) {
                            coords[x][y].setHeight(random.nextInt(255));
                            grad=0;
                        } else {
                            coords[x][y].setHeight(coords[x][y - 1].height + grad);
                        }

                    } else {

                        if (y == 0) {
                            coords[x][y].setHeight(coords[x - 1][y].height + grad);
                        } else {
                            coords[x][y].setHeight(
                                    ((coords[x - 1][y].height +
                                            coords[x][y - 1].height)
                                            / 2) + grad
                            );
                        }

                    }

                    if (coords[x][y].height > 150) i++;

                    if(coords[x][y].height > 255) coords[x][y].height = 255;
                    if(coords[x][y].height < 0) coords[x][y].height = 0;
                }
            }
            if(i<5000&&(initMethod==1||initMethod==3||initMethod==5)) System.out.println("Unsuccessful map generation, but kept it due to custom seed");
        } while (i < 5000&&!(initMethod==1||initMethod==3||initMethod==5));
        if(i>=5000) System.out.println("Successful map generation!");
        //</editor-fold>

        //<editor-fold desc="Gradient finder">
        for (int x = 0; x < size.width; x++) {
            for (int y = 0; y < size.height; y++) {
                if(x==0){
                    coords[x][y].gradl = 0;
                } else {
                    coords[x][y].gradl = coords[x][y].height-coords[x-1][y].height;
                }

                if(y==0){
                    coords[x][y].gradu = 0;
                } else{
                    coords[x][y].gradu = coords[x][y].height-coords[x][y-1].height;
                }

                if(x==size.width-1){
                    coords[x][y].gradr = 0;
                } else {
                    coords[x][y].gradr = coords[x][y].height-coords[x+1][y].height;
                }

                if(y==size.height-1){
                    coords[x][y].gradr = 0;
                } else {
                    coords[x][y].gradd = coords[x][y].height-coords[x][y+1].height;
                }
            }
        }
        //</editor-fold>
    }

    Color[][] drawMap(int mapType){
        Color[][] colors = new Color[size.width][size.height];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color[size.height];
            for (int j = 0; j < colors[i].length; j++) {
                colors[i][j] = Color.pink;
            }
        }

        //<editor-fold desc="Heightmap generator">
        if(mapType == 0){
            for (int x = 0; x < size.width; x++) {
                for (int y = 0; y < size.height; y++) {
                    colors[x][y] = new Color(coords[x][y].height, coords[x][y].height, coords[x][y].height);
                }
            }
        }
        //</editor-fold>

        //<editor-fold desc="Biome Map generator">
        else if(mapType==1){
            for (int x = 0; x < size.width; x++) {
                for (int y = 0; y < size.height; y++) {
                    colors[x][y] = coords[x][y].biomeColor();
                }
            }
        }
        //</editor-fold>

        //<editor-fold desc="Natural Map generator">
        else if(mapType==2){
            for (int x = 0; x < size.width; x++) {
                for (int y = 0; y < size.height; y++) {
                    colors[x][y] = coords[x][y].color();
                }
            }
        }
        //</editor-fold>

        return colors;
    }

    Dimension getSize(){
        return size;
    }

    Coord[][] getCoords(){
        return coords;
    }

    public static final int HEIGHTMAP = 0;

    public static final int BIOMEMAP = 1;

    public static final int NATUREMAP = 2;
}
