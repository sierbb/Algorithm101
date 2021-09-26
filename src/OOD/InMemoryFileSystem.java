package OOD;

import java.util.*;

abstract class Entry{
    protected Directory parent;
    protected long created;
    protected long lastUpdated;
    protected long lastAccessed;
    protected String name;
    protected List<Entry> children;

    public Entry(String n, Directory p){
        name = n;
        parent = p;
        created = System.currentTimeMillis();
    }

    public boolean delete(){
        //delete the current entry from its parent
        if (parent == null){
            return false;
        }
        return parent.deleteEntry(this);
    }

    public abstract int size();

    public String getFullPath(){
        if (parent == null){
            return name;
        }else {
            return parent.getFullPath() + "/" + name;
        }
    }

    public long getCreated(){
        return created;
    }

    public long getLastUpdated(){
        return lastUpdated;
    }

    public long getLastAccessed(){
        return lastAccessed;
    }

    public String getName(){
        return name;
    }

    public void rename(String newName){
        name = newName;
    }

    public Entry getChild(String childName){
        for (Entry child : children){
            if (child.getName().equals(childName)){
                return child;
            }
        }
        return null;
    }
}

class File extends Entry{
    private String content;
    private int size;
    protected Directory parent;

    public File(String n, Directory p, int size){
        super(n, p);
        this.size = size;
    }

    public int size(){
        return size;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String c){
        content = c;
    }
}

class Directory extends Entry {
    private List<Entry> contents;

    public Directory(String n, Directory p){
        super(n, p);
        contents = new ArrayList<>();
    }

    protected List<Entry> getContents(){
        //ls
        return contents;
    }

    public int size(){
        int size = 0;
        for (Entry e : contents){
            size += e.size();
        }
        return size;
    }

    public int numberOfFiles(){
        //use iteration/recursion to count all the items from all sub-directory under current Directory object
        int count = 0;
        for (Entry e : contents){
            if( e instanceof Directory){
                //if it is a directory, we count it as 1 file, and run function on the directory
                count++;
                Directory dir = (Directory) e;
                count+= dir.numberOfFiles();
            }else if (e instanceof File){
                count++;
            }
        }
        return count;
    }

    public boolean deleteEntry(Entry e){
        //rm - delete an entry under current directory
        return contents.remove(e);
    }

    public void addEntry(Entry e){
        //mkdir / touch
        contents.add(e);
    }
}

public class InMemoryFileSystem{
    private final Directory root;

    public InMemoryFileSystem(){
        root = new Directory("/", null);
    }

    private List<Entry> resolve(String path){
        //From the given path, find the Entry object for each subdirectory, return a list
        if (!path.startsWith("/")){
            throw new IllegalArgumentException("Invalid full path: " + path );
        }
        //first get rid of the heading "/", then split full path into pieces
        String[] components = path.substring(1).split("/");
        List<Entry> entryList = new ArrayList<>();
        //Start from root directory since it is a full path
        entryList.add(root);

        //start from root, on each level, get the subdirectory that matches the piece name, then jump to next level
        Entry cur = root;
        for (String component : components){
            if (cur == null){
                throw new IllegalArgumentException("Invalid path piece: " + component);
            }
            if (component.length() > 0){ //not an empty string
                cur = ((Directory)cur).getChild(component);
                entryList.add(cur);
            }
        }
        return entryList;
    }

    public void mkdir(String path){
        //Create new directory under the specific entry

        List<Entry> entryList = resolve(path);
        String[] components = path.split("/"); //include "" from the beginning but that's ok
        //now try to get the last dirName, and create it
        String dirName = components[components.length-1];
//        String parent = components[components.length-2];
        Directory p = (Directory)entryList.get(entryList.size()-2);
        Directory newDir = new Directory(dirName, p);
        p.addEntry(newDir);
    }

    public void createNewFile(String path){
        //Create new file under the specific entry

        List<Entry> entryList = resolve(path);
        String[] components = path.split("/");
        String fileName = components[components.length-1];
        Directory p = (Directory)entryList.get(entryList.size()-2);
        File newFile = new File(fileName, p, 0);
        p.addEntry(newFile);
    }

    public void delete(String path){
        //Delete the specific entry

        List<Entry> entryList = resolve(path);
        String[] components = path.split("/");
        String dirName = components[components.length-1];
        Directory p = (Directory)entryList.get(entryList.size()-2);
        Entry toDelete = p.getChild(dirName);
        p.deleteEntry(toDelete);
    }

    public Entry[] list(String path){
        //List all contents in provided path directory

        List<Entry> entryList = resolve(path);
        String[] components = path.split("/");
        //if last piece is directory, list all of its file
        Entry last = entryList.get(entryList.size()-1);
        if (last instanceof Directory){
            List<Entry> contents = ((Directory)last).getContents();
            Entry[] result = new Entry[contents.size()];
            for (int i = 0; i < contents.size(); i++){
                result[i] = contents.get(i);
            }
            return result;
        }else if (last instanceof File){
            return new Entry[]{last};
        }
        //If not directory or file, unrecognized entry
        return new Entry[0];
    }

    public int count(){
        //return count of all files in current root directory

        return 0;
    }


}


