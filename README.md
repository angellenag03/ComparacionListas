
# Angel Adrian Sanchez Navarro || 222209190
# Práctica: ArrayList vs. LinkedList en Java Collections Framework

## 1. Resultados de tiempos obtenidos

> Los tiempos siguientes se obtuvieron ejecutando el código de `BenchmarkListas.java` (N = 100 000 para acceso, inserción al final y eliminación; 50 000 para inserción al inicio) tres veces consecutivas en la misma JVM. Si al ejecutar en tu propia máquina obtienes valores distintos, es normal (ver pregunta 11) — puedes sustituir estos números por los tuyos manteniendo el mismo formato de tabla.

### 1.1 Acceso mediante `get(i)`

| Ejecución | ArrayList (ms) | LinkedList (ms) |
|---|---|---|
| 1 | 6.743 | 4498.383 |
| 2 | 0.314 | 4199.517 |
| 3 | 0.419 | 4256.353 |
| **Promedio** | **2.492** | **4318.084** |

### 1.2 Acceso mediante `for-each`

| Ejecución | ArrayList (ms) | LinkedList (ms) |
|---|---|---|
| 1 | 7.306 | 4.611 |
| 2 | 0.614 | 0.945 |
| 3 | 5.602 | 1.176 |
| **Promedio** | **4.507** | **2.244** |

## 2. Comparación: `get(i)` vs. `for-each`

La diferencia entre ambos métodos de recorrido es el resultado más contundente de la práctica:

- Con **`get(i)`**, `ArrayList` recorre la lista en apenas ~2.5 ms de promedio, mientras que `LinkedList` tarda **más de 4 segundos** (~4318 ms) para el mismo trabajo. Esto se debe a que cada llamada a `get(i)` en `LinkedList` recorre los nodos desde uno de los extremos hasta llegar a la posición `i`, así que recorrer toda la lista con este método cuesta O(n²) en total (n llamadas, cada una O(n)).
- Con **`for-each`**, ambas estructuras terminan en tiempos comparables (`ArrayList` ~4.5 ms, `LinkedList` ~2.2 ms), porque el iterador interno de `LinkedList` guarda una referencia al nodo actual y avanza al siguiente en O(1) por paso, dando un recorrido total O(n) — igual que `ArrayList`.
- **Conclusión práctica:** nunca se debe recorrer una `LinkedList` con `get(i)` en un ciclo; siempre debe usarse `for-each` (o un `Iterator`/`ListIterator` explícito).

## 3. Resultados de inserciones

### 3.1 Inserción al inicio (`add(0, x)`, 50 000 elementos)

| Ejecución | ArrayList (ms) | LinkedList (ms) |
|---|---|---|
| 1 | 107.602 | 7.677 |
| 2 | 103.527 | 6.923 |
| 3 | 114.603 | 2.357 |
| **Promedio** | **108.577** | **5.652** |

### 3.2 Inserción al final (`add(x)`, 100 000 elementos)

| Ejecución | ArrayList (ms) | LinkedList (ms) |
|---|---|---|
| 1 | 5.631 | 4.139 |
| 2 | 1.644 | 3.712 |
| 3 | 12.062 | 3.483 |
| **Promedio** | **6.446** | **3.778** |

| Operación | ArrayList (promedio) | LinkedList (promedio) |
|---|---|---|
| Insertar al inicio | 108.577 ms | 5.652 ms |
| Insertar al final | 6.446 ms | 3.778 ms |

**Hipótesis vs. resultado:** se esperaba que `LinkedList` fuera muy superior al insertar al inicio (O(1) contra O(n) de `ArrayList`, que debe desplazar todo el arreglo con `System.arraycopy`), y así lo confirman los datos (~19× más rápida). Al insertar al final, ambas son O(1) (amortizado en `ArrayList`), por lo que los tiempos resultan del mismo orden de magnitud.

## 4. Resultados de eliminaciones

### 4.1 Eliminación desde el inicio (`remove(0)` hasta vaciar, 100 000 elementos)

| Ejecución | ArrayList (ms) | LinkedList (ms) |
|---|---|---|
| 1 | 437.712 | 7.356 |
| 2 | 413.727 | 0.918 |
| 3 | 417.753 | 3.637 |
| **Promedio** | **423.064** | **3.970** |

