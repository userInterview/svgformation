package loiz.hibernate.main;
//import loiz.hibernate.beans.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import loiz.hibernate.beans.TableSociete;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;




public class TestHibernateInnerJoin {
	// Cette bibliothèque fetch deux fois mais une seule reqête est exécutée :
	public static void main(String[] args) {		
		myObjSqlInnerJoin unObjHib = new myObjSqlInnerJoin();
		Configuration hibConf = new Configuration().configure("hibernateBaseAcquisitionHUpdate.cfg.xml");
//		hibConf = hibConf.addAnnotatedClass(TableAcquisition.class);
//		hibConf = hibConf.addAnnotatedClass(TableDenrees.class);
//		hibConf = hibConf.addAnnotatedClass(TableOperation.class);
//		hibConf = hibConf.addAnnotatedClass(TablePlaces.class);
//		hibConf = hibConf.addAnnotatedClass(TableSociete.class);

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

class myObjSqlInnerJoin {

	public myObjSqlInnerJoin() {
		super();
	}

	public void runFetchOperation(Session ArgMySess ) { 
		System.out.println("*************************** DEBUT runFetchOperation() **************************************");		
		Transaction objTrans = ArgMySess.beginTransaction();
  		@SuppressWarnings("unchecked")
 		Query<Object[]> mySQLquery = 
 		ArgMySess.createSQLQuery("SELECT SOC.NomSociete ,SOC.PaysSociete,SOC.AdresseSociete, OPE.DateOperation " 
		+ " from Societe SOC inner join TableOperations OPE "
		+ " on SOC.NomSociete = OPE.SocieteOperation"); 		
 		
  		mySQLquery.setCacheable(false);		
  		List<Object[]> objListSelectSocietes = mySQLquery.getResultList() ;
		System.out.println("objListSelectSocietes : " + objListSelectSocietes +"\n");
		
		for (Object[] eleObj:objListSelectSocietes) {
			System.out.println("-------------------------------");
			for(int i=0;i<eleObj.length;i++){						
			System.out.printf( "<"+eleObj[i] +">, ");
			}
			System.out.println("-------------------------------");
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
