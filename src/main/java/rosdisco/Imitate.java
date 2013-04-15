package rosdisco;

import java.util.List;

import edu.wpi.cetask.*;
import edu.wpi.disco.*;
import edu.wpi.disco.Agenda.Plugin;
import edu.wpi.disco.lang.*;

import java.util.*;

public class Imitate {

   public static void main (String[] args) { new Imitate(); }
   
   private static final Interaction interaction = new Interaction(new MyAgent("agent"), new User("user"));
   private static final Disco disco = interaction.getDisco();
   private static final boolean guess = disco.getProperty("interaction@guess", true);
 
   private Imitate () {
      try (ConsoleWindow window = new ConsoleWindow(interaction, 600, 500, 14)) {
         disco.load("SetDTableStepLess.xml");
         try { interaction.join(); } catch (InterruptedException e) {}
      }
  }
   
  public static void duplicate () {
	 boolean rootFlag = true;
	 
	for (Iterator<Plan> top = disco.tops(); top.hasNext();)
	{

		Plan newRoot = top.next();
		Plan tplan = new Plan(newRoot.getGoal().getType().newInstance());
		
		//get children and add them to a new plan
		System.out.println("getting next plan top.next()");
		System.out.println("adding plan to top of current instance of disco" + tplan);
		System.out.println("is this the top node? :" + disco.isTop(tplan));
		tplan.getGoal().setExternal(false);
		disco.addTop(tplan);
		System.out.println("ADDED newROOT" + tplan);
		top.remove();
		System.out.println("REMOVED" + tplan);
	}
   }

   private void agent () {
      // see simple model for agent turn at
      // {@link Agent#respond(Interaction,boolean,boolean)}
      interaction.getSystem().respond(interaction, false, guess);
   }
   
   private void user (Task task, Plan contributes) {
      interaction.done(true, task, contributes);
   }
   
   private Task newInstance (String task) { 
      return disco.getTaskClass(task).newInstance();
   }
   
   private static class MyAgent extends Agent {
      
      private MyAgent (String name) { super(name); }
      
      @Override
      public void say (Utterance utterance) {
         // here is where you would put natural language generation
         // and/or pass utterance string to TTS or GUI
         // for now we just call Disco's default formatting and print
         // out result on system console
         System.out.println(utterance.getDisco().formatUtterance(utterance));
      }
   }
}
