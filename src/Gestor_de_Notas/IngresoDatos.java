package Gestor_de_Notas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class IngresoDatos extends JFrame {
    private JTextField NotaPOO;
    private JPanel panelIngresDatos;
    private JTextField NotaDiseño;
    private JTextField NotaGestion;
    private JTextField NotaRedes;
    private JTextField NotaCalculo;
    private JTextField textCedula;
    private JTextField textApellido;
    private JTextField textNombre;
    private JTextField textID;
    private JButton menuButton;
    private JButton guardarButton;

    public IngresoDatos() {
        setSize(900,800 );
        setPreferredSize(new Dimension(900,400));
        pack();
        setTitle("Ingreso de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelIngresDatos);
        setLocationRelativeTo(null);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos(); //llama al metodo
            }
        });
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Banner banner = new Banner();
                banner.visualizar();
                dispose();
            }
        });
    }
    private void guardarDatos(){
        //optener los datos
        String id = textID.getText().trim();
        String nombre = textNombre.getText().trim();
        String apellido = textApellido.getText().trim();
        String cedula = textCedula.getText().trim();
        String notapoo = NotaPOO.getText().trim();
        String notadiseño = NotaDiseño.getText().trim();
        String notagestion = NotaGestion.getText().trim();
        String notaredes = NotaRedes.getText().trim();
        String notacalculo = NotaCalculo.getText().trim();

        //validacion de los campos completos
        if(id.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || notapoo.isEmpty() || notadiseño.isEmpty() || notagestion.isEmpty() || notaredes.isEmpty() || notacalculo.isEmpty()){
            JOptionPane.showMessageDialog(null,"Por favor, completar todos los campos.","Campos Vacios",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            //convertir las notas a double
            double notaPOO = Double.parseDouble(notapoo);
            double notaDiseño = Double.parseDouble(notadiseño);
            double notaGestion = Double.parseDouble(notagestion);
            double notaRedes = Double.parseDouble(notaredes);
            double notaCalculo = Double.parseDouble(notacalculo);
            //VALIDAR Q LAS NOTAS SEA SOBRE 20
            if (notaPOO > 20 || notaDiseño > 20 || notaGestion > 20 || notaRedes > 20 || notaCalculo > 20) {
                JOptionPane.showMessageDialog(null, "Todas las notas deben ser menores o iguales a 20.", "Notas inválidas", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //calcular el promedio
            double promedio = (notaPOO+notaDiseño+notaGestion+notaRedes+notaCalculo)/5;
            //determinar si aprueba o falla
            String estado = promedio >=14 ? "A":"F";

            // datos de conexion
            String url = "jdbc:mysql://localhost:3307/gestor_calificaciones";
            String user = "root";
            String password = "1234";
            String query = "INSERT INTO datos_estudiantes (ID,NOMBRE,APELLIDO,CEDULA,NOTA_POO,NOTA_DISEÑO,NOTA_GESTION,NOTA_REDES,NOTA_CALCULO,PROMEDIO,APRUEBA_O_FALLA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //Conexion y ejecucion de la consulta
        try (Connection connection = DriverManager.getConnection(url,user,password)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(id));
            statement.setString(2, nombre);
            statement.setString(3, apellido);
            statement.setString(4, cedula);
            statement.setDouble(5, notaPOO);
            statement.setDouble(6, notaDiseño);
            statement.setDouble(7, notaGestion);
            statement.setDouble(8, notaRedes);
            statement.setDouble(9, notaCalculo);
            statement.setDouble(10,promedio);
            statement.setString(11,estado);

            int rowsInserted = statement.executeUpdate();//ejecuta la consulta
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Registro exitoso.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "No se puedo registrar. Intentelo de nuevo.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null,"Por  favor, ingrese valores Numericos validos para las notas.","ERROR DE FORMATO",JOptionPane.ERROR_MESSAGE);
        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al conetar con la base de datos.","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void limpiarCampos(){
        textID.setText("");
        textNombre.setText("");
        textApellido.setText("");
        textCedula.setText("");
        NotaPOO.setText("");
        NotaDiseño.setText("");
        NotaGestion.setText("");
        NotaRedes.setText("");
        NotaCalculo.setText("");
    }
    public void visualizar(){
        setVisible(true);
    }
}
