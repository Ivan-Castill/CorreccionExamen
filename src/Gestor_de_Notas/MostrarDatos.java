package Gestor_de_Notas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MostrarDatos extends JFrame {
    private JTable table1;
    private JPanel panelMostrar;
    private JTextField BusquedaCedula;
    private JButton buscarButton;
    private JButton menuButton;
    private JButton mostrarTodosLosDatosButton;

    public MostrarDatos() {
        setSize(800, 700);
        setPreferredSize(new Dimension(800, 700));
        pack();
        setTitle("Mostrar Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar el panel principal
        panelMostrar = new JPanel();
        panelMostrar.setLayout(new BorderLayout());
        setContentPane(panelMostrar);

        // Panel superior para los botones y campo de texto
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel labelCedula = new JLabel("Cédula:");
        BusquedaCedula = new JTextField(10);
        buscarButton = new JButton("Buscar");
        menuButton = new JButton("Menú");
        mostrarTodosLosDatosButton = new JButton("Mostrar Todos");

        topPanel.add(labelCedula);
        topPanel.add(BusquedaCedula);
        topPanel.add(buscarButton);
        topPanel.add(menuButton);
        topPanel.add(mostrarTodosLosDatosButton);
        panelMostrar.add(topPanel, BorderLayout.NORTH);

        // Configurar la tabla
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "ID", "NOMBRE", "APELLIDO", "CÉDULA",
                        "POO", "DISEÑO", "GESTIÓN", "REDES", "CÁLCULO",
                        "PROMEDIO", "ESTADO"
                }
        );
        table1 = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table1);
        panelMostrar.add(scrollPane, BorderLayout.CENTER);

        // Cargar todos los datos al inicio
        cargarTodosLosDatos();

        // Listeners
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = BusquedaCedula.getText().trim();
                if (!cedula.isEmpty()) {
                    buscarYMostrarPorCedula(cedula);
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese una cédula válida.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mostrarTodosLosDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarTodosLosDatos();
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

    // Método para cargar todos los datos desde la base de datos
    private void cargarTodosLosDatos() {
        String url = "jdbc:mysql://localhost:3307/gestor_calificaciones";
        String user = "root";
        String password = "1234";
        String query = "SELECT * FROM datos_estudiantes";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0); // Limpiar datos anteriores

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("ID"),
                        resultSet.getString("NOMBRE"),
                        resultSet.getString("APELLIDO"),
                        resultSet.getString("CEDULA"),
                        resultSet.getDouble("NOTA_POO"),
                        resultSet.getDouble("NOTA_DISEÑO"),
                        resultSet.getDouble("NOTA_GESTION"),
                        resultSet.getDouble("NOTA_REDES"),
                        resultSet.getDouble("NOTA_CALCULO"),
                        resultSet.getDouble("PROMEDIO"),
                        resultSet.getString("APRUEBA_O_FALLA")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para buscar un estudiante por su cédula
    private void buscarYMostrarPorCedula(String cedula) {
        String url = "jdbc:mysql://localhost:3307/gestor_calificaciones";
        String user = "root";
        String password = "1234";
        String query = "SELECT * FROM datos_estudiantes WHERE CEDULA = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cedula);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0); // Limpiar datos anteriores

            if (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("ID"),
                        resultSet.getString("NOMBRE"),
                        resultSet.getString("APELLIDO"),
                        resultSet.getString("CEDULA"),
                        resultSet.getDouble("NOTA_POO"),
                        resultSet.getDouble("NOTA_DISEÑO"),
                        resultSet.getDouble("NOTA_GESTION"),
                        resultSet.getDouble("NOTA_REDES"),
                        resultSet.getDouble("NOTA_CALCULO"),
                        resultSet.getDouble("PROMEDIO"),
                        resultSet.getString("APRUEBA_O_FALLA")
                });
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos con la cédula proporcionada.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar la ventana
    public void visualizar() {
        setVisible(true);
    }
}
