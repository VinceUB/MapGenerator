package io.github.vkb24312.MapGenerator;

import java.awt.*;

public class Coord {
    public Coord(){
        this.height = 0;
        this.biome = 0;
    }

    int height;

    int biome;

    int gradl;
    int gradr;
    int gradu;
    int gradd;

    Color color(){
        //50<x<200
        double height1 = height;
        if(height1<50){
            return Color.blue;
        } else if(height1>200){
            return Color.white;
        } else if (height1>150){
            return new Color((int) Math.round(((height1-51)/150)*255), (int) Math.round(((height1-50)/150)*255), (int) Math.round(((height1-50)/150)*255));
        } else {
            return new Color(0, 149, 38);
        }
        /*
        if(height1<50){
            return Color.blue;
        } else if(grad>2||grad<-2){
            return new Color((int) Math.round(((height1-51)/255)*255), (int) Math.round(((height1-50)/255)*255), (int) Math.round(((height1-50)/255)*255));
        } else if (height1>200){
            return Color.white;
        } else {
            return new Color(0, 149, 38);
        }
        */

    }

    void setHeight(int height){
        this.height = height;
    }

    public static final int BIOME_PLAINS = 0;

    public static final int BIOME_DESSERt = 1;


}
