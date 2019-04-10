# Projet de développement Android
Assane Bodiang / Ballé Traoré
Université Grenoble Alpes



## Description: 
Nous avons voulu réaliser une application d’affichage des données métro, avec en plus la météo du moment. 
Nous avons filtré les lignes et pris en compte les PROXIMOS, les CHRONOPOST et les trams. Le reste est facile à ajouter il faut juste enlever le filtre, nous avons fait ceci pour pouvoir facilement debugger, dans la suite on prévoit d’afficher les lignes selon leur type.
## Structure:
Nous avons une activité principale dans laquelle nous avons 4 fragments, une pour l’affichage des arrêts les plus proches. A terme on voulait afficher les itinéraires vers ces station, mais on n’a malheureusement pas eu le temps. 
Le 2ème fragment sert à afficher les différentes lignes possibles, ensuite après avoir sélectionné la ligne voulue on lance une activité qui affiche les 2 directions de la ligne. En cliquant sur un arrêt nous affichons un popup avec les prochains horaires de passage d’où on peut ajouter l’arrêt aux favoris. 
Le fragment pour afficher les informations du trafic et celui pour gérer les favoris et les informations du trafic n’ont pas été implémentés. 
Nous avons un Drawer Layout pour afficher la photo de profil et aller vers les activités tels que: “Paramètre”, “Contacter Nous” et “Profil”.  
## Technologies Utilisées
OpenStreetMap,
Picasso,
hdodenhof.
## Notes:
Les horaires de tram et de CHRONOPOST ont 2 heures de retard (donc nous avons fait un +2).
## Fonctionnalités non implémentées:
Liaison avec une base de donnée Firebase
Stockage des favoris
Connection 
Mode nuit et langue
