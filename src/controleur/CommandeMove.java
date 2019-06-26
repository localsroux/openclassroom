/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import model.Modele;

/**
 *
 * @author St ROUX
 */
public class CommandeMove extends Commande {

    private final int cible;
    private final int xDep;
    private final int yDep;
    private final int x;
    private final int y;

    public CommandeMove(int Selected, int x, int y, int xDep, int yDep) {
        cible = Selected;
        this.x = x;
        this.y = y;
        this.xDep = xDep;
        this.yDep = yDep;
    }

    @Override
    public void execute(Modele m) {
        m.get(cible).move(x - xDep, y - yDep);
    }

    @Override
    public void undo(Modele m) {
        m.get(cible).move(xDep - x, yDep - y);
    }
}
