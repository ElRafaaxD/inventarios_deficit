package Modelos;

/**
 *
 * @author miguelLlano
 */
public class Deficit {

    private int id;
    private String nombre;                  // nombre producto
    private float demanda_anual;            // D
    private float costo_por_pedido;         // S
    private float costo_mantenimiento;      // H
    private float costo_por_unidad_faltante;// C
    private int anio_calculo;               // año de calculo
    private boolean deficit;                // true = con déficit

    // Constructor desde base de datos
    public Deficit(
        int id, 
        String nombre, 
        float demanda_anual, 
        float costo_por_pedido, 
        float costo_mantenimiento, 
        float costo_por_unidad_faltante,
        int anio_calculo,
        boolean deficit) {
        
        this.id = id;
        this.nombre = nombre;
        this.demanda_anual = demanda_anual;
        this.costo_por_pedido = costo_por_pedido;
        this.costo_mantenimiento = costo_mantenimiento;
        this.costo_por_unidad_faltante = costo_por_unidad_faltante;
        this.anio_calculo = anio_calculo;
        this.deficit = deficit;
    }

    // Constructor para insertar en base de datos
    public Deficit(
        String nombre,
        float demanda_anual,            //D
        float costo_por_pedido,         //S
        float costo_mantenimiento,      //H
        float costo_por_unidad_faltante,//C
        int anio_calculo,               //fecha para año
        boolean deficit) {
        
        this.id = -1;
        this.nombre = nombre;
        this.demanda_anual = demanda_anual;
        this.costo_por_pedido = costo_por_pedido;
        this.costo_mantenimiento = costo_mantenimiento;
        this.costo_por_unidad_faltante = costo_por_unidad_faltante;
        this.anio_calculo = anio_calculo;
        this.deficit = deficit;
    }
    
    public Deficit(
        String nombre,
        float demanda_anual,      //D
        float costo_por_Pedido,   //S
        float costo_mantenimiento,//H
        int anio_calculo,         //fecha para año
        boolean deficit) {
        
        this.id = -1;
        this.nombre = nombre;
        this.demanda_anual = demanda_anual;
        this.costo_por_pedido = costo_por_pedido;
        this.costo_mantenimiento = costo_mantenimiento;
        this.anio_calculo = anio_calculo;
        this.deficit = deficit;
    }

    /**
     * Calculo del tamaño optimo de lote Q *
     * @return 
    **/
    public float Q() {
        return (float) (deficit
        ? Math.sqrt(
        (2 * demanda_anual * costo_por_pedido * (costo_mantenimiento + costo_por_unidad_faltante)) / (costo_mantenimiento * costo_por_unidad_faltante))
        : Math.sqrt((2 * demanda_anual * costo_por_pedido / costo_mantenimiento))
        );
    }

    /**
     * Costo total *
     * @return
    **/
    public float CT() {
        float q = Q();//Q*
        
        if(deficit) {
            float s = S();//S*
            return (s/2 * costo_mantenimiento) + 
                   (( (q - s) / 2 ) * costo_por_unidad_faltante) + 
                   (demanda_anual / q * costo_por_pedido);
        }
        
        return (q/2 * costo_mantenimiento) + (demanda_anual/q * costo_por_pedido);
    }

    /**
     * Inventario maximo disponible *
     */
    public float S() {
        return (float) (deficit
        ? Q() * 
          (costo_por_unidad_faltante / (costo_mantenimiento + costo_por_unidad_faltante)) 
        : 0
        );
    }

    /**
     * Numero de pedidos al año
     */
    public float N() {
        return demanda_anual/Q() ;
    }

    /**
     * Tiempo entre pedidos T
     */
    public float T() {
        return Q() / demanda_anual;
    }

    @Override
    public String toString() {
        return "Deficit {"
            + "id=" + id
            + ", nombre='" + nombre + '\''
            + ", demanda_anual=" + demanda_anual
            + ", costo_por_pedido=" + costo_por_pedido
            + ", costo_mantenimiento=" + costo_mantenimiento
            + ", costo_por_unidad_faltante=" + costo_por_unidad_faltante
            + ", anio_calculo=" + anio_calculo
            + ", deficit=" + deficit
            + '}';
    }
    
    /* Getters */
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
        return costo_por_pedido;
    }

    public float getCosto_mantenimiento() {
        return costo_mantenimiento;
    }

    public float getCosto_por_unidad_faltante() {
        return costo_por_unidad_faltante;
    }

    public int getAnio_calculo() {
        return anio_calculo;
    }

    public boolean is_deficit() {
        return deficit;
    }
}
