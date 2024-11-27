package cz.vsb.tuo.kel0060;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import cz.vsb.tuo.kel0060.classes.DigitalOrder;
import cz.vsb.tuo.kel0060.classes.PhysicalOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@SpringBootApplication
@EntityScan(basePackages = "Entities")
public class Kel0060Application {

	public static void main(String[] args) {
		SpringApplication.run(Kel0060Application.class, args);
		
    	CreateDB.createDataBase();
    	
    	
    	
    	LoginHandler checker = new LoginHandler();
    	
    	UserGateway db = new UserGateway();
    	
    	db.addUser("user@email.com", "Kirill", "keleshidi", 'U', "12345678");
    	
    	
    	 boolean IsGood = checker.login("user@scam.com", "alsdgj9jed");
         if (IsGood) {
        	 System.out.println("Good user");

             
         }else {
        	 System.out.println("some scam");
         }
         
    	 boolean IsGood2 = checker.login("user@email.com", "12345678");
         if (IsGood2) {
        	 System.out.println("Good user");

             
         }else {
        	 System.out.println("some scam");
         }
         
         
         
         
         
         Search search = new Search();
         List<String> mangaList = search.getMangaByCategory("Action");
         
         
         for (String manga : mangaList) {
             System.out.println(manga);
         }
         
         
         List<String> mangaNameList = search.getMangaByName("Nar");
         for (String manga : mangaNameList) {
             System.out.println(manga);
         }

         
         
         System.out.println("---- Ghost Pattern: PhysicalOrder ----");
         PhysicalOrder physicalOrder = new PhysicalOrder();
         
         // At this point, additional data has not been loaded
         System.out.println("PhysicalOrder created, but additional data not loaded yet.");
         
         // Set values (this would ideally set them directly into the object if they were eagerly loaded)
         physicalOrder.setShippingAddress("123 Example St, Cityville");
         physicalOrder.setTrackingNumber("TRACK123456789");

         // Access shipping address (triggers loading of shipping address if it wasn't already)
         System.out.println("Shipping Address: " + physicalOrder.getShippingAddress());
         
         // Access tracking number (data is already loaded, so no additional loading happens)
         System.out.println("Tracking Number: " + physicalOrder.getTrackingNumber());

         // Demonstrating the Value Holder pattern with DigitalOrder
         System.out.println("\n---- Value Holder Pattern: DigitalOrder ----");
         DigitalOrder digitalOrder = new DigitalOrder();
         
         // At this point, download link has not been loaded
         System.out.println("DigitalOrder created, but download link not loaded yet.");
         
         // Set the download link (triggering ValueHolder behavior)
         digitalOrder.setDownloadLink("https://example.com/download/digital-order");

         // Access download link (triggers loading from the ValueHolder)
         System.out.println("Download Link: " + digitalOrder.getDownloadLink());
         
         // Access download link again (value is already loaded)
         System.out.println("Download Link (cached): " + digitalOrder.getDownloadLink());
         
	}

}