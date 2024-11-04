/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package klijentskaaplikacija;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kokan
 */
public class KlijentskaAplikacija {
    
    private static final String USER_AGENT = "Mozilla/2.0";

    public static void obradaZahteva(){
        System.out.println("Zahtevi:\n" +
                            "1. Kreiranje grada\n" +
                            "2. Kreiranje korisnika\n" +
                            "3. Promena email adrese za korisnika\n" +
                            "4. Promena mesta za korisnika\n" +
                            "5. Kreiranje kategorije\n" +
                            "6. Kreiranje video snimka\n" +
                            "7. Promena naziva video snimka\n" +
                            "8. Dodavanje kategorije video snimku\n" +
                            "9. Kreiranje paketa\n" +
                            "10. Promena mesečne cene za paket\n" +
                            "11. Kreiranje pretplate korisnika na paket\n" +
                            "12. Kreiranje gledanja video snimka od strane korisnika\n" +
                            "13. Kreiranje ocene korisnika za video snimak\n" +
                            "14. Menjanje ocene korisnika za video snimak\n" +
                            "15. Brisanje ocene korisnika za video snimak\n" +
                            "16. Brisanje video snimka od strane korisnika koji ga je kreirao\n" +
                            "17. Dohvatanje svih mesta\n" +
                            "18. Dohvatanje svih korisnika\n" +
                            "19. Dohvatanje svih kategorija\n" +
                            "20. Dohvatanje svih video snimaka\n" +
                            "21. Dohvatanje kategorija za određeni video snimak\n" +
                            "22. Dohvatanje svih paketa\n" +
                            "23. Dohvatanje svih pretplata za korisnika\n" +
                            "24. Dohvatanje svih gledanja za video snimak\n" +
                            "25. Dohvatanje svih ocena za video snimak\n" + 
                            "------------------------------------------------------------------\n"+
                            "Unesite broj zahteva: ");
        
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        String param;
        String path="http://localhost:8080/CentralniServer/resources/funkcionalnosti/";
        boolean POST = true;
        
        switch (choice) {
            case 1:
                param = kreirajGrad(scanner);
                path+="1?";
                break;
            case 2:
                param = kreirajKorisnika(scanner);
                path+="2?";
                break;
            case 3:
                param = promenaEmaila(scanner);
                path+="3?";
                break;
            case 4:
                param = promenaMesta(scanner);
                path+="4?";
                break;
            case 5:
                param = kreirajKategorije(scanner);
                path+="5?";
                break;
            case 6:
                param = kreirajVideo(scanner);
                path+="6?";
                break;
            case 7:
                param = promenaNazivaVidea(scanner);
                path+="7?";
                break;  
            case 8:
                param = dodajKategorijuVideu(scanner);
                path+="8?";
                break;    
            case 9:
                param = kreirajPaket(scanner);
                path+="9?";
                break;  
            case 10:
                param = promenaCenePaketa(scanner);
                path+="10?";
                break;  
            case 11:
                param = kreirajPreplatu(scanner);
                path+="11?";
                break;  
            case 12:
                param = kreirajGledanje(scanner);
                path+="12?";
                break;  
            case 13:
                param = kreirajOcenu(scanner);
                path+="13?";
                break;  
            case 14:
                param = promenaOcene(scanner);
                path+="14?";
                break;  
            case 15:
                param = obrisiOcenu(scanner);
                path+="15?";
                break;  
            case 16:
                param = obrisiVideo(scanner);
                path+="16?";
                break;     
            case 17:
                param = "";
                path+="17";
                POST = false;
                break;
            case 18:
                param = "";
                path+="18";
                POST = false;
                break;
            case 19:
                param = "";
                path+="19";
                POST = false;
                break;   
            case 20:
                param = "";
                path+="20";
                POST = false;
                break;
            case 21:
                param = dohvatiKategorijeZaVideo(scanner);
                path+="21?";
                POST = false;
                break;
            case 22:
                param = "";
                path+="22";
                POST = false;
                break;
            case 23:
                param = dohvatiPreplateKorisnika(scanner);
                path+="23?";
                POST = false;
                break;
            case 24:
                param = dohvatiGledanjaVidea(scanner);
                path+="24?";
                POST = false;
                break;
            case 25:
                param = dohvatiOceneVidea(scanner);
                path+="25?";
                POST = false;
                break;
            default:
                System.out.println("Uneta je pogresna vrednost!\n");
                return;
        }
        
        System.out.println("\nODGOVOR:");
        try {
            send(path, param, POST);
        } catch (IOException ex) {
            Logger.getLogger(KlijentskaAplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
    
    public static String kreirajGrad(Scanner scanner){
        System.out.println("Unesite naziv grada: ");
        String naziv = scanner.next();
        return "naziv=" + naziv;
    }
    
    public static String kreirajKorisnika(Scanner scanner){
        String param = "", ime, email, nazivMesta, pol;
        int godiste;
        
        System.out.println("Unesite ime korisnika: ");
        ime = scanner.next();
        param += "ime=" + ime;
        System.out.println("Unesite email korisnika: ");
        email = scanner.next();
        param += "&email=" + email;
        System.out.println("Unesite godiste korisnika: ");
        godiste = scanner.nextInt();
        param += "&godiste=" + godiste;
        System.out.println("Unesite pol korisnika: ");
        pol = scanner.next();
        param += "&pol=" + pol;
        System.out.println("Unesite naziv mesta korisnika: ");
        nazivMesta = scanner.next();
        
        param += "&mesto=" + nazivMesta;
        return param;
    }
    
    public static String promenaEmaila(Scanner scanner){
        String param = "", ime, email;
        
        System.out.println("Unesite ime korisnika: ");
        ime = scanner.next();
        param += "ime=" + ime;
        System.out.println("Unesite novi email korisnika: ");
        email = scanner.next();
        
        param += "&email=" + email;
        return param;
    }
    
    public static String promenaMesta(Scanner scanner){
        String param = "", ime, nazivMesta;
        
        System.out.println("Unesite ime korisnika: ");
        ime = scanner.next();
        param += "ime=" + ime;
        System.out.println("Unesite naziv novog mesta korisnika: ");
        nazivMesta = scanner.next();
        
        param += "&mesto=" + nazivMesta;
        return param;
    }
    
    public static String kreirajKategorije(Scanner scanner){
        System.out.println("Unesite naziv kategorije: ");
        String naziv = scanner.next();
        return "naziv=" + naziv;
    }
    
    public static String kreirajVideo(Scanner scanner){
        String param = "", naziv, vlasnik;
        int trajanje;
        
        System.out.println("Unesite naziv video snimka: ");
        naziv = scanner.next();
        param += "naziv=" + naziv;
        System.out.println("Unesite trajanje video snimka: ");
        trajanje = scanner.nextInt();
        param += "&trajanje=" + trajanje;
        System.out.println("Unesite ime vlasnika video snimka: ");
        vlasnik = scanner.next();
        param += "&vlasnik=" + vlasnik;
        
        return param;
    }
    
    public static String promenaNazivaVidea(Scanner scanner){
        String param = "", nazivstari, nazivnovi;
        
        System.out.println("Unesite naziv video snimka: ");
        nazivstari = scanner.next();
        param += "nazivstari=" + nazivstari;
        System.out.println("Unesite novi naziv video snimka: ");
        nazivnovi = scanner.next();
        param += "&nazivnovi=" + nazivnovi;
        
        return param;
    }
    
    public static String dodajKategorijuVideu(Scanner scanner){
        String param = "", naziv, kategorija;
        
        System.out.println("Unesite naziv video snimka: ");
        naziv = scanner.next();
        param += "naziv=" + naziv;
        System.out.println("Unesite naziv kategorije: ");
        kategorija = scanner.next();
        param += "&kategorija=" + kategorija;
        
        return param;
    }
    
    public static String obrisiVideo(Scanner scanner){
        String param = "", naziv, vlasnik;
        
        System.out.println("Unesite naziv video snimka: ");
        naziv = scanner.next();
        param += "naziv=" + naziv;
        System.out.println("Unesite ime vlasnika video snimka: ");
        vlasnik = scanner.next();
        param += "&vlasnik=" + vlasnik;
        
        return param;
    }
    
    public static String dohvatiKategorijeZaVideo(Scanner scanner){
        String param = "", naziv;
        
        System.out.println("Unesite naziv video snimka: ");
        naziv = scanner.next();
        param += "naziv=" + naziv;
        
        return param;
    }
    
    public static String kreirajPaket(Scanner scanner){
        System.out.println("Unesite mesecnu cenu paketa: ");
        int cena = scanner.nextInt();
        return "cena=" + cena;
    }
    
    public static String promenaCenePaketa(Scanner scanner){
        String param = "";
        int novacena, idpaketa;
        
        System.out.println("Unesite id paketa: ");
        idpaketa = scanner.nextInt();
        param += "idpaketa=" + idpaketa;
        System.out.println("Unesite novu mesecnu cenu: ");
        novacena = scanner.nextInt();
        param += "&novacena=" + novacena;
        
        return param;
    }
    
    public static String kreirajPreplatu(Scanner scanner){
        String param = "", ime;
        int idpaketa;
        
        System.out.println("Unesite id paketa: ");
        idpaketa = scanner.nextInt();
        param += "idpaketa=" + idpaketa;
        System.out.println("Unesite ime korinsika: ");
        ime = scanner.next();
        param += "&ime=" + ime;
        
        return param;
    }
    
    public static String kreirajGledanje(Scanner scanner){
        String param = "", ime, video;
        int sekPoc, sekGle;
        
        System.out.println("Unesite ime korinsika: ");
        ime = scanner.next();
        param += "ime=" + ime;
        System.out.println("Unesite naziv videa: ");
        video = scanner.next();
        param += "&video=" + video;
        System.out.println("Unesite sekundu pocekta: ");
        sekPoc = scanner.nextInt();
        param += "&sekPoc=" + sekPoc;
        System.out.println("Unesite sekudne gledanja: ");
        sekGle = scanner.nextInt();
        param += "&sekGle=" + sekGle;
        
        return param;
    }
    
    public static String kreirajOcenu(Scanner scanner){
        String param = "", ime, video;
        int ocena;
        
        System.out.println("Unesite ime korinsika: ");
        ime = scanner.next();
        param += "ime=" + ime;
        System.out.println("Unesite naziv videa: ");
        video = scanner.next();
        param += "&video=" + video;
        System.out.println("Unesite ocenu: ");
        ocena = scanner.nextInt();
        param += "&ocena=" + ocena;
        
        return param;
    }
    
    public static String promenaOcene(Scanner scanner){
        String param = "", ime, video;
        int ocena;
        
        System.out.println("Unesite ime korinsika: ");
        ime = scanner.next();
        param += "ime=" + ime;
        System.out.println("Unesite naziv videa: ");
        video = scanner.next();
        param += "&video=" + video;
        System.out.println("Unesite novu ocenu: ");
        ocena = scanner.nextInt();
        param += "&ocena=" + ocena;
        
        return param;
    }
    
    public static String obrisiOcenu(Scanner scanner){
        String param = "", ime, video;
        
        System.out.println("Unesite ime korinsika: ");
        ime = scanner.next();
        param += "ime=" + ime;
        System.out.println("Unesite naziv videa: ");
        video = scanner.next();
        param += "&video=" + video;
        
        return param;
    }
    
    public static String dohvatiPreplateKorisnika(Scanner scanner){
        String param = "", ime;
        
        System.out.println("Unesite ime korisnika: ");
        ime = scanner.next();
        param += "ime=" + ime;
        
        return param;
    }
    
    public static String dohvatiGledanjaVidea(Scanner scanner){
        String param = "", naziv;
        
        System.out.println("Unesite naziv videa: ");
        naziv = scanner.next();
        param += "naziv=" + naziv;
        
        return param;
    }
    
    public static String dohvatiOceneVidea(Scanner scanner){
        String param = "", naziv;
        
        System.out.println("Unesite naziv videa: ");
        naziv = scanner.next();
        param += "naziv=" + naziv;
        
        return param;
    }
    
 //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------   

    private static void send(String url, String PARAMS, boolean POST) throws IOException {
        URL obj = new URL(url+PARAMS);
        
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        if(POST)
            con.setRequestMethod("POST");
        else
            con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                }
                in.close();
                
                String izlaz = response.toString();
                if (izlaz.startsWith("Lista")) {
                    izlaz = izlaz.replaceAll("\b", "\n");
                }
                // print result
                System.out.println(izlaz);
        } else {
                System.out.println("Request did not work.");
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        while(true){
            obradaZahteva();
            
            System.out.println("\nDa li zelite da nastavite(0-NE, 1-DA): ");
            choice = scanner.nextInt();
            if(choice == 0)
                break;
        }
    }
 
}