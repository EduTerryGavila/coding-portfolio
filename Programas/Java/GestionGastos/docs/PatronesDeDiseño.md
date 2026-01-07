# Explicación de Patrones de Diseño

## Adaptador + Factoría
Se han implementado estos patrones, conforme a los requisitos del enunciado, para facilitar la importación de gastos desde múltiples formatos externos. Esta combinación permite extender la funcionalidad de importación sin necesidad de alterar el código base del sistema.

## Estrategia
Se ha aplicado el patrón en el sistema de alertas. Tal y como se especificaba, este patrón permite encapsular la lógica de las alarmas para definir su comportamiento de forma dinámica, variando según la periodicidad configurada (por ejemplo, límites semanales o mensuales).

## Singleton
Este patrón se utiliza en los repositorios (como se observa en el `ControladorPrincipal`). Su propósito es garantizar que exista una única instancia de la clase encargada de los datos, permitiendo un punto de acceso global y centralizado a la información en toda la aplicación.

## Composite
Se ha seleccionado el patrón **Composite** para el módulo de filtros. Dado que la aplicación requiere filtrar por criterios simples (mes, categoría, fecha) y también por combinaciones complejas de estos, este patrón resulta idóneo al permitir tratar de manera uniforme tanto a los filtros individuales como a las composiciones de filtros.