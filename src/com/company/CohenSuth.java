package com.company;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class CohenSuth extends JFrame implements ActionListener {

    private static int width = 800;
    private static int height = 600;

    private static int Xv_min = 100;
    private static int Xv_max = 300;
    private static int Yv_min = 100;
    private static int Yv_max = 300;

    private static int x0;
    private static int y0;
    private static int x1;
    private static int y1;

    private static int x0n;
    private static int y0n;
    private static int x1n;
    private static int y1n;

    private static boolean draw2 = false;

    public CohenSuth() {
        super("Cohen-Sutherland LineClipping");

        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));
        this.setTitle("Drawing tings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    void draw(Graphics g) {

        super.paintComponents(g);
        Graphics2D graph2 = (Graphics2D) g;
        //graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape rootRect = new Rectangle2D.Float(Xv_min, height - Yv_max, Xv_max - Xv_min, Yv_max - Yv_min);
        Shape line = new Line2D.Float(x0, height - y0, x1, height - y1);
        Shape line2 = new Line2D.Float(x0n, height - y0n, x1n, height - y1n);
        graph2.setColor(Color.BLACK);
        graph2.draw(rootRect);
        graph2.setColor(Color.BLUE);
        graph2.draw(line);
        graph2.setColor(Color.RED);
        if(draw2)
        graph2.draw(line2);


    }

    public void paint(Graphics g) {

        draw(g);
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nEnter The X_min, X_max, Y_min, and Y_max Coordinates of the clipping window/Viewport:" +
                "\n\ninput the coordinates in order mentioned above and press enter after each.");
        System.out.println("\nEnter X_min of the clipping window/Viewport (MUST BE GREATER THAN 0):");
        Xv_min = Integer.parseInt(br.readLine());
        System.out.println("[+] X_min Added\n\nEnter X_max of the clipping window/Viewport (MUST BE LESS THAN " + width + "):");
        Xv_max = Integer.parseInt(br.readLine());
        System.out.println("[+] X_max Added\n\nEnter Y_min of the clipping window/Viewport (MUST BE GREATER THAN " + 0 + "):");
        Yv_min = Integer.parseInt(br.readLine());
        System.out.println("[+] Y_min Added\n\nEnter Y_max of the clipping window/Viewport (MUST BE LESS THAN " + height + "):");
        Yv_max = Integer.parseInt(br.readLine());
        System.out.println("[+] Y_max Added\n");

        System.out.println("\nEnter the end-points of the line in order x0, y0, x1, and y1");
        x0 = Integer.parseInt(br.readLine());
        x0n = x0;
        System.out.println("[+] x0 added\n\nEnter y0");
        y0 = Integer.parseInt(br.readLine());
        y0n = y0;
        System.out.println("[+] y0 added\n\nEnter x1");
        x1 = Integer.parseInt(br.readLine());
        x1n = x1;
        System.out.println("[+] x1 added\n\nEnter y1");
        y1 = Integer.parseInt(br.readLine());
        y1n = y1;
        System.out.println("[+] y1 added\n\n");

        double m = (y1 - y0) / (x1 - x0);

        int bit11 = zeroOrOne(y0 - Yv_max);
        int bit12 = zeroOrOne(Yv_min - y0);
        int bit13 = zeroOrOne(x0 - Xv_max);
        int bit14 = zeroOrOne(Xv_min - x0);

        int bit21 = zeroOrOne(y1 - Yv_max);
        int bit22 = zeroOrOne(Yv_min - y1);
        int bit23 = zeroOrOne(x1 - Xv_max);
        int bit24 = zeroOrOne(Xv_min - x1);

        int p_one = (int) (bit11 * Math.pow(2, 3) + bit12 * Math.pow(2, 2) + bit13 * Math.pow(2, 1) + bit14 * Math.pow(2, 0));
        int p_two = (int) (bit21 * Math.pow(2, 3) + bit22 * Math.pow(2, 2) + bit23 * Math.pow(2, 1) + bit24 * Math.pow(2, 0));

        if (p_one == 0 && p_two == 0) System.out.println("The whole line is inside the clipping window");
        else if ((p_one & p_two) == 0) {
            draw2 = true;
            System.out.println("Part of the line is inside the clipping window!");
            if (bit11 == 1) {
                y0n = Yv_max;
                x0n = (int) (x0 + (1 / m) * (y0n - y0));
            }
            if (bit12 == 1) {
                y0n = Yv_min;
                x0n = (int) (x0 + (1 / m) * (y0n - y0));
            }
            if (bit13 == 1) {
                x0n = Xv_max;
                y0n = (int) (y0 + m * (x0n - x0));
            }
            if (bit14 == 1) {
                x0n = Xv_min;
                y0n = (int) (y0 + m * (x0n - x0));
            }

            if (bit21 == 1) {
                y1n = Yv_max;
                x1n = (int) (x1 + (1 / m) * (y1n - y1));
            }
            if (bit22 == 1) {
                y1n = Yv_min;
                x1n = (int) (x1 + (1 / m) * (y1n - y1));
            }
            if (bit23 == 1) {
                x1n = Xv_max;
                y1n = (int) (y1 + m * (x1n - x1));
            }
            if (bit24 == 1) {
                x1n = Xv_min;
                y1n = (int) (y1 + m * (x1n - x1));
            }
        }
        else System.out.println("The entire line is outside the clipping window :(");





        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CohenSuth();
            }
        });
    }

    static int zeroOrOne(int n) {
        if (n > 0) {
            return 1;
        } else
            return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

}