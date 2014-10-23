package webspider_TXT;  

import java.util.List;
import java.util.Iterator; 
import java.util.ArrayList;

import org.htmlparser.Node;  
import org.htmlparser.NodeFilter;  
import org.htmlparser.Parser;  
import org.htmlparser.filters.NodeClassFilter;  
import org.htmlparser.filters.OrFilter;  
import org.htmlparser.tags.LinkTag;  
import org.htmlparser.util.NodeList;  
import org.htmlparser.util.ParserException;  

public class Htmlparser {  
	public static void main(String args[])
	{
		String url = "http://ds.eywedu.com/jinyong/tlbb/";
        LinkFilter linkFilter = new LinkFilter(){  
            public boolean accept(String url) {  
                if(url.contains("eywedu"))
                    return true;  
                else  
                    return false;   
            }                
        };  
        List<String> urlList = extractLinks(url, linkFilter);          
        for (Iterator<String> i = urlList.iterator(); i.hasNext();) { 
            String stringRef = i.next(); 
            System.out.println(stringRef); 
            new FileDownLoader().downloadFile(stringRef);	
        }
	}
	
	public static List<String> extractLinks(String url, LinkFilter filter) {  
        List<String> links = new ArrayList<String>();  
        try {  
            Parser parser = new Parser(url);  
            parser.setEncoding("gb2312");  
            NodeFilter frameNodeFilter = new NodeFilter(){  
                public boolean accept(Node node) {  
                    if (node.getText().startsWith("frame src="))  
                        return true;  
                    else   
                        return false;   
                }  
            };               
            NodeFilter aNodeFilter = new NodeClassFilter(LinkTag.class);                
            OrFilter linkFilter = new OrFilter(frameNodeFilter, aNodeFilter);                
            NodeList nodeList = parser.extractAllNodesThatMatch(linkFilter);                
            for(int i = 0; i<nodeList.size();i++)
            {  
                Node node = nodeList.elementAt(i);  
                String linkURL = "";   
                if(node instanceof LinkTag)
                {  
                    LinkTag link = (LinkTag)node;  
                    linkURL= link.getLink();  
                }
                if(filter.accept(linkURL) && !links.contains(linkURL))
                	links.add(linkURL);          
            }  
              
        } catch (ParserException e) {  
            e.printStackTrace();  
        }  
        return links;  
    }  
}  