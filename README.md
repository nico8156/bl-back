# Blog de Livres

## Presentation globale:
Ce projet regroupe deux parties : FRONT ET BACK 
### FRONT
Le Front est développé sous angular 15

Les bibiothèques utilisées sont : [ngrx](https://ngrx.io/) et [rxjs](https://rxjs.dev/guide/operators)
Le projet est structuré en pages, chaque dossier de src/app représente une page navigable. Le dossier shared comprend tous les composants utilisables par plusieurs pages.
L'API utilisée pour collecter les données est l'api de [googleBooks](https://developers.google.com/books?hl=fr)

La partie front exploite de multiples fonctionnalités propres a angular:
- lazy loading pour charger les blocs de code au fur et a mesure de leur utilité.
- forms module pour gérer les saisies de l'utilisateur et fournir à l'utilisateur des informations relatives à ses saisies.
- AuthGuard pour conditionner la navigation de l'utilisateur après son identification.
- AuthInterceptor pour éxécuter les requêtes qui nécessitent un JWT.
- Mise en place de custom pipes pour personnaliser l'affichage des données.
- angular router pour une navigation simple et efficace.
- RxJs pour traiter les observables et gagner en réactivité dans l'interface utilisateur.
- NgRx afin de gérer les state de l'application et l'asynchrone lors des requêtes http.
- Jasmine et Karma pour l'ensemble des tests unitaires (71 tests pour cette application).

### BACK
Le Back est développé en Java 21 grâce au framework spring boot 3.2.1


### Déploiement

Pour utiliser ce code : 
- executer la commande suivante dans votre terminal: kubectl apply -f k8s
- Possibilité de lancer le back en éxécutant :
  1. kubectl apply -f bl-mysql-deployment.yml
  2. kubectl apply -f bl-api-deployment.yml
- ... et de cloner le front : 
