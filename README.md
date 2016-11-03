# Challenge-Solution

## Section A: Coding Challenge
### A1. Assumption:  
Both Product and Listing have manufacturer as a common attribute, along with title that has different contents. But manufacturer at “product.txt” contains only company name, whereas ‘listings.txt’ contains country information too (for example: ‘Sony’ and ‘Sony Canada’). To associate product with listing, I assume that first word of manufacturer at ‘listings.txt’ always tells about the company info.

### A2. Methodology: 
 - At first, a ` HashMap<String, List<JSONObject>` is created, where manufacturer name is used as key and all listings having same manufacturer are added to the list. 
 - Read each single product from 'products.txt' and find out listings from the map
  * if manufacturer is in the map, retrive the list and form TreeSet for the listing's title (performance optimize for string matching) 
  * Check product tile is in the tree, if found that means listing is associated with product
  * Write the matching outcome in the 'results.txt' file 
  * Delete matched lsting from the List as one listing can be associated with a product at most.

### A3. Complexity Analysis:
 - Assume that 'products.txt' size is 'P' and 'listings.txt' size is 'L'
 - Creating the hashmap, needs to read the file 'listings.txt' once; so time complexity is O(L), as well as space needed is O(L)
 - Matching each product with listing map needs O(K.MlogN), K is the number of words in product title, M is the number of listings under a manufecturer and N is the number of unique words in the listing's title. Here we can ignore K as value is likely constant and far less than P or L, so time complexity for product to listings matching is O(P.MlogN)
 - Total time complexity is O(L + P.MlogN)
 
## How to Run
 - Here MatchProductWithList.java is the java source file that needs 'products.txt', 'listings.txt', and JSON opensource library 'json-simple-1.1.1.jar' at the same location
 - Excute the script 'run.sh' and it will generate the 'results.txt' file





