package sam.bee.rmi.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

@SuppressWarnings({ "rawtypes", "unchecked" })
public interface IHello extends Remote {


    public String helloWorld() throws RemoteException;


    public String sayHelloToSomeBody(String someBodyName) throws RemoteException;
}
