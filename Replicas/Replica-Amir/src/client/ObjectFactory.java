
package client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetNonRetunersResponse_QNAME = new QName("lib", "getNonRetunersResponse");
    private final static QName _ReserveInterLibraryResponse_QNAME = new QName("lib", "reserveInterLibraryResponse");
    private final static QName _SetDuration_QNAME = new QName("lib", "setDuration");
    private final static QName _RegisterUser_QNAME = new QName("lib", "registerUser");
    private final static QName _ReserveBookResponse_QNAME = new QName("lib", "reserveBookResponse");
    private final static QName _ReserveInterLibrary_QNAME = new QName("lib", "reserveInterLibrary");
    private final static QName _GetNonRetuners_QNAME = new QName("lib", "getNonRetuners");
    private final static QName _SetDurationResponse_QNAME = new QName("lib", "setDurationResponse");
    private final static QName _RegisterUserResponse_QNAME = new QName("lib", "registerUserResponse");
    private final static QName _ReserveBook_QNAME = new QName("lib", "reserveBook");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SetDuration }
     * 
     */
    public SetDuration createSetDuration() {
        return new SetDuration();
    }

    /**
     * Create an instance of {@link ReserveInterLibraryResponse }
     * 
     */
    public ReserveInterLibraryResponse createReserveInterLibraryResponse() {
        return new ReserveInterLibraryResponse();
    }

    /**
     * Create an instance of {@link GetNonRetunersResponse }
     * 
     */
    public GetNonRetunersResponse createGetNonRetunersResponse() {
        return new GetNonRetunersResponse();
    }

    /**
     * Create an instance of {@link ReserveInterLibrary }
     * 
     */
    public ReserveInterLibrary createReserveInterLibrary() {
        return new ReserveInterLibrary();
    }

    /**
     * Create an instance of {@link ReserveBookResponse }
     * 
     */
    public ReserveBookResponse createReserveBookResponse() {
        return new ReserveBookResponse();
    }

    /**
     * Create an instance of {@link RegisterUser }
     * 
     */
    public RegisterUser createRegisterUser() {
        return new RegisterUser();
    }

    /**
     * Create an instance of {@link SetDurationResponse }
     * 
     */
    public SetDurationResponse createSetDurationResponse() {
        return new SetDurationResponse();
    }

    /**
     * Create an instance of {@link GetNonRetuners }
     * 
     */
    public GetNonRetuners createGetNonRetuners() {
        return new GetNonRetuners();
    }

    /**
     * Create an instance of {@link ReserveBook }
     * 
     */
    public ReserveBook createReserveBook() {
        return new ReserveBook();
    }

    /**
     * Create an instance of {@link RegisterUserResponse }
     * 
     */
    public RegisterUserResponse createRegisterUserResponse() {
        return new RegisterUserResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNonRetunersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "lib", name = "getNonRetunersResponse")
    public JAXBElement<GetNonRetunersResponse> createGetNonRetunersResponse(GetNonRetunersResponse value) {
        return new JAXBElement<GetNonRetunersResponse>(_GetNonRetunersResponse_QNAME, GetNonRetunersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveInterLibraryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "lib", name = "reserveInterLibraryResponse")
    public JAXBElement<ReserveInterLibraryResponse> createReserveInterLibraryResponse(ReserveInterLibraryResponse value) {
        return new JAXBElement<ReserveInterLibraryResponse>(_ReserveInterLibraryResponse_QNAME, ReserveInterLibraryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetDuration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "lib", name = "setDuration")
    public JAXBElement<SetDuration> createSetDuration(SetDuration value) {
        return new JAXBElement<SetDuration>(_SetDuration_QNAME, SetDuration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "lib", name = "registerUser")
    public JAXBElement<RegisterUser> createRegisterUser(RegisterUser value) {
        return new JAXBElement<RegisterUser>(_RegisterUser_QNAME, RegisterUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "lib", name = "reserveBookResponse")
    public JAXBElement<ReserveBookResponse> createReserveBookResponse(ReserveBookResponse value) {
        return new JAXBElement<ReserveBookResponse>(_ReserveBookResponse_QNAME, ReserveBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveInterLibrary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "lib", name = "reserveInterLibrary")
    public JAXBElement<ReserveInterLibrary> createReserveInterLibrary(ReserveInterLibrary value) {
        return new JAXBElement<ReserveInterLibrary>(_ReserveInterLibrary_QNAME, ReserveInterLibrary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNonRetuners }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "lib", name = "getNonRetuners")
    public JAXBElement<GetNonRetuners> createGetNonRetuners(GetNonRetuners value) {
        return new JAXBElement<GetNonRetuners>(_GetNonRetuners_QNAME, GetNonRetuners.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetDurationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "lib", name = "setDurationResponse")
    public JAXBElement<SetDurationResponse> createSetDurationResponse(SetDurationResponse value) {
        return new JAXBElement<SetDurationResponse>(_SetDurationResponse_QNAME, SetDurationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "lib", name = "registerUserResponse")
    public JAXBElement<RegisterUserResponse> createRegisterUserResponse(RegisterUserResponse value) {
        return new JAXBElement<RegisterUserResponse>(_RegisterUserResponse_QNAME, RegisterUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "lib", name = "reserveBook")
    public JAXBElement<ReserveBook> createReserveBook(ReserveBook value) {
        return new JAXBElement<ReserveBook>(_ReserveBook_QNAME, ReserveBook.class, null, value);
    }

}
