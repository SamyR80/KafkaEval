# prog_evenementiel
Evaluation du module programmation évènementiel

-------------------------------------------------

Projet : Samy BEAUDOIN et Clément BOULMONT

Pour démarrer le projet, il faut se rendre dans le dossier "répertoire" et taper les 3 commandes dans 3 terminaux différents :

Docker :  docker-compose -f .\zk-single-kafka-single.yml up
Prometheus :  ./prometheus --config.file=prometheus.yml
Kafka exporter :  ./kafka_exporter --kafka.server=127.0.0.1:9092

Création des deux topics :
docker exec -it kafka1 kafka-topics --create --topic Temperature_Celsius --bootstrap-server localhost:9092
docker exec -it kafka1 kafka-topics --create --topic Temperature_Fahrenheit --bootstrap-server localhost:9092


Enfin pour configuré grafana, se rendre sur l'url : localhost:3000/
Se connecté avec les identifiants : 
login : admin
password : admin

Ajouté une nouvelle datasource et choisir Prometheus (image1)
Dans la partie HTTP mettre l'url : http://localhost:9090 | et en Access : Browser (image2) et save
Ensuite créer une dashboard avec en paramètre "Prometheus" au lieu du "default" et saisir des noms de metrics souhaité (image3)
