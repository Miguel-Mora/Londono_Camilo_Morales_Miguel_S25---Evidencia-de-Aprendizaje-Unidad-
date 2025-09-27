package servicio;

import modelo.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Cooperativa {

    private static final Cooperativa INSTANCE = new Cooperativa();

    private final List<Socio> socios = new ArrayList<>();

    private Cooperativa() {}

    public static Cooperativa getInstance() {
        return INSTANCE;
    }

    public void registrarSocio(Socio socio) throws Exception {
        Objects.requireNonNull(socio, "El socio no puede ser null");
        boolean existe = socios.stream()
                .anyMatch(s -> Objects.equals(s.getCedula(), socio.getCedula()));
        if (existe) {
            throw new Exception("Ya existe un socio con cédula " + socio.getCedula());
        }
        socios.add(socio);
    }

    public List<Socio> getSocios() {
        return Collections.unmodifiableList(socios);
    }

    public void listarSocios() {
        socios.forEach(s -> System.out.println(s));
    }

    public void listarSociosResumen() {
        socios.forEach(s ->
                System.out.println(s.getNombre() + " (" + s.getCedula() + ") -> " + s.getCuentas().size() + " cuentas"));
    }

    public Optional<Socio> buscarSocioPorCedula(String cedula) {
        return socios.stream()
                .filter(s -> Objects.equals(s.getCedula(), cedula))
                .findFirst();
    }

    public List<Cuenta> filtrarCuentasPorSaldo(double monto) {
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .filter(c -> c.getSaldo() > monto)
                .collect(Collectors.toList());
    }

    public double obtenerSumaTotalSaldos() {
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .mapToDouble(Cuenta::getSaldo)
                .sum();
    }


    public List<Socio> filtrarSocios(Predicate<Socio> criterio) {
        return socios.stream()
                .filter(criterio)
                .collect(Collectors.toList());
    }

   
    public <T extends Cuenta> List<T> cuentasDeTipo(Class<T> tipo) {
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .filter(tipo::isInstance)
                .map(tipo::cast)
                .collect(Collectors.toList());
    }

    public void paraCadaCuenta(Consumer<Cuenta> accion) {
        socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .forEach(accion);
    }
}
