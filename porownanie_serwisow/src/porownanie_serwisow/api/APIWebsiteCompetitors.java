/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porownanie_serwisow.api;

/**
 *
 * @author Adrian
 */
public class APIWebsiteCompetitors {
   
   private String competitor; //
   private Integer traffic; //
   private Integer keywords; //
   private Integer commonKeywords; //
   private Float relevance;

   public APIWebsiteCompetitors() {
        this.competitor = "";
        this.traffic = 0;
        this.keywords = 0;
        this.commonKeywords = 0;
        this.relevance = 0.0f;
    }

    
    public String EntityToString() {
        return 
            "[" + 
            this.competitor + ", " +
            Float.toString(this.relevance) + ", " + 
            Integer.toString(this.commonKeywords) + ", " + 
            Integer.toString(this.traffic) + ", " + 
            Integer.toString(this.keywords) + "]";
    }
     
    //    
    public void setAttribute(String attr_name, String attr_value) {
        String attr = attr_name.toLowerCase().trim();
        switch (attr) {
            case "competitor" : this.setCompetitor(attr_value); break;
            case "relevance" : this.setRelevance(attr_value); break;
            case "commonkeywords" : this.setCommonKeywords(attr_value); break;
            case "traffic": this.setTraffic(attr_value); break;
            case "keywords": this.setKeywords(attr_value); break;
        default: break;
        }
    }   

    
    //

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
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

    public Integer getCommonKeywords() {
        return commonKeywords;
    }

    public void setCommonKeywords(Integer commonKeywords) {
        this.commonKeywords = commonKeywords;
    }
    
    public void setCommonKeywords(String commonKeywords) {
        this.commonKeywords = Integer.parseInt(commonKeywords.trim());
    }

    public Float getRelevance() {
        return relevance;
    }

    public void setRelevance(Float relevance) {
        this.relevance = relevance;
    }
    
    public void setRelevance(String relevance) {
        this.relevance = Float.parseFloat(relevance.trim());
    }


    
    
}
