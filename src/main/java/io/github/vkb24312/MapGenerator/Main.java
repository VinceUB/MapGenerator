package io.github.vkb24312.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main extends JPanel{

    private int gx = 0;
    private int gy = 0;
    private Color gc = new Color(new Random().nextInt(0xFFFFFF));

    public static void main(String[] args) throws InterruptedException{
        coord[][] coords = new coord[300][300];
        Main main = new Main();

        //<editor-fold desc="Random r setup">
        Random r = new Random();
        try {
            if (!args[1].equals("\u0000")) {
                r.setSeed(Long.parseLong(args[1]));
            }
        } catch (ArrayIndexOutOfBoundsException ignore) {
            System.out.print("To use a custom seed, type the seed as an argument");
        }
        //</editor-fold>

        //<editor-fold desc="Heightmap generator">
        int i = 0;
        while(i<5000) {
            for (int x = 0; x < 300; x++) {
                coords[x] = new coord[300];
                for (int y = 0; y < 300; y++) {
                    coords[x][y] = new coord();
                    if (x == 0) {

                        if (y == 0) {
                            coords[x][y].setHeight(r.nextInt(255));
                        } else {
                            coords[x][y].setHeight(coords[x][y - 1].height + (r.nextInt(10) - 5));
                        }

                    } else {

                        if (y == 0) {
                            coords[x][y].setHeight(coords[x - 1][y].height + (r.nextInt(10) - 5));
                        } else {
                            coords[x][y].setHeight(
                                    (coords[x - 1][y].height + r.nextInt(10) - 5 +
                                            coords[x][y - 1].height + r.nextInt(10) - 5)
                                            / 2
                            );
                        }
                    }

                    if (coords[x][y].height > 150) i++;
                }
            }
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
        for (main.gx = 0; main.gx < 300; main.gx++) {
            for (main.gy = 0; main.gy < 300; main.gy++) {

                //main.gc = new Color(Math.abs(coords[main.gx][main.gy].height), Math.abs(coords[main.gx][main.gy].height), Math.abs(coords[main.gx][main.gy].height));
                main.gc = coords[main.gx][main.gy].color();
                if(!(coords[main.gx][main.gy].color()==Color.blue)) i++;

                main.repaint();
                Thread.sleep(1);
            }
        }
        //</editor-fold>
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(gc);
        g2d.drawRect(gx, gy, 1, 1);
    }
}


