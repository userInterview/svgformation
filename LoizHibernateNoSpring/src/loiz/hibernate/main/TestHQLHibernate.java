package loiz.hibernate.main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import loiz.hibernate.beans.*;


public class TestHQLHibernate {
	// Cette bibliothèque fetch deux fois mais une seule reqête est exécutée :
	public static void main(String[] args) {		
		myObjHib unObjHib = new myObjHib();
		Configuration hibConf = new Configuration().configure("hibernateBaseAcquisitionHUpdate.cfg.xml");
		hibConf = hibConf.addAnnotatedClass(TableAcquisition.class);
		hibConf = hibConf.addAnnotatedClass(TableDenrees.class);
		hibConf = hibConf.addAnnotatedClass(TableOperation.class);
		hibConf = hibConf.addAnnotatedClass(TablePlaces.class);

		StandardServiceRegistryBuilder objSerRegBuild = new StandardServiceRegistryBuilder();
		objSerRegBuild = objSerRegBuild.applySettings(hibConf.getProperties());
		ServiceRegistry objSerReg = objSerRegBuild.build();
		SessionFactory sessFac = hibConf.buildSessionFactory(objSerReg);
		Session mySess = sessFac.openSession();		
		
		try {			
			unObjHib.runFetchOperation(mySess);
			unObjHib.runFetchOperation(mySess);
			unObjHib.runFetchOperation(mySess);
		}

		catch (Exception Ex) {
			System.err.printf("**************************** Problème : \n", Ex);
			Ex.printStackTrace();
		}
	}
}

class myObjHib {

	public myObjHib() {
		super();
	}

	public void runFetchOperation(Session ArgMySess ) { 
		System.out.println("*************************** DEBUT runFetchOperation() **************************************");		
		Transaction objTrans = ArgMySess.beginTransaction();
  		@SuppressWarnings("unchecked")
		Query<TableDenrees> myHQLquery = ArgMySess.createQuery("from TableDenrees where TypeDenree = 'Legume'")	;
  		myHQLquery.setCacheable(true);
		List<TableDenrees> listDenrees = new ArrayList<TableDenrees>();
		listDenrees = myHQLquery.getResultList()  ;
		System.out.println("listDenrees : " + listDenrees +"\n");
		for(TableDenrees eleD:listDenrees)  
		System.out.println("eleD : " + eleD +"\n");
		

		objTrans.commit();
		emptyObject( objTrans, listDenrees);
		System.out.println("*************************** FIN runFetchOperation() **************************************");
	}

	public void emptyObject( Transaction ArgobjTrans, List<TableDenrees> ArgobjDenrees) {
		ArgobjTrans = null;
		ArgobjDenrees = null;
	}

}
