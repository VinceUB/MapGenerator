package io.github.vkb24312.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main extends JPanel{

    private int gx = 0;
    private int gy = 0;
    private Color gc = new Color(new Random().nextInt(0xFFFFFF));

    public static void main(String[] args) throws InterruptedException{
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
        int i=0;
        System.out.println("Starting Map drawing");
        for (main.gx = 0; main.gx < 300; main.gx++) {
            for (main.gy = 0; main.gy < 300; main.gy++) {

                //main.gc = new Color(Math.abs(coords[main.gx][main.gy].height), Math.abs(coords[main.gx][main.gy].height), Math.abs(coords[main.gx][main.gy].height));
                main.gc = map.coords[main.gx][main.gy].color();
                if(!(map.coords[main.gx][main.gy].color()==Color.blue)) i++;

                main.repaint();
                Thread.sleep(1);
            }
        }
        System.out.print("Finished Map drawing!");
        //</editor-fold>
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(gc);
        g2d.drawRect(gx, gy, 1, 1);
    }
}


