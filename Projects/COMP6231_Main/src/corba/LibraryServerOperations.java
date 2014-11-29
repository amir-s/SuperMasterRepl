package corba;

/**
 * Interface definition: LibraryServer.
 * 
 * @author OpenORB Compiler
 */
public interface LibraryServerOperations
{
    /**
     * Operation registerUser
     */
    public String registerUser(String instName, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password);

    /**
     * Operation reserveBook
     */
    public String reserveBook(String instName, String username, String password, String bookName, String authorName);

    /**
     * Operation setDuration
     */
    public String setDuration(String instName, String adminUsername, String adminPassword, String username, String bookName, String authorName, String days);

    /**
     * Operation reserveInterLibrary
     */
    public String reserveInterLibrary(String instName, String username, String password, String bookName, String authorName);

    /**
     * Operation getNonRetuners
     */
    public String getNonRetuners(String instName, String adminUsername, String adminPassword, String days);

}
