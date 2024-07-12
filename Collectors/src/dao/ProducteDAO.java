/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Producte;
import java.util.List;

/**
 *
 * @author jrmd
 */
public class ProducteDAO {

    public static List<Producte> getAll() {
        return List.of(
                new Producte(1, "AMD Ryzen 7 5800X 3.8GHz", "Procesadors", 325.99, 21),
                new Producte(2, "MSI RTX 3060 TI VENTUS 2X", "T.Gràfiques", 599.91, 21),
                new Producte(5, "Hummus de cigrons", "Aliments", 1.2, 10),
                new Producte(7, "Intel Core i5-11400F 2.6 GHz", "Procesadors", 155.99, 21),
                new Producte(8, "MSI GeForce GTX 1650 D6", "T.Gràfiques", 247.03, 21),
                new Producte(9, "Olives negres sense pinyol 350g", "Aliments", 0.95, 10),
                new Producte(10, "Gigabyte GeForce RTX 3090 Ti GAMING OC 24GB GDDR6X", "T.Gràfiques", 2149.89, 21),
                new Producte(12, "Pipes girasol torrades sense sal 125g", "Aliments", 0.95, 10),
                new Producte(18, "Amanida de la casa de panís, lletuga, tomata, carlota i olives", "Aliments", 1.80, 10),
                new Producte(19, "AMD Ryzen 5 5600 3.5GHz Box", "Procesadors", 212.98, 21)
        );
    }

    public static boolean comensaPerVocal(String s) {
        return "AEIOUaeiou".indexOf(s.charAt(0)) != -1;
    }

}
