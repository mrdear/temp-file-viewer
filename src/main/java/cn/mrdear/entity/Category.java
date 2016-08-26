package cn.mrdear.entity;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Niu Li
 * @date 2016/8/24
 */
public class Category implements Comparable<Category>{

    public Category() {
        this.nodes = new TreeSet<Category>();
        this.state = new StateBean();
    }

    /**
     * text : Parent 1
     * nodes : [{"text":"Child 1","nodes":[{"text":"Grandchild 1"},{"text":"Grandchild 2"}]},{"text":"Child 2"}]
     */
    private String text;
    private String path;
    private String icon;
    private boolean isDir = false;
    private Set<Category> nodes;
    /**
     * href : #node-1
     * selectable : false
     * state : {"checked":true,"expanded":true}
     * tags : ["available"]
     */
    private boolean selectable;
    /**
     * checked : true
     * expanded : true
     */
    private StateBean state;
    private List<String> tags;

    public boolean getIsDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Category> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Category> nodes) {
        this.nodes = nodes;
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

    public static class StateBean {
        private boolean checked = false;
        private boolean expanded = false;

        public StateBean(boolean checked, boolean expanded) {
            this.checked = checked;
            this.expanded = expanded;
        }
        public StateBean() {
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean isExpanded() {
            return expanded;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }
    }

}
