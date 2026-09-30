package com.example.ui.party

enum class TruthOrDareType {
    ACTION,
    VERITE
}

data class ActionVeriteItem(
    val type: TruthOrDareType,
    val text: String,
    val category: String = "Ambiance"
)

data class RouletteRule(
    val title: String,
    val instruction: String,
    val type: String, // "BOIRE", "DISTRIBUER", "BONUS", "DÉFI"
    val colorHex: String = "#D4AF37"
)

object PartyGamesData {

    val actionsBarList: List<String> = listOf(
        // 1-2 (Exemples demandés)
        "Mélange un fond de ton verre avec celui de ton voisin de droite et bois-le.",
        "Mime la pose de victoire d'un perso de jeu vidéo, les autres doivent deviner.",

        // 3-15 (Défis verres & endurance)
        "Fais un duel de regards avec ton voisin d'en face sans ciller : le premier qui rit ou cligne boit 2 gorgées.",
        "Bois 2 gorgées de ton verre les mains dans le dos sans renverser.",
        "Fais semblant d'être un barman ultra prétentieux et prends la commande de tout le monde avec un accent snob.",
        "Laisse ton voisin de gauche composer un mini-cocktail mystère dans une cuillère et avale-le cul-sec.",
        "Mime un animal qui a trop bu en train de rentrer chez lui, le groupe doit deviner lequel.",
        "Fais un toast vibrant et solennel de 30 secondes en l'honneur de la personne avec le verre le plus vide.",
        "Garde un glaçon dans ta main fermée jusqu'à ce que la moitié ait fondu, ou bois 3 gorgées.",
        "Prends un selfie mémorable avec tout le groupe et publie-le en story ou envoie-le à ton meilleur pote.",
        "Chante le refrain de ta chanson de soirée préférée avec une voix d'opéra dramatique.",
        "Fais 10 pompes d'affilée ou distribue 3 gorgées réparties entre deux joueurs.",
        "Échange de place avec le joueur de ton choix et bois une gorgée dans son verre.",

        // 16-30 (Mimes & gages physiques drôles)
        "Parle en rimes sur chaque phrase pendant les deux prochains tours.",
        "Fais un concours de bras de fer avec la personne assise à ta gauche : le perdant boit 2 gorgées.",
        "Mime l'animation d'une emote célèbre de Fortnite ou d'un jeu vidéo.",
        "Laisse la personne à ta droite te recoiffer ou te coiffer n'importe comment pour les 3 prochains tours.",
        "Prends une gorgée de ton verre en gardant un contact visuel intense avec quelqu'un sans cligner.",
        "Fais une imitation d'un commentateur de football ou d'esport qui commente la soirée.",
        "Essaie de jongler avec 2 sous-bocks ou deux glaçons sans les faire tomber par terre.",
        "Dis l'alphabet à l'envers à partir de la lettre Z jusqu'à T sans hésiter, sinon bois 2 gorgées.",
        "Prends l'accent de ton choix (québécois, marseillais, britannique...) jusqu'à ton prochain tour.",
        "Fais un check ultra élaboré et synchronisé avec ton voisin de droite.",
        "Laisse quelqu'un envoyer un emoji '🍹' au 4e contact de tes messages récents sans explication.",
        "Fais tourner un sous-bock sur ton doigt ou sur la table pendant 5 secondes.",
        "Imite la démarche d'un mec qui sort de boîte à 5h du matin et cherche ses clés.",
        "Fais semblant d'être le garde du corps personnel du joueur à ta gauche pendant 2 tours.",
        "Mime l'action de secouer un shaker avec une intensité maximale de champion du monde.",

        // 31-50 (Interactions table, mini-jeux & barmans)
        "Propose un pierre-feuille-ciseaux avec chaque joueur de la table : chaque défaite = 1 gorgée.",
        "Raconte une blague nulle en gardant un visage totalement neutre : si personne ne rit, tu bois 2 gorgées.",
        "Fais un moonwalk ou 3 pas de danse au milieu de la pièce.",
        "Fais deviner un film ou une série culte uniquement par le mime en moins de 30 secondes.",
        "Bois une gorgée en tenant ton verre uniquement avec tes pouces.",
        "Invente immédiatement un slogan publicitaire ridicule pour la boisson que tu as dans la main.",
        "Chuchote à l'oreille de ton voisin de gauche ce que tu penses vraiment de son outfit ce soir.",
        "Fais 15 squats d'affilée en tenant ton verre sans en renverser une seule goutte.",
        "Laisse le groupe décider d'un surnom débile que tout le monde devra utiliser pour toi ce soir.",
        "Mime un serveur maladroit qui renverse un plateau de shots imaginaire.",
        "Pointe du doigt sans hésiter la personne la plus susceptible de finir endormie avant 2h.",
        "Tire la langue et essaie de toucher ton nez, si tu échoues bois 1 gorgée.",
        "Fais un massage d'épaules express de 15 secondes à la personne à ta gauche.",
        "Mets tes chaussettes sur tes mains pendant les 2 prochains tours du jeu.",
        "Prends la pose pour une fausse couverture de magazine de mode et reste immobile 10 secondes.",
        "Fais semblant d'être un sommelier snob et analyse l'odeur du verre de ton voisin avec des mots pompeux.",
        "Cite 5 marques de bière ou d'alcool en moins de 5 secondes chrono ou bois 2 gorgées.",
        "Mime une scène d'action au ralenti digne de Matrix avec la personne en face de toi.",
        "Laisse quelqu'un te dessiner une mini moustache au doigt ou au stylo lavable.",
        "Tente de tenir en équilibre sur un pied les yeux fermés pendant 15 secondes.",
        "Bois une gorgée synchronisée 'bras croisés' à la russe avec ton voisin de droite.",
        "Désigne deux joueurs qui doivent immédiatement trinquer et boire 2 gorgées ensemble."
    )

