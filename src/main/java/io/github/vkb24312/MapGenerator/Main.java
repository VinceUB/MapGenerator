package io.github.vkb24312.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

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
            if (!args[0].equals("\u0000")) {
                r.setSeed(Long.parseLong(args[0]));
            }
        } catch (ArrayIndexOutOfBoundsException ignore) {
            System.out.println("To use a custom seed, type the seed as an argument");
        }
        //</editor-fold>

        //<editor-fold desc="Heightmap generator">
        int i = 0;
        do {
            System.out.println("Starting a new map");
            for (int x = 0; x < 300; x++) {
                coords[x] = new coord[300];
                for (int y = 0; y < 300; y++) {
                    int grad = (r.nextInt(11) - 5);
                    coords[x][y] = new coord();
                    if (x == 0) {

                        if (y == 0) {
                            coords[x][y].setHeight(r.nextInt(255));
                            grad = 0;
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

                    coords[x][y].grad = grad;

                    if (coords[x][y].height > 150) i++;
                }
            }
            if(i<5000&&!args[0].equals("\u0000")) System.out.println("Unsuccessful map generation, but kept it due to custom seed");
        } while (i < 5000&&args[0].equals("\u0000"));
        if(i>=5000) System.out.println("Successful map generation!");
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
        for (main.gx = 0; main.gx < 300; main.gx++) {
            for (main.gy = 0; main.gy < 300; main.gy++) {

                //main.gc = new Color(Math.abs(coords[main.gx][main.gy].height), Math.abs(coords[main.gx][main.gy].height), Math.abs(coords[main.gx][main.gy].height));
                main.gc = coords[main.gx][main.gy].color();
                if(!(coords[main.gx][main.gy].color()==Color.blue)) i++;

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


