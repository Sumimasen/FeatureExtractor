import java.util.List;
import java.util.Stack;

//This is a class for storing the object information for each candidate
public class TagObject {

	public String TagType;
	public String TagTerm;
	public int Position;
	public int TF;
	
    public TagObject(String ObjTagType, String ObjTagTerm, int ObjPosition, int ObjTF) 
    {
    	TagType = ObjTagType;
    	TagTerm = ObjTagTerm;
    	Position = ObjPosition;
    	TF = 0;
    }
    
    
    public void setObjTF(int Freq)
    {
    	TF = Freq;
    }

}
