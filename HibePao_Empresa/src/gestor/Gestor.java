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
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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

    private final static String driver = "com.mysql.jdbc.Driver";

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
            throw new  MyException(ex.getSQLState() + " Error al conectarse");
        }
    }

    public void cerrarConexion() throws MyException {
        try {
            this.conn.close();
            System.out.println("se cierra");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new  MyException(ex.getSQLState() + " no se ha podido cerrar la base de datos, posible perdida de informacion");

        }
    }
    public void registrarEmpleado(int id, String nombre, String apellido, String puesto,  Float salario, TipoContrato tipo, int idJefe)throws SQLException{
      {
        PreparedStatement st = null;// Sentencia SQL con placeholders
        String sql = "INSERT INTO Raves (idUsuario, nombre, ubicacion, fecha, detalles) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        // Asignar valores a los placeholders
        preparedStatement.setString(1, idUsuario); // id
        preparedStatement.setString(2, nombre); // name
        preparedStatement.setString(3, ubicacion); // ubicacion
        preparedStatement.setDate(4, fecha); // fecha
        preparedStatement.setString(5, detalles); // fecha
        

        // Ejecutar la sentencia
        int rowsInserted = preparedStatement.executeUpdate();

        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "El registro fue insertado exitosamente.");
            System.out.println("El registro fue insertado exitosamente.");

        }
    }
    }
}
