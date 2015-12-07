package introsde.assignment.soap.ws;
import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.Person;

import java.text.ParseException;
import java.util.List;

import javax.jws.WebService;


/**
 * In this class are implemented all the methods requested
 * by the assignemnt.
 * 
 * This class implements the interface People and defines
 * all methods that must be implemented in order to 
 * connet to the reserver and execute some actions
 * 
 * @author Carlo Nicolo'
 *
 */
@WebService(endpointInterface = "introsde.assignment.soap.ws.People",
    serviceName="PeopleService")
public class PeopleImpl implements People {
	  
    /**
     * This methods is used to get all people stored in the database
     * 
     * Method #1: readPersonList()
     * 
     */
    @Override
    public List<Person> getPeople() {
        return Person.getAll();
    }
    
    /**
     * This method is used to get all information about the person
     * with the "id" specified in the param 
     * 
     * Method #2: readPerson(int id)
     * 
     * 
     * @param id the id of the person that we want read from the database
     */
    @Override
    public Person readPerson(int id) {
        System.out.println("---> Reading Person by id = "+id);
        Person p = Person.getPersonById(id);
        if (p!=null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getName());
        } else {
            System.out.println("---> Didn't find any Person with  id = "+id);
        }
        return p;
    }
    
    /**
     * This method is used to update the information of a person corresponding to
     * the param passed in the method through 
     * 
     * Method #3: updatePerson(Person p)
     * 
     * @param person the person for which we want update the information
     */
    @Override
    public Person updatePerson(Person person) throws ParseException{
        Person.updatePerson(person);
        Person existing = Person.getPersonById(person.getIdPerson());
        //
        int person_id = person.getIdPerson();

        if (existing == null) {
            //If the Person is not presents 
        	System.out.println("There is no Person with the id: " + person_id);
        } else {
            person.setIdPerson(person_id);
            if (person.getName() == null){
            	person.setName(existing.getName());
            }
            if (person.getLastname() == null){
            	person.setLastname(existing.getLastname());
            }
            if (person.getBirthdate() == null){
            	person.setBirthdate(existing.getBirthdate());
            }
            person.setHealthMeasureHistories(existing.getHealthMeasureHistories());
            Person.updatePerson(person);
        }
             
        return person;
    }
    
    /**
     * This method is used to create a new person
     * using the param of Type Person passed through the method
     * 
     * Method #4: createPerson(Person p)
     * 
     * @param person is the person that we want add
     * 
     */
    @Override
    public Person addPerson(Person person) throws ParseException {
    	System.out.println("//////////"+person.getHMHistories());
        Person p = Person.savePerson(person);
        return p;
    }
    
    /**
     * This method is used to create a new person also with a measure
     * using the params passed in the method.
     * 
     * Method #11: createPerson(Person p, HealthMeasureHistory measure)
     * 
     * @param person is the person with all information that we want create
     * @param measure represents the object HealthMeasureHistory containing all information inside 
     * for creating a new measure
     * 
     */
    @Override
	public Person addPersonFull(Person person, HealthMeasureHistory measure) throws ParseException {
    	Person p = Person.savePerson(person);
    	savePersonMeasure(p.getIdPerson(), measure);
		return readPerson(p.getIdPerson());
	}
    
      
    /**
     * This method is used for deleting a person
     * with the id passed as function param
     * 
     * Method #5: deletePerson(int id)
     * 
     * @param id is the id of person that we want delete
     * 
     */
    @Override
    public String deletePerson(int id) {
        Person p = Person.getPersonById(id);
        if (p!=null) {
            Person.removePerson(p);
            String id_person = Integer.toString(p.getIdPerson());
            String messageOk = "The element: "+id_person+" has been deleted";
            return messageOk;
        } else {
        	String id_person = Integer.toString(p.getIdPerson());
        	String messageBad = "The element: "+id_person+" has not been deleted";
            return messageBad;
        }
    }
    
    //Method #6
    /**
     * This method is used for getting the person History,
     * all the measurements of a determinate type for a specified person
     * identified by "id"
     * 
     * Method #6: readPersonHistory(int id, String measureType)
     * 
     * @param id the person id for which we want search the measureType
     * @param measureType the measureType for which we want search the history
     * 
     */
    @Override
    public List<HealthMeasureHistory> readPersonHistory(int id, String measureType){
    	//System.out.println("---> Reading Person by id = "+id);
        Person p = Person.getPersonById(id);
        List<HealthMeasureHistory> history = null;
        if (p!=null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getName());
            history = Person.getHistory(p,measureType);
        } else {
            System.out.println("---> Didn't find any Person with  id = "+id);
        }
    	return history;
    }
    
    
    /**
     * This method is used to get all measureTypes used for the people 
     * stored in the database 
     * 
     * Method #7: readMeasureTypes() 
     * 
     */
    @Override
    public List<String> readMeasureTypes(){
    	List<String> l = HealthMeasureHistory.getMeasureTypes();
    	return HealthMeasureHistory.getMeasureTypes();
    }

    
    
    /**
     * This method is used to get the value of a specified "measureType" for 
     * a person identified by "id"
     * 
     * Method #8: readPersonMeasure(int id, String measureType, int mid)
     * 
     * @param id is the person id 
     * @param measureType is the measure type (e.g: weight) for which we want get the value
     * @param mid is the id of the measure
     * 
     */
	@Override
	public int readPersonMeasure(int id, String measureType, int mid) {
		Person p = Person.getPersonById(id);
        List<HealthMeasureHistory> value = null;
        if (p!=null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getName());
            value = Person.getHistoryValue(p,measureType,mid);
        } else {
            System.out.println("---> Didn't find any Person with  id = "+id);
        }
    	//return history;
        return Integer.parseInt(value.get(0).getValue());
	}

	//Method #9
	/**
	 * This method is used for saving a new person measure.
	 * 
	 * @param id is the id of the person
	 * @param measure is the object containing all measure information to save
	 * 
	 * Method #9: savePersonMeasure(int id, HealthMeasureHistory m)
	 * 
	 */
	@Override
	public List<HealthMeasureHistory> savePersonMeasure(int id, HealthMeasureHistory measure) throws ParseException {
		Person p = Person.getPersonById(id);
		measure.setPerson(p);
		
		HealthMeasureHistory h = HealthMeasureHistory.saveHealthMeasureHistory(measure);
		List<HealthMeasureHistory> history = null;
		history = Person.getHistory(p,measure.getMeasureType());
		
		return history;
	}

	//Method #10
	/**
	 * This method is used for updating the "measure" of a specified person 
	 * identified by "id"
	 * 
	 * Method #10: updatePersonMeasure(int id, HealthMeasureHistory m)
	 * 
	 * @param id is the id of a person
	 * @param measure is the measure object containing all information for updating the HealthMeasureHistory
	 * 
	 */
	@Override
	public HealthMeasureHistory updatePersonMeasure(int id, HealthMeasureHistory measure) throws ParseException {
		Person p = Person.getPersonById(id);
		
		
		HealthMeasureHistory existing = HealthMeasureHistory.getHealthMeasureHistoryById(measure.getIdMeasureHistory());
		
		
		existing.setMeasureType(measure.getMeasureType());
		existing.setTimestamp(measure.getTimestamp());
		existing.setMeasureValueType(measure.getMeasureValueType());
		existing.setValue(measure.getValue());
		
    	//existing.setValue(measure.getValue());
    	HealthMeasureHistory.updateHealthMeasureHistory(existing);
 
		return existing;
	}

	
}
