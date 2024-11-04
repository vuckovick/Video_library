/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.centralniserver.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import jms.Connection;
import jms.Request;

/**
 *
 * @author kokan
 */

@Path("funkcionalnosti")
public class CentralniServerResource {

    @Inject
    private Connection connection;
    
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
//PODSISTEM1
    
    @POST
    @Path("1")
    public Response kreirajGrad(@QueryParam("naziv") String naziv){
        if(naziv==null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "1");
        request.add("naziv", naziv);
        connection.send(request, 1);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("2")
    public Response kreirajKorisnika(@QueryParam("ime") String ime, @QueryParam("email") String email, @QueryParam("godiste") int godiste, @QueryParam("pol") String pol, @QueryParam("mesto") String mesto){
        if(ime == null || email == null || pol == null || mesto == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "2");
        request.add("ime", ime);
        request.add("email", email);
        request.add("godiste", Integer.toString(godiste));
        request.add("pol", pol);
        request.add("mesto", mesto);
        connection.send(request, 1);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("3")
    public Response promenaEmaila(@QueryParam("ime") String ime, @QueryParam("email") String email){
        if(ime == null || email == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "3");
        request.add("ime", ime);
        request.add("email", email);
        connection.send(request, 1);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("4")
    public Response promenaMesta(@QueryParam("ime") String ime, @QueryParam("mesto") String mesto){
        if(ime == null || mesto == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "4");
        request.add("ime", ime);
        request.add("email", mesto);
        connection.send(request, 1);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @GET
    @Path("17")
    public Response dohvatiSvaMesta(){
        Request request = new Request();
        request.add("func", "17");
        connection.send(request, 1);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @GET
    @Path("18")
    public Response dohvatiSveKorisnike(){
        Request request = new Request();
        request.add("func", "18");
        connection.send(request, 1);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
//PODSISTEM2
    
    @POST
    @Path("5")
    public Response kreirajKategoriju(@QueryParam("naziv") String naziv){
        if(naziv == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "5");
        request.add("naziv", naziv);
        connection.send(request, 2);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("6")
    public Response kreirajVideo(@QueryParam("naziv") String naziv, @QueryParam("trajanje") int trajanje, @QueryParam("vlasnik") String vlasnik){
        if(naziv == null || vlasnik == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "6");
        request.add("naziv", naziv);
        request.add("trajanje", Integer.toString(trajanje));
        request.add("vlasnik", vlasnik);
        connection.send(request, 2);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("7")
    public Response promenaNazivaVidea(@QueryParam("nazivstari") String nazivstari, @QueryParam("nazivnovi") String nazivnovi){
        if(nazivstari == null || nazivnovi == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "7");
        request.add("nazivstari", nazivstari);
        request.add("nazivnovi", nazivnovi);
        connection.send(request, 2);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("8")
    public Response dodajKategorijuVideu(@QueryParam("naziv") String naziv, @QueryParam("kategorija") String kategorija){
        if(naziv == null || kategorija == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "8");
        request.add("naziv", naziv);
        request.add("kategorija", kategorija);
        connection.send(request, 2);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("16")
    public Response obrisiVideo(@QueryParam("naziv") String naziv, @QueryParam("vlasnik") String vlasnik){
        if(naziv == null || vlasnik == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "16");
        request.add("naziv", naziv);
        request.add("vlasnik", vlasnik);
        connection.send(request, 2);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @GET
    @Path("19")
    public Response dohvatiSveKategorije(){
        Request request = new Request();
        request.add("func", "19");
        connection.send(request, 2);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @GET
    @Path("20")
    public Response dohvatiSveVidee(){
        Request request = new Request();
        request.add("func", "20");
        connection.send(request, 2);
        
        return Response
               .ok(connection.recieve())
                .build();
    }

    @GET
    @Path("21")
    public Response dohvatiKategorijeZaVideo(@QueryParam("naziv") String naziv){
        if(naziv == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "21");
        request.add("naziv", naziv);
        connection.send(request, 2);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
//PODSISTEM3    
    
    @POST
    @Path("9")
    public Response kreirajPaket(@QueryParam("cena") int cena){
        Request request = new Request();
        request.add("func", "9");
        request.add("cena", Integer.toString(cena));
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("10")
    public Response promenaCenePaketa(@QueryParam("idpaketa") int idpaketa, @QueryParam("novacena") int novacena){
        Request request = new Request();
        request.add("func", "10");
        request.add("idpaketa", Integer.toString(idpaketa));
        request.add("novacena", Integer.toString(novacena));
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("11")
    public Response kreirajPreplatu(@QueryParam("idpaketa") int idpaketa, @QueryParam("ime") String ime){
        if(ime == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        Request request = new Request();
        request.add("func", "11");
        request.add("idpaketa", Integer.toString(idpaketa));
        request.add("ime", ime);
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("12")
    public Response kreirajGledanje(@QueryParam("ime") String ime, @QueryParam("video") String video, @QueryParam("sekPoc") int sekPoc, @QueryParam("sekGle") int sekGle){
        if(ime == null || video == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        Request request = new Request();
        request.add("func", "12");
        request.add("ime", ime);
        request.add("video", video);
        request.add("sekPoc", Integer.toString(sekPoc));
        request.add("sekGle", Integer.toString(sekGle));
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("13")
    public Response kreirajOcenu(@QueryParam("ime") String ime, @QueryParam("video") String video, @QueryParam("ocena") int ocena){
        if(ime == null || video == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        Request request = new Request();
        request.add("func", "13");
        request.add("ime", ime);
        request.add("video", video);
        request.add("ocena", Integer.toString(ocena));
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("14")
    public Response promenaOcene(@QueryParam("ime") String ime, @QueryParam("video") String video, @QueryParam("ocena") int ocena){
        if(ime == null || video == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        Request request = new Request();
        request.add("func", "14");
        request.add("ime", ime);
        request.add("video", video);
        request.add("ocena", Integer.toString(ocena));
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @POST
    @Path("15")
    public Response obrisiOcenu(@QueryParam("ime") String ime, @QueryParam("video") String video){
        if(ime == null || video == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        Request request = new Request();
        request.add("func", "15");
        request.add("ime", ime);
        request.add("video", video);
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @GET
    @Path("22")
    public Response dohvatiSvePakete(){
        Request request = new Request();
        request.add("func", "22");
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }

    @GET
    @Path("23")
    public Response dohvatiPreplateKorisnika(@QueryParam("ime") String ime){
        if(ime == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "23");
        request.add("ime", ime);
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @GET
    @Path("24")
    public Response dohvatiGledanjaVidea(@QueryParam("naziv") String naziv){
        if(naziv == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "24");
        request.add("naziv", naziv);
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
    
    @GET
    @Path("25")
    public Response dohvatiOceneVidea(@QueryParam("naziv") String naziv){
        if(naziv == null)
            Response
               .ok("Nisu lepo uneti parametri!")
                .build();
        
        
        Request request = new Request();
        request.add("func", "25");
        request.add("naziv", naziv);
        connection.send(request, 3);
        
        return Response
               .ok(connection.recieve())
                .build();
    }
}
