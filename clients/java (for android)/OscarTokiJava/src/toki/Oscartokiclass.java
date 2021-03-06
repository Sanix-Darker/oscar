/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 ***********************************************************************************
 * Oscar-Toki Class .
 * This Java class example  take the clientkey and generate a toki that will change
 * every minute to securize communications betwen clients and clients.
 * between Client -> Client and between Client -> Client.
 */
package toki;

//import for time cheking
import java.util.Date;

// Imports for hash
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 *
 * @author Sanix-darker
 */
public class Oscartokiclass {

  private String clientkey;
  private Boolean debugMode = false;
  private String toki;
  private String peerURL = "";
  private String oscarURL = "";
  private String appName = "";
  private final Integer lifetime_of_toki = 5; // not in millisecond or second just the difference between 9 first digits
  // of
  // timestamp
  // By assimillation its about 40seconds before the toki will not be valid
  // anymore

  /**
   * A Setter for the clientkey
   * 
   * @param m
   */
  public void setClientkey(String m) {
    // set passed parameter as name
    this.clientkey = m;
  }

  /**
   * A getter for the clientkey param
   * 
   * @return
   */
  public String getClientkey() {
    // return the set name
    return this.clientkey;
  }

  /**
   * A Setter for the peerURL
   * 
   * @param p
   */
  public void setPeerURL(String p) {
    // set passed parameter as name
    this.peerURL = p;
  }

  /**
   * A getter for the peerURL param
   * 
   * @return
   */
  public String getPeerURL() {
    // return the set name
    return this.peerURL;
  }

  /**
   * A Setter for the peerURL
   * 
   * @param o
   */
  public void setOscarURL(String o) {
    // set passed parameter as name
    this.oscarURL = o;
  }

  /**
   * A getter for the peerURL param
   * 
   * @return
   */
  public String getOscarURL() {
    // return the set name
    return this.oscarURL;
  }

  /**
   * A Setter for the clientkey
   * 
   * @param a
   */
  public void setAppName(String a) {
    // set passed parameter as name
    this.appName = a;
  }

  /**
   * A getter for the clientkey param
   * 
   * @return
   */
  public String getAppName() {
    // return the set name
    return this.appName;
  }

  /**
   * This method is a getter for the current valid toki.
   * 
   * @return
   */
  public String getToki() {
    // return the set name
    return this.toki;
  }

  /**
   * This
   * 
   * @param t
   */
  public void setToki(String t) {
    // return the set name
    this.toki = t;
  }

  /**
   * This method is a getter for the current valid toki.
   * 
   * @return
   */
  public Boolean getDebugmode() {
    // return the set name
    return this.debugMode;
  }

  /**
   * This
   * 
   * @param d
   */
  public void setDebugmode(Boolean d) {
    // return the set name
    this.debugMode = d;
  }

  public void tokiPrint(Object toprintt) {
    if (this.debugMode == true) {
      System.out.println("[ DEBUG-OSCARTOKI ] > " + toprintt);
    }
  }

  public StringBuilder reverseString(String toreverse) {
    return new StringBuilder(toreverse).reverse();
  }

  public String getSHA(String input) {

    try {

      // Static getInstance method is called with hashing SHA
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      // digest() method called
      // to calculate message digest of an input
      // and return array of byte
      byte[] messageDigest = md.digest(input.getBytes());
      // Convert byte array into signum representation
      BigInteger no = new BigInteger(1, messageDigest);
      // Convert message digest into hex value
      String hashtext = no.toString(16);

      while (hashtext.length() < 32) {
        hashtext = "0" + hashtext;
      }
      return hashtext;
    }

    // For specifying wrong message digest algorithms
    catch (NoSuchAlgorithmException e) {
      System.out.println("Exception thrown" + " for incorrect algorithm: " + e);

      return null;
    }
  }

  /**
   * This method is the generator of the toki.
   * 
   */
  public void generateToki() {
    tokiPrint("Starting Generation of the Toki.");

    // Let manae about the time
    Date date = new Date();
    long time = date.getTime();
    String timeAsString = Long.toString(time);

    if (this.clientkey.length() > 5) {
      // Now let manage about the core of the toki
      String TokiCore = getSHA(getSHA(this.clientkey + " - " + timeAsString + " - " + this.clientkey));

      String brouillage = getSHA(timeAsString + "Brouillage de piste").substring(0, 15);
      // System.out.println("Time in Milliseconds: " + time);
      // get first 9 digit of the time
      String toki_generated = this.reverseString(timeAsString) + "|" + this.reverseString(TokiCore) + "|" + brouillage;

      tokiPrint("Toki generated: " + toki_generated);

      setToki(toki_generated);

    } else {
      System.out.println("Some Error Occurs When Generating the toki, please verify the clientKey you provided.");
    }

  }

