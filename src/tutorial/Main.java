package tutorial;

import javax.persistence.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/p5.odb");
        EntityManager em = emf.createEntityManager();

       // 1) almacenar 10 puntos na base 
       
        System.out.println(" 1) almacenar 10 puntos na base ");
       
       em.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            Point p = new Point(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();

       
      /*  Query query1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        System.out.println("Total Points: " + query1.getSingleResult());

      
        Query query2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
        System.out.println("Average X: " + query2.getSingleResult());
*/
       // 2) listar todos os puntos ( o seu id , e demais atributos)
       
        System.out.println("2) listar todos os puntos ( o seu id , e  atributos)");
        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results = query.getResultList();
       
        for (Point p : results) {
            System.out.println("ID: "+p.getId()+ "\nAtrivutos: "+p);
         
        }
        
 
      
      
        //3) amosar os atributos do punto con id =10 
        
        System.out.println("3) amosar os atributos do punto con id =10");
        TypedQuery<Point> q2 = em.createQuery("SELECT p from Point P where id=10", Point.class);
        Point p1 = q2.getSingleResult();
        System.out.println("Punto con id="+p1.getId() +" = cordenada: " + p1);
        
      
      
      //4) actualizar o punto de id =10 , coordenada y,  ao valor que ten mais 2, e dicir se o atributo y do punto de id=10  valia 4 , debe pasar a valer 6.  
       
        System.out.println("4) actualizar o punto de id =10 , coordenada y,  ao valor que ten mais 2, e dicir se o atributo y do punto de id=10  valia 4 , debe pasar a valer 6.  ");
        em.getTransaction().begin();
        Query q4 = em.createQuery("UPDATE Point SET y = y+2 WHERE id=10");
        q4.executeUpdate();
        em.getTransaction().commit();
        
        //5) eliminar punto de id=5 
       
        System.out.println("5) eliminar punto de id=5 ");
        em.getTransaction().begin();
        int ok = em.createQuery("DELETE from Point WHERE id=5").executeUpdate();
        em.getTransaction().commit();
        
    
        //6)  actualizacion masiva selectiva : actualizar coordenada y ao valor 1000 para todos os puntos que teñan un  valor de y  inferior a un valor pasado por parametro (por exemplo facelo para o valor  6).

        
         System.out.println("actualizacion de los point con y < 6, modificando y al valor pasado por parametro(y<1000)");
        em.getTransaction().begin();
        Query up = em.createQuery("UPDATE Point SET y=:var WHERE y<6");
        up.setParameter("var", 1000);
        up.executeUpdate();
        em.getTransaction().commit();
        
        
        //7) borrado masivo selectivo masivo(delete queries).
   
        
        System.out.println("Borrado de los point con x < al valor pasado por parametro (x<3)");
        em.getTransaction().begin();
        Query up2 = em.createQuery("DELETE from Point WHERE x<:var");
        up2.setParameter("var", 3);
        up2.executeUpdate();
        em.getTransaction().commit();
        
      
        
        
      
        System.out.println("Comprobacion de como queda la base de datos");
       
       
        for (Point p : results) {
            System.out.println("ID: "+p.getId()+ "\nAtrivutos: "+p);
         
        }
        em.close();
        emf.close();
    }
}