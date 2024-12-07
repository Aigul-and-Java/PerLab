import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;


    public class task3 {
        public static void main(String[] args) throws IOException, ParseException {

            String valuesPath = args[0];
            String testsPath = args[1];
            String reportPath = args[2];

            JSONParser parser = new JSONParser();
            JSONObject valuesData = new JSONObject();
            JSONObject testsData = new JSONObject();

            try {
                // считаем наши JSON файлы
                valuesData = (JSONObject) parser.parse(new FileReader(valuesPath));
                ;
                testsData = (JSONObject) parser.parse(new FileReader(testsPath));

            } catch (IOException | ParseException e) {
                System.out.println("Ошибка" + e.getMessage());
            }
            setter(valuesData, testsData, reportPath);
        }

        private static void setter(JSONObject valuesData, JSONObject testsData, String reportPath) throws IOException, ParseException {


            HashMap<Long, String> results = new HashMap<>();

            JSONArray valuesArray = (JSONArray) valuesData.get("values");
            for (Object valueObj : valuesArray) {
                JSONObject value = (JSONObject) valueObj;
                results.put((Long) value.get("id"), (String) value.get("value"));
            }


            JSONObject reportData;
            try (FileWriter file = new FileWriter(reportPath)) {
                file.write(testsData.toJSONString());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }


            JSONParser parser = new JSONParser();
            reportData = (JSONObject) parser.parse(new FileReader(reportPath));


            reportData.replace("tests", arrayParser(reportData.get("tests"), results));


            try (FileWriter file = new FileWriter(reportPath)) {
                file.write(reportData.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static JSONArray arrayParser(Object values, HashMap<Long, String> results) {

            JSONArray helper = (JSONArray) values;

            for (int i = 0; i < helper.size(); i++) {
                JSONObject test = (JSONObject) helper.get(i);

                if (test.containsKey("values")) {
                    test.replace("values", arrayParser(test.get("values"), results));
                }

                if (test.containsKey("value") && test.containsKey("id") && results.containsKey(test.get("id"))) {
                    test.replace("value", results.get(test.get("id")));
                }
            }
            return helper;
        }
    }
