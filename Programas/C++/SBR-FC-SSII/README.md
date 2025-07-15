## Sistema Basado en Reglas con Factores de Certeza

Este proyecto implementa un **sistema basado en reglas** que emplea **factores de certeza** (valores en el rango \[-1, 1]) para razonar sobre hechos y determinar el grado de confianza en metas o conclusiones. Se utiliza retropropagación de metas (backward chaining) para calcular el factor de certeza final de las metas solicitadas.

---

### Características principales

* **Encabezamiento hacia atrás** para la inferencia.
* **Factores de certeza** en cada regla y hecho.
* Uso de la librería estándar de C++ `regex` para procesamiento de expresiones regulares.
* Conjunto de pruebas (pruebas 1, 2, 3 y A) que muestran el paso a paso del cálculo de los factores de certeza.

---

## Estructura del proyecto

```text
C:.
│   Practica2-Cuestiones.pdf
│   Practica2-informe.pdf
│
├───Pruebas
│   ├───prueba1
│   │       BC-1.txt
│   │       BC-1BH-1.txt
│   │       BH-1.txt
│   │       Prueba-1--(completo).pdf
│   │       RedInferenciaCompleta.jpeg
│   │
│   ├───prueba2
│   │       BC-2.txt
│   │       BC-2BH-2-EST.txt
│   │       BC-2BH-2-RM.txt
│   │       BH-2-EST.txt
│   │       BH-2-RM.txt
│   │       Prueba-2--(enunciado-y-formalizacion).pdf
│   │       RedInferenciaGanaEST.jpeg
│   │       RedInferenciaGanaESTCompleta.jpeg
│   │       RedInferenciaGanaRM.jpeg
│   │       RedInferenciaGanaRMCompleta.jpeg
│   │
│   ├───prueba3
│   │       BC-3.txt
│   │       BC-3BH-3.txt
│   │       BH-3.txt
│   │       Prueba-3--(enunciado).pdf
│   │       Prueba-3--(formalizacion).pdf
│   │       RedInferencia.jpeg
│   │       RedInferenciaCompleta.jpeg
│   │
│   └───pruebaA
│           BC-A.txt
│           BC-ABH-A.txt
│           BH-A.txt
│           Prueba-A--(enunciado-y-formalizacion).pdf
│           RedInferencia.jpeg
│           RedInferenciaCompleta.jpeg
│
└───Software
        ejecutable.exe
        ManualDeUso.pdf
        SBR-FC.cpp
```
---

## Ejecución

Para ejecutar el programa, desplázate al directorio de la prueba que desees utilizar y ejecuta:

```bash
./programa ./BC.txt ./BH.txt
```

* `BC.txt`: Fichero de **Base de Conocimientos** (reglas y factores de certeza).
* `BH.txt`: Fichero de **Base de Hechos** (hechos iniciales y sus factores de certeza).

> *Ejemplo:*
>
> ```bash
> cd Pruebas/prueba1
> ./programa BC.txt BH.txt
> ```

---

## Pruebas incluidas

* **Prueba 1**: Caso básico con pocas reglas.
* **Prueba 2**: Ejemplo intermedio con dos casos a calcular.
* **Prueba 3**: Caso avanzado con múltiples dependencias.
* **Prueba A**: Caso personalizado.

Cada carpeta de prueba contiene sus ficheros `BC.txt` y `BH.txt` con detalles específicos.

---

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el fichero `LICENSE` para más información.

---

**¡Gracias por tu interés en este proyecto!**
