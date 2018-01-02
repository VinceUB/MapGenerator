package io.github.vkb24312.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main extends JPanel{

    private int gx = 0;
    private int gy = 0;
    private Color[][] gc = new Color[300][300];

    private Rectangle[][] rects = new Rectangle[300][300];

    public static void main(String[] args){
        Main main = new Main();

        //<editor-fold desc="Map setup">
        Map map;
        try {
            if (!args[0].equals("\u0000")) {
                map = new Map(Long.parseLong(args[0]));
            } else {
                map = new Map();
                System.out.println("To use a custom seed, type your seed as the argument");
            }
        } catch (NumberFormatException ignore){
            map = new Map();
            System.out.println("This program only supports numeric seeds");
            System.out.println("To use a custom seed, type your seed as the argument");
        } catch (ArrayIndexOutOfBoundsException ignore){
            map = new Map();
            System.out.println("To use a custom seed, type your seed as the argument");
        }
        //</editor-fold>

        //<editor-fold desc="JFrame generator">
        JFrame frame = new JFrame("Map Generator");
        frame.add(main);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //</editor-fold>

        //<editor-fold desc="Map drawer">
        System.out.println("Starting Map drawing");
        for (int x = 0; x < 300; x++) {
            main.gc[x] = new Color[300];
            main.rects[x] = new Rectangle[300];
            for (int y = 0; y < 300; y++) {
                main.gc[x][y] = Color.pink;
                main.rects[x][y] = new Rectangle();

                main.rects[x][y].setBounds(x, y, 1, 1);
            }
        }
        main.repaint();

        for (main.gx = 0; main.gx < 300; main.gx++) {
            for (main.gy = 0; main.gy < 300; main.gy++) {

                //main.gc = new Color(Math.abs(coords[main.gx][main.gy].height), Math.abs(coords[main.gx][main.gy].height), Math.abs(coords[main.gx][main.gy].height));
                main.gc[main.gx][main.gy] = map.coords[main.gx][main.gy].color();

                main.repaint(main.rects[main.gx][main.gy]);
            }
        }
        System.out.print("Finished Map drawing!");
        //</editor-fold>
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                g2d.setColor(gc[i][j]);
                g2d.draw(rects[i][j]);
            }
        }
    }
}


