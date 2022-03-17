package fr.uga.im2ag.l3.miage.db.repository;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import fr.uga.im2ag.l3.miage.db.model.Abonne;
import fr.uga.im2ag.l3.miage.db.model.Enums;

public class Fixtures {
    private static final Faker FAKER = Faker.instance(new Random(42));

    public static Date convertDate(String dateString) throws ParseException{
        return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime());
    }

    public static Enums.sexe getRandomSexe(){
        Enums.sexe sexe;
        sexe = Enums.sexe.values()[new Random().nextInt(3)];
        return sexe;
    }

    public static Abonne createAbonne(Date dateAbonnement){
        Abonne newAbonne = new Abonne()
        .setPrenom(FAKER.funnyName().name())
        .setNom(FAKER.gameOfThrones().character())
        .setAdresse(FAKER.address().fullAddress())
        .setDateNaissance(new java.sql.Date(FAKER.date().past(60 * 365, 30 * 365, TimeUnit.DAYS).getTime()))
        .setSexe(getRandomSexe())
        .setDateDebut(dateAbonnement);

        newAbonne.setCodeSecret(9999);
        return newAbonne;
            // to do
            // code secret generator
            // .setCodeSecret(new Random().nextInt(arg0));
    }
}