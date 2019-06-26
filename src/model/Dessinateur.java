/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Stéphane ROUX
 */
public interface Dessinateur {

    public void dessinePoint(int px, int py);

    public void dessineCercle(int px, int py, int rayon);

    public void dessineSegment(int ax, int ay, int bx, int by);

    public void dessineDroite(int ax, int ay, int bx, int by);

    public void dessineDemiDroite(int ax, int ay, int bx, int by);

    public void dessineVecteur(int ax, int ay, int bx, int by);

    public void blanchit();
    
    
}
