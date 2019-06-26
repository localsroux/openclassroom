package vue;

import controleur.Controleur;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.Dessinateur;
import model.Modele;

public class VueDessin
        extends JPanel
        implements Dessinateur, MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {

    private Controleur controleur;
    private List<VuePrimitive> vues;
    private int posXSouris;
    private int posYSouris;
    private Image cursorCrayon;
    private Image cursorDefault;
    private int x0;
    private int y0;

    public VueDessin(Controleur c) {
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        this.posXSouris = 0;
        this.posYSouris = 0;
        this.x0 = 0;
        this.y0 = 0;
        this.controleur = c;
        setBackground(Color.WHITE);
        this.vues = new ArrayList();

        Toolkit tk = Toolkit.getDefaultToolkit();
        try {
            this.cursorCrayon = ImageIO.read(getClass().getResource("curseurCrayon.png"));
            this.cursorDefault = ImageIO.read(getClass().getResource("curseurMain.png"));
        } catch (IOException ex) {
            Logger.getLogger(VueDessin.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCursor(this.controleur.getEtat().equals("SUPPRESSION - DEPLACEMENT") ? tk.createCustomCursor(this.cursorDefault, new Point(0, 0), "ToolTipText") : tk.createCustomCursor(this.cursorCrayon, new Point(0, 0), "ToolTipText"));

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        requestFocus();
    }

    public void update(Modele m) {
        m.dessine(this);
        Toolkit tk = Toolkit.getDefaultToolkit();
        setCursor(this.controleur.getEtat().equals("SUPPRESSION - DEPLACEMENT") ? tk.createCustomCursor(this.cursorDefault, new Point(0, 0), "ToolTipText") : tk.createCustomCursor(this.cursorCrayon, new Point(0, 0), "ToolTipText"));
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (VuePrimitive v : this.vues) {
            v.paint(g, getHeight(), getWidth(), this.posXSouris, this.posYSouris, this.x0, this.y0);
        }
    }

    @Override
    public void dessinePoint(int px, int py) {
        this.vues.add(new VuePoint(px, py));
    }

    @Override
    public void dessineCercle(int px, int py, int rayon) {
        this.vues.add(new VueCercle(px, py, rayon));
    }

    @Override
    public void dessineSegment(int ax, int ay, int bx, int by) {
        this.vues.add(new VueSegment(ax, ay, bx, by));
    }

    @Override
    public void dessineDroite(int ax, int ay, int bx, int by) {
        this.vues.add(new VueDroite(ax, ay, bx, by));
    }

    @Override
    public void dessineDemiDroite(int ax, int ay, int bx, int by) {
        this.vues.add(new VueDemiDroite(ax, ay, bx, by));
    }

    @Override
    public void dessineVecteur(int ax, int ay, int bx, int by) {
        this.vues.add(new VueVecteur(ax, ay, bx, by));
    }

    @Override
    public void blanchit() {
        this.vues.clear();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.controleur.cliqueVueDessin(e.getX() + this.x0, e.getY() + this.y0);
        requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.controleur.selectionne(e.getX() + this.x0, e.getY() + this.y0);
        requestFocus();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.controleur.relache(e.getX() + this.x0, e.getY() + this.y0);
        requestFocus();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!this.controleur.bouge(e.getX() + this.x0, e.getY() + this.y0)) {
            this.x0 += this.posXSouris - e.getX();
            this.y0 += this.posYSouris - e.getY();
        }
        this.posXSouris = e.getX();
        this.posYSouris = e.getY();
        requestFocus();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.posXSouris = e.getX();
        this.posYSouris = e.getY();
        repaint();
        requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        moveOrigine(e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        moveOrigine(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void moveOrigine(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                this.x0 -= 1;
                break;
            case KeyEvent.VK_DOWN:
                this.y0 += 1;
                break;
            case KeyEvent.VK_RIGHT:
                this.x0 += 1;
                break;
            case KeyEvent.VK_UP:
                this.y0 -= 1;
        }
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int nb = e.getWheelRotation();
        int nbAbs = nb < 0 ? -nb : nb;
        for (int i = 0; i < nbAbs; i++) {
            this.y0 = (nb < 0 ? this.y0 - 2 : this.y0 + 2);
            repaint();
        }
    }

    public void centrer() {
        this.x0 = 0;
        this.y0 = 0;
    }

    public void saveAsImage(String path)
            throws IOException {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), 4);
        Graphics g = image.getGraphics();
        paintComponent(g);
        String format = "JPG";
        File f = new File(path);
        ImageIO.write(image, format, f);
    }

    private static interface VuePrimitive {

        public void paint(Graphics g, int height, int length, int posXsouris, int posYsouris, int x0, int y0);

        public boolean isOn(int px, int py, int x0, int y0);
    }

    private static class VuePoint
            implements VuePrimitive {

        private int x;
        private int y;

        public VuePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void paint(Graphics g, int height, int length, int posXsouris, int posYsouris, int x0, int y0) {
            Color c = g.getColor();
            g.setColor(isOn(posXsouris, posYsouris, x0, y0) ? Color.GREEN : Color.RED);
            g.fillOval(this.x - x0 - 3, this.y - y0 - 3, 6, 6);
            g.setColor(c);
        }

        public boolean isOn(int px, int py, int x0, int y0) {
            px += x0;
            py += y0;
            return Math.abs(Math.sqrt((this.x - px) * (this.x - px) + (this.y - py) * (this.y - py))) < 10;
        }
    }

    private static class VueCercle
            implements VueDessin.VuePrimitive {

        private int x;
        private int y;
        private int rayon;

        public VueCercle(int x, int y, int r) {
            this.x = x;
            this.y = y;
            this.rayon = r;
        }

        public void paint(Graphics g, int height, int length, int posXsouris, int posYsouris, int x0, int y0) {
            Color c = g.getColor();
            g.setColor(isOn(posXsouris, posYsouris, x0, y0) ? Color.GREEN : Color.BLUE);
            g.drawOval(this.x - this.rayon - x0, this.y - this.rayon - y0, 2 * this.rayon, 2 * this.rayon);
            g.setColor(c);
        }

        public boolean isOn(int posXsouris, int posYsouris, int x0, int y0) {
            return Math.abs(this.rayon - Math.sqrt((this.x - posXsouris - x0) * (this.x - posXsouris - x0) + (this.y - posYsouris - y0) * (this.y - posYsouris - y0))) < 10;
        }
    }

    private static class VueSegment
            implements VueDessin.VuePrimitive {

        private int ax;
        private int ay;
        private int bx;
        private int by;

        public VueSegment(int ax, int ay, int bx, int by) {
            this.ax = ax;
            this.bx = bx;
            this.ay = ay;
            this.by = by;
        }

        public void paint(Graphics g, int height, int length, int posXsouris, int posYsouris, int x0, int y0) {
            Color c = g.getColor();
            g.setColor(isOn(posXsouris, posYsouris, x0, y0) ? Color.GREEN : Color.BLUE);
            g.drawLine(this.ax - x0, this.ay - y0, this.bx - x0, this.by - y0);
            g.setColor(c);
        }

        public boolean isOn(int posXsouris, int posYsouris, int x0, int y0) {
            posXsouris += x0;
            posYsouris += y0;

            double dMA = Math.sqrt((posXsouris - this.ax) * (posXsouris - this.ax) + (posYsouris - this.ay) * (posYsouris - this.ay));
            double dMB = Math.sqrt((posXsouris - this.bx) * (posXsouris - this.bx) + (posYsouris - this.by) * (posYsouris - this.by));
            double dAB = Math.sqrt((this.bx - this.ax) * (this.bx - this.ax) + (this.by - this.ay) * (this.by - this.ay));

            return dMA + dMB < 2 * Math.sqrt(dAB * dAB / 4 + 100);
        }
    }

    private static class VueDroite
            implements VueDessin.VuePrimitive {

        private int ax;
        private int ay;
        private int bx;
        private int by;

        public VueDroite(int ax, int ay, int bx, int by) {
            this.ax = ax;
            this.bx = bx;
            this.ay = ay;
            this.by = by;
        }

        public void paint(Graphics g, int height, int length, int posXsouris, int posYsouris, int x0, int y0) {
            Color c = g.getColor();
            g.setColor(isOn(posXsouris, posYsouris, x0, y0) ? Color.GREEN : Color.BLUE);
            this.ax -= x0;
            this.bx -= x0;
            this.ay -= y0;
            this.by -= y0;
            if (this.ax == this.bx) {
                g.drawLine(this.ax, 0, this.bx, height);
            } else if (this.ay == this.by) {
                g.drawLine(0, this.ay, length, this.by);
            } else {
                double m = ((double) this.by - (double) this.ay) / ((double) this.bx - (double) this.ax);
                double p = (double) this.ay - m * (double) this.ax;
                int dx = 0;
                int dy = 0;
                int fx = 0;
                int fy = 0;
                if (m < 0.0D) {
                    if (p > height) {
                        dy = height;
                        dx = (int) ((height - p) / m);
                    } else {
                        dx = 0;
                        dy = (int) p;
                    }
                    if (m * length + p < 0.0D) {
                        fy = 0;
                        fx = (int) (-p / m);
                    } else {
                        fx = length;
                        fy = (int) (m * length + p);
                    }
                } else {
                    if (p < 0.0D) {
                        dy = 0;
                        dx = (int) (-p / m);
                    } else {
                        dy = (int) p;
                        dx = 0;
                    }
                    if (m * length + p > height) {
                        fy = height;
                        fx = (int) ((height - p) / m);
                    } else {
                        fx = length;
                        fy = (int) (m * length + p);
                    }
                }
                g.drawLine(dx, dy, fx, fy);
            }
            g.setColor(c);
            this.ax += x0;
            this.bx += x0;
            this.ay += y0;
            this.by += y0;
        }

        public boolean isOn(int posXsouris, int posYsouris, int x0, int y0) {
            posXsouris += x0;
            posYsouris += y0;
            double AB = Math.sqrt((this.ax - this.bx) * (this.ax - this.bx) + (this.ay - this.by) * (this.ay - this.by));
            double AM = Math.sqrt((this.ax - posXsouris) * (this.ax - posXsouris) + (this.ay - posYsouris) * (this.ay - posYsouris));
            double MB = Math.sqrt((posXsouris - this.bx) * (posXsouris - this.bx) + (posYsouris - this.by) * (posYsouris - this.by));
            double s = (AB + AM + MB) / 2.0D;
            double aire = Math.sqrt(s * (s - AB) * (s - MB) * (s - AM));
            double dist = 2.0D * aire / AB;
            return dist < 10.0D;
        }
    }

    private static class VueVecteur
            implements VueDessin.VuePrimitive {

        private int ax;
        private int ay;
        private int bx;
        private int by;

        public VueVecteur(int ax, int ay, int bx, int by) {
            this.ax = ax;
            this.bx = bx;
            this.ay = ay;
            this.by = by;
        }

        public void paint(Graphics g, int height, int length, int posXsouris, int posYsouris, int x0, int y0) {
            Color c = g.getColor();
            g.setColor(isOn(posXsouris, posYsouris, x0, y0) ? Color.GREEN : Color.BLUE);
            this.ax -= x0;
            this.bx -= x0;
            this.ay -= y0;
            this.by -= y0;

            g.drawLine(this.ax, this.ay, this.bx, this.by);
            g.drawOval(this.bx - 10, this.by - 10, 20, 20);
            g.setColor(c);
            this.ax += x0;
            this.bx += x0;
            this.ay += y0;
            this.by += y0;
        }

        public boolean isOn(int posXsouris, int posYsouris, int x0, int y0) {
            posXsouris += x0;
            posYsouris += y0;
            double dMA = Math.sqrt((posXsouris - this.ax) * (posXsouris - this.ax) + (posYsouris - this.ay) * (posYsouris - this.ay));
            double dMB = Math.sqrt((posXsouris - this.bx) * (posXsouris - this.bx) + (posYsouris - this.by) * (posYsouris - this.by));
            double dAB = Math.sqrt((this.bx - this.ax) * (this.bx - this.ax) + (this.by - this.ay) * (this.by - this.ay));
            return dMA + dMB < 2 * Math.sqrt(dAB * dAB / 4 + 100);
        }
    }

    private static class VueDemiDroite
            implements VueDessin.VuePrimitive {

        private int ax;
        private int ay;
        private int bx;
        private int by;

        public VueDemiDroite(int ax, int ay, int bx, int by) {
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
        }

        public void paint(Graphics g, int height, int length, int posXsouris, int posYsouris, int x0, int y0) {
            Color c = g.getColor();
            g.setColor(isOn(posXsouris, posYsouris, x0, y0) ? Color.GREEN : Color.BLUE);
            this.ax -= x0;
            this.bx -= x0;
            this.ay -= y0;
            this.by -= y0;
            if (this.ax == this.bx) {
                if (this.ay < this.by) {
                    g.drawLine(this.ax, this.ay, this.bx, height);
                } else {
                    g.drawLine(this.ax, this.ay, this.bx, 0);
                }
            } else if (this.ay == this.by) {
                if (this.ax < this.bx) {
                    g.drawLine(this.ax, this.ay, length, this.by);
                } else {
                    g.drawLine(this.ax, this.ay, 0, this.by);
                }
            } else {
                double m = ((double) this.by - (double) this.ay) / ((double) this.bx - (double) this.ax);
                double p = (double) this.ay - m * (double) this.ax;
                int dx = this.ax;
                int dy = this.ay;
                int fx = 0;
                int fy = 0;
                if (m > 0) {
                    if (this.bx > this.ax) {
                        fx = length;
                        fy = (int) (m * length + p);
                        if (fy > height) {
                            fy = height;
                            fx = (int) ((height - p) / m);
                        }
                    } else {
                        fx = 0;
                        fy = (int) p;
                        if (fy < 0) {
                            fy = 0;
                            fx = (int) (-p / m);
                        }
                    }
                } else if (this.bx > this.ax) {
                    fx = length;
                    fy = (int) (m * length + p);
                    if (fy < 0) {
                        fy = 0;
                        fx = (int) (-p / m);
                    }
                } else {
                    fx = 0;
                    fy = (int) p;
                    if (fy > height) {
                        fy = height;
                        fx = (int) ((height - p) / m);
                    }
                }
                g.drawLine(dx, dy, fx, fy);
            }
            g.setColor(c);
            this.ax += x0;
            this.bx += x0;
            this.ay += y0;
            this.by += y0;
        }

        public boolean isOn(int posXsouris, int posYsouris, int x0, int y0) {
            posXsouris += x0;
            posYsouris += y0;
            double AB = Math.sqrt((this.ax - this.bx) * (this.ax - this.bx) + (this.ay - this.by) * (this.ay - this.by));
            double AM = Math.sqrt((this.ax - posXsouris) * (this.ax - posXsouris) + (this.ay - posYsouris) * (this.ay - posYsouris));
            double MB = Math.sqrt((posXsouris - this.bx) * (posXsouris - this.bx) + (posYsouris - this.by) * (posYsouris - this.by));
            double s = (AB + AM + MB) / 2.0D;
            double aire = Math.sqrt(s * (s - AB) * (s - MB) * (s - AM));
            double dist = 2 * aire / AB;
            double xAB = this.bx - this.ax;
            double yAB = this.by - this.ay;
            double xAC = posXsouris - this.ax;
            double yAC = posYsouris - this.ay;
            double prod = xAB * xAC + yAB * yAC;
            return (dist < 10) && (prod >= 0);
        }
    }
}
