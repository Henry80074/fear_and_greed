package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.lang.Float.parseFloat;

public class InsertIntoFearAndGreed {
    public static void main(String[] args ){
        Connection c = null;
        Statement stmt = null;
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/fear_and_greed",
                            "postgres", password.password);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            //stmt = c.createStatement();

            Reader fileReader = new FileReader("C:\\Users\\3henr\\IdeaProjects\\crypto_fear_and_greed\\output.json");

            jsonObject = (JSONObject) parser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");

            System.out.println(jsonArray);
            System.out.println(jsonArray.size());

            String sql = "INSERT INTO fear_greed_index VALUES (?, ?, ?, ?) ON CONFLICT (timestamp) " +
                    "DO NOTHING";

            //  iterate through array object to get key and values,  then parse into postgresql

            Iterator<Object> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
               JSONObject object = (JSONObject) iterator.next();
               PreparedStatement pstmt = c.prepareStatement(sql);
                //pstmt.setString(1, pk);
                String s_num = (String) object.get("timestamp");
                String true_number = s_num + "000";
                pstmt.setString(1, (String) true_number);
                pstmt.setInt(2, Integer.parseInt((String) object.get("value")));
                pstmt.setFloat(3, 0.0F);
                pstmt.setString(4, (String) object.get("value_classification"));
                pstmt.executeUpdate();
                System.out.println(pstmt);
            }



            Reader fileReader2 = new FileReader("C:\\Users\\3henr\\IdeaProjects\\crypto_fear_and_greed\\bitcoin_price_chart.json");

            jsonObject = (JSONObject) parser.parse(fileReader2);
            JSONArray jsonArray2 = (JSONArray) jsonObject.get("prices");
            ArrayList<String> list = new ArrayList<String>();
            if (jsonArray2 != null) {
                int len = jsonArray2.size();
                for (int i=0;i<len;i++){
                    list.add(jsonArray2.get(i).toString());
                }
            }
            System.out.println(list);

            String sql2 = "Update fear_greed_index SET price = ? WHERE timestamp = ?";

            //  iterate through array object to get key and values,  then parse into postgresql
            for(int i=0;i<=(jsonArray2.size()-1);i++){
                PreparedStatement pstmt = c.prepareStatement(sql2);
                String temp_list = list.get(i).replaceAll("\\[|\\]", "");
                List<String> items = Arrays.asList(temp_list.split("\\s*,\\s*"));
                pstmt.setFloat(1, parseFloat(items.get(1)));
                pstmt.setString(2, (String) items.get(0));
                pstmt.executeUpdate();
                System.out.println(pstmt);
            }
            String sql3 = "DELETE FROM fear_greed_index WHERE price = 0"; //be warned, possible that some useful data is deleted (dated feb 2018)
            PreparedStatement pstmt = c.prepareStatement(sql3);
            pstmt.executeUpdate();
            c.commit();
            c.close();

        } catch (ParseException e) {
            System.out.println("err");
           e.printStackTrace();
       } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("info inserted successfully");
    }
}
