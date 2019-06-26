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
public class Cercle extends ElementGeometrique {

    private Point mycentre;
    private Point pointDef;

    public Cercle(Point c, Point d) {
        super();
        mycentre = c;
        pointDef = d;
        mycentre.addDependant(ID);
        pointDef.addDependant(ID);
        contenus.put(c.getID(), c);
        contenus.put(d.getID(), d);
    }

    @Override
    public void move(double dx, double dy) {
        for (Integer e : contenus.keySet()) {
            contenus.get(e).move(dx, dy);
        }
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
        return null;
    }

    @Override
    protected ElementGeometrique translateReel(Vecteur v) {
        Point nc = (Point) mycentre.translateReel(v);
        Point nd = (Point) pointDef.translateReel(v);
        return new Cercle(nc, nd);
    }

    @Override
    protected ElementGeometrique rotateReel(Point centre, double angle) {
        Point nc = (Point) mycentre.rotateReel(centre, angle);
        Point nd = (Point) pointDef.rotateReel(centre, angle);
        return new Cercle(nc, nd);

    }

    @Override
    protected ElementGeometrique symCentreReel(Point centre) {
        Point nc = (Point) mycentre.symCentreReel(centre);
        Point nd = (Point) pointDef.symCentreReel(centre);
        return new Cercle(nc, nd);
    }

    @Override
    protected ElementGeometrique symAxReel(Droite axe) {
        Point nc = (Point) mycentre.symAxReel(axe);
        Point nd = (Point) pointDef.symAxReel(axe);
        return new Cercle(nc, nd);

    }

    @Override
    public void dessine(Dessinateur d) {

        d.dessineCercle((int) mycentre.getX(), (int) mycentre.getY(), (int) this.rayon());
    }

    @Override
    public boolean canBeCenter() {
        return false;
    }

    @Override
    public boolean canBeAxis() {
        return false;
    }

    @Override
    public boolean isOn(double x, double y) {
        return Math.abs(this.rayon() - Math.sqrt((x - mycentre.getX()) * (x - mycentre.getX()) + (y - mycentre.getY()) * (y - mycentre.getY()))) < ElementGeometrique.delta;
    }

    @Override
    public double getXnear(double x, double y) {
        double xc = mycentre.getX();
        double yc = mycentre.getY();
        double R = this.rayon();
        double vx = x - xc;
        double vy = y - yc;
        double dist = Math.sqrt((x - xc) * (x - xc) + (y - yc) * (y - yc));
        vx /= dist;
        vx *= R;
        return xc + vx;
    }

    @Override
    public double getYnear(double x, double y) {
        double xc = mycentre.getX();
        double yc = mycentre.getY();
        double R = this.rayon();
        double vx = x - xc;
        double vy = y - yc;
        double dist = Math.sqrt((x - xc) * (x - xc) + (y - yc) * (y - yc));
        vy /= dist;
        vy *= R;
        return yc + vy;
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
        Point nc = (Point) mycentre.homothReel(aThis, rapport);
        Point nd = (Point) pointDef.homothReel(aThis, rapport);
        return new Cercle(nc, nd);
    }

    @Override
    public String toString() {
        String ret = "CERCLE " + String.valueOf(ID) + " " + String.valueOf(mycentre.ID) + " " + String.valueOf(pointDef.ID) + " D ";
        for (Integer i : dependants) {
            ret += i.toString() + " ";
        }
        ret += "C";
        for (Integer i : contenus.keySet()) {
            ret += " " + i.toString();
        }
        return ret;
    }

    public double rayon() {
        double cx = mycentre.getX();
        double cy = mycentre.getY();
        double ax = pointDef.getX();
        double ay = pointDef.getY();
        return Math.sqrt((cx - ax) * (cx - ax) + (cy - ay) * (cy - ay));
    }

    public double xC() {
        return mycentre.getX();

    }

    public double yC() {
        return mycentre.getY();
    }

    @Override
    public void accept(Intersector i) {
        i.visiteCercle(this);
    }
}
