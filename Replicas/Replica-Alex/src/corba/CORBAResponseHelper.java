package corba;

/** 
 * Helper class for : CORBAResponse
 *  
 * @author OpenORB Compiler
 */ 
public class CORBAResponseHelper
{
    private static final boolean HAS_OPENORB;
    static
    {
        boolean hasOpenORB = false;
        try
        {
            Thread.currentThread().getContextClassLoader().loadClass( "org.openorb.orb.core.Any" );
            hasOpenORB = true;
        }
        catch ( ClassNotFoundException ex )
        {
            // do nothing
        }
        HAS_OPENORB = hasOpenORB;
    }
    /**
     * Insert CORBAResponse into an any
     * @param a an any
     * @param t CORBAResponse value
     */
    public static void insert(org.omg.CORBA.Any a, corba.CORBAResponse t)
    {
        a.insert_Streamable(new corba.CORBAResponseHolder(t));
    }

    /**
     * Extract CORBAResponse from an any
     *
     * @param a an any
     * @return the extracted CORBAResponse value
     */
    public static corba.CORBAResponse extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        if (HAS_OPENORB && a instanceof org.openorb.orb.core.Any) {
            // streamable extraction. The jdk stubs incorrectly define the Any stub
            org.openorb.orb.core.Any any = (org.openorb.orb.core.Any)a;
            try {
                org.omg.CORBA.portable.Streamable s = any.extract_Streamable();
                if ( s instanceof corba.CORBAResponseHolder )
                    return ( ( corba.CORBAResponseHolder ) s ).value;
            }
            catch ( org.omg.CORBA.BAD_INV_ORDER ex )
            {
            }
            corba.CORBAResponseHolder h = new corba.CORBAResponseHolder( read( a.create_input_stream() ) );
            a.insert_Streamable( h );
            return h.value;
        }
        return read( a.create_input_stream() );
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;
    private static boolean _working = false;

    /**
     * Return the CORBAResponse TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            synchronized(org.omg.CORBA.TypeCode.class) {
                if (_tc != null)
                    return _tc;
                if (_working)
                    return org.omg.CORBA.ORB.init().create_recursive_tc(id());
                _working = true;
                org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
                org.omg.CORBA.StructMember _members[] = new org.omg.CORBA.StructMember[ 2 ];

                _members[ 0 ] = new org.omg.CORBA.StructMember();
                _members[ 0 ].name = "errorCode";
                _members[ 0 ].type = orb.get_primitive_tc( org.omg.CORBA.TCKind.tk_string );
                _members[ 1 ] = new org.omg.CORBA.StructMember();
                _members[ 1 ].name = "data";
                _members[ 1 ].type = orb.get_primitive_tc( org.omg.CORBA.TCKind.tk_string );
                _tc = orb.create_struct_tc( id(), "CORBAResponse", _members );
                _working = false;
            }
        }
        return _tc;
    }

    /**
     * Return the CORBAResponse IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:corba/CORBAResponse:1.0";

    /**
     * Read CORBAResponse from a marshalled stream
     * @param istream the input stream
     * @return the readed CORBAResponse value
     */
    public static corba.CORBAResponse read(org.omg.CORBA.portable.InputStream istream)
    {
        corba.CORBAResponse new_one = new corba.CORBAResponse();

        new_one.errorCode = istream.read_string();
        new_one.data = istream.read_string();

        return new_one;
    }

    /**
     * Write CORBAResponse into a marshalled stream
     * @param ostream the output stream
     * @param value CORBAResponse value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, corba.CORBAResponse value)
    {
        ostream.write_string( value.errorCode );
        ostream.write_string( value.data );
    }

}
