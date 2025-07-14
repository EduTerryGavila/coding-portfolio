/*
Asignatura: (1903) Bases de Datos
Curso: 2024/25
Convocatoria: junio

Practica: P2. Consultas en SQL

Equipo de practicas: bdxxyy <----- PON TU CUENTA AQUI
 Integrante 1:              <----- NOMBRES COMPLETOS
 Integrante 2: 
*/

--S1.1.
SELECT s.titulo, s.genero, s.edad_minima
FROM serie s
WHERE s.nacionalidad NOT IN('Espana') AND s.edad_minima < 18;

--S1.2.
SELECT UNIQUE e.serie, e.temporada
FROM estoy_viendo e
ORDER BY e.serie, e.temporada;

--S1.3.
SELECT *
FROM capitulo c
WHERE c.temporada > 2 AND c.duracion < 45
ORDER BY c.serie, c.temporada;

--S1.4.
SELECT u.usuario_id, u.nombre, u.f_registro
FROM usuario u
WHERE u.cuota < 101 AND u.cuota > 49 AND u.f_registro > TO_DATE('20/05/2020', 'dd/mm/yyyy')
ORDER BY u.f_registro DESC;

--S1.5.
SELECT UNIQUE e.usuario
FROM estoy_viendo e
WHERE e.f_ultimo_acceso < TO_DATE('05/02/2025', 'dd/mm/yyyy') AND e.minuto > 15
ORDER BY e.usuario;

--S1.6.
SELECT n.interprete_id, n.nombre, n.a_nacimiento 
FROM interprete n
WHERE n.a_nacimiento > 1969 AND n.a_nacimiento < 1980 AND n.nacionalidad = 'Reino Unido'
ORDER BY n.a_nacimiento, n.nombre;

--S1.7.
SELECT u.nombre, u.email, TO_CHAR(u.f_registro, 'Month')
FROM usuario u
WHERE u.nombre LIKE '%ia%';

--S1.8.
SELECT u.usuario_id, u.nombre, ROUND(u.cuota + u.cuota*0.05, 2) nueva_cuota
FROM usuario u
WHERE u.f_registro < TO_DATE('01/01/2021', 'dd/mm/yyyy') AND u.cuota < 80
ORDER BY u.usuario_id;

--S1.9.
SELECT u.nombre, ROUND((SYSDATE-u.f_registro)/365.25, 1) tiempo_registro
FROM usuario u
WHERE ((SYSDATE-u.f_registro)/365.25) > 5
ORDER BY u.nombre;

--S1.10.
SELECT e.usuario, e.serie, e.temporada, e.capitulo, e.minuto, ROUND(SYSDATE-e.f_ultimo_acceso,0)
FROM estoy_viendo e
WHERE e.usuario IN('U222', 'U777') AND e.minuto > 20;

--S1.11.
SELECT n.nombre, n.nacionalidad, r.rol
FROM reparto r
    JOIN interprete n ON n.interprete_id = r.interprete
WHERE r.serie = 'S001'
ORDER BY r.rol;

--S1.12.
SELECT s.serie_id, s.titulo, q.etiqueta
FROM etiquetado q
    JOIN serie s ON s.serie_id = q.serie
WHERE s.nacionalidad = 'Estados Unidos' AND s.genero = 'Drama'
ORDER BY s.serie_id;

--S1.13.
SELECT UNIQUE n.nombre, n.a_nacimiento, r.rol
FROM interprete n
    JOIN reparto r ON r.interprete = n.interprete_id
WHERE n.nacionalidad = 'Reino Unido' AND r.rol IN('Protagonista', 'Secundario')
ORDER BY n.nombre;

--S1.14.
SELECT s.titulo, i.f_interes
FROM interes i
    JOIN serie s ON s.serie_id = i.serie
    JOIN usuario u ON u.usuario_id = i.usuario
WHERE u.nombre = 'Turiano'
ORDER BY s.titulo DESC;

--S1.15.
SELECT r.interprete, n.nombre, s.serie_id, s.titulo
FROM reparto r
    JOIN serie s ON s.serie_id = r.serie
    JOIN interprete n ON n.interprete_id = r.interprete
WHERE r.rol = 'Figuracion';

