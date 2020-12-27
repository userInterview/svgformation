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
   
public class CreateAcquisition {
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

		//TableOperation objOperation = new TableOperation() ;
		try {
			TableOperation objOperation = (TableOperation)mySess.get(TableOperation.class, 1) ;
			TablePlaces objPlace = (TablePlaces)mySess.get(TablePlaces.class, 1) ; 
			TableDenrees objDenree1 = (TableDenrees)mySess.get(TableDenrees.class, 1) ; 
			TableDenrees objDenree2 = (TableDenrees)mySess.get(TableDenrees.class, 2) ;					
			
			System.out.println("Societe qui va faire une aquisition : " + objOperation.getSocieteOperation()) ; 
			System.out.println("Denrée 1 de l\'aquisition : " + objDenree1.getNomDenree()) ;
			System.out.println("Denrée 2 de l\'aquisition : " + objDenree2.getNomDenree()) ; 
			System.out.println("Place de l\'aquisition : " + objPlace.getNomPlace()) ;
			TableAcquisition objAcquisition = new  TableAcquisition(objPlace, objOperation, objDenree1, (double)5000);
			mySess.save(objAcquisition);
			objTrans.commit();
			
			
		}

		catch (HibernateException hibernateEx) {

			System.err.printf("**************************** Problème : \n", hibernateEx);
			hibernateEx.printStackTrace();
		}

		
	}

}
