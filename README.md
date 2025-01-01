# Ma Supérette du Net

![Java](https://img.shields.io/badge/Java-8-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14.5-blue)
![IntelliJ IDEA](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-orange)
![License](https://img.shields.io/badge/License-MIT-green)

## Description
Tout d'abord, il est conseillé de lire ce README sur GitHub, puisque le rendu sur GitHub est plus lisible que sur un éditeur de texte. ensuite, si vous avez des problèmes de compilation, veuillez consulter les sections ci-dessous.

Ce projet propose l'informatisation d'une supérette pour la gestion des fournisseurs, des achats, des stocks, des clients et des ventes. L'application permet au gérant de la supérette d'accéder à divers tableaux de bord et de gérer efficacement les opérations quotidiennes.

## Fonctionnalités

- **Tableaux de bord** :
    - Affichage du top 10 des produits vendu du jour, et par mois dans deux graphiques camembert.
    - Affichage des résultats du jour et du mois sous forme d'un graphique en barre. en affichant les bénéfices, les couts et le CA sur les produits.
- **Gestion des fournisseurs** : 
    - Affichage des fournisseurs, il faut double cliquer sur une ligne pour afficher les détails et les contacts associés. Il est aussi possible d'ajouter, modifier, supprimer des fournisseurs avec un clique droit, et idem pour les contacts associés.
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

- Créez une base de données nommée `superette` (nom au choix).
- Importez le schéma de la base de données depuis le fichier `schema.sql` et toutes les tables dans le dossier `/sql`.
- Configurez les paramètres de connexion à la base de données dans le fichier `/resources/config.properties`. 
- Si vous avez des problèmes de compilation, veuillez consulter les sections ci-dessous.

3. Compilez le projet soit avec Eclipse ou IntelliJ IDEA :
- Ouvrez le projet dans votre IDE
- Vérifiez bien que le projet est configuré avec le JDK 8.
- Puis compilez le projet.
- Si vous avez des problèmes de compilation, veuillez consulter les sections ci-dessous. (il est conseillé de compiler avec eclipse)

# Problèmes de compilation avec Eclipse
1. Premier réflèxe:
- Faire un f5 sur le projet pour rafraichir les dépendances.
2. Si vous avez d'autres problèmes avec éclipse, le problème vient peut être du jdk:
- Clique droit sur le projet, `Properties>Java Build Path>Libraries>Modifier le JRE System Library>Choisir le JDK 8`.
- Toujours dans les properties, `Java Compiler>Compiler compliance level>Choisir 1.8`.
3. Si vous avez un problème lié au fichier config:
- Vérifiez d'avoir bien rempli les champs du fichier config.properties.
- Sinon, il faut s'assurer que le dossier ressource est bien défini en tant que dossier de ressource. Pour cela, clic droit sur le dossier ressource>Build Path>Use as Source Folder.
4. Si vous avez un problème lié à la classe "`org.postgresql.Driver`": 
- C'est que la librairie n'est pas bien importée, pour cela, clic droit sur le projet `Build Path>Configure Build Path>Libraries>Classpath>Add External JARs`, puis Choisir le fichier postgresql-42.2.23.jar dans le dossier lib.

# Problèmes de compilation avec IntelliJ IDEA
1. Si vous avez des problèmes avec IntelliJ IDEA, le problème vient peut être du jdk:
- Vous pouvez essayer de modifier le jdk`File>Project Structure>Project>Project SDK>Choisir le JDK 8`.
- Toujours dans les properties, `File>Project Structure>Project>Project language level>Choisir SDK Default`.
2. Si vous avez un problème lié au fichier config:
- Vérifiez d'avoir bien rempli les champs du fichier config.properties.
- Sinon, il faut s'assurer que le dossier ressource est bien défini en tant que dossier de ressource. Pour cela, clic droit sur le dossier ressource>Mark Directory as>Resources Root.
3. Si vous avez un problème lié à la classe "`org.postgresql.Driver`":
- C'est que la librairie n'est pas bien importée, pour cela, clic droit sur le projet `Open Module Settings>Libraries>+>Java`, puis choisir le fichier postgresql-42.2.23.jar dans le dossier lib.
4. Si vous avez des problèmes de build
- Vérifiez que le build output est bien défini.
# Utilisation
Lancez l'application et accédez à l'interface utilisateur pour gérer les fournisseurs, les produits, les commandes et les ventes. Utilisez les tableaux de bord pour obtenir des informations détaillées sur les opérations de la supérette. 
Les tableaux qui ont un hover, sont double cliquable pour afficher plus d'informations, ou pour les modifier rapidement.
Les tableaux qui ont un clique droit, sont modifiables, vous pouvez ajouter, modifier, ou supprimer des éléments.

L'exécutable .jar se trouve dans le dossier `build`

Si vous avez de problèmes de compilation veuillez consulter les sections ci-dessus.

# Images:
![image](https://tidic.fr/res/ams/ams-img0.png)
![image](https://tidic.fr/res/ams/ams-img1.png)
![image](https://tidic.fr/res/ams/ams-img2.png)
![image](https://tidic.fr/res/ams/ams-img3.png)
![image](https://tidic.fr/res/ams/ams-img4.png)


# License
Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.