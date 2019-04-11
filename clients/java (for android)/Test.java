public class Test
{

    public static void main( String[] args )
    {
 
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
        
        // It's important to read this variable from a config file and not to 
        // put it hard in the code like this[FOR SECURITY], it's just a quick example 
        String Example_microservicekey = "aess3212-kj321gyu-gsad76-dsa687-21y873";
        //set name member of this object
        OscarToki.setMicroservicekey(Example_microservicekey);
        OscarToki.generateToki();
        // print the name
        System.out.println("Microservicekey: '" + OscarToki.getMicroservicekey()+"'");
        System.out.println("Toki: '" + OscarToki.getToki()+"'");

        /**
         * You can Get a newly generated Toki by calling OscarToki.getToki()
        */
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

            /**
             * 
             * NOW!!! 
             * 
             * YOU CAN DO YOUR STUFF HERE CUZ THE TOKI IS VALID
             * 
             */

            System.out.println("This is a Valid Toki!");

        }else{ // the toki is not valid

            System.out.println("Oops! This Toki is not valid!");

        }
    }
}