--S1.16.
SELECT s.titulo, t.temporada, t.a_estreno, c.capitulo, c.titulo
FROM serie s
    JOIN capitulo c ON c.serie = s.serie_id
    JOIN temporada t ON t.serie = s.serie_id AND t.temporada = c.temporada
WHERE s.genero = 'Ciencia Ficcion' AND c.duracion < 54 AND c.duracion > 49
ORDER BY s.titulo, t.temporada;

--S1.17.
SELECT u.nombre, u.email, s.titulo, e.temporada, e.capitulo
FROM estoy_viendo e
    JOIN serie s ON s.serie_id = e.serie
    JOIN usuario u ON u.usuario_id = e.usuario
WHERE s.genero = 'Policiaca'
ORDER BY u.nombre DESC;

--S1.18.
SELECT s.titulo, u.nombre
FROM interes n
    JOIN serie s ON s.serie_id = n.serie
    JOIN usuario u ON u.usuario_id = n.usuario
WHERE EXTRACT(YEAR FROM u.f_registro) < 2019
ORDER BY s.titulo;

--S1.19.
SELECT u.nombre, s.titulo, c.titulo, e.minuto
FROM estoy_viendo e
    JOIN usuario u ON u.usuario_id = e.usuario
    JOIN serie s ON s.serie_id = e.serie
    JOIN capitulo c ON c.serie = e.serie AND e.temporada = c.temporada AND c.capitulo = e.capitulo
WHERE e.f_ultimo_acceso < TO_DATE('01/02/2025', 'dd/mm/yyyy')
ORDER BY u.nombre;

--S1.20.
SELECT UNIQUE n.nombre, 2025-n.a_nacimiento
FROM interprete n
    JOIN reparto r ON r.interprete = n.interprete_id
    JOIN estoy_viendo e ON e.serie = r.serie
WHERE n.nacionalidad = 'Espana' AND n.a_nacimiento > 1985;

--S1.21.
SELECT UNIQUE s.titulo, s.genero, t.a_estreno
FROM interes n
    JOIN serie s ON s.serie_id = n.serie
    JOIN temporada t ON t.serie = s.serie_id
WHERE n.vista = 'SI' AND t.temporada = 1
ORDER BY s.titulo;

--S1.22.
SELECT u.nombre, COALESCE(n.serie,'SIN INTERESES') serie_id
FROM interes n
    RIGHT JOIN usuario u ON u.usuario_id = n.usuario
ORDER BY u.nombre DESC;

--S1.23.
SELECT s.titulo, COALESCE(q.etiqueta, '*SIN ETIQUETAS*')
FROM serie s
    LEFT JOIN etiquetado q ON  q.serie = s.serie_id
WHERE s.genero = 'Policiaca'
ORDER BY s.titulo, q.etiqueta;

--1.24.
SELECT unique s.titulo, COALESCE(u.nombre, '*NADIE*') nombre_usuario
FROM serie s
    LEFT JOIN estoy_viendo e ON e.serie = s.serie_id
    LEFT JOIN usuario u ON u.usuario_id = e.usuario
ORDER BY  s.titulo, nombre_usuario;

--1.25.
SELECT UNIQUE u.nombre, COALESCE(s.titulo, '*NADA*') titulo_serie
FROM usuario u
    LEFT JOIN estoy_viendo e ON e.usuario = u.usuario_id
    LEFT JOIN serie s ON s.serie_id = e.serie
ORDER BY u.nombre, titulo_serie;

--1.26.
SELECT serie_id
FROM serie
MINUS
SELECT serie
FROM etiquetado;

--1.27.
SELECT serie_id
FROM serie
WHERE genero NOT IN ('Policiaca')
MINUS
SELECT serie
FROM estoy_viendo;

--1.28.
(SELECT n.serie
FROM interes n
INTERSECT
SELECT q.serie
FROM etiquetado q
WHERE q.etiqueta = 'Thriller')
MINUS
SELECT e.serie
FROM estoy_viendo e;

--1.29.
SELECT r.interprete
FROM reparto r
WHERE r.rol = 'Protagonista'
INTERSECT
SELECT re.interprete
FROM reparto re
WHERE re.rol = 'Reparto';

