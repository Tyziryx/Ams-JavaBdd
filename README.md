# Ma Supérette du Net

![Java](https://img.shields.io/badge/Java-8-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14.5-blue)
![IntelliJ IDEA](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-orange)
![License](https://img.shields.io/badge/License-MIT-green)

## Description

Ce projet propose l'informatisation d'une supérette pour la gestion des fournisseurs, des achats, des stocks, des clients et des ventes. L'application permet au gérant de la supérette d'accéder à divers tableaux de bord et de gérer efficacement les opérations quotidiennes.

## Fonctionnalités

- **Tableaux de bord** :
    - Affichage du top 10 des produits vendu du jour, et par mois dans deux graphiques camembert.
    - Affichage des résultats du jour et du mois sous forme d'un graphique en barre. en affichant les bénéfices, les couts et le CA sur les produits.
- **Gestion des fournisseurs** : 
    - Affichage des fournisseurs, il faut double cliquer sur une ligne pour afficher les détails et les contacts associés. Il est aussi possible d'ajouter, modifier, supprimer des fournisseurs avec un clique droit.
    - Il y a aussi un affichage pour contrats avec la possibilité d'ajouter, modifier, et supprimer des contrats facilement avec un clique droit.
- **Gestion des commandes** : 
    - Affichage des produits en quantité limité, c'est a dire les produits ayant moins de 10 en stock. On peut les recommender en double cliquant dessus et ça affiche un formulaire de commande. 
    - Il y a aussi un affichage des produits disponibles chez les différents fournisseurs. On peut les commander en double cliquant dessus.
- **Gestion des ventes** : 
    - Affichage de toutes les ventes passées dans le magasin.
- **Gestion des stock** : 
    - Affichage des du stock du magasin. Pour cela on récupère tous les lots de produits en on ne garde que les lots ayant une quantité supérieur à 0. 
    - Il y a aussi l'affichage des produits périmés, on prend donc tous les produits du stock dont la date de peremption est inferieur a la date du jour.

## Structure de la base de données

La base de données est modélisée en PostgreSQL et comprend les tables suivantes :
- `commande_a_effectuer`:
  - `id_commande`: `UUID`
  - `id_produit`: `int`
- `contact_associe`:
  - `siret`: `int`
  - `nom`: `varchar`
  - `prenom`: `varchar`
  - `fonction`: `varchar`
  - `email`: `varchar`
  - `telephone`: `varchar`
- `contrat`:
  - `id_produit`: `int`
  - `quantite_min`: `int`
  - `date_debut`: `date`
  - `date_fin`: `date`
  - `prix_produit`: `float`
  - `id_fournisseur`: `int`
  - `id_contrat`: `UUID`
- `fournisseur`:
  - `nom_societe`: `varchar`
  - `siret`: `int`
  - `adresse`: `varchar`
  - `email`: `varchar`
- `lot_de_produit`:
  - `id_produit`: `int`
  - `quantite`: `int`
  - `date_achat`: `date`
  - `date_peremption`: `date`
  - `prix_achat`: `float`
  - `prix_unitaire`: `float`
  - `id_fournisseur`: `int`
  - `id_lot_de_produit`: `UUID`
- `prix_fournisseur`:
  - `id_fournisseur`: `int`
  - `id_produit`: `int`
  - `prix`: `float`
- `produit`:
  - `id_produit`: `int`
  - `nom`: `varchar`
  - `description`: `varchar`
  - `categorie`: `varchar`
- `vente`:
  - `numero_ticket`: `int`
  - `id_produit`: `int`
  - `date_vente`: `date`
  - `prix_unite`: `int`
  - `quantite`: `int`
  - `id_lot_de_produit`: `UUID`

## Installation

1. Clonez le dépôt :
   ```bash
   git clone https://github.com/Tyziryx/Ams-JavaBdd.git
   cd Ams-JavaBdd
   ```
2. Configurez la base de données PostgreSQL :  

- Créez une base de données nommée `superette`.
- Importez le schéma de la base de données depuis le fichier `schema.sql`.
- Executer la commande: `CREATE EXTENSION IF NOT EXISTS "uuid-ossp";` pour activer l'extension uuid et pouvoir générer des UUID automatiquement.
- Configurez les paramètres de connexion à la base de données dans le fichier `/resources/config.properties`.  
- Mettre le dossier ressource en tant que fichier de ressource dans IntelliJ IDEA. (ressource root)
- Compilez et exécutez l'application

# Utilisation
Lancez l'application et accédez à l'interface utilisateur pour gérer les fournisseurs, les produits, les commandes et les ventes. Utilisez les tableaux de bord pour obtenir des informations détaillées sur les opérations de la supérette.

# License
Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.