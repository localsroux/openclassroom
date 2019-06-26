/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.function.Consumer;

/**
 *
 * @author Stéphane ROUX
 */
public class Droite extends ElementGeometrique {

    private final Point A;
    private Point B;

    public Droite(Point p1, Point p2) {
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
        contenus.keySet().forEach((Integer e) -> {
            contenus.get(e).move(dx, dy);
        });
    }

    @Override
    protected ElementGeometrique translateSpec(ElementGeometrique source) {
        return null;
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
        return source.symAxReel(this);
    }

    @Override
    protected ElementGeometrique translateReel(Vecteur v) {
        Point nA = (Point) A.translateReel(v);
        Point nB = (Point) B.translateReel(v);
        return new Droite(nA, nB);
    }

    @Override
    protected ElementGeometrique rotateReel(Point centre, double angle) {
        Point nA = (Point) A.rotateReel(centre, angle);
        Point nB = (Point) B.rotateReel(centre, angle);
        return new Droite(nA, nB);

    }

    @Override
    protected ElementGeometrique symCentreReel(Point centre) {
        Point nA = (Point) A.symCentreReel(centre);
        Point nB = (Point) B.symCentreReel(centre);
        return new Droite(nA, nB);
    }

    @Override
    protected ElementGeometrique symAxReel(Droite axe) {
        Point nA = (Point) A.symAxReel(axe);
        Point nB = (Point) B.symAxReel(axe);
        return new Droite(nA, nB);

    }

    @Override
    public void dessine(Dessinateur d) {

        d.dessineDroite((int) (A.getX()), (int) (A.getY()), (int) (B.getX()), (int) (B.getY()));
    }

    @Override
    public boolean canBeCenter() {
        return false;
    }

    @Override
    public boolean canBeAxis() {
        return true;
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

    @Override
    public boolean isOn(double x, double y) {
        double AB = Math.sqrt((A.getX() - B.getX()) * (A.getX() - B.getX()) + (A.getY() - B.getY()) * (A.getY() - B.getY()));
        double AM = Math.sqrt((A.getX() - x) * (A.getX() - x) + (A.getY() - y) * (A.getY() - y));
        double MB = Math.sqrt((x - B.getX()) * (x - B.getX()) + (y - B.getY()) * (y - B.getY()));
        double s = (AB + AM + MB) / 2;
        double aire = Math.sqrt(s * (s - AB) * (s - MB) * (s - AM));
        double dist = 2 * aire / AB;
        return (dist < ElementGeometrique.delta);
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
        return false;
    }

    @Override
    protected ElementGeometrique homothSpec(ElementGeometrique source, double rapport) {
        return null;
    }

    @Override
    protected ElementGeometrique homothReel(Point aThis, double rapport) {
        Point p1 = (Point) A.homothReel(aThis, rapport);
        Point p2 = (Point) B.homothReel(aThis, rapport);
        return new Droite(p1, p2);
    }

    @Override
    public String toString() {
        String ret = "DROITE " + String.valueOf(ID) + " " + String.valueOf(A.ID) + " " + String.valueOf(B.ID) + " D ";
        ret = dependants.stream().map((i) -> i.toString() + " ").reduce(ret, String::concat);
        ret += "C";
        ret = contenus.keySet().stream().map((i) -> " " + i.toString()).reduce(ret, String::concat);
        return ret;
    }

    @Override
    public void accept(Intersector i) {
        i.visiteDroite(this);
    }
}
