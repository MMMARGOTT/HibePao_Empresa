/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibepao_empresa;

import excepciones.MyException;
import gestor.Gestor;
import javax.swing.JOptionPane;

/**
 *
 * @author Niio
 */
public class HibePao_Empresa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MyException {
        // TODO code application logic here
     
            // TODO code application logic here
            
            Gestor miConexion = new Gestor("root", "hibePao_empresa", "jdbc:mysql://localhost:3306/", "");
            miConexion.initDataBase();
            
            
           /*
            LogIn logIn = new LogIn(miConexion);
            logIn.setVisible(true);
            logIn.setLocationRelativeTo(null);
            
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    */
   }
        }
    
    