    val veritesBarList: List<String> = listOf(
        // 1-2 (Exemples demandés)
        "Quel est le cocktail le plus honteux ou sucré que tu adores secrètement ?",
        "Si tu devais virer quelqu'un de cette soirée, ce serait qui ?",

        // 3-15 (Alcool, gaffes & soirées arrosées)
        "Quelle est la pire gaffe ou maladresse que tu aies commise sous l'effet de l'alcool ?",
        "Quel est le pire message ou vocal envoyé en fin de soirée que tu regrettes encore ?",
        "As-tu déjà fait semblant de boire un shot pour le recracher ou le verser discrètement dans une plante ?",
        "Quel joueur autour de cette table embrasserais-tu sur un coup de tête si tu y étais obligé ?",
        "Quel est ton plus gros mensonge pour esquiver une soirée ou un date Tinder ?",
        "Quel est le montant le plus déraisonnable que tu aies claqué en un seul soir de fête ?",
        "Quelle est la personne ici présente dont le style vestimentaire te laisse le plus perplexe ?",
        "As-tu déjà fouillé dans le téléphone de ton crush ou de ton partenaire en douce ?",
        "Quel est le surnom le plus ridicule ou gênant qu'on t'ait jamais donné ?",
        "Quelle est la chose la plus illégale ou limite que tu aies faite pendant une soirée étudiante ?",
        "Si tu devais échanger ta vie pour 24 heures avec quelqu'un autour de cette table, qui choisirais-tu ?",

        // 16-30 (Secrets de bar, crushs & réputations)
        "Quelle est la rumeur la plus folle ou totalement fausse qui a circulé sur toi ?",
        "As-tu déjà flashé ou eu un crush secret sur le mec ou la meuf d'un ami ?",
        "Quel est le pire rencard de bar que tu aies vécu dans ta vie ?",
        "Quelle est la pire excuse que tu aies sortie le lendemain pour justifier ton comportement de la veille ?",
        "Qui dans cette pièce ferait selon toi le pire colocataire au monde et pourquoi ?",
        "Quel est le secret inavouable que personne parmi tes proches actuels ne connaît ?",
        "As-tu déjà simulé d'être sobre face à un vigile ou à tes parents alors que tu voyais double ?",
        "Quelle chanson kitsch ou ringarde connais-tu par cœur au premier degré sans honte ?",
        "Quel joueur ici présent a selon toi le taux de séduction le plus élevé en soirée ?",
        "As-tu déjà ghosté quelqu'un le lendemain d'une soirée sans jamais donner de nouvelles ?",
        "Quel est le cocktail ou l'alcool qui t'a filé la pire gueule de bois de ton existence ?",
        "Quelle est la dispute la plus ridicule que tu aies eue pour une histoire de verre ou d'addition ?",
        "As-tu déjà dragué un serveur, une serveuse ou un barman juste pour avoir une réduc ou un shot offert ?",
        "Quelle est la chose la plus ridicule que tu aies achetée sur internet en pleine nuit sans t'en rappeler ?",
        "Si tu devais choisir une personne dans cette pièce pour survivre avec toi à une apocalypse zombie, qui ce serait ?",

        // 31-50 (Dilemmes sans filtre & confessions intimes)
        "As-tu déjà menti sur ton âge, ton job ou tes études pour faire le mec/la meuf mystérieux en soirée ?",
        "Quelle est la pire chose que tu aies dite sur quelqu'un présent ici par derrière ?",
        "Quel est ton pire souvenir lié à un karaoké en public ?",
        "As-tu déjà prétendu aimer un truc (anime, musique, sport) juste pour plaire à ton crush ?",
        "Quelle est la plus grosse somme d'argent que tu aies perdue ou prêtée sans jamais la revoir ?",
        "Quelle est la pire habitude que tu as quand tu es chez toi tout seul ?",
        "As-tu déjà envoyé un message compromettant à la mauvaise personne par inattention ?",
        "Si tu devais te faire tatouer le prénom de quelqu'un assis à cette table, ce serait qui ?",
        "Quel est le mensonge le plus culotté que tu aies sorti à un prof ou à un patron ?",
        "Quelle est ta plus grande insécurité physique ou de personnalité en soirée ?",
        "As-tu déjà pleuré en soirée pour une raison complètement absurde ?",
        "Quel joueur autour de cette table a selon toi le comportement le plus insupportable quand il a bu ?",
        "Quelle est la pire photo de toi qui existe sur les réseaux sociaux ?",
        "As-tu déjà mangé de la nourriture tombée par terre en soirée parce que tu avais trop faim ?",
        "Si tu devais te marier demain avec l'un des joueurs présents, lequel choisirais-tu sans hésiter ?",
        "Quel est le plat ou la boisson 'bizarre' que tu aimes manger en rentrant de boîte à 5h du mat ?",
        "Quelle est la personne dont tu as le plus honte d'avoir été amoureux(se) dans ton passé ?",
        "As-tu déjà fait semblant de ne pas avoir d'argent pour éviter de payer une tournée ?",
        "Quelle est la pire chose que tes parents aient découverte sur tes sorties nocturnes ?",
        "Si ton historique internet des dernières 48h était projeté sur un grand écran maintenant, tu serais à quel point gêné ?",
        "Quel est le pire cadeau qu'on t'ait jamais offert et que tu as fait semblant d'adorer ?",
        "Qui dans cette pièce a le potentiel d'avoir la vie la plus chaotique dans 10 ans selon toi ?"
    )

    val actionVeriteList: List<ActionVeriteItem> = veritesBarList.map {
        ActionVeriteItem(TruthOrDareType.VERITE, it)
    } + actionsBarList.map {
        ActionVeriteItem(TruthOrDareType.ACTION, it)
    }

