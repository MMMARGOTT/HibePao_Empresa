/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor;

import excepciones.MyException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Empleado;
import modelo.TipoContrato;

/**
 *
 * @author Niio
 */
public class Gestor {

    private String user;
    private String db;
    private String conexion;
    private String password;
    private Connection conn;

    private final static String driver = "com.mysql.cj.jdbc.Driver";
    private ArrayList<Empleado> listaEmpleados;

    public Gestor(String user, String db, String conexion, String password) {
        this.user = user;
        this.db = db;
        this.conexion = conexion;
        this.password = password;
        this.conn = null;
    }

    //Métodos para inicializar y cerrar la base de datos
    public void initDataBase() throws MyException {
        try {
            //cargar el driver
            Class.forName(driver);
            this.conn = DriverManager.getConnection(conexion + db, user, password);
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
            throw new MyException("No has puesto la librería MySql");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new MyException(ex.getSQLState() + " Error al conectarse");
        }
    }

    public void cerrarConexion() throws MyException {
        try {
            this.conn.close();
            System.out.println("se cierra");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new MyException(ex.getSQLState() + " no se ha podido cerrar la base de datos, posible perdida de informacion");

        }
    }

    public void registrarEmpleado(int id, String nombre, String apellido, String puesto, Float salario, TipoContrato tipo, int idJefe) throws SQLException {
        {
            PreparedStatement st = null;
            String sql = "INSERT INTO empleados  (id, nombre,apellido, puesto, salario, tipo, jefe_id) VALUES (?, ?, ?, ?, ?, ?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            // Asignar valores a los placeholders
            preparedStatement.setInt(1, id); // id
            preparedStatement.setString(2, nombre); // name
            preparedStatement.setString(3, apellido); // ubicacion
            preparedStatement.setString(4, puesto); // fecha
            preparedStatement.setFloat(5, salario); // fecha
            preparedStatement.setString(6, tipo.toString());
            preparedStatement.setInt(7, idJefe); // id

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "El registro fue insertado exitosamente.");
                System.out.println("El registro fue insertado exitosamente.");

            }
        }
    }

    public ArrayList<Empleado> ConsultarEmpleado(String puesto, int idJefe) throws MyException {
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();

        try {
            String sql = "SELECT * FROM empleado WHERE puesto && jefe_id";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, puesto);
            preparedStatement.setInt(2, idJefe);

            try (ResultSet rs = (ResultSet) preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Empleado e = new Empleado(rs.getInt("id"),
                            rs.getString("nombre"), rs.getString("apellido"),
                            rs.getString("puesto"), rs.getFloat("salario"),
                            rs.getString("tipo"), rs.getInt("idJefe"));
                    listaEmpleados.add(e);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getSQLState(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return listaEmpleados;
    }

    public void modificarSalario(int idEmpleado, float nuevoSalario) throws SQLException {
        String sql = "UPDATE empleados SET salario = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setFloat(1, nuevoSalario);
            preparedStatement.setInt(2, idEmpleado);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Salario actualizado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado al empleado");
            }
        }
    }

    public void borrarEmpleado(int idEmpleado) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, idEmpleado);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado a ese empleado con ese id");
            }
        }
    }

}
