/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2017.payments.entity;

/**
 *
 * @author sjd38
 */
public class BalanceExceededException extends Exception {
 
    public BalanceExceededException(String msg) {
        super(msg);
    }
}  

