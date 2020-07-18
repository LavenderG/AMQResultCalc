# AMQResultCalc
La aplicación **AMQResultCalc** permite generar automáticamente los resultados de AMQ de una o más rondas. Para ello necesitará dos archivos de entrada: un archivo con los resultados de las rondas realizadas y otro archivo con los resultados previos (necesario para sumar los resultados calculados a los resultados previos).

La aplicación generará dos archivos de salida: un archivo **post.bbc** con el post automático en formato BBCode listo para ser posteado (salvo las capturas de pantalla y la fecha de las rondas), y un archivo **resultados_out.amq** con los resultados totales que podrá ser utilizado como archivos de resultados previos en siguientes cálculos del programa

## Instalación

Para instalar la aplicación, ver [**INSTALL.md**](INSTALL.md)

## Uso

A continuación se explicará cómo utilizar la aplicación.

### Cargar las rondas <a name="cargar_rondas"></a>
Inicia la aplicación y en la barra de menú pulsa sobre *Archivo -> Cargar rondas...*. Se abrirá un diálogo de selección de archivo en el cual tendrás que seleccionar el archivo que contiene la información de la ronda o rondas jugadas. Este archivo tendrás que crearlo tú manualmente. Para saber más sobre el formato de archivo pulsa [**aquí**](#formato_rondas).

Es posible que el archivo introducido esté mal escrito. En ese caso, se mostrará un mensaje de error en la pantalla de la parte inferior de la aplicación.

### Cargar los resultados previos <a name="cargar_resultados"></a>
Tras haber cargado las rondas pulsa sobre "Archivo -> Cargar resultados previos...* en la barra de menú. Se abrirá un diálogo de selección de archivo en el cual tendrás que seleccionar el archivo que contiene la información de los resultados previos. Este archivo tendrás que crearlo tú manualmente. Para saber más sobre el formato de archivo pulsa [**aquí**](#formato_resultados).

Hay casos en los que puede que no tengas resultados previos (por ejemplo, al querer calcular las primeras rondas de un mes). En ese caso, basta con cargar un archivo vacío de resultados previos.

También es posible que el archivo introducido esté mal escrito. En ese caso, se mostrará un mensaje de error en la pantalla de la parte inferior de la aplicación.

### Generar los resultados <a name="generar_resultados"></a>

Una vez cargadas las rondas y resultados previos, se procederán a generar los resultados. Para ello, pulsa sobre "Calcular -> Generar resultados" en la barra de menú. En caso de error, la aplicación notificará de ello. Si todo ha ido correctamente, se mostrarán los mensajes de éxito en la parte inferior de la aplicación. Estos mensajes indican la localización del archivo **post.bbc** (contiene el post de BBCode generado de forma automática) y el archivo **resultados_out.amq**, que contiene los resultados totales tras el cálculo y que podrá ser utilizado en el paso [cargar los resultados previos](#cargar_resultados) de posteriores cálculos.

## Formato de los archivos <a name="formato"></a>

A continuación se describirá el formato de los archivos utilizados por la aplicación.

### Archivo de rondas <a name="formato_rondas"></a>

Este archivo contendrá la información de la ronda o rondas jugadas. La extensión puede ser cualquiera y el formato es el siguiente.

#### Ronda estándar <a name="formato_rondas_std"></a>

La definición de una ronda estándar comienza así:

    BEGINSTDROUND;Nombre de la ronda;URL de la captura de la ronda

donde *BEGINSTDROUND* es la etiqueta de comienzo de ronda estándar que debe escribirse tal cual, *Nombre de la ronda* será sustituido por el nombre que le quieres dar a la ronda (pueden utilizarse espacios) y *URL de la captura de la ronda* será la URL del pantallazo de la ronda.


Tras esto, se definirán los resultados de los usuarios, que tendrán el siguiente formato:

    Nombre del jugador;Posición;Respuestas correctas

donde *Nombre del jugador* será el nombre del jugador, *Posición* indicará la posición en la que ha quedado el jugador como un número entero (1 para el primero, 2 para el segundo, etc.) y *Respuestas correctas* será el número de respuestas que ha respondido correctamente el jugador.


Finalmente, se definirá el final de la ronda, así

    ENDSTDROUND

donde *ENDSTDROUND* es la etiqueta de final de ronda estándar que debe escribirse tal cual.

#### Ronda de battle royale <a name="formato_rondas_royale"></a>

La definición de una ronda estándar comienza así:

    BEGINBATTLEROYALE;Nombre de la ronda;URL de la captura de la ronda

donde *BEGINBATTLEROYALE* es la etiqueta de comienzo de ronda de battle royale que debe escribirse tal cual,*Nombre de la ronda* será sustituido por el nombre que le quieres dar a la ronda (pueden utilizarse espacios) y y *URL de la captura de la ronda* será la URL del pantallazo de la ronda.


Tras esto, se definirán los resultados de los usuarios, que tendrán el siguiente formato:

    Nombre del jugador;Posición;Respuestas correctas;Vidas;Resucitado

donde *Nombre del jugador* será el nombre del jugador, *Posición* indicará la posición en la que ha quedado el jugador como un número entero (1 para el primero, 2 para el segundo, etc.), *Respuestas correctas* será el número de respuestas que ha respondido correctamente el jugador, *Vidas* será el número de vidas que le han quedado al jugador (0 si se ha quedado sin vidas) y *Resucitado* indicará si el usuario ha podido resucitar o no (si el usuario ha resucitado se escribirá 1; si no, se escribirá 0).

Finalmente, se definirá el final de la ronda, así:

    ENDBATTLEROYALE

donde *ENDBATTLEROYALE* es la etiqueta de final de ronda battle royale que debe escribirse tal cual.

### Archivo de resultados previos <a name="formato_resultados"></a>

Este archivo contendrá la lista de resultados previos. Los resultados se definirán con el siguiente formato:

    Nombre del jugador;Puntuación total

donde *Nombre del jugador* será el nombre del jugador y *Puntuación total será* la puntuación total del jugador.

## Ejemplos <a name="formato_ejemplos"></a>

A continuación se mostrarán ejemplos de formato de los archivos de la aplicación.
**Nota**: las capturas de los ejemplos no tienen por qué corresponderse con los resultados de las rondas.

### Rondas estándar <a name="formato_ejemplos_std"></a>

    BEGINSTDROUND;Ronda 1;https://i.imgur.com/2RpQXMo.png
    Vainilla;1;7
    Keme;2;6
    Jerao91;2;6
    Hatsujaya;4;5
    gooses;4;5
    LavenderG;6;2
    Razeus;7;1
    vader;8;0
    ENDSTDROUND
    BEGINSTDROUND;Ronda 2;https://i.imgur.com/emJuk5L.png
    Vainilla;1;10
    Keme;2;8
    Hatsujaya;3;7
    Jerao91;3;7
    gooses;5;2
    Razeus;5;2
    vader;5;2
    LavenderG;5;2
    ENDSTDROUND
    BEGINSTDROUND;Ronda 3;https://i.imgur.com/ELnrufh.png
    Keme;1;8
    Jerao91;2;7
    Hatsujaya;3;6
    Vainilla;4;5
    gooses;5;4
    LavenderG;5;4
    Razeus;7;3
    vader;7;3
    ENDSTDROUND

### Rondas estándar + battle royale <a name="formato_ejemplos_std_royale"></a>

    BEGINSTDROUND;Ronda 1;https://i.imgur.com/BCeGwMl.png
    Hatsujaya;1;8
    Keme;2;7
    Jerao91;2;7
    santygrass;4;5
    gooses;5;3
    vader;5;3
    LavenderG;5;3
    Razeus;8;2
    ENDSTDROUND
    BEGINSTDROUND;Ronda 2;https://i.imgur.com/pTCJunW.png
    Hatsujaya;1;8
    santygrass;2;6
    Keme;2;6
    Jerao91;4;5
    gooses;5;4
    LavenderG;5;4
    Pacochef;7;2
    Razeus;7;2
    vader;7;2
    ENDSTDROUND
    BEGINSTDROUND;Ronda 3;https://i.imgur.com/y9NApYv.png
    Keme;1;7
    Hatsujaya;1;7
    Jerao91;4;5
    LavenderG;5;2
    Razeus;5;2
    gooses;7;1
    vader;7;1
    Pacochef;9;0
    ENDSTDROUND
    BEGINSTDROUND;Ronda 4;https://i.imgur.com/vc6pqtF.png
    Hatsujaya;1;8
    santygrass;2;6
    Jerao91;3;5
    gooses;4;3
    LavenderG;5;2
    Razeus;6;1
    vader;6;1
    ENDSTDROUND
    BEGINBATTLEROYALE;Battle Royale 1;https://i.imgur.com/Ai1P2Br.png
    santygrass;1;14;4;0
    Jerao91;2;12;2;0
    Hatsujaya;3;11;1;0
    LavenderG;4;10;0;0
    gooses;5;8;0;0
    ENDBATTLEROYALE

### Resultados previos <a name="formato_ejemplos_resultados"></a>

    Keme;400
    Hatsujaya;238
    santygrass;197
    Jerao91;244
    gooses;93
    LavenderG;83
    vader;69
    Razeus;48
    Topo;5
    Pacochef;3
    Vainilla;129
    Darkizard;0
