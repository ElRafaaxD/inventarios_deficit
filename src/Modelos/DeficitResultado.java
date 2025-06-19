package Modelos;

public class DeficitResultado extends Deficit {
    
    private int id_resultado;
    private float Q;
    private float CT;
    private float S;
    private float N;
    private float T;
    
    public DeficitResultado(
        int id_deficit, 
        String nombre, 
        float demanda_anual, 
        float costo_por_pedido, 
        float costo_mantenimiento, 
        float costo_por_unidad_faltante,
        int anio_calculo,
        boolean deficit,
        //datos de la clase actual
        int id_resultado,
        float Q,
        float CT,
        float S,
        float N,
        float T) {
        //constructor padre
        super(id_deficit, nombre, demanda_anual, costo_por_pedido, costo_mantenimiento, 
        costo_por_unidad_faltante, anio_calculo, deficit);
        //asignar valores de la clase 
        this.id_resultado = id_resultado;
        this.Q = Q;
        this.CT = CT;
        this.S = S;
        this.N = N;
        this.T = T;
    }
    
    @Override
    public String toString() {
        return super.toString().replace("}", "")
            +
            ", id_resultado=" + id_resultado
            + ", Q=" + Q
            + ", CT=" + CT
            + ", S=" + S
            + ", N=" + N
            + ", T=" + T
            + '}';
    }


    /*Getters*/
    public int getId_resultado() {
        return id_resultado;
    }

    public float getQ() {
        return Q;
    }

    public float getCT() {
        return CT;
    }

    public float getS() {
        return S;
    }

    public float getN() {
        return N;
    }

    public float getT() {
        return T;
    }
}
