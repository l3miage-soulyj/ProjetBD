package fr.uga.im2ag.l3.miage.db;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import fr.uga.im2ag.l3.miage.db.model.Abonne;
import fr.uga.im2ag.l3.miage.db.model.Bornette;
import fr.uga.im2ag.l3.miage.db.model.Client;
import fr.uga.im2ag.l3.miage.db.model.Enums;
import fr.uga.im2ag.l3.miage.db.model.Location;
import fr.uga.im2ag.l3.miage.db.model.NonAbonne;
import fr.uga.im2ag.l3.miage.db.model.Station;
import fr.uga.im2ag.l3.miage.db.model.Velo;
import fr.uga.im2ag.l3.miage.db.model.Enums.Etat;
import fr.uga.im2ag.l3.miage.db.repository.RepositoryFactory;
import fr.uga.im2ag.l3.miage.db.repository.api.AbonneRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.BornetteRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.LocationRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.NonAbonneRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.StationRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.VeloRepository;
import fr.uga.im2ag.l3.miage.db.utils.LectureClavier;

public class MenuUtilisateur {

    RepositoryFactory daoFactory = new RepositoryFactory();
    EntityManager entityManager = Persistence.createEntityManagerFactory("JPA-HBM").createEntityManager();
    StationRepository stationRepository = daoFactory.newStationRepository(entityManager);
    BornetteRepository bornetteRepository = daoFactory.newBornetteRepository(entityManager);
    AbonneRepository abonneRepository = daoFactory.newAbonneRepository(entityManager);
    VeloRepository veloRepository = daoFactory.newVeloRepository(entityManager);
    LocationRepository locationRepository = daoFactory.newLocationRepository(entityManager);
    NonAbonneRepository nonAbonneRepository = daoFactory.newNonAbonneRepository(entityManager);

