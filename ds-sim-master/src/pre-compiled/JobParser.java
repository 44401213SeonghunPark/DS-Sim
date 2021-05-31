public class JobParser {
    protected String JobType;
    protected Integer JobId;

    protected Integer JobCoreCount;
    protected Integer JobMemory;
    protected Integer JobDisk;

    public JobParser(String[] fieldBuffer) {
        JobType = fieldBuffer[0];
        JobId = Integer.parseInt(fieldBuffer[2]);
        JobCoreCount = Integer.parseInt(fieldBuffer[4]);
        JobMemory = Integer.parseInt(fieldBuffer[5]);
        JobDisk = Integer.parseInt(fieldBuffer[6].trim()); // remove whitespace
    }
    
    protected Integer JobCoreCount() {
    	return this.JobCoreCount;
    }
}
