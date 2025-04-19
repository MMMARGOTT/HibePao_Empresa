/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import excepciones.MyException;

/**
 *
 * @author Niio
 */
public class Empleado {
    
    private int id;
    private String nombre;
    private String apellido;
    private String puesto;
    private float salario;
    private TipoContrato tipo;
    private int idJefe;
    
    public Empleado(int id, String nombre, String apellido, String puesto, float salario, String tipo, int idJefe) throws MyException {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.puesto = puesto;
        this.salario = salario;
        
        if (tipo.equals("INDEFINIDO")) {
            this.setTipo(TipoContrato.INDEFINIDO);
        } else if (tipo.equals("TEMPORAL")) {
            this.setTipo(TipoContrato.TEMPORAL);
            
        } else if (tipo.equals("PRACTICAS")) {
            this.setTipo((TipoContrato.PRACTICAS));
        }else{
            throw new MyException("Error al asignar el tipo");
        }
        
        this.idJefe = idJefe;
        
    }

    public Empleado(String nombre, String apellido, String puesto, float salario, TipoContrato tipo, int idJefe) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.puesto = puesto;
        this.salario = salario;
        this.tipo = tipo;
        this.idJefe = idJefe;
    }
    
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getPuesto() {
        return puesto;
    }
    
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    public float getSalario() {
        return salario;
    }
    
    public void setSalario(float salario) {
        this.salario = salario;
    }
    
    public TipoContrato getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoContrato tipo) {
        this.tipo = tipo;
    }
    
    public int getIdJefe() {
        return idJefe;
    }
    
    public void setIdJefe(int idJefe) {
        this.idJefe = idJefe;
        
    }
    
    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", puesto=" + puesto + ", salario=" + salario + ", tipo=" + tipo + ", idJefe=" + idJefe + '}';
        
    }
}
