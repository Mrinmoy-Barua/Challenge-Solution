
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MatchProductWithList {
	
	// map contains manufacturer and associate listings using "listings.txt"  file
	static HashMap<String, List<JSONObject>> mapOfManufacturer = new HashMap<String, List<JSONObject>>();

	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		structuringListingsData();
		
		try {
			if (!new File("products.txt").exists()) {
				throw new FileNotFoundException("products.txt");
			}

			if (!new File("listings.txt").exists()) {
				throw new FileNotFoundException("listings.txt");
			}
			
			File file = new File("results.txt");
			if (file.exists()){
			file.delete();	
			}

			BufferedReader br = new BufferedReader(new FileReader(
					"products.txt"));
			String line;

			while ((line = br.readLine()) != null) {
				try {
					jsonObject = (JSONObject) parser.parse(line);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				// get the product tile an word separate by space
				String title = getProductTitle(jsonObject);
				String manufacturer = getManufacturer(jsonObject);

				checkProductWithListing(title, manufacturer);
			}

		} catch (FileNotFoundException e) {			
			System.out.println(e.getMessage());			
		} catch (IOException e) {			
			System.out.println(e.getMessage());	
		}

	}
	
	/**
	 * Get the product title as it is stored in the object, applicable only for 
	 * objects from "product.txt"
	 * @param object JSON Object
	 * @return Product Title
	 */
	private static String getProductTitle(JSONObject object){
		return (String) object.get("product_name");			
	}
	
	/**
	 * Get the list title from the "listings.txt"
	 * @param object JSON Object
	 * @return Formatted product tile in lower case 
	 */
	private static String getListingTitle(JSONObject object){
		String title = (String) object.get("title");		
		title = title.replace('-', ' ');
		title = title.replace('_', ' ');		
		return title.toLowerCase();		
	}
	
	
	/**
	 * Get the manufacturer name
	 * @param object JSON Object
	 * @return Formatted manufacturer name in lower case
	 */
	private static String getManufacturer(JSONObject object){
		String mfName = (String) object.get("manufacturer");
		mfName.replace("-", " ");
		mfName.replace("_", " ");
		return mfName.toLowerCase();		
	}
	
	private static String getFormattedTitle(String prodTitle){
		prodTitle = prodTitle.replace('-', ' ');
		prodTitle = prodTitle.replace('_', ' ');		
		return  prodTitle.toLowerCase();
	}
	
	/**
	 * Check each product from "products.txt" and find out best matches list of product
	 * Write the outcome of the matching in the "result.txt" file
	 * @param prodTitle Title of the product
	 * @param prodManufacturer Manufacturer name
	 */
	private static void checkProductWithListing(String prodTitle, String prodManufacturer){
				
		List<JSONObject> listOfMatchTitle = new ArrayList<JSONObject>();
		
		if(mapOfManufacturer.containsKey(prodManufacturer)){					
			List<JSONObject> listOfObjects = mapOfManufacturer.get(prodManufacturer);
			Iterator<JSONObject> it = listOfObjects.iterator();
			while(it.hasNext()){
				JSONObject singleObject = it.next();
				int count = 0;					
				String formattedTitle =  getFormattedTitle(prodTitle);		
				String[] prodTitleWords = formattedTitle.split("\\s+");
				String listTitle = getListingTitle(singleObject);
				
				TreeSet<String> ts = new TreeSet<String>();
				for(String str: Arrays.asList(listTitle.split(" "))){
					ts.add(str);
				}
				// lets count how many words match
				for (String titleWord : prodTitleWords) {
					Boolean isMatch = ts.contains(titleWord);
					if (isMatch) {
						count++;
					}
				}

				// if all words of product title match with a listing; associate the listing object to the
				// product
				if (count == prodTitleWords.length) {
					listOfMatchTitle.add(singleObject);

					// as each list can be associated with a single product -
					// we can now delete the list as already matched
					listOfObjects.remove(singleObject);

					mapOfManufacturer.put(prodManufacturer, listOfObjects);
				}
			}			
	
			//Write to the "results.txt" file
			writeOnResultFile(prodTitle, listOfMatchTitle);
		}
	}

	
	/**
	 * Read the "listings.txt" file and create map where key is the manufacturer(first word) and value is 
	 * an arrayList of matching JSONObject 
	 */	
	private static void structuringListingsData(){
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try (BufferedReader br = new BufferedReader(new FileReader("listings.txt"))){
			String line;
			while((line = br.readLine()) != null){
				try {
					jsonObject = (JSONObject) parser.parse(line);
				} catch (ParseException e) {
					e.printStackTrace();
				}
		
				//get the manufacturer name and only pick the first word to match with product's manufacturer
				String manufacturerName = getManufacturer(jsonObject);
				if(manufacturerName.contains(" ")){
				   manufacturerName = manufacturerName.trim();
				   manufacturerName	= manufacturerName.substring(0, manufacturerName.indexOf(" ")); 
				}
				
				//if manufacturer name is not in the map, add to the map 
				if(!mapOfManufacturer.containsKey(manufacturerName)){
					mapOfManufacturer.put(manufacturerName, null);
					addListingToMap(mapOfManufacturer, manufacturerName, jsonObject);
				}
				else {
					addListingToMap(mapOfManufacturer, manufacturerName, jsonObject);
				}			
			}
		} catch(IOException e){
			e.printStackTrace();			
		}		
	}

	
	/**
	 * 
	 * @param mapOfManufacturer The map contains manufacturer and associate product object list
	 * @param manufacturerName First word of manufacturer from "listing.txt"
	 * @param jsonObject Product listing details
	 */	
	private static void addListingToMap(HashMap<String,List<JSONObject>> mapOfManufacturer, String manufacturerName, JSONObject jsonObject){
		if(mapOfManufacturer.get(manufacturerName) == null){
			List<JSONObject> listOfObject = new CopyOnWriteArrayList<JSONObject>();
			listOfObject.add(jsonObject);
			mapOfManufacturer.put(manufacturerName, listOfObject);
		}
		else {
			List<JSONObject> listOfObject = mapOfManufacturer.get(manufacturerName);
			listOfObject.add(jsonObject);
			mapOfManufacturer.put(manufacturerName, listOfObject);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private static void writeOnResultFile(String prodTitle, List<JSONObject> list){
		JSONObject obj = new JSONObject();
		obj.put("product_name", prodTitle);
		obj.put("listings", list);
		
		File file =  new File("results.txt");
		
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
		
		try {
			FileWriter fileWritter = new FileWriter(file.getName(),true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(obj.toJSONString());
			bufferWritter.newLine();
			bufferWritter.flush();
			bufferWritter.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
}