    val quiPourraitList = listOf(
        // 1-6 (Calibrages demandés)
        "...tryhard une classée sur Valorant à 3h du mat' ?",
        "...binge-watcher Suits ou Lupin au lieu de réviser ses partiels ?",
        "...chanter du Yamê ou du Josman à pleins poumons sous la douche ?",
        "...faire un carnage absolu en jouant Tank sur Overwatch ?",
        "...renverser son verre sur le canapé en soirée ?",
        "...loupé son arrêt de tram parce qu'il/elle dormait ?",

        // 7-20 (Gaming & Galères nocturnes)
        "...rage-quit une game de FIFA/FC 24 après avoir pris un but à la 90e minute ?",
        "...commander un Uber Eats à 4h du matin et s'endormir avant que le livreur n'arrive ?",
        "...faire une 'disparition irlandaise' d'une soirée sans dire au revoir à personne ?",
        "...s'improviser DJ en soirée et imposer sa playlist de rap français à tout le monde ?",
        "...finir le mois avec 3,42 € sur son compte bancaire le 12 du mois ?",
        "...réviser un partiel de 4 heures la veille à partir de 23h ?",
        "...perdre son briquet, ses clés et son pass transport dans la même soirée ?",
        "...crier 'JPP' ou 'C'est un banger' au premier degré dans une conversation sérieuse ?",
        "...passer 45 minutes à choisir quoi regarder sur Netflix pour finalement s'endormir devant ?",
        "...se faire flashbang par son propre coéquipier sur CS2 ou Valorant ?",
        "...vocaliser tout un album de Jul en rentrant à pied à 5h du matin ?",
        "...lancer 'juste une dernière game' sur League of Legends et voir le soleil se lever ?",
        "...faire semblant d'être attentif en amphi alors qu'il/elle fait ses courses sur Vinted ?",
        "...renverser son bol de céréales ou ses pâtes sur son clavier d'ordinateur ?",

        // 21-40 (Vie étudiante, séries, réseaux)
        "...envoyer un message compromettant à son crush et mettre son téléphone en mode avion par panique ?",
        "...acheter un pass salle de sport, y aller deux fois et continuer de payer l'abonnement pendant un an ?",
        "...débattre pendant deux heures pour savoir si One Piece est meilleur que Naruto ?",
        "...se tromper de sens dans le métro le premier jour de son stage ?",
        "...faire un cocktail avec du jus d'orange premier prix et du sirop de grenadine en se prenant pour un mixologue ?",
        "...connaître par cœur les répliques de Peaky Blinders et marcher au ralenti avec une casquette ?",
        "...manger des pâtes au beurre matin, midi et soir pendant une semaine de flemme ?",
        "...se faire carry en ranked par un gamin de 12 ans sur Fortnite ?",
        "...scroller sur TikTok pendant 3 heures aux toilettes jusqu'à avoir les jambes engourdies ?",
        "...oublier l'anniversaire de son meilleur pote mais se souvenir des stats de tous les joueurs de foot ?",
        "...venir en amphi en pyjama ou en jogging sous prétexte qu'il fait trop froid ?",
        "...lancer un karaoké Céline Dion ou Diam's à fond à 2h du matin sans pression ?",
        "...refuser de sortir parce qu'un nouvel épisode de son anime préféré vient de sortir ?",
        "...bloquer sur le boss d'Elden Ring pendant 4 heures et prétendre que la manette a bugué ?",
        "...envoyer un vocal de 7 minutes pour raconter un ragot qui tenait en une phrase ?",
        "...s'endormir en soirée la tête sur la table au milieu du bruit et de la musique ?",
        "...se faire griller en train de stalker la story Instagram de son ex depuis un compte secondaire ?",
        "...dépenser 50 balles en skins d'armes sur un jeu gratuit sans aucun remords ?",
        "...prendre 20 minutes pour commander au drive du McDo alors qu'il/elle prend toujours la même chose ?",
        "...laisser traîner une assiette dans l'évier jusqu'à ce qu'un nouvel écosystème s'y développe ?",

        // 41-60 (Moments de soirée, défis, maladresses)
        "...proposer un bain de minuit ou une virée nocturne improvisée sur un coup de tête ?",
        "...faire une chute spectaculaire sur Mario Kart à cause d'une carapace bleue sur la ligne d'arrivée ?",
        "...raconter toute sa vie intime à un parfait inconnu dans la file d'attente des toilettes en boîte ?",
        "...prétendre qu'il/elle 'gère le piment' et finir en larmes au premier piment jalapeño ?",
        "...mettre 15 réveils espacés de 3 minutes pour au final tous les éteindre et se rendormir ?",
        "...essayer d'ouvrir une bouteille sans décapsuleur avec les dents ou un briquet et tout casser ?",
        "...pleurer devant la fin d'une série ou d'un anime et prétendre que c'est une allergie aux acariens ?",
        "...répondre 'J'arrive dans 5 minutes' alors qu'il/elle n'est même pas encore sous la douche ?",
        "...se lancer dans une recette MasterChef à 23h et ruiner trois poêles dans la cuisine ?",
        "...connaître absolument toutes les paroles des sons de Gazo et Tiakola sans bégayer ?",
        "...perdre un pari débile et devoir se raser un sourcil ou se teindre les cheveux en bleu ?",
        "...acheter des sneakers en édition limitée et refuser de poser le pied par terre s'il pleut ?",
        "...prétendre avoir lu tout le livre au programme alors qu'il/elle a juste lu la page Wikipédia ?",
        "...claquer tout son salaire ou sa bourse le premier week-end et survivre au riz tout le reste du mois ?",
        "...faire un monologue de 30 minutes sur le lore de Dark Souls ou de Star Wars en soirée ?",
        "...garder son verre vide à la main pendant une heure juste pour avoir une contenance en groupe ?",
        "...rater son permis de conduire pour un refus de priorité imaginaire ?",
        "...mettre sa playlist en soirée et se vexer si quelqu'un change de son après 30 secondes ?",
        "...tenter un salto sur un lit ou un canapé et démolir la table basse ?",
        "...laisser en 'Vu' un message pendant 4 jours et répondre 'Désolé j'avais pas vu' ?",

        // 61-80 (Délires du quotidien & gaming)
        "...acheter une plante verte pour décorer son studio et la laisser mourir de soif en deux semaines ?",
        "...faire semblant de comprendre les règles d'un jeu de société complexe pour ne pas passer pour un naze ?",
        "...tenter de réparer un ordi ou un téléphone soi-même avec un tuto YouTube douteux et l'achever complètement ?",
        "...chanter les punchlines de Ninho ou SDM avec une intensité dramatique digne d'un film ?",
        "...perdre une game de Rocket League et accuser le ping alors que la connexion est en fibre optique ?",
        "...faire semblant d'être au téléphone dans la rue pour éviter de croiser quelqu'un qu'il/elle connaît ?",
        "...dévaliser le buffet d'apéro et stocker des chips dans ses poches pour plus tard ?",
        "...se lancer dans un débat politique enflammé à 4h du mat' avec des gens qu'il ne connaît même pas ?",
        "...vouloir commander un shot le plus fort possible juste pour faire le malin et faire la grimace de sa vie ?",
        "...commander trois formules tacos taille L pour deux personnes en pensant que 'ça passe large' ?",
        "...sortir en t-shirt en plein mois de décembre parce que 'de toute façon on reste à l'intérieur' ?",
        "...regarder 12 épisodes d'affilée d'une série coréenne sous un plaid au lieu de dormir ?",
        "...tenter de faire du beatbox en soirée et projeter des postillons partout sur la table ?",
        "...avoir un écran de téléphone brisé en mille morceaux depuis 8 mois sans jamais le réparer ?",
        "...jouer à la Switch discrètement au fond de l'amphi pendant le cours magistral d'économie ?",
        "...proposer de faire un blind test musical et râler parce que personne ne trouve ses sons de niche ?",
        "...faire un vocal de 10 secondes composé uniquement de bruits bizarres et de rires hystériques ?",
        "...confondre sel et sucre en voulant préparer un cocktail maison ou un gâteau ?",
        "...porter des lunettes de soleil en intérieur en soirée pour 'le flow' ?",
        "...demander l'addition au resto et faire semblant de chercher sa carte bancaire au fond de son sac ?",

        // 81-100 (Situations cultes et fins de soirée)
        "...prendre un selfie avec le vigile de la boîte à la fermeture ?",
        "...essayer de monter dans le mauvais Uber parce que la voiture était blanche aussi ?",
        "...connaître les chorégraphies TikTok de mémoire et les sortir au milieu de la piste de danse ?",
        "...mettre une chemise froissée pour un rencard en prétendant que 'c'est du lin, c'est fait exprès' ?",
        "...avoir 84 onglets ouverts sur Safari sur son téléphone sans jamais en fermer aucun ?",
        "...faire semblant d'apprécier le gin tonic ou le café noir sans sucre pour paraître adulte ?",
        "...perdre au bière-pong dès le premier tour alors qu'il/elle s'était autoproclamé champion régional ?",
        "...passer la nuit entière à refaire le monde sur un balcon avec une clope ou un verre à la main ?",
        "...lancer un stream sur Twitch avec 0 viewer et commenter la partie comme s'il y avait 50 000 personnes ?",
        "...faire brûler des toasts au point de déclencher l'alarme incendie de toute la résidence étudiante ?",
        "...demander au serveur 'C'est quoi votre cocktail le plus sucré ?' dans un bar à cocktails chic ?",
        "...se tromper de prénom en parlant à son crush lors d'un premier rendez-vous ?",
        "...regarder des vidéos de rénovation de piscine ou de fabrication de savon à 3h du matin sans savoir pourquoi ?",
        "...inventer une excuse lunaire du genre 'mon hamster a mangé mes clés' pour annuler une sortie ?",
        "...réussir à négocier une réduction sur un kebab avec un argumentaire digne d'un avocat d'affaires ?",
        "...prendre 45 photos d'un verre de cocktail avec l'éclairage de deux téléphones pour sa story ?",
        "...finir par dormir dans la baignoire avec un coussin parce que tous les lits étaient pris ?",
        "...chanter du Damso avec le regard noir au premier rang d'un concert ?",
        "...partir en vacances avec une valise de 23 kg pour un simple week-end de 2 jours ?",
        "...proposer de refaire une dernière tournée à 5h du matin alors que tout le monde veut aller dormir ?"
    )

