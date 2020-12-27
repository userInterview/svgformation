package loiz.hibernate.beans;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Cacheable;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/* The persistent class for the TableSociete table */

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "Societe")
public class TableSociete {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdSociete")
	private int idSociete;

	@Column(name = "NomSociete")
	private String nomSociete;

	@Column(name = "PaysSociete")
	private String paysSociete;

	@Column(name = "AdresseSociete")
	private String adresseSociete;	
	
	public TableSociete() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TableSociete(String nomSociete, String paysSociete, String adresseSociete) {
		super();
		this.nomSociete = nomSociete;
		this.paysSociete = paysSociete;
		this.adresseSociete = adresseSociete;
	}

	public int getIdSociete() {
		return idSociete;
	}

	public void setIdSociete(int idSociete) {
		this.idSociete = idSociete;
	}

	public String getNomSociete() {
		return nomSociete;
	}

	public void setNomSociete(String nomSociete) {
		this.nomSociete = nomSociete;
	}

	public String getPaysSociete() {
		return paysSociete;
	}

	public void setPaysSociete(String paysSociete) {
		this.paysSociete = paysSociete;
	}

	public String getAdresseSociete() {
		return adresseSociete;
	}

	public void setAdresseSociete(String adresseSociete) {
		this.adresseSociete = adresseSociete;
	}

	@Override
	public String toString() {
		return "TableSociete [idSociete=" + idSociete + ", nomSociete=" + nomSociete + ", paysSociete=" + paysSociete
				+ ", adresseSociete=" + adresseSociete + "]";
	}

}