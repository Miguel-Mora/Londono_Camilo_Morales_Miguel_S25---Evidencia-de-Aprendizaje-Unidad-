package app;

import modelo.*;
import servicio.Cooperativa;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // 1) Obtener la instancia única (Singleton)
            Cooperativa coop = Cooperativa.getInstance();

            // 2) Crear socios y cuentas
            Socio s1 = new Socio("Ana Gómez", "123");
            Socio s2 = new Socio("Luis Torres", "456");

            CuentaAhorros c1 = new CuentaAhorros("C001", 0.02);
            CuentaAhorros c2 = new CuentaAhorros("C002", 0.03);

            // 3) Abrir cuentas para cada socio
            s1.abrirCuenta(c1);
            s2.abrirCuenta(c2);

            // 4) Registrar socios (evita duplicados por cédula)
            coop.registrarSocio(s1);
            coop.registrarSocio(s2);

            // 5) Transacciones (lanzan o no excepción según el tipo)
            Transaccion d1 = new Deposito(c1, 5000);
            d1.ejecutar();

            Transaccion r1 = new Retiro(c1, 2000);
            r1.ejecutar();

            // 6) Listados con lambdas explícitas
            System.out.println("=== Lista de socios ===");
            coop.listarSocios(); // usa lambda en Cooperativa

            System.out.println("\n=== Resumen de socios (nombre, cédula, #cuentas) ===");
            coop.listarSociosResumen(); // usa lambda en Cooperativa

            // 7) Búsqueda con Optional
            System.out.println("\n=== Buscar socio por cédula ===");
            coop.buscarSocioPorCedula("123").ifPresentOrElse(
                socio -> System.out.println("Encontrado: " + socio),
                () -> System.out.println("No existe socio con esa cédula")
            );

            // 8) Filtro funcional por saldo
            System.out.println("\n=== Cuentas con saldo mayor a 1000 ===");
            coop.filtrarCuentasPorSaldo(1000).forEach(c -> System.out.println(c));

            // 9) Suma total de saldos (Streams + method reference)
            System.out.println("\nSuma total de saldos: " + coop.obtenerSumaTotalSaldos());

            // 10) Genéricos: obtener solo cuentas de ahorro
            System.out.println("\n=== Cuentas de tipo CuentaAhorros (genéricos) ===");
            List<CuentaAhorros> ahorros = coop.cuentasDeTipo(CuentaAhorros.class);
            ahorros.forEach(ca -> System.out.println(
                "Cuenta " + ca.getNumeroCuenta() + " | saldo=" + ca.getSaldo() + " | interes=" + ca.getInteres()
            ));

            // 11) Acciones funcionales sobre todas las cuentas
            System.out.println("\n=== Acciones funcionales sobre todas las cuentas ===");
            // Imprimir número de cuenta con lambda explícita
            coop.paraCadaCuenta(c -> System.out.println("Cuenta -> " + c.getNumeroCuenta()));

            // Aplicar intereses SOLO a CuentaAhorros usando genéricos + forEach lambda
            ahorros.forEach(ca -> ca.aplicarInteres());
            System.out.println("\nSaldos luego de aplicar intereses a cuentas de ahorro:");
            ahorros.forEach(ca -> System.out.println(ca));

            // 12) Ejemplo de filtro de socios (criterio arbitrario con lambda)
            System.out.println("\n=== Socios con al menos 1 cuenta ===");
            coop.filtrarSocios(s -> s.getCuentas().size() >= 1)
                .forEach(s -> System.out.println(s.getNombre() + " -> " + s.getCuentas().size() + " cuenta(s)"));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