    val jeNaiJamaisList = listOf(
        // 1-5 (Exemples demandés)
        "...fini un cocktail que je trouvais dégueulasse juste pour ne pas gâcher.",
        "...ragé sur Brawl Stars au point de crier en public.",
        "...fait semblant de connaître les règles du MMA devant un combat de Ciryl Gane.",
        "...menti sur mon grade pour impressionner quelqu'un.",
        "...bu un shooter sans les mains.",

        // 6-25 (Cocktails, bars & défis alcoolisés)
        "...commandé un cocktail avec un nom prétentieux juste pour avoir l'air stylé.",
        "...fait semblant d'aimer la bière IPA alors que je trouvais ça amer comme du poison.",
        "...piqué un verre ou un sous-bock sérigraphié dans un bar pour ma collection.",
        "...dansé sur une table ou sur le comptoir en fin de soirée.",
        "...tenté de préparer un cocktail sophistiqué en secouant une bouteille d'eau en guise de shaker.",
        "...renversé un cocktail à 15 balles sur les chaussures blanches de quelqu'un.",
        "...mis du sirop de menthe dans de la bière en appelant ça 'une création originale'.",
        "...bu le fond de verre d'un inconnu sur une table par mégarde ou par audace.",
        "...fait semblant d'être sobre face à un vigile en marchant le plus droit possible.",
        "...fait un cul-sec sur un cocktail à base de Tabasco ou de sauce piquante pour un pari.",
        "...essayé d'ouvrir une bière avec les dents et regretté immédiatement pour mon émail.",
        "...demandé 'c'est quoi le cocktail qui saoule le plus vite ?' au barman.",
        "...bu un shot d'alcool pur en faisant une grimace digne d'un film d'horreur.",
        "...fait tomber mon téléphone directement au fond d'un verre ou d'un pichet de sangria.",
        "...dit 'Ce soir je bois qu'un seul verre' et terminé à 5h du matin en boîte.",
        "...essayé d'attraper les glaçons avec ma paille pendant 10 minutes dans un verre.",
        "...pris en photo mon verre sous tous les angles avec le flash avant de boire la première gorgée.",
        "...demandé un verre d'eau gratuit dans un bar pour y vider discrètement une flasque d'alcool.",
        "...goûté un shooter avec de la cannelle et des flammes en me brûlant légèrement la lèvre.",
        "...fait semblant d'être un grand connaisseur de vins en humant le verre avec des hochements de tête.",

        // 26-50 (Anecdotes de soirées & fins de nuit)
        "...quitté une soirée à l'anglaise sans prévenir personne parce que la flemme était trop lourde.",
        "...perdu ma veste au vestiaire parce que j'avais égaré le ticket à numéro.",
        "...envoyé un vocal de 4 minutes complètement incompréhensible à mon crush à 3h du matin.",
        "...fait une sieste de 20 minutes sur le canapé en pleine soirée bondée.",
        "...confondu deux personnes toute une soirée et discuté avec la mauvaise.",
        "...menti sur mon prénom à des inconnus en soirée juste pour me créer une double vie.",
        "...fini par manger des restes de pizza froide trouvés sur la table au réveil.",
        "...supplié le DJ de passer un son précis au moins 5 fois d'affilée.",
        "...oublié le prénom d'une personne 10 secondes après qu'elle s'est présentée.",
        "...fait croire que j'étais le cousin ou le pote du DJ pour gratter l'entrée VIP.",
        "...dépensé la moitié de mon budget du mois en tournées de shots dans un moment d'euphorie.",
        "...essayé de rentrer dans le mauvais appartement après une soirée un peu trop arrosée.",
        "...perdu mes clés, ma carte bancaire ou mes écouteurs au cours de la même nuit.",
        "...fait semblant de recevoir un appel urgent pour m'extirper d'une discussion ennuyeuse.",
        "...chanté 'Bande Organisée' ou un classique des années 2000 en hurlant dans la rue.",
        "...fait semblant d'avoir un briquet pour engager la conversation dans la zone fumeur.",
        "...pris un selfie avec un parfait inconnu dans les toilettes d'un bar.",
        "...refait le monde sur un balcon pendant 2 heures avec quelqu'un rencontré 10 minutes plus tôt.",
        "...pris un Uber en sélectionnant accidentellement mon ancienne adresse à 20 km.",
        "...bu de l'eau tiède directement au robinet des toilettes en rentrant de soirée.",
        "...participé à un tournoi de bière-pong improvisé avec des gobelets douteux et de la bière tiède.",
        "...menti sur mon âge pour entrer dans un bar ou un club quand j'étais mineur.",
        "...commandé un shot pour tout le monde sans avoir vérifié le prix unitaire sur la carte.",
        "...terminé une soirée en finissant le kebab de mon pote parce qu'il n'avait plus faim.",
        "...dormi avec mes chaussures et mon manteau sur le lit après être rentré de soirée.",

        // 51-70 (Vie étudiante & galères d'amphi)
        "...révisé un partiel uniquement la veille entre minuit et 6h du matin avec des Monster.",
        "...séché un cours magistral de 8h parce que mon lit était beaucoup trop confortable.",
        "...fait semblant de prendre des notes sur mon ordi en amphi alors que je scrollais Vinted.",
        "...mangé des pâtes au beurre ou au ketchup pendant 4 jours d'affilée en fin de mois.",
        "...oublié d'éteindre mon micro sur Zoom ou Teams pendant un cours à distance.",
        "...emprunté un stylo au premier cours de l'année sans jamais le rendre.",
        "...inventé un problème familial imaginaire pour justifier une absence en TD.",
        "...imprimé mes cours 10 minutes avant l'examen dans une panique absolue.",
        "...utilisé ChatGPT pour rédiger l'introduction complète d'un devoir sans la relire.",
        "...dormi sur les tables du fond de la bibliothèque universitaire.",
        "...vérifié mon compte bancaire et découvert un solde inférieur à 2 € avant le 15 du mois.",
        "...demandé à un pote de signer la feuille de présence à ma place en amphi.",
        "...fait un exposé oral improvisé sans avoir ouvert le diaporama au préalable.",
        "...oublié le jour d'un examen et m'en être rendu compte 30 minutes avant.",
        "...acheté un livre universitaire à 40 balles pour ne jamais ouvrir la moindre page.",
        "...mis accidentellement du sel à la place du sucre dans la préparation d'un gâteau ou d'un café.",
        "...marché dans la rue avec des écouteurs sans musique juste pour ne pas qu'on m'adresse la parole.",
        "...décalé mon réveil 8 fois de suite le matin avec des alarmes espacées de 3 minutes.",
        "...fait brûler un plat surgelé ou une pizza au four au point d'enfumer tout le salon.",
        "...acheté des baskets hors de prix pour les garder dans leur boîte de peur de les salir.",

        // 71-85 (Gaming & séries)
        "...cassé la manette de ma console après une défaite injuste sur FIFA ou Rocket League.",
        "...passé une nuit blanche entière à jouer en ranked sur LoL ou Valorant la veille d'un contrôle.",
        "...accusé la connexion ou le ping après m'être fait éliminer bêtement sur un jeu.",
        "...acheté un skin ou un passe de combat avec de l'argent réel en me promettant que c'était le dernier.",
        "...insulté un bot dans un jeu vidéo comme si c'était un vrai joueur.",
        "...prétendu être malade pour esquiver une sortie et pouvoir jouer en ligne tranquille.",
        "...passé plus de 2 heures à créer le physique de mon personnage avant même de commencer le jeu.",
        "...crié de joie tout seul dans ma chambre après avoir battu un boss d'Elden Ring ou de Dark Souls.",
        "...regardé des streams Twitch pendant plus de 6 heures d'affilée un dimanche pluvieux.",
        "...téléchargé un jeu mobile addictif pour finalement y passer 4 heures d'affilée aux toilettes.",
        "...binge-watché une saison complète de 10 épisodes en une seule journée au lieu de travailler.",
        "...pleuré devant la fin d'un animé ou d'un film en prétendant avoir une poussière dans l'œil.",
        "...spoilé sans faire exprès un épisode majeur de série à un ami et m'être fait insulter.",
        "...fait semblant d'avoir vu Peaky Blinders ou Breaking Bad pour m'intégrer à une discussion.",
        "...re-regardé pour la 5e fois la même série réconfortante plutôt que d'en commencer une nouvelle.",

        // 86-100 (Réseaux sociaux, crushs & secrets inavouables)
        "...stalké le compte Insta de quelqu'un jusqu'à ses publications de 2018 par curiosité.",
        "...liké par erreur une photo vieille de 3 ans en stalkant quelqu'un en paniquant.",
        "...mis une story Instagram ciblée uniquement pour qu'une seule personne précise la voie.",
        "...supprimé une photo sur les réseaux sociaux parce qu'elle n'avait pas fait assez de likes en 15 minutes.",
        "...laissé un message en 'Lu' pendant plusieurs jours en prétextant un débordement imaginaire.",
        "...envoyé un screen d'une conversation à la personne même dont je parlais par mégarde.",
        "...appris par cœur une chorégraphie TikTok ridicule tout seul dans ma salle de bain.",
        "...bloqué quelqu'un sur un coup de tête pour le débloquer 2 heures plus tard.",
        "...fait semblant de chercher mes clés ou mon téléphone pendant 5 minutes alors qu'ils étaient dans ma main.",
        "...fait semblant de comprendre une blague en riant fort alors que je n'avais rien capté.",
        "...tenté de couper mes propres cheveux ou ma frange tout seul devant le miroir et fait un carnage.",
        "...fait le serment solennel 'plus jamais je ne boirai' le dimanche matin à 11h.",
        "...tenté d'impressionner quelqu'un en jonglant avec une bouteille ou un shaker et l'avoir brisé par terre.",
        "...refait toute la chorégraphie d'une chanson en boîte avec une synchronisation catastrophique.",
        "...avoué un gros secret de ma vie lors d'une partie de Je n'ai jamais pour ne pas avoir à boire."
    )

