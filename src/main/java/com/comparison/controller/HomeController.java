package com.comparison.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.comparison.model.Entity;




@Controller 
public class HomeController {
	private static List<Entity> entityList = new ArrayList();
	
	@RequestMapping("index")
	public ModelAndView index(){
		
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("lists", getMav());
		return mav;
	}
	
	@RequestMapping("yearClick")
	public ModelAndView yearClick(){
		
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("lists", sortYear(getMav()));
		return mav;
	}
	
	@RequestMapping("makerClick")
	public ModelAndView makerClick(){
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("lists", sortCharacter(getMav()));
		return mav;
	}
	
	
	
	@RequestMapping("priceClick")
	public ModelAndView priceClick(){
		
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("lists", sortNumber(getMav()));
		return mav;
	}
	
	
	@RequestMapping("tCCRatingClick")
	public ModelAndView tCCRatingClick(){
		
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("lists", sortTCCRating(getMav()));
		return mav;
	}
	
	
	
	@RequestMapping("calculate")
	public ModelAndView calculate(HttpServletRequest req, HttpServletResponse resp){
		String  distance = (String) req.getParameter("distance");
		List<Entity> instance = getMav();
		for(int i=0; i<instance.size(); i++){
			Entity e = (Entity)instance.get(i);
			String hwy = e.getHwyMPG();
			Double result = Double.valueOf(distance)/Double.valueOf(hwy);
			 ((Entity)instance.get(i)).setFullconsumption(result.toString());
		}
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("lists", instance);
		return mav;
	}
	
	
	@RequestMapping("calculate2")
	public ModelAndView calculate2(HttpServletRequest req, HttpServletResponse resp){
		ArrayList list = new ArrayList();
		String  inputayear = (String) req.getParameter("inputayear");
		for(int i=0; i<getMav().size(); i++){
			Entity e = (Entity)(getMav().get(i));
			if(e.getYear().equals(inputayear)){
				list.add(e);
			}
		}
		Integer total= 0;
		for(int j = 0; j<list.size(); j++){
			Entity e = (Entity)list.get(j);
			total = total + Integer.valueOf(e.getHwyMPG());
		}
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("avg", total/list.size());
		mav.addObject("lists", getMav());
		return mav;
	}
	
	
	@RequestMapping("getRandom")
	public ModelAndView getRandom(){
		Entity e = new Entity();
		 int max=4;
	     int min=0;
	     Random random = new Random();
	     int s = random.nextInt(max)%(max-min+1) + min;
		e = getMav().get(s);
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("lists", getMav());
		mav.addObject("e", e);
		return mav;
	}
	
	
	static{
		try { 
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("input.txt");
			InputStreamReader reader = new InputStreamReader(is); 
			BufferedReader br = new BufferedReader(reader); 
			String line = "";
			line = br.readLine();
			System.out.println(line);
			

			while (line != null) {
				line = br.readLine(); 
				System.out.println(line);
				if (line != null) {
					Entity e = new Entity();
					String[] lineList = line.split("\\|");
					e.setMake(lineList[0]);
					e.setModel(lineList[1]);
					e.setColor(lineList[2]);
					e.setYear(lineList[3]);
					e.setPrice(lineList[4]);
					e.settCCRating(lineList[5]);
					e.setHwyMPG(lineList[6]);
					e.setFullconsumption(lineList[7]);
					entityList.add(e);
				}
			}
			
			
			System.out.println(entityList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @param oldList
	 * @return
	 */
	public  List<Entity> sortCharacter(List<Entity> oldList){
		oldList = getMav();
		List<Entity> newList = new ArrayList();
		String str = "";
		for(int i=0;i<oldList.size();i++){
			String make = oldList.get(i).getMake();
			if(i==0){
				str = make;
			}else{
				str = str+"|"+make;
			}
		}
		String[] strArr = str.split("\\|");
		Arrays.sort(strArr);
		for(String s : strArr){
			for(Entity e : oldList){
				if(s.equals(e.getMake())){
					newList.add(e);
				}
			}
		}
		return newList;
	}
	/**
	 * @param oldList
	 * @return
	 */
	public  List<Entity> sortNumber(List<Entity> oldList){
		oldList = getMav();
		List<Entity> newList = new ArrayList();
		String str = "";
		for(int i=0;i<oldList.size();i++){
			String price = oldList.get(i).getPrice();
			price = price.replace("$", "");
			price = price.replaceAll(",","");
			if(i==0){
				str = price;
			}else{
				str = str+"|"+price;
			}
		}
		
        String[] s = splitStringByComma(str);
        int[] ii = new int[s.length];
        for(int i = 0;i<s.length;i++){
            ii[i] =Integer.parseInt(s[i]);
        }
        Arrays.sort(ii);
        for(int a : ii){
        	for(int i=0;i<oldList.size();i++){
            	String price = oldList.get(i).getPrice().replace("$", "").replaceAll(",", "");
            	if(a ==  Integer.parseInt(price)){
            		newList.add(oldList.get(i));
            		oldList.remove(i);
            	}
            }
    	}
        return newList;
	}
	/**
	 * @param oldList
	 * @return
	 */
	public  List<Entity> sortYear(List<Entity> oldList){
		oldList = getMav();
		List<Entity> newList = new ArrayList();
		String str = "";
		for(int i=0;i<oldList.size();i++){
			String year = oldList.get(i).getYear();
			if(i==0){
				str = year;
			}else{
				str = str+"|"+year;
			}
		}
		
        String[] s = splitStringByComma(str);
        int[] ii = new int[s.length];
        for(int i = 0;i<s.length;i++){
            ii[i] =Integer.parseInt(s[i]);
        }
        Arrays.sort(ii);
        for(int a : ii){
        	for(int i=0;i<oldList.size();i++){
            	String year = oldList.get(i).getYear();
            	if(a ==  Integer.parseInt(year)){
            		newList.add(oldList.get(i));
            		oldList.remove(i);
            	}
            }
    	}
        return newList;
	}
	
	
	/**
	 * @param oldList
	 * @return
	 */
	public  List<Entity> sortTCCRating(List<Entity> oldList){
		oldList = getMav();
		List<Entity> newList = new ArrayList();
		String str = "";
		for(int i=0;i<oldList.size();i++){
			String tcc = oldList.get(i).gettCCRating();
			if(i==0){
				str = tcc;
			}else{
				str = str+"|"+tcc;
			}
		}
		
        String[] s = splitStringByComma(str);
        Double[] ii = new Double[s.length];
        for(int i = 0;i<s.length;i++){
            ii[i] =Double.valueOf(s[i]);
        }
        Arrays.sort(ii);
        DecimalFormat    df   = new DecimalFormat("######0.0000000");   
        for(Double a : ii){
        	for(int i=0;i<oldList.size();i++){
            	String tcc = oldList.get(i).gettCCRating();
            	String cc = df.format(Double.valueOf(tcc));
            	if(df.format(a).equals(cc)){
            		newList.add(oldList.get(i));
            		oldList.remove(i);
            	}
            }
    	}
        return newList;
	}
	
	public  String[] splitStringByComma(String source){
        if(source==null||source.trim().equals(""))
             return null;
        StringTokenizer commaToker = new StringTokenizer(source,"\\|");
        String[] result = new String[commaToker.countTokens()];
        int i=0;
        while(commaToker.hasMoreTokens()){
            result[i] = commaToker.nextToken();
            i++;
        }
        return result;
    }
	
	public List<Entity> getMav(){
		try { 

			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("input.txt");
			InputStreamReader reader = new InputStreamReader(is); 
			BufferedReader br = new BufferedReader(reader); 
			String line = "";
			line = br.readLine();
			System.out.println(line);
			ArrayList	entityList = new ArrayList();

			while (line != null) {
				line = br.readLine(); 
				System.out.println(line);
				if (line != null) {
					Entity e = new Entity();
					String[] lineList = line.split("\\|");
					e.setMake(lineList[0]);
					e.setModel(lineList[1]);
					e.setColor(lineList[2]);
					e.setYear(lineList[3]);
					e.setPrice(lineList[4]);
					e.settCCRating(lineList[5]);
					e.setHwyMPG(lineList[6]);
					e.setFullconsumption(lineList[7]);
					entityList.add(e);
				}
			}
			
			return entityList;
			//System.out.println(entityList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
