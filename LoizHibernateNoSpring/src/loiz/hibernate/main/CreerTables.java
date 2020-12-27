package loiz.hibernate.main;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import loiz.hibernate.beans.*;
   
public class CreerTables {

	public static void main(String[] args) {		
		
		  
		Configuration hibConf = new Configuration().configure("hibernateBaseAcquisitionHCreate.cfg.xml");        

		hibConf = hibConf.addAnnotatedClass(TableAcquisition.class) ;
		hibConf = hibConf.addAnnotatedClass(TableDenrees.class) ;
		hibConf = hibConf.addAnnotatedClass(TableOperation.class) ;
		hibConf = hibConf.addAnnotatedClass(TablePlaces.class) ;
		
		
		StandardServiceRegistryBuilder objSerRegBuild = new StandardServiceRegistryBuilder();
		objSerRegBuild = objSerRegBuild.applySettings(hibConf.getProperties()) ;
		ServiceRegistry objSerReg = objSerRegBuild.build() ;  
		SessionFactory sessFac = hibConf.buildSessionFactory(objSerReg); 

		Session mySess = sessFac.openSession();
		Transaction objTrans = mySess.beginTransaction();					
		try {
			objTrans.commit();	
		}

		catch (HibernateException hibernateEx) {

			System.err.printf("**************************** Save data problem : \n", hibernateEx);
			hibernateEx.printStackTrace();
		}

		
	}

}
