/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem3;

import entiteti.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
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
    
    private static String kreirajVideo(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Kreiran je novi video snimak";
        
        try{
            String naziv = request.get().getValue();
            
            em.getTransaction().begin();
            Videosnimak v = new Videosnimak();
            v.setNaziv(naziv);
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
    
    private static String obrisiVideo(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Obrisan je video snimak";
        
        try{
            String naziv = request.get().getValue();
            
            Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
            q1.setParameter("naziv", naziv);
            List<Videosnimak> rezultat1 = q1.getResultList();
            Videosnimak v = rezultat1.get(0);
            
            em.getTransaction().begin();
            
            List<Ocena> ocene = v.getOcenaList();
            List<Gledanje> gledanja = v.getGledanjeList();
            
            if(!gledanja.isEmpty()){
                for(Gledanje g : gledanja){
                    Korisnik k = g.getIdKorisnik();
                    List<Gledanje> gle = k.getGledanjeList();
                    gle.remove(g);
                    k.setGledanjeList(gle);
                    em.persist(k);
                    em.remove(g);
                }
            }
            
            if(!ocene.isEmpty()){
                for(Ocena o : ocene){
                    Korisnik k = o.getIdKorisnik();
                    List<Ocena> oce = k.getOcenaList();
                    oce.remove(o);
                    k.setOcenaList(oce);
                    em.persist(k);
                    em.remove(o);
                }
            }
            
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
    
    private static String kreirajPaket(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Kreirana je novi paket";
        
        try{
            int cena = Integer.parseInt(request.get().getValue());
            
            em.getTransaction().begin();
            Paket p = new Paket();
            p.setCena(cena);
            em.persist(p);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo kreiranje novog paketa";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String promenaCenePaketa(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Promenjen je cena paketa";
        
        try{
            int idpaketa = Integer.parseInt(request.get().getValue());
            int cena = Integer.parseInt(request.get().getValue());
            
            Query q = em.createNamedQuery("Paket.findByIdpaket");
            q.setParameter("idpaket", idpaketa);
            List<Paket> rezultat = q.getResultList();
            if(rezultat.isEmpty())
                return "Ne postoji paket sa zadatim id-em";
            
            em.getTransaction().begin();
            Paket p = rezultat.get(0);
            p.setCena(cena);
            em.persist(p);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspela promena cene paketa";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String kreirajPreplatu(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Kreirana je nova preplata";
        
        try{
            int idpaketa = Integer.parseInt(request.get().getValue());
            String ime = request.get().getValue();
            Timestamp datenow = Timestamp.from(Instant.now());
            
            Query q1 = em.createNamedQuery("Paket.findByIdpaket");
            q1.setParameter("idpaket", idpaketa);
            List<Paket> rezultat1 = q1.getResultList();
            if(rezultat1.isEmpty())
                return "Ne postoji paket sa zadatim id-em";
            
            Paket pak = rezultat1.get(0);
            int cena = pak.getCena();
            
            Query q2 = em.createNamedQuery("Korisnik.findByIme");
            q2.setParameter("ime", ime);
            List<Korisnik> rezultat2 = q2.getResultList();
            if(rezultat2.isEmpty())
                return "Ne postoji korisnik sa zadatim imenom";
            
            Korisnik k = rezultat2.get(0);
            List<Preplata> kor_preplate = k.getPreplataList();
            
            if(!kor_preplate.isEmpty()){
                for(Preplata pre : kor_preplate){
                    Timestamp datebefore = new Timestamp(pre.getDatum().getTime());
                    LocalDateTime localDate1 = datebefore.toLocalDateTime();
                    localDate1 = localDate1.plusMonths(1);
                    datebefore = Timestamp.valueOf(localDate1);

                    if(datebefore.after(datenow))
                        return "Nije proslo vise od mesec dana od poslednje preplate";
                }
            }
            
            em.getTransaction().begin();
            Preplata p = new Preplata();
            p.setCena(cena);
            p.setDatum(datenow);
            p.setIdPaket(pak);
            p.setIdKorisnik(k);
            em.persist(p);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo kreiranje nove preplate";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String kreirajGledanje(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Kreirano je novo gledanje";
        
        try{
            String ime = request.get().getValue();
            String video = request.get().getValue();
            int sekPoc = Integer.parseInt(request.get().getValue());
            int sekGle = Integer.parseInt(request.get().getValue());
            Timestamp datenow = Timestamp.from(Instant.now());
            
            Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
            q1.setParameter("naziv", video);
            List<Videosnimak> rezultat1 = q1.getResultList();
            if(rezultat1.isEmpty())
                return "Ne postoji video sa zadatim nazivom";
            
            Videosnimak v = rezultat1.get(0);
            
            Query q2 = em.createNamedQuery("Korisnik.findByIme");
            q2.setParameter("ime", ime);
            List<Korisnik> rezultat2 = q2.getResultList();
            if(rezultat2.isEmpty())
                return "Ne postoji korisnik sa zadatim imenom";
            
            Korisnik k = rezultat2.get(0);
            
            em.getTransaction().begin();
            Gledanje g = new Gledanje();
            g.setDatum(datenow);
            g.setIdKorisnik(k);
            g.setIdVideoSnimak(v);
            g.setSekundPocetka(sekPoc);
            g.setSekundeGledanja(sekGle);
            em.persist(g);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo kreiranje novog gledanja";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String kreirajOcenu(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Kreirana je nova ocena";
        
        try{
            String ime = request.get().getValue();
            String video = request.get().getValue();
            int ocena = Integer.parseInt(request.get().getValue());
            Timestamp datenow = Timestamp.from(Instant.now());
            
            if(ocena < 0 || ocena > 5)
                return "Ocena mora da bude u opsegu 1-5";
            
            Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
            q1.setParameter("naziv", video);
            List<Videosnimak> rezultat1 = q1.getResultList();
            if(rezultat1.isEmpty())
                return "Ne postoji video sa zadatim nazivom";
            
            Videosnimak v = rezultat1.get(0);
            
            Query q2 = em.createNamedQuery("Korisnik.findByIme");
            q2.setParameter("ime", ime);
            List<Korisnik> rezultat2 = q2.getResultList();
            if(rezultat2.isEmpty())
                return "Ne postoji korisnik sa zadatim imenom";
            
            Korisnik k = rezultat2.get(0);
            
            Query q3 = em.createNamedQuery("Ocena.findAll");
            List<Ocena> rezultat3 = q3.getResultList();
            
            if(!rezultat3.isEmpty()){
                for(Ocena oce : rezultat3){
                    if(v.equals(oce.getIdVideosnimak()))
                        if(k.equals(oce.getIdKorisnik()))
                            return "Vec postoji ocena za zadati video od strane zadatog korisnika";
                }
            }
            
            em.getTransaction().begin();
            Ocena o = new Ocena();
            o.setDatum(datenow);
            o.setIdKorisnik(k);
            o.setOcena(ocena);
            o.setIdVideosnimak(v);
            em.persist(o);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo kreiranje nove ocene";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String promenaOcene(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Promenjena je ocena";
        
        try{
            String ime = request.get().getValue();
            String video = request.get().getValue();
            int ocena = Integer.parseInt(request.get().getValue());
            Timestamp date = Timestamp.from(Instant.now());
            
            Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
            q1.setParameter("naziv", video);
            List<Videosnimak> rezultat1 = q1.getResultList();
            if(rezultat1.isEmpty())
                return "Ne postoji video sa zadatim nazivom";
            
            Videosnimak v = rezultat1.get(0);
            
            Query q2 = em.createNamedQuery("Korisnik.findByIme");
            q2.setParameter("ime", ime);
            List<Korisnik> rezultat2 = q2.getResultList();
            if(rezultat2.isEmpty())
                return "Ne postoji korisnik sa zadatim imenom";
            
            Query q3 = em.createNamedQuery("Ocena.findAll");
            List<Ocena> rezultat3 = q3.getResultList();
            if(rezultat3.isEmpty())
                return "Ne postoji nijedna ocena";
            
            Korisnik k = rezultat2.get(0);
            Ocena o = null;
            
            boolean postoji = false;
            for(Ocena oc : rezultat3){
                if(v.equals(oc.getIdVideosnimak()))
                    if(k.equals(oc.getIdKorisnik())){
                        postoji = true;
                        o = oc;
                    }
            }
            
            if(!postoji)
                return "Ne postoji ocena za zadati video od strane zadatog korisnika";
            
            em.getTransaction().begin();
            o.setOcena(ocena);
            o.setDatum(date);
            em.persist(o);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspela promena ocene";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String obrisiOcenu(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Obrisana je ocena";
        
        try{
            String ime = request.get().getValue();
            String video = request.get().getValue();
            
            Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
            q1.setParameter("naziv", video);
            List<Videosnimak> rezultat1 = q1.getResultList();
            if(rezultat1.isEmpty())
                return "Ne postoji video sa zadatim nazivom";
            
            Videosnimak v = rezultat1.get(0);
            
            Query q2 = em.createNamedQuery("Korisnik.findByIme");
            q2.setParameter("ime", ime);
            List<Korisnik> rezultat2 = q2.getResultList();
            if(rezultat2.isEmpty())
                return "Ne postoji korisnik sa zadatim imenom";
            
            Query q3 = em.createNamedQuery("Ocena.findAll");
            List<Ocena> rezultat3 = q3.getResultList();
            if(rezultat3.isEmpty())
                return "Ne postoji nijedna ocena";
            
            Korisnik k = rezultat2.get(0);
            Ocena o = null;
            
            boolean postoji = false;
            for(Ocena oc : rezultat3){
                if(v.equals(oc.getIdVideosnimak()))
                    if(k.equals(oc.getIdKorisnik())){
                        postoji = true;
                        o = oc;
                    }
            }
            
            if(!postoji)
                return "Ne postoji ocena za zadati video od strane zadatog korisnika";
            
            em.getTransaction().begin();
            List<Ocena> ocene1 = v.getOcenaList();
            if(!ocene1.isEmpty()){
                for (Ocena oc1 : ocene1) {
                    if(o.equals(oc1))
                        ocene1.remove(o);

                }
            }
            
            v.setOcenaList(ocene1);
            em.persist(v);
            
            List<Ocena> ocene2 = k.getOcenaList();
            if(!ocene2.isEmpty()){
                for (Ocena oc2 : ocene2) {
                    if(o.equals(oc2))
                        ocene2.remove(o);
                }
            }
            k.setOcenaList(ocene2);
            em.persist(k);
            
            em.remove(o);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo brisanje ocene";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String dohvatiSvePakete(){
        EntityManager em = emf.createEntityManager();
        Query q1 = em.createNamedQuery("Paket.findAll");
        List<Paket> rezultat1 = q1.getResultList();
        int cnt=1;
        String txt="Lista svih paketa:\b";
        if(rezultat1.isEmpty())
            return txt;
        for (Paket p : rezultat1) {
            txt= txt + Integer.toString(cnt) + ":\t" + Integer.toString(p.getIdpaket()) + "\t" + Integer.toString(p.getCena()) + '\b';
            cnt++;
        }
        em.close();
        return txt;
    }
    
    private static String dohvatiPreplateKorisnika(Request request){
        EntityManager em = emf.createEntityManager();
        String ime = request.get().getValue();
        Query q1 = em.createNamedQuery("Korisnik.findByIme");
        q1.setParameter("ime", ime);
        List<Korisnik> rezultat1 = q1.getResultList();
        if(rezultat1.isEmpty())
            return "Ne postoji korisnik sa zadatim imenom";
        int cnt=1;
        String txt="Lista svih preplata za zadatog korisnika:\b";
        Korisnik k = rezultat1.get(0);
        List<Preplata> rezultat2 = k.getPreplataList();
        if(rezultat2.isEmpty())
            return txt;
        for (Preplata p : rezultat2) {
            txt= txt + Integer.toString(cnt) + ":\t" + Integer.toString(p.getIdPaket().getIdpaket()) + "\t" + p.getDatum().toString() + "\t" + Integer.toString(p.getCena()) + "\b";
            cnt++;
        }
        em.close();
        return txt;
    }
    
    private static String dohvatiGledanjaVidea(Request request){
        EntityManager em = emf.createEntityManager();
        String naziv = request.get().getValue();
        Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
        q1.setParameter("naziv", naziv);
        List<Videosnimak> rezultat1 = q1.getResultList();
        if(rezultat1.isEmpty())
            return "Ne postoji video sa zadatim nazivom";
        int cnt=1;
        String txt="Lista svih gledanja za zadati video:\b";
        Videosnimak v = rezultat1.get(0);
        List<Gledanje> rezultat2 = v.getGledanjeList();
        if(rezultat2.isEmpty())
            return txt;
        for (Gledanje g : rezultat2) {
            txt= txt + Integer.toString(cnt) + ":\t" + g.getIdKorisnik().getIme() + "\t" + g.getDatum().toString() + "\t" + Integer.toString(g.getSekundPocetka()) + "s-" + Integer.toString(g.getSekundPocetka()+g.getSekundeGledanja()) + "s\b";
            cnt++;
        }
        em.close();
        return txt;
    }
    
    private static String dohvatiOceneVidea(Request request){
        EntityManager em = emf.createEntityManager();
        String naziv = request.get().getValue();
        Query q1 = em.createNamedQuery("Videosnimak.findByNaziv");
        q1.setParameter("naziv", naziv);
        List<Videosnimak> rezultat1 = q1.getResultList();
        if(rezultat1.isEmpty())
            return "Ne postoji video sa zadatim nazivom";
        int cnt=1;
        String txt="Lista svih ocena za zadati video:\b";
        Videosnimak v = rezultat1.get(0);
        List<Ocena> rezultat2 = v.getOcenaList();
        if(rezultat2.isEmpty())
            return txt;
        for (Ocena o : rezultat2) {
            txt= txt + Integer.toString(cnt) + ":\t" + o.getIdKorisnik().getIme() + "\t" + o.getDatum().toString() + "\t" + Integer.toString(o.getOcena()) + "\b";
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
        emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        context = connectionFactory.createContext();
        producer = context.createProducer();
        consumer = context.createConsumer(myQueuePodsistem3);
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
                        case "6":
                            txt_msg.setText(kreirajVideo(request));
                            return;
                        case "7":
                            txt_msg.setText(promenaNaziva(request));
                            return;
                        case "16":
                            txt_msg.setText(obrisiVideo(request));
                            return;
                        case "9":
                            txt_msg.setText(kreirajPaket(request));
                            break;
                        case "10":
                            txt_msg.setText(promenaCenePaketa(request));
                            break;
                        case "11":
                            txt_msg.setText(kreirajPreplatu(request));
                            break;
                        case "12":
                            txt_msg.setText(kreirajGledanje(request));
                            break;
                        case "13":
                            txt_msg.setText(kreirajOcenu(request));
                            break;
                        case "14":
                            txt_msg.setText(promenaOcene(request));
                            break;
                        case "15":
                            txt_msg.setText(obrisiOcenu(request));
                            break;
                        case "22":
                            txt_msg.setText(dohvatiSvePakete());
                            break;
                        case "23":
                            txt_msg.setText(dohvatiPreplateKorisnika(request));
                            break;
                        case "24":
                            txt_msg.setText(dohvatiGledanjaVidea(request));
                            break;
                        case "25":
                            txt_msg.setText(dohvatiOceneVidea(request));
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
