/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.*;
import controleur.*;
import java.awt.*;

/**
 *
 * @author Stéphane ROUX
 */
public class VueInfo extends JPanel {

    private Controleur controleur;
    private JLabel texte;

    public VueInfo(Controleur c) {
        controleur = c;
        texte = new JLabel(controleur.getEtat());

        this.setLayout(new BorderLayout());
        this.add(texte, BorderLayout.WEST);
        this.setBackground(Color.ORANGE);
    }

    public void update() {
        texte.setText(controleur.getEtat());
    }
}
