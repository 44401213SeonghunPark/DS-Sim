public class Server {
    protected String ServerName;
    protected Integer ServerId;
    protected Integer ServerCoreCount;
    protected Integer ServerJobsWaiting;


    public Server(String[] fieldBuffer) {
    	ServerName = fieldBuffer[0];
            ServerId = Integer.parseInt(fieldBuffer[1]);
            ServerCoreCount = Integer.parseInt(fieldBuffer[4]);
            ServerJobsWaiting = Integer.parseInt(fieldBuffer[7]); 

    }
    
    protected int ServerCoreCount() {
    	return this.ServerCoreCount;
    }

    protected int ServerJobsWaiting() {
    	return this.ServerJobsWaiting;
    }
}
