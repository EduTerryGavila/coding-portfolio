# App Gastos

## Integrantes del grupo:

    - Eduardo Terry Gavilá  | eduardo.terryg@um.es | Grupo 3.4
    - Gonzalo Lucena Vázquez | gonzalo.lucenav@um.es | Grupo 3.1
    - Pedro José Aguilar Garre | pj.aguilargarre@um.es | Grupo 3.4

## Profesor responsable:

    - Pedro Delgado Yarza

## Historias de usuario y modelado:

- [Ver modelado](docs/ModeladoUML.jpeg)
- [Ver requisitos (Historias de usuario)](docs/HistoriasUsuario.md)

## Objetivo de la aplicación:

    El objetivo de la aplicación es poder administrar los gastos del usuario de manera que este pueda filtrarlos, importarlos, crear alarmas, editarlos, etc. La aplicación tambien soporta el manejo de cuentas compartidas para gastos conjuntos de manera local estableciendo un porcentaje a cada miembro del grupo en la creación de esta.

## Funcionalidad implementada:

    - Creación de gastos
    - Visualización de los gastos con gráficas
    - Filtrado de gastos
    - Importación de gastos
    - Creación y gestión de alarmas
    - Modo terminal para la creación, edicion y borrado de gastos
    - Gestión de cuentas compartidas

- [Ver proyecto](java/AppGastos/)

## Ejecución:

	- Primero se debe de ejecutar en el terminal lo siguiente: git clone https://github.com/ElNombreDelRepositorio.git
	- Después se debe de importar el proyecto en Eclipse usando la opción de importar proyecto Maven.
	- Por último se debe ejecutar la clase Main con "Run as Java Aplication" introduciendo como argumento la carpeta donde se tiene javafx, en nuestro caso: 
    "--module-path "C:\TDS\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml"

Para más informacion del uso de la aplicación, [consultar manual de usuario](docs/Manual%20de%20usuario.md).
 
