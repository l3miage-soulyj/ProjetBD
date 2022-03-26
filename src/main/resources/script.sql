//Station :
INSERT INTO Station VALUES (1, 'Cours Gambetta');
INSERT INTO Station VALUES (2, 'Grande rue de la Guillotière');
INSERT INTO Station VALUES (3, 'Quai Claude Bernard');

//Bornette :
INSERT INTO Bornette  (NUMEROB , ETATB, LIBRE, STATION_IDSTATION) VALUES (1, 'OK', 0, 1);
INSERT INTO Bornette (NUMEROB , ETATB, LIBRE, STATION_IDSTATION) VALUES (2, 'OK', 1, 1); 
INSERT INTO Bornette (NUMEROB , ETATB, LIBRE, STATION_IDSTATION) VALUES (3, 'OK', 0, 2); 
INSERT INTO Bornette (NUMEROB , ETATB, LIBRE, STATION_IDSTATION) VALUES (4, 'OK', 1, 2); 
INSERT INTO Bornette (NUMEROB , ETATB, LIBRE, STATION_IDSTATION) VALUES (5, 'OK', 0, 3); 
INSERT INTO Bornette (NUMEROB , ETATB, LIBRE, STATION_IDSTATION) VALUES (6, 'OK', 1, 3); 
INSERT INTO Bornette (NUMEROB , ETATB, LIBRE, STATION_IDSTATION)  VALUES (7, 'OK', 0, 3,);

//Velo :
INSERT INTO Velo VALUES (1, '17/03/2022' , 'OK' , 'HOLLANDAIS', 'EN_STATION', 1);
INSERT INTO Velo (NUMEROV, DATEMISEENSERVICE, ETATV, MODELEV, SITUATION) VALUES (2, '17/03/2022' , 'OK' , 'VTT', 'EN_LOCATION'); 
INSERT INTO Velo VALUES (3, '20/03/2022' , 'HS' , 'VTT', 'EN_STATION', 3);
INSERT INTO Velo (NUMEROV, DATEMISEENSERVICE, ETATV, MODELEV, SITUATION) VALUES (4, '15/03/2022' , 'HS' , 'HOLLANDAIS', 'EN_LOCATION'); 
INSERT INTO Velo VALUES (5, '20/03/2022' , 'OK', 'VTC', 'EN_STATION', 5);
INSERT INTO Velo (NUMEROV, DATEMISEENSERVICE, ETATV, MODELEV, SITUATION) VALUES (6, '22/03/2022' , 'OK' , 'VTC', 'EN_LOCATION'); 
INSERT INTO Velo VALUES (7, '20/03/2022' , 'OK' , 'VTT', 'EN_STATION', 7);

//Modification de Bornette :
UPDATE Bornette 
SET VELO_NUMEROV = 1 
WHERE NUMEROB = 1;
UPDATE Bornette 
SET VELO_NUMEROV = 3 
WHERE NUMEROB = 3;
UPDATE Bornette 
SET VELO_NUMEROV = 5
WHERE NUMEROB = 5;
UPDATE Bornette 
SET VELO_NUMEROV = 7
WHERE NUMEROB = 7;

//Station_Bornette :
INSERT INTO Station_Bornette VALUES (1,1);
INSERT INTO Station_Bornette VALUES (1,2);
INSERT INTO Station_Bornette VALUES (2,3);
INSERT INTO Station_Bornette VALUES (2,4);
INSERT INTO Station_Bornette VALUES (3,5);
INSERT INTO Station_Bornette VALUES (3,6);
INSERT INTO Station_Bornette VALUES (3,7);

//Creneau :
INSERT INTO Creneau VALUES (1, TO_TIMESTAMP('12:00:00', 'HH24:MI:SS'),TO_TIMESTAMP('20:00:00', 'HH24:MI:SS'), 'PLUS', 1);
INSERT INTO Creneau VALUES (2, TO_TIMESTAMP('12:00:00', 'HH24:MI:SS'),TO_TIMESTAMP('20:00:00', 'HH24:MI:SS'), 'MOINS', 1);
INSERT INTO Creneau VALUES (3, TO_TIMESTAMP('20:00:00', 'HH24:MI:SS'),TO_TIMESTAMP('08:00:00', 'HH24:MI:SS'), 'NUL', 1);
INSERT INTO Creneau VALUES (4, TO_TIMESTAMP('08:00:00', 'HH24:MI:SS'),TO_TIMESTAMP('08:00:00', 'HH24:MI:SS'), 'PLUS', 2);
INSERT INTO Creneau VALUES (5, TO_TIMESTAMP('08:00:00', 'HH24:MI:SS'),TO_TIMESTAMP('08:00:00', 'HH24:MI:SS'), 'MOINS', 3);

//Station_Creneau :
INSERT INTO Station_Creneau VALUES (1,1);
INSERT INTO Station_Creneau VALUES (1,2);
INSERT INTO Station_Creneau VALUES (1,3);
INSERT INTO Station_Creneau VALUES (2,4);
INSERT INTO Station_Creneau VALUES (3,5);

//Client :
INSERT INTO Client VALUES (1, 2000, '14141414567');
INSERT INTO Client VALUES (2, 1999, '14120004567');
INSERT INTO Client VALUES (3, 1998, '14119974567');
INSERT INTO Client VALUES (4, 1997, '14117654567');

//Abonne :
INSERT INTO Abonne VALUES ('6 rue jean jaures', '01/01/2022', '01/01/2023', '27/07/2000', 'HAULT', 'Jean', 'MALE', 1);
INSERT INTO Abonne VALUES ('7 place de la liberté', '10/03/2022', '10/03/2023', '06/01/1999', 'BERACHE', 'Kamila', 'FEMELLE', 2);

//NonAbonne :
INSERT INTO NonAbonne VALUES (3);
INSERT INTO NonAbonne VALUES (4);

//Location : 
INSERT INTO LOCATION VALUES (1,4,TO_TIMESTAMP('12:00:00', 'HH24:MI:SS'),TO_TIMESTAMP('12:15:00', 'HH24:MI:SS'),1,2,1);
INSERT INTO LOCATION VALUES (2,5,TO_TIMESTAMP('20:00:00', 'HH24:MI:SS'),TO_TIMESTAMP('20:24:00', 'HH24:MI:SS'),2,3,2);
INSERT INTO LOCATION VALUES (3,2,TO_TIMESTAMP('11:35:00', 'HH24:MI:SS'),TO_TIMESTAMP('11:45:00', 'HH24:MI:SS'),3,2,1);
INSERT INTO LOCATION VALUES (4,6,TO_TIMESTAMP('23:00:00', 'HH24:MI:SS'),TO_TIMESTAMP('23:20:00', 'HH24:MI:SS'),4,3,1);

//Client_Location :
INSERT INTO Client_Location VALUES (1,1);
INSERT INTO Client_Location VALUES (2,2);
INSERT INTO Client_Location VALUES (3,3);
INSERT INTO Client_Location VALUES (4,4);

//Location_Velo :
INSERT INTO Location_Velo VALUES (1,2);
INSERT INTO Location_Velo VALUES (2,4);
INSERT INTO Location_Velo VALUES (3,6);
INSERT INTO Location_Velo VALUES (4,7);
