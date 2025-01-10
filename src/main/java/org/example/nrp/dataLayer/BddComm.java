package org.example.nrp.dataLayer;

import org.example.nrp.functionalLayer.CarteRestaurant;
import org.example.nrp.functionalLayer.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BddComm {
    private final static Connection con = connect();
    private final static Statement executer = createSt();

    //
//    public static List<CarteRestaurant> getCRest_(){
//        // a function that return a list of all cards from the database
//        List<CarteRestaurant> cloud = new ArrayList<CarteRestaurant>();
//        Etudiant ana = new Etudiant(
//                "Youssef",
//                "haddadi",
//                Niveau.INE1,
//                Filiere.ASEDS,
//                null
//        );
//        Etudiant nta = new Etudiant(
//                "Mohammed Mehdi",
//                "Bahri",
//                Niveau.INE1,
//                Filiere.ASEDS,
//                null
//        );
//
//        Etudiant nti = new Etudiant(
//                "Khadija",
//                "Ouanour",
//                Niveau.INE1,
//                Filiere.ASEDS,
//                null
//        );
//
//
//        CarteRestaurant carte_ana = new CarteRestaurant(
//            ana,
//            300,
//            true
//        );
//
//        CarteRestaurant carte_nta = new CarteRestaurant(
//                nta,
//                300,
//                true
//        );
//
//        CarteRestaurant carte_nti = new CarteRestaurant(
//                nti,
//                300,
//                true
//        );
//
//        CarteRestaurant carte_ana1 = new CarteRestaurant(
//                ana,
//                300,
//                false
//        );
//
//        ana.setCarte(carte_ana);
//        nta.setCarte(carte_nta);
//        nti.setCarte(carte_nti);
//
//
//        cloud.add(carte_ana);
//        cloud.add(carte_nta);
//        cloud.add(carte_nti);
//        cloud.add(carte_ana1);
//
//        return cloud;
//    }

    public static List<CarteRestaurant> getCRest(){
        List<CarteRestaurant> lc = new ArrayList<>();
        ResultSet rs = execute("SELECT * \n" +
                "FROM Beta_cards \n" +
                "JOIN Beta_have_card ON Beta_cards.id = Beta_have_card.idC\n" +
                "JOIN Beta_students ON Beta_have_card.idS = Beta_students.id;");
        try{
            while(rs.next()){
                CarteRestaurant crest = createCarte(rs);
                lc.add(crest);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }


        return lc;

    }

    public static void main(String[] args) {
        try{

        printResultSet(
                execute("SELECT * \n" +
                        "FROM Beta_cards \n" +
                        "JOIN Beta_have_card ON Beta_cards.id = Beta_have_card.idC\n" +
                        "JOIN Beta_students ON Beta_have_card.idS = Beta_students.id;")
        );
        }catch (SQLException  e){
            System.out.println(e.getMessage());
        }
    }

    public static Connection connect(){
        // connect to the database and create a conn object
        String url = "jdbc:mysql://localhost:3306/NRest";
        String username = "root";
        String password = "";
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        }catch(SQLException e){
            return null;
        }
    }

    public static CarteRestaurant createCarte(ResultSet rs) throws SQLException{
        CarteRestaurant carte = new CarteRestaurant();
        Etudiant ana = new Etudiant();

        carte.setE(ana);
        ana.setCarte(carte);

        carte.setCode(
                Integer.parseInt(rs.getString("idC"))
        );
        carte.setSolde(
                Integer.parseInt(rs.getString("solde"))
        );
        carte.setActive(
                rs.getString("is_active").equals("1") ? true : false
        );
        ana.setCode(
                Integer.parseInt(rs.getString("idS"))
        );
        ana.setNom(rs.getString("nom"));
        ana.setPrenom(rs.getString("prenom"));
        ana.setNiveau(rs.getString("niveau"));
        ana.setFiliere(rs.getString("filiere"));

        return carte;
    }

    public static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Print column headers
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + "\t");
        }
        System.out.println();

        // Print rows
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            System.out.println();
        }
    }

    public static ResultSet execute(String query) {
        // execute the given query and give the result as a List
        if(executer == null) return null;

        try{
            executer.execute(query);
            ResultSet rs = executer.getResultSet();
            return rs;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Statement createSt(){
        try{
            Statement st = con.createStatement();
            return st;
        }catch(SQLException|NullPointerException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static void close(){
        try {
            executer.close();
            con.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

}
