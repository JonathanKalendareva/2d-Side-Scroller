import java.applet.*;
import javax.swing.JOptionPane;

public class Game
{
  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private int jackaSucks;
  private int speed;
  private AudioClip moosica;

  
  public Game()
  {
    grid = new Grid(5, 10);
    grid.setBackground("hallway.gif");
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    jackaSucks = 0;
    speed = 100;
    updateTitle();
    moosica = Applet.newAudioClip(this.getClass().getResource("HSM.wav"));
    
  }
  
  public void play()
  {
    JOptionPane.showMessageDialog(grid, "Aight YOU'RE A SENIOR who is 6 school days away from their your last day of classes and then the SUPREME CODER ruins your House of Cards marathon");
    JOptionPane.showMessageDialog(grid, "Your duty as senior is to avoid the books for as long as possible and catch the \"zzz\"s");
    JOptionPane.showMessageDialog(grid, "After catching enough \"zzzs\" you will be wake up and unwisely use your new found energy to watch Netflex instaed of doing your work");
    JOptionPane.showMessageDialog(grid, "diclaimer: once you wake up, there is more work for you to dodge at a faster rate");
    JOptionPane.showMessageDialog(grid, "May the Old Gods and the New be with you the night before your deadlines");
    moosica.play();
    while (!isGameOver())
    {
      if(jackaSucks < 40)
        grid.setImage(new Location(userRow,0),"student.gif");
      else
        grid.setImage(new Location(userRow,0),"woke.gif");
      
      grid.pause(speed);
      handleKeyPress();
      if(msElapsed % 300 == 0) {
            scrollLeft();
            populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
    }
    moosica.stop();
    if(jackaSucks > 300)
        JOptionPane.showMessageDialog(grid, "Congrats, you have graduated with a GPA two letter grades lower than that of yours in junior year and probably dropped 10 points in AP Java!!!!");
    else
        JOptionPane.showMessageDialog(grid, "Yikes, your GPA took too big of a hit and your college has rescinded your acceptance, have fun in summer school lossahhh!");
  }
  
  public void handleKeyPress()
  {
      if(jackaSucks > 40)
          speed = 50;
      removeImg(userRow,0);
      int key = grid.checkLastKeyPressed();
      if(key == 40 && userRow < 4 ){
        userRow++;
        handleCollision(new Location(userRow ,0));
      }
      else if(key == 38 && userRow >  0) {
        userRow--;
        handleCollision (new Location(userRow,0));
      }
          
  }
  
  public void populateRightEdge()
  {
      double chance = Math.random();
      
      if(jackaSucks < 40) {
          if(chance >=.45)
            grid.setImage(new Location((int)(Math.random()*5),9),"books.gif");
          else if(chance <.4)
              grid.setImage(new Location((int)(Math.random()*5),9),"zzz.gif");
      }
      else {
          if(chance >=.35)
            grid.setImage(new Location((int)(Math.random()*5),9),"books.gif");
          else if(chance <.3)
              grid.setImage(new Location((int)(Math.random()*5),9),"netflix.gif");
      }    
  }
  
  public void removeImg(int r, int c)
  {
    Location currLoc = new Location(r,c);  
    grid.setImage(currLoc, null); 
  }
  
  public void scrollLeft()
  {
      handleCollision(new Location(userRow,1));
      for (int r = 0; r < grid.getNumRows(); r++) {
          for (int c = 0; c < grid.getNumCols(); c++) {
              if(c!=0) {
                grid.setImage(new Location(r, c - 1), grid.getImage(new Location(r,c)));
                removeImg(r,c);
              }
              else {
                grid.setImage(new Location(r, 0), grid.getImage(new Location(r,c)));
                removeImg(r,c);
              }
            }  
        }      
  }
  
  public void handleCollision(Location loc)
  {
      if(grid.getImage(loc) != null) {
          if("zzz.gif".equals(grid.getImage(loc)) || "netflix.gif".equals(grid.getImage(loc))) {
            timesGet++;
            removeImg(userRow,0);
          }
          else if("books.gif".equals(grid.getImage(loc))){
            timesAvoid++;
            removeImg(userRow,0);
          }  
            jackaSucks = timesGet - timesAvoid;
      }
  }
  
  public int getScore()
  {
    return jackaSucks;  
  }
  
  public void updateTitle()
  {
    grid.setTitle( "Avoid: " + timesAvoid + "\n Get: " + timesGet);
  }
  
  public boolean isGameOver()
  {
    return jackaSucks > 300 || jackaSucks < 0;
  }
  
  public static void test()
  {
    Game game = new Game();
    game.play();
  }
  
  public static void main(String[] args)
  {
    test(); 
  }
}