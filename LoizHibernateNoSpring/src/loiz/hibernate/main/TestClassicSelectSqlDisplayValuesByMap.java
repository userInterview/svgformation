package loiz.hibernate.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import loiz.hibernate.beans.*;


public class TestClassicSelectSqlDisplayValuesByMap {
	// Cette bibliothèque fetch deux fois mais une seule reqête est exécutée :
	public static void main(String[] args) {		
		myObjDenree unObjHib = new myObjDenree();
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

class myObjDenree {

	public myObjDenree() {
		super();
	}

	public void runFetchOperation(Session ArgMySess ) { 
		//System.out.println("*************************** DEBUT runFetchOperation() **************************************");		
		Transaction objTrans = ArgMySess.beginTransaction();
  		@SuppressWarnings("unchecked")
  		NativeQuery<TableDenrees> mySQLquery = ArgMySess.createNativeQuery("SELECT TypeDenree, NomDenree FROM TableDenrees");
  		mySQLquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
  		//mySQLquery.addEntity(TableDenrees.class);  		
  		//mySQLquery.setCacheable(true);		
  		@SuppressWarnings("unchecked")
  		List<Object> objListSelectDenrees = (List<Object>)(Object)mySQLquery.getResultList() ;
		//List<TableDenrees> objListSelectDenrees = (List<TableDenrees>)(Object)mySQLquery.getResultList() ;
		System.out.println("objSelectDenrees : " + objListSelectDenrees +"\n");
		
		for (Object ele:objListSelectDenrees) {
			System.out.println("-------------------------------");	
			System.out.println("<" + ele + ">") ;
			Map myMappedEle = (Map)ele ;
			System.out.println("<" + myMappedEle.get("TypeDenree") + ">, "+"<" + myMappedEle.get("NomDenree") + ">, ");//			
//			System.out.println("<" + ele.getNomDenree() + ">, "+"<" + ele.getTypeDenree() + ">, "+"<" + ele.getUniteDeVenteDenree() + ">, "+"<" + ele.getValeurUniteDenree() + ">, ");
//			@SuppressWarnings("rawtypes")
//			Map eleMap = (Map)(Object)ele;
//			@SuppressWarnings("rawtypes")
//			Collection<Object> myCollec = eleMap.values();
//			System.out.printf("<" + myCollec + ">, ");
//			 Iterator<Object> myIterator = myCollec.iterator();
//		        while(myIterator.hasNext()){
//		            System.out.printf("<" + myIterator.next() + ">, ");
//		        }
			System.out.println("-------------------------------");
		}
		
		objTrans.commit();
		//emptyObject( objTrans, myHQLquery);
		//System.out.println("*************************** FIN runFetchOperation() **************************************");
	}

	public void emptyObject( Transaction ArgobjTrans, Query<Object[]> ArgMyHQLquery) {
		ArgobjTrans = null;
		ArgMyHQLquery = null;
	}

}
