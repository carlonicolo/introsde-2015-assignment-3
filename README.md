# Introsde 2015 Assignment#3(SERVER): SOAP Web Services
This is the third assignment for the "Introduction to Service Design and Engineering" course. The assignment is composed by two parts:
* The **Server** that exposes SOAP Web Services, and that is deployied on Heroku. ***This README is about the server*** 
* The [**Client**](https://github.com/carlonicolo/introsde-2015-assignment-3-client) that call each of server implemented services and prints the result. [**Here**](https://github.com/carlonicolo/introsde-2015-assignment-3-client/blob/master/README.md) there is the README for the client.  

In this assignment is requested to implement a server and a client calling this server. </br>
The server is deployed on Heroku.</br>
This assignment covers:
* [LAB07](https://github.com/IntroSDE/lab07): Reading and writing from Databases & JPA (Java Persistence API)
* [LAB08](https://github.com/IntroSDE/lab08): SOAP Web Services(1)
* [LAB09](https://github.com/IntroSDE/lab09): SOAP Web Services(2)



**Endpoint** </br>
**Service Name**:  http://ws.soap.assignment.introsde/}PeopleService </br> 
**Port Name**: http://ws.soap.assignment.introsde/}PeopleImplPort  </br>

**Information** </br>
**Address**: http://vast-temple-7100.herokuapp.com/ws/people </br>
**WSDL**: http://vast-temple-7100.herokuapp.com/ws/people?wsdl </br>

**Implementation class**: introsde.assignment.soap.ws.PeopleImpl

### Client
The Github repository of the client that i made for connecting to this server: [https://github.com/carlonicolo/introsde-2015-assignment-3-client](https://github.com/carlonicolo/introsde-2015-assignment-3-client)


### Methods
Using JAX-WS, implement CRUD services for the following model including the following operations:

* **Method #1: readPersonList() => List** | should list all the people in the database (see below Person model to know what data to return here) in your database
* **Method #2: readPerson(Long id) => Person** | should give all the Personal information plus current measures of one Person identified by {id} (e.g., current measures means current healthProfile)
* **Method #3: updatePerson(Person p) => Person** | should update the Personal information of the Person identified by {id} (e.g., only the Person's information, not the measures of the health profile)
* **Method #4: createPerson(Person p) => Person** | should create a new Person and return the newly created Person with its assigned id (if a health profile is included, create also those measurements for the new Person).
* **Method #5: deletePerson(Long id)** should delete the Person identified by {id} from the system
* **Method #6: readPersonHistory(Long id, String measureType) => List** should return the list of values (the history) of {measureType} (e.g. weight) for Person identified by {id}
* **Method #7: readMeasureTypes() => List** should return the list of measures
* **Method #8: readPersonMeasure(Long id, String measureType, Long mid) => Measure** should return the value of {measureType} (e.g. weight) identified by {mid} for Person identified by {id}
* **Method #9: savePersonMeasure(Long id, Measure m)** =>should save a new measure object {m} (e.g. weight) of Person identified by {id} and archive the old value in the history
* **Method #10: updatePersonMeasure(Long id, Measure m) => Measure** | should update the measure identified with {m.mid}, related to the Person identified by {id}



## @WebServices
Below is showed the class People.java Interface where are written the methods that have been implemented according to the assignment

```java
@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") int id);

    @WebMethod(operationName="readPersonList")
    @WebResult(name="people") 
    public List<Person> getPeople();

    @WebMethod(operationName="createPerson")
    @WebResult(name="person") 
    public Person addPerson(@WebParam(name="person") Person person) throws ParseException;
    
    @WebMethod(operationName="createFullPerson")
    @WebResult(name="person") 
    public Person addPersonFull(@WebParam(name="person") Person person, @WebParam(name="measure") HealthMeasureHistory measure ) throws ParseException;

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
```

I commented the class [PeopleImpl.java] (https://github.com/carlonicolo/introsde-2015-assignment-3/blob/master/src/introsde/assignment/soap/ws/People.java) , that implements the People.java interface, then if you have any dubtsyou can check the comments that i made there.


## How to run
As specified and requested by the assignment, the server runs on Heroku:
* **Address**: http://vast-temple-7100.herokuapp.com/ws/people 
* **WSDL**: http://vast-temple-7100.herokuapp.com/ws/people?wsdl

So for using this server according to the assignment requests, you need to check the [client](https://github.com/carlonicolo/introsde-2015-assignment-3-client) that i implemented. In the [client README](https://github.com/carlonicolo/introsde-2015-assignment-3-client/blob/master/README.md) i explained how it works and how run it. 


By the way if you want to try the server, using (e.g) the client [Postman](https://www.getpostman.com/), and execute the service readPerson(int id) you need to send the request in this way: </br>

```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://ws.soap.assignment.introsde/">
  <m:readPerson>
      <personId>52</personId>
  </m:readPerson>
</soap:Body>
</soap:Envelope>
```
Where the value of the personId is the id of the person for which we want get information, and the response is:
```xml
<?xml version='1.0' encoding='UTF-8'?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
    <S:Body>
        <ns2:readPersonResponse xmlns:ns2="http://ws.soap.assignment.introsde/">
            <person>
                <personId>53</personId>
                <firstname>Bob</firstname>
                <lastname>Marley</lastname>
                <birthdate>1961-10-11</birthdate>
                <currentHealth>
                    <measure>
                        <mid>53</mid>
                        <dateRegistered>2015-12-07</dateRegistered>
                        <measureType>weight</measureType>
                        <measureValue>85</measureValue>
                        <measureValueType>Int</measureValueType>
                    </measure>
                </currentHealth>
            </person>
        </ns2:readPersonResponse>
    </S:Body>
</S:Envelope>
```
Otherwise another option is to clone the project on your local machine and run the server on your local machine performing these steps:
```
git clone https://github.com/carlonicolo/introsde-2015-assignment-3.git
cd introsde-2015-assignment-3/
ant install
ant start
```
At this point set my client or Postman, with the right endpoint, to consume the services offered by the server. 
