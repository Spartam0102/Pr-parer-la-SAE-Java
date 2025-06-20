DROP TABLE IF EXISTS PANIER;
DROP TABLE IF EXISTS DETAILCOMMANDE;
DROP TABLE IF EXISTS THEMES;
DROP TABLE IF EXISTS POSSEDER;
DROP TABLE IF EXISTS EDITER;
DROP TABLE IF EXISTS ECRIRE;
DROP TABLE IF EXISTS COMMANDE;
DROP TABLE IF EXISTS VENDEUR;
DROP TABLE IF EXISTS LIVRE;
DROP TABLE IF EXISTS EDITEUR;
DROP TABLE IF EXISTS AUTEUR;
DROP TABLE IF EXISTS CLIENT;
DROP TABLE IF EXISTS VENDEUR;
DROP TABLE IF EXISTS GERER;
DROP TABLE IF EXISTS ADMINISTRATEUR;
DROP TABLE IF EXISTS MAGASIN;
DROP TABLE IF EXISTS CLASSIFICATION;

CREATE TABLE AUTEUR (
  PRIMARY KEY (idauteur),
  idauteur   varchar(11) NOT NULL,
  nomauteur  varchar(100),
  anneenais  int,
  anneedeces int
);

CREATE TABLE CLASSIFICATION (
  PRIMARY KEY (iddewey),
  iddewey  varchar(3) NOT NULL,
  nomclass varchar(50)
);


CREATE TABLE COMMANDE (
  PRIMARY KEY (numcom),
  numcom  int NOT NULL,
  datecom date,
  enligne char(1),
  livraison char(1),
  idcli   int NOT NULL,
  idmag   INT NOT NULL
);

CREATE TABLE DETAILCOMMANDE (
  PRIMARY KEY (numcom, numlig),
  numcom    int NOT NULL,
  numlig    int NOT NULL,
  qte       int,
  prixvente decimal(6,2),
  isbn      varchar(13) NOT NULL
);

CREATE TABLE ECRIRE (
  PRIMARY KEY (isbn, idauteur),
  isbn     varchar(13) NOT NULL,
  idauteur varchar(11) NOT NULL
);

CREATE TABLE EDITER (
  PRIMARY KEY (isbn, idedit),
  isbn   varchar(13) NOT NULL,
  idedit int NOT NULL
);

CREATE TABLE EDITEUR (
  PRIMARY KEY (idedit),
  idedit  int NOT NULL,
  nomedit varchar(100)
);

CREATE TABLE LIVRE (
  PRIMARY KEY (isbn),
  isbn      varchar(13) NOT NULL,
  titre     varchar(200),
  nbpages   int,
  datepubli int,
  prix      decimal(6,2)
);

CREATE TABLE MAGASIN (
  PRIMARY KEY (idmag),
  idmag    int NOT NULL,
  nommag   VARCHAR(42),
  adressemag VARCHAR(200),
  telmag VARCHAR(20),
  note FLOAT
);

CREATE TABLE POSSEDER (
  PRIMARY KEY (idmag, isbn),
  idmag int NOT NULL,
  isbn  varchar(13) NOT NULL,
  qte   int
);

CREATE TABLE THEMES (
  PRIMARY KEY (isbn, iddewey),
  isbn    varchar(13) NOT NULL,
  iddewey varchar(3) NOT NULL
);
/*modification de tables*/
CREATE TABLE CLIENT (
  PRIMARY KEY (idcli),
  idcli      int NOT NULL,
  nomcli     varchar(50),
  prenomcli  varchar(30),
  adressecli varchar(100),
  codepostal varchar(5),
  villecli   varchar(100),
  mdpC varchar(50)
);

/*Ajout de tables*/

CREATE TABLE VENDEUR (          
    idVen INT PRIMARY KEY,
    nomVen VARCHAR(50),
    prenomVen VARCHAR(50),
    idmag int,
    mdpV varchar(50)
);

CREATE TABLE PANIER (
    idCli INT,
    isbn VARCHAR(13),
    quantite INT DEFAULT 1,
    PRIMARY KEY (idCli, isbn)
);

CREATE TABLE ADMINISTRATEUR (          
    idAdmin INT PRIMARY KEY,
    nomAdmin VARCHAR(50),
    prenomAdmin VARCHAR(50),
    mdpA varchar(50)
);


CREATE TABLE GERER (          
  PRIMARY KEY (idmag,idAdmin),
  idmag    INT NOT NULL,
  idAdmin INT NOT NULL


);


ALTER TABLE PANIER ADD FOREIGN KEY (idCli) REFERENCES CLIENT(idCli);
ALTER TABLE PANIER ADD FOREIGN KEY (isbn) REFERENCES LIVRE(isbn);
ALTER TABLE VENDEUR ADD FOREIGN KEY (idmag) REFERENCES MAGASIN(idmag);
ALTER TABLE GERER ADD FOREIGN KEY (idAdmin) REFERENCES ADMINISTRATEUR(idAdmin);
ALTER TABLE GERER ADD FOREIGN KEY (idmag) REFERENCES MAGASIN(idmag);


ALTER TABLE COMMANDE ADD FOREIGN KEY (idmag) REFERENCES MAGASIN (idmag);
ALTER TABLE COMMANDE ADD FOREIGN KEY (idcli) REFERENCES CLIENT (idcli);

ALTER TABLE DETAILCOMMANDE ADD FOREIGN KEY (isbn) REFERENCES LIVRE (isbn);
ALTER TABLE DETAILCOMMANDE ADD FOREIGN KEY (numcom) REFERENCES COMMANDE (numcom);

ALTER TABLE ECRIRE ADD FOREIGN KEY (idauteur) REFERENCES AUTEUR (idauteur);
ALTER TABLE ECRIRE ADD FOREIGN KEY (isbn) REFERENCES LIVRE (isbn);

ALTER TABLE EDITER ADD FOREIGN KEY (idedit) REFERENCES EDITEUR (idedit);
ALTER TABLE EDITER ADD FOREIGN KEY (isbn) REFERENCES LIVRE (isbn);

ALTER TABLE POSSEDER ADD FOREIGN KEY (isbn) REFERENCES LIVRE (isbn);
ALTER TABLE POSSEDER ADD FOREIGN KEY (idmag) REFERENCES MAGASIN (idmag);

ALTER TABLE THEMES ADD FOREIGN KEY (iddewey) REFERENCES CLASSIFICATION (iddewey);
ALTER TABLE THEMES ADD FOREIGN KEY (isbn) REFERENCES LIVRE (isbn);