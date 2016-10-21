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

## Section B: Personal Information
### About Me:
My name is Mrinmoy Barua and since July 2014 serving at PTC, Waterloo as Software Engineer. My work responsibilities include full lifecycle development of related software modules that integrate with the core Application Lifecycle Management (ALM) product. In my daily work life, I usually go through troubleshoot, debug and reported bug fixing found in large existing code. Having the market driven attitude and active participation in a team environment, I work on different features that add extra value to the core product. In addition, optimize different modules to have better performance and working on test automation with related architecture ensures outstanding quality of the deliverable module.  

Before starting my current job, I have completed my Ph.D. in Electrical and Computer Engineering from the University of Waterloo. My research work focused on user-centric access control in the cloud environment, as well as secure data communication in a distributed environment. 

My various academic projects and research work demonstrate my analytical and problem-solving abilities as well as being receptive to new and challenging ideas. Working as a project coordinator has allowed me to develop excellent teamwork and problem-solving skills, which I understand would be valuable for the open position at Sortable.
 
### Why Dream to Work at Sortable:
I always dream a career where I will serve as full stack developer. Looking at the job description, technologies and the type of work, I found myself as Sortable is the right place where I can build my career with the company’s growth. 

### Software Experiences:
 - Language : Best With: Java, C/C++, SQL; Proficient With: MATLAB, Perl, PHP, Assembly, UML, HTML, JavaScript, Python
 - Database : Oracle, MySQL, SQL Server, NoSQL (MongoDB)
 - Tools : Eclipse, NetBeans, IntelliJ, Visual Studio, Apache Maven, JUnit, JSON, OpenGL, LabVIEW
 - Elec. Design Tools: Cadence Design System, HSPICE, Xilinx FPGA ISE, OrCAD, VHDL
 - Concepts: Object Oriented Analysis/Design (OOA/OOD), Agile Methodologies, Hadoop Distributed File System,    Applied Cryptography, Wireless networks and Communications Protocols

### Education:
 - PhD, Electrical and Computer Engineering, 	   			             July, 2014,  
   University of Waterloo, Ontario, Canada, 
   GPA: 88.6/100, 
	 Research Topic: Secure Data Aggregation and Access Control in Cloud Assisted Health Care System

- Master of Science, Electrical and Computer Engineering,          Aug 2008, 
  University of Western Ontario, Ontario, Canada, 
	GPA: 88.2/100
  Research Topic: Efficient ASIC implementation of Encryption algorithm in deep-submicron technology
  for secure data communications

- Bachelor of Science, Computer Science and Engineering,           April 2000, 
  University of Asia Pacific, Bangladesh
  GPA: 3.9/4.00





