public class Test {

    public static void main(String args[]) throws Exception {

        // Instantiate the OscarToki
        Oscartoki OscarToki = new Oscartoki();
        // You can set the debug mode here:
        // It will print what is done during the compilation of the toki or the
        // verification
        // You can turn it off by setDebugmode to false
        OscarToki.setDebugmode(true);

        /** EXAMPLE FOR THE GENERATION PROCESS TO ADD IN HEADER OFF EACH REQUESTS */
        System.out.println("# ----------------------------------");
        System.out.println("# OscarToki Generation started!");
        System.out.println("# ----------------------------------");
        // It's important to read this variable fomr a config file and not to
        // put it hard in the code like this[FOR SECURITY], it's just a quick example
        String Example_clientkey = "aess3212-kj321gyu-gsad76-dsa687-21y873";
        // set name member of this object
        OscarToki.setClientkey(Example_clientkey);
        OscarToki.generateToki();
        // print the name
        System.out.println("Clientkey: '" + OscarToki.getClientkey() + "'");
        System.out.println("Toki: '" + OscarToki.getToki() + "'");

        System.out.print("\n");

        /** EXAMPLE FOR VEFY THE TOKI RECEIVED */
        System.out.println("# ----------------------------------");
        System.out.println("# OscarToki Verification process started!");
        System.out.println("# ----------------------------------");

        String Example_toki = "3038471894551|3bca087ea08a60d30a573388609f28eaa96286dbbd77d12ade7fca43d7b09c71|e39ecbfc0d8bccc";
        // Dans la verification, si le timestamp est depasser de 100, ce n'est plus
        // valide
        System.out.println("Example_toki: '" + Example_toki + "'");

        String[] result = OscarToki.checkToki();
        String checktoki_status = (String) result[0];
        String checktoki_response = (String) result[1];

        if ("true".equals(checktoki_status)) {

            OscarToki.tokiPrint("Stating tokiCheck...");

            String oscartokikey = checktoki_response;
            OscarToki.tokiPrint("oscartokikey: " + oscartokikey);

            // Now you have the Key (toki and you can request with that now by adding it in
            // headers)
            // Your requested url (http://mira....)
            String url = "";

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