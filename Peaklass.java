/**
 * Peaklass
 * Eesmärk: Programmi töö korraldus. Sisendite küsimine.
 */

import javax.swing.JOptionPane;
import java.util.Scanner;

public class Peaklass {

    public static void main(String[] args) {
	System.out.println("\n*=-=*=-=*=-=*=-=*=-=*=-=*");
        System.out.println("Tere tulemast kasiinosse!");
	System.out.println("*=-=*=-=*=-=*=-=*=-=*=-=*\n");

        Scanner sc = new Scanner(System.in);

        System.out.print("Sisesta oma nimi: ");
        String nimi = sc.nextLine();

        System.out.print("Sisesta oma sünniaasta: ");
        int sünniaasta = sc.nextInt();
        if(sünniaasta > 2005){
            System.out.println("Olete liiga noor, palun lahkuge :(");
            System.exit(0);
        }

        System.out.print("Tee sissemakse: ");
        int sissemakse = sc.nextInt();
        boolean onVIP = false;

        //Sisestatud atribuudid saavad klassi mängija isendiväärtusteks

        Mängija mängija = new Mängija(nimi,sünniaasta,sissemakse);

        System.out.println("\nValikud on: ");
        System.out.println("Blackjack");
        System.out.println("Rulett");
        System.out.println("Video Pokker");
        int tegevus = 0;
        while(tegevus != 6) {

            if (mängija.getVõidud() - mängija.getKaotused() > 50){
                System.out.println("Kahjuks olete liiga kasumlikud\nSeetõttu peame teid kasiinost välja viskama\nAidaa!");
                System.exit(0);
            }

            if (mängija.getBilanss() <= 0){
                System.out.println("Pankrot! Teeni raha ja külasta meid jälle!");
                System.exit(0);
            }
            System.out.println("\nVali, mida soovid teha: ");
            System.out.println("1 - Mängi Blackjacki ; 2 - Mängi Ruletti ; 3 - Mängi Video Pokkerit\n" +
                    "4 - Väljasta info; 5 - !*Hakka VIPiks*! 6 - Lõpeta");
            tegevus = sc.nextInt();
            int bet = 0;
            if(tegevus == 1 || tegevus == 2 || tegevus == 3){
                System.out.println("Sisesta panus");
                bet = sc.nextInt();
                if(bet > mängija.getBilanss()) {
                    System.out.println("Pole piisavalt vahendeid");
                    continue;
                }

            }
            switch(tegevus){
                case 1:
                    new Blackjack().mängi(mängija,bet);
                    break;
                case 2:
                    new Rulett().mängi(mängija,bet);
                    break;
                case 3:
                    new VideoPokker().mängi(mängija,bet);
                    break;
                case 4:
                    System.out.println(mängija.toString());
                    break;
                case 5:
                    if (mängija.getBilanss() >= 11 && onVIP == false) {
                        mängija = new VIPMängija(mängija.getNimi(), mängija.getSünniaasta(), mängija.getBilanss() - 10);
                        System.out.println("Oled nüüd VIP liige!");
                        onVIP = true;
                    }
                    else if (onVIP == true) System.out.println("Oled juba VIP!");
                    else System.out.println("Sul ei ole piisavalt vahendeid, et hakata VIPiks.");
                    break;
                case 6:
                    System.out.println("Lõpetanud");
                    break;
                default:
                    System.out.println("Viga");
            }

        }
        //idee: kui mängija bilanss läheb üle 20, siis saab ta banni, sest võidab liiga palju
    }

}
