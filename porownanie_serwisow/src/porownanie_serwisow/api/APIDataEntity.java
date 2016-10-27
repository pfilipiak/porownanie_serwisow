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
public class APIDataEntity {
   private String keyword;
   private String landingPage;
   private Byte position; //1-20
   private Integer volumen;
   private Float trafficShare; //procent, ddd.ff
   private Long timeStamp; 
   
   public APIDataEntity() {
        
    }

    public APIDataEntity(APIDataEntity apiDE) {
        this.keyword = apiDE.getKeyword();
        this.landingPage = apiDE.getLandingPage();
    }


   public void setKeyword(String keyword){
        this.keyword = keyword;  
    }
   
   public void setLandingPage(String landingPage){
        this.landingPage = landingPage;  
    }
   
   public String getKeyword(){
       return this.keyword;  
   }
      
   public String getLandingPage(){
        return this.landingPage;
    }
    
      
}
