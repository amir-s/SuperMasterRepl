package corba;

/**
 * Struct definition: CORBAResponse.
 * 
 * @author OpenORB Compiler
*/
public final class CORBAResponse implements org.omg.CORBA.portable.IDLEntity
{
    /**
     * Struct member errorCode
     */
    public String errorCode;

    /**
     * Struct member data
     */
    public String data;

    /**
     * Default constructor
     */
    public CORBAResponse()
    { }

    /**
     * Constructor with fields initialization
     * @param errorCode errorCode struct member
     * @param data data struct member
     */
    public CORBAResponse(String errorCode, String data)
    {
        this.errorCode = errorCode;
        this.data = data;
    }

}
