# Architecture Microservices : Services Utilisateur, Commande et Passerelle API

## Aperçu

Ce projet met en œuvre une architecture microservices basée sur Spring Boot, conçue pour répondre aux défis courants des systèmes distribués. L'architecture propose une approche modulaire et évolutive pour gérer les comptes utilisateurs, traiter les commandes et sécuriser la communication via une passerelle API. En isolant les fonctionnalités en services distincts, cette architecture garantit la maintenabilité, la scalabilité et une intégration facile avec d'autres systèmes.

---

## Fonctionnalités

### Service Utilisateur

- **Gestion des utilisateurs** :
  - Enregistrer de nouveaux utilisateurs.
  - Authentifier les utilisateurs et générer des jetons JWT.
  - Récupérer les détails des utilisateurs par ID.
  - Mettre à jour et supprimer les profils utilisateurs.
- **Validation** :
  - Imposer des contraintes sur les champs obligatoires comme `username` et `password`.

### Service Commande

- **Gestion des commandes** :
  - Créer de nouvelles commandes.
  - Récupérer les commandes par ID ou par utilisateur.
  - Mettre à jour et supprimer les commandes.
- **Validation** :
  - Vérifier que les champs requis comme `userId`, `product` et `quantity` sont fournis.

### Passerelle API

- **Routage** :
  - Diriger les requêtes API vers les microservices appropriés.
- **Sécurité** :
  - Sécuriser les points de terminaison avec l'authentification JWT.
  - Fournir un point d'accès unifié à l'application.
- **Simplification de l'accès** :
  - Exposer les API utilisateur et commande via un point d'entrée unique.

---

## Technologies Utilisées

- **Framework Backend** : Spring Boot 3.4
- **Base de Données** : H2 Database
- **ORM** : Hibernate
- **Gestion des Dépendances** : Maven
- **Passerelle API** : Spring Cloud Gateway
- **Sécurité** : Spring Security avec JWT
- **Tests** : Postman

---

## Prérequis

- Java 17 ou supérieur
- Maven 3.8+

---

## Instructions d'installation et d'exécution

### Construire le projet

```bash
$ mvn clean install
```

### Lancer les services

#### Service Utilisateur

```bash
$ cd user-service
$ mvn spring-boot:run
```

Le user-service démarre sur `http://localhost:8081`.

Le order-service démarre sur `http://localhost:8082`.

La Passerelle API démarre sur `http://localhost:8085`.

---

## Points de terminaison API

### Service Utilisateur

- **Enregistrer un utilisateur** : `POST /users/register`
  - Corps de la requête :
    ```json
    {
      "username": "sampleuser",
      "password": "securepassword"
    }
    ```
- **Authentifier un utilisateur** : `POST /users/login`
  - Corps de la requête :
    ```json
    {
      "username": "sampleuser",
      "password": "securepassword"
    }
    ```
  - Réponse :
    ```json
    {
      "token": "<JWT Token>"
    }
    ```

### Service Commande

- **Créer une commande** : `POST /orders`
  - En-têtes : `Authorization: Bearer <JWT Token>`
  - Corps de la requête :
    ```json
    {
      "userId": 1,
      "product": "Laptop",
      "quantity": 1
    }
    ```
- **Récupérer les commandes par utilisateur** : `GET /orders/user/{userId}`
  - En-têtes : `Authorization: Bearer <JWT Token>`

### Passerelle API

- **Routage** :
  - Service Utilisateur : `/users/**`
  - Service Commande : `/orders/**`
- **Authentification** :
  - Nécessite des jetons JWT pour un accès sécurisé.

---

## Schéma de Base de Données

### Table Utilisateur

| Colonne     | Type         | Contraintes       |
| ----------- | ------------ | ----------------- |
| `id`        | BIGINT       | Clé Primaire      |
| `username`  | VARCHAR(255) | Non Null, Unique  |
| `password`  | VARCHAR(255) | Non Null          |

### Table Commande

| Colonne     | Type         | Contraintes       |
| ----------- | ------------ | ----------------- |
| `id`        | BIGINT       | Clé Primaire      |
| `user_id`   | BIGINT       | Non Null          |
| `product`   | VARCHAR(255) | Non Null          |
| `quantity`  | INT          | Non Null          |

---

## Gestion des Erreurs

- **400 Bad Request** : Déclenchée par une entrée invalide, comme des champs obligatoires manquants.
- **401 Unauthorized** : Déclenchée par des jetons JWT manquants ou invalides.
- **404 Not Found** : Déclenchée lorsque la ressource demandée n'existe pas.
- **500 Internal Server Error** : Déclenchée par des erreurs inattendues du serveur.

---
