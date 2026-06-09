# BancaMovil

# Sistema de Notificaciones Multicanal

## 📌 Descripción del Proyecto
Este proyecto es una simulación de un sistema de notificaciones bancarias diseñado en Java. Permite el envío de alertas y códigos de seguridad a través de diferentes canales (Email, SMS y WhatsApp), integrando sistemas modernos y sistemas heredados (Legacy).

El objetivo principal de este repositorio es demostrar la correcta aplicación de múltiples **Patrones de Diseño de Software** para crear un código escalable, mantenible y limpio.

## 🛠️ Patrones de Diseño Implementados

Se han aplicado los siguientes patrones de diseño para resolver diferentes problemas arquitectónicos:

* **Factory Method:** Se utilizó para delegar la creación de los distintos tipos de notificaciones (`Email`, `SMS`, `WhatsApp`) sin acoplar el código cliente a las clases concretas.
* **Adapter:** Se implementó para integrar un sistema antiguo de SMS (`SistemaSMSAntiguo`) que no era compatible con la interfaz moderna de `Notificacion`.
* **Builder:** Se usó para la creación paso a paso de objetos complejos, en este caso, la construcción de la `Alerta` de seguridad con sus diferentes atributos (título, contenido, prioridad, destinatario).
* **Decorator:** Permitió añadir funcionalidades extra a los mensajes en tiempo de ejecución de forma dinámica (como marcar una `NotificacionUrgente` o `NotificacionCifrada`) sin modificar las clases base.
* **Composite:** Estructuró la jerarquía del banco en forma de árbol (`Banco` -> `Agencias` -> `Cajeros`), permitiendo que al enviar una alerta a la raíz, esta se propague automáticamente a todos los nodos hoja.
* **Singleton:** Garantizó la existencia de una única instancia del `Logger` para llevar un registro centralizado del historial de eventos del sistema.

## 💻 Tecnologías Utilizadas
* **Lenguaje:** Java
* **IDE:** IntelliJ IDEA
* **Paradigma:** Programación Orientada a Objetos (POO)

## 🚀 Cómo ejecutar el proyecto
1. Clonar o descargar el repositorio.
2. Abrir el proyecto en IntelliJ IDEA (se recomienda usar el archivo `.iml` para cargar la configuración automáticamente).
3. Ejecutar la clase principal ubicada en `src/app/AppMain.java`.
4. Seguir las instrucciones en la consola para interactuar con el menú multicanal.
