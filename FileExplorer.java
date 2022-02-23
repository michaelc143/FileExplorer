import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FileExplorer {
	
	// Returns a list of the names of all files and directories in
	// the the given folder.
	// Throws NotDirectoryException with a description error message if
	// the provided currentFolder does not exist or if it is not a directory
	public static ArrayList<String> listContents(File currentFolder) throws NotDirectoryException 
	{
		//if the file isn't a directory or is a file, then a NotDirectoryException is thrown
		if(currentFolder.isDirectory()==false||currentFolder.isFile()==true)
			throw new NotDirectoryException("Given folder is not a directory");
		//ArrayList created for return made of string objects
		ArrayList<String> files = new ArrayList<String>();
		//contents array copies over all files from currentFolder
		String[] contents = currentFolder.list();
		//loop iterates through and adds all file and directory names to the files array
		for(int i=0; i<contents.length; i++)
		{
			files.add(contents[i]);
		}
		return files;
	}
	// Recursive method that lists the names of all the files (not directories)
	// in the given folder and its sub-folders.
	// Throws NotDirectoryException with a description error message if
	// the provided currentFolder does not exist or if it is not a directory
	public static ArrayList<String> deepListContents (File currentFolder) throws NotDirectoryException 
	{
		//throws NotDirectoryException if currentFolder is not a directory or if it doesn't exist
		if(currentFolder.exists()!=true||currentFolder.isDirectory()==false)
			throw new NotDirectoryException("Folder does not exist or is not a correctly formatted directory");
		//ArrayList created to return the names of all the files in the currentFolder
		ArrayList<String> files = new ArrayList<String>();
		//contents array copies over all files and directories from currentFolder
		File[]contents = currentFolder.listFiles();
		//for loop iterates through everything in contents
		for(int i=0; i<contents.length; i++)
		{
			//if its a directory, recursion occurs and runs through again
			if(contents[i].isDirectory())
				files.addAll(deepListContents(contents[i]));
			//if its a file, the name of the file is added to the files ArrayList
			else if (contents[i].isFile()==true)
				files.add(contents[i].getName());
		}
		return files;	
	}
	// Searches the given folder and all of its subfolders for an exact match
	// to the provided fileName. This method must be recursive or must use a
	// recursive private helper method to operate.
	// This method returns a path to the file, if it exists.
	// Throws NoSuchElementException with a descriptive error message if the
	// search operation returns with no results found (including the case if
	// fileName is null or currentFolder does not exist, or was not a directory)
	public static String searchByName (File currentFolder, String fileName)
	{
		//if the fileName is null, the folder isn't a directory, or the folder doesn't exist,
		//a NoSuchElementException is thrown
		if(fileName==null||currentFolder.isDirectory()==false||currentFolder.exists()==false)
			throw new NoSuchElementException("Directory not found/Does not exist");
		//contents array copies over all files and directories from the currentFolder file
		File[]contents = currentFolder.listFiles();
		//loop iterates through contents to see if there's any files with matching name
		for(int i=0; i<contents.length; i++)
		{
			//if the object is a file and it has the name you're looking for, it returns the path
			//if the object is a directory, it recurs and goes through the 
			//directory again to look for matching files
			if(contents[i].isDirectory())
			{
				 return(searchByName(contents[i], fileName));
			}
			else if(contents[i].isFile()&&contents[i].getName().equals(fileName))
			{
				return contents[i].getPath();
			}
		}
		//if it makes it all the way to the end and doesn't return,
		//a NoSuchElementException is thrown 
		throw new NoSuchElementException("No results for search with file name: " +fileName); 
	}
	// Recursive method that searches the given folder and its subfolders
	// for ALL files that contain the given key in part of their name.
	// Returns An arraylist of all the names of files that match and an empty arraylist
	// when the operation returns with no results found (including the case where
	// currentFolder is not a directory).
	public static ArrayList<String> searchByKey (File currentFolder, String key) 
	{
		//an ArrayList is created to be able to return any matching files
		ArrayList<String> files = new ArrayList<String>();
		//contents array copies over all files and directories from currentFolder
		File[]contents = currentFolder.listFiles();
		//iterates through contents to see files
		for(int i=0; i<contents.length; i++)
		{
			//if the object is a file and has the key in its name
			//the name of the file is added to the files ArrayList
			if(contents[i].isFile()&&contents[i].getName().indexOf(key)!=-1)
			{
				files.add(contents[i].getName());
			}
			//if the object is another directory, 
			//it recurs to look through the directory for files with key in the name
			else if(contents[i].isDirectory())
			{
				files.addAll(searchByKey(contents[i], key));
			}
		}
		//returns the files array of all the file names that are matching with key
		return files;	
	}
	// Recursive method that searches the given folder and its subfolders for
	// ALL files whose size is within the given max and min values, inclusive.
	// Returns an array list of the names of all files whose size are within
	// the boundaries and an empty arraylist if the search operation returns
	public static ArrayList<String> searchBySize(File currentFolder, long sizeMin,long sizeMax) 
	{
		//ArrayList created to return all files within size parameters
		ArrayList<String> files = new ArrayList<String>();
		//contents array copies over all files and direcotires from currentFolder
		File[]contents = currentFolder.listFiles();
		//loop iterates through and checks all files
		for(int i=0; i<contents.length; i++)
		{
			//if the object is a file and is within the size parameters
			//the file name is added to the files ArrayList
			if(contents[i].isFile()&&(contents[i].getTotalSpace()>sizeMin&&contents[i].getTotalSpace()<sizeMax))
			{
				files.add(contents[i].getName());
			}
			//if the object is another directory
			//it recurs and looks through the direcotry for files within the size parameters
			else if(contents[i].isDirectory())
			{
				files.addAll(searchBySize(contents[i], sizeMin, sizeMax));
			}
		}
		//returns the files array of all the files within the size parameters
		return files;
	}
			
}
