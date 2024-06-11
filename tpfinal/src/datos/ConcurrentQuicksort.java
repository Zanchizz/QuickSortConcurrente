package datos; // Pertenece al paquete datos

import java.util.Random; // Importo las librerias para randomizar
import java.util.concurrent.RecursiveTask; // Importo la librería para usar RecursiveTask

public class ConcurrentQuicksort extends RecursiveTask<Void> { // Se abre la clase ConcurrentQuicksort, que extiende RecursiveTask
    private static final long serialVersionUID = 1L; // ID de serialización para la clase
    private static final int THRESHOLD = 10000; // Umbral para decidir cuándo realizar QuickSort secuencialmente
    int start, end; // Variables para los índices de inicio y fin del array
    int[] arr; // Array a ser ordenado

    public ConcurrentQuicksort(int start, int end, int[] arr) { // Constructor de la clase
        this.start = start; // Inicializa el índice de inicio
        this.end = end; // Inicializa el índice de fin
        this.arr = arr; // Inicializa el array
    }

    private int partition(int start, int end, int[] arr) { // Método para particionar el array
        int i = start, j = end; // Inicializa los índices de partición
        int pivotIndex = new Random().nextInt(j - i + 1) + i; // Elige un pivote aleatorio
        int pivotValue = arr[pivotIndex]; // Obtiene el valor del pivote
        swap(arr, pivotIndex, end); // Coloca el pivote al final
        j--; // Decrementa el índice j

        while (i <= j) { // Bucle para particionar el array
            if (arr[i] <= pivotValue) { // Si el valor actual es menor o igual al pivote
                i++; // Incrementa i
            } else if (arr[j] >= pivotValue) { // Si el valor actual es mayor o igual al pivote
                j--; // Decrementa j
            } else { // Si los valores están fuera de lugar
                swap(arr, i, j); // Intercambia los valores
                i++; // Incrementa i
                j--; // Decrementa j
            } 
        }

        swap(arr, i, end); // Coloca el pivote en su posición correcta
        return i; // Devuelve la posición del pivote
    }

    private static void swap(int[] arr, int i, int j) {  // Método para intercambiar elementos en el array
        int temp = arr[i]; // Guarda el valor de arr[i] en una variable temporal
        arr[i] = arr[j]; // Asigna el valor de arr[j] a arr[i]
        arr[j] = temp; // Asigna el valor temporal a arr[j]
    } // Cierra el swap

    @Override
    protected Void compute() { // Método que realiza el QuickSort concurrente
        if (start >= end) { // Caso base: si el array tiene uno o ningún elemento
            return null; // Devuelve nada
        } // Cierra el if
        
        if (end - start < THRESHOLD) { // Si el tamaño del subarray es menor que el umbral
            quicksort(arr, start, end); // Realiza QuickSort secuencial
            return null; // Devuelve nada
        } // Cierra el if

        int pivot = partition(start, end, arr); // Particiona el array y obtiene el pivote

        ConcurrentQuicksort leftTask = new ConcurrentQuicksort(start, pivot - 1, arr); // Crea una nueva tarea para la mitad izquierda
        ConcurrentQuicksort rightTask = new ConcurrentQuicksort(pivot + 1, end, arr); // Crea una nueva tarea para la mitad derecha

        leftTask.fork(); // Ejecuta la tarea de la mitad izquierda en un nuevo hilo
        rightTask.compute(); // Ejecuta la tarea de la mitad derecha en el hilo actual
        leftTask.join(); // Espera a que la tarea de la mitad izquierda termine

        return null; // Termina la ejecución y devuelve nada
    }
    
    
    
    public static void quicksort(int[] arr, int start, int end) { // Método para realizar QuickSort secuencial
        if (start >= end) { // Caso base: si el array tiene uno o ningún elemento
            return; // No hace nada
        }

        int pivot = partition(arr, start, end); // Particiona el array y obtiene el pivote
        quicksort(arr, start, pivot - 1); // Ordena recursivamente la mitad izquierda
        quicksort(arr, pivot + 1, end); // Ordena recursivamente la mitad derecha
    }
    

    private static int partition(int[] arr, int start, int end) { // Método para particionar el array
        int pivot = arr[end]; // Elige el último elemento como pivote
        int i = start - 1; // Inicializa el índice i

        for (int j = start; j < end; j++) { // Bucle para particionar el array
            if (arr[j] <= pivot) { // Si el valor actual es menor o igual al pivote
                i++; // Incrementa i
                swap(arr, i, j); // Intercambia los valores
            }
        }

        swap(arr, i + 1, end); // Coloca el pivote en su posición correcta
        return i + 1; // Devuelve la posición del pivote
    }
}