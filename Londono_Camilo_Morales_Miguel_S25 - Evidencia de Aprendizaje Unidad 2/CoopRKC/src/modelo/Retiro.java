package modelo;

public class Retiro implements Transaccion {
    private final Cuenta cuenta;
    private final double monto;

    public Retiro(Cuenta cuenta, double monto) {
        if (cuenta == null) throw new IllegalArgumentException("La cuenta no puede ser null");
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void ejecutar() throws MontoInvalidoException, FondosInsuficientesException {
        cuenta.retirar(monto);
    }

    @Override
    public double getMonto() { return monto; }
}
