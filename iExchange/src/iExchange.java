import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class iExchange extends Plugin {

	String name = "iExchange";
	String version = "1.5.2";
	String author = "Darkdiplomat";
	static Logger log = Logger.getLogger("Minecraft");
	
	static final String updatrUrl = "http://www.visualillusionsent.net/canaryplugins/iExchange.updatr";
	static final String updatrFileUrl = "http://www.visualillusionsent.net/canaryplugins/iExchange.jar";
	static final String updatrNotes = "Update for Minecraft 1.8.x - Block ID 98 switches on Damage Values added";

	public void initialize() {
		iExchangeListener listener = new iExchangeListener();
		etc.getLoader().addListener( PluginLoader.Hook.COMMAND, listener, this, PluginListener.Priority.MEDIUM);
	}
	
	public synchronized void disable() {
		etc.getInstance().removeCommand("/iex");
		log.info(name + " version " + version + " is disabled!");
	}
	
	public void enable() {
		iExchangeProps.renameITEMSFILE();
		iExchangeProps.loadPROPERTIESFILE();
		iExchangeProps.loadSELLITEMPRICESFILE();
		iExchangeProps.loadBUYITEMSFILE();
		iExchangeProps.loadGROUPSFILE();
		createUpdatrFile();
		etc.getInstance().addCommand("/iex", "[buy/sell] [item] [amount] - buy or sell items");
		log.info( name + " version " + version + " by " + author + " is enabled!" );
		
	}
	public void createUpdatrFile(){
		try {
			File updatrDir = new File("Updatr");
	         	//create Updatr directory if it does not exsits already
	         	if(updatrDir.exists()){
	         		File updatrFile = new File("Updatr" + File.separator + name + ".updatr");
	         		//Updatr file does not exist, create it
	         		if(!updatrFile.exists()){
	         			updatrFile.createNewFile();
	         			BufferedWriter writer = new BufferedWriter(new FileWriter(updatrFile));
	         			writer.write("name = " + name); writer.newLine();
	         			writer.write("version = " + version); writer.newLine();
	         			writer.write("url = " + updatrUrl); writer.newLine();
	         			writer.write("file = " + updatrFileUrl); writer.newLine();
	         			writer.write("notes = " + updatrNotes); writer.newLine();
	         			writer.close();
	         		}
	          }
	      } catch (IOException e) {
	         iExchange.log.log(Level.SEVERE, null, e);
	      }
	  }
}