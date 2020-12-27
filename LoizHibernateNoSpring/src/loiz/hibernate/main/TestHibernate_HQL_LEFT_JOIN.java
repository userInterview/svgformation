package loiz.hibernate.main;
//import loiz.hibernate.beans.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import loiz.hibernate.beans.TableAcquisition;
import loiz.hibernate.beans.TableDenrees;
import loiz.hibernate.beans.TableOperation;
import loiz.hibernate.beans.TableSociete;
import loiz.hibernate.beans.TablePlaces;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;




public class TestHibernate_HQL_LEFT_JOIN {
	// Cette bibliothèque fetch deux fois mais une seule reqête est exécutée :
	public static void main(String[] args) {		
		myObj_HQL_InnerJoin unObjHib = new myObj_HQL_InnerJoin();
		Configuration hibConf = new Configuration().configure("hibernateBaseAcquisitionHUpdate.cfg.xml");
		hibConf = hibConf.addAnnotatedClass(TableAcquisition.class);
		hibConf = hibConf.addAnnotatedClass(TableDenrees.class);
		hibConf = hibConf.addAnnotatedClass(TableOperation.class);
		hibConf = hibConf.addAnnotatedClass(TablePlaces.class);
		hibConf = hibConf.addAnnotatedClass(TableSociete.class);
		

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

class myObj_HQL_InnerJoin {

	public myObj_HQL_InnerJoin() {
		super();
	}

	public void runFetchOperation(Session ArgMySess ) { 
		System.out.println("*************************** DEBUT runFetchOperation() **************************************");		
		Transaction objTrans = ArgMySess.beginTransaction();  		
 		@SuppressWarnings("unchecked")
		Query<Object> mySQLquery = (Query<Object>)(Object)ArgMySess.createQuery("SELECT s.idSociete, s.nomSociete ,s.paysSociete,s.adresseSociete, o.dateOperation FROM TableSociete s "
										+" left JOIN TableOperation o ON s.nomSociete = o.societeOperation");
 		mySQLquery.setCacheable(true);				
 		/*
 		Query<Object> mySQLquery = (Query<Object>)(Object)ArgMySess.createQuery("SELECT s.idSociete, s.nomSociete ,s.paysSociete,s.adresseSociete, o.dateOperation FROM "
		 		 + " TableSociete s JOIN TableOperations o ON s.nomSociete = o.societeOperation") ;  */
  		//mySQLquery.setCacheable(false);		
  		List<Object> objListSelectSocietes = mySQLquery.getResultList() ;
		System.out.println("objListSelectSocietes : " + objListSelectSocietes +"\n");
		int i = 0 ;
		for (Object ele:objListSelectSocietes) {			
			if (i==0) {System.out.println("-----------------------");
					   System.out.println("ele : " + ele);}
			if (ele instanceof TableSociete) {				
				System.out.println("type Societe : <" + ele + ">,") ;}
			else {
				Object[] eleObj = (Object[])ele ;
				System.out.println( "non type Societe :");
				for(int k=0;k<eleObj.length;k++)						
					System.out.printf( "<"+eleObj[k] +">, ");
			     }
			i++;
		}		
		
		objTrans.commit();
		emptyObject( objTrans, mySQLquery);
		System.out.println("\n*************************** FIN runFetchOperation() **************************************");
	}

	public void emptyObject( Transaction ArgobjTrans, Query<Object> ArgMyHQLquery) {
		ArgobjTrans = null;
		ArgMyHQLquery = null;
	}

}
