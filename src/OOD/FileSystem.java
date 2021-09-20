package OOD;

import java.util.*;

public class FileSystem {

    class File{
        private String content = "";
        private Map<String, File> children = new HashMap<>();
        private boolean isFile = false;
    }

    private File root = null;

    public FileSystem() {
        root = new File(); //initialize the root path
    }

    public List<String> ls(String path) {
        List<String> res = new ArrayList<>();
        String[] dirs = path.split("/"); //"/" split into [""]
        File cur = root;
        String name = "";
        for (String dir : dirs){
            if (dir.length() == 0){ //to get rid of the "" being return in the split list
                continue;
            }
            //if current dir has children and cur dir is in children, we can go further
            if (cur.children.containsKey(dir)){
                cur = cur.children.get(dir);
            }else {
                //if dir does not exists, do not create or go any further, return current result list
                res.add(dir);
                return res;
            }
            name = dir;
        }
        //the last element is either the bottom folder name, or a single file
        if (cur.isFile){
            res.add(name);
        }else {
            for (String subdir : cur.children.keySet()){
                res.add(subdir);
            }
        }
        Collections.sort(res); //String defualts to lexico order so good
        return res;
    }

    public void mkdir(String path) {
        //Same as ls, difference is to create new DIR object and added to prev level's Map
        String[] dirs = path.split("/");
        File cur = root;
        for (String dir : dirs){
            if (dir.length() == 0){ //to get rid of the "" being return in the split list
                continue;
            }
            //if current dir has children and cur dir is in children, we can go further
            if (cur.children.containsKey(dir)){
                cur = cur.children.get(dir);
            }else {
                //if dir does not exists, means we need to create and add to current dir's children Map
                File file = new File();
                cur.children.put(dir, file);
                cur = file;
            }
        }
    }

    public void addContentToFile(String filePath, String content) {
        //Same as mkdir, difference is to create new File and mark isFile, and add to children Map
        String[] dirs = filePath.split("/");
        File cur = root;
        for (String dir : dirs){
            if (dir.length() == 0){ //to get rid of the "" being return in the split list
                continue;
            }
            if (cur.children.containsKey(dir)){
                cur = cur.children.get(dir);
            }else {
                File file = new File();
                cur.children.put(dir, file);
                cur = file;
            }
        }
        //since we are at the last File, need to mark as file
        cur.isFile = true;
        if (cur.content != null){
            cur.content = cur.content + content;
        }else {
            cur.content = content;
        }

    }

    public String readContentFromFile(String filePath) {
        //Same as addContentToFile, difference is to create new File and mark isFile, and return the content
        String[] dirs = filePath.split("/");
        File cur = root;
        for (String dir : dirs){
            if (dir.length() == 0){ //to get rid of the "" being return in the split list
                continue;
            }
            if (cur.children.containsKey(dir)){
                cur = cur.children.get(dir);
            }else {
                File file = new File();
                cur.children.put(dir, file);
                cur = file;
            }
        }
        //since we are at the last File, need to mark as file
        cur.isFile = true;
        return cur.content;
    }

    public static void main(String[] args){
        //["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
        //[[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]
        FileSystem obj = new FileSystem();
        System.out.println(obj.ls("/"));
        obj.mkdir("/a/b/c");
        System.out.println(obj.ls("/a/b"));
        obj.addContentToFile("/a/b/c/d", "hello");
        System.out.println(obj.ls("/"));
        System.out.println(obj.readContentFromFile("/a/b/c/d"));
    }

}

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */

//clarification:
//FileSystem -> file path mapping to filecontent
//if a path is a directory -> Set<subpath>
//if a path is a file -> An File<> object with content as field?

//M1: use hashmap - lots of
//Map<dirPath, List<String> subfolders> dirs for a certain directory
//Map<Filename, content> files for a certain directory

//ls(dir) -> return List<String> subfolders of input dir -> Need to parse by / and go level by level
//mkdir() -> append a new subfolder path to list, and create a new dirs and files map for this specific new dir?
//addContentToFile() -> similar to mkdir() to go down to the file, either create or append to existing file's content value
//readContent() -> similar to mkdir() to go down to the file

//TC:O(m) parse filePath + O(n) go down to bottom level + O(klogk) sort files
//SC:O() ???


//M2: use trie?
//Trie -> same prefix will be precalculated? O(k) lookup where k is the length of levels
//Each File is a TrieNode(isFile, content, HashMap<String, File>)
//for ls() -> from root TrieNode (new node from the beginning), go all the way down until hit the last subpath, and return KeySet()
//mkdir() -> from root TrieNode, go all the way down until hitting last subpath, create new TrieNode if not exists
//readContent() -> trie down and create empty file/ read from file
//AddContentToFile() -> trie down and create file/append to content

//TC:O(n) to trie down, n = levels of path split after '/'
//SC:O(k^n) k is number of possible subdir, n is number of path level
