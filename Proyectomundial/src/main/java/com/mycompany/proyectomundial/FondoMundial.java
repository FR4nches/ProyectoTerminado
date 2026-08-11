package com.mycompany.proyectomundial;

import javax.swing.*;
import java.awt.*;

public class FondoMundial extends JPanel {

    private Image imagen;

    public FondoMundial() {

        java.net.URL url = getClass().getResource("/images/estadio.jpg");

        if (url != null) {
            imagen = new ImageIcon(url).getImage();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagen != null) {
            g.drawImage(
                imagen,
                0,
                0,
                getWidth(),
                getHeight(),
                this
            );
        }
    }
}