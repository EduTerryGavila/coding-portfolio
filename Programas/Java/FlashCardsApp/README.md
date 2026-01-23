# FlashCards App

## Casos de uso y modelado:

- [Ver casos de uso](requisitos/CasosDeUso.md)
- [Ver modelado](diseño/modelado/modelado.jpg)


## Objetivo de la aplicación:
	
	El objetivo de la aplicación es el de poder crear o importar cursos para poder practicar en algún ámbito de interés usando diferentes técnicas de aprendizaje. También como objetivo se tiene el mejorar tus conocimientos y comprobar tus resultados consultando las estadísticas.

## Funcionalidad implementada:

	- Inicio de sesión y registro en la aplicación.
	- Creación de cursos con 3 tipos de pregunta.
	- Posibilidad de utilizar 3 técnicas de aprendizaje.
	- Posibilidad de guardar los cursos.
	- Importación/Exportación de cursos.
	- Estadísticas consultables por el usuario.
	- Posibilidad de eliminar y editar cursos.
	- Funcionalidad para buscar cursos. (Extra).

- [Ver proyecto](java/src/main/java/com/miapp/flashcards)

## Cubrimiento y tests unitarios:

	El cubrimiento es bastante bueno aunque el global sea un 48.5% ya que incluye la carpeta test la cual ha sido ejecutada al 0% al cubrir la app. La importante es la carpeta src/main/java la cual ha obtenido un 82.3%.

- [Ver cubrimiento](documentacion/Pruebas/Cubrimiento.jpg)

	Las pruebas unitarias han sido satisfactorias.

- [Ver pruebas unitarias](documentacion/Pruebas/TestUnitarios.jpg)

## Ejecución:

	- Primero se debe de ejecutar en el terminal lo siguiente: git clone https://github.com/EduTerryGavila/PDS-enero.git
	- Después se debe de importar el proyecto en Eclipse usando la opción de importar proyecto Maven.
	- Por último se debe ejecutar la clase Main con "Run as Java Aplication".
	
## Ficheros:

Se han creado unos JSON de prueba para probar la aplicación. [Pulsa aquí](documentacion/EjemplosJSON) para verlos.

Para más información sobre el uso de la aplicación, [consultar el manual de usuario](documentacion/ManualUsuario.md).


