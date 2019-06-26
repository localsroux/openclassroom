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
public class Vecteur extends ElementGeometrique {

    private final Point A;
    private final Point B;

    public Vecteur(Point p1, Point p2) {
        super();
        A = p1;
        B = p2;
        A.addDependant(ID);
        B.addDependant(ID);

        contenus.put(A.getID(), A);
        contenus.put(B.getID(), B);
    }

    @Override
    public void move(double dx, double dy) {
        for (Integer e : contenus.keySet()) {
            contenus.get(e).move(dx, dy);
        }
    }

    @Override
    protected ElementGeometrique translateSpec(ElementGeometrique source) {
        return source.translateReel(this);
    }

    @Override
    protected ElementGeometrique rotateSpec(ElementGeometrique source, double angle) {
        return null;
    }

    @Override
    protected ElementGeometrique symCentreSpec(ElementGeometrique source) {
        return null;
    }

    @Override
    protected ElementGeometrique symAxSpec(ElementGeometrique source) {
        return null;
    }

    @Override
    protected ElementGeometrique translateReel(Vecteur v) {
        Point nA = (Point) A.translateReel(v);
        Point nB = (Point) B.translateReel(v);
        return new Vecteur(nA, nB);
    }

    @Override
    protected ElementGeometrique rotateReel(Point centre, double angle) {
        Point nA = (Point) A.rotateReel(centre, angle);
        Point nB = (Point) B.rotateReel(centre, angle);
        return new Vecteur(nA, nB);

    }

    @Override
    protected ElementGeometrique symCentreReel(Point centre) {
        Point nA = (Point) A.symCentreReel(centre);
        Point nB = (Point) B.symCentreReel(centre);
        return new Vecteur(nA, nB);
    }

    @Override
    protected ElementGeometrique symAxReel(Droite axe) {
        Point nA = (Point) A.symAxReel(axe);
        Point nB = (Point) B.symAxReel(axe);
        return new Vecteur(nA, nB);

    }

    @Override
    public void dessine(Dessinateur d) {

        d.dessineVecteur((int) (A.getX()), (int) (A.getY()), (int) (B.getX()), (int) (B.getY()));
    }

    @Override
    public boolean canBeCenter() {
        return false;
    }

    @Override
    public boolean canBeAxis() {
        return false;
    }

    public double getAX() {
        return A.getX();
    }

    public double getAY() {
        return A.getY();
    }

    public double getBX() {
        return B.getX();
    }

    public double getBY() {
        return B.getY();
    }

    public double getX() {
        return B.getX() - A.getX();
    }

    public double getY() {
        return B.getY() - A.getY();
    }

    @Override
    public boolean isOn(double x, double y) {
        double dMA = Math.sqrt((x - A.getX()) * (x - A.getX()) + (y - A.getY()) * (y - A.getY()));
        double dMB = Math.sqrt((x - B.getX()) * (x - B.getX()) + (y - B.getY()) * (y - B.getY()));
        double dAB = Math.sqrt((B.getX() - A.getX()) * (B.getX() - A.getX()) + (B.getY() - A.getY()) * (B.getY() - A.getY()));
        return dMA + dMB < 2 * Math.sqrt(dAB * dAB / 4 + 100);
    }

    @Override
    public double getXnear(double x, double y) {
        if (A.getX() == B.getX()) {
            return A.getX();
        }
        if (A.getY() == B.getY()) {
            return x;
        }
        double m = (A.getY() - B.getY()) / (A.getX() - B.getX());
        double p = A.getY() - m * A.getX();
        double m2 = -1 / m;
        double p2 = y - m2 * x;

        return (p - p2) / (m2 - m);
    }

    @Override
    public double getYnear(double x, double y) {

        if (A.getX() == B.getX()) {
            return y;
        }
        double m = (A.getY() - B.getY()) / (A.getX() - B.getX());
        double p = A.getY() - m * A.getX();
        return m * this.getXnear(x, y) + p;
    }

    @Override
    public boolean canBeVector() {
        return true;
    }

    @Override
    protected ElementGeometrique homothSpec(ElementGeometrique source, double rapport) {
        return null;
    }

    @Override
    protected ElementGeometrique homothReel(Point aThis, double rapport) {
        Point p1 = (Point) A.homothReel(aThis, rapport);
        Point p2 = (Point) B.homothReel(aThis, rapport);
        return new Vecteur(p1, p2);
    }

    @Override
    public String toString() {
        String ret = "VECTEUR " + String.valueOf(ID) + " " + String.valueOf(A.ID) + " " + String.valueOf(B.ID) + " D ";
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
        i.visiteVecteur(this);
    }
}
