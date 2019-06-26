/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import model.*;

/**
 *
 * @author St ROUX
 */
public abstract class Commande {

    public abstract void execute(Modele m);

    public abstract void undo(Modele m);
}
