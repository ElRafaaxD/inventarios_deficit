/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author miguelLlano
 */
public class Deficit {
    private int id;
    private String nombre;
    private float demanda_anual;            //D
    private float costo_por_Pedido;         //S
    private float costo_mantenimiento;      //H
    private float costo_por_unidad_faltante;//C (solo con deficit)
    //si es con (true) o sin (false) deficit
    private boolean deficit;

    //para extraer desde la db
    public Deficit(
        int id, 
        String nombre, 
        float demanda_anual, 
        float costo_por_Pedido, 
        float costo_mantenimiento, 
        float costo_por_unidad_faltante, 
        boolean deficit) {
        
        this.id = id;
        this.nombre = nombre;
        this.demanda_anual = demanda_anual;
        this.costo_por_Pedido = costo_por_Pedido;
        this.costo_mantenimiento = costo_mantenimiento;
        this.costo_por_unidad_faltante = costo_por_unidad_faltante;
        this.deficit = deficit;
    }
    
    //para agregar deficit a la db
    public Deficit(
        String nombre, 
        float demanda_anual, 
        float costo_por_Pedido, 
        float costo_mantenimiento, 
        float costo_por_unidad_faltante, 
        boolean deficit) {
        
        this.id = -1;
        this.nombre = nombre;
        this.demanda_anual = demanda_anual;
        this.costo_por_Pedido = costo_por_Pedido;
        this.costo_mantenimiento = costo_mantenimiento;
        this.costo_por_unidad_faltante = costo_por_unidad_faltante;
        this.deficit = deficit;
    }
    
    /*logica para caulcuar deficit*/
    
    public float Q() {
        //agrega la logica toñito (que devuelva Q)
        
        return 0;
    }
    
    public float CT() {
        //agrega la logica toñito (que devuelva CT)
        
        return 0;
    }
    
    //con deficit
    public float S() {
        if(this.deficit) return 0;
        
        //agrega la logica toñito (que devuelva S)
        
        return 0;
    }
    
    //sin deficit
    public float N() {
        if(!this.deficit) return 0;
        
        //agrega la logica toñito (que devuelva n)
        
        return 0;
    }
    
    public float T() {
        if(!this.deficit) return 0;
        
        //agrega la logica toñito (que devuelva T)
        return 0;
    }

    /*Getters*/
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getDemanda_anual() {
        return demanda_anual;
    }

    public float getCosto_por_Pedido() {
        return costo_por_Pedido;
    }

    public float getCosto_mantenimiento() {
        return costo_mantenimiento;
    }

    public float getCosto_por_unidad_faltante() {
        return costo_por_unidad_faltante;
    }
    
    public boolean is_deficit() {
        return deficit;
    }
    
    
}