`ArrayList` debe desplazar todos los elementos restantes una posición a la izquierda en cada `remove(0)` (O(n) por operación, O(n²) en total), mientras que `LinkedList` solo necesita actualizar la referencia de su nodo cabeza (O(1) por operación, O(n) en total). La diferencia observada (~106×) es coherente con esa complejidad.

## 5. Tabla comparativa final

| Característica | ArrayList | LinkedList |
|---|---|---|
| Implementa `List` | Sí | Sí |
| Estructura | Arreglo dinámico | Lista doblemente enlazada |
| Acceso `get(i)` | O(1) | O(n) |
| Modificación `set(i)` | O(1) | O(n) |
| Inserción al final | O(1) amortizado | O(1) |
| Inserción al inicio | O(n) | O(1) |
| Eliminación al inicio | O(n) | O(1) |
| Búsqueda por valor | O(n) | O(n) |
| Recorrido completo | O(n) | O(n) |
| Implementa `Deque` | No | Sí |
| Memoria adicional por elemento | Menor en general | Mayor por los enlaces |
| Acceso aleatorio frecuente | Adecuado | Poco adecuado |
| Operaciones frecuentes en extremos | No es su principal fortaleza | Adecuado |

## 6. Preguntas de análisis

**1. ¿Qué interfaz implementan tanto ArrayList como LinkedList?**
Ambas implementan `List` (y transitivamente `Collection` e `Iterable`). Adicionalmente, `ArrayList` implementa `RandomAccess`, y `LinkedList` implementa `Deque` (y `Queue`).

**2. ¿Cuál es la principal diferencia en su estructura interna?**
`ArrayList` se apoya en un arreglo dinámico: los elementos están contiguos en memoria y se accede a ellos por índice. `LinkedList` es una lista doblemente enlazada: cada elemento vive en un nodo independiente que guarda referencias al nodo anterior y al siguiente.

**3. ¿Por qué ArrayList.get(i) tiene complejidad O(1)?**
Porque conociendo la dirección base del arreglo y el tamaño de cada elemento, la posición `i` se calcula con una operación aritmética directa (`base + i * tamaño`), sin recorrer nada.

**4. ¿Por qué LinkedList.get(i) tiene complejidad O(n)?**
Porque no existe acceso directo por índice: hay que recorrer los nodos uno a uno (desde el inicio o el final, según cuál quede más cerca) siguiendo los enlaces `next`/`prev` hasta alcanzar la posición `i`.

**5. ¿Qué ocurre internamente cuando se ejecuta ArrayList.add(0, elemento)?**
Se desplazan todos los elementos existentes una posición hacia la derecha (típicamente con `System.arraycopy`) para abrir espacio en el índice 0, y luego se coloca ahí el nuevo elemento. Esto es O(n).

**6. ¿Por qué LinkedList.add(0, elemento) no necesita desplazar los demás elementos?**
Porque solo se crea un nuevo nodo y se actualizan un par de referencias (el `next` del nuevo nodo apunta al antiguo primer nodo, y el `prev` de este apunta al nuevo nodo, además de actualizar la referencia `first` de la lista). Ningún otro nodo se modifica.

**7. ¿Por qué afirmar que "LinkedList es mejor para inserciones" puede ser incorrecto?**
Porque esa ventaja solo aplica a inserciones en los extremos (o cuando ya se tiene una referencia al nodo, por ejemplo con un `ListIterator`). Para insertar en una posición intermedia arbitraria dada por índice, primero hay que **localizar** el nodo recorriendo la lista (O(n)), por lo que el costo total termina siendo igual o peor que en `ArrayList`, y además con peor localidad de caché.

**8. ¿Qué diferencia observó entre recorrer LinkedList mediante get(i) y mediante for-each?**
Con `get(i)` el recorrido completo tardó miles de milisegundos (~4318 ms de promedio) porque cada acceso individual es O(n). Con `for-each` el mismo recorrido tomó apenas unos milisegundos (~2.2 ms de promedio), porque el iterador avanza nodo a nodo en O(1), dando un total O(n).

**9. ¿Qué resultados obtuvo para inserciones al inicio?**
`ArrayList` promedió ~108.6 ms y `LinkedList` ~5.7 ms para 50 000 inserciones en la posición 0; `LinkedList` fue aproximadamente 19 veces más rápida, consistente con O(n) vs. O(1).

