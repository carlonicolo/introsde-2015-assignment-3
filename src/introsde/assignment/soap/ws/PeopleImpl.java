package introsde.assignment.soap.ws;
import introsde.assignment.soap.model.Person;

import java.text.ParseException;
import java.util.List;

import javax.jws.WebService;

//Service Implementation

@WebService(endpointInterface = "introsde.assignment.soap.ws.People",
    serviceName="PeopleService")
public class PeopleImpl implements People {

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

    @Override
    public List<Person> getPeople() {
        return Person.getAll();
    }

    @Override
    public Person addPerson(Person person) {
        //TODO healthProfile add or check if a Person has an healtprofile and do all checking, currentProfile and send currentProfile in historyProfile
    	Person.savePerson(person);
        return person;
    }

    @Override
    public Person updatePerson(Person person) throws ParseException {
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
    /*
    @Override
    public int updatePersonHP(int id, LifeStatus hp) {
        LifeStatus ls = LifeStatus.getLifeStatusById(hp.getIdMeasure());
        if (ls.getPerson().getIdPerson() == id) {
            LifeStatus.updateLifeStatus(hp);
            return hp.getIdMeasure();
        } else {
            return -1;
        }
    }
	*/
}
