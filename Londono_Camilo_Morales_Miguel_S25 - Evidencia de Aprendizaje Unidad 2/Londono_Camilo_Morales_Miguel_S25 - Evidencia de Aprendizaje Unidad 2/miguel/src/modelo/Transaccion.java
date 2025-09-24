package modelo;

public interface Transaccion {
    void ejecutar() throws Exception;
    double getMonto();
}