package org;

import org.treesitter.TSLanguage;
import org.treesitter.TSNode;
import org.treesitter.TSParser;
import org.treesitter.TSPoint;
import org.treesitter.TSQuery;
import org.treesitter.TSQueryCapture;
import org.treesitter.TSQueryCursor;
import org.treesitter.TSQueryMatch;
import org.treesitter.TSTree;
import org.treesitter.TreeSitterJava;
import org.treesitter.TreeSitterJson;

public class TreeSitterTest {

	
	
	
	
	public static void main(String args[]) {
		
		
		String jsonSource = "[1, null]";
		TSParser parser = new TSParser();
		TSLanguage json = new TreeSitterJson();
		TSLanguage java = new TreeSitterJava();
        // set language parser
        parser.setLanguage(java);
        // parser with string input
        String javaSource = "int";
        TSTree tree = parser.parseString(null, javaSource);
        
        TSQuery query = new TSQuery(java, "((type_identifier) @type)\n((integral_type) @type)");
        
        TSQueryCursor cursor = new TSQueryCursor();
        cursor.exec(query, tree.getRootNode());
        
        TSQueryMatch match = new TSQueryMatch();
        
        while(cursor.nextMatch(match)) {
        	TSQueryCapture[] captures =  match.getCaptures();
        	
        	System.out.println("captures len : " + captures.length);
        	
        	for (TSQueryCapture capture : captures) {
        		TSPoint start = capture.getNode().getStartPoint();
        		TSPoint end = capture.getNode().getEndPoint();
        		
        		System.out.println("getType : " + capture.getNode().getGrammarType());
        		System.out.println("start: [" + start.getRow() + ", " + start.getColumn() + "]");
        		System.out.println("end: [" + end.getRow() + ", " + end.getColumn() + "]");
        		 
        	}
        }
        

        
        TSNode rootNode = tree.getRootNode().getChild(0);
        
//        for (int i = 0; i < rootNode.getChildCount(); ++i) {
//        	System.out.println("Child " + i + " :");
//        	System.out.println("Grammar type: " + rootNode.getChild(i).getGrammarType());
//        	System.out.println(rootNode.getChild(i).getType() + " ");
//        	
//       
//        }


        
	}
}

















