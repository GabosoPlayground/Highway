package com.highway;

/*
The MIT License (MIT)

Copyright (c) 2015 Aditya Chatterjee

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Track Class
 */
public class Track extends JFrame implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Track.class);

    //Colors
    private static final Color BACKGROUND_COLOR = Color.decode("#4CAF50");
    private static final Color COLOR_CAR_ONE = Color.decode("#D50000");
    private static final Color COLOR_CAR_TWO = Color.decode("#00C853");
    private static final Color COLOR_ME_CAR = Color.decode("#0091EA");
    private static final Color COLOR_BOUNDARY = Color.decode("#D32F2F");
    private static final Color COLOR_TIRE = Color.decode("#212121");

    private static final Font FONT_TITLE = new Font("Trebuchet MS", Font.BOLD, 14);
    private static final Font FONT_NORMAL = new Font("Trebuchet MS", Font.PLAIN, 12);

    int x;
    int y;
    int xDirection;
    int yDirection;

    private Rectangle meCar = new Rectangle(x, y, 20, 50);

    int yy = 1;

    private Rectangle road = new Rectangle(65, 0, 285, 1000);
    private Rectangle leftBoundary = new Rectangle(45, 20, 20, 500);
    private Rectangle rightBoundary = new Rectangle(350, 20, 20, 500);

    private Rectangle carOne = new Rectangle(100, 380, 20, 30);
    private Rectangle cartTwo = new Rectangle(300, 350, 30, 10);

    private Rectangle rightKerbOne = new Rectangle(45, 90, 20, 40);
    private Rectangle rightKerbTwo = new Rectangle(350, 90, 20, 40);
    private Rectangle rightKerbThree = new Rectangle(45, 200, 20, 40);

    private Rectangle leftKerbOne = new Rectangle(350, 200, 20, 40);
    private Rectangle leftKerbTwo = new Rectangle(45, 300, 20, 40);
    private Rectangle leftKerbThree = new Rectangle(350, 300, 20, 40);

    int sd = 0;
    int s1 = -30;
    int s2 = 0;
    int w = 0;

    public Track() {
        setBackground(BACKGROUND_COLOR);
        addKeyListener(new AL());
        setTitle("Speed Up");
        setSize(510, 400);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        meCar.x = 200;
        meCar.y = 350;
        yy = 1;
    }

    private void move() {
        int cv = 2;
        int cd = 2;

        if (w % 13 == 0)
            cv = 5;
        else if (w % 17 == 0)
            cv = 2;

        if (w % 11 == 0)
            cd = 5;
        else if (w % 17 == 0)
            cd = 2;
        else if (w % 100 == 0)
            cd = 15;

        meCar.x += xDirection;
        meCar.y += yDirection;

        carOne.y += cd;
        cartTwo.y += cv;
        sd += yDirection;
        s1 += cd;
        s2 += cv;
        w++;

        if (yDirection == -3) {
            rightKerbOne.y -= yDirection;
            rightKerbThree.y -= yDirection;
            leftKerbTwo.y -= yDirection;
            rightKerbTwo.y -= yDirection;
            leftKerbOne.y -= yDirection;
            leftKerbThree.y -= yDirection;
        }

        meCar = setXandYToCar(meCar, 150);
        carOne = setXandYToCar(carOne, 140);
        cartTwo = setXandYToCar(cartTwo, 155);

        if (rightKerbOne.y >= 350)
            rightKerbOne.y = 50;
        if (rightKerbThree.y >= 350)
            rightKerbThree.y = 50;
        if (leftKerbTwo.y >= 350)
            leftKerbTwo.y = 50;
        if (rightKerbTwo.y >= 350)
            rightKerbTwo.y = 50;
        if (leftKerbOne.y >= 350)
            leftKerbOne.y = 50;
        if (leftKerbThree.y >= 350)
            leftKerbThree.y = 50;
    }

    @Override
    public void run() {
        try {
            while (true) {
                move();
                Thread.sleep(20);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public class AL extends KeyAdapter {

        private void setXDirection(int xdir) {
            xDirection = xdir;
        }

        private void setYDirection(int ydir) {
            yDirection = ydir;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT)
                setXDirection(-3);
            if (keyCode == KeyEvent.VK_RIGHT)
                setXDirection(+3);
            if (keyCode == KeyEvent.VK_UP)
                setYDirection(-3);
            if (keyCode == KeyEvent.VK_DOWN)
                setYDirection(+3);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT)
                setXDirection(0);
            if (keyCode == KeyEvent.VK_RIGHT)
                setXDirection(0);
            if (keyCode == KeyEvent.VK_UP)
                setYDirection(0);
            if (keyCode == KeyEvent.VK_DOWN)
                setYDirection(0);
        }
    }

    @Override
    public void paint(Graphics g) {
        Image image = createImage(getWidth(), getHeight());
        Graphics dbg = image.getGraphics();
        paintComponent(dbg);
        g.drawImage(image, 0, 0, this);
    }

    private void paintComponent(Graphics g) {
        //Left boundary
        g.setColor(COLOR_BOUNDARY);
        g.fillRect(leftBoundary.x, leftBoundary.y, leftBoundary.width, leftBoundary.height);
        //Right boundary
        g.setColor(COLOR_BOUNDARY);
        g.fillRect(rightBoundary.x, rightBoundary.y, rightBoundary.width, rightBoundary.height);
        //Road
        g.setColor(Color.DARK_GRAY);
        g.fillRect(road.x, road.y, road.width, road.height);
        //Lane dividers
        g.setColor(Color.YELLOW);
        g.fillRect(rightKerbOne.x + 170, rightKerbOne.y, 5, 25);
        g.fillRect(leftKerbTwo.x + 170, leftKerbTwo.y, 5, 25);
        g.fillRect(rightKerbThree.x + 170, rightKerbThree.y, 5, 25);
        //Right kerbs
        g.setColor(Color.WHITE);
        g.fillRect(rightKerbOne.x, rightKerbOne.y, rightKerbOne.width, rightKerbOne.height);
        g.fillRect(leftKerbTwo.x, leftKerbTwo.y, leftKerbTwo.width, leftKerbTwo.height);
        g.fillRect(rightKerbThree.x, rightKerbThree.y, rightKerbThree.width, rightKerbThree.height);
        //Left kerbs
        g.fillRect(leftKerbOne.x, leftKerbOne.y, leftKerbOne.width, leftKerbOne.height);
        g.fillRect(rightKerbTwo.x, rightKerbTwo.y, rightKerbTwo.width, rightKerbTwo.height);
        g.fillRect(leftKerbThree.x, leftKerbThree.y, leftKerbThree.width, leftKerbThree.height);

        //Me car
        g.setColor(COLOR_ME_CAR);
        g.fillRect(meCar.x, meCar.y, 20, 50);
        //Tires of me car
        g.setColor(COLOR_TIRE);
        g.fillRect(meCar.x + 20, meCar.y + 5, 5, 5);
        g.fillRect(meCar.x - 5, meCar.y + 5, 5, 5);
        g.fillRect(meCar.x + 20, meCar.y + 40, 5, 5);
        g.fillRect(meCar.x - 5, meCar.y + 40, 5, 5);

        //Car one
        g.setColor(COLOR_CAR_ONE);
        g.fillRect(carOne.x, meCar.y - s1 - sd, 20, 50);
        //Tires of car one
        g.setColor(COLOR_TIRE);
        g.fillRect(carOne.x + 20, meCar.y - s1 - sd + 5, 5, 5);
        g.fillRect(carOne.x - 5, meCar.y - s1 - sd + 5, 5, 5);
        g.fillRect(carOne.x + 20, meCar.y - s1 - sd + 40, 5, 5);
        g.fillRect(carOne.x - 5, meCar.y - s1 - sd + 40, 5, 5);

        //Car two
        g.setColor(COLOR_CAR_TWO);
        g.fillRect(cartTwo.x, meCar.y - s2 - sd, 20, 50);
        //Tires of car two
        g.setColor(COLOR_TIRE);
        g.fillRect(cartTwo.x + 20, meCar.y - s2 - sd + 5, 5, 5);
        g.fillRect(cartTwo.x - 5, meCar.y - s2 - sd + 5, 5, 5);
        g.fillRect(cartTwo.x + 20, meCar.y - s2 - sd + 40, 5, 5);
        g.fillRect(cartTwo.x - 5, meCar.y - s2 - sd + 40, 5, 5);

        g.setColor(Color.WHITE);
        g.setFont(FONT_TITLE);
        g.drawString("Distance travelled", 380, 170);

        g.setFont(FONT_NORMAL);
        String r1 = "Me     " + (-sd);
        g.drawString(r1, 380, 190);
        r1 = "C1     " + (s1);
        g.drawString(r1, 380, 210);
        r1 = "C2     " + (s2);
        g.drawString(r1, 380, 230);

        g.setFont(FONT_TITLE);
        r1 = "Rank";
        g.drawString(r1, 380, 250);

        g.setFont(FONT_NORMAL);
        //First place
        int m = Math.max(s1, Math.max(s2, -sd));
        r1 = "1° " + getNameFromDriver(m);
        g.drawString(r1, 380, 270);
        //Second place
        int m1 = Math.min(s1, Math.min(s2, -sd));
        r1 = "3° " + getNameFromDriver(m1);
        g.drawString(r1, 380, 310);
        //Third place
        m = s1 + s2 - sd - m - m1;
        r1 = "2° " + getNameFromDriver(m);
        g.drawString(r1, 380, 290);

        repaint();
    }

    private String getNameFromDriver(int distanceTravelled) {
        if (s1 - distanceTravelled == 0)
            return "C1";
        else if (s2 - distanceTravelled == 0)
            return "C2";
        else
            return "Me";
    }

    private Rectangle setXandYToCar(Rectangle car, int distanceY) {
        if (car.x <= 50)
            car.x = 50;
        if (car.x >= 350)
            car.x = 350;
        if (car.y <= distanceY)
            car.y = distanceY;
        if (car.y > 380)
            car.y = 380;

        return car;
    }

    public static void main(String[] args) {
        String message = "Be Ready to Fly your Car";
        int result = JOptionPane.showConfirmDialog(null, message, "Speed Up", JOptionPane.OK_CANCEL_OPTION);

        // Result equals OK
        if (result == 0) {
            Track su = new Track();
            Thread t1 = new Thread(su);
            t1.start();
        } else {
            // Result not equals OK
            System.exit(0);
        }

    }

}