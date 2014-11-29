package corba;

/**
 * Interface definition: LibraryServer.
 * 
 * @author OpenORB Compiler
 */
public class _LibraryServerStub extends org.omg.CORBA.portable.ObjectImpl
        implements LibraryServer
{
    static final String[] _ids_list =
    {
        "IDL:corba/LibraryServer:1.0"
    };

    public String[] _ids()
    {
     return _ids_list;
    }

    private final static Class _opsClass = corba.LibraryServerOperations.class;

    /**
     * Operation registerUser
     */
    public String registerUser(String instName, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("registerUser",true);
                    _output.write_string(instName);
                    _output.write_string(firstName);
                    _output.write_string(lastName);
                    _output.write_string(emailAddress);
                    _output.write_string(phoneNumber);
                    _output.write_string(username);
                    _output.write_string(password);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("registerUser",_opsClass);
                if (_so == null)
                   continue;
                corba.LibraryServerOperations _self = (corba.LibraryServerOperations) _so.servant;
                try
                {
                    return _self.registerUser( instName,  firstName,  lastName,  emailAddress,  phoneNumber,  username,  password);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation reserveBook
     */
    public String reserveBook(String instName, String username, String password, String bookName, String authorName)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("reserveBook",true);
                    _output.write_string(instName);
                    _output.write_string(username);
                    _output.write_string(password);
                    _output.write_string(bookName);
                    _output.write_string(authorName);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("reserveBook",_opsClass);
                if (_so == null)
                   continue;
                corba.LibraryServerOperations _self = (corba.LibraryServerOperations) _so.servant;
                try
                {
                    return _self.reserveBook( instName,  username,  password,  bookName,  authorName);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation setDuration
     */
    public String setDuration(String instName, String adminUsername, String adminPassword, String username, String bookName, String authorName, String days)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("setDuration",true);
                    _output.write_string(instName);
                    _output.write_string(adminUsername);
                    _output.write_string(adminPassword);
                    _output.write_string(username);
                    _output.write_string(bookName);
                    _output.write_string(authorName);
                    _output.write_string(days);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("setDuration",_opsClass);
                if (_so == null)
                   continue;
                corba.LibraryServerOperations _self = (corba.LibraryServerOperations) _so.servant;
                try
                {
                    return _self.setDuration( instName,  adminUsername,  adminPassword,  username,  bookName,  authorName,  days);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation reserveInterLibrary
     */
    public String reserveInterLibrary(String instName, String username, String password, String bookName, String authorName)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("reserveInterLibrary",true);
                    _output.write_string(instName);
                    _output.write_string(username);
                    _output.write_string(password);
                    _output.write_string(bookName);
                    _output.write_string(authorName);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("reserveInterLibrary",_opsClass);
                if (_so == null)
                   continue;
                corba.LibraryServerOperations _self = (corba.LibraryServerOperations) _so.servant;
                try
                {
                    return _self.reserveInterLibrary( instName,  username,  password,  bookName,  authorName);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation getNonRetuners
     */
    public String getNonRetuners(String instName, String adminUsername, String adminPassword, String days)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("getNonRetuners",true);
                    _output.write_string(instName);
                    _output.write_string(adminUsername);
                    _output.write_string(adminPassword);
                    _output.write_string(days);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("getNonRetuners",_opsClass);
                if (_so == null)
                   continue;
                corba.LibraryServerOperations _self = (corba.LibraryServerOperations) _so.servant;
                try
                {
                    return _self.getNonRetuners( instName,  adminUsername,  adminPassword,  days);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

}
