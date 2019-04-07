package webapps2017.restful;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * <h1> Convert One Currency to Another!</h>
 * Restful service to convert a value from one currency to another.
 * @author sjd38
 * @version  1.0
 * @since    2017-04-12
 */

@Singleton

/*** Annotate the class so that it exports Employee resources under the relative path /employee ***/
@Path("/conversion")
public class RSConversion {
 
    private final double GBP_to_USD = 1.25;
    private final double GBP_to_EUR = 1.18;
    private final double USD_to_GBP = 0.80;
    private final double USD_to_EUR = 0.94;
    private final double EUR_to_GBP = 0.85;
    private final double EUR_to_USD = 1.06;
    
    public RSConversion() {
    }

/**
 * This method converts the source currency value into the destination currency, and returns the value.
 * @param currency1 This is the source currency
 * @param currency2 This is the destination currency
 * @param amount_of_currency1 This is the source currency value
 * @return The converted currency value will be sent back in the HTTPResponse in JSON format if both currency types exist.
 *         Else a Not_Found status will be returned.
 */
@GET
@Path("/{currency1}/{currency2}/{amount_of_currency1}")
@Produces({"application/json","application/json"})
    public Response getConvertedCurrency(@PathParam("currency1") String currency1, @PathParam("currency2") String currency2, @PathParam("amount_of_currency1") double amount_of_currency1) {
        double newCurrency = 0;
        int    currencyUnsuported = 0;
        switch (currency1){
            case "GBP": 
                switch (currency2){
                    case "USD":
                        newCurrency = (double) Math.round((amount_of_currency1 * GBP_to_USD) * 100)/ 100;
                        break;
                    case "EUR":
                        newCurrency = (double) Math.round((amount_of_currency1 * GBP_to_EUR) * 100)/ 100;
                        break;
                    case "GBP":
                        newCurrency = amount_of_currency1;
                        break;    
                    default:
                        currencyUnsuported = 1;
                }
                break;
            case "USD":
                switch (currency2){
                    case "GBP":
                        newCurrency = (double) Math.round((amount_of_currency1 * USD_to_GBP) * 100)/ 100;
                        break;
                    case "EUR":
                        newCurrency = (double) Math.round((amount_of_currency1 * USD_to_EUR) * 100)/ 100;
                        break;
                    case "USD":
                        newCurrency = amount_of_currency1;
                        break;        
                    default:
                        currencyUnsuported = 1;
                }
                break;
            case "EUR":
                switch (currency2){
                    case "GBP":
                        newCurrency = (double) Math.round((amount_of_currency1 * EUR_to_GBP) * 100)/ 100;
                        break;
                    case "USD":
                        newCurrency = (double) Math.round((amount_of_currency1 * EUR_to_USD) * 100)/ 100;
                        break;
                    case "EUR":
                        newCurrency = amount_of_currency1;
                        break;        
                    default:
                        currencyUnsuported = 1;
                }
                break;
            default:
                currencyUnsuported = 1;
        }
        System.out.println("currencyUnsuported = " + currencyUnsuported);
        if (currencyUnsuported == 1) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            CurrencyJson myJson = new CurrencyJson(currency1,currency2,amount_of_currency1,newCurrency);
            System.out.println("All ok currencyJson created");
            return Response.ok(myJson).build();
        }
    }
    
    
    
    /**********************************************************************************************************************/

    @PostConstruct
    public void init() {
        System.out.println("Singleton Object for this RESTfull Web Service has been created!!");
    }

    @PreDestroy
    public void clean() {
        System.out.println("Singleton Object for this RESTfull Web Service has been cleaned!!");
    }
}
