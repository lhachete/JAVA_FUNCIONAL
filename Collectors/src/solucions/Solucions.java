/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package solucions;

import main.*;
import dao.ProducteDAO;
import dto.Producte;
import java.security.CodeSigner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author jrmd
 */
public class Solucions {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // 1. Guarda en un ArrayList els productes que costen entre 100 i 300 euros (sense iva)
        ArrayList<Producte> productes100a300 = ProducteDAO.getAll().stream()
                .filter(p -> p.getPreu() >= 100 && p.getPreu() <= 300)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(productes100a300);

        // 2. Guarda en un LinkedHashSet els preus dels productes d'alimentació, ordenats de major a menor
        LinkedHashSet<Double> preusAlimentacio = ProducteDAO.getAll().stream()
                .filter(p -> p.getCategoria().equals("Aliments"))
                .map(Producte::getPreu)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println(preusAlimentacio);

        // 3. Guarda en un List aquells productes que el seu nom comence per "A" sabent que la llista ha de quedar immutable.
        List<Producte> productesA = ProducteDAO.getAll().stream()
                .filter(p -> p.getNom().startsWith("A"))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        //productesA.add(null);
        System.out.println(productesA);

        // 4. Guarda en un String les categories (sense repetir), ordenades alfabèticament i separades per comes.
        String categories = ProducteDAO.getAll().stream()
                .map(Producte::getCategoria)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println(categories);

        // 5. Guarda el producte amb el preu més alt
        Producte producteCar = ProducteDAO.getAll().stream()
                .collect(Collectors.maxBy(Comparator.comparingDouble(Producte::getPreu)))
                .orElse(null);
        System.out.println(producteCar);

        // 6. Guarda el producte amb el nom més curt
        Producte producteLlarg = ProducteDAO.getAll().stream()
                .collect(Collectors.minBy(Comparator.comparingInt(p -> p.getNom().length())))
                .orElse(null);
        System.out.println(producteLlarg);

        // 7. Guarda la mitjana de preus dels procesadors
        double mitjanaProcesadors = ProducteDAO.getAll().stream()
                .filter(p -> p.getCategoria().equals("Procesadors"))
                .collect(Collectors.averagingDouble(Producte::getPreu));
        System.out.println(mitjanaProcesadors);

