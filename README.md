# Blog de Livres

## Presentation globale:
Ce projet regroupe deux parties : FRONT ET BACK.
### FRONT
Le Front est développé sous angular 15.

Les bibliothèques utilisées sont : [ngrx](https://ngrx.io/) et [rxjs](https://rxjs.dev/guide/operators).
Le projet est structuré en pages, chaque dossier de src/app représente une page navigable. Le dossier "shared" comprend tous les composants utilisables par plusieurs pages.
L'API utilisée pour collecter les données est l'api [googleBooks](https://developers.google.com/books?hl=fr).

La partie front exploite de multiples fonctionnalités propres a angular:
- Lazy loading pour charger les blocs de code au fur et a mesure de leur utilité.
- Forms module pour gérer les saisies de l'utilisateur et fournir à l'utilisateur des informations relatives à ses saisies.
- AuthGuard pour conditionner la navigation de l'utilisateur après son identification.
- AuthInterceptor pour éxécuter les requêtes qui nécessitent un JWT en personnalisant ces requêtes avec le JWT.
- Mise en place de custom pipes pour personnaliser l'affichage des données.
- Angular router pour une navigation simple et efficace.
- RxJs pour traiter les observables et gagner en réactivité dans l'interface utilisateur.
- NgRx afin de gérer les state de l'application et l'asynchrone lors des requêtes http.
- Jasmine et Karma pour l'ensemble des tests unitaires (71 tests pour cette application).

### BACK:
Le Back est développé en Java 21 grâce au framework spring boot 3.2.1
Le projet a été initialisé avec:[springBoot initializer](https://start.spring.io/).
Le projet est structuré classiquement sous la forme : controller, entity, repository et service.
Les dependances sont ajoutées au format maven dans le fichier pom.xml.
Les principales dependances sont : 
- Spring data jpa pour faciliter les échanges aves la base de données;
- Spring security, pour mettre en place authentification, autorisation, protection contre certaines attaques comme CSRF, injection SQL;
- Lombok pour fournir les getter et setter notemment permettant ainsi un code plus propre et concis;
- MySQL connector pour connecter l'application Spring Boot à la base de données MYSQL;
- Framework de tests unitaires: Junit ; Mockito pour simuler les données.

### Déploiement:
Chaque partie a été dockerizée : d'un coté le front, de l'autre le back avec la base de données.
Ainsi vous trouverez un fichier docker dans le front mais aussi dans le back. Côté back, j'ai utilisé docker-compose pour synchroniser le back avec la base de données pendant le developpement.

Dans l'idée de deployer l'application sur AWS, j'ai utilisé Kubernetes pour préparer, orchestrer ces différentes entités: FRONT, BACK et BASE DE DONNEES(BDD).
Voici les étapes suivies pour préparer l'application dans kubernetes:
1. Créer les build (un build front, un build back) pour les intégrer dans une image Docker.
2. Push des images sur [DockerHub](https://hub.docker.com/repository/docker/nicobzh/backend-demo-app/general).
3. Rédaction d'un manifeste (formal yaml ou yml) pour créer le pod dans kubernetes qui contiendra chaque container.
4. Assurer la commnuication du back avec la BDD via un clusterIP(communication interne dans le master node), pour tester on utlise un nodePort(uniquement pour le developpement) qui fournit la communication vers l'extérieur (notre navigateur ici).
5. Rédaction de manifestes pour des deployments, permettant ainsi de générer autant de pods en fonction des besoins et de les relancer lors de crash.
6. Pour assurer la communication finale on relie front et back avec un Ingress: qui permettra en fonction de la requete l'aiguillage de cette requete soit vers le back (/api/...) soit vers le front (pour tout le reste). Chaque élément est exposé aux autres par un clusterIp et l'Ingress permet d'exposer la bonne requête au bon endroit (en fonction de l'adresse de requête).

### CI/CD:
Dépôt de la code base sur github, utilisation de githubActions pour l'intégration et le développement continu: 
Lors de chaque push sur la branche main, une nouvelle image est crée sous docker et pushée sur le hub.
Lors du déploiement sur AWS via un EKS(Elastic Kubernetes Service) on pourrait tout a fait actualiser le cluster grace à ce workflow(auquel il faudrait ajouter quelques lignes de codes pour finaliser le processus). 

### Usage:
Pour utiliser ce code : 
- Clonez le repo du back : dans votre terminal, passez la commande: git clone https://github.com/nico8156/bl-back.git
- Apres avoir cloné le repo github, executez la commande suivante dans votre terminal: kubectl apply -f k8s

---
  
- Possibilité de lancer le back en éxécutant :
  1. kubectl apply -f bl-mysql-deployment.yml
  2. kubectl apply -f bl-api-deployment.yml
- ... et de cloner le front avec : git clone https://github.com/nico8156/bl-front.git ; suivi de npm install et enfin ng serve pour lancer l'application.
- 
---

Une fois sur l'application, vous serez invités à vous enregistrer(en cliquant sur register).
Après la connexion, vous serez dirigés vers l'onglet de recherche.
Saisissez un titre de livre, un auteur... 
Cliquez sur INFO pour obtenir plus de renseignements et ajouter livre choisi à votre bibliothèque.
Cliquez ensuite sur l'onglet de cette même bibliothèque et constatez que votre livre est bien présent.
Cliquez sur ce livre est vous pourrez ajouter un commentaire.
Dans la partie droite de la barre d'onglets un bouton vous permet de créer une nouvelle bibliothèque.
Dans la barre de navigation, en cliquant sur votre username ou sur votre photo, vous pourrez éditer le nom des bibliothèques, modifier votre username ou encore ajouter un lien vers une photo plus personnelle.
À la fin de votre navigation, cliquez sur logout pour sortir de l'application et naviguer vers la page de login. Essayez de vous connecter à nouveau.

---
Ce code est le fruit d'un travail personnel et ne constitue en aucun cas une copie collée d'un autre dépôt
Il représente le travail pour servir une idée simple, un blog sur la lecture.
Je vous remercie pour cette lecture, très bonne navigation.
