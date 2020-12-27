package loiz.hibernate.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import loiz.hibernate.beans.*;


public class TestHibernateCacheLevelTwo {
	// Cette bibliothèque fetch deux fois mais une seule reqête est exécutée :
	public static void main(String[] args) {
		
		HibernateQueryleveltwo objHibQuery = new HibernateQueryleveltwo();
		Configuration hibConf = new Configuration().configure("hibernateBaseAcquisitionHUpdate.cfg.xml");
		hibConf = hibConf.addAnnotatedClass(TableAcquisition.class);
		hibConf = hibConf.addAnnotatedClass(TableDenrees.class);
		hibConf = hibConf.addAnnotatedClass(TableOperation.class);
		hibConf = hibConf.addAnnotatedClass(TablePlaces.class);

		StandardServiceRegistryBuilder objSerRegBuild = new StandardServiceRegistryBuilder();
		objSerRegBuild = objSerRegBuild.applySettings(hibConf.getProperties());
		ServiceRegistry objSerReg = objSerRegBuild.build();


		try {			
			objHibQuery.runFetchOperation(hibConf, objSerReg);
			objHibQuery.runFetchOperation(hibConf, objSerReg);			
			objHibQuery.runFetchOperation(hibConf, objSerReg);	
			
		}

		catch (Exception Ex) {
			System.err.printf("**************************** Problème : \n", Ex);
			Ex.printStackTrace();
		}

	}

}

class HibernateQueryleveltwo {

	public HibernateQueryleveltwo() {
		super();
	}

	public void runFetchOperation(Configuration ArghibConf, ServiceRegistry ArgobjSerReg ) {
		System.out.println("*************************** DEBUT runFetchOperation() **************************************");
		SessionFactory sessFac = ArghibConf.buildSessionFactory(ArgobjSerReg);
		Session mySess = sessFac.openSession();
		Transaction objTrans = mySess.beginTransaction();
		TableOperation objOperation = new TableOperation();
		objOperation = (TableOperation) mySess.get(TableOperation.class, 1);
		objTrans.commit();
		emptyObject( objTrans, objOperation);
		System.out.println("*************************** FIN runFetchOperation() **************************************");
	}

	public void emptyObject( Transaction ArgobjTrans, TableOperation ArgobjOperation) {
		ArgobjTrans = null;
		ArgobjOperation = null;
	}

}