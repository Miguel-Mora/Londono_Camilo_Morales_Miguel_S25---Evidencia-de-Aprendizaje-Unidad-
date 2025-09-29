package modelo;

import java.util.*;


public class Socio {
    private String nombre;
    private String cedula;
    private List<Cuenta> cuentas;

    public Socio(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.cuentas = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public List<Cuenta> getCuentas() { return cuentas; }

    public void abrirCuenta(Cuenta cuenta) throws Exception {
        Optional<Cuenta> existe = cuentas.stream()
                .filter(c -> c.getNumeroCuenta().equals(cuenta.getNumeroCuenta()))
                .findFirst();
        if (existe.isPresent()) {
            throw new Exception("El número de cuenta ya existe para este socio.");
        }
        cuentas.add(cuenta);
    }

    @Override
    public String toString() {
        return "Socio {" +
                "nombre = '" + nombre + '\'' +
                ", cedula = '" + cedula + '\'' +
                ", cuentas = " + cuentas.size() +
                '}';
    }
}