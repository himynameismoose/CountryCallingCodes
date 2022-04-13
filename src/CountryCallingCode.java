import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CountryCallingCode {
    /**
     * API URL: https://jsonmock.hackerrank.com/api/countries?name=<country>
     */
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("What is the country you need to call? ");
        String country = scanner.next();
        System.out.println();
        System.out.print("What is the phone number? ");
        String phoneNumber = scanner.next();


        String result = getCallingCode(country, phoneNumber);

        System.out.println(result);
    }

    public static String getCallingCode(String country, String phoneNumber) throws IOException {
        URL url = new URL("https://jsonmock.hackerrank.com/api/countries?name=" + country);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        String callingCode = "";

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);

            JsonObject co = new Gson().fromJson(response.toString(), JsonObject.class);
            JsonArray data = co.getAsJsonArray("data");

            for (int i = 0; i < data.size(); i++) {
                callingCode = data.get(i).getAsJsonObject().get("callingCodes").getAsString();
            }
        }

        in.close();
        con.disconnect();

        if (callingCode.equals(""))
            return "-1";
        else
            return "+" + callingCode + " " + phoneNumber;
    }
}
