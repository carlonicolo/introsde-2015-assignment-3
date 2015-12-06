package introsde.assignment.soap.ws;
import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.Person;

import java.text.ParseException;
import java.util.List;

import javax.jws.WebService;

//Service Implementation

@WebService(endpointInterface = "introsde.assignment.soap.ws.People",
    serviceName="PeopleService")
public class PeopleImpl implements People {
	  
    //Method #1
    @Override
    public List<Person> getPeople() {
        return Person.getAll();
    }
    
    //Method #2
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
    
    //Method #3
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
    
    //Method #4
    @Override
    public Person addPerson(Person person) throws ParseException {
    	System.out.println("//////////"+person.getHMHistories());
        Person p = Person.savePerson(person);
        return p;
    }
    
    
    //Method #4-1
    @Override
	public Person addPersonFull(Person person, HealthMeasureHistory measure) throws ParseException {
    	Person p = Person.savePerson(person);
    	savePersonMeasure(p.getIdPerson(), measure);
		return readPerson(p.getIdPerson());
	}
    
      
    //Method #5
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
    //Method #7
    @Override
    public List<String> readMeasureTypes(){
    	List<String> l = HealthMeasureHistory.getMeasureTypes();
    	return HealthMeasureHistory.getMeasureTypes();
    }

    //Method #8
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
