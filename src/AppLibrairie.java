import java.util.Scanner;

public class apptest {

    private boolean quitter;

    public apptest() {
        this.quitter = false;
    }

    public void run() {
        bienvenue();
        while (!quitter) {
            menu();
        }
        auRevoir();
    }

    public void menu() {
        boolean passerSuivant = false;
        while (!passerSuivant) {
            System.out.println("+-------------------------+");
            System.out.println("|  Menu principal         |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| C: Connexion            |");
            System.out.println("+-------------------------+");
            String commande = lireCommande();

            if (commande.equals("q")) {
                quitter = true;
                passerSuivant = true;
            } else if (commande.equals("c")) {
                connexion();
                passerSuivant = true;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void connexion() {
        boolean passerSuivant = false;
        while (!passerSuivant && !quitter) {
            System.out.println("+-------------------------+");
            System.out.println("|  Connexion              |");
            System.out.println("+-------------------------+");
            System.out.println("| R: Retour               |");
            System.out.println("| C: Client               |");
            System.out.println("| V: Vendeur              |");
            System.out.println("| A: Administrateur       |");
            System.out.println("+-------------------------+");
            String commande = lireCommande();

            if (commande.equals("r")) {
                passerSuivant = true;
            } else if (commande.equals("c")) {
                client();
            } else if (commande.equals("v")) {
                vendeur();
            } else if (commande.equals("a")) {
                administrateur();
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void client() {
        menuRole("Client");
    }

    public void vendeur() {
        menuRole("Vendeur");
    }

    public void administrateur() {
        menuRole("Administrateur");
    }

    public void menuRole(String role) {
        boolean commandeFaite = false;
        while (!commandeFaite && !quitter) {
            System.out.println("+-------------------------+");
            System.out.println("|  " + role);
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| P: Menu principal       |");
            System.out.println("+-------------------------+");
            String commande = lireCommande();

            if (commande.equals("q")) {
                quitter = true;
                commandeFaite = true;
            } else if (commande.equals("p")) {
                commandeFaite = true; // retour au menu principal
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void bienvenue() {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Bienvenue ! En week-end comme dans la semaine, les bons comptes font les bons amis.│");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    public void auRevoir() {
        System.out.println("╭─────────────────────────────────────────────────╮");
        System.out.println("│ Au revoir, bonne route et bonne continuation ! │");
        System.out.println("╰─────────────────────────────────────────────────╯");
    }

    private String lireCommande() {
        System.out.print("Commande > ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input.strip().toLowerCase();
    }

    public static void main(String[] args) {
        new apptest().run();
    }
}
