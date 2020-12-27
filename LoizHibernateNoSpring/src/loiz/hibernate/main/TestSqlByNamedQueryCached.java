package loiz.hibernate.main;

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

public class TestSqlByNamedQueryCached {
	public static void main(String[] args) {
		myObjDenreeSqlResultSetMapping unObjHib = new myObjDenreeSqlResultSetMapping();
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

class myObjDenreeSqlResultSetMapping {

	public myObjDenreeSqlResultSetMapping() {
		super();
	}

	public void runFetchOperation(Session ArgMySess ) { 
		System.out.println("*************************** DEBUT runFetchOperation() **************************************");		
		Transaction objTrans = ArgMySess.beginTransaction();
		@SuppressWarnings("unchecked")
		Query<TableDenrees> MySQLquery  = ArgMySess.createNamedQuery("NQTableDenrees", TableDenrees.class) ;
		//MySQLquery.addEntity(TableDenrees.class);  		
		MySQLquery.setCacheable(true);	
		List<TableDenrees>  objListSelectDenrees =  MySQLquery.getResultList();		
		
		System.out.println("objListSelectDenrees : " + objListSelectDenrees);
		int i = 0 ;
		for (Object ele:objListSelectDenrees) {			
			if (i==0) {System.out.println("-----------------------\n------------------------");
					   System.out.println("ele : " + ele);}
			if (ele instanceof TableDenrees) {
				TableDenrees eleObj = (TableDenrees)ele ;
				System.out.println("<" + eleObj.getTypeDenree() + ">,"+"<" + eleObj.getNomDenree() + ">"+"<" + eleObj.getTableAcquisitions() + ">") ;	}							
			else
				System.out.println("non type denree : <" + ele + ">") ;
			i++;
		}		
		objTrans.commit();
		emptyObject( objTrans, MySQLquery);
		System.out.println("*************************** FIN runFetchOperation() **************************************");
	}

	public void emptyObject(Transaction ArgobjTrans, Query<TableDenrees> ArgMySQLquery) {
		ArgobjTrans = null;
		ArgMySQLquery = null;
	}

}
