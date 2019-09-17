package menu;
 
/**
 * Reference: <a href="https://www.java-forums.org/new-java/26584-menu-driven-console-help-please.html">A Simple Text-Based Menu System</a>
 * @author JosAH
 */
public class MenuItem implements Runnable {
     
    private String title;
    private Runnable exec;
 
    protected MenuItem(String title) { this(title, null); }
 
    public MenuItem(String title, Runnable exec) {
        this.title= title;
        this.exec= exec;
    }
     
    public String getTitle() { return title; }
     
    public boolean isExec() { return exec != null; }
     
    protected void setExec(Runnable exec) { this.exec = exec; }
     
    public void run() {
         
        try {
            exec.run();
        }
        catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}