package mychessmate;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Melvic
 */
public class Resource {
    protected static ResourceBundle resources;
    static{
        try{
            resources = ResourceBundle.getBundle("mychessmate.res.MyChessmateProperties",Locale.getDefault());
        }catch(Exception e){
            System.out.println("Mychessmate properties not found");
            javax.swing.JOptionPane.showMessageDialog(null,
                    "MyChessmate properties not found",
                    "Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    public String getResourceString(String key){
        String str;
        try{
            str = resources.getString(key);
        }catch(Exception e){
            str = null;
        }
        return str;
    }
    protected URL getResource(String key){
        String name = getResourceString(key);
        if(name != null){
            URL url = this.getClass().getResource(name);
            return url;
        }
        return null;
    }
}