    val rouletteBarmanList: List<String> = listOf(
        // 1-5 (Exemples demandés)
        "Bois 2 gorgées",
        "Distribue 3 gorgées",
        "Tournée générale : cul-sec !",
        "Immunité jusqu'au prochain tour",
        "Choisis un partenaire de boisson",

        // 6-20 (Gorgées & distributions directes)
        "Bois 1 gorgée",
        "Bois 3 gorgées",
        "Distribue 2 gorgées",
        "Distribue 4 gorgées",
        "Cul-sec sur ton verre !",
        "Le joueur à ta droite boit 2 gorgées",
        "Le joueur à ta gauche boit 2 gorgées",
        "Le joueur en face de toi boit 2 gorgées",
        "Cascade générale : tout le monde boit 1 gorgée",
        "Shot de l'amitié avec la personne de ton choix",
        "Gorgée miroir : ton voisin boit autant que toi",
        "Le verre le plus plein boit 2 gorgées",
        "Le verre le plus vide se ressert immédiatement",
        "Silence absolu pendant 2 minutes ou cul-sec",
        "Tous ceux qui ont du vin boivent 1 gorgée",

        // 21-35 (Défis express & conditions)
        "Tous ceux qui ont de la bière boivent 1 gorgée",
        "Tous ceux qui ont un cocktail boivent 2 gorgées",
        "Ceux qui portent du noir boivent 1 gorgée",
        "Le dernier à toucher son nez boit 2 gorgées",
        "Le premier à lever la main distribue 3 gorgées",
        "Interdit de dire 'OUI' ou 'NON' pendant 3 tours",
        "Distribue 5 gorgées réparties entre tous les joueurs",
        "Bois 2 gorgées sans utiliser tes mains",
        "Tous les célibataires boivent 2 gorgées",
        "Tous ceux en couple boivent 1 gorgée",
        "Duel de regards : le perdant boit 2 gorgées",
        "Fais un toast officiel et enflammé avant de boire",
        "Immunité totale contre les distributions pendant 5 minutes",
        "Échange ton verre avec ton voisin de gauche",
        "Rejoue immédiatement : double dose !",

        // 36-50 (Sentences du Barman & mini-jeux)
        "Invente une règle obligatoire jusqu'à la fin de la partie",
        "Le joueur le plus jeune de la table boit 2 gorgées",
        "Le joueur le plus âgé distribue 2 gorgées",
        "Cite 3 cocktails en moins de 3 secondes ou bois 2 gorgées",
        "Tout le monde trinque et boit une gorgée synchronisée",
        "Le joueur à ta droite te donne un gage ou tu bois 3 gorgées",
        "Double gorgée pour toi et ton voisin de gauche",
        "Pierre-feuille-ciseaux avec le voisin : le perdant boit 2 gorgées",
        "Tu deviens le barman : sers un verre à quelqu'un",
        "Cul-sec ou avoue un secret de soirée !",
        "Interdit de regarder son téléphone pendant 10 minutes sous peine de shot",
        "Bois 1 gorgée pour chaque voyelle dans ton prénom",
        "Désigne le joueur le plus sage : il boit 2 gorgées",
        "Tournée des rois : les 2 personnes à tes côtés boivent 1 gorgée",
        "Shot surprise ou cul-sec immédiat !"
    )

