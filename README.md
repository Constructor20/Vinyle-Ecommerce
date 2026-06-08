# Vinyl Store - E-commerce de Vinyles

Application desktop JavaFX pour la gestion et la vente de vinyles.

Projet réalisé en BTS SIO SLAM.

## Technologies utilisées

- **Java 21** (LTS)
- **JavaFX 21.0.9** (interface graphique)
- **MySQL** (base de données avec XAMPP)
- **Maven** (gestion des dépendances)
- **BCrypt** (sécurisation des mots de passe)

## Structure du projet

```
src/main/java/com/vinylstore/
├── Main.java                         # Point d'entrée de l'application
├── controllers/
│   ├── LoginController.java          # Connexion utilisateur
│   ├── RegisterController.java       # Inscription utilisateur
│   └── HomeController.java           # Page d'accueil après connexion
├── models/
│   └── User.java                     # Modèle Utilisateur
└── utils/
    ├── DatabaseConnection.java       # Connexion à MySQL
    └── UtilisateurDAO.java           # Requêtes SQL (CRUD)
src/main/resources/views/
├── login.fxml                        # Page de connexion
├── register.fxml                     # Page d'inscription
└── home.fxml                         # Page d'accueil
sql/
├── schema.sql                        # Structure de la base de données
└── sample_data.sql                   # Données d'exemple
```

## Fonctionnalités réalisées

- ✅ Inscription avec validation (email unique, mots de passe identiques, BCrypt)
- ✅ Connexion sécurisée (vérification email + mot de passe hashé)
- ✅ Déconnexion
- ✅ Page d'accueil personnalisée (affichage du nom de l'utilisateur)

## Installation

### 1. Prérequis

- Java 21 installé
- XAMPP avec MySQL activé
- Maven (ou utiliser le wrapper `mvnw` fourni)

### 2. Base de données

1. Lancer XAMPP → démarrer MySQL
2. Dans phpMyAdmin, exécuter le fichier `sql/schema.sql`
3. (Optionnel) Exécuter `sql/sample_data.sql` pour des données d'exemple

### 3. Configuration

Vérifier les infos de connexion dans `DatabaseConnection.java` :
```java
URL = "jdbc:mysql://localhost:3306/vinyl_store";
USER = "root";
PASSWORD = ""; // mot de passe XAMPP (vide par défaut)
```

### 4. Lancer l'application

```bash
mvn javafx:run
```

Ou avec le wrapper Maven (sans avoir Maven installé) :
```bash
./mvnw javafx:run
```
