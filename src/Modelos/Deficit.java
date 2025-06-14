package Modelos;

/**
 *
 * @author miguelLlano
 */
public class Deficit {

    private int id;
    private String nombre;
    private float demanda_anual;            // D
    private float costo_por_Pedido;         // S
    private float costo_mantenimiento;      // H
    private float costo_por_unidad_faltante; // C
    private boolean deficit;                // true = con déficit

    // Constructor desde base de datos
    public Deficit(int id, String nombre, float demanda_anual, float costo_por_Pedido, float costo_mantenimiento, float costo_por_unidad_faltante, boolean deficit) {
        this.id = id;
        this.nombre = nombre;
        this.demanda_anual = demanda_anual;
        this.costo_por_Pedido = costo_por_Pedido;
        this.costo_mantenimiento = costo_mantenimiento;
        this.costo_por_unidad_faltante = costo_por_unidad_faltante;
        this.deficit = deficit;
    }

    // Constructor para insertar en base de datos
    public Deficit(String nombre, float demanda_anual, float costo_por_Pedido, float costo_mantenimiento, float costo_por_unidad_faltante, boolean deficit) {
        this.id = -1;
        this.nombre = nombre;
        this.demanda_anual = demanda_anual;
        this.costo_por_Pedido = costo_por_Pedido;
        this.costo_mantenimiento = costo_mantenimiento;
        this.costo_por_unidad_faltante = costo_por_unidad_faltante;
        this.deficit = deficit;
    }

    /**
     * Cálculo del tamaño óptimo de lote Q *
     */
    public float Q() {
        if (deficit) {
            // Con déficit
            return (float) Math.sqrt((2 * demanda_anual * costo_por_Pedido * (costo_mantenimiento + costo_por_unidad_faltante))
                    / (costo_mantenimiento * costo_por_unidad_faltante));
        } else {
            // Sin déficit
            return (float) Math.sqrt((2 * demanda_anual * costo_por_Pedido) / costo_mantenimiento);
        }
    }

    /**
     * Costo total *
     */
    public float CT() {
        float Q = Q();
        if (deficit) {
            float S = S();
            float N = Q - S;
            float term1 = (demanda_anual * costo_por_Pedido) / Q;
            float term2 = (float) ((Math.pow(N, 2) * costo_mantenimiento) / (2 * Q));
            float term3 = (float) ((Math.pow(S, 2) * costo_por_unidad_faltante) / (2 * Q));
            return term1 + term2 + term3;
        } else {
            return (demanda_anual * costo_por_Pedido) / Q + (Q * costo_mantenimiento) / 2;
        }
    }

    /**
     * Punto de reorden S (solo con déficit) *
     */
    public float S() {
        if (!deficit) {
            return 0;
        }
        float Q = Q();
        return (Q * costo_mantenimiento) / (costo_mantenimiento + costo_por_unidad_faltante);
    }

    /**
     * Nivel máximo de inventario N (solo con déficit) *
     */
    public float N() {
        if (!deficit) {
            return 0;
        }
        float Q = Q();
        return Q - S();
    }

    /**
     * Tiempo entre pedidos T (solo con déficit) *
     */
    public float T() {
        if (!deficit) {
            return 0;
        }
        float Q = Q();
        return Q / demanda_anual;
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
