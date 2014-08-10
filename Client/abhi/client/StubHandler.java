/** *  */package abhi.client;import java.io.IOException;import java.lang.reflect.InvocationHandler;import java.lang.reflect.Method;import java.net.Socket;import java.net.UnknownHostException;import abhi.utility.*;import abhi.utility.BaseSignal.SignalType;/** * @author abhisheksharma, dkrew * */public class StubHandler implements InvocationHandler {	/* (non-Javadoc)	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])	 */	private RemoteRef remoteRef;		public StubHandler(RemoteRef ref)	{		this.remoteRef = ref;	}		@Override	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {		// TODO Auto-generated method stub		Socket clientSocket = null;				InvokeMethodSignal invokeSignal = new InvokeMethodSignal(remoteRef.getClass_Name(), method.getName(), method.getReturnType().getName(), args, method.getDeclaringClass());				try		{			// Connecting to the server			clientSocket = new Socket(remoteRef.getIp_Address(), remoteRef.getPort());						// packing signal and sending it			HelperUtility.sendSignal(clientSocket, invokeSignal);			// Receive signal			BaseSignal baseSignal = (BaseSignal) HelperUtility.receiveSignal(clientSocket);						if(baseSignal.getSignalType() == SignalType.InvocationResponse)			{				InvocationResponseSignal responseMsg = (InvocationResponseSignal) baseSignal;								if(responseMsg.getReturnObject() == null)					throw new Exception("Sever returned NULL. Request un-successful");								if(responseMsg.isException())					return new Exception(responseMsg.getExceptionMessage());								else				{					return responseMsg.getReturnObject();				}			}			else 			{				return null;			}					}		catch (UnknownHostException e) 		{		      e.printStackTrace();		} 		catch (IOException e) 		{		     e.printStackTrace();		      		      //TO-DO when this exception comes in I need to go and re-try to get the a new lookup object		    } 		finally {		      if (clientSocket != null) {		        try 		        {		        	clientSocket.close();		        } catch (IOException e) {		          e.printStackTrace();		        }		      }		    }			return null;	}}