    /*
     * convertit un string pass?? en param??tre en un objet de type Date
     * 
     *
     * @return un objet de type Date
     * 
     *
     * @param dateString
     */
    public Date convertDate(String dateString) throws ParseException {
        return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime());
    }

    public Timestamp convertTimestamp(String timeString) throws ParseException {
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = format.parse(timeString);
        Timestamp timestamp = new java.sql.Timestamp(date.getTime());

        return timestamp;

    }
    /*
     * Verifie que la date pass??e en param??tre respecte l'expression r??guli??re d'une date
     * au format sql  :(yyyy-mm-jj)
     *
     * @return true si  la date est au bon format 
     * 
     *
     * @param Formatdate
     */
    public boolean isValidDate(String Formatdate) {
        String regex = "^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence) Formatdate);
        return matcher.matches();
    }
    /*
     * Renvoie une station choisi par l'utilisateur si il est dans une situation
     *d'emprunt ou de depot
     *
     * @return une station 
     * abonnees
     *
     * @param estEmprunt de type boolean
     */
    public Station choisirStation(boolean estEmprunt) {
        
        List<Station> ListStation = stationRepository.getAll();//on recup??re la liste des staions de la BDD
        int choix;
        Station stationChoisi = null;
        int i = 0;
        while (stationChoisi == null) {
            i = 0;
            for (Station station : ListStation) { // On parcours la liste des stations
                System.out.println("choisissez un station!");

                if (estEmprunt) { // EMPRUNT
                    if (!station.stationOK()) { // On verifie que la station est OK
                        System.out.println(i + " - " + station.getAdresse() + " (*AUCUN VELO DISPONIBLE*)");

                    } else {
                        System.out.println(i + " - " + station.getAdresse()); // On affiche chaque station
                    }
                } else { // DEPOT
                    if (station.contientPlaceLibre()) {
                        System.out.println(i + " - " + station.getAdresse() + " (*PLACES DISPONIBLES*)"); // On affiche
                                                                                                          // les
                                                                                                          // stations
                                                                                                          // avec des
                                                                                                          // places
                                                                                                          // disponibles
                    } else {
                        System.out.println(i + " - " + station.getAdresse()); // On affiche chaque station
                    }
                }
                i++;
            }

            choix = LectureClavier.lireEntier("");
            if (estEmprunt) { // EMPRUNT
                while (choix > ListStation.size() - 1 || choix < 0 || !ListStation.get(choix).stationOK()) {
                    choix = LectureClavier
                            .lireEntier("Tappez un num??ro de station valide et avec des v??los disponibles !"); // Si la
                                                                                                               // station
                                                                                                               // n'est
                                                                                                               // pas OK
                                                                                                               // on
                                                                                                               // affiche
                                                                                                               // la
                                                                                                               // message
                                                                                                               // d'erreur
                }
            } else { // DEPOT
                while (choix > ListStation.size() - 1 || choix < 0 || !ListStation.get(choix).contientPlaceLibre()) {
                    choix = LectureClavier.lireEntier("Tappez un num??ro de station valide et avec des places libres !"); // Si
                                                                                                                         // la
                                                                                                                         // station
                                                                                                                         // ne
                                                                                                                         // contient
                                                                                                                         // pas
                                                                                                                         // des
                                                                                                                         // places
                                                                                                                         // libres
                                                                                                                         // on
                                                                                                                         // affiche
                                                                                                                         // une
                                                                                                                         // message
                                                                                                                         // de
                                                                                                                         // warning
                }
            }
            stationChoisi = ListStation.get(choix);
        }
        return stationChoisi;
    }

    /*
     * Verifie que le code passe est dans la base des abonnees
     *
     * @return <code>true</code> si codeSecret est dans la base des donnees des
     * abonnees
     *
     * @param codeSecret
     */
    public boolean contientCodeSecretAbonne(int codeSecret) {
        boolean b = false;
        List<Abonne> list = abonneRepository.getAll();
        for (Abonne abonne : list) {
            if (abonne.getCodeSecret() == codeSecret) {
                b = true;
            }
        }
        return b;
    }

    /*
     * Verifie que le code passe est dans la base des non abonnees
     *
     * @return <code>true</code> si codeSecret est dans la base des donnees des
     * abonnees
     *
     * @param codeSecret
     */
    public boolean contientCodeSecretNonAbonne(int codeSecret) {
        List<NonAbonne> list = nonAbonneRepository.getAll();
        boolean b = false;
        for (NonAbonne abonne : list) {
            if (abonne.getCodeSecret() == codeSecret) {
                b = true;
            }
        }
        return b;
    }

    /* Renvoie l'abonne corrspondant au code secret pass?? en param??tre
     *
     *
     * @return un abonne
     * 
     *
     * @param codeSecret
     */
    public Abonne getAbonneAvecCode(int codeSecret) {
        List<Abonne> list = abonneRepository.getAll();
        Abonne a = null;
        for (Abonne abonne : list) {
            if (abonne.getCodeSecret() == codeSecret) {
                a = abonne;
            }
        }
        return a;
    }

   /* Renvoie le non-abonne corrspondant au code secret pass?? en param??tre
     *
     *
     * @return un non abonne
     * 
     *
     * @param codeSecret
     */
    public NonAbonne getNonAbonneAvecCode(int codeSecret) {
        List<NonAbonne> list = nonAbonneRepository.getAll();
        NonAbonne a = null;
        for (NonAbonne nonAbonne : list) {
            if (nonAbonne.getCodeSecret() == codeSecret) {
                a = nonAbonne;
            }
        }
        return a;
    }

    /*
     * Affiche l'historique des location en cours ou termin??es d'un client 
     *
     * 
     * 
     *
     * @param un client 
     */
    public void consulterHistoriqueDesLocations(Client c){
        int indexLocation = 0;
        List<Location> listLocationsClient = c.getLocations();
            if(listLocationsClient !=null){
                for (Location location : listLocationsClient) {
                
                    if(location.getHeureFin()!=null){
                    System.out.println(indexLocation + ". Location num??ro "+" "+location.getIdLoc()+" date : "+new java.util.Date(location.getHeureDebut().getTime())+"");
                    System.out.println("Cout total: "+location.getCout()+"euros");

                
                    }else{
                        System.out.println( indexLocation + ". Location en cours  num??ro "+" "+location.getIdLoc()+" heure debut : "+ new java.util.Date(location.getHeureDebut().getTime())+" et vous avez " + location.getVelos().size()+" v??lo pas rendu!") ;
                    }
                    indexLocation++;


            }
           
        }else{
            System.out.println("Aucun historique de location termin?? ou en cours !!");
        }
        




    }
    
    /*
     * Saisie des information d'une personne qui veut devenir un abonne et ajoute un nouvel abonne ?? la base 
     *
     *
     *
     * 
     */

    
    public void abonner() {

        // *****Param??tre ?? saisir******//
        Abonne nouvelleAbonne;
        String nom;
        String prenom;
        Date dateNaissance;
        Enums.sexe sexe;
        String adresse;
        Date dateAbonnement;
        int codeSecret;
        String numeroCB;

        System.out.println("#########################################################");
        System.out.println(" |_   _|                   (_)     | | (_)             ");
        System.out.println("   | |  _ __  ___  ___ _ __ _ _ __ | |_ _  ___  _ __   ");
        System.out.println("   | | | '_ \\/ __|/ __| '__| | '_ \\| __| |/ _ \\| '_ \\  ");
        System.out.println("  _| |_| | | \\__ \\ (__| |  | | |_) | |_| | (_) | | | | ");
        System.out.println(" |_____|_| |_|___/\\___|_|  |_| .__/ \\__|_|\\___/|_| |_| ");
        System.out.println("                             | |                       ");
        System.out.println("                             |_|                       ");
        System.out.println("#########################################################");
        System.out.println(" ");

        // *****Saisie du nom***** //
        System.out.println("Saisissez votre nom de Famille:");
        nom = LectureClavier.lireChaine();

        // *****Saisie du prenom*****//
        System.out.println("Saisissez votre prenom ");
        prenom = LectureClavier.lireChaine();

        // *****Saisie de la date de naissance*****//
        // initialisation
        dateNaissance = new Date(System.currentTimeMillis());

        System.out.println("Saisissez votre date de naissance (AAAA-MM-JJ): ");
        String str = LectureClavier.lireChaine();
        // Ici on verifie que str demand?? est au bon format )
        while (isValidDate(str) != true) {
            System.out.println("Saisissez votre date de naissance!(AAAA-MM-JJ): ");
            str = LectureClavier.lireChaine();
        }

        try {
            dateNaissance = convertDate(str);
        } catch (Exception e) {
            System.out.println("pattern de date invalide , use:(AAAA-MM-JJ)");
        }

        // *****Saisie du sexe*****//
        int i = 0;
        sexe = Enums.sexe.NON_BINAIRE;// initialisation
        while (i < 1 || i > 3) {
            System.out.println("Saisissez votre sexe : ");
            System.out.println(" saisissez 1 pour homme : ");
            System.out.println(" saisissez 2 pour femme : ");
            System.out.println(" saisissez 3 pour non binaire : ");
            i = LectureClavier.lireEntier("entier lu :");
        }
        switch (i) {
            case 1:
                sexe = Enums.sexe.MALE;
                break;

            case 2:
                sexe = Enums.sexe.FEMELLE;
                break;

            case 3:
                sexe = Enums.sexe.NON_BINAIRE;
                break;

            default:
                break;

        }

        // *****Saisie de l'adresse*****//
        System.out.println("Saisissez votre adresse : ");
        adresse = LectureClavier.lireChaine();

        // *****Saisie du mot de passe*****//
        System.out.println("Saisissez votre code secret   : ");
        codeSecret = LectureClavier.lireEntier("code lu:");
        while (contientCodeSecretAbonne(codeSecret) == true) {
            codeSecret = LectureClavier.lireEntier(" code secret d??ja existant ,entrez un nouveau code :");
        }

        // *****Saisie du num??ro de carte bancaire****
        System.out.println("Saisissez votre num??ro de carte bancaire  : ");
        numeroCB = LectureClavier.lireChaine();
        // date d'inscription = date de souscription d'un abonnement
        dateAbonnement = new Date(System.currentTimeMillis());
        // instantiation d'un nouvelle abonn??
        nouvelleAbonne = new Abonne(nom, prenom, sexe, adresse, dateNaissance, dateAbonnement);
        nouvelleAbonne.setCodeSecret(codeSecret);
        nouvelleAbonne.setNumeroCB(numeroCB);

        // nouvelleAbonne dans la base de donn??e
        entityManager.getTransaction().begin();
        abonneRepository.save(nouvelleAbonne);
        entityManager.getTransaction().commit();

    }

    /*  identifie un client ?? l'aide de son code secret issu de la BDD 
     * 
     *
     *
     * @return un client 
     */

    public Client identifier() {
        int select;
        int codeSecret;
        Client c = null;
        String abonneToStr = "";

        System.out.println("################################################################");
        System.out.println(" |_ _|__| | ___ _ __ | |_(_)/ _(_) ___ __ _| |_(_) ___  _ __  ");
        System.out.println("  | |/ _` |/ _ \\ '_ \\| __| | |_| |/ __/ _` | __| |/ _ \\| '_ \\ ");
        System.out.println("  | | (_| |  __/ | | | |_| |  _| | (_| (_| | |_| | (_) | | | |");
        System.out.println(" |___\\__,_|\\___|_| |_|\\__|_|_| |_|\\___\\__,_|\\__|_|\\___/|_| |_|");
        System.out.println("################################################################");

        // CHOISIR ABONNE OU NON ABONNE
        System.out.println("Tappez 1 si vous ??tes abonn??s, 2 sinon :");
        select = 0;

        while (select > 2 || select <= 0) {
            select = LectureClavier.lireEntier("** Tappez 1 si vous ??tes abonn??s, 2 sinon **");
        }

        // ABONNE
        if (select == 1) {
            System.out.println("Saisissez votre code secret afin de vous identifier : ");
            codeSecret = LectureClavier.lireEntier("Code lu :");
            while (!contientCodeSecretAbonne(codeSecret)) {
                System.out.println(
                        "Votre code secret  ne correspond ?? aucun abonne veuillez resaisir ?? nouveau votre code secret (tappez -1 pour retourner au menu principale): ");
                codeSecret = LectureClavier.lireEntier("Code lu :");
                if (codeSecret == -1) {
                    mainMenu();
                }
            }
            c = getAbonneAvecCode(codeSecret);
            abonneToStr = getAbonneAvecCode(codeSecret).toString();
            System.out.println(" Bonjour " + abonneToStr + ".");
        }
        // NON ABONNE
        else {
            System.out.println("Saisissez votre code secret afin de vous identifier : ");
            codeSecret = LectureClavier.lireEntier("Code lu :");
            while (!contientCodeSecretNonAbonne(codeSecret)) {
                System.out.println(
                        "Votre code secret  ne correspond ?? aucun non abonne veuillez resaisir ?? nouveau votre code secret (tappez -1 pour retourner au menu principale): ");
                codeSecret = LectureClavier.lireEntier("Code lu :");
                if (codeSecret == -1) {
                    mainMenu();
                }
            }
            c = getNonAbonneAvecCode(codeSecret);

        }
        return c;

    }
    
    /*cree un nouvel non abonne et lui fournit un code secret unique 
     * 
     *
     * @return un non abonne
     * 
     *
     */
    public NonAbonne continuerSanConnexion() {

        NonAbonne a = null;
        int codeSecret;
        String numeroCB;
        Random random = new Random();
        codeSecret = random.nextInt(100) * 10;
       
        //on fournit un code secret unique 
        while (contientCodeSecretNonAbonne(codeSecret)) {
            codeSecret = random.nextInt(100) * 100;
        }

        System.out.println("votre code secret  est : "+ codeSecret +" RETENEZ LE IMPERATIVEMENT !!");

        System.out.println("Saisissez votre num??ro de carde bancaire : ");
        numeroCB = LectureClavier.lireChaine();

        a = new NonAbonne();
        a.setCodeSecret(codeSecret);
        a.setNumeroCB(numeroCB);

        // nouvelNonAbonne dans la base de donn??e
        entityManager.getTransaction().begin();
        nonAbonneRepository.save(a);
        entityManager.getTransaction().commit();
        return a;
    }

    /*  
     * 
     * Associe une location d'un ou plusieurs velo ?? un client
     *
     */
    public void emprunt(Station s, Client c) {

        int codeSecret;
        Bornette bornette;

        // ABONNE
        if (c instanceof Abonne) {
            codeSecret = LectureClavier.lireEntier("Saisir votre code secret : ");
            while (c.getCodeSecret()!=codeSecret){
                System.out.println("Code secret incorrect! ");
                System.out.println("Resaisissez votre codre secret!");

                codeSecret = LectureClavier.lireEntier("");
            }
            System.out.println((getAbonneAvecCode(codeSecret)));
        }
        // NON ABONNE
        else {
            codeSecret = LectureClavier.lireEntier("Saisir votre code secret : ");
            while (c.getCodeSecret()!=codeSecret) {
                System.out.println("code secret incorrect ");
                System.out.println("Resaisissez votre codre secret!");

                codeSecret = LectureClavier.lireEntier("Resaisissez votre code secret !");
            }
        }
        // Afficher la liste des bornettes :
        System.out.println("Liste des  bornettes poss??dant un v??lo OK  : ");

        // Ici on parcours la liste des bornettes avec des velo
        int index = 0;
        List<Bornette> bornettesAvecVeloOk = new ArrayList<Bornette>();
        for (Bornette b : s.getBornettes()) {
            if (!b.getLibre()&&(b.getVelo().getEtat()==Etat.OK)) { // Si la bornette est occup??e avec un velo en bonne etat
                bornettesAvecVeloOk.add(b);
                System.out.println("" + index + " - Bornette B" + (index) + "" + b.getVelo().getModele());
                index++;
            }
            
        }
        System.out.println("Voulez vous declarer un velo HS parmis la liste de velo ci-dessus ? (1:oui 2:non)");
        int select = LectureClavier.lireEntier("");

        while(select > 2|| select <=0){
            select = LectureClavier.lireEntier(" 1 oui  2 non !");
            
        }
        if(select==1){
            
            int i = LectureClavier.lireEntier(" choisi le num??ro de la bornnette o?? le velo est HS");
            bornette = bornettesAvecVeloOk.get(i);
            Velo v = bornette.getVelo(); //Prendre la bornnette  N avec un velo ok et change son etat
            v.setEtat(Etat.HS);
            
           
            entityManager.getTransaction().begin();
            veloRepository.save(v);
            bornetteRepository.save(bornette);
            stationRepository.save(s);
            entityManager.getTransaction().commit();
            
            menuClient(c);
        
        
        }else{
        

        
       
        Location locationEnCours = findLocationPasFini(c);
        if (locationEnCours == null) {
            bornette = bornettesAvecVeloOk.get(LectureClavier.lireEntier(" Choisi une bornnette")); // Prendre la bornette N

            Timestamp heureDebut = new Timestamp(System.currentTimeMillis()); // Heure courante

            locationEnCours = new Location(heureDebut, c, s);
            Velo v = bornette.getVelo(); // Nouvelle location avec l'heure courante et client c
            locationEnCours.addVelos(v);

            v.veloEstLoue();

            c.addLocation(locationEnCours); // Rajout de la location au client
            entityManager.getTransaction().begin();
            locationRepository.save(locationEnCours);
            veloRepository.save(v);
            bornetteRepository.save(bornette);
            stationRepository.save(s);
            entityManager.getTransaction().commit();

        } else {

            bornette = bornettesAvecVeloOk.get(LectureClavier.lireEntier(""));

            Velo v = bornette.getVelo();
            locationEnCours.addVelos(v);
            v.veloEstLoue();
            entityManager.getTransaction().begin();
            locationRepository.save(locationEnCours);
            veloRepository.save(v);
            bornetteRepository.save(bornette);
            stationRepository.save(s);
            entityManager.getTransaction().commit();

        }

    }
    }
    /* 
     *  affiche les Velo en cours de location pour un client et renvoi le velo choisi
     *  
     *  @return un Velo
     */
    Velo choisirVeloEnLocation(Client c, Location l) {

        Location locationClient = l;

        // afficher la liste des velos en location
        int compteur = 0;
        for (Velo velo : locationClient.getVelos()) {
            System.out.println(compteur + ". Velo numero" + velo.getNumero() + " de mod??le " + velo.getModele());
            compteur++;
        }

        int choixVelo;
        do {
            choixVelo = LectureClavier.lireEntier("Saisissez votre choix!");
        } while (choixVelo < 0 || choixVelo > locationClient.getVelos().size());

        // disclaimer for client
        if (locationClient.getVelos().size() - 1 > 0) {
            System.out.println("Il vous reste " + (locationClient.getVelos().size()-1) + " velos ?? rendre!");
            System.out.println("La location ne s'arret pas jusqu'?? vous avez rendu tous les velos lou??s!");
        }

        return locationClient.getVelos().get(choixVelo);
    }
    /* affiche une liste de bornnette libre et renvoi la bornnette choisie 
     *
     * 
     * @return une bornnette
     */
    Bornette choisirBornetteLibre(Station s) {
        Bornette bornette;
        int index = 0;
        List<Bornette> bornettes = new ArrayList<Bornette>();
        for (Bornette b : s.getBornettes()) {
            if (b.getLibre()) { // Si la bornette est libre
                bornettes.add(b);
                System.out.println(index + " - Bornette B" + (index) + " ");
                index++;
            }
        }

        bornette = bornettes.get(LectureClavier.lireEntier("")); // Prendre la bornette N

        return bornette;
    }
    /*
     * Renvoie une location en cours 
     *
     * @return un location
     */
    Location findLocationPasFini(Client c) {

        List<Location> listLocationsClient = c.getLocations();
        Location locationClient = null;
        if (listLocationsClient != null) {
            for (Location location : listLocationsClient) {
                if (location.getVelos().size() > 0) {
                    locationClient = location;
                    break;
                }
            }
        }
        return locationClient;

    }
    /*
     *  Deposer un velo ?? une sation s pour un client c
     * 
     */
    void deposer(Station s, Client c) {
        int codeSecret = LectureClavier.lireEntier("veuillez saisir votre code secret!");
        if (c instanceof Abonne) {
            while (c.getCodeSecret()!=codeSecret) {
                codeSecret=LectureClavier.lireEntier("veuillez saisir votre code secret!");
            }
        } else {
            while (c.getCodeSecret()!=codeSecret) {
                codeSecret=LectureClavier.lireEntier("veuillez saisir votre code secret!");
            }
        }

        // choisir les velos en location
        Location location = findLocationPasFini(c);
        Velo veloChoisi = choisirVeloEnLocation(c, location);
        declarerEtatVelo(veloChoisi);

        // Ici on parcours la liste des bornettes libres
        Bornette bornette = choisirBornetteLibre(s);

        veloChoisi.veloEstRendu(bornette);
        Timestamp heureFin = new Timestamp(System.currentTimeMillis()); // Heure courante

        location.endLocation(s, heureFin, veloChoisi);

        entityManager.getTransaction().begin();
        locationRepository.save(location);
        veloRepository.save(veloChoisi);
        bornetteRepository.save(bornette);
        stationRepository.save(s);
        entityManager.getTransaction().commit();

    }
    /*
     * Change l'etat d'un velo  lors du depot
     *
     */
    void declarerEtatVelo(Velo v) {

        System.out.println("Quel est l'etat actuel du v??lo "+v.getModele()+" que vous voulez d??poser ? ");
        int select = LectureClavier.lireEntier("1 - Bon ??tat\n2 - Mauvais ??tat");
        while (select > 2 || select <= 0) {
            select = LectureClavier.lireEntier("** Tappez 1 Bon ??tat , 2 sinon !! **");

        }
        if (select == 1) {
            System.out.println(" Le velo "+v.getModele()+" est toujours en bon ??tat: "+v.getEtat()+"");
        } else {
            v.setEtat(Etat.HS);
            System.out
                    .println(" Le velo "+ v.getModele() + " est donc actuellement en mauvaise ??tat " + v.getEtat()+"");

        }

    }
    /*
     * Menu principal
     */
    public void mainMenu() {

        int select;
        Client c = null;

        System.out.println("-------- __@      __@       __@       __@      __~@ ");
        System.out.println("----- _`\\<,_    _`\\<,_    _`\\<,_     _`\\<,_    _`\\<,_");
        System.out.println("---- (*)/ (*)  (*)/ (*)  (*)/ (*)  (*)/ (*)  (*)/ (*)");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("        ##################################");
        System.out.println("         \\ \\    / / |  __ (_)    | |   ");
        System.out.println("          \\ \\ / /__| |__) |  ___| | __");
        System.out.println("           \\ \\/ / _ \\  ___/ |/ __| |/ /");
        System.out.println("            \\  /  __/ |   | | (__|   < ");
        System.out.println("             \\/ \\___|_|   |_|\\___|_|\\_\\");
        System.out.println("        ##################################");
        System.out.println("-------- __@      __@       __@       __@      __~@ ");
        System.out.println("----- _`\\<,_    _`\\<,_    _`\\<,_     _`\\<,_    _`\\<,_");
        System.out.println("---- (*)/ (*)  (*)/ (*)  (*)/ (*)  (*)/ (*)  (*)/ (*)");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("");

        do {

            System.out.println("Tapez un des num??ros pour : ");
            System.out.println("1 - S'abonner");
            System.out.println("2 - S'identifier");
            System.out.println("3 - Continuer sans connexion (pour les non abonn??s)");
            System.out.println("4 - Quitter l'application");
            select = LectureClavier.lireEntier("num??ro:");
            System.out.println("");

        } while (select > 4 || select <= 0);

        switch (select) {
            case 1:
                abonner();
                mainMenu();
                break;
            case 2:
                c = identifier();
                menuClient(c);
                break;
            case 3:
                c = continuerSanConnexion();
                menuClient(c);
                break;
            default:
                System.out.println("aurevoir");
                System.exit(0);

                break;
        }

    }

    /*
     * menu client 
     * 
     */
    public void menuClient(Client c) {
        int select;
        Station s = null;
        boolean estEmprunt= true;
        
        if(c instanceof NonAbonne){
        
        
        do {

            System.out.println("Tapez un des num??ros pour : ");
            System.out.println("1 - Eprunter un velo ");
            System.out.println("2 - Deposer un velo");
            System.out.println("3 - Revenir au menu principal");
            select = LectureClavier.lireEntier("num??ro:");
            System.out.println("");

        } while (select > 3 || select <= 0);

        switch (select) {
            case 1:
                s = choisirStation(estEmprunt);
                emprunt(s, c);
                menuClient(c);
                break;
            case 2:
                s = choisirStation(!estEmprunt);
                deposer(s, c);
                menuClient(c);
                break;

            default:
                mainMenu();
                break;
        }

    }else{

        do {
        System.out.println("Tapez un des num??ros pour : ");
        System.out.println("1 - Eprunter un velo ");
        System.out.println("2 - Deposer un velo");
        System.out.println("3 - Consulter  l'historique des locations ");
        System.out.println("4 - Revenir au menu principal");
        
        select = LectureClavier.lireEntier("num??ro:");
        System.out.println("");

        }while (select > 4 || select <= 0);

        switch (select) {
            case 1:
                s = choisirStation(estEmprunt);
                emprunt(s, c);
                menuClient(c);
                break;
            case 2:
                s = choisirStation(!estEmprunt);
                deposer(s, c);
                menuClient(c);
                break;
            case 3:
                consulterHistoriqueDesLocations(c);
                menuClient(c);
                break;
              
            case 4:
            mainMenu();
            break;

                
        }


    }


    }



}

