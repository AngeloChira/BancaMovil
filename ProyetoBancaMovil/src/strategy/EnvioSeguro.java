package strategy;

public class EnvioSeguro implements EstrategiaEnvio {

    @Override
    public void ejecutar(String mensaje) {

        System.out.println("\n=== ENVÍO SEGURO ===");
        System.out.println("Verificando autenticación...");
        System.out.println("Canal seguro establecido.");
    }

}