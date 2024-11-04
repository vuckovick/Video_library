/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import com.mycompany.centralniserver.resources.CentralniServerResource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 *
 * @author kokan
 */

@Stateless
public class Connection {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueueCentralni")
    Queue myQueueCentralni;
    
    @Resource(lookup="myQueuePodsistem1")
    Queue myQueuePodsistem1;
    
    @Resource(lookup="myQueuePodsistem2")
    Queue myQueuePodsistem2;
    
    @Resource(lookup="myQueuePodsistem3")
    Queue myQueuePodsistem3;
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ocisti(){
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(myQueueCentralni);
        Message msg = consumer.receiveNoWait();
        while(msg !=null){
            msg = consumer.receiveNoWait();
        }

    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void send(Request request, int podsistem){
        ocisti();
        ocisti();
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        try {
            ObjectMessage objectMessage = context.createObjectMessage();
            objectMessage.setObject(request);
            if(podsistem == 1)
                producer.send(myQueuePodsistem1, objectMessage);
            if(podsistem == 2)
                producer.send(myQueuePodsistem2, objectMessage);
            if(podsistem == 3)
                producer.send(myQueuePodsistem3, objectMessage);
        } catch (JMSException ex) {
            Logger.getLogger(CentralniServerResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String recieve(){
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(myQueueCentralni);
        Message msg = consumer.receive();
        String txt_msg;
        try {
            msg.acknowledge();
            txt_msg = ((TextMessage) msg).getText();
        } catch (JMSException ex) {
            txt_msg = "Error";
        }
        
        return txt_msg;
    }
}
