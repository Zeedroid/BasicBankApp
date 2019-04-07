package webapps2017.restful;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sjd38
 */
@XmlRootElement(name = "currencyjson")
public class CurrencyJson {
    
    String currency1;
    String currency2;
    double amountCurrency1;
    double amountCurrency2;
    
    public CurrencyJson(String currency1, String currency2, double amountCurrency1, double amountCurrency2){
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.amountCurrency1 = amountCurrency1;
        this.amountCurrency2 = amountCurrency2;
    }
    
    public CurrencyJson(){
        
    }
    
    @XmlElement
    public String getCurrency1(){
        return currency1;
    }
    
    public void setCurrency1(String currency1){
        this.currency1 = currency1;
    }
    
    @XmlElement
    public String getCurrency2(){
        return currency2;
    }
    
    public void setCurrency2(String currency2){
        this.currency2 = currency2;
    }    
    
    @XmlElement
    public double getAmountCurrency1(){
        return amountCurrency1;
    }
    
    public void setAmountCurrency1(double amountCurrency1){
        this.amountCurrency1 = amountCurrency1;
    }        
    
    @XmlElement
    public double getAmountCurrency2(){
        return amountCurrency2;
    }
    
    public void setAmountCurrency2(double amountCurrency2){
        this.amountCurrency2 = amountCurrency2;
    }     
    
}
