package introsde.assignment.soap.ws;
import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.Person;

import java.text.ParseException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.eclipse.persistence.history.HistoryPolicy;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") int id);

    @WebMethod(operationName="getPeopleList")
    @WebResult(name="people") 
    public List<Person> getPeople();

    @WebMethod(operationName="createPerson")
    @WebResult(name="person") 
    public Person addPerson(@WebParam(name="person") Person person) throws ParseException;

    @WebMethod(operationName="updatePerson")
    @WebResult(name="person") 
    public Person updatePerson(@WebParam(name="person") Person person) throws ParseException;

    @WebMethod(operationName="deletePerson")
    @WebResult(name="deleteNotification") 
    public String deletePerson(@WebParam(name="personId") int id);
    
    @WebMethod(operationName="readPersonHistory")
    @WebResult(name="measure") 
    public List<HealthMeasureHistory> readPersonHistory(@WebParam(name="personId") int id, @WebParam(name="measureType") String measureType);
    
    @WebMethod(operationName="readMeasureTypes")
    @WebResult(name="measureType") 
    public List<String> readMeasureTypes();
    
    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="measureValue") 
    public int readPersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measureType") String measureType, @WebParam(name="mid") int mid );
    
    @WebMethod(operationName="savePersonMeasure")
    @WebResult(name="measure") 
    public List<HealthMeasureHistory> savePersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") HealthMeasureHistory measure )throws ParseException;
    
    @WebMethod(operationName="updatePersonMeasure")
    @WebResult(name="measure") 
    public HealthMeasureHistory updatePersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") HealthMeasureHistory measure )throws ParseException;
    
}
