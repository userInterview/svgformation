package loiz.hibernate.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import loiz.hibernate.beans.*;


public class TestClassicSelectSqlDisplayValuesByEntityMemberNames {	
	public static void main(String[] args) {		
		myObjHibMap unObjHib = new myObjHibMap();
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

class myObjHibMap {

	public myObjHibMap() {
		super();
	}

	private void emptyObject( Transaction ArgobjTrans, NativeQuery<TableDenrees>  ArgMySQLquery) {
		ArgobjTrans = null;
		ArgMySQLquery = null;
	}	

	
	private static List<TableDenrees> castList(List<TableDenrees> c) {
	    List<TableDenrees> r = new ArrayList<TableDenrees>(c.size());	      
	    Iterator<TableDenrees> itr = c.iterator();	      
	      while(itr.hasNext()) {
     	     Object[] element =  (Object[])(Object)itr.next();
	         TableDenrees objTable = (TableDenrees)element[0];
	         r.add(objTable);
	      }
	    return r; 
	}
	
	public void runFetchOperation(Session ArgMySess ) { 
		System.out.println("DEBUT runFetchOperation() **************************************");		
		Transaction objTrans = ArgMySess.beginTransaction();
		NativeQuery<TableDenrees> mySQLquery = ArgMySess.createNativeQuery("SELECT * FROM TableDenrees", TableDenrees.class);
  		mySQLquery.addEntity(TableDenrees.class);  		
  		mySQLquery.setCacheable(true);		
   		List<TableDenrees>  objList = mySQLquery.list();
  		List<TableDenrees> objListSelectDenrees = castList(objList);
		int i = 0;
		for (TableDenrees ele:objListSelectDenrees) {
			if(i==0) System.out.println("-------------------------------");			
			System.out.println("<" + ele.getNomDenree() + ">, "+"<" + ele.getTypeDenree() + ">, "+"<" + ele.getUniteDeVenteDenree() + ">, "+"<" + ele.getValeurUniteDenree() + ">, ");
			if ((i+1) == objListSelectDenrees.size()) 	System.out.println("-------------------------------");
			i++;
		}		
		objTrans.commit();
		emptyObject( objTrans, mySQLquery);
		System.out.println("FIN runFetchOperation() **************************************");
	}
	

}