  /**
   * This method verify a lamda toki and return true or false if it's good or not
   * 
   * @param toki
   * @return
   */
  public Boolean verifyToki(String toki) {
    // 9 [0, 8] premiers chiffres du timestamp s'il y'a une difference de 30

    Date date = new Date();
    long current_time = date.getTime();
    String current_time_string = String.valueOf(current_time);
    current_time = Long.parseLong(current_time_string.substring(0, 9), 10);

    if (toki.length() > 5) {

      String[] arrOfElt = toki.split("\\|", -1);
      String get_toki_time = arrOfElt[0];
      StringBuilder get_toki_time_sb = this.reverseString(get_toki_time);
      long toki_time_tocompare = Long.parseLong(get_toki_time_sb.toString().substring(0, 9), 10);
      long original_toki_time = Long.parseLong(get_toki_time_sb.toString(), 10);

      tokiPrint("Starting Verification.");
      tokiPrint("current_time: " + current_time);
      tokiPrint("toki_time_tocompare: " + toki_time_tocompare);

      // S'il y'a une difference de plus de 100seconde, le toki n'est plus valide
      if (current_time - toki_time_tocompare > lifetime_of_toki) {

        tokiPrint("Toki is too old!");
        tokiPrint("Toki is invalid!");
        return false;

      } else {
        tokiPrint("Toki is in time!");

        String TokiCore = getSHA(getSHA(this.clientkey + " - " + original_toki_time + " - " + this.clientkey));

        String reComputed = this.reverseString(TokiCore).toString();
        String given = arrOfElt[1];

        tokiPrint("Toki given: '" + given + "'");
        tokiPrint("Toki reComputed: '" + reComputed + "'");

        if (given.equals(reComputed)) {
          tokiPrint("Toki is valid!");
          return true;
        } else {
          tokiPrint("Toki is invalid!");
          return false;
        }

      }

    } else {
      System.out.println("There is an error for the toki you have sent, maybe it's not Valid, please Check it again!");
      return false;
    }

  }

  public void pingPuceToOscar() throws MalformedURLException, ProtocolException, IOException {

    this.tokiPrint("In pingPuceToOscar!");

    URL url = new URL(this.getOscarURL() + "/api/v1/puces");
    Map<String, Object> params = new LinkedHashMap<>();
    params.put("app_name", this.appName);
    params.put("client", this.clientkey);

    StringBuilder postData = new StringBuilder();
    for (Map.Entry<String, Object> param : params.entrySet()) {
      if (postData.length() != 0)
        postData.append('&');
      postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
      postData.append('=');
      postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
    }
    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
    conn.setDoOutput(true);
    conn.getOutputStream().write(postDataBytes);

    Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    String response = "";
    for (int c; (c = in.read()) >= 0;)
      response += (char) c;

    // print result
    this.tokiPrint("Response: " + response);
  }

  /**
   * Take the toki in param and generate a uniue queue
   * 
   * @param toki
   * @return
   */
  public String generateTokiKey(String toki) {

    if (this.verifyToki(toki) == true) {

      Date date = new Date();
      long current_time = date.getTime();
      String current_time_string = String.valueOf(current_time);
      String timeAsString = current_time_string.substring(0, 7);

      this.tokiPrint("In generateTokiKey, TokiKey generated successfully!");

      return this.getSHA(this.getSHA(timeAsString + this.getSHA(toki) + this.getClientkey())) + "o_o"
          + this.reverseString(timeAsString);

    } else {

      this.tokiPrint("In generateTokiKey, Toki is invalid!");
      return "IMVALID TOKI";

    }

  }

  /**
   * This method take the toki and the kwy in param and anayse it to verify it's a
   * good key
   * 
   * @param toki
   * @param key
   * @return
   */
  public Boolean verifyTokiKey(String toki, String key) {

    if (this.verifyToki(toki) == true) {

      try {
        String timeAsString = this.reverseString(key.split("o_o")[1]).toString();

        String recomputeKey = this.getSHA(this.getSHA(timeAsString + this.getSHA(toki) + this.getClientkey())) + "o_o"
            + this.reverseString(timeAsString);

        this.tokiPrint("In verifyTokiKey!");
        this.tokiPrint("TokiKey: " + key);
        this.tokiPrint("TokiKey Recompute: " + recomputeKey);

        if (recomputeKey.equals(key)) {

          this.tokiPrint("In verifyTokiKey, TokiKey is valid!");
          return true;

        } else {

          this.tokiPrint("In verifyTokiKey, TokiKey is invalid!");
          return false;

        }
      } catch (Exception e) {
        this.tokiPrint("Unknow Error occur on the response, try again!");
        return false;
      }

    } else {

      this.tokiPrint("In verifyTokiKey, TokiKey is invalid!");
      return false;

    }

  }

  /**
   * A Simple request that send first a valid Toki, then get a key generated by
   * the peer and return it to be send again with the toki So that both
   * authentificated each other and exchange informations
   * 
   * @return
   * @throws java.lang.Exception
   */

  public String[] checkToki() throws Exception {

    String url = this.peerURL;

    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    // optional default is GET
    con.setRequestMethod("GET");

    // add request header
    con.setRequestProperty("oscartoki", this.getToki());

    int responseCode = con.getResponseCode();
    this.tokiPrint("\nToki sending the Request===================================");
    this.tokiPrint("\nSending 'GET' request to URL : " + url);
    this.tokiPrint("Response Code : " + responseCode);

    StringBuffer response;
    try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
      String inputLine;
      response = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
    }

