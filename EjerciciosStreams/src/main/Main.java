/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import dao.DepartamentsDAO;
import dao.EmpleatsDAO;
import dto.Departament;
import dto.Empleat;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author jmas
 */
public class Main {

    public static void main(String[] args) {
        // 1.- Imprimeix el primer empleat trobat amb més de 50 anys
        System.out.println("|[ 1 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .filter(e -> e.getEdat() > 50)
                .findFirst()
                .ifPresent(System.out::println);

        // 2. Imprimeix true si tots els empleats son majors d'edat i false en cas contrari
        System.out.println("|[ 2 ]| -----------------------------------");
        System.out.println("¿Todos los empleados son mayores de edad?: "
                + EmpleatsDAO.getEmpleats().stream()
                        .allMatch(e -> e.getEdat() > 18)
        );

        // 3. Imprimeix true si hi ha algun empleat major de 65 anys i false en cas contrari
        System.out.println("|[ 3 ]| -----------------------------------");
        System.out.println("¿Hay algún empleado mayor de 65 años?: "
                + EmpleatsDAO.getEmpleats().stream()
                        .anyMatch(e -> e.getEdat() > 65)
        );

        // 4. Imprimeix el número d'empleats que tenen més de 50 anys
        System.out.println("|[ 4 ]| -----------------------------------");
        System.out.println("Empleados mayores de 50 años: "
                + EmpleatsDAO.getEmpleats().stream()
                        .filter(e -> e.getEdat() > 50)
                        .count()
        );

        // 5. Imprimeix el nom dels empleats del departament d'informàtica, en majúscules.
        System.out.println("|[ 5 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .filter(e -> e.getDepartament().getNom().equals("Informàtica"))
                .forEach(e -> System.out.println(e.getNom().toUpperCase()));

        // 6. Imprimeix la quantitat de lletres de l'empleat d'informàtica amb el nom més llarg.
        System.out.println("|[ 6 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .filter(e -> e.getDepartament().getNom().equals("Informàtica"))
                .mapToInt(e -> e.getNom().length())
                .max()
                .ifPresent(l -> System.out.println("Cantidad de letras del nombre más largo: " + l));

        // 7. Imprimeix el nom dels departaments que comencen per C
        System.out.println("|[ 7 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .filter(e -> e.getDepartament().getNom().startsWith("C"))
                .map(e -> e.getDepartament().getNom())
                .distinct()
                .forEach(System.out::println);

        // 8. Imprimeix la suma de totes les edats dels empleats
        System.out.println("|[ 8 ]| -----------------------------------");
        System.out.println("Suma de la edad de todos los empleados: "
                + EmpleatsDAO.getEmpleats().stream()
                        .mapToInt(e -> e.getEdat())
                        .sum()
        );

        // 9 UTILITZA EL MÈTODE GETEMPLEATS DE LA CLASSE DEPARTAMENT (NOMÉS PER A L'EXERCICI 9 I 10)
        // Imprimeix la quantitat d'empleats de cada departament
        System.out.println("|[ 9 ]| -----------------------------------");
        DepartamentsDAO.getDepartaments().stream()
                .forEach(d -> {
                    System.out.print("Departamento: " + d.getNom() + ", ");
                    System.out.println("Cantidad de empleados: " + d.getEmpleats().size());
                });

        // 10. UTILITZANT EL MÈTODE GETEMPLEATS DE LA CLASSE DEPARTAMENT (NOMÉS PER A L'EXERCICI 9 I 10)
        // Imprimeix la llista de noms dels empleats del departament comercial i de comptatilitat (una única llista d'String)
        System.out.println("|[ 10 ]| -----------------------------------");
        DepartamentsDAO.getDepartaments().stream()
                .filter(n -> n.getNom().equals("Comercial") || n.getNom().equals("Comptabilitat"))
                .flatMap(d -> d.getEmpleats().stream())
                .map(empleat -> empleat.getNom() + " " + empleat.getCognoms())
                .forEach(System.out::println);

        // 11. El mateix d'abans però mostra els noms ordenats alfabèticament.
        System.out.println("\n|[ 11 ]| -----------------------------------");
        DepartamentsDAO.getDepartaments().stream()
                .filter(n -> n.getNom().equals("Comercial") || n.getNom().equals("Comptabilitat"))
                .flatMap(d -> d.getEmpleats().stream())
                .map(empleat -> empleat.getNom() + " " + empleat.getCognoms())
                .sorted()
                .forEach(System.out::println);

        // 12. Mostra la mitjana d'edat dels empleats del departament d'informàtica.
        System.out.println("|[ 12 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .filter(e -> e.getDepartament().getNom().equals("Informàtica"))
                .mapToInt(e -> e.getEdat())
                .average()
                .ifPresent(e -> System.out.println("Media de las edades del departamento de Informática: " + e));

        // 13. Mostra un String que serà el resultat de concatenar la primera lletra de cada departament.
        System.out.println("|[ 13 ]| -----------------------------------");
        DepartamentsDAO.getDepartaments().stream()
                .map(e -> e.getNom().charAt(0))
                .forEach(System.out::print);

        // 14. Mostra el número de telèfon més alt dels departaments.
        System.out.println("\n|[ 14 ]| -----------------------------------");
        DepartamentsDAO.getDepartaments().stream()
                .map(e -> e.getTelefon())
                .max((t1, t2) -> Integer.compare(t1, t2))
                .ifPresent(t -> System.out.println("Numero de teléfono más alto: " + t));

        // 15. Mostra el departament complet amb el número de telèfon més alt.
        System.out.println("|[ 15 ]| -----------------------------------");
        DepartamentsDAO.getDepartaments().stream()
                .sorted((t1, t2) -> Integer.compare(t2.getTelefon(), t1.getTelefon()))
                .limit(1)
                .forEach(System.out::println);

        // 16. Sense fer ús del mètode "getEmpleats". Dels departament que tenen treballadors, mostrar el nom del departament i el nombre de treballadors que hi treballen.
        System.out.println("|[ 16 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .collect(Collectors.groupingBy(Empleat::getDepartament, Collectors.counting()))
                .forEach((dep, count) -> System.out.println("Departamento de " + dep.getNom() + " con " + count + " trabajador/es."));

        // 17A. Guarda en un Map un registre per a cada Departament (que tinga treballadors) que tinga associat com a valor la llista d'empleats d'eixe departament
        System.out.println("|[ 17A ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .collect(Collectors.groupingBy(Empleat::getDepartament))
                .forEach((dep, emple) -> {
                    System.out.println("Departamento de " + dep.getNom() + " con los empleados: ");
                    emple.forEach(System.out::println);
                    System.out.println("");
                });

        // 17B. Igual que l'anterior pero la llista no serà d'empleats sino del nom dels empleats
        System.out.println("|[ 17B ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .collect(Collectors.groupingBy(Empleat::getDepartament, Collectors.mapping(Empleat::getNom, Collectors.toList())))
                .forEach((dep, nom) -> {
                    System.out.println("Departamento de " + dep.getNom() + " con los empleados ordenados: ");
                    nom.forEach(System.out::println);
                    System.out.println("");
                });

        // 17C. Igual que l'anterior pero amb els empleats ordenats alfabèticament
        System.out.println("|[ 17C ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .collect(Collectors.groupingBy(Empleat::getDepartament, Collectors.mapping(Empleat::getNom, Collectors.toCollection(TreeSet::new))))//Con TreeSet consigo que la lista ya se ordene.
                .forEach((dep, nom) -> {
                    System.out.println("Departamento de " + dep.getNom() + " con los empleados ordenados: ");
                    nom.forEach(System.out::println);
                    System.out.println("");
                });

//        // 18A. Obtín un Map que continga per a cada departament una llista d'enters grans (BigInteger) que es corresponguen amb les edats dels empleats d'eixe departament
        System.out.println("|[ 18A ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .collect(Collectors.groupingBy(Empleat::getDepartament, Collectors.mapping(e -> BigInteger.valueOf(e.getEdat()), Collectors.toList())))
                .forEach((dep, edad) -> {
                    System.out.println("Departamento de " + dep.getNom() + " con edades de: ");
                    edad.forEach(System.out::println);
                    System.out.println("");
                });

//        // 18B. El mateix que abans però en comptes de l'edat, el probable següent número primer
        System.out.println("|[ 18B ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .collect(Collectors.groupingBy(Empleat::getDepartament, Collectors.mapping(e -> BigInteger.valueOf(e.getEdat()).nextProbablePrime(), Collectors.toList())))
                .forEach((dep, edad) -> {
                    System.out.println("Departamento de " + dep.getNom() + ": ");
                    edad.forEach(System.out::println);
                    System.out.println("");
                });

//        // 19. Obtín un Map que continga per a cada departament l'empleat més jove.
        System.out.println("|[ 19 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .collect(Collectors.groupingBy(Empleat::getDepartament, Collectors.minBy(Comparator.comparingInt(Empleat::getEdat))))
                .forEach((dep, joven) -> System.out.println("Departamento: " + dep.getNom() + "\nEmpleado más joven: " + joven));

//        // 20. Obtín un String que continga el mateix que abans, amb el format "Departament1:Empleat1, Departament2:Empleat2 ..."
//        System.out.println("|[ 20 ]| -----------------------------------NO ME SALE");
//        EmpleatsDAO.getEmpleats().stream()
//                .collect(Collectors.groupingBy(Empleat::getDepartament,
//                        Collectors.mapping(Collectors.minBy(
//                                Comparator.comparingInt(Empleat::getEdat)), Collectors.joining(", ")  )))
//
//        // 21. Obtín un Map que conté com a clau el DNI dels empleats i com a valor el nom d'eixe empleat
        System.out.println("|[ 21 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .collect(Collectors.toMap(Empleat::getDni, Empleat::getNom))
                .forEach((d, n) -> System.out.println("DNI: " + d + " - Nombre: " + n));

//        // 22A. Donat un array bidimensional d'Integer transformar-lo en un array unidimensional amb els mateixos valors:
        System.out.println("|[ 22A ]| -----------------------------------");
        Integer[][] matriuInteger = {{3, 2, 5}, {0, -8, 7}, {9, 9, 6}};
        Stream.of(matriuInteger)
                .flatMap(Arrays::stream)
                .forEach(e -> System.out.print(e + " "));

//        // 22B. Versió amb int[] en comptes d'Integer[]
        System.out.println("\n|[ 22B ]| -----------------------------------");
        Stream.of(matriuInteger)
                .flatMap(Arrays::stream)
                .map(Integer::intValue) // Lo transformo con el metodo que tiene Integer
                .forEach(e -> System.out.print(e + " "));

//        // 23. Guarda una llista amb els noms dels estudis de tots els empleats (de forma única), ordenats alfabèticament.
        System.out.println("\n|[ 23 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .flatMap(e -> e.getTitols().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList())
                .forEach(System.out::println);

//        // 24. Imprimeix per pantalla aquells empleats que tinguen un CFGS
        System.out.println("|[ 24 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .filter(e -> e.getTitols().contains("CFGM"))
                .collect(Collectors.toList())
                .forEach(System.out::println);

//        // 25. Mostra un empleat qualsevol que tinga una llicenciatura
        System.out.println("|[ 25 ]| -----------------------------------");
        EmpleatsDAO.getEmpleats().stream()
                .filter(e -> e.getTitols().contains("Llicenciatura"))
                .findAny()
                .ifPresent(System.out::println);

//        // 26. Mostra per a cada nom d'empleat la suma de les lletres dels seus titols
        System.out.println("|[ 26 ]| -----------------------------------");
//        EmpleatsDAO.getEmpleats().stream()                    ERROR WHY
//                .collect(Collectors.toMap(Empleat::getNom, 
//                        s -> s.getTitols().stream().mapToInt(s -> s.length()).sum()))
//                .forEach((nom, suma) -> System.out.println("Nombre: " + nom + " - " + suma));

//        // 27. Mostra el total de lletres dels titols (incloent duplicats) dels empleats
        System.out.println("|[ 27 ]| -----------------------------------");
        
        
//        // 28. Mostra l'empleat amb major quantitat de títols
        System.out.println("|[ 28 ]| -----------------------------------");                
//        EmpleatsDAO.getEmpleats().stream()        
//                .map(e -> e.getTitols().stream().count())
//                .max((t1, t2) -> Long.compare(t1, t2))
//                .ifPresent(System.out::println);
        
                
//        // 29A. Obtín una llista que continga 5 aleatoris entre 0 i 9 en format String
//        System.out.println("|[ 29A ]| -----------------------------------");
//
//        // 29B. El mateix d'abans, però ara sense valors repetits.
//        System.out.println("|[ 29B ]| -----------------------------------");
//
//        // 30. Crea un stream amb tots els números positius divisibles entre 3. Després filtra aquells que siguen quadrats perfectes. 
//        // Després descarta els 5 primers valors obtinguts. Finalment mostra els 10 següents valors obtinguts
//        System.out.println("|[ 30 ]| -----------------------------------");
//
//        // 31. A partir dels cognoms dels empleats imprimeix amb una operació intermèdia els cognoms sense transformar i després transformats en majúscules.
//        // Finalment retorna el nombre d'empleats.
//        System.out.println("|[ 31 ]| -----------------------------------");
//
//        // 32. Imprimeix el cognom dels empleats ordenats en ordre alfabètic invers.
//        System.out.println("|[ 32 ]| -----------------------------------");
//
//        // 33. Obtín la suma dels títols de tots els empleats
//        System.out.println("|[ 33 ]| -----------------------------------");
//
//        // 34. Obtín un String amb la concatenació dels títols d'aquells empleats que tinguen menys de 30 anys, separats per espais (sense duplicats)
//        System.out.println("|[ 34 ]| -----------------------------------");
//
//        // 35A. Mostra el cognom del primer dels empleats trobat que tinga menys de 30 anys i que tinga una llicenciatura. En cas de no trobar-lo mostra "No trobat"
//        System.out.println("|[ 35A ]| -----------------------------------");
//
//        // 35B. Mostra el primer dels empleats (complet) trobat que tinga menys de 30 anys i que tinga una llicenciatura. En cas de no trobar-lo mostra "No trobat"
//        System.out.println("|[ 35B ]| -----------------------------------");
//
//        // 36. Obtín un LinkedHashSet dels títols que tenen tots els empleats.
//        System.out.println("|[ 36 ]| -----------------------------------");
//
//        // 37. Obtín un Map amb dos claus, la primera tindrá com a valors una llista dels empleats amb el títol de CFGS i l'altra clau tindrá una llista amb els que no el tenen.
//        System.out.println("|[ 37 ]| -----------------------------------");
//
//        // 38. A partir d'una llista d'String retorna la mateixa llista pero sense cadenes buides
//        System.out.println("|[ 38 ]| -----------------------------------");
//        List<String> elementsOriginals = List.of("aigua", "", "llet", "oli", "", "  ", "lletuga");
//
//        // 39. Obtín una cadena separada per comes basada en una llista determinada d'enters. 
//        // Cada element ha d'anar precedit de la lletra 'p' si el nombre és parell i precedit de la lletra 'i' si el nombre és imparell. 
//        // Per exemple, si la llista d'entrada és (3,44), la eixida hauria de ser 'i3, p44'.
//        System.out.println("|[ 39 ]| -----------------------------------");
//        List<Integer> llistaEnters = List.of(9, 23, 4, 15);
//
//        // 40. A partir dels empleats. Obtín un mapa que tinga com a clau el nom del departament i com a valor un altre mapa
//        // Este segon mapa tindrà com a clau el cognom de l'empleat y com a valor la llista de títols que té.
//        System.out.println("|[ 40 ]| -----------------------------------");
//
//        // EXTRA A: A partir d'un String retorna les lletres distintes que conté, separades per comes (només lletres, no espais ni números ni altres caracters)
//        System.out.println("|[ EA ]| -----------------------------------");
//        String frase1 = "Tinc un 8 en PROG";
//
//        // EXTRA B: A partir d'un String retorna la quantitat de lletres distintes que conté (només lletres, no espais ni números ni altres caracters)
//        System.out.println("|[ EB ]| -----------------------------------");
//
//        // EXTRA C: Obtín un mapa que mostre com a clau cada lletra distinta del String, i com a valor la quantitat de vegades que apareix.
//        System.out.println("|[ EC ]| -----------------------------------");
//        // EXTRA D: Mostra la lletra amb major freqüencia d'aparicions, i el nombre d'aparicions d'eixa lletra.
//        System.out.println("|[ ED ]| -----------------------------------");
    }

}
