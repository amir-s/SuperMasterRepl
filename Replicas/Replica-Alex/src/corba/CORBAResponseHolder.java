package corba;

/**
 * Holder class for : CORBAResponse
 * 
 * @author OpenORB Compiler
 */
final public class CORBAResponseHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal CORBAResponse value
     */
    public corba.CORBAResponse value;

    /**
     * Default constructor
     */
    public CORBAResponseHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public CORBAResponseHolder(corba.CORBAResponse initial)
    {
        value = initial;
    }

    /**
     * Read CORBAResponse from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = CORBAResponseHelper.read(istream);
    }

    /**
     * Write CORBAResponse into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        CORBAResponseHelper.write(ostream,value);
    }

    /**
     * Return the CORBAResponse TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return CORBAResponseHelper.type();
    }

}
