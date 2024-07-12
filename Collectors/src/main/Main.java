/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // 1. Guarda en un ArrayList els productes que costen entre 100 i 300 euros (sense iva)
        ArrayList<Producte> producto = ProducteDAO.getAll().stream()
                .filter(e -> e.getPreu() >= 100 && e.getPreu() <= 300)
                .collect(Collectors.toCollection(ArrayList::new));
//        System.out.println(producto);

        // 2. Guarda en un LinkedHashSet els preus dels productes d'alimentació, ordenats de major a menor
        LinkedHashSet<Double> producto2 = ProducteDAO.getAll().stream()
                .filter(e -> e.getCategoria().equals("Aliments"))
                .map(e -> e.getPreu())
                .collect(Collectors.toCollection(LinkedHashSet::new));
//        System.out.println(producto2);

        // 3. Guarda en un List aquells productes que el seu nom comence per "A" sabent que la llista ha de quedar immutable.
        List<String> producto3 = ProducteDAO.getAll().stream()
                .filter(e -> e.getNom().startsWith("A"))
                .map(e -> e.toString())
                .collect(Collectors.toUnmodifiableList());
//        System.out.println(producto3);

        // 4. Guarda en un String les categories (sense repetir), ordenades alfabèticament i separades per comes.
        String producto4 = ProducteDAO.getAll().stream()
                .map(Producte::getCategoria)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", ", "Categorias: ", ""));
//        System.out.println(producto4);

        // 5. Guarda el producte amb el preu més alt
        Producte producto5 = ProducteDAO.getAll().stream()
                .collect(Collectors.maxBy(Comparator.comparingDouble(Producte::getPreu)))
                .orElse(null);
//        System.out.println(producto5);

        // 6. Guarda el producte amb el nom més curt
        Producte producto6 = ProducteDAO.getAll().stream()
                .collect(Collectors.minBy(Comparator.comparingInt(Producte -> Producte.getNom().length())))
                .orElse(null);
//        System.out.println(producto6);

        // 7. Guarda la mitjana de preus dels procesadors
        Double producto7 = ProducteDAO.getAll().stream()
                .filter(e -> e.getCategoria().equals("Procesadors"))
                .collect(Collectors.averagingDouble(Producte::getPreu));
//        System.out.println(producto7);

        // 8. Guarda en un mapa agrupant per categoria la llista de productes de cada categoria
        Map<String, List<Producte>> producto8 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(e -> e.getCategoria()));
        producto8.forEach((categoria, lista) -> System.out.println(categoria + ": " + lista));

        // 9. Guarda en un mapa agrupant per categoria la mitjana de preus de cada categoria
        Map<String, Double> producto9 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(Producte::getCategoria, Collectors.averagingDouble(Producte::getPreu)));
        producto9.forEach((categoria, med) -> System.out.println(categoria + " y su media de precios " + med));

//         ¿Qué farà el següent codi?
        Map<Double, List<Producte>> tramsDe100 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(p -> Math.round(p.getPreu() / 100) * 100.0));
        tramsDe100.forEach((preu, llista) -> System.out.println(preu + ": " + llista));

        // 9. Guarda en un mapa agrupant per la primera lletra del nom del producte, la llista de noms per a cada grup
        Map<Character, List<String>> producto9_2 = ProducteDAO.getAll().stream()
                .map(e -> e.getNom())
                .collect(Collectors.groupingBy(e -> e.charAt(0)));
        producto9_2.forEach((carac, lista) -> System.out.println("Caracter: " + carac + " , lista: " + lista));

        // 10. Guarda la mateixa informació d'abans però ara en un LinkedHashMap ordenat per clau
        LinkedHashMap<Character, List<String>> producto10 = ProducteDAO.getAll().stream()
                .map(Producte::getNom)
                .sorted()
                .collect(Collectors.groupingBy(n -> n.charAt(0), LinkedHashMap::new, Collectors.toList()));
        producto10.forEach((carac, lista) -> System.out.println("Caracter: " + carac + " , lista: " + lista));

        // 11. Guarda un mapa amb dos grups (true i false). El primer agruparà els productes d'Aliments, i el segon agruparà la resta.
        System.out.println("---11---");
        Map<Boolean, List<Producte>> llistaAlimentacioAltres = ProducteDAO.getAll().stream()
                .collect(Collectors.partitioningBy(p -> p.getCategoria().equals("Aliments")));
        llistaAlimentacioAltres.forEach((boolea, llista) -> System.out.println(boolea + ": " + llista));

        // 12. Guarda un mapa amb dos grups (true i false).
        //El primer agruparà els noms dels productes que comencen per vocal separats per commes, i el segon la resta.
        System.out.println("---11---");
        Map<Boolean, String> productos12 = ProducteDAO.getAll().stream()
                .map(Producte::getNom)
                .collect(Collectors.partitioningBy(ProducteDAO::comensaPerVocal, Collectors.joining(", ")));
        
        productos12.forEach((boolea, llista) -> System.out.println(boolea + ": " + llista));
        
        // 13. Guarda en un mapa agrupant per categoria el noms dels productes separats per comes per a cada categoria
        Map <String, String> productos13 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(Producte::getCategoria, Collectors.mapping(Producte::getNom, Collectors.joining(", "))));
        productos13.forEach((cat, llista) -> System.out.println(cat + ": " + llista));
        
        // 14. Guarda en un mapa agrupant per categoria un LinkedHashSet dels preus d'eixa categoria
        Map <String, LinkedHashSet<Double>> producto14 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(Producte::getCategoria, Collectors.mapping(Producte::getPreu, Collectors.toCollection(LinkedHashSet::new))));
        producto14.forEach((cat, llista) -> System.out.println(cat + ": " + llista));
        
        // 15. Mostra el mateix mapa d'abans pero ara guarda els preus en un LinkedList ordenat de menor a major.
        Map<String, LinkedList<Double>> producto15_2 = ProducteDAO.getAll().stream()
        .collect(Collectors.groupingBy(Producte::getCategoria,
                Collectors.mapping(Producte::getPreu,
                        Collectors.collectingAndThen(Collectors.toCollection(LinkedList::new), l -> {
                            l.sort(Double::compareTo); // O Collections.sort(l)
                            return l;
                        }))));

        
        producto15_2.forEach((cat, llista) -> System.out.println(cat + ": " + llista));
        
        // 16. Guarda en un mapa agrupant per categoria la quantitat de productes d'eixa categoria
        Map<String, Long> producto16 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(Producte::getCategoria,
                        Collectors.counting()
                ));
        producto16.forEach((cat, quant) -> System.out.println(cat + ": " + quant));
                
        
        // 17. Guarda la mateixa informació d'abans però ara en un TreeMap<String,Integer>
        TreeMap<String,Integer> producto17 = ProducteDAO.getAll().stream()
                .collect(Collectors.groupingBy(Producte::getCategoria,
                        TreeMap::new, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
        producto17.forEach((cat, quant) -> System.out.println(cat + ": " + quant));
        
        
        //18. Guarda en un Array d'Strings aquelles categories (sense repetir i ordenades) que tinguen algun producte més car dels 100 euros
        ArrayList<String> productoFinal =  ProducteDAO.getAll().stream()
                .filter(e -> e.getPreu() > 100)
                .map(Producte::getCategoria)
                .distinct()
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(productoFinal);
    }

}
