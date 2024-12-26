# Ma Supérette du Net

![Java](https://img.shields.io/badge/Java-8-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14.5-blue)
![IntelliJ IDEA](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-orange)
![License](https://img.shields.io/badge/License-MIT-green)

## Description

Ce projet propose l'informatisation d'une supérette pour la gestion des fournisseurs, des achats, des stocks, des clients et des ventes. L'application permet au gérant de la supérette d'accéder à divers tableaux de bord et de gérer efficacement les opérations quotidiennes.

## Fonctionnalités

- **Gestion des fournisseurs** : Ajout, modification, suppression et accès aux informations des fournisseurs.
- **Gestion des produits** : Identification des produits par numéro unique, nom, description, catégorie et prix de vente.
- **Gestion des achats** : Enregistrement des lots de produits achetés avec quantité, date d'achat et date de péremption.
- **Gestion des ventes** : Détails des tickets de caisse incluant produit, lot, date de vente et prix.
- **Tableaux de bord** :
    - Résultats du jour et du mois : total des ventes, bénéfices, classement des meilleures ventes.
    - Commandes à effectuer : validation, report, modification de quantité, choix du fournisseur.
    - Prix d'achat moyen des produits.
    - Liste des lots arrivant à échéance.

## Structure de la base de données

La base de données est modélisée en PostgreSQL et comprend les tables suivantes :
- `fournisseurs`
- `produits`
- `achats`
- `ventes`
- `contrats`
- `contacts_associes`

## Installation

1. Clonez le dépôt :
   ```bash
   git clone https://github.com/Tyziryx/Ams-JavaBdd.git
   cd Ams-JavaBdd
   ```
2. Configurez la base de données PostgreSQL :  

- Créez une base de données nommée `superette`.
- Importez le schéma de la base de données depuis le fichier `schema.sql`.
- Configurez les paramètres de connexion à la base de données dans le fichier `src/main/resources/application.properties`.  
- Compilez et exécutez l'application

# Utilisation
Lancez l'application et accédez à l'interface utilisateur pour gérer les fournisseurs, les produits, les achats et les ventes. Utilisez les tableaux de bord pour obtenir des informations détaillées sur les opérations de la supérette.

# License
Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.