package DecryptEncrypt;

import com.sun.jdi.PathSearchingVirtualMachine;

public class HerstellerFormatierung {

    String issuer1 = "CN=BA-Bulk-CA-10:PN " +
            "    O=Bundesagentur fuer Arbeit           C=DE";

    String issuer2 = "CN=BA-Bulk-CA-10:PN, O=Bundesagentur fuer Arbeit, C=DE";

    public static void main(String[] args) {
        HerstellerFormatierung herstellerFormatierung = new HerstellerFormatierung();
        System.out.println(herstellerFormatierung.formatIssuer(herstellerFormatierung.issuer1));
        System.out.println(herstellerFormatierung.formatIssuer(herstellerFormatierung.issuer2));
    }

    String formatIssuer(String issuer) {
        return issuer.replaceAll("\\s+O=", ", O=").replaceAll("\\s+C=", ", C=").replaceAll("\\s+,", ",").replaceAll(",,", ",");
    }
}




