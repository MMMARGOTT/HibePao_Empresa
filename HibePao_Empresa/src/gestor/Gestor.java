/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor;

import excepciones.MyException;
import interfaces.MenuPrincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Empleado;
import modelo.Jefe;
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

    public Connection getConnection() {
        return conn;
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

    public void registrarEmpleado(String nombre, String apellido, String puesto, Float salario, TipoContrato tipo) {
        try {
            PreparedStatement st = null;
            String sql = "INSERT INTO empleados(nombre, apellido, puesto, salario, tipo_contrato) VALUES (?, ?, ?, ?, ?)";

            st = conn.prepareStatement(sql);
            st.setString(1, nombre);
            st.setString(2, apellido);
            st.setString(3, puesto);
            st.setFloat(4, salario);
            st.setString(5, tipo.toString());

            int rowsInserted = st.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "El registro fue insertado exitosamente.");
                System.out.println("El registro fue insertado exitosamente.");

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    public ArrayList<Empleado> consultarEmpleado(String puesto, int idJefe) throws MyException {
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();

        try {
            String sql = "SELECT * FROM empleados WHERE puesto = ? AND jefe_id = ?";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            sentencia.setString(1, puesto);
            sentencia.setInt(2, idJefe);
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido");
                Float salario = resultado.getFloat("salario");
                String tipoContrato = resultado.getString("tipo_contrato");
                int jefeId = resultado.getInt("jefe_id");

                //TipoContrato tipoContatoEnum = TipoContrato.valueOf(tipoContrato);
                Empleado e = new Empleado(id, nombre, apellido, puesto, salario, tipoContrato, jefeId);

                listaEmpleados.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

    public void asignarJefe(int idEmpleado, int idJefe) throws SQLException {
        String sql = "UPDATE empleados SET jefe_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJefe);
            ps.setInt(2, idEmpleado);

            int rowsAsignadas = ps.executeUpdate();
            if (rowsAsignadas > 0) {
                JOptionPane.showMessageDialog(null, "Jefe asignado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Empleado no encontrado");
            }
        }

    }

    public void volverMenu(Gestor gestor) {
        MenuPrincipal mp = new MenuPrincipal(gestor);
        mp.setVisible(true);
        mp.setLocationRelativeTo(null); //Para poner la ventana en el centr

    }
}
