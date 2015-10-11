## Tabla de contenidos
* [Programa](#programa)
    * [Equipo](#equipo)
    * [Objetivo del proyecto](#Objetivo del proyecto)
    * [Detalles del desarrollo](#Detalles del desarrollo)
    * [Servicio de encomiendas ChilExplox](#Servicio de encomiendas ChilExplox)
        * [Descripción larga](#Descripción larga)
    * [Procedimiento de instalación](#Procedimiento de instalación)
* [Bibliografía](#bibliografia)
* [Política de integridad académica](#politica-de-integridad-academica)

# Programa

## Equipo

| Nombre                | Github        | Email         |
|:--------------------- |:--------------|:--------------|
| Thomas Pryce Jones    | [@thomprycejones](https://github.com/thomprycejones) | ?@uc.cl |
| Pedro Saratscheff     | [@psaratscheff](https://github.com/psaratscheff) | pasaratscheff@uc.cl |
| Felix Schiegg         | [@flargofelix](https://github.com/flargofelix) | fschiegg1@uc.cl |

## Objetivo del proyecto

El objetivo del proyecto grupal es que los alumnos logren tener un acercamiento real a lo que es el desarrollo de software en la actualidad. Se busca que los alumnos puedan aplicar los conocimientos adquiridos en el curso durante el semestre, de tal forma que una vez finalizado el proyecto tengan una concepción propia de los factores que ayudan a desarrollar un software desde el punto de vista ingenieril (Levantamiento de requisitos, diseño, codificación y testing).

## Detalles del desarrollo
* Se desarrolla la aplicación ChilExplox en Java.

## Servicio de encomiendas ChilExplox

### Descripción larga

El gerente de informática de la reconocida empresa de servicios de encomiendas ChilExplox, ha observado el sorprendente crecimiento que está teniendo la empresa. Preocupado por la estabilidad y buen funcionamiento del negocio, ha decidido mejorar el sistema inform ́atico que controla el servicio de encomiendas. En su intento se vio enfrentado a un sistema legado, mal diseñado, mal documentado y con ausencia total de refactory, ante tal descubrimiento ha decidido desarrollar un nuevo software, dado a que no cuenta con el equipo de trabajo adecuado, ha decidió externalizar, pidiendo la ayuda a los insignes alumnos de la PUC.

A continuación se procede a detallar el giro de la empresa y elementos que el gerente considera importantes:

La empresa cuenta con diversas sucursales en todo el país, en cada sucursal se reciben a diario decenas de encargos para enviar a otra sucursal en el país, por lo que en cada oficina debe existir un computador con acceso a la información propia de las encomiendas del lugar donde se está. Será necesario entonces, que el usuario tenga la opción de elegir la sucursal que está administrando en determinado momento. (O sea, en un mismo computador se puede tener acceso a la información de todas las sucursales existentes).

Diariamente llegan varios clientes con el objetivo de enviar encomiendas a todo Chile. Cuando son atendidos, el usuario del sistema debe poder crear un nuevo pedido, donde se ponen los datos del cliente (Nombre, número de teléfono, dirección) y además se deben colocar los datos de la encomienda (Peso, volumen, sucursal de destino, dirección de destino y prioridad). Tras llenar los datos de la encomienda (no necesariamente llenados los del cliente), el usuario tiene acceso a un presupuesto del costo del envío, calculado en función de las características de la encomienda. Para un mismo cliente se pueden asociar diversas encomiendas antes de cerrar el pedido (solo se puede cerrar el pedido si se llenan los datos del cliente). Una vez se tiene listo el pedido (la suma de encomiendas) se ve el monto final que debe pagar el cliente.

Lo primero que debería suceder es que los pedidos se mantengan ordenados según la urgencia que tienen.
Cada pedido tiene un estado, el cual debe comenzar con el valor “En origen”, indicando que aún está en la sucursal desde donde se envía.

La empresa cuenta con una flota de camiones distribuidos en las diversas sucursales. El usuario del sistema puede ver la disponibilidad de camiones y puede escoger enviar algún pedido (a su elección). Cuando se envía un camión con un pedido (considere que un cami ́on puede llevar un número fijo pedidos, pero a una misma sucursal. Ese número es determinado por ustedes) , el estado de este último debe cambiar a “En tránsito”. Por otro lado, cada sucursal tiene una lista donde aparecen los camiones que han llegado a ella y que esperan ser descargados (Puede asumir que los camiones llegan inmediatamente a su destino). El usuario debe tener la opción de seleccionar un camión o todos, y recepcionar los pedidos que contienen. Con esta última acción el estado cambia a “En destino”.

Cuando se recibe un pedido, se deben revisar los datos de manera manual para confirmar la correcta
recepción. Si todos los datos son correctos, el estado cambia a “Entregado”. A partir de este punto puede asumir que el sistema no debe preocuparse por lo que suceda con las encomiendas. En cualquier caso, al recibirse el pedido, el camión vuelve a la sucursal de origen.

## Bibliografía

## Política de Integridad Académica

Los alumnos de la Escuela de Ingeniería de la Pontificia Universidad Católica de Chile deben mantener un comportamiento acorde a la Declaración de Principios de la Universidad. En particular, se espera que mantengan altos estándares de honestidad académica. Cualquier acto deshonesto o fraude académico está prohibido; los alumnos que incurran en este tipo de acciones se exponen a un Procedimiento Sumario. Es responsabilidad de cada alumno conocer y respetar el documento sobre Integridad Académica publicado por la Dirección de Docencia de la Escuela de Ingeniería en el SIDING
Específicamente, para los cursos del Departamento de Ciencia de la Computación, rige obligatoria- mente la siguiente política de integridad académica. Todo trabajo presentado por un alumno para los efectos de la evaluación de un curso debe ser hecho individualmente por el alumno, sin apoyo en material de terceros. Por “trabajo” se entiende en general las interrogaciones escritas, las tareas de programación u otras, los trabajos de laboratorio, los proyectos, el examen, entre otros. Si un alumno copia un trabajo, obtendrá nota final 1.1 en el curso y se solicitará a la Dirección de Pregrado de la Escuela de Ingeniería que no le permita retirar el curso de la carga académica semestral. Por “copia” se entiende incluir en el trabajo presentado como propio partes hechas por otra persona
Obviamente, está permitido usar material disponible públicamente, por ejemplo, libros o contenidos tomados de Internet, siempre y cuando se incluya la referencia correspondiente. Lo anterior se entiende como complemento al Reglamento del Alumno de la Pontificia Universidad Católica de Chile Por ello, es posible pedir a la Universidad la aplicación de sanciones adicionales especificadas en dicho reglamento.

## Procedimiento de instalación
