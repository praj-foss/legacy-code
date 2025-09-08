package game;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class BouncingBall2 extends MIDlet {
    final WhiteCanvas canvas = new WhiteCanvas();
    final int FPS = 18;

    void gameLoop() {
        final float MSPF = 1000f/FPS;
        long frameEnd = System.currentTimeMillis();
        while (true) {
            canvas.clear();
            canvas.bounceBall();
            canvas.moveBall(MSPF);
            canvas.drawBall();
            canvas.flushGraphics();
            frameEnd += (long) MSPF;
            long timeLeft = frameEnd - System.currentTimeMillis();
            if (timeLeft > 0) {
                try {
                    Thread.sleep(timeLeft);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    protected void startApp() throws MIDletStateChangeException {
        Display.getDisplay(this).setCurrent(canvas);
        new Thread(new Runnable() {
            public void run() {
                gameLoop();
            }
        }).start();
    }

    protected void pauseApp() { }

    protected void destroyApp(boolean b) throws MIDletStateChangeException { }

    static class WhiteCanvas extends GameCanvas {
        private Graphics g;

        WhiteCanvas() {
            super(true);
            setFullScreenMode(true);
            g = getGraphics();
        }

        void clear() {
            g.setColor(0xffffff);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        float yPos = 0, yVel = 0.1f;
        float xPos = 0, xVel = 0.1f;
        int dm = 40;
        void drawBall() {
            g.setColor(0xff0000);
            g.fillRoundRect((int)xPos, (int)yPos, dm, dm, dm, dm);
        }

        void moveBall(float t) {
            yPos += yVel * t;
            xPos += xVel * t;
        }

        void bounceBall() {
            if (yPos < 0 || yPos > getHeight() - dm) {
                yVel = -yVel;
            }
            if (xPos < 0 || xPos > getWidth() - dm) {
                xVel = -xVel;
            }
        }
    }
}
