/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam.controller;

import com.exam.model.Category;
import com.exam.model.Product;
import com.exam.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author farid
 */
@ManagedBean(name = "productCtrl")
@ViewScoped
public class ProductController {

    private UploadedFile file;
    private Product product;
    Session session = null;
    Transaction tx = null;

    public ProductController() {
        product = new Product();
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public String save() {
        tx = session.beginTransaction();
        try {
            session.save(product);
            tx.commit();
            session.flush();
            this.massage("Saved Successfully");
            return "admin/home";
        } catch (Exception e) {
            tx.rollback();
            this.massage("Saved Failed, please try again!");
            e.printStackTrace();

        }
        return "register";
    }
    
    public List<Category> allCategories() {
        tx = session.beginTransaction();
        try {
            Query query = session.createQuery("From Category");
            List<Category> entityList = (List<Category>) query.list();           
            return entityList;

        } catch (Exception e) {
            this.massage("Not Found");
            e.printStackTrace();
        }
        return null;
    }

    public void massage(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(msg));
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    

}