    // We get the response
    String result_ = response.toString();

    // print result
    this.tokiPrint("Response: " + result_);

    // We verify the key respond by the peer
    // Here the code need externals lib to proceed, and i did'nt want to add it, to
    // not force the utilisation of a specific lib by a lamda developper on a lambda
    // project,
    // SOOOO, it's really simple what you have to do down here
    // Down there is the algorithm to proceed:

    // 1 - We parse the response.toString() from the http GET request to obtain an
    // JSON object to easyly extract tokikey.
    // 2- That's all you have to do here, so use the librairy you want to implement
    // the JSON parse.
    // 3- Please contact the Author of OscarToki if there is a problem.
    // [Sanix-darker]

    // JSON Message readed from the hard disk
    JSONObject ResponseJson = new JSONObject(result_);

    String tokikey = "";
    if (ResponseJson.has("tokikey")) {
      tokikey = ResponseJson.getString("tokikey");
      this.tokiPrint("TokiKey getted: " + tokikey);
    }

    // Replace the value Parsed from the request here (the tokikey).
    String[] array_to_return = new String[2];

    if (this.verifyTokiKey(this.getToki(), tokikey) == true) {

      this.tokiPrint("TokiKey receive on check is valid!");
      array_to_return[0] = "true";
      array_to_return[1] = tokikey;

    } else {

      this.tokiPrint("TokiKey receive on check is invalid, please try a new request!");
      array_to_return[0] = "false";
      array_to_return[1] = "TokiKey receive on check is invalid!";
    }

    return array_to_return;
  }

  // An example of using Oscar-Toki
  // main method will be called first when program is executed
  public static void main(String args[]) throws Exception {

    // Instantiate the OscarToki
    Oscartokiclass OscarToki = new Oscartokiclass();
    // You can set the debug mode here:
    // It will print what is done during the compilation of the toki or the
    // verification
    // You can turn it off by setDebugmode to false
    OscarToki.setDebugmode(true);

    /** EXAMPLE FOR THE GENERATION PROCESS TO ADD IN HEADER OFF EACH REQUESTS */
    System.out.println("# ------------------------------------------------------");
    System.out.println("# OscarToki Generation started--------------------------");
    System.out.println("# ------------------------------------------------------");
    // It's important to read this variable fomr a config file and not to
    // put it hard in the code like this[FOR SECURITY], it's just a quick example

    String Example_clientkey = "5cbc654dc3355e382201615c";
    // set name member of this object
    OscarToki.setClientkey(Example_clientkey);

    String appName = "defaultApp";
    // set name member of this object
    OscarToki.setAppName(appName);

    String peerURL = "http://127.0.0.1:1234";
    // set name member of this object
    OscarToki.setPeerURL(peerURL);

    String oscarURL = "http://127.0.0.1:5991";
    // set name member of this object
    OscarToki.setOscarURL(oscarURL);

    // Generate the Toki
    OscarToki.generateToki();
    // print the name
    System.out.println("Clientkey: '" + OscarToki.getClientkey() + "'");
    System.out.println("Toki: '" + OscarToki.getToki() + "'");

    System.out.print("\n");

    /** EXAMPLE FOR VEFY THE TOKI RECEIVED */
    System.out.println("# -------------------------------------------------------------------");
    System.out.println("# OscarToki Verification process started-----------------------------");
    System.out.println("# -------------------------------------------------------------------");

    String Example_toki = OscarToki.getToki();
    // Dans la verification, si le timestamp est depasser de 100, ce n'est plus
    // valide
    System.out.println("Example_toki: '" + Example_toki + "'");

    String[] result = OscarToki.checkToki();
    String checktoki_status = (String) result[0];
    String checktoki_response = (String) result[1];

    if ("true".equals(checktoki_status)) {

      // let's ping Oscar about this exchange
      OscarToki.pingPuceToOscar();

      OscarToki.tokiPrint("Stating tokiCheck...");

      String oscartokikey = checktoki_response;
      OscarToki.tokiPrint("oscartokikey: " + oscartokikey);

      // Now you have the Key (toki and you can request with that now by adding it in
      // headers)
      // Your requested url (http://mira....)
      String url = OscarToki.getPeerURL();

      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      // optional default is GET
      con.setRequestMethod("GET");
      // add request header
      con.setRequestProperty("oscartoki", OscarToki.getToki());
      con.setRequestProperty("oscartokikey", oscartokikey);
      int responseCode = con.getResponseCode();

      StringBuffer response;
      try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
        String inputLine;
        response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
      }
      // print result
      OscarToki.tokiPrint("Response: " + response.toString());

      ///////////////////////////////////////////////////////////////////////
      ////// --------------------------------------------------------------//
      ////// DO YOUR STUFF HERE CUZ THE TOKI AND IT's KEY ARE BOTH VALIDS //
      ////// --------------------------------------------------------------//
      ///////////////////////////////////////////////////////////////////////

    } else {

      // Let's print the error
      OscarToki.tokiPrint("Error: " + checktoki_response);

    }

  }
}
