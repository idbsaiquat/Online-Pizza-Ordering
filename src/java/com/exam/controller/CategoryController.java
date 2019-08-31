/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author farid
 */
@ManagedBean(name = "catCtrl")
@ViewScoped
public class CategoryController {
    
    public String save(){
        
        return "category";
    }
}
