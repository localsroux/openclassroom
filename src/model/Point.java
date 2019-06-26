/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Stéphane ROUX
 */
public class Point extends ElementGeometrique {

    private double x;
    private double y;

    public Point(double nx, double ny) {
        super();
        x = nx;
        y = ny;
    }

    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    protected ElementGeometrique translateSpec(ElementGeometrique source) {
        return null;
    }

    @Override
    protected ElementGeometrique rotateSpec(ElementGeometrique source, double angle) {
        return source.rotateReel(this, angle);
    }

    @Override
    protected ElementGeometrique symCentreSpec(ElementGeometrique source) {
        return source.symCentreReel(this);
    }

    @Override
    protected ElementGeometrique symAxSpec(ElementGeometrique source) {
        return null;
    }

    @Override
    protected ElementGeometrique translateReel(Vecteur v) {
        double newPx = x + v.getX();
        double newPy = y + v.getY();
        return new Point(newPx, newPy);
    }

    @Override
    protected ElementGeometrique rotateReel(Point centre, double angle) {
        double a = centre.x;
        double b = centre.y;
        double nx = Math.cos(angle) * (x - a) - Math.sin(angle) * (y - b) + a;
        double ny = Math.sin(angle) * (x - a) + Math.cos(angle) * (y - b) + b;


        return new Point(nx, ny);

    }

    @Override
    protected ElementGeometrique symCentreReel(Point centre) {
        double dx = centre.x - x;
        double dy = centre.y - y;
        return new Point(x + 2 * dx, y + 2 * dy);
    }

    @Override
    protected ElementGeometrique symAxReel(Droite axe) {
        double ax = axe.getAX();
        double ay = axe.getAY();
        double bx = axe.getBX();
        double by = axe.getBY();
        //Coordonnées vecteur directeur
        double vdx = bx - ax;
        double vdy = by - ay;

        //Coordonnées vecteur normal
        double vnx = -vdy;
        double vny = vdx;


        //On oriente le vecteur du point vers la droite
        if (vnx * (ax - x) + vny * (ay - y) < 0) {
            vnx = -vnx;
            vny = -vny;
        }

        //On normalise le vecteur normal
        double norme = Math.sqrt(vnx * vnx + vny * vny);
        vnx /= norme;
        vny /= norme;

        //On calcule la distance du point à la droite
        double ab = Math.sqrt((ax - bx) * (ax - bx) + (ay - by) * (ay - by));
        double bc = Math.sqrt((x - bx) * (x - bx) + (y - by) * (y - by));
        double ca = Math.sqrt((ax - x) * (ax - x) + (ay - y) * (ay - y));
        double s = (ab + bc + ca) / 2;
        double aire = Math.sqrt(s * (s - ab) * (s - bc) * (s - ca));
        double distDroite = 2 * aire / ab;

        //On applique une translation au point dont le vecteur a une norme qui fait le double de la distance du point à la droite
        vnx *= 2 * distDroite;
        vny *= 2 * distDroite;
        return new Point(x + vnx, y + vny);

    }

    @Override
    public void dessine(Dessinateur d) {
        d.dessinePoint((int) (this.x), (int) (this.y));
    }

    @Override
    public boolean canBeCenter() {
        return true;
    }

    @Override
    public boolean canBeAxis() {
        return false;
    }

    @Override
    public boolean isOn(double x, double y) {
        return Math.abs(Math.sqrt((x - this.getX()) * (x - this.getX()) + (y - this.getY()) * (y - this.getY()))) < ElementGeometrique.delta;
    }

    @Override
    public double getXnear(double x, double y) {
        return x;
    }

    @Override
    public double getYnear(double x, double y) {
        return y;
    }

    @Override
    public boolean canBeVector() {
        return false;
    }

    @Override
    protected ElementGeometrique homothSpec(ElementGeometrique source, double rapport) {
        return source.homothReel(this, rapport);
    }

    @Override
    protected ElementGeometrique homothReel(Point centre, double rapport) {
        double newX = centre.x + rapport * (this.x - centre.x);
        double newY = centre.y + rapport * (this.y - centre.y);
        return new Point(newX, newY);
    }

    @Override
    public String toString() {
        String ret = "POINT " + String.valueOf(ID) + " " + String.valueOf(x) + " " + String.valueOf(y) + " D ";
        for (Integer i : dependants) {
            ret += i.toString() + " ";
        }
        ret += "C";
        for (Integer i : contenus.keySet()) {
            ret += " " + i.toString();
        }
        return ret;
    }

    @Override
    public void accept(Intersector i) {
        i.visitePoint(this);
    }
}
