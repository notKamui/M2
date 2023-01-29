# Rapport de projet Stream Processing - Jimmy Teillard

## Introduction

Outre une légère bataille avec l'API de Kafka qui a plutôt très mal vieilli 
(elle laisse faire beaucoup de betises au lieu d'utiliser le type system, et cache des exceptions),
j'ai pu finir le TP à l'exception de la question 7.

## Setup

Ce projet utilise Kotlin avec Gradle en build system.
Il est conseillé d'utiliser IntelliJ IDEA pour le manipuler le plus facilement possible.

Si vous ouvrez ce projet dans IntelliJ, l'IDE vous proposera de charger la configuration Gradle.

Une fois fait, il faut lancer la task `generateAvroJava` pour générer la classe Prescription
qui est utilisée par le projet, à partir du fichier `src/main/avro/prescription.avsc`.

Si vous ne trouvez pas comment faire par l'IDE, 
vous pouvez utiliser la commande suivante à la racine du projet :

```bash
./gradlew generateAvroJava
```

Il faut aussi lancer 3 serveurs Kafka sur localhost:9092, localhost:9093 et localhost:9094,
ainsi que créer trois topics:
- `prescription` sur localhost:9092
- `prescription-dispatched` sur 3 partitions sur localhost:9092, localhost:9093 et localhost:9094
- `prescription-anonymized` sur localhost:9092

## Structure du projet

Le projet est composé de plusieurs fichiers, 
qui se trouvent dans le dossier `src/main/kotlin`,
dont certains servent de points d'entrée pour les différents producer/consumer.

- `prescription-utils.kt`: contient des fonctions utilitaires pour générer des prescriptions
  et sérialiser/désérialiser des objets `Prescription` en JSON ou en binaire Avro.
- `offset.kt`: contient simplement une fonction pour calculer une valeur avec une marge d'erreur
- `topics.kt`: contient les noms des topics utilisés dans le projet sous forme de constantes.
  En l'occurrence, il y en a 3
    - `prescription`
    - `prescription-dispatched` (qui est partitionné)
    - `prescription-anonymized`
- `producer.kt`: contient le code du producer, qui génère des prescriptions et les envoie sur le topic `prescription` au format Avro, sans clés.
- `dispatcher.kt`: contient le code du consumer qui lit les prescriptions sur le topic `prescription` 
  et les ré-écrit sur le topic `prescription-dispatched` avec une clé qui est le cip la prescription.
- `consumer-loop.kt`: contient le code du consumer qui lit les prescriptions sur le topic `prescription-dispatched` 
  sur toutes les partitions et les affiche.
- `processor.kt`: contient le code du consumer qui lit les prescriptions sur le topic `prescription` 
  et les ré-écrit sur le topic `prescription-anonymized` après les avoir anonymisées (avec un KafkaStream).
- `filter.kt`: contient le code du consumer qui lit les prescriptions sur le topic `prescription-anonymized` 
  et les affiche uniquement si elles ont un prix supérieur à 4 euros (avec un KafkaStream).

## Lancement

Pour lancer les différents main, il suffit d'ouvrir les fichiers correspondants dans IntelliJ,
et de cliquer directement sur la flèche verte à côté de la fonction `main` pour lancer le programme.

Pour rappel, les fichiers qui ont des main sont:
- `producer.kt`
- `dispatcher.kt`
- `consumer-loop.kt`
- `processor.kt`
- `filter.kt`
