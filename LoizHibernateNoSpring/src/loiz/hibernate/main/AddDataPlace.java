package loiz.hibernate.main;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.HashSet;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import loiz.hibernate.beans.*;
   
public class AddDataPlace {

	public static void main(String[] args) {
		
		Hashtable<Integer,TablePlaces> objHashTable = new Hashtable<Integer,TablePlaces>(); 
		Map<Integer,TablePlaces> objHashMap = new HashMap<Integer,TablePlaces>(); 
		Set<TablePlaces> objHashSet = new HashSet<TablePlaces>();				
		
		List <TablePlaces> listPlaces = new ArrayList<TablePlaces>() ;
		listPlaces.add(new TablePlaces("SIDNEY", "AUSTRALIA"));
		listPlaces.add(new TablePlaces("PEKIN", "CHINA"));
		listPlaces.add(new TablePlaces("TOKYO", "JAPAN"));
		listPlaces.add(new TablePlaces("CAPCOREE", "COREE DU SUD"));
		listPlaces.add(new TablePlaces("MADRID", "ESPAGNE"));
		listPlaces.add(new TablePlaces("LISBONNE", "PORTUGAL"));
		
		int i = 1 ;
		for(TablePlaces elePlace : listPlaces) {
			if (i<=3)
				objHashTable.put(i, elePlace) ;
			i++ ;
		}		
		List <Hashtable<Integer,TablePlaces>> listHashPlace = new ArrayList<Hashtable<Integer,TablePlaces>>() ;
		listHashPlace.add(objHashTable) ;
		i = 1 ;
		objHashTable = new Hashtable<Integer,TablePlaces>(); //ré-initialisation pour contenir les 3 valeurs suivantes
		for(TablePlaces elePlace : listPlaces) {
			if (i>=4 && i<= 6 )
				objHashTable.put(i, elePlace) ;
			i++ ;
		}		
		
		listHashPlace.add(objHashTable) ;
		i = 1 ;
		for(TablePlaces elePlace : listPlaces) {
			objHashMap.put(i, elePlace) ;	
			i++ ;
		}
		for(TablePlaces elePlace : listPlaces) {
			objHashSet.add(elePlace) ;				
		}
		
		
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
			for(TablePlaces elePlace : listPlaces) {
				
				mySess.save(elePlace);
				//mySess.remove(elePlace);
			}
			objTrans.commit();
			System.exit(0);
		}

		catch (HibernateException hibernateEx) {

			System.err.printf("**************************** Save data problem : \n", hibernateEx);
			hibernateEx.printStackTrace();
		}

		
	}

}
