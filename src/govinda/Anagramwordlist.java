package govinda;


import java.util.ArrayList;

//imports
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

//class definition

@PersistenceCapable
public class Anagramwordlist {
	// the identifier for the object as we will be using the user key for this
	// we need to use a key object here
	@PrimaryKey
	@Persistent
	private Key id;
	
	@Persistent
	ArrayList<String> wordlist = new ArrayList<>();
	
	// constructor
	public Anagramwordlist(Key id)
	{
		this.id=id;
		wordlist = new ArrayList<String>();
	}
	
	
	public boolean addWord(String word)
	{
	
		if(wordlist.contains(word))
		{
			return false;
		}else
		{
			wordlist.add(word);
			return true;
		}
	}

	public ArrayList<String> getAnagramwordlist(Key user_key)
	{
		return wordlist;
	}
	  
}
