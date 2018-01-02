package io.github.vkb24312.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main extends JPanel{

    private int gx = 0;
    private int gy = 0;
    private Color[][] gc = new Color[300][300];

    private Rectangle[][] rects = new Rectangle[300][300];

    public static void main(String[] args){
        try {if(args[1].equals(null)) args[1] = "1";}
        catch (ArrayIndexOutOfBoundsException ignore){
            try {
                args = new String[]{args[0], "1"};
            } catch (ArrayIndexOutOfBoundsException ignore1){
                args = new String[]{null, "1"};
            }
        }
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
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ignore){
            map = new Map();
            System.out.println("To use a custom seed, type your seed as the argument");
        }
        //</editor-fold>

        //<editor-fold desc="JFrame generator">
        JFrame frame = new JFrame("Map Generator");
        frame.setSize(300, 550);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main.setLayout(null);

        JLabel seedl = new JLabel("Type your seed (if any) below:");
        seedl.setBounds(0, 300, 300, 50);

        JTextArea seed = new JTextArea();
        seed.setBounds(0, 350, 300, 50);
        seed.setLineWrap(true);

        JButton regen = new JButton("Regenerate");
        regen.setBounds(100, 400, 100, 50);

        JButton close = new JButton("Close frame");
        close.setBounds(75, 450, 150, 50);

        main.add(seedl);
        main.add(seed);
        main.add(regen);
        main.add(close);
        frame.add(main);

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

        main.gc = map.drawMap(Map.NATUREMAP);
        main.repaint();
        System.out.println("Finished Map drawing!");
        //</editor-fold>

        final String[] arg = args;
        regen.addActionListener(e -> {
            System.out.print("\n\n\n\n\n");
            System.out.println("Making new Map");
            if(seed.getText().equals("\u0000")){
                main(new String[]{null, Integer.toString(Integer.parseInt(arg[1])+1)});
            } else {
                main(new String[]{seed.getText(), Integer.toString(Integer.parseInt(arg[1])+1)});
            }
        });

        close.addActionListener(e -> {
            if(Integer.parseInt(arg[1])==1) {
                System.out.print("\n\n\n\n\n");
                System.out.println("Closing program due to user request");
                System.out.println("Thank you for using my map generator!");
                System.exit(0);
            } else {
                frame.setVisible(false);
            }
        });
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        try {
            for (int i = 0; i < 300; i++) {
                for (int j = 0; j < 300; j++) {
                    g2d.setColor(gc[i][j]);
                    g2d.draw(rects[i][j]);
                }
            }
        } catch(NullPointerException ignore){
            for (int i = 0; i < 300; i++) {
                rects[i] = new Rectangle[300];
                for (int j = 0; j < 300; j++) {
                    rects[i][j] = new Rectangle();
                }
            }
        }
    }

}


