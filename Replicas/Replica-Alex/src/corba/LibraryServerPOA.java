package corba;

/**
 * Interface definition: LibraryServer.
 * 
 * @author OpenORB Compiler
 */
public abstract class LibraryServerPOA extends org.omg.PortableServer.Servant
        implements LibraryServerOperations, org.omg.CORBA.portable.InvokeHandler
{
    public LibraryServer _this()
    {
        return LibraryServerHelper.narrow(_this_object());
    }

    public LibraryServer _this(org.omg.CORBA.ORB orb)
    {
        return LibraryServerHelper.narrow(_this_object(orb));
    }

    private static String [] _ids_list =
    {
        "IDL:corba/LibraryServer:1.0"
    };

    public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte [] objectId)
    {
        return _ids_list;
    }

    public final org.omg.CORBA.portable.OutputStream _invoke(final String opName,
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler)
    {

        if (opName.equals("createAccount")) {
                return _invoke_createAccount(_is, handler);
        } else if (opName.equals("getNonRetuners")) {
                return _invoke_getNonRetuners(_is, handler);
        } else if (opName.equals("reserveBook")) {
                return _invoke_reserveBook(_is, handler);
        } else if (opName.equals("reserveInterLibrary")) {
                return _invoke_reserveInterLibrary(_is, handler);
        } else if (opName.equals("setDuration")) {
                return _invoke_setDuration(_is, handler);
        } else if (opName.equals("show")) {
                return _invoke_show(_is, handler);
        } else {
            throw new org.omg.CORBA.BAD_OPERATION(opName);
        }
    }

    // helper methods
    private org.omg.CORBA.portable.OutputStream _invoke_createAccount(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        String arg0_in = _is.read_string();
        String arg1_in = _is.read_string();
        String arg2_in = _is.read_string();
        String arg3_in = _is.read_string();
        String arg4_in = _is.read_string();
        String arg5_in = _is.read_string();
        String arg6_in = _is.read_string();

        corba.CORBAResponse _arg_result = createAccount(arg0_in, arg1_in, arg2_in, arg3_in, arg4_in, arg5_in, arg6_in);

        _output = handler.createReply();
        corba.CORBAResponseHelper.write(_output,_arg_result);

        return _output;
    }

    private org.omg.CORBA.portable.OutputStream _invoke_reserveBook(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        String arg0_in = _is.read_string();
        String arg1_in = _is.read_string();
        String arg2_in = _is.read_string();
        String arg3_in = _is.read_string();

        corba.CORBAResponse _arg_result = reserveBook(arg0_in, arg1_in, arg2_in, arg3_in);

        _output = handler.createReply();
        corba.CORBAResponseHelper.write(_output,_arg_result);

        return _output;
    }

    private org.omg.CORBA.portable.OutputStream _invoke_reserveInterLibrary(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        String arg0_in = _is.read_string();
        String arg1_in = _is.read_string();
        String arg2_in = _is.read_string();
        String arg3_in = _is.read_string();

        corba.CORBAResponse _arg_result = reserveInterLibrary(arg0_in, arg1_in, arg2_in, arg3_in);

        _output = handler.createReply();
        corba.CORBAResponseHelper.write(_output,_arg_result);

        return _output;
    }

    private org.omg.CORBA.portable.OutputStream _invoke_getNonRetuners(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        String arg0_in = _is.read_string();
        String arg1_in = _is.read_string();
        String arg2_in = _is.read_string();
        String arg3_in = _is.read_string();

        corba.CORBAResponse _arg_result = getNonRetuners(arg0_in, arg1_in, arg2_in, arg3_in);

        _output = handler.createReply();
        corba.CORBAResponseHelper.write(_output,_arg_result);

        return _output;
    }

    private org.omg.CORBA.portable.OutputStream _invoke_setDuration(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        String arg0_in = _is.read_string();
        String arg1_in = _is.read_string();
        String arg2_in = _is.read_string();

        corba.CORBAResponse _arg_result = setDuration(arg0_in, arg1_in, arg2_in);

        _output = handler.createReply();
        corba.CORBAResponseHelper.write(_output,_arg_result);

        return _output;
    }

    private org.omg.CORBA.portable.OutputStream _invoke_show(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;

        corba.CORBAResponse _arg_result = show();

        _output = handler.createReply();
        corba.CORBAResponseHelper.write(_output,_arg_result);

        return _output;
    }

}