--1.30.
SELECT n.usuario
FROM interes n
MINUS
SELECT e.usuario
FROM estoy_viendo e;

--1.31.
SELECT n.serie serie, n.usuario usuario
FROM interes n
WHERE n.f_interes > TO_DATE('15/01/2023', 'dd/mm/yyyy')
MINUS
(SELECT e.serie, e.usuario
FROM estoy_viendo e
INTERSECT
SELECT ni.serie, ni.usuario
FROM interes ni
WHERE ni.f_interes > TO_DATE('15/01/2023', 'dd/mm/yyyy'))
ORDER BY serie, usuario;

--1.32.
SELECT e.usuario usuario
FROM estoy_viendo e
WHERE SYSDATE-e.f_ultimo_acceso > 6*30
UNION
SELECT u.usuario_id
FROM usuario u
WHERE u.cuota < 70 AND EXTRACT(YEAR FROM u.f_registro) > 2021
ORDER BY usuario;

--1.33.
SELECT s.serie_id
FROM serie s
WHERE s.genero = 'Drama' AND s.edad_minima = 18
INTERSECT
SELECT c.serie
FROM capitulo c
WHERE c.temporada = 2 AND c.duracion < 101 AND c.duracion > 59;

--1.34.
SELECT n.serie
FROM interes n
    JOIN usuario u ON u.usuario_id = n.usuario
WHERE u.cuota < 46 AND u.cuota > 29
UNION
SELECT s.serie_id
FROM serie s
WHERE s.edad_minima < 16
UNION
SELECT c.serie
FROM capitulo c
WHERE c.titulo LIKE '%4%';

--1.35.
(SELECT r.interprete
FROM reparto r
WHERE r.rol = 'Reparto' 
INTERSECT 
SELECT n.interprete_id
FROM interprete n
WHERE n.a_nacimiento < 1990 AND n.a_nacimiento > 1979)
MINUS
SELECT re.interprete
FROM reparto re
    JOIN serie s ON s.serie_id = re.serie
WHERE s.nacionalidad = 'Estados Unidos';

--2.1.
SELECT s.titulo, s.genero
FROM serie s
WHERE s.serie_id NOT IN (SELECT e.serie
                    FROM estoy_viendo e);
                    
--2.2.
SELECT u.nombre, u.f_registro, u.cuota
FROM usuario u
WHERE u.usuario_id IN (SELECT e.usuario
                      FROM estoy_viendo e
                      WHERE e.f_ultimo_acceso < SYSDATE-1460);
                      
--2.3.
SELECT UNIQUE q.etiqueta
FROM etiquetado q
WHERE q.serie IN (SELECT n.serie
                  FROM interes n
                  WHERE n.usuario NOT IN (SELECT e.usuario
                                          FROM estoy_viendo e));
                                          
--2.4.
SELECT u.nombre, u.f_registro, u.email
FROM usuario u
WHERE u.usuario_id NOT IN(SELECT n.usuario
                          FROM interes n
                          WHERE n.serie IN (SELECT s.serie_id
                                            FROM serie s
                                            WHERE s.nacionalidad = 'Espana'))
ORDER BY u.nombre;

--2.5.
SELECT i.nombre, i.nacionalidad, i.a_nacimiento
FROM interprete i
WHERE i.interprete_id IN (SELECT r.interprete
                          FROM reparto r
                          WHERE r.rol = 'Protagonista' AND r.serie IN (SELECT s.serie_id
                                                                        FROM serie s
                                                                        WHERE s.genero = 'Policiaca'));
                            
--2.6.
SELECT i.nombre, i.nacionalidad
FROM interprete i
WHERE i.nacionalidad NOT IN ('Espana') AND i.interprete_id IN (SELECT r.interprete
                                                                FROM reparto r
                                                                WHERE r.serie IN (SELECT s.serie_id
                                                                                  FROM serie s
                                                                                  WHERE s.nacionalidad = 'Espana'))
ORDER BY i.nombre;

--2.7.
SELECT u.usuario_id, u.nombre, u.email
FROM usuario u
WHERE u.usuario_id IN (SELECT e.usuario
                        FROM estoy_viendo e
                        WHERE e.serie IN (SELECT r.serie
                                          FROM reparto r
                                          WHERE r.interprete IN (SELECT i.interprete_id
                                                                 FROM interprete i
                                                                 WHERE i.a_nacimiento < 2001 AND i.a_nacimiento > 1989)));

