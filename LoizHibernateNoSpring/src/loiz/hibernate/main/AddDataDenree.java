package loiz.hibernate.main;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
//import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder ;
import loiz.hibernate.beans.*;
   
public class AddDataDenree {

	public static void main(String[] args) {		
		
		
		
		List <TableDenrees> listDenrees = new ArrayList<TableDenrees>() ;
		//public TableDenree(String Arg_nomDenree, String Arg_typeDenree, String Arg_uniteDeVenteDenree, String Arg_valeurUniteDenree) {		
		listDenrees.add(new TableDenrees("Tomates", "Legume", "tonne", "100"));
		listDenrees.add(new TableDenrees("Carottes", "Legume", "tonne", "90"));
		listDenrees.add(new TableDenrees("Aubergines", "Legume", "tonne", "150"));
		listDenrees.add(new TableDenrees("Fenouille", "Legume", "tonne", "180"));
		listDenrees.add(new TableDenrees("RIZ", "Feculent", "tonne", "50"));
		listDenrees.add(new TableDenrees("Farine", "Cereale", "tonne", "200"));
		
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
		try {
			for(TableDenrees eleDentree : listDenrees) {
				mySess.save(eleDentree);
			}
			objTrans.commit();	
		}

		catch (HibernateException hibernateEx) {

			System.err.printf("**************************** Save data problem : \n", hibernateEx);
			hibernateEx.printStackTrace();
		}

		
	}

}