    val rouletteRules: List<RouletteRule> = rouletteBarmanList.map { item ->
        val type = when {
            item.contains("Distribue", ignoreCase = true) -> "DISTRIBUER"
            item.contains("Immunité", ignoreCase = true) -> "BONUS"
            item.contains("Bois", ignoreCase = true) || item.contains("gorgée", ignoreCase = true) || item.contains("cul-sec", ignoreCase = true) || item.contains("shot", ignoreCase = true) -> "BOIRE"
            else -> "DÉFI"
        }
        val colorHex = when (type) {
            "BOIRE" -> "#FF5252"
            "DISTRIBUER" -> "#81C784"
            "BONUS" -> "#FFD54F"
            else -> "#FFB74D"
        }
        RouletteRule(
            title = item,
            instruction = "Sentence du Barman appliquée immédiatement !",
            type = type,
            colorHex = colorHex
        )
    }

    val undercoverPairsList: List<Pair<String, String>> = listOf(
        // Boissons & Cocktails (20)
        Pair("Mojito", "Caipirinha"),
        Pair("Vodka", "Gin"),
        Pair("Whisky", "Bourbon"),
        Pair("Bière", "Cidre"),
        Pair("Vin rouge", "Vin blanc"),
        Pair("Champagne", "Prosecco"),
        Pair("Tequila", "Mezcal"),
        Pair("Rhum", "Cachaça"),
        Pair("Espresso Martini", "White Russian"),
        Pair("Spritz", "Negroni"),
        Pair("Coca-Cola", "Pepsi"),
        Pair("Eau plate", "Eau gazeuse"),
        Pair("Limonade", "Tonic"),
        Pair("Café", "Thé"),
        Pair("Jus d'orange", "Jus de pomme"),
        Pair("Sirop de grenadine", "Sirop de fraise"),
        Pair("Glaçon", "Glace pilée"),
        Pair("Shaker", "Verre à mélange"),
        Pair("Paille", "Touilleur"),
        Pair("Barman", "Sommelier"),

        // Tech, Gaming & Séries (20)
        Pair("Valorant", "Overwatch"),
        Pair("PlayStation", "Xbox"),
        Pair("Souris", "Manette"),
        Pair("Clavier", "Écran"),
        Pair("Discord", "TeamSpeak"),
        Pair("Twitch", "YouTube"),
        Pair("Netflix", "Prime Video"),
        Pair("Spotify", "Deezer"),
        Pair("Casque", "Écouteurs"),
        Pair("PC portable", "Tablette"),
        Pair("Brawl Stars", "Clash Royale"),
        Pair("Fortnite", "Apex Legends"),
        Pair("Mario", "Luigi"),
        Pair("Fifa", "Rocket League"),
        Pair("Suits", "Lupin"),
        Pair("League of Legends", "Dota 2"),
        Pair("GTA", "Red Dead"),
        Pair("Instagram", "TikTok"),
        Pair("ChatGPT", "Gemini"),
        Pair("Steam", "Epic Games"),

        // Soirée, Fête & Vie nocturne (10)
        Pair("Boîte de nuit", "Bar"),
        Pair("Soirée appart", "Festival"),
        Pair("DJ", "Groupe live"),
        Pair("Shot", "Pinte"),
        Pair("Piste de danse", "Comptoir"),
        Pair("Confetti", "Paillettes"),
        Pair("Vigile", "Bouncer"),
        Pair("Vestiaire", "Fumoir"),
        Pair("Lendemain de fête", "Nuit blanche"),
        Pair("Uber", "Taxi"),

        // Nourriture & Fast-food (10)
        Pair("Pizza", "Burger"),
        Pair("Frites", "Potatoes"),
        Pair("Kebab", "Tacos"),
        Pair("Ketchup", "Mayonnaise"),
        Pair("Crêpe", "Gaufre"),
        Pair("Chocolat noir", "Chocolat au lait"),
        Pair("Croissant", "Pain au chocolat"),
        Pair("Pâtes", "Riz"),
        Pair("Fromage", "Charcuterie"),
        Pair("Nutella", "Confiture"),

        // Musique & Culture (10)
        Pair("Rap", "Trap"),
        Pair("Guitare", "Basse"),
        Pair("Piano", "Synthétiseur"),
        Pair("Batterie", "Percussions"),
        Pair("Concert", "Clip"),
        Pair("Vinyle", "CD"),
        Pair("Micro", "Enceinte"),
        Pair("Cinéma", "Théâtre"),
        Pair("Série", "Film"),
        Pair("Livre", "BD"),

        // Sports & Compétition (10)
        Pair("Boxe", "MMA"),
        Pair("Judo", "Karaté"),
        Pair("Football", "Futsal"),
        Pair("Basketball", "Handball"),
        Pair("Tennis", "Ping-pong"),
        Pair("Course", "Marathon"),
        Pair("Natation", "Plongée"),
        Pair("Ski", "Snowboard"),
        Pair("Skate", "Roller"),
        Pair("Arbitre", "Coach"),

        // Quotidien & Études (10)
        Pair("Examen", "Partiel"),
        Pair("Stylo", "Crayon"),
        Pair("Cahier", "Classeur"),
        Pair("Sac à dos", "Sacoche"),
        Pair("Réveil", "Chronomètre"),
        Pair("Bus", "Tramway"),
        Pair("Gare", "Aéroport"),
        Pair("Appartement", "Maison"),
        Pair("Ascenseur", "Escalier"),
        Pair("Parapluie", "Imperméable"),

        // Nature & Lieux (10)
        Pair("Plage", "Piscine"),
        Pair("Mer", "Océan"),
        Pair("Montagne", "Colline"),
        Pair("Forêt", "Jungle"),
        Pair("Soleil", "Lune"),
        Pair("Pluie", "Neige"),
        Pair("Bordeaux", "Paris"),
        Pair("Hôtel", "Camping"),
        Pair("Île", "Presqu'île"),
        Pair("Rivière", "Lac"),

        // Boissons, Saveurs & Bar (15)
        Pair("Limonade", "Diabolo"),
        Pair("Sirop de menthe", "Sirop de grenadine"),
        Pair("Chocolat chaud", "Cappuccino"),
        Pair("Espresso", "Café allongé"),
        Pair("Pastis", "Ricard"),
        Pair("Whisky", "Rhum"),
        Pair("Shot", "Goulée"),
        Pair("Amaretto", "Baileys"),
        Pair("Cointreau", "Grand Marnier"),
        Pair("Punch", "Sangria"),
        Pair("Bière blonde", "Bière blanche"),
        Pair("Cidre doux", "Cidre brut"),
        Pair("Sucre roux", "Sucre blanc"),
        Pair("Citron jaune", "Citron vert"),
        Pair("Glaçon", "Pain de glace"),

        // Tech, Matériel & Gaming (15)
        Pair("Écran tactile", "Tablette"),
        Pair("Stylet", "Doigt"),
        Pair("Casque sans fil", "Écouteurs"),
        Pair("Clavier mécanique", "Clavier membrane"),
        Pair("Câble USB-C", "Câble Lightning"),
        Pair("Batterie externe", "Chargeur"),
        Pair("Mode sombre", "Mode clair"),
        Pair("Bluetooth", "Wi-Fi"),
        Pair("Nintendo Switch", "Steam Deck"),
        Pair("Manette", "Joystick"),
        Pair("Minecraft", "Roblox"),
        Pair("League of Legends", "Dota 2"),
        Pair("CS:GO", "Valorant"),
        Pair("Battle Royale", "Match à mort"),
        Pair("Serveur", "Cloud"),

        // Musique, Culture & Spectacle (15)
        Pair("Festival", "Concert"),
        Pair("Enceinte Bluetooth", "Barre de son"),
        Pair("Refrain", "Couplet"),
        Pair("Auto-Tune", "Vocodeur"),
        Pair("Album", "EP"),
        Pair("Freestyle", "Improvisation"),
        Pair("Basse", "Contrebasse"),
        Pair("Violon", "Violoncelle"),
        Pair("Cinéma", "Projection"),
        Pair("Popcorn sucré", "Popcorn salé"),
        Pair("Dessin animé", "Anime"),
        Pair("Manga", "Comics"),
        Pair("Podcast", "Émission radio"),
        Pair("Spectacle", "One-man-show"),
        Pair("Stand-up", "Théâtre"),

        // Sports, Arts martiaux & Activités (15)
        Pair("Coup de poing", "Coup de pied"),
        Pair("K.-O.", "Abandon"),
        Pair("Ring", "Octogone"),
        Pair("Ceinture noire", "Ceinture marron"),
        Pair("Kimono", "Tatami"),
        Pair("Pêche au leurre", "Pêche au bouchon"),
        Pair("Canne à pêche", "Épuisette"),
        Pair("Tente", "Sac de couchage"),
        Pair("Surf", "Bodyboard"),
        Pair("Vélo de route", "VTT"),
        Pair("Trotinette", "Skate"),
        Pair("Randonnée", "Balade"),
        Pair("Escalade", "Accrobranche"),
        Pair("Musculation", "Crossfit"),
        Pair("Cardio", "Sprint"),

        // Nourriture, Cuisine & Snacks (15)
        Pair("Tacos", "Burrito"),
        Pair("Nuggets", "Tenders"),
        Pair("Croque-monsieur", "Panini"),
        Pair("Sandwich", "Wrap"),
        Pair("Pain burger", "Pain bagel"),
        Pair("Sauce barbecue", "Sauce burger"),
        Pair("Sauce samouraï", "Sauce algérienne"),
        Pair("Chips", "Doritos"),
        Pair("Pistaches", "Cacahuètes"),
        Pair("Smoothie", "Milkshake"),
        Pair("Glace à la vanille", "Glace au chocolat"),
        Pair("Beignet", "Churro"),
        Pair("Brownie", "Cookie"),
        Pair("Blender", "Mixeur plongeant"),
        Pair("Four", "Micro-ondes"),

        // Vie quotidienne, Vêtements & Objets (15)
        Pair("Veste en jean", "Blouson en cuir"),
        Pair("Sweat à capuche", "Pull"),
        Pair("Sneakers", "Baskets"),
        Pair("Bonnet", "Casquette"),
        Pair("Montre à aiguilles", "Montre connectée"),
        Pair("Bague", "Bracelet"),
        Pair("Lunettes de soleil", "Lunettes de vue"),
        Pair("Porte-monnaie", "Porte-cartes"),
        Pair("Clé de maison", "Bip de garage"),
        Pair("Oreiller", "Coussin"),
        Pair("Couette", "Plaid"),
        Pair("Valise", "Sac de voyage"),
        Pair("Miroir", "Vitre"),
        Pair("Lampe de chevet", "Plafonnier"),
        Pair("Bougie", "Encens"),

        // Lieux, Transports & Villes (10)
        Pair("TGV", "TER"),
        Pair("Métro", "RER"),
        Pair("Trottinette électrique", "Vélo en libre-service"),
        Pair("Autoroute", "Nationale"),
        Pair("Rond-point", "Carrefour"),
        Pair("Pont", "Tunnel"),
        Pair("Place publique", "Parc"),
        Pair("Bordeaux", "Toulouse"),
        Pair("Marseille", "Nice"),
        Pair("Lyon", "Grenoble")
    )
}
