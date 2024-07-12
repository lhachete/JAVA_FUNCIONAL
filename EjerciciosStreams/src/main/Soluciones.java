package main;

import dao.DepartamentsDAO;
import dao.EmpleatsDAO;
import dto.Empleat;
import dto.Departament;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Soluciones {

    public static void main(String[] args) {
        // 1.- Primer empleat trobat amb més de 50 anys
        System.out.println("-----1-----");
        EmpleatsDAO.getEmpleats()
                .stream()
                .filter(e -> e.getEdat() > 50)
                .findFirst()
                .ifPresent(System.out::println);
       System.out.println("-----2-----");
       
        // 2. Saber si tots els empleats son majors d'edat
        System.out.println(
                EmpleatsDAO.getEmpleats()
                        .stream()
                        .allMatch(e -> e.getEdat() >= 18)
        );
        
        System.out.println("-----3-----");
        // 3. Saber si hi ha algun empleat major de 65 anys
        System.out.println(
                EmpleatsDAO.getEmpleats()
                        .stream()
                        .anyMatch(e -> e.getEdat() > 65)
        );

        System.out.println("-----4-----");
        // 4. Comptar el nombre d'empleats que tenen més de 50 anys
        System.out.println(
                EmpleatsDAO.getEmpleats()
                        .stream()
                        .filter(e -> e.getEdat() > 40)
                        .count()
        );

        System.out.println("-----5-----");
        // 5. Nom dels empleats d'informàtica, en majúscules.
        EmpleatsDAO.getEmpleats()
                .stream()
                .filter(e -> e.getDepartament().getNom().equals("Informàtica"))
                .map(Empleat::getNom)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        System.out.println("-----6-----");
        // 6. Nombre de lletres de l'empleat d'informàtica amb el nom més llarg.
        EmpleatsDAO.getEmpleats()
                .stream()
                .filter(e -> e.getDepartament().getNom().equals("Informàtica"))
                .map(Empleat::getNom)
                .mapToInt(String::length)
                .max()
                .ifPresent(System.out::println);

        System.out.println("-----7-----");
        // 7. El nom dels departaments que comencen per C
        DepartamentsDAO.getDepartaments()
                .stream()
                .map(Departament::getNom)
                .filter(n -> n.startsWith("C"))
                .forEach(System.out::println);

        System.out.println("-----8-----");
        // 8. Suma totes les edats dels empleats
        System.out.println(
                EmpleatsDAO.getEmpleats()
                        .stream()
                        .mapToInt(Empleat::getEdat)
                        .sum()
        );
        
        System.out.println("-----8B-----");
        System.out.println(
                EmpleatsDAO.getEmpleats()
                        .stream()
                        .map(Empleat::getEdat)
                        .reduce(0, Integer::sum)
        );

        System.out.println("-----9-----");
        // 9 UTILITZANT EL MÈTODE GETEMPLEATS DE LA CLASSE DEPARTAMENT
        // OBTINDRE EL NOMBRE D'EMPLEATS DE CADA DEPARTAMENT
        DepartamentsDAO.getDepartaments()
                .stream()
                .map(Departament::getEmpleats)
                .map(ArrayList::size)
                .forEach(System.out::println);

        System.out.println("-----10-----");
        // 10. UTILITZANT EL MÈTODE GETEMPLEATS DE LA CLASSE DEPARTAMENT
        // OBTINDRE LA LLISTA DE NOMS DELS EMPLEATS DEL DEPARTAMENT COMERCIAL I DE COMPTABILITAT (UNA LLISTA D'STRING)
        DepartamentsDAO.getDepartaments()
                .stream()
                .filter(d -> d.getNom().equals("Comercial") || d.getNom().equals("Comptabilitat"))
                .map(Departament::getEmpleats)
                .flatMap(ArrayList::stream)
                .map(Empleat::getNom)
                .forEach(System.out::println);

        System.out.println("-----11-----");
        // 11. El mateix d'abans però mostra els noms ordenats alfabèticament. (altra opció per al filter)
        final Predicate<Departament> comercial = d -> d.getNom().equals("Comercial");
        final Predicate<Departament> comptabilitat = d -> d.getNom().equals("Comptabilitat");

        DepartamentsDAO.getDepartaments()
                .stream()
                .filter(comercial.or(comptabilitat))
                .map(Departament::getEmpleats)
                .flatMap(ArrayList::stream)
                .map(Empleat::getNom)
                .sorted()
                .forEach(System.out::println);

        System.out.println("-----12-----");
        // 12. Mostra la mitjana d'edat dels empleats del departament d'informàtica.
        EmpleatsDAO.getEmpleats()
                .stream()
                .filter(e -> e.getDepartament().getNom().equals("Informàtica"))
                .mapToInt(Empleat::getEdat)
                .average()
                .ifPresent(System.out::println);

        System.out.println("-----13-----");
        // 13. Mostra un String que serà el resultat de concatenar la primera lletra de cada departament.
        System.out.println(
                DepartamentsDAO.getDepartaments()
                        .stream()
                        .map(Departament::getNom)
                        .reduce("", (a, d) -> a.concat(String.valueOf(d.charAt(0))))
        );

        System.out.println("-----14-----");
        // 14. Mostra el número de telèfon més alt dels departaments.
        DepartamentsDAO.getDepartaments()
                .stream()
                .mapToInt(Departament::getTelefon)
                .max()
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Telèfon no trobat")
                );

        System.out.println("-----14B-----");
        DepartamentsDAO.getDepartaments()
                .stream()
                .mapToInt(Departament::getTelefon)
                .reduce((a, b) -> Math.max(a, b))
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Telèfon no trobat")
                );

        // 15. Mostra el departament complet amb el número de telèfon més alt.
        System.out.println("-----  15 -------");

        DepartamentsDAO.getDepartaments()
                .stream()
                .reduce((a, d) -> (d.getTelefon() > a.getTelefon()) ? d : a)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Departament no trobat")
                );

        System.out.println("-----15B-----");
        DepartamentsDAO.getDepartaments()
                .stream()
                .sorted((d1,d2) -> d2.getTelefon() - d1.getTelefon())
                .findFirst()
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Departament no trobat")
                );
        System.out.println("-----15C-----");
        DepartamentsDAO.getDepartaments()
                .stream()
                .sorted(Comparator.comparing(Departament::getTelefon).reversed())
                .findFirst()
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Departament no trobat")
                );


        // 16. Sense fer ús del mètode "getEmpleats". Dels departament que tenen treballadors, mostrar el nom del departament i el nombre de treballadors que hi treballen.
        System.out.println("-----16----- (getDepartaments)");
        DepartamentsDAO.getDepartaments()
                .stream()
                .filter(d -> !d.getEmpleats().isEmpty())
                .collect(toMap(Departament::getNom, d -> d.getEmpleats().size()))
                .forEach((nom,empleats) -> System.out.println(nom + " -> " + empleats));
        
        //Fent ús de getEmpleats:
        System.out.println("-----16----- (getEmpleats)");
        EmpleatsDAO.getEmpleats()
                .stream()
                .collect(groupingBy(Empleat::getDepartament, counting()))
                .forEach((k, v) -> System.out.println(k.getNom() + " -> " + v));

        // 17. Guarda en un Map un registre per a cada Departament (que tinga treballadors) que tinga associat com a valor la llista d'empleats d'eixe departament
        System.out.println("-----17----- (getDepartaments)");
        final Map<Departament, List<Empleat>> mapa = DepartamentsDAO.getDepartaments()
                .stream()
                .filter(d -> !d.getEmpleats().isEmpty())
                .collect(toMap(Function.identity(), Departament::getEmpleats));

        mapa.forEach((k, v) -> System.out.println(k.getNom() + ":\n\t" + v));
        
        // Fent ús de getEmpleats
        System.out.println("-----17----- (getEmpleats)");
        final Map<Departament, List<Empleat>> mapaB = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(groupingBy(Empleat::getDepartament, toList()));

        mapaB.forEach((k, v) -> System.out.println(k.getNom() + ":\n\t" + v));

        System.out.println("-----17B-----");
        // 17B. Igual que l'anterior pero la llista no serà d'empleats sino del nom dels empleats
        final Map<Departament, List<String>> mapa2 = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(groupingBy(Empleat::getDepartament, mapping(Empleat::getNom, toList())));

        mapa2.forEach((k, v) -> System.out.println(k.getNom() + ":\n\t" + v));

        System.out.println("-----17C-----");
        // 17C. Igual que l'anterior pero amb els empleats ordenats alfabèticament
        final Map<Departament, List<String>> mapa3 = EmpleatsDAO.getEmpleats()
                .stream()
                .sorted((e1, e2) -> e1.getNom().compareTo(e2.getNom()))
                .collect(groupingBy(Empleat::getDepartament, mapping(Empleat::getNom, toList())));

        mapa3.forEach((k, v) -> System.out.println(k.getNom() + ":\n\t" + v));
        
        System.out.println("-----17C-----");
        final Map<Departament, List<String>> mapa3B = EmpleatsDAO.getEmpleats()
                .stream()
                .sorted(Comparator.comparing(Empleat::getNom))
                .collect(groupingBy(
                            Empleat::getDepartament, 
                            mapping(
                                    Empleat::getNom, 
                                    toList()
                            )
                        )
                );

        mapa3B.forEach((k, v) -> System.out.println(k.getNom() + ":\n\t" + v));

        System.out.println("-----17C-----");
        final Map<Departament, List<String>> mapa4 = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(
                        groupingBy(
                                Empleat::getDepartament, 
                                mapping(
                                        Empleat::getNom, 
                                        Collectors.collectingAndThen(
                                                toList(), 
                                                l -> {
                                                    Collections.sort(l);
                                                    return l;
                                                }
                                        )
                                )
                        )
                );

        mapa4.forEach((k, v) -> System.out.println(k.getNom() + ":\n\t" + v));

        System.out.println("-----18-----");
        // 18. Obtín un Map que continga per a cada departament una llista d'enters grans (BigInteger) que es corresponguen amb les edats dels empleats d'eixe departament
        final Map<Departament, List<BigInteger>> edatsDepartaments = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(
                        groupingBy(
                                Empleat::getDepartament,
                                mapping(
                                        e -> BigInteger.valueOf(e.getEdat()),
                                        toList()
                                )
                        )
                );

        System.out.println(edatsDepartaments);
        
        System.out.println("-----18B-----");
        // 18B. El mateix que abans però en comptes de l'edat, el probable següent número primer
        final Map<Departament, List<BigInteger>> edatsPrimerDepartaments = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(
                        groupingBy(
                                Empleat::getDepartament, 
                                mapping(e -> BigInteger.valueOf(e.getEdat()).nextProbablePrime(), toList())
                        )
                );

        System.out.println(edatsPrimerDepartaments);

        System.out.println("-----19-----");
        // 19. Obtín un Map que continga per a cada departament l'empleat més jove.
        final Map<Departament, Empleat> empleatsJoves = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(
                        groupingBy(
                                Empleat::getDepartament, 
                                Collectors.collectingAndThen(
                                        toList(), 
                                        l -> l.stream().collect(minBy(Comparator.comparingInt(Empleat::getEdat))).get()
                                )
                        )
                );

        empleatsJoves.forEach((k, v) -> System.out.println(k.getNom() + ": " + v.getNom() + " - " + v.getEdat()));
        
        System.out.println("-----19B-----");
        final Map<Departament, Empleat> empleatsJovesB = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(
                        groupingBy(
                                Empleat::getDepartament, 
                                Collectors.collectingAndThen(
                                        toList(), 
                                        l -> Collections.min(l, Comparator.comparing(Empleat::getEdat))
                                )
                        )
                );

        empleatsJovesB.forEach((k, v) -> System.out.println(k.getNom() + ": " + v.getNom() + " - " + v.getEdat()));
        
        System.out.println("-----19C-----");
        final Map<Departament, Empleat> empleatsJovesC = DepartamentsDAO.getDepartaments()
                .stream()
                .filter(d -> !d.getEmpleats().isEmpty())
                .collect(toMap(Function.identity(), d -> d.getEmpleats().stream().min(Comparator.comparing(Empleat::getEdat)).orElse(null)));
                

        empleatsJovesC.forEach((k, v) -> System.out.println(k.getNom() + ": " + v.getNom() + " - " + v.getEdat()));

        System.out.println("-----20-----");
        // 20. Obtín una llista d'Strings que continga el mateix que abans, amb el format "Departament1:Empleat1, Departament2:Empleat2 ..."
        final String empleatsJoves2 = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(groupingBy(
                        Empleat::getDepartament,
                        collectingAndThen(
                                minBy(Comparator.comparingInt(Empleat::getEdat)),
                                opt -> opt.map(emp -> emp.getDepartament().getNom() + ":" + emp.getNom())
                                          .orElse("")
                        )
                    )
                )
                .values()
                .stream()
                .collect(joining(", "));
        System.out.println(empleatsJoves2);
        
        System.out.println("-----20B-----");
        final String empleatsJoves2B = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(collectingAndThen(
                                groupingBy(
                                        Empleat::getDepartament,
                                        collectingAndThen(
                                                minBy(Comparator.comparingInt(Empleat::getEdat)),
                                                o -> o.map(Empleat::getNom).orElse("Sense empleats joves")
                                        )
                                ),
                                map -> map.entrySet().stream()
                                        .map(entry -> entry.getKey().getNom() + ":" + entry.getValue())
                                        .collect(joining(", "))
                        )
                );

        System.out.println(empleatsJoves2B);
        
        
        System.out.println("-----20C-----");
        final String empleatsJoves2C = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(
                        collectingAndThen(
                                groupingBy(
                                        Empleat::getDepartament, collectingAndThen(
                                                toList(), l -> l.stream().collect(minBy(Comparator.comparingInt(Empleat::getEdat))).get()
                                        )
                                ),
                                h -> h.keySet().stream().map(k -> k.getNom() + ":" + h.get(k).getNom()).collect(joining(", "))
                        )
                );
        System.out.println(empleatsJoves2C);
        
        

        System.out.println("-----21-----");
        // 21. Obtín un Map que conté com a clau el DNI dels empleats i com a valor el nom d'eixe empleat
        final Map<String, String> dniNoms = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(
                        toMap(
                                Empleat::getDni,
                                Empleat::getNom
                        )
                );
        System.out.println(dniNoms);
        
        System.out.println("-----21B-----");
        // 21B. Obtín una llista d'Strings que continga DNI dels empleats i el nom d'eixe empleat amb el format DNI - Nom. Llista ordenada per DNI
        final List<String> dniNoms2 = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(
                                        Empleat::getDni,
                                        Empleat::getNom
                                ),
                                h -> h.keySet().stream().sorted().map(k -> k + " - " + h.get(k)).collect(toList())
                        )
                );
        System.out.println(dniNoms2);

        System.out.println("-----21C-----");
        
        // Versió més senzilla
        final List<String> dniNoms3 = EmpleatsDAO.getEmpleats()
                .stream()
                .sorted((e1, e2) -> e1.getDni().compareTo(e2.getDni()))
                .map(e -> e.getDni() + " - " + e.getNom())
                .collect(toList());

        System.out.println(dniNoms3);
        
        System.out.println("-----21D-----");
        List<String> dniNoms3B = EmpleatsDAO.getEmpleats()
                .stream()
                .sorted(Comparator.comparing(Empleat::getDni)) // Ordena por DNI
                .map(empleado -> empleado.getDni() + " - " + empleado.getNom())
                .collect(toList());
        System.out.println(dniNoms3B);

        System.out.println("-----22-----");
        // 22. Donat un array bidimensional d'Integer transformar-lo en un array unidimensional amb els mateixos valors:
        final Integer[][] matriuInteger = {{3, 2, 5}, {0, -8, 7}, {9, 9, 6}};

        final Integer[] arrayInteger = Arrays.stream(matriuInteger)
                .flatMap(Arrays::stream)
                //.sorted() Si volem ordenar tots els valors
                .toArray(Integer[]::new);

        System.out.println(Arrays.toString(arrayInteger));

        System.out.println("-----22B-----");
        // Versió amb "int"
        final int[] arrayInt = Arrays.stream(matriuInteger)
                .flatMap(Arrays::stream)
                .mapToInt(i -> i)
                //.sorted() Si volem ordenar tots els valors
                .toArray();

        System.out.println(Arrays.toString(arrayInt));
        
        System.out.println("-----23-----");
        // 23. Guarda una llista amb els noms dels estudis de tots els empleats (de forma única), ordenats alfabèticament.
        final List<String> titols = EmpleatsDAO.getEmpleats()
                .stream()
                .map(Empleat::getTitols)
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .collect(toList());

        System.out.println(titols);

        System.out.println("-----24-----");
        // 24. Imprimeix per pantalla aquells empleats que tinguen un CFGS
        EmpleatsDAO.getEmpleats()
                .stream()
                .filter(e -> e.getTitols().contains("CFGS"))
                .forEach(System.out::println);

        System.out.println("-----25-----");
        // 25. Mostra un empleat que tinga una llicenciatura
         EmpleatsDAO.getEmpleats()
                .stream()
                .filter(e -> e.getTitols().contains("Llicenciatura"))
                .findAny()
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("No hi ha empleats amb llicenciatura")
                );

         System.out.println("-----26-----");
        // 26. Mostra per a cada nom d'empleat la suma de les lletres dels seus titols
        System.out.println(EmpleatsDAO.getEmpleats()
                .stream()
                .collect(
                        Collectors.toMap(Empleat::getNom, e -> e.getTitols().stream().mapToInt(String::length).sum())
                )
        );

        System.out.println("-----27-----");
        // 27. Mostra el total de lletres dels titols (incloent duplicats) dels empleats
        System.out.println(EmpleatsDAO.getEmpleats()
                .stream()
                .map(Empleat::getTitols)
                .flatMap(List::stream)
                .mapToInt(String::length)
                .sum()
        );

        System.out.println("-----28-----");
        // 28. Mostra l'empleat amb major nombre de títols
        EmpleatsDAO.getEmpleats()
                .stream()
                .max((e1, e2) -> e1.getTitols().size() - e2.getTitols().size())
                .ifPresent(System.out::println);
        
        System.out.println("-----28B-----");
        EmpleatsDAO.getEmpleats()
                .stream()
                .max(Comparator.comparing(e -> e.getTitols().size()))
                .ifPresent(System.out::println);

        System.out.println("-----29-----");
        // 29. Obtín una llista que continga 5 aleatoris entre 0 i 9 en format String
        final List<String> aleatoris1 = Stream.generate(() -> new Random().nextInt(10))
                .limit(5)
                .map(String::valueOf)
                .collect(toList());

        System.out.println(aleatoris1);
        
        System.out.println("-----29----- (amb random.ints)");
        
        final List<String> aleatoris1B = new Random().ints(5, 0, 10)
                .mapToObj(String::valueOf)
                .collect(toList());

        System.out.println(aleatoris1B);

        System.out.println("-----29A-----");
        // 29A. Sense valors repetits.
        final List<String> aleatoris2 = Stream.generate(() -> new Random().nextInt(10))
                .distinct()
                .limit(5)
                .map(String::valueOf)
                .collect(toList());

        System.out.println(aleatoris2);
        
        System.out.println("-----29A----- (amb random.ints)");
        final List<String> aleatoris2B = new Random().ints(0,10)
                .distinct()
                .limit(5)
                .mapToObj(String::valueOf)
                .collect(toList());

        System.out.println(aleatoris2B);

        System.out.println("-----30-----");
        // 30. Crea un stream amb tots els números positius divisibles entre 3. Després filtra aquells que siguen quadrats perfectes. 
        // Després descarta els 5 primers valors obtinguts. Finalment mostra els 10 següents valors obtinguts
        IntStream.iterate(3, i -> i + 3)
                .filter(i -> Math.sqrt(i) - Math.floor(Math.sqrt(i)) == 0)
                .skip(5)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("-----31-----");
        // 31. A partir dels cognoms dels empleats imprimeix amb una operació intermedia els cognoms sense transformar i després transformats en majúscules.
        // Finalment retorna el nombre d'empleats.
        final long total = EmpleatsDAO.getEmpleats()
                .stream()
                .map(Empleat::getCognoms)
                .peek(System.out::println)
                .map(String::toUpperCase)
                .peek(System.out::println)
                .collect(Collectors.counting());

        System.out.println("TOTAL: " + total);

        System.out.println("-----32-----");
        // 32. Imprimeix el cognom dels empleats ordenats en ordre alfabètic invers.
        EmpleatsDAO.getEmpleats()
                .stream()
                .map(Empleat::getCognoms)
                //.sorted((s1, s2) -> s2.compareTo(s1))
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        System.out.println("-----33-----");
        // 33. Obtín la suma dels títols de tots els empleats
        final int totalTitols = EmpleatsDAO.getEmpleats()
                .stream()
                .map(Empleat::getTitols)
                .mapToInt(ArrayList::size)
                .sum();

        System.out.println(totalTitols);

        // Amb reduce
        System.out.println("-----33B-----");
        final int totalTitols2 = EmpleatsDAO.getEmpleats()
                .stream()
                .map(Empleat::getTitols)
                .map(ArrayList::size)
                .reduce(0, (ac, et) -> ac + et);

        System.out.println(totalTitols2);

        System.out.println("-----34-----");
        // 34. Obtín la concatenació dels títols d'aquells empleats que tinguen menys de 30 anys, separats per espais (sense duplicats)
        final String titols30 = EmpleatsDAO.getEmpleats()
                .stream()
                .filter(e -> e.getEdat() < 30)
                .map(Empleat::getTitols)
                .flatMap(List::stream)
                .distinct()
                .collect(joining(" "));

        System.out.println(titols30);

        // Amb reduce
        System.out.println("-----34B-----");
        final String titols30b = EmpleatsDAO.getEmpleats()
                .stream()
                .filter(e -> e.getEdat() < 30)
                .map(Empleat::getTitols)
                .flatMap(List::stream)
                .distinct()
                .reduce("", (a, s) -> a.concat(s + " "));
        System.out.println(titols30b);

        // 35. Mostra el cognom del primer dels empleats trobat que tinga menys de 30 anys i que tinga una llicenciatura. En cas de no trobar-lo mostra "No trobat"
        // ¿I si proves per a menors de 40?
        System.out.println("-----35-----");
        System.out.println(
                EmpleatsDAO.getEmpleats()
                        .stream()
                        .filter(e -> e.getEdat() < 30)
                        .filter(e -> e.getTitols().contains("Llicenciatura"))
                        .map(Empleat::getCognoms)
                        .findFirst()
                        .orElse("No trobat!")
        );

        System.out.println("-----35B-----");
        // 35B. Mostra el primer dels empleats (complet) trobat que tinga menys de 30 anys i que tinga una llicenciatura. En cas de no trobar-lo mostra "No trobat"
        // ¿I si proves per a menors de 40?
        EmpleatsDAO.getEmpleats()
                .stream()
                .filter(e -> e.getEdat() < 30)
                .filter(e -> e.getTitols().contains("Llicenciatura"))
                .findFirst()
                .ifPresentOrElse(System.out::println, () -> System.out.println("No trobat!"));

        System.out.println("-----36-----");
        // 36. Obtín un LinkedHashSet dels títols que tenen tots els empleats.
        final LinkedHashSet<String> titolsSet = EmpleatsDAO.getEmpleats()
                .stream()
                .map(Empleat::getTitols)
                .flatMap(List::stream)
                .collect(toCollection(LinkedHashSet::new));

        System.out.println(titolsSet);

        System.out.println("-----37-----");
        // 37. Obtín un Map amb dos claus, la primera tindrá com a valors una llista dels empleats amb el títol de CFGS i l'altra clau tindrá una llista amb el que no el tenen.
        final Map<Boolean, List<Empleat>> empleatsAmbCFGS = EmpleatsDAO.getEmpleats()
                .stream()
                .collect(Collectors.partitioningBy(e -> e.getTitols().contains("CFGS")));

        empleatsAmbCFGS.forEach((k, v) -> System.out.println((k ? "Amb CFGS" : "Sense CFGS") + ": " + v));

        System.out.println("-----38-----");
        // 38. A partir d'una llista d'String retorna la mateixa llista pero sense cadenes buides
        final List<String> elementsOriginals = List.of("aigua", "", "llet", "oli", "", "  ", "lletuga");
        System.out.println(elementsOriginals);
        // final List<String> elementsFiltrats = elementsOriginals.stream().filter(s -> !s.isBlank()).collect(toList());
        final List<String> elementsFiltrats = elementsOriginals.stream().filter(Predicate.not(String::isBlank)).collect(toList());
        System.out.println(elementsFiltrats);

        System.out.println("-----39-----");
        // 39. Obtín una cadena separada per comes basada en una llista determinada d'enters. 
        // Cada element ha d'anar precedit de la lletra 'p' si el nombre és parell i precedit de la lletra 'i' si el nombre és imparell. 
        // Per exemple, si la llista d'entrada és (3,44), la eixida hauria de ser 'i3, p44'.
        final List<Integer> llistaEnters = List.of(9, 23, 4, 15);

        final String llistaEntersModificada = llistaEnters.stream()
                .map(e -> (e % 2 == 0 ? "p" : "i") + e)
                .collect(Collectors.joining(", "));

        System.out.println(llistaEntersModificada);

        System.out.println("-----40-----");
        // 40. Obtín un mapa que tinga com a clau el nom del departament (amb empleats) i com a valor un altre mapa
        // Este segon mapa tindrà com a clau el cognom de l'empleat y com a valor la llista de títols que té.
        final Map<String, Map<String, Integer>> titolsEmpleatDepartament
                = EmpleatsDAO.getEmpleats()
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        e -> e.getDepartament().getNom(),
                                        Collectors.toMap(e -> e.getCognoms(), e -> e.getTitols().size())
                                )
                        );
        titolsEmpleatDepartament.forEach((kd, vd) -> {
            System.out.println(kd + ": ");
            vd.forEach((ke, ve) -> System.out.println("\t" + ke + " té " + ve + " títols"));
        });
        System.out.println("--- EXTRA A ---");
        // EXTRA A: A partir d'un String retorna les lletres distintes que conté (només lletres, no espais ni números ni altres caracters)
        final String frase1 = "Tinc un 8 en PROG";
        final String lletresDistintes = frase1.chars()
                .filter(Character::isAlphabetic)
                .map(Character::toLowerCase)
                .distinct()
                .mapToObj(Character::toString)
                .collect(Collectors.joining(", "));
        System.out.println(lletresDistintes);

        System.out.println("--- EXTRA B ---");
        // EXTRA B: A partir d'un String retorna el número de lletres distintes que conté (només lletres, no espais ni números ni altres caracters)
        final long numeroDistintes = frase1.chars()
                .filter(Character::isAlphabetic)
                .map(Character::toLowerCase)
                .distinct()
                .mapToObj(Character::toString)
                .count();

        System.out.println(numeroDistintes);

        System.out.println("--- EXTRA C ---");
        // EXTRA C: Obtín un mapa que mostre com a clau cada lletra distinta del String, i com a valor el nombre de vegades que apareix.
        final Map<String, Long> lletraFrequencia = frase1.chars()
                .filter(Character::isAlphabetic)
                .map(Character::toLowerCase)
                .mapToObj(Character::toString)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println(lletraFrequencia);

        System.out.println("--- EXTRA D ---");
        // EXTRA D: Mostra la lletra amb major frequencia d'aparicions, i el nombre d'aparicions d'eixa lletra.
        lletraFrequencia.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresentOrElse(
                        System.out::println, 
                        () -> System.out.println("Lletres no trobades")
                );

    }

}
