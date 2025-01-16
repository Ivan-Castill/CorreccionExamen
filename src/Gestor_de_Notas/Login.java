package Gestor_de_Notas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
    private JTextField textUser;
    public JPanel panelLogin;
    private JPasswordField passwordField1;
    private JButton accederButton;

    public Login(JFrame parentFrame) {
        accederButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //optener datos
                String UserIngresado = textUser.getText();
                String PassIngresado = new String(passwordField1.getPassword());
                //validadcion de credenciales
                if (validarCredenciales(UserIngresado,PassIngresado)){
                    JOptionPane.showMessageDialog(null,"Bienvenido \n"+UserIngresado+"\n Inicio de sesion exitosa");
                    //abre la siguiente pantalla
                    Banner banner = new Banner();
                    banner.visualizar();
                    parentFrame.dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorecta","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    //metodo para validar las credenciales
    private boolean validarCredenciales(String UserIngresado,String PassIngresado){
        //Datos de conexion
        String url = "jdbc:mysql://localhost:3307/gestor_calificaciones";
        String user = "root";
        String password ="1234";
        String query = "SELECT USUARIOS,PASS FROM usuarios WHERE USUARIOS = ? AND PASS = ?";
        try (Connection connection = DriverManager.getConnection(url,user,password)){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,UserIngresado);
            statement.setString(2,PassIngresado);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al conectar con la base de datos","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
