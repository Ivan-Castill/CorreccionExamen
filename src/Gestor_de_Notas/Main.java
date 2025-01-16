package Gestor_de_Notas;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Ejecucion del Programa: Correcto");
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login(frame).panelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,400);
        frame.setPreferredSize(new Dimension(500,400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}