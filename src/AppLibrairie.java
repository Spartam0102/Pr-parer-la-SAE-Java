public class AppLibrairie {
    private Entreprise entreprise;

    private boolean quitterApp = false;

    public AppLibrairie(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public void run() {
        bienvenue();
        while (!quitterApp) {
            menuPrincipal();
        }
        auRevoir();
    }

    public void menuPrincipal() {
        boolean menuActif = true;
        while (menuActif && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("|  Menu principal         |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| C: Connexion            |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menuActif = false;
            } else if (commande.equals("c")) {
                menuConnexion();
                menuActif = false; // revient au menu principal après connexion
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuConnexion() {
        boolean menu2 = false;
        while (!menu2 && !quitterApp) {
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
                menu2 = true;
            } else if (commande.equals("c")) {
                menuClient();
            } else if (commande.equals("v")) {
                menuVendeur();
            } else if (commande.equals("a")) {
                menuAdministrateur();
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuClient() {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Client                  |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| A: Afficher magasins    |");
            System.out.println("| P: Menu précédent       |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else if (commande.equals("a")) {
                System.out.println(entreprise.toString());
            } else if (commande.equals("p")) {
                menu3 = true;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuVendeur() {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Vendeur                 |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| A: Afficher magasins    |");
            System.out.println("| P: Menu précédent       |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else if (commande.equals("a")) {
                System.out.println(entreprise.toString());
            } else if (commande.equals("p")) {
                menu3 = true;
            } else {
                System.out.println("Commande invalide.");
            }
        }
    }

    public void menuAdministrateur() {
        boolean menu3 = false;
        while (!menu3 && !quitterApp) {
            System.out.println("+-------------------------+");
            System.out.println("| Administrateur          |");
            System.out.println("+-------------------------+");
            System.out.println("| Q: Quitter              |");
            System.out.println("| A: Afficher magasins    |");
            System.out.println("| J: ajpouter magasins    |");
            System.out.println("| P: Menu précédent       |");
            System.out.println("+-------------------------+");

            String commande = lireCommande();

            if (commande.equals("q")) {
                quitterApp = true;
                menu3 = true;
            } else if (commande.equals("a")) {
                System.out.println(entreprise.toString());
            } else if (commande.equals("j")) {
                System.out.print("nom > ");
                String nomR = System.console().readLine();
                System.out.print("ville > ");
                String villeR = System.console().readLine();
                System.out.print("id > ");
                int idR = Integer.parseInt(System.console().readLine());

                Magasin magasin = new Magasin(nomR, villeR,idR);
                entreprise.ajouterMagasin(magasin);
            } else if (commande.equals("p")) {
                menu3 = true;
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
        System.out.println("│ Au revoir, bonne route et bonne continuation !  │");
        System.out.println("╰─────────────────────────────────────────────────╯");
    }

    private String lireCommande() {
        System.out.print("Commande > ");
        String mess = System.console().readLine().strip().toLowerCase();
        return mess;
    }
}
