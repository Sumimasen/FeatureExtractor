import java.io.BufferedReader;


import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.sql.SQLException;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException,
    ClassNotFoundException {
		// TODO Auto-generated method stub

		System.out.println("Hello World");
		
		// Initialize the tagger
		MaxentTagger tagger = new MaxentTagger("taggers/left3words-wsj-0-18.tagger");

		String sample = "";
		String tagged = "";
        
        FileInputStream fstream = new FileInputStream("input.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        FileWriter q = new FileWriter("output.txt",false);	//append flag
        BufferedWriter out =new BufferedWriter(q);
        
        //we will now pick up sentences line by line from the file input.txt and store it in the string sample
        while((sample = br.readLine())!=null)
        {
        //tag the string
        tagged = tagger.tagString(sample);
        
        
      
        //write it to the file output.txt
        out.write(tagged);
        
        System.out.println(tagged);
        
        out.newLine();
       
        }
        out.close();
        ParseDoc();
	}
	
	
	public static void ParseDoc() throws IOException
	{
		System.out.println("In ParseDoc");
		
		FileInputStream fstream = new FileInputStream("output.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        List<TagObject> myObjList = new ArrayList<TagObject>();
        
        Scanner scan = new Scanner( new File("output.txt"));  
        scan.useDelimiter("\\Z");  
        String sample = scan.next(); 

//        String sample = new Scanner(new File("output.txt")).useDelimiter("\\Z").next();
                    
        
        System.out.println("Line:");
        String[] words = sample.split(" ");
        
        System.out.println(sample);
        
        for (int i = 0; i < words.length; i++)
        {
	       	System.out.println(words[i]);
	       	 
	       	TagObject newObject;
	       	 
	       	String[] wordParts = words[i].split("/");	//[0] will be the term, [1] will be the type

	       	if ((wordParts[1].contains("NN")) || (wordParts[1].contains("NNS")) || (wordParts[1].contains("NNP")) || (wordParts[1].contains("NNPS")))
	       	{
	       		String Phrase = wordParts[0];
	       		
	       		for (int j = i+1; j < words.length; j++) 
       			{
	       			String futureWord = words[j].split("/")[1];
	       			
       				if ((futureWord.contains("NN")) || (futureWord.contains("NNS")) || (futureWord.contains("NNP")) ||(futureWord.contains("NNPS")))
       				{
       					Phrase = Phrase + " " + words[j].split("/")[0];
       					i = j;
       				}
       				else
       					break;
       					
       			}
	       		
	       		newObject = new TagObject(wordParts[1], Phrase, i, 0);
	       		myObjList.add(newObject);
	       	}
        }
        
        
        System.out.println(myObjList.size());
        
        GetTermFreq(myObjList);
        
        for (int i = 0; i < myObjList.size(); i++)
        	System.out.println(myObjList.get(i).TagTerm);
        
        //Try out DB
//        Database mysql = new Database(); // init database interface object
//        try{
//        	
//        	boolean IsSuccessful = mysql.connectDB("root", "password", "SaberChan", "keywords");
//        	
//        }catch (SQLException ex) {
//            System.out.println(ex.toString());
//        }
        

	}
	
	public static void GetData()
	{
		
	}
	
	
	
	
	public static void GetTermFreq(List<TagObject> myObjList)
    {
		System.out.println("GetTermFreq: List Size -> ");
		System.out.println(myObjList.size());
		
    	Stack aStack = new Stack();
    	
    	for (int i = 0; i < myObjList.size(); i++)
    	{
    		String BaseType = myObjList.get(i).TagType;
    		String BaseTerm = myObjList.get(i).TagTerm;
    		int Freq = 1;
    		
    		//System.out.println(BaseTerm);
    		//System.out.println(BaseType);
    		
        	for (int j = i+1; j < myObjList.size(); j++)
        	{
        		if ((BaseType.equals(myObjList.get(j).TagType)) && (BaseTerm.equals(myObjList.get(j).TagTerm)))	//Checks the type and term. Not sure if there will be a case where a term can be tagged as different types 
        		{
        			Freq++;
        			aStack.push ( new Integer(j) );
        		}
        	}
        	
        	while ( !aStack.empty() )
            {
        		
        		Integer stackValue = (Integer) aStack.pop();
        		myObjList.get(stackValue).setObjTF(Freq);
        		System.out.println(Freq);
            }
    	}
    	
    	System.out.println("Printing TF: ");
    	
    	for (int i = 0; i < myObjList.size(); i++)
    		System.out.println(myObjList.get(i).TF);
    }
	
	
	public static void ParseTags()
	{
		Boolean getNoun = false;
		
		if (getNoun)	//If we decide that all nouns are candidates
			ParseNoun();
	}
	
	//pass in features that we need to extract as parameters
	private static void ParseNoun()
	{
//		FileInputStream fstream = new FileInputStream("Noun_tags.txt");
//        DataInputStream in = new DataInputStream(fstream);
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        
//        String sample = "";
//		String tagged = "";
//		
//		int NumOfCandiates = 0;
//		
//        //we will now pick up sentences line by line from the file input.txt and store it in the string sample
//        while ((sample = br.readLine()) != null)
//        {
//        	NumOfCandiates++;
//        	
//	        //tag the string
//	        tagged = tagger.tagString(sample);
//	        FileWriter q = new FileWriter("output.txt",true);
//	        BufferedWriter out =new BufferedWriter(q);
//	        //write it to the file output.txt
//	        out.write(tagged);
//	        out.newLine();
//	        out.close();
//        }
	}

	
}