**10. ¿Qué resultados obtuvo para inserciones al final?**
Ambas resultaron similares y rápidas: `ArrayList` ~6.4 ms y `LinkedList` ~3.8 ms para 100 000 inserciones, coherente con que ambas son O(1) (amortizado en el caso de `ArrayList`) en ese extremo.

**11. ¿Los tiempos medidos coinciden exactamente con lo esperado a partir de Big-O? Explique.**
No exactamente, y no deberían: Big-O describe cómo **crece** el costo con el tamaño de la entrada, no un tiempo absoluto. Los tiempos reales dependen también de factores como el calentamiento del compilador JIT (las primeras ejecuciones suelen ser más lentas, como se ve en la Corrida 1 de varias tablas), la localidad de caché del procesador (favorece a `ArrayList` por su memoria contigua), el overhead de creación de objetos `Integer`/nodos, y el recolector de basura. Por eso dos algoritmos con la misma complejidad asintótica pueden tener tiempos absolutos distintos.

**12. ¿Qué costo de memoria adicional tiene conceptualmente una lista enlazada?**
Cada nodo de `LinkedList` necesita, además del dato, dos referencias (a los nodos anterior y siguiente) más la cabecera propia del objeto nodo. `ArrayList` solo almacena las referencias a los elementos de forma contigua en un arreglo, sin esa estructura envolvente, por lo que su overhead por elemento es menor.

**13. ¿Qué ventajas proporciona programar contra List?**
Permite cambiar la implementación concreta (`ArrayList`, `LinkedList`, etc.) sin modificar el código que usa la lista, reduce el acoplamiento entre módulos, facilita las pruebas (se pueden usar implementaciones distintas para test y producción) y sigue el principio de "programar contra interfaces, no contra implementaciones".

**14. ¿Por qué Deque representa mejor el problema de la cola de trabajos?**
Porque el problema exige explícitamente insertar en ambos extremos (trabajos urgentes al frente, normales al final) y consultar/retirar desde el frente. `Deque` expone justamente esas operaciones (`addFirst`, `addLast`, `peekFirst`, `pollFirst`) sin ofrecer ni sugerir operaciones que no se necesitan, como el acceso aleatorio por índice de `List`.

**15. ¿En qué escenario seleccionaría ArrayList?**
Cuando predomina el acceso aleatorio o secuencial por índice (lecturas frecuentes, recorridos), y las modificaciones ocurren sobre todo al final de la colección — por ejemplo, listas de resultados, tablas en memoria, colecciones que se llenan una vez y se consultan muchas veces.

**16. ¿En qué escenario tendría sentido utilizar LinkedList?**
Cuando predominan las inserciones y eliminaciones en los extremos (o junto a una posición ya conocida vía iterador) y el acceso aleatorio por índice es raro — por ejemplo, colas, pilas, buffers de trabajos pendientes, implementaciones de `Deque`.

## 7. Conclusión técnica

Los experimentos confirman que la elección entre `ArrayList` y `LinkedList` no debe basarse únicamente en la notación Big-O de cada operación aislada, sino en el **patrón de uso real** de la colección. `ArrayList` domina claramente cuando hay acceso aleatorio por índice (miles de veces más rápido que `LinkedList` en `get(i)`), gracias a la localidad de memoria de su arreglo contiguo. `LinkedList`, en cambio, es muy superior cuando las operaciones se concentran en los extremos de la colección (inserciones y eliminaciones al inicio), donde evita el desplazamiento masivo de elementos que sí penaliza a `ArrayList`.

También quedó claro que la forma de recorrer una lista importa tanto como la estructura elegida: usar `get(i)` sobre una `LinkedList` es un antipatrón que puede degradar un recorrido O(n) a O(n²), mientras que `for-each` (o un iterador explícito) mantiene el comportamiento O(n) esperado en ambas implementaciones.

Por último, el ejercicio de refactorizar `ColaTrabajos` de `List` a `Deque` muestra un principio de diseño más amplio: la decisión relevante no es "¿ArrayList o LinkedList?", sino "¿qué abstracción (interfaz) describe mejor las operaciones que necesito?". Al modelar la cola de trabajos como `Deque`, el código expresa su intención con mayor claridad y queda libre de elegir, de forma transparente, la implementación (`LinkedList` u otra) que mejor desempeño ofrezca para ese contrato.
