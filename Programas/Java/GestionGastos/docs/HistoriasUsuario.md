# Historias de Usuario



### GESTIÓN GASTO



##### 1\. Registrar Gasto:

La aplicación permitirá al usuario registrar sus gastos personales, asociándoles la cantidad, fecha y una categoría (si no existe habría que crearla).



* Criterios de Aceptación:



&nbsp;	1. Criterio de Visualización:

&nbsp;	Cuando el usuario registra un nuevo gasto, entonces la aplicación debe mostrar los campos necesarios para completar el registro.



&nbsp;	2. Criterio de Validación:

&nbsp;	Cuando la info. ingresada por el usuario es incorrecta o está incompleta, entonces la aplicación debe mostrar un mensaje de error indicando el problema y 	permitir al usuario corregir la info.



&nbsp;	3. Criterio de Confirmación:

&nbsp;	Cuando la aplicación registra el gasto con éxito, muestra al usuario una confirmación.



###### 1.1 Crear Categoría:

Como usuario, si la categoría que quiero establecerle a mi gasto no existe, quiero poder crearla.



* Criterios de Aceptación:

La aplicación permite al usuario pasarle por escrito el nombre de la nueva categoría.





##### 2\. Editar Gasto:

Como usuario quiero poder editar mis gastos en cualquier momento.



* Criterios de Aceptación:

La aplicación permitirá al usuario seleccionar uno de sus gastos y modificar cualquiera de sus 3 campos (cantidad, fecha y/o categoría). Informando de fracaso (info. errónea e incompleta) o de éxito.





##### 3\. Borrar Gasto:

Como usuario quiero poder eliminar mis gastos en cualquier momento.



* Criterios de Aceptación:

La aplicación permitirá al usuario seleccionar uno de sus gastos y borrarlo, informando siempre de éxito.





### GESTIÓN ALERTAS



##### 1\. Crear Alerta:

Como usuario quiero establecer límites de gasto, y para ello quiero crear una alerta.



* Criterios de Aceptación:

La aplicación permitirá al usuario crear una alerta estableciendo la franja temporal a la que esta aplica (diaria, semanal, etc.), la cantidad límite y, opcionalmente, una categoría de gasto.



La aplicación, una vez superado el límite de gasto, emitirá una notificación.

La aplicación mostrará error si la info. es incompleta o éxito si se completó la creación.





###### 1.1 Vincular Categoría:

Como usuario, aunque sea opcional, quiero vincular una categoría de gasto con mi alerta.



* Criterios de Aceptación:

La aplicación mostrará al usuario las categorías de gasto disponibles.

Si no se encuentra entre ellas, le permitirá crearla. (funcionalidad 1.1 de gestión gasto)





##### 2\. Revisar Notificaciones:

Como usuario quiero poder revisar las notificaciones que me han saltado en cualquier momento.



* Criterios de Aceptación:

La aplicación deberá contar con un historial de notificaciones (repositorio) y darle acceso al usuario para poder revisarlo.



##### 

##### 3\. Borrar Alerta:

Como usuario quiero poder eliminar una alerta ya creada.



* Criterios de Aceptación: La aplicación permitirá al usuario seleccionar una de sus alarmas y eliminarla, informando así del éxito de la operación.





### GESTIÓN CUENTAS COMPARTIDAS



##### 1\. Crear cuenta compartida:

Como usuario, quiero crear una cuenta compartida para administrar gastos conjuntos.



* Criterios de aceptación: La aplicación muestra una opción para crear una cuenta compartida introduciendo los nombres de los participantes, si los gastos se pagarán de manera equitativa y el nombre. Tras la creación, la cuenta aparece en la lista de cuentas compartidas.





##### 2\. Añadir gasto compartido:

Como usuario, quiero añadir un nuevo gasto para que quede registrado en la cuenta compartida.



* Criterios de aceptación: La cuenta permite añadir un nuevo gasto proporcionando la cantidad de dinero, el nombre de la persona que paga y la descripción. El gasto se refleja inmediatamente en el historial de la cuenta.





##### 3\. Establecer porcentaje:

Como usuario, quiero establecer un porcentaje en un gasto para poder administrar quien paga más o menos em dicho gasto.



* Criterios de aceptación: Al añadir un gasto la aplicación pide que se introduzca el porcentaje que le corresponde pagar a cada uno a no ser de que se haya puesto pagar a partes iguales de manera fija. La suma de los porcentajes debe ser 100%.





##### 4\. Visualizar dinero pendiente:

Como usuario, quiero visualizar el dinero pendiente para ver cuanto dinero debe o se le debe a cada uno.



* Criterios de aceptación: Se puede visualizar las cifras de cada participante de la cuenta.





##### 5\. Eliminar cuenta compartida:

Como usuario, quiero eliminar una cuenta compartida para no darle más uso y liberar memoria.



* Criterios de aceptación: Al seleccionar una cuenta compartida se permite eliminarla.





### IMPORTAR DATOS



##### 1\. Leer fichero:

Como usuario, quiero poder seleccionar y leer un fichero con información de gastos, para importar datos previos sin tener que introducirlos manualmente.



* Criterios de aceptación: El sistema debe permitir seleccionar un archivo desde el dispositivo.



##### 2\. Incorporar gastos:



Como usuario, quiero que los gastos leídos desde un fichero se incorporen automáticamente a mi lista de gastos, para mantener mi información actualizada sin duplicar trabajo.



* Criterios de aceptación:

&nbsp;	Los gastos válidos deben añadirse a la base de datos del sistema.

&nbsp;	Si existen gastos duplicados, el sistema debe advertir al usuario.

&nbsp;	Debe confirmar al final cuántos registros se importaron correctamente.





### PRESENTACIÓN DE INFORMACIÓN



##### 1\. Visualizar tabla/lista:

Como usuario, quiero ver mis gastos en una tabla o lista ordenada, para revisar fácilmente las transacciones registradas.



* Criterios de aceptación: La tabla debe mostrar columnas como fecha, categoría, descripción y cantidad.



##### 2\. Diagramas de barras/circulares:

Como usuario, quiero visualizar mis gastos mediante gráficos de barras o circulares, para identificar de manera rápida cómo distribuyo mis gastos.



* Criterios de aceptación:

&nbsp;	El sistema debe permitir elegir el tipo de gráfico.

&nbsp;	Los gráficos deben actualizarse al cambiar los filtros o el rango de fechas.

&nbsp;	Las categorías deben tener colores diferenciados.



##### 3\. Visualización avanzada (opcional):

Como usuario avanzado, quiero acceder a visualizaciones adicionales, para analizar mi comportamiento financiero a lo largo del tiempo.



* Criterios de aceptación: El sistema debe ofrecer un calendario donde ver los gastos.



##### 4\. Filtrado:

Como usuario, quiero filtrar la información mostrada según categoría, fecha o cantidad, para analizar gastos específicos de manera más precisa.



* Criterios de aceptación:

&nbsp;	El sistema debe permitir múltiples filtros combinados.

&nbsp;	Los gráficos y tablas deben actualizarse al aplicar filtros.

&nbsp;	Debe haber una opción para limpiar todos los filtros fácilmente.

&nbsp;	



