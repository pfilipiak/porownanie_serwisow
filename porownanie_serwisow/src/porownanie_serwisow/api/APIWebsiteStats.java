/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow.api;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Adrian
 */
public class APIWebsiteStats {
   
   private String website; //serwis
   private Integer traffic; //
   private Integer keywords; //
   private String dataYYYYMM; //data 

   public APIWebsiteStats() {
        this.website = "";
        this.traffic = 0;
        this.keywords = 0;
        this.dataYYYYMM = new SimpleDateFormat("yyyyMM").format(new Date());
    }

    
    public String EntityToString() {
        return 
            "[" + 
            this.website + ", " +
            this.dataYYYYMM + ", " +
            Integer.toString(this.traffic) + ", " + 
            Integer.toString(this.keywords) + "]";
    }
     
    //    
    public void setAttribute(String attr_name, String attr_value) {
        String attr = attr_name.toLowerCase().trim();
        switch (attr) {
            case "hist_date" : this.setDataYYYYMM(attr_value); break;
            case "traffic": this.setTraffic(attr_value); break;
            case "keywords": this.setKeywords(attr_value); break;
        default: break;
        }
    }   

    
    //

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
    }
    
    public void setTraffic(String traffic) {
        this.traffic = Integer.parseInt(traffic.trim());
    }

    public Integer getKeywords() {
        return keywords;
    }

    public void setKeywords(Integer keywords) {
        this.keywords = keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = Integer.parseInt(keywords.trim());
    }

    public String getDataYYYYMM() {
        return dataYYYYMM;
    }

    public void setDataYYYYMM(String dataYYYYMM) {
        this.dataYYYYMM = dataYYYYMM;
    }
    
    
}
