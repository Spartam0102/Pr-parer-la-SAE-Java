// package TestApp;
// import App.*; 
// import BD.*; 
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;
// import java.util.List;
// import java.util.ArrayList;



// public class TestAdministrateur {

//     private Administrateur admin;
//     private Entreprise entreprise;
//     private Magasin magasin;
//     private Livre livre;

//     @BeforeEach
//     void setUp() {
//         admin = new Administrateur("NomAdmin", "PrenomAdmin", "1990-01-01", 1);
//         entreprise = new Entreprise("NomEntreprise", "AdresseEntreprise");
//         magasin = new Magasin("NomMagasin", "VilleMagasin", 101);
//         Auteur auteur = new Auteur("NomAuteur", "PrenomAuteur", "1980-05-05", 1);
//         Editeur editeur = new Editeur(1, "NomEditeur");
//         Classification classification = new Classification(123, "Fiction");
//         List<Classification> classifications = new ArrayList<>();
//         classifications.add(classification);
//         livre = new Livre(1, "TitreLivre", "2023-01-15", 25, 300, classifications, editeur, auteur);
//     }

//     @Test
//     void testAjouterLibrairie() {
//         admin.ajouterLibrairie(entreprise, magasin);
//         assertTrue(entreprise.getListeMagasins().contains(magasin), "Le magasin devrait être dans la liste des magasins de l'entreprise");
//     }

//     @Test
//     void testGererStockLivreExistant() {
//         magasin.ajouterLivre(List.of(livre), List.of(10));
//         admin.gérerStock(magasin, livre, 20);
//         assertEquals(20, magasin.stockLivre.get(magasin.getLivres().indexOf(livre)), "Le stock du livre devrait être mis à jour");
//     }

//     @Test
//     void testGererStockLivreNonExistant() {
//         admin.gérerStock(magasin, livre, 30);
//         assertFalse(magasin.getLivres().contains(livre), "Le livre ne devrait pas être ajouté au magasin");
//     }
// }