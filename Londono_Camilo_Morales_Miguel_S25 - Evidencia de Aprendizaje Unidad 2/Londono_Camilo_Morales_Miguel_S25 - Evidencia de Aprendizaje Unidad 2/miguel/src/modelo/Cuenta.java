package modelo;

public abstract class Cuenta {
    protected String numeroCuenta;
    protected double saldo;

    public Cuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) {
            throw new IllegalArgumentException("El número de cuenta no puede estar vacío");
        }
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0;
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }

    public void depositar(double monto) throws MontoInvalidoException {
        if (monto <= 0) {
            throw new MontoInvalidoException("El monto a depositar debe ser > 0");
        }
        saldo += monto;
    }

    public void retirar(double monto) throws MontoInvalidoException, FondosInsuficientesException {
        if (monto <= 0) {
            throw new MontoInvalidoException("El monto a retirar debe ser > 0");
        }
        if (saldo < monto) {
            throw new FondosInsuficientesException("Fondos insuficientes en la cuenta " + numeroCuenta);
        }
        saldo -= monto;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
