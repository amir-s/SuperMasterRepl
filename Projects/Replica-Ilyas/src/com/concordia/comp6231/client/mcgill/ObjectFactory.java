
package com.concordia.comp6231.client.mcgill;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.concordia.comp6231.client.mcgill package. 
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

    private final static QName _ReserveBook_QNAME = new QName("http://comp6231.concordia.com/", "reserveBook");
    private final static QName _CreateAccountResponse_QNAME = new QName("http://comp6231.concordia.com/", "createAccountResponse");
    private final static QName _SetDurationResponse_QNAME = new QName("http://comp6231.concordia.com/", "setDurationResponse");
    private final static QName _ReserveBookResponse_QNAME = new QName("http://comp6231.concordia.com/", "reserveBookResponse");
    private final static QName _ReserveInterLibrary_QNAME = new QName("http://comp6231.concordia.com/", "reserveInterLibrary");
    private final static QName _CreateAccount_QNAME = new QName("http://comp6231.concordia.com/", "createAccount");
    private final static QName _GetNonReturners_QNAME = new QName("http://comp6231.concordia.com/", "getNonReturners");
    private final static QName _GetNonReturnersResponse_QNAME = new QName("http://comp6231.concordia.com/", "getNonReturnersResponse");
    private final static QName _ReserveInterLibraryResponse_QNAME = new QName("http://comp6231.concordia.com/", "reserveInterLibraryResponse");
    private final static QName _SetDuration_QNAME = new QName("http://comp6231.concordia.com/", "setDuration");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.concordia.comp6231.client.mcgill
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
     * Create an instance of {@link GetNonReturnersResponse }
     * 
     */
    public GetNonReturnersResponse createGetNonReturnersResponse() {
        return new GetNonReturnersResponse();
    }

    /**
     * Create an instance of {@link GetNonReturners }
     * 
     */
    public GetNonReturners createGetNonReturners() {
        return new GetNonReturners();
    }

    /**
     * Create an instance of {@link CreateAccount }
     * 
     */
    public CreateAccount createCreateAccount() {
        return new CreateAccount();
    }

    /**
     * Create an instance of {@link SetDurationResponse }
     * 
     */
    public SetDurationResponse createSetDurationResponse() {
        return new SetDurationResponse();
    }

    /**
     * Create an instance of {@link CreateAccountResponse }
     * 
     */
    public CreateAccountResponse createCreateAccountResponse() {
        return new CreateAccountResponse();
    }

    /**
     * Create an instance of {@link ReserveBook }
     * 
     */
    public ReserveBook createReserveBook() {
        return new ReserveBook();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://comp6231.concordia.com/", name = "reserveBook")
    public JAXBElement<ReserveBook> createReserveBook(ReserveBook value) {
        return new JAXBElement<ReserveBook>(_ReserveBook_QNAME, ReserveBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://comp6231.concordia.com/", name = "createAccountResponse")
    public JAXBElement<CreateAccountResponse> createCreateAccountResponse(CreateAccountResponse value) {
        return new JAXBElement<CreateAccountResponse>(_CreateAccountResponse_QNAME, CreateAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetDurationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://comp6231.concordia.com/", name = "setDurationResponse")
    public JAXBElement<SetDurationResponse> createSetDurationResponse(SetDurationResponse value) {
        return new JAXBElement<SetDurationResponse>(_SetDurationResponse_QNAME, SetDurationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://comp6231.concordia.com/", name = "reserveBookResponse")
    public JAXBElement<ReserveBookResponse> createReserveBookResponse(ReserveBookResponse value) {
        return new JAXBElement<ReserveBookResponse>(_ReserveBookResponse_QNAME, ReserveBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveInterLibrary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://comp6231.concordia.com/", name = "reserveInterLibrary")
    public JAXBElement<ReserveInterLibrary> createReserveInterLibrary(ReserveInterLibrary value) {
        return new JAXBElement<ReserveInterLibrary>(_ReserveInterLibrary_QNAME, ReserveInterLibrary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://comp6231.concordia.com/", name = "createAccount")
    public JAXBElement<CreateAccount> createCreateAccount(CreateAccount value) {
        return new JAXBElement<CreateAccount>(_CreateAccount_QNAME, CreateAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNonReturners }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://comp6231.concordia.com/", name = "getNonReturners")
    public JAXBElement<GetNonReturners> createGetNonReturners(GetNonReturners value) {
        return new JAXBElement<GetNonReturners>(_GetNonReturners_QNAME, GetNonReturners.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNonReturnersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://comp6231.concordia.com/", name = "getNonReturnersResponse")
    public JAXBElement<GetNonReturnersResponse> createGetNonReturnersResponse(GetNonReturnersResponse value) {
        return new JAXBElement<GetNonReturnersResponse>(_GetNonReturnersResponse_QNAME, GetNonReturnersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveInterLibraryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://comp6231.concordia.com/", name = "reserveInterLibraryResponse")
    public JAXBElement<ReserveInterLibraryResponse> createReserveInterLibraryResponse(ReserveInterLibraryResponse value) {
        return new JAXBElement<ReserveInterLibraryResponse>(_ReserveInterLibraryResponse_QNAME, ReserveInterLibraryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetDuration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://comp6231.concordia.com/", name = "setDuration")
    public JAXBElement<SetDuration> createSetDuration(SetDuration value) {
        return new JAXBElement<SetDuration>(_SetDuration_QNAME, SetDuration.class, null, value);
    }

}
