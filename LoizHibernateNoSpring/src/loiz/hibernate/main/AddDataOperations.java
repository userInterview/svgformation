package loiz.hibernate.main;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder ;

import loiz.hibernate.beans.*;
   
public class AddDataOperations {

	public static void main(String[] args) {		
		
		
		
		List <TableOperation> listOperations = new ArrayList<TableOperation>() ;
		listOperations.add(new TableOperation("13/01/2017", "NESTLE"));
		listOperations.add(new TableOperation("11/03/2018", "DANONE"));
		listOperations.add(new TableOperation("24/10/2048", "UNILEVER"));
		
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
			for(TableOperation elePlace : listOperations) {
				mySess.save(elePlace);
			}
			objTrans.commit();	
		}

		catch (HibernateException hibernateEx) {

			System.err.printf("**************************** Save data problem : \n", hibernateEx);
			hibernateEx.printStackTrace();
		}

		
	}

}
