package corba;

/**
 * Interface definition: LibraryServer.
 * 
 * @author OpenORB Compiler
 */
public interface LibraryServerOperations
{
    /**
     * Operation createAccount
     */
    public corba.CORBAResponse createAccount(String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password, String educationalInstitution);

    /**
     * Operation reserveBook
     */
    public corba.CORBAResponse reserveBook(String username, String password, String bookName, String authorName);

    /**
     * Operation reserveInterLibrary
     */
    public corba.CORBAResponse reserveInterLibrary(String username, String password, String bookName, String authorName);

    /**
     * Operation getNonRetuners
     */
    public corba.CORBAResponse getNonRetuners(String adminUsername, String adminPassword, String educationalInstitution, String numDays);

    /**
     * Operation setDuration
     */
    public corba.CORBAResponse setDuration(String username, String bookName, String numDays);

    /**
     * Operation show
     */
    public corba.CORBAResponse show();

}
