package test1; // Pertenece a paquete test1


import datos.ConcurrentQuicksort; // Importo el archivo que genera los ordenamientos
import java.util.Random; // Importo las librerias para randomizar
import java.util.concurrent.ForkJoinPool; // Importo las librerias para usar ForkJoinPool

public class mainprincipal { // Se abre la clase mainprincipal

	public static void main(String[] args) { // Se abre el main
		int n = 1000000; // Tamaño del array en cuanto a posiciones
        int[] arr = new int[n]; // creacion del array
        Random random = new Random(); // Creacion de Random

        for (int i = 0; i < n; i++) { // Bucle segun cantidad de posiciones
            arr[i] = random.nextInt(10000000); // Dato aleatorio para cada valor del array
        }

        // Sequential QuickSort
        int[] arrCopy1 = arr.clone(); // Genero un clon del array original
        long startTime = System.currentTimeMillis(); // Empieza el cronometro para ver cuanto tarda el Quick Sort Secuencial
        ConcurrentQuicksort.quicksort(arrCopy1, 0, arrCopy1.length - 1); // LLamo a la funcion que ordena el array secuencialmente
        long endTime = System.currentTimeMillis(); // Una vez que termina la funcion corto el cronometro
        System.out.println("El Ordenamiento Quick Sort Secuencial tardó: " + (endTime - startTime) + " milisegundos"); // Imprimo el resultado de tiempo que dio el cronometro

        // Parallel QuickSort
        int[] arrCopy2 = arr.clone(); // Genero un clon del array original de nuevo
        ForkJoinPool pool = ForkJoinPool.commonPool(); // Crea un ForkJoinPool común
        startTime = System.currentTimeMillis(); // Empieza el cronometro para ver cuánto tarda el QuickSort concurrente
        pool.invoke(new ConcurrentQuicksort(0, arrCopy2.length - 1, arrCopy2));
        endTime = System.currentTimeMillis(); // Una vez que termina la función corta el cronometro
        System.out.println("Parallel QuickSort took: " + (endTime - startTime) + " ms"); // Imprime el resultado de tiempo que dio el cronometro
	} // Cierre main

	
	
	
} // Cierra la clase
