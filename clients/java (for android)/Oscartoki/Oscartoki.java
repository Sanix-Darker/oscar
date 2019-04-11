/*
* Oscar-Toki Class .
* This Java class example  take the microservicekey and generate a toki that will change
* every minute to securize communications betwen clients and microservices.
* between Client -> Microservice and between Microservice -> Client.
*/

//import for time cheking
import java.util.Date;

// Imports for hash
import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

public class Oscartoki{

    private String microservicekey;
    private Boolean debugMode = false;
    private String toki;
    private Integer lifetime_of_toki = 3; // not in millisecond or second just the difference between 9 first digits of timestamp
                                          // By assimillation its about 40seconds before the toki will not be valid anymore

    /**
     * A Setter for the microservicekey
     * @param a
     */
    public void setMicroservicekey(String m){
      //set passed parameter as name
      this.microservicekey = m;
    }
    /**
     * A getter for the microservicekey param
     * @return
     */
    public String getMicroservicekey(){
      //return the set name
      return this.microservicekey;
    }

    /**
     * This method is a getter for the current valid toki.
     * @return
     */
    public String getToki(){
        //return the set name
        return this.toki;
    }
    /**
     * This 
     * @param t
     */
    public void setToki(String t){
      //return the set name
      this.toki = t;
    }


    /**
     * This method is a getter for the current valid toki.
     * @return
     */
    public Boolean getDebugmode(){
      //return the set name
      return this.debugMode;
    }
    /**
     * This 
     * @param t
     */
    public void setDebugmode(Boolean d){
      //return the set name
      this.debugMode = d;
    }


    public void tokiPrint(Object toprintt){
      if (this.debugMode == true){
        System.out.println("[ DEBUG-OSCARTOKI ] > "+toprintt);
      }
    }

    public static String getSHA(String input) 
    { 
  
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
            System.out.println("Exception thrown"
                               + " for incorrect algorithm: " + e); 
  
            return null; 
        } 
    }

    /**
     * This method is the generator of the toki.
     * @return
     */
    public void generateToki(){
        tokiPrint("Starting Generation of the Toki.");

        // Let manae about the time
        Date date= new Date();
        long time = date.getTime();
        String timeAsString = Long.toString(time);
        StringBuilder timeAsString_sb = new StringBuilder(timeAsString);

        // Now let manage about the core of the toki
        String TokiCore = getSHA(getSHA(this.microservicekey + " - " + timeAsString + " - " + this.microservicekey));
        StringBuilder TokiCore_sb = new StringBuilder(TokiCore);

        String brouillage = getSHA(timeAsString + "Brouillage de piste").substring(0, 15);
        //System.out.println("Time in Milliseconds: " + time);
        // get first 9 digit of the time
        String toki_generated = timeAsString_sb.reverse() + "|" + TokiCore_sb.reverse() + "|" + brouillage;

        tokiPrint("Toki generated: "+toki_generated);

        setToki(toki_generated);
    }
    /**
     * This method verify a lamda toki and return true or false if it's good or not
     * @param toki
     * @return
     */
    public Boolean verifyToki(String toki){
      // 9 [0, 8] premiers chiffres du timestamp s'il y'a une difference de 30

      Date date= new Date();
      long current_time = date.getTime();
      String current_time_string = String.valueOf(current_time);
      current_time = Long.parseLong(current_time_string.substring(0, 9), 10);

      String[] arrOfElt = toki.split("\\|", -1);
      String get_toki_time = arrOfElt[0];
      StringBuilder get_toki_time_sb = new StringBuilder(get_toki_time).reverse();
      long toki_time_tocompare = Long.parseLong(get_toki_time_sb.toString().substring(0, 9), 10);
      long original_toki_time = Long.parseLong(get_toki_time_sb.toString(), 10);

      tokiPrint("Starting Verification.");
      tokiPrint("current_time: " + current_time);
      tokiPrint("toki_time_tocompare: " + toki_time_tocompare);

      // S'il y'a une difference de plus de 100seconde, le toki n'est plus valide
      if(current_time-toki_time_tocompare > lifetime_of_toki){

        tokiPrint("Toki is too old!");
        tokiPrint("Toki is invalid!");
        return false;

      }else{
        tokiPrint("Toki is in time!");

        String TokiCore = getSHA(getSHA(this.microservicekey + " - " + original_toki_time + " - " + this.microservicekey));
        StringBuilder TokiCore_sb = new StringBuilder(TokiCore);

        String reComputed = TokiCore_sb.reverse().toString();
        String given = arrOfElt[1];

        tokiPrint("Toki given: '" + given + "'");
        tokiPrint("Toki reComputed: '" + reComputed+"'");

        if (given.equals(reComputed)){
          tokiPrint("Toki is valid!");
          return true;
        }else{
          tokiPrint("Toki is invalid!");
          return false;
        }

      }
    }

    // An example of using Oscar-Toki
    //main method will be called first when program is executed
    public static void main(String args[]){

      // Instantiate the OscarToki
      Oscartoki OscarToki = new Oscartoki();
      // You can set the debug mode here:
      // It will print what is done during the compilation of the toki or the verification
      // You can turn it off by setDebugmode to false
      OscarToki.setDebugmode(true);

      /** EXAMPLE FOR THE GENERATION PROCESS TO ADD IN HEADER OFF EACH REQUESTS */
      System.out.println("# ----------------------------------");
      System.out.println("# OscarToki Generation started!");
      System.out.println("# ----------------------------------");
      // It's important to read this variable fomr a config file and not to 
      // put it hard in the code like this[FOR SECURITY], it's just a quick example 
      String Example_microservicekey = "aess3212-kj321gyu-gsad76-dsa687-21y873";
      //set name member of this object
      OscarToki.setMicroservicekey(Example_microservicekey);
      OscarToki.generateToki();
      // print the name
      System.out.println("Microservicekey: '" + OscarToki.getMicroservicekey()+"'");
      System.out.println("Toki: '" + OscarToki.getToki()+"'");

      System.out.print("\n");

      /** EXAMPLE FOR VEFY THE TOKI RECEIVED */
      System.out.println("# ----------------------------------");
      System.out.println("# OscarToki Verification process started!");
      System.out.println("# ----------------------------------");

      String Example_toki = "7674707094551|79f041e75fcdec730f4a1a48099fdefc2e301acccc7057765aaae11ced752afe|b313c1b16118e8e";
      // Dans la verification, si le timestamp est depasser de 100, ce n'est plus valide
      System.out.println("Example_toki: '" + Example_toki+"'");

      // If the Toki is valid
      if (OscarToki.verifyToki(Example_toki) == true){

        System.out.println("This is a Valid Toki!");

      }else{ // the toki is not valid
        
        System.out.println("Oops! This Toki is not valid!");

      }

    }
  }

  /*
    OUTPUT of the above given Java Class Example would be :
    Hello Visitor
  */
  