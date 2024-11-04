/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem2;

import entiteti.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jms.Request;
import java.util.List;
import javax.jms.Message;
import javax.persistence.Query;

/**
 *
 * @author kokan
 */

public class Main {
    @Resource(lookup="myQueueCentralni")
    private static Queue myQueueCentralni;
    
    @Resource(lookup="myQueuePodsistem2")
    private static Queue myQueuePodsistem2;
    
    @Resource(lookup="myQueuePodsistem3")
    private static Queue myQueuePodsistem3;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    private static JMSContext context;
    private static JMSProducer producer;
    private static JMSConsumer consumer;
    private static EntityManagerFactory emf;
    
    private static String kreirajKorisnika(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Kreiran je novi korisnik";
        
        try{
            String ime = request.get().getValue();
            
            em.getTransaction().begin();
            Korisnik k = new Korisnik();
            k.setIme(ime);
            em.persist(k);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo kreiranje novog korisnika";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String kreirajKategoriju(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Kreirana je nova kategorija";
        
        try{
            String naziv = request.get().getValue();
            Query q1 = em.createNamedQuery("Kategorija.findByNaziv");
            q1.setParameter("naziv", naziv);
            List<Videosnimak> rezultat1 = q1.getResultList();
            if(!rezultat1.isEmpty())
                return "Postoji vec kategorija za zadatim nazivom";
            
            em.getTransaction().begin();
            Kategorija k = new Kategorija();
            k.setNaziv(naziv);
            em.persist(k);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo kreiranje nove kategorije";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String kreirajVideo(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Kreiran je novi video snimak";
        
        try{
            String naziv = request.get().getValue();
            int trajanje = Integer.parseInt(request.get().getValue());
            String vlasnik = request.get().getValue();
            Timestamp date = Timestamp.from(Instant.now());
            
            Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
            q1.setParameter("naziv", naziv);
            List<Videosnimak> rezultat1 = q1.getResultList();
            if(!rezultat1.isEmpty())
                return "Postoji vec video za zadatim nazivom";
            
            Query q2 = em.createNamedQuery("Korisnik.findByIme");
            q2.setParameter("ime", vlasnik);
            List<Korisnik> rezultat2 = q2.getResultList();
            if(rezultat2.isEmpty())
                return "Ne postoji vlasnik";
            
            Request r = new Request();
            r.add("func", "6");
            r.add("naziv", naziv);
            producer.send(myQueuePodsistem3, r);
            
            em.getTransaction().begin();
            Korisnik k = rezultat2.get(0);
            Videosnimak v = new Videosnimak();
            v.setNaziv(naziv);
            v.setTrajanje(trajanje);
            v.setIdVlasnik(k);
            v.setPostavljanje(date);
            em.persist(v);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo kreiranje novog video snimka";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String promenaNaziva(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Promenjen je naziv video snimka";
        
        try{
            String nazivstari = request.get().getValue();
            String nazivnovi = request.get().getValue();
            
            Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
            q1.setParameter("naziv", nazivstari);
            List<Videosnimak> rezultat1 = q1.getResultList();
            if(rezultat1.isEmpty())
                return "Ne postoji video sa zadatim starim nazivom";
            
            Query q2 = em.createNamedQuery("Videosnimak.findByNaziv");
            q2.setParameter("naziv", nazivnovi);
            List<Videosnimak> rezultat2 = q2.getResultList();
            if(!rezultat2.isEmpty())
                return "Vec postoji video sa zadatim novim nazivom";
            
            Request r = new Request();
            r.add("func", "7");
            r.add("nazivstari", nazivstari);
            r.add("nazivnovi", nazivnovi);
            producer.send(myQueuePodsistem3, r);
            
            em.getTransaction().begin();
            Videosnimak v = rezultat1.get(0);
            v.setNaziv(nazivnovi);
            em.persist(v);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspela promena naziva video snimka";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String dodajKategorijuVideu(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Dodata je nova kategorija video snimku";
        
        try{
            String naziv = request.get().getValue();
            String kategorija = request.get().getValue();
            
            Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
            q1.setParameter("naziv", naziv);
            List<Videosnimak> rezultat1 = q1.getResultList();
            if(rezultat1.isEmpty())
                return "Ne postoji video sa zadatim nazivom";
            
            Query q2 = em.createNamedQuery("Kategorija.findByNaziv");
            q2.setParameter("naziv", kategorija);
            List<Kategorija> rezultat2 = q2.getResultList();
            if(rezultat2.isEmpty())
                return "Ne postoji video sa zadatom kategorijom";
            
            
            Videosnimak v = rezultat1.get(0);
            Kategorija k = rezultat2.get(0);
            List<Kategorija> kategorije = v.getKategorijaList();
            if(!kategorije.isEmpty()){
                for (Kategorija kat : kategorije) {
                    if(kat.equals(k)){
                        return "Video snimak je vec u toj kategoriji";
                    }
                }
            }
            
            List<Videosnimak> snimci = k.getVideosnimakList();

            em.getTransaction().begin();
            snimci.add(v);
            kategorije.add(k);
            k.setVideosnimakList(snimci);
            v.setKategorijaList(kategorije);
            em.persist(v);
            em.persist(k);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo dodavanje nove kategorije video snimku";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String obrisiVideo(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Obrisan je video snimak";
        
        try{
            String naziv = request.get().getValue();
            String vlasnik = request.get().getValue();
            
            Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
            q1.setParameter("naziv", naziv);
            List<Videosnimak> rezultat1 = q1.getResultList();
            if(rezultat1.isEmpty())
                return "Ne postoji video sa zadatim nazivom";
            
            Query q2 = em.createNamedQuery("Korisnik.findByIme");
            q2.setParameter("ime", vlasnik);
            List<Korisnik> rezultat2 = q2.getResultList();
            if(rezultat2.isEmpty())
                return "Ne postoji vlasnik sa zadatim nazivom";
            
            Videosnimak v = rezultat1.get(0);
            Korisnik k = rezultat2.get(0);
            
            if(!k.equals(v.getIdVlasnik()))
                return "Korisnik nije vlasnik videa";
            
            Request r = new Request();
            r.add("func", "16");
            r.add("naziv", naziv);
            producer.send(myQueuePodsistem3, r);
            
            em.getTransaction().begin();
            List<Kategorija> kategorije = v.getKategorijaList();
            if(!kategorije.isEmpty()){
                for (Kategorija kat : kategorije) {
                    List<Videosnimak> kat_videi = kat.getVideosnimakList();
                    kat_videi.remove(v);
                    kat.setVideosnimakList(kat_videi);
                    em.persist(kat);
                }
            }
            
            List<Videosnimak> kor_videi = k.getVideosnimakList();
            kor_videi.remove(v);
            k.setVideosnimakList(kor_videi);
            em.persist(k);
            
            em.remove(v);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo brisanje video snimka";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String dohvatiSveKategorije(){
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("Kategorija.findAll");
        List<Kategorija> rezultat = q.getResultList();
        int cnt=1;
        String txt="Lista svih kategorija:\b";
        if(rezultat.isEmpty())
            return txt;
        for (Kategorija k : rezultat) {
            txt= txt + Integer.toString(cnt) + ":\t" + k.getNaziv() + "\b";
            cnt++;
        }
        em.close();
        return txt;
    }
    
    private static String dohvatiSveVidee(){
        EntityManager em = emf.createEntityManager();
        Query q1 = em.createNamedQuery("Videosnimak.findAll");
        List<Videosnimak> rezultat1 = q1.getResultList();
        int cnt=1;
        String txt="Lista svih videa:\b";
        if(rezultat1.isEmpty())
            return txt;
        for (Videosnimak v : rezultat1) {
            Korisnik k = v.getIdVlasnik();
            txt= txt + Integer.toString(cnt) + ":\t" + v.getNaziv() + '\t'+Integer.toString(v.getTrajanje()) + "s\t" + v.getPostavljanje().toString() + '\t' + k.getIme() + '\b';
            cnt++;
        }
        em.close();
        return txt;
    }
    
    private static String dohvatiKategorijeZaVideo(Request request){
        EntityManager em = emf.createEntityManager();
        String naziv = request.get().getValue();
        Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
        q1.setParameter("naziv", naziv);
        List<Videosnimak> rezultat1 = q1.getResultList();
        if(rezultat1.isEmpty())
            return "Ne postoji video sa zadatim nazivom";
        int cnt=1;
        String txt="Lista svih kategorija za dati video:\b";
        Videosnimak v = rezultat1.get(0);
        List<Kategorija> rezultat2 = v.getKategorijaList();
        if(rezultat2.isEmpty())
            return txt;
        for (Kategorija k : rezultat2) {
            txt= txt + Integer.toString(cnt) + ":\t" + k.getNaziv() + "\b";
            cnt++;
        }
        em.close();
        return txt;
    }
    
    public static void ocisti(){
        Message msg = consumer.receiveNoWait();
        while(msg !=null){
            msg = consumer.receiveNoWait();
        }

    }
    
    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("Podsistem2PU");
        context = connectionFactory.createContext();
        producer = context.createProducer();
        consumer = context.createConsumer(myQueuePodsistem2);
        ocisti();
        ocisti();
        consumer.setMessageListener((msg) -> {
           try {
               msg.acknowledge();
                System.out.println("primljenja poruka");
                if (msg instanceof ObjectMessage) {
                    Request request = (Request) ((ObjectMessage)msg).getObject();
                    String func = request.get().getValue();
                    TextMessage txt_msg = context.createTextMessage();
                    switch (func) {
                        case "2":
                            txt_msg.setText(kreirajKorisnika(request));
                            return;
                        case "5":
                            txt_msg.setText(kreirajKategoriju(request));
                            break;
                        case "6":
                            txt_msg.setText(kreirajVideo(request));
                            break;
                        case "7":
                            txt_msg.setText(promenaNaziva(request));
                            break;
                        case "8":
                            txt_msg.setText(dodajKategorijuVideu(request));
                            break;
                        case "16":
                            txt_msg.setText(obrisiVideo(request));
                            break;
                        case "19":
                            txt_msg.setText(dohvatiSveKategorije());
                            break;
                        case "20":
                            txt_msg.setText(dohvatiSveVidee());
                            break;
                        case "21":
                            txt_msg.setText(dohvatiKategorijeZaVideo(request));
                            break;
                        default:
                            txt_msg.setText("Unet je nepostojeci zahtev");
                            break;
                    }
                    
                    producer.send(myQueueCentralni, txt_msg);
                    System.out.println("poslata poruka");
                } else {
                    System.err.println("Received non-obj message!");
                }
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        while (true){
            try {
                System.out.println("Ceka se poruka");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
}
