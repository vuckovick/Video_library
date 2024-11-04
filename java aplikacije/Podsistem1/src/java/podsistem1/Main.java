/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem1;

import entiteti.Korisnik;
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
import entiteti.Mesto;
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
    
    @Resource(lookup="myQueuePodsistem1")
    private static Queue myQueuePodsistem1;
    
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
    
    private static String kreirajGrad(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Kreiran je novi grad";
        
        try{
            String naziv = request.get().getValue();
            Query q = em.createNamedQuery("Mesto.findByNaziv");
            q.setParameter("naziv", naziv);
            List<Mesto> rezultat = q.getResultList();
            if(!rezultat.isEmpty())
                return "Postoji vec mesto sa zadatim imenom";
            em.getTransaction().begin();
            Mesto m = new Mesto();
            m.setNaziv(naziv);
            em.persist(m);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspelo kreiranje novog grada";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String kreirajKorisnika(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Kreiran je novi korisnik";
        
        try{
            String ime = request.get().getValue();
            String email = request.get().getValue();
            int godiste = Integer.parseInt(request.get().getValue());
            char pol = (request.get().getValue()).charAt(0);
            String mesto = request.get().getValue();
            
            if(pol != 'M' && pol != 'Z')
                return "Pol mora da bude ili M ili Z";
            
            Query q1 = em.createNamedQuery("Mesto.findByNaziv");
            q1.setParameter("naziv", mesto);
            List<Mesto> rezultat1 = q1.getResultList();
            if(rezultat1.isEmpty())
                return "Ne postoji zadato mesto";
            
            Query q2 = em.createNamedQuery("Korisnik.findByIme");
            q2.setParameter("ime", ime);
            List<Korisnik> rezultat2 = q2.getResultList();
            if(!rezultat2.isEmpty())
                return "Postoji vec korisnik za zadatim imenom";
            
            Request r1 = new Request();
            r1.add("func", "2");
            r1.add("ime", ime);
            producer.send(myQueuePodsistem2, r1);
            
            Request r2 = new Request();
            r2.add("func", "2");
            r2.add("ime", ime);
            producer.send(myQueuePodsistem3, r2);
            
            Mesto m = rezultat1.get(0);
            em.getTransaction().begin();
            Korisnik k = new Korisnik();
            k.setIme(ime);
            k.setEmail(email);
            k.setGodiste(godiste);
            k.setPol(pol);
            k.setIdMesta(m);
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
    
    private static String promenaEmaila(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Uspesno je promenjen email";
        
        try{
            String ime = request.get().getValue();
            String email = request.get().getValue();
            
            Query q = em.createNamedQuery("Korisnik.findByIme");
            q.setParameter("ime", ime);
            List<Korisnik> rezultat = q.getResultList();
            if(rezultat.isEmpty())
                return "Ne postoji zadati korisnik";
            
            
            em.getTransaction().begin();
            Korisnik k = rezultat.get(0);
            k.setEmail(email);
            em.persist(k);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspela promena email-a";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String promenaMesta(Request request){
        EntityManager em = emf.createEntityManager();
        String txt = "Uspesno je promenjeno mesto";
        
        try{
            String ime = request.get().getValue();
            String mesto = request.get().getValue();
            
            Query q1 = em.createNamedQuery("Mesto.findByNaziv");
            q1.setParameter("naziv", mesto);
            List<Mesto> rezultat1 = q1.getResultList();
            if(rezultat1.isEmpty())
                return "Ne postoji zadato mesto";
            
            Query q2 = em.createNamedQuery("Korisnik.findByIme");
            q2.setParameter("ime", ime);
            List<Korisnik> rezultat2 = q2.getResultList();
            if(rezultat2.isEmpty())
                return "Ne postoji zadati korisnik";
            
            em.getTransaction().begin();
            Korisnik k = rezultat2.get(0);
            Mesto m = rezultat1.get(0);
            k.setIdMesta(m);
            em.persist(k);
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
                txt = "Nije uspela promena mesta";
            }
            em.close();
        }
        
        return txt;
    }
    
    private static String dohvatiSvaMesta(){
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("Mesto.findAll");
        List<Mesto> rezultat = q.getResultList();
        int cnt=1;
        String txt="Lista svih mesta:\b";
        if(rezultat.isEmpty())
            return txt;
        for (Mesto mesto : rezultat) {
            txt= txt + Integer.toString(cnt) + ":\t" + mesto.getNaziv() + "\b";
            cnt++;
        }
        em.close();
        return txt;
    }
    
    private static String dohvatiSveKorisnike(){
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("Korisnik.findAll");
        List<Korisnik> rezultat = q.getResultList();
        int cnt=1;
        String txt="Lista svih korisnika:\b";
        if(rezultat.isEmpty())
            return txt;
        for (Korisnik k : rezultat) {
            txt= txt + Integer.toString(cnt) + ":\t" + k.getIme() + "\t" + k.getEmail()
                    + "\t" + k.getPol() + "\t" + Integer.toString(k.getGodiste()) + "\t"
                    + k.getIdMesta().getNaziv() + "\b";
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
        emf = Persistence.createEntityManagerFactory("Podsistem1PU");
        context = connectionFactory.createContext();
        producer = context.createProducer();
        consumer = context.createConsumer(myQueuePodsistem1);
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
                        case "1":
                            txt_msg.setText(kreirajGrad(request));
                            break;
                        case "2":
                            txt_msg.setText(kreirajKorisnika(request));
                            break;
                        case "3":
                            txt_msg.setText(promenaEmaila(request));
                            break;
                        case "4":
                            txt_msg.setText(promenaMesta(request));
                            break;
                        case "17":
                            txt_msg.setText(dohvatiSvaMesta());
                            break;
                        case "18":
                            txt_msg.setText(dohvatiSveKorisnike());
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
