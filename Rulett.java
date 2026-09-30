/**
 * Rulett
 * Eesmärk: Kasutaja panustab raha kas punasele või mustale.
 * Võidu korral teenib kasumit 2x panus
 */

import java.util.Scanner;

public class Rulett extends Mäng{

    @Override
    public void mängi(Mängija mängija, int bet){
        String värv = "";

        Scanner sc2 = new Scanner(System.in);
        System.out.println("Vali värv (punane/must): ");
        String valitudVärv = sc2.nextLine().toLowerCase();

        int voidusumma = bet * 2;
        int randomnumber = (int)(Math.random() * 37);
        if (randomnumber == 0){
            värv = "roheline";
        }
        else if (randomnumber < 19){
            värv = "punane";
        }
        else värv = "must";

        System.out.println("Keerutan...");
        System.out.println("Värv: " + värv);

        if (värv.equals(valitudVärv)){
            System.out.println("Võitsid " + voidusumma + " ühikut.");
            mängija.suurendaBilanssi(voidusumma);
        }
        else{
            System.out.println("Kaotasid " + bet);
            mängija.vähendaBilanssi(bet);
        }

    }
}
