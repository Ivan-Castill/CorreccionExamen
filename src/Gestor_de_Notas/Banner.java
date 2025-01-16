package Gestor_de_Notas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Banner extends JFrame{
    private JButton ingresoDeNotasButton;
    private JPanel panelMenu;
    private JButton mostrarDatosButton;

    public Banner() {
        setSize(500,400);
        setPreferredSize(new Dimension(500,400));
        pack();
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelMenu);
        setLocationRelativeTo(null);

        ingresoDeNotasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IngresoDatos ingresoDatos = new IngresoDatos();
                ingresoDatos.visualizar();
                dispose();
            }
        });
        mostrarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarDatos mostrarDatos = new MostrarDatos();
                mostrarDatos.visualizar();
                dispose();
            }
        });
    }
    public void visualizar(){
        setVisible(true);
    }
}
