/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam.controller;

import com.exam.model.Category;
import com.exam.model.Product;
import com.exam.util.HibernateUtil;
import com.exam.util.Upload;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.FileUploadEvent;
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
        
    
    public ProductController() {
        product = new Product();
        
    }

    public String saveData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            String path = "C:\\Users\\Instructor\\Documents\\app\\FoodsApp\\web\\resources\\upload\\products";
            String hasFile = Upload.uploadFile(path, file);

            if (hasFile == null) {
                massage("File upload failed");
                return "product";
            }
            product.setImgName(hasFile);
            
            session.save(product);
            tx.commit();
            session.flush();
            this.massage("Saved Successfully");
            return "product";
        } catch (Exception e) {
            tx.rollback();
            this.massage("Saved Failed, please try again!");
            e.printStackTrace();

        }
        return "product";
    }

    public List<Category> allCategories() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query =  session.createQuery("From Category");
            List<Category> entityList = (List<Category>) query.list();
            session.flush();
            return entityList;

        } catch (Exception e) {
            this.massage("Not Found");
            e.printStackTrace();
        }
        return null;
    }
    
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
     
    public void handleFileUpload(FileUploadEvent event) {
        this.file = event.getFile();        
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
