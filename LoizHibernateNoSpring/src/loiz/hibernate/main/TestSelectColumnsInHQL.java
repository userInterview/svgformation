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


public class TestSelectColumnsInHQL {
	// Cette bibliothèque fetch deux fois mais une seule reqête est exécutée :
	public static void main(String[] args) {		
		myObjHibSelect unObjHib = new myObjHibSelect();
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

class myObjHibSelect {

	public myObjHibSelect() {
		super();
	}

	public void runFetchOperation(Session ArgMySess ) { 
		System.out.println("*************************** DEBUT runFetchOperation() **************************************");		
		Transaction objTrans = ArgMySess.beginTransaction();
  		@SuppressWarnings("unchecked")
		Query<Object[]> myHQLquery = ArgMySess.createQuery("SELECT typeDenree, uniteDeVenteDenree, valeurUniteDenree from TableDenrees")	;
  		myHQLquery.setCacheable(false);		
		List<Object[]> objSelectDenrees = (List<Object[]>) myHQLquery.getResultList() ;
		System.out.println("objSelectDenrees : " + objSelectDenrees +"\n");
		
		for (Object[] eleObj:objSelectDenrees) {
			System.out.println("-------------------------------");
			System.out.println("eleObj[0] : " + eleObj[0] +"\n");		
			System.out.println("eleObj[1] : " + eleObj[1] +"\n");	
			System.out.println("eleObj[2] : " + eleObj[2] +"\n");
			System.out.println("-------------------------------");
		}
		
		
		
		objTrans.commit();
		emptyObject( objTrans, myHQLquery);
		System.out.println("*************************** FIN runFetchOperation() **************************************");
	}

	public void emptyObject( Transaction ArgobjTrans, Query<Object[]> ArgMyHQLquery) {
		ArgobjTrans = null;
		ArgMyHQLquery = null;
	}

}
