package loiz.hibernate.main;

import org.hibernate.Session;

/*import java.util.ArrayList;
import java.util.List;*/

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import loiz.hibernate.beans.*;
   
public class getExistingDenreesFromTable {
//@SuppressWarnings(value = { "unused" })
	public static void main(String[] args) {		
		
		
		
		Configuration hibConf = new Configuration().configure("hibernateBaseAcquisitionHUpdate.cfg.xml");       
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
	
		TableDenrees objDenreeFromSGBD = (TableDenrees)mySess.get(TableDenrees.class, 2) ;
		TableDenrees objDenreeFromJavaEntity = new TableDenrees("Tomates", "Legume", "tonne", "100");
		
			
		try {
			System.out.println("objDenreeFromSGBD from SGBD : " + objDenreeFromSGBD + "\n");
			System.out.println("objDenreeFromJavaEntity from java instanciation : " + objDenreeFromJavaEntity + "\n");
			objTrans.commit();			
		}

		catch (HibernateException hibernateEx) {

			System.err.printf("**************************** Probl�me : \n", hibernateEx);
			hibernateEx.printStackTrace();
		}

		
	}

}