        // 8. Guarda en un mapa agrupant per categoria la llista de productes de cada categoria
        Map<String, List<Producte>> productesPerCategoria = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(Producte::getCategoria));
        productesPerCategoria.forEach((categoria, llista) -> System.out.println(categoria + ": " + llista));

        // 9. Guarda en un mapa agrupant per categoria la mitjana de preus de cada categoria
        Map<String, Double> mitjanaPerCategoria = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(Producte::getCategoria, Collectors.averagingDouble(Producte::getPreu)));
        mitjanaPerCategoria.forEach((categoria, mitjana) -> System.out.println(categoria + ": " + mitjana));

        // ¿Qué farà el següent codi?
        Map<Double, List<Producte>> tramsDe100 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(p -> Math.round(p.getPreu() / 100) * 100.0));
        tramsDe100.forEach((preu, llista) -> System.out.println(preu + ": " + llista));

        // 9. Guarda en un mapa agrupant per la primera lletra del nom del producte, la llista de noms per a cada grup
        Map<Character, List<String>> nomsPerPrimeraLletra = ProducteDAO.getAll().stream()
                .map(Producte::getNom)
                .collect(Collectors.groupingBy(n -> n.charAt(0)));
        nomsPerPrimeraLletra.forEach((lletra, noms) -> System.out.println(lletra + ": " + noms));

        // 10. Guarda la mateixa informació d'abans però ara en un LinkedHashMap ordenat per clau
        System.out.println("---10---");
        LinkedHashMap<Character, List<String>> nomsPerPrimeraLletra2 = ProducteDAO.getAll().stream()
                .map(Producte::getNom)
                .sorted()
                .collect(Collectors.groupingBy(n -> n.charAt(0), LinkedHashMap::new, Collectors.toList()));
        nomsPerPrimeraLletra2.forEach((lletra, noms) -> System.out.println(lletra + ": " + noms));

        // 11. Guarda un mapa amb dos grups (true i false). El primer agruparà els productes d'Aliments, i el segon agruparà la resta.
        System.out.println("---11---");
        Map<Boolean, List<Producte>> llistaAlimentacioAltres = ProducteDAO.getAll().stream()
                .collect(Collectors.partitioningBy(p -> p.getCategoria().equals("Aliments")));
        llistaAlimentacioAltres.forEach((boolea, llista) -> System.out.println(boolea + ": " + llista));

        // 12. Guarda un mapa amb dos grups (true i false). El primer agruparà els noms dels productes que comencen per vocal separats per commes, i el segon la resta.
        System.out.println("---12---");
        Map<Boolean, String> llistaProductesVocal = ProducteDAO.getAll().stream()
                .map(Producte::getNom)
                .collect(Collectors.partitioningBy(ProducteDAO::comensaPerVocal, Collectors.joining(", ")));
        llistaProductesVocal.forEach((boolea, llista) -> System.out.println(boolea + ": " + llista));

        // 13. Guarda en un mapa agrupant per categoria el noms dels productes separats per comes per a cada categoria
        System.out.println("---13---");
        Map<String, String> productesPerCategoria2 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(Producte::getCategoria, Collectors.mapping(Producte::getNom, Collectors.joining(", "))));

        productesPerCategoria2.forEach((categoria, llista) -> System.out.println(categoria + ": " + llista));

        // 14. Guarda en un mapa agrupant per categoria un LinkedHashSet dels preus d'eixa categoria
        System.out.println("---14---");
        Map<String, LinkedHashSet<Double>> preusPerCategoria = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(Producte::getCategoria, Collectors.mapping(Producte::getPreu, Collectors.toCollection(LinkedHashSet::new))));

        preusPerCategoria.forEach((categoria, llista) -> System.out.println(categoria + ": " + llista));

        // 15. Mostra el mateix mapa d'abans pero ara guarda els preus en un LinkedList ordenat de menor a major.
        System.out.println("---15---");
        Map<String, LinkedList<Double>> preusPerCategoria2 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(Producte::getCategoria,
                        Collectors.mapping(Producte::getPreu,
                                Collectors.collectingAndThen(
                                        Collectors.toCollection(LinkedList::new), l -> {
                                    Collections.sort(l);
                                    return l;
                                }))));

        preusPerCategoria2.forEach((categoria, llista) -> System.out.println(categoria + ": " + llista));

        // 16. Guarda en un mapa agrupant per categoria la quantitat de productes d'eixa categoria
        System.out.println("---16---");
        Map<String, Long> quantitatPerCategoria = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(
                        Producte::getCategoria,
                        Collectors.counting()
                ));

        quantitatPerCategoria.forEach((categoria, quantitat) -> System.out.println(categoria + ": " + quantitat));

        // 17. Guarda la mateixa informació d'abans però ara en un TreeMap<String,Integer>
        System.out.println("---17---");
        TreeMap<String, Integer> quantitatPerCategoria2 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(
                        Producte::getCategoria,
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                Long::intValue
                        )));

        quantitatPerCategoria2.forEach((categoria, quantitat) -> System.out.println(categoria + ": " + quantitat));
        
        //18. Guarda en un Array d'Strings aquelles categories (sense repetir i ordenades) que tinguen algun producte més car dels 100 euros
        System.out.println("---18---");
        String[] categoriesMesDe100 = ProducteDAO.getAll().stream()
                .filter(p -> p.getPreu()>100)
                .map(Producte::getCategoria)
                .distinct()
                .sorted()
                .toArray(String[]::new);
        System.out.println(Arrays.toString(categoriesMesDe100));
        

    }

}
