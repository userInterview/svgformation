package loiz.hibernate.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.UniqueConstraint;
import javax.persistence.Cacheable;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;
/* The persistent class for the TableDenrees database table */

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)

@NamedNativeQueries({ @NamedNativeQuery(name = "NQTableDenrees",
		// query = "SELECT IDDENREE,
		// TYPEDENREE,NOMDENREE,UNITEDEVENTEDENREE,VALEURUNITEDENREE FROM
		// TABLEDENREES" , 
		query = "SELECT IDDENREE, TYPEDENREE, NOMDENREE,UNITEDEVENTEDENREE,VALEURUNITEDENREE, TABLEACQUISITIONS.IDACQUISITION,"
				+ " TABLEACQUISITIONS.MONTANTACQUISITION FROM TABLEDENREES, TABLEACQUISITIONS "
				+ " WHERE TABLEACQUISITIONS.IDEXTDENREE = TABLEDENREES.IDDENREE", resultClass = TableDenrees.class) })

@SqlResultSetMapping(name = "TableDenreesRequete", columns = { @ColumnResult(name = "TypeDenree"),
		@ColumnResult(name = "NomDenree") })

@SqlResultSetMapping(name = "TableDenrees", entities = {
		@EntityResult(entityClass = loiz.hibernate.beans.TableDenrees.class, fields = {
				@FieldResult(name = "idDenree", column = "IdDenree"),
				@FieldResult(name = "nomDenree", column = "NomDenree"),
				@FieldResult(name = "typeDenree", column = "TypeDenree"),
				@FieldResult(name = "uniteDeVenteDenree", column = "uniteDeVenteDenree"),
				@FieldResult(name = "valeurUniteDenree", column = "ValeurUniteDenree") }) })
@Table(name = "TableDenrees", uniqueConstraints = @UniqueConstraint(columnNames = { "NomDenree", "TypeDenree" }))

// @NamedQuery(name="TableDenree.findAll", query="SELECT t FROM TableDenree t")
public class TableDenrees implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdDenree")
	private int idDenree;

	@Column(name = "NomDenree")
	private String nomDenree;

	@Column(name = "TypeDenree")
	private String typeDenree;

	@Column(name = "UniteDeVenteDenree")
	private String uniteDeVenteDenree;

	@Column(name = "ValeurUniteDenree")
	private String valeurUniteDenree;

	// bi-directional many-to-one association to TableAcquisition
	@OneToMany(mappedBy = "tableDenree")
	private List<TableAcquisition> tableAcquisitions;

	public TableDenrees() {
	}

	public TableDenrees(String Arg_nomDenree, String Arg_typeDenree, String Arg_uniteDeVenteDenree,
			String Arg_valeurUniteDenree) {
		this.setNomDenree(Arg_nomDenree);
		this.setTypeDenree(Arg_typeDenree);
		this.setUniteDeVenteDenree(Arg_uniteDeVenteDenree);
		this.setValeurUniteDenree(Arg_valeurUniteDenree);
	}

	public String getNomDenree() {
		return this.nomDenree;
	}

	public void setNomDenree(String nomDenree) {
		this.nomDenree = nomDenree;
	}

	public String getTypeDenree() {
		return this.typeDenree;
	}

	public void setTypeDenree(String typeDenree) {
		this.typeDenree = typeDenree;
	}

	public String getUniteDeVenteDenree() {
		return this.uniteDeVenteDenree;
	}

	public void setUniteDeVenteDenree(String uniteDeVenteDenree) {
		this.uniteDeVenteDenree = uniteDeVenteDenree;
	}

	public String getValeurUniteDenree() {
		return this.valeurUniteDenree;
	}

	public void setValeurUniteDenree(String valeurUniteDenree) {
		this.valeurUniteDenree = valeurUniteDenree;
	}

	public List<TableAcquisition> getTableAcquisitions() {
		return this.tableAcquisitions;
	}

	public void setTableAcquisitions(List<TableAcquisition> tableAcquisitions) {
		this.tableAcquisitions = tableAcquisitions;
	}

	public TableAcquisition addTableAcquisition(TableAcquisition tableAcquisition) {
		getTableAcquisitions().add(tableAcquisition);
		tableAcquisition.setTableDenree(this);

		return tableAcquisition;
	}

	public TableAcquisition removeTableAcquisition(TableAcquisition tableAcquisition) {
		getTableAcquisitions().remove(tableAcquisition);
		tableAcquisition.setTableDenree(null);

		return tableAcquisition;
	}

	@Override
	public String toString() {
		return "TableDenrees [idDenree=" + idDenree + ", nomDenree=" + nomDenree + ", typeDenree=" + typeDenree
				+ ", uniteDeVenteDenree=" + uniteDeVenteDenree + ", valeurUniteDenree=" + valeurUniteDenree
				+ ", tableAcquisitions=" + tableAcquisitions + "]";
	}

}