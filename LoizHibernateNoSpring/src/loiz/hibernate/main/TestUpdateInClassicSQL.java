package loiz.hibernate.main;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import loiz.hibernate.beans.*;


public class TestUpdateInClassicSQL {
	// Cette bibliothèque fetch deux fois mais une seule reqête est exécutée :
	public static void main(String[] args) {		
		myObjHibUpdateSQLClassic unObjHib = new myObjHibUpdateSQLClassic();
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
			unObjHib.runFetchOperation(mySess);  //Resultat
			unObjHib.runUpdateOperation(mySess); //Ajout d'une data		
			unObjHib.runFetchOperation(mySess);  //Resultat
			unObjHib.runDeleteOperation(mySess); //Suppression de la data
			unObjHib.runFetchOperation(mySess);  //Resultat
		}

		catch (Exception Ex) {
			System.err.printf("**************************** Problème : \n", Ex);
			Ex.printStackTrace();
		}
	}
} 

class myObjHibUpdateSQLClassic {

	public myObjHibUpdateSQLClassic() {
		super();
	}

	public void runDeleteOperation(Session ArgMySess ) { 
		
		System.out.println("*************************** DEBUT runFetchOperation() **************************************");		
		Transaction objTrans = ArgMySess.beginTransaction();
  		@SuppressWarnings({ "unchecked"})
  		NativeQuery<Object[]> mySQLquery = ArgMySess.createNativeQuery("DELETE FROM TableDenrees  WHERE NomDenree = \'Abricot5\'");
  		mySQLquery.executeUpdate();
		objTrans.commit();
		//emptyObject( objTrans, myHQLquery);
		System.out.println("*************************** FIN runFetchOperation() **************************************");
	}
	public void runUpdateOperation(Session ArgMySess ) { 
		System.out.println("*************************** DEBUT runFetchOperation() **************************************");		
		Transaction objTrans = ArgMySess.beginTransaction();
  		@SuppressWarnings({ "unchecked"})
  		NativeQuery<Object[]> mySQLquery = ArgMySess.createNativeQuery("INSERT INTO TableDenrees (NomDenree ,TypeDenree,UniteDeVenteDenree,ValeurUniteDenree) VALUES (\'Abricot5\',\'Fruit\',\'kilos\', 14 )");
  		mySQLquery.executeUpdate();
		objTrans.commit();
		//emptyObject( objTrans, myHQLquery);
		System.out.println("*************************** FIN runFetchOperation() **************************************");
	}
	
	public void runFetchOperation(Session ArgMySess ) { 
		System.out.println("*************************** DEBUT runFetchOperation() **************************************");		
		Transaction objTrans = ArgMySess.beginTransaction();
  		@SuppressWarnings("unchecked")
  		NativeQuery<Object[]> mySQLquery = ArgMySess.createNativeQuery("SELECT * FROM TableDenrees");
  		mySQLquery.addEntity(TableDenrees.class);
  		mySQLquery.setCacheable(true);		
  		@SuppressWarnings("unchecked")
		List<TableDenrees> objListSelectDenrees = (List<TableDenrees>)(Object)mySQLquery.getResultList() ;
		System.out.println("objSelectDenrees : " + objListSelectDenrees +"\n");	
		for (TableDenrees eleObj:objListSelectDenrees) {
			System.out.println("-------------------------------");
			System.out.printf("<"+eleObj.getNomDenree()+">,<"+eleObj.getTypeDenree()+">");
		}
		
		
		
		objTrans.commit();
		//emptyObject( objTrans, myHQLquery);
		System.out.println("*************************** FIN runFetchOperation() **************************************");
	}

	public void emptyObject( Transaction ArgobjTrans, Query<Object[]> ArgMyHQLquery) {
		ArgobjTrans = null;
		ArgMyHQLquery = null;
	}

}