--2.8.
SELECT u.usuario_id, u.nombre, u.cuota
FROM usuario u
WHERE u.usuario_id IN (SELECT e.usuario
                        FROM estoy_viendo e
                        WHERE e.capitulo = 1 AND e.temporada = 1 AND e.serie IN (SELECT t.serie
                                                                                 FROM temporada t
                                                                                 WHERE 2 > ALL (SELECT te.temporada
                                                                                                FROM temporada te
                                                                                                WHERE te.serie = e.serie)));

--2.9.
SELECT s.titulo, s.nacionalidad, s.edad_minima
FROM serie s
WHERE s.genero = 'Drama' AND s.serie_id IN (SELECT q.serie
                                        FROM etiquetado q
                                        WHERE q.etiqueta IN (SELECT qe.etiqueta
                                                             FROM etiquetado qe
                                                             WHERE qe.etiqueta = q.etiqueta AND qe.serie IN (SELECT se.serie_id
                                                                                                             FROM serie se
                                                                                                             WHERE se.genero = 'Policiaca')));

--2.10.
SELECT u.usuario_id, u.nombre, u.f_registro
FROM usuario u
WHERE (u.cuota < 65 AND u.usuario_id IN (SELECT n.usuario
                                        FROM interes n
                                        WHERE n.f_interes > TO_DATE('01/01/2024','dd/mm/yyyy')) OR (EXTRACT(YEAR FROM u.f_registro) = 2022 AND u.usuario_id NOT IN (SELECT e.usuario
                                                                                                                                                                    FROM estoy_viendo e)));

--2.11.
SELECT s.serie_id, s.titulo
FROM serie s
WHERE s.serie_id IN (SELECT c.serie
                     FROM capitulo c
                     WHERE c.temporada = 2 AND c.duracion > ALL (SELECT ca.duracion
                                                                 FROM capitulo ca
                                                                 WHERE ca.temporada = 3 AND ca.serie = c.serie));

--2.12.
SELECT s.titulo, s.nacionalidad
FROM serie s
WHERE s.serie_id IN (SELECT t.serie
                    FROM temporada t
                    WHERE t.temporada = 1 AND t.serie IN (SELECT te.serie
                                                          FROM temporada te
                                                          WHERE te.temporada = 2 AND te.serie = t.serie AND te.a_estreno-t.a_estreno >= 2));
                                               
--2.13.
SELECT s.serie_id, s.titulo, s.genero
FROM serie s
WHERE s.serie_id IN (SELECT e.serie
                     FROM estoy_viendo e
                     WHERE e.serie NOT IN (SELECT n.serie
                                           FROM interes n
                                           WHERE e.usuario = n.usuario));
                                           
--2.14.
SELECT q.etiqueta, COUNT(*) n_series
FROM etiquetado q
GROUP BY q.etiqueta
ORDER BY n_series;

--2.15.
SELECT s.nacionalidad, COUNT(*) n_series
FROM serie s
GROUP BY s.nacionalidad
ORDER BY n_series;

--2.16.
SELECT s.genero, COUNT(*)
FROM serie s
WHERE s.edad_minima > 12
GROUP BY s.genero
ORDER BY s.genero DESC;

--2.17.
SELECT n.usuario, COUNT(*)
FROM interes n
WHERE n.vista = 'SI'
GROUP BY n.usuario;

--2.18.
SELECT n.serie, COUNT(*)
FROM interes n
GROUP BY n.serie
ORDER BY n.serie;

--2.19.
SELECT q.etiqueta
FROM etiquetado q
GROUP BY q.etiqueta
HAVING COUNT(*) > 3;

--2.20.
SELECT COUNT(*) n_series
FROM (SELECT c.serie
      FROM capitulo c
      WHERE c.titulo IS NULL
      GROUP BY c.serie);

--2.21.
SELECT n.nacionalidad
FROM interprete n
GROUP BY n.nacionalidad
HAVING COUNT(*) = 1
ORDER BY n.nacionalidad;

