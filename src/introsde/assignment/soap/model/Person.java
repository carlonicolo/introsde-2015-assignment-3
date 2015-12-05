package introsde.assignment.soap.model;

import introsde.assignment.soap.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Locale;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@Entity  // indicates that this class is an entity to persist in DB
@Table(name="Person") // to whole table must be persisted
@NamedQueries({
	@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p"),
	@NamedQuery(name="Person.currentHealth", query="SELECT h FROM HealthMeasureHistory h "
												+ "WHERE h.person = ?1 "
												+ "GROUP BY h.measureType "
												+ "HAVING h.timestamp = MAX(h.timestamp)"),
	@NamedQuery(name="Person.readHistory", query="SELECT h FROM HealthMeasureHistory h "
												+ "WHERE h.person = ?1 AND h.measureType LIKE ?2")
})
@XmlRootElement
@XmlType(propOrder={"idPerson", "name", "lastname" , "birthdate", "healthMeasureHistories"})
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Person")
    
    @Column(name="idPerson")
    private int idPerson;
    
    @Column(name="lastname")
    private String lastname;
    
    @Column(name="name")
    private String name;
    
    @Column(name="username")
    private String username;
    
    @Temporal(TemporalType.DATE) // defines the precision of the date attribute
    @Column(name="birthdate")
    private Date birthdate; 
    
    @Column(name="email")
    private String email;
    
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<HealthMeasureHistory> healthMeasureHistories; 
    
    // add below all the getters and setters of all the private attributes
    
    // getters
    public int getIdPerson(){
        return idPerson;
    }

    public String getLastname(){
        return lastname;
    }
    @XmlElement(name="firstname")
    public String getName(){
        return name;
    }
    @XmlTransient
    public String getUsername(){
        return username;
    }
    public String getBirthdate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(birthdate);
    }
    @XmlTransient
    public String getEmail(){
        return email;
    }
    
    @XmlElementWrapper(name="currentHealth")
    @XmlElement(name="measure")
    public List<HealthMeasureHistory> getHealthMeasureHistories() {
		return this.getQueryCurrentHealth();
	}
    
    public List<HealthMeasureHistory> getHMHistories() {
		return this.healthMeasureHistories;
	}
    
    // setters
    public void setIdPerson(int idPerson){
        this.idPerson = idPerson;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setBirthdate(String bd) throws ParseException{
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = format.parse(bd);
        this.birthdate = date;
    }
    public void setEmail(String email){
        this.email = email;
    }   
    public void setHealthMeasureHistories(List<HealthMeasureHistory> param) {
		this.healthMeasureHistories = param;
	}
    
    public static Person getPersonById(int personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static List<Person> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public static Person savePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    } 

    public static Person updatePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
    
    public List<HealthMeasureHistory> getQueryCurrentHealth() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<HealthMeasureHistory> list = em.createNamedQuery("Person.currentHealth", HealthMeasureHistory.class)
	    		.setParameter(1, this)
	    		.getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}

	public static List<HealthMeasureHistory> getHistory(Person p, String measureType) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<HealthMeasureHistory> list = em.createNamedQuery("Person.readHistory", HealthMeasureHistory.class)
	    		.setParameter(1, p)
	    		.setParameter(2, measureType)
	    		.getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
}