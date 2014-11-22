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
     * Operation createAccount
     */
    public corba.CORBAResponse createAccount(String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password, String educationalInstitution)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("createAccount",true);
                    _output.write_string(firstName);
                    _output.write_string(lastName);
                    _output.write_string(emailAddress);
                    _output.write_string(phoneNumber);
                    _output.write_string(username);
                    _output.write_string(password);
                    _output.write_string(educationalInstitution);
                    _input = this._invoke(_output);
                    corba.CORBAResponse _arg_ret = corba.CORBAResponseHelper.read(_input);
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
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("createAccount",_opsClass);
                if (_so == null)
                   continue;
                corba.LibraryServerOperations _self = (corba.LibraryServerOperations) _so.servant;
                try
                {
                    return _self.createAccount( firstName,  lastName,  emailAddress,  phoneNumber,  username,  password,  educationalInstitution);
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
    public corba.CORBAResponse reserveBook(String username, String password, String bookName, String authorName)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("reserveBook",true);
                    _output.write_string(username);
                    _output.write_string(password);
                    _output.write_string(bookName);
                    _output.write_string(authorName);
                    _input = this._invoke(_output);
                    corba.CORBAResponse _arg_ret = corba.CORBAResponseHelper.read(_input);
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
                    return _self.reserveBook( username,  password,  bookName,  authorName);
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
    public corba.CORBAResponse reserveInterLibrary(String username, String password, String bookName, String authorName)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("reserveInterLibrary",true);
                    _output.write_string(username);
                    _output.write_string(password);
                    _output.write_string(bookName);
                    _output.write_string(authorName);
                    _input = this._invoke(_output);
                    corba.CORBAResponse _arg_ret = corba.CORBAResponseHelper.read(_input);
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
                    return _self.reserveInterLibrary( username,  password,  bookName,  authorName);
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
    public corba.CORBAResponse getNonRetuners(String adminUsername, String adminPassword, String educationalInstitution, String numDays)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("getNonRetuners",true);
                    _output.write_string(adminUsername);
                    _output.write_string(adminPassword);
                    _output.write_string(educationalInstitution);
                    _output.write_string(numDays);
                    _input = this._invoke(_output);
                    corba.CORBAResponse _arg_ret = corba.CORBAResponseHelper.read(_input);
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
                    return _self.getNonRetuners( adminUsername,  adminPassword,  educationalInstitution,  numDays);
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
    public corba.CORBAResponse setDuration(String username, String bookName, String numDays)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("setDuration",true);
                    _output.write_string(username);
                    _output.write_string(bookName);
                    _output.write_string(numDays);
                    _input = this._invoke(_output);
                    corba.CORBAResponse _arg_ret = corba.CORBAResponseHelper.read(_input);
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
                    return _self.setDuration( username,  bookName,  numDays);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation show
     */
    public corba.CORBAResponse show()
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("show",true);
                    _input = this._invoke(_output);
                    corba.CORBAResponse _arg_ret = corba.CORBAResponseHelper.read(_input);
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
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("show",_opsClass);
                if (_so == null)
                   continue;
                corba.LibraryServerOperations _self = (corba.LibraryServerOperations) _so.servant;
                try
                {
                    return _self.show();
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

}