--2.22.
SELECT t.serie, COUNT(*)
FROM temporada t
GROUP BY t.serie
HAVING COUNT(*) > 3;

--2.23.
SELECT n.serie, COUNT(*)
FROM interes n
WHERE n.serie IN (SELECT r.serie
                  FROM reparto r
                  WHERE r.interprete IN (SELECT n.interprete_id
                                         FROM interprete n
                                         WHERE n.nombre = 'David Tennant'))
GROUP BY n.serie;

--2.24.
SELECT AVG(u.cuota)
FROM usuario u
WHERE u.usuario_id IN (SELECT n.usuario
                        FROM interes n
                        GROUP BY n.usuario
                        HAVING COUNT(*) > 3);
                        
--2.25.
SELECT s.titulo, s.nacionalidad, s.genero
FROM serie s
WHERE s.serie_id IN (SELECT t.serie
                     FROM temporada t
                     GROUP BY t.serie
                     HAVING COUNT(*) > 3) AND s.nacionalidad NOT IN ('Reino Unido') AND s.edad_minima < 18;

--2.26.
SELECT c.serie, c.temporada, COUNT(*) n_capitulos
FROM capitulo c
WHERE c.duracion < 45 AND c.serie IN (SELECT t.serie
                                      FROM temporada t
                                      GROUP BY t.serie
                                      HAVING COUNT(*) > 2)
GROUP BY c.serie, c.temporada;

--2.27.
SELECT u.nombre, u.cuota
FROM usuario u
WHERE u.usuario_id IN (SELECT e.usuario
                       FROM estoy_viendo e
                       WHERE e.serie IN (SELECT t.serie
                                         FROM temporada t
                                         GROUP BY t.serie
                                         HAVING COUNT(*) = 2));
                                         
--2.28.
SELECT u.nombre, u.f_registro, u.cuota
FROM usuario u
WHERE u.usuario_id IN (SELECT n.usuario
                       FROM interes n
                       GROUP BY n.usuario
                       HAVING COUNT(*) > 5);
                       
--2.29.
SELECT n.nombre, n.nacionalidad, n.a_nacimiento
FROM interprete n
WHERE n.interprete_id IN (SELECT r.interprete
                       FROM reparto r
                       GROUP BY r.interprete
                       HAVING COUNT(*) > 1)
ORDER BY n.nombre;

--2.30.
SELECT u.nombre
FROM usuario u
WHERE u.usuario_id IN (SELECT e.usuario
                       FROM estoy_viendo e
                       GROUP BY e.usuario, e.serie
                       HAVING COUNT(*) > 1);

--2.31.
SELECT r.interprete, COUNT(*)
FROM reparto r
WHERE r.serie IN (SELECT s.serie_id
                  FROM serie s
                  WHERE s.nacionalidad = 'Reino Unido') AND r.interprete IN (SELECT n.interprete_id
                                                                             FROM interprete n
                                                                             WHERE n.nacionalidad = 'Estados Unidos')
GROUP BY r.interprete;

--2.32.
SELECT s.serie_id, COUNT(DISTINCT e.usuario)
FROM estoy_viendo e
    RIGHT JOIN serie s ON s.serie_id = e.serie
GROUP BY s.serie_id
ORDER BY s.serie_id;

--2.33.
SELECT u.usuario_id, COUNT(DISTINCT e.serie) num_series
FROM estoy_viendo e
    RIGHT JOIN usuario u ON u.usuario_id = e.usuario
GROUP BY u.usuario_id
ORDER BY num_series DESC;

--2.34.
SELECT n.usuario, COUNT(*) n_series
FROM interes n
WHERE n.vista = 'NO' AND (n.serie, n.usuario) NOT IN (SELECT e.serie, e.usuario
                                                      FROM estoy_viendo e)
GROUP BY n.usuario;

--2.35.
SELECT c.serie, COUNT(DISTINCT c.temporada) n_temporadas , COUNT(c.capitulo) n_capitulos
FROM capitulo c
WHERE c.serie IN (SELECT r.serie
                  FROM reparto r
                  WHERE r.interprete IN (SELECT n.interprete_id
                                         FROM interprete n
                                         WHERE n.nacionalidad = 'Estados Unidos'))
GROUP BY c.serie
ORDER BY c.serie;