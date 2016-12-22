package cn.mrdear.entity;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Niu Li
 * @date 2016/8/24
 */
public class Category implements Comparable<Category>{

    public Category() {
        this.children = new TreeSet<Category>();
    }

    /**
     * 节点名
     */
    private String name;
    /**
     * 节点id
     */
    private int id;
    /**
     * 节点表示的路径
     */
    private String path;
    /**
     * 是否是文件夹
     */
    private boolean isDir = false;
    /**
     * 节点
     */
    private Set<Category> children;
    /**
     * 是否展开
     */
    private boolean spread;

    public boolean getIsDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDir() {
        return isDir;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    /**
     * 使得文件夹排序在前面,treeSet为递增排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Category o) {
        //目录优先
        if (!this.isDir && o.isDir){
            return 1;
        }
        if (this.isDir && !o.isDir){
            return -1;
        }
        //并列的相同类型元素自然序
        return 1;
    }
}
