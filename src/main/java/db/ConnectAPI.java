package db;


import java.net.HttpURLConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ConnectAPI {
    private String codeP;
    private String codeD;
    private  JSONArray dataProvince;
    private  JSONArray dataDistricts;
    private JSONArray dataWard;


    private ObservableList<String> dsTinh;
    private  ObservableList<String> dsHuyen;
    private ObservableList<String> dsPhuong;

    public ConnectAPI(){
        dsTinh = danhSachTinh();
        dataDistricts = danhSachHuyen();
        dataWard = danhSachDuong();

    }

    public ObservableList<String> getDsTinh(){
        return dsTinh;
    }

    public ObservableList<String> getDsHuyen(String tenTinh){
        ArrayList<String> dsHuyenList = new ArrayList<>();
        String codeTinh = getCodeProvince(tenTinh);
        System.out.print(codeTinh);
        for (Object it:
                dataDistricts) {
            JSONObject tmp = (JSONObject) it;
            if (tmp.get("province_code").toString().equals(codeTinh)) {
                dsHuyenList.add(tmp.get("name").toString());
            }
        }
        dsHuyen = FXCollections.observableArrayList(dsHuyenList);
        return dsHuyen;
    }

    public ObservableList<String> getDuong(String tenHuyen){
        ArrayList<String> dsDuongList = new ArrayList<>();
        String codeHuyen = getCodeDistrict(tenHuyen);
        for (Object it:
                dataWard) {
            JSONObject tmp = (JSONObject) it;
            if (tmp.get("district_code").toString().equals(codeHuyen))
                dsDuongList.add(tmp.get("name").toString());

        }
        dsPhuong = FXCollections.observableArrayList(dsDuongList);
        return dsPhuong;
    }

    private ObservableList<String> danhSachTinh() {
        ArrayList<String> listTinh = new ArrayList<>();
        try {
            URL url = new URL("https://provinces.open-api.vn/api/p/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();


                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parse = new JSONParser();
                dataProvince = (JSONArray) parse.parse(String.valueOf(informationString));

                //Get the first JSON object in the JSON array
                System.out.println(dataProvince.get(0));

                JSONObject countryData = (JSONObject) dataProvince.get(0);
                countryData.get("name");

                for (Object it :
                        dataProvince) {
                    JSONObject data = (JSONObject) it;
                    listTinh.add(data.get("name").toString());
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(listTinh);
    }

    private JSONArray danhSachHuyen() {
        dataDistricts = new JSONArray();
        try {
            URL url = new URL("https://provinces.open-api.vn/api/d/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();


                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parse = new JSONParser();
                dataDistricts = (JSONArray) parse.parse(String.valueOf(informationString));


            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return dataDistricts;

    }

    private JSONArray danhSachDuong() {
        dataWard = new JSONArray();
        try {
            URL url = new URL("https://provinces.open-api.vn/api/w/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();


                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parse = new JSONParser();
                dataWard = (JSONArray) parse.parse(String.valueOf(informationString));

            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return dataWard;
    }

    public String getCodeProvince(String tenTinh){
        for (Object it :
                dataProvince) {
            JSONObject data = (JSONObject) it;
           if (data.get("name").equals(tenTinh))
               return  data.get("code").toString();
        }
        return null;
    }

    public String getCodeDistrict(String tenHuyen){
        for (Object it :
                dataDistricts) {
            JSONObject data = (JSONObject) it;
            String nameDis = data.get("name").toString();
            if (nameDis.equals(tenHuyen)){
                return data.get("province_code").toString();
            }
        }
        return null;
    }


}

