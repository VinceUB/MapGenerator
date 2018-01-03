package io.github.vkb24312.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main extends JPanel{
    private Dimension gs = new Dimension(300, 300);

    private int gx = 0;
    private int gy = 0;
    private Color[][] gc = new Color[gs.width][gs.height];

    private Rectangle[][] rects = new Rectangle[gs.width][gs.height];

    public static void main(String[] args){
        try {if(args[1].equals("Hi from Vince")) args[1] = "1";}
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
                map = new Map(main.gs, Long.parseLong(args[0]));
            } else {
                map = new Map(main.gs);
                System.out.println("To use a custom seed, type your seed as the argument");
            }
        } catch (NumberFormatException ignore){
            map = new Map(main.gs);
            System.out.println("This program only supports numeric seeds");
            System.out.println("To use a custom seed, type your seed as the argument");
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ignore){
            System.out.println("To use a custom seed, type your seed as the argument");
            map = new Map(main.gs);
        }
        //</editor-fold>

        //<editor-fold desc="JFrame generator">
        JFrame frame = new JFrame("Map Generator");
        frame.setSize(main.gs.width, main.gs.height+250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel headPanel = new JPanel(new CardLayout());

        main.setLayout(null);

        JLabel seedl = new JLabel("Type your seed (if any) below:");
        seedl.setBounds(0, main.gs.height, main.gs.width, 50);

        JTextArea seed = new JTextArea();
        seed.setBounds(0, main.gs.height+50, main.gs.width, 50);
        seed.setLineWrap(true);

        JButton regen = new JButton("Regenerate");
        regen.setBounds(main.gs.width/2-50, main.gs.height+100, 100, 50);

        JButton close = new JButton("Close frame");
        close.setBounds(main.gs.width/2-75, main.gs.height+150, 150, 50);

        main.add(seedl);
        main.add(seed);
        main.add(regen);
        main.add(close);
        frame.add(main);

        frame.setVisible(true);

        //</editor-fold>

        //<editor-fold desc="Map drawer">
        System.out.println("Starting Map drawing");
        for (int x = 0; x < main.gs.width; x++) {
            main.gc[x] = new Color[main.gs.height];
            main.rects[x] = new Rectangle[main.gs.height];
            for (int y = 0; y < main.gs.height; y++) {
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
            for (int i = 0; i < gs.width; i++) {
                for (int j = 0; j < gs.height; j++) {
                    g2d.setColor(gc[i][j]);
                    g2d.draw(rects[i][j]);
                }
            }
        } catch(NullPointerException ignore){
            for (int i = 0; i < gs.width; i++) {
                rects[i] = new Rectangle[gs.height];
                for (int j = 0; j < gs.height; j++) {
                    rects[i][j] = new Rectangle();
                }
            }
            repaint();
        }
    }

}


