import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class iExchangeProps {
	static Logger log = Logger.getLogger("Minecraft");
	static String SellItemPricesFile = "plugins/config/iExchange/SellItemPrices.txt";
	static String BuyItemPricesFile = "plugins/config/iExchange/BuyItemPrices.txt";
	static String PropertiesFile = "plugins/config/iExchange/Properties.ini";
	static String TradeFile = "plugins/config/iExchange/TradesPending.txt";
	static String GroupFile = "plugins/config/iExchange/GroupPrices/GroupPricesREADME.txt";
	static String ReNameItemsFile = "plugins/config/iExchange/Items.txt";	
	static Boolean BuyEnabled = true;
	static Boolean SellEnabled = true;
	static Boolean TradeEnabled = false;
	static Boolean GroupPrices = false;
	static String SellEnabledString = SellEnabled.toString();
	static String BuyEnabledString = BuyEnabled.toString();
	static String GroupPricesString = GroupPrices.toString();	
	public static boolean getBUYENABLED() {
		BuyEnabled = Boolean.parseBoolean(BuyEnabledString);
		return BuyEnabled;
	}
	public static boolean getSELLENABLED() {
		SellEnabled = Boolean.parseBoolean(SellEnabledString);
		return SellEnabled;
	}
	public static boolean getGROUPPRICESENABLED() {
		GroupPrices = Boolean.parseBoolean(GroupPricesString);
		return GroupPrices;
	}
	public static void renameITEMSFILE(){
		File itemsFile = new File(ReNameItemsFile);
		File SellItemPrices = new File(SellItemPricesFile);
		if (itemsFile.exists()) {
			try{
				itemsFile.renameTo(SellItemPrices);
			}catch (Exception e) {
				log.log(Level.SEVERE, "[iExchange] Failed to RENAME: \"Items.txt\" to \"SellItemPrices.txt\", unable to continue.");
			}
		}
	}
	public static void loadSELLITEMPRICESFILE() {
		File SellItemPrices = new File(SellItemPricesFile);
		if (SellItemPrices.exists()) {
			try {				
				Properties SellItemPricesSettings = new Properties();
				SellItemPricesSettings.load(new FileInputStream(SellItemPricesFile));
			}catch (Exception e) {
				log.log(Level.SEVERE, "[iExchange] Failed to LOAD: \"SellItemPrices.txt\", unable to continue.");
			}
		}else{
			createITEMSFILE();
		}
	}
	public static void createITEMSFILE() {
		File SellItemPrices = new File(SellItemPricesFile);
		try {
			SellItemPrices.getParentFile().mkdirs();
			BufferedWriter outChannel = new BufferedWriter(new FileWriter(SellItemPrices));
			outChannel.write("#The format is ITEMNAME:ID:PRICE"); outChannel.newLine();
			outChannel.write("Stone:1:2"); outChannel.newLine();
			outChannel.write("stone:1:2"); outChannel.newLine();
			outChannel.flush();
			outChannel.close();
		} catch (Exception e) {
			log.log(Level.SEVERE, "[iExchange] Failed to CREATE: \"SellItemPrices.txt\", unable to continue.");
		}
	}
	public static void loadBUYITEMSFILE() {
		File BuyItemPrices = new File(BuyItemPricesFile);
		if (BuyItemPrices.exists()) {
			try {				
				Properties BuyItemPricesSettings = new Properties();
				BuyItemPricesSettings.load(new FileInputStream(BuyItemPrices));
			}catch (Exception e) {
				log.log(Level.SEVERE, "[iExchange] Failed to LOAD: \"BuyItemPrices.txt\", unable to continue.");
			}
		}else{
			createBUYITEMSFILE();
		}
	}
	public static void createBUYITEMSFILE() {
		File BuyItemPrices = new File(BuyItemPricesFile);
		try {
			BuyItemPrices.getParentFile().mkdirs();
			BufferedWriter outChannel = new BufferedWriter(new FileWriter(BuyItemPrices));
			outChannel.write("#The format is ITEMNAME:ID:PRICE"); outChannel.newLine();
			outChannel.write("Stone:1:5"); outChannel.newLine();
			outChannel.write("stone:1:5"); outChannel.newLine();
			outChannel.flush();
			outChannel.close();
		} catch (Exception e) {
			log.log(Level.SEVERE, "[iExchange] Failed to CREATE: \"BuyItemPrices.txt\" File, unable to continue.");
		}
	}
	public static void loadPROPERTIESFILE() {
		File Propertiesfile = new File(PropertiesFile);
		if (Propertiesfile.exists()) {
			try {				
				Properties PropertiesSettings = new Properties();
				PropertiesSettings.load(new FileInputStream(PropertiesFile));
				BuyEnabledString = PropertiesSettings.getProperty("Buy-Enabled", BuyEnabledString);
				SellEnabledString = PropertiesSettings.getProperty("Sell-Enabled", SellEnabledString);
				GroupPricesString = PropertiesSettings.getProperty("GroupPrices-Enabled", GroupPricesString);
			}catch (Exception e) {
				log.log(Level.SEVERE, "[iExchange] Failed to LOAD: \"Properties.ini\", unable to continue.");
			}
		}else{
			createPROPERTIESFILE();
		}
	}
	public static void createPROPERTIESFILE() {
		File Propertiesfile = new File(PropertiesFile);
		try {
			Propertiesfile.getParentFile().mkdirs();
			BufferedWriter outChannel = new BufferedWriter(new FileWriter(Propertiesfile));
			outChannel.write("#Trade is unused at this time"); outChannel.newLine();
			outChannel.write("Buy-Enabled = " + BuyEnabled); outChannel.newLine();
			outChannel.write("Sell-Enabled = " + SellEnabled); outChannel.newLine();
			outChannel.write("Trade-Enabled = " + TradeEnabled); outChannel.newLine();
			outChannel.write("GroupPrices-Enabled = " + GroupPrices);outChannel.newLine();
			outChannel.flush();
			outChannel.close();
		} catch (Exception e) {
			log.log(Level.SEVERE, "[iExchange] Failed to CREATE: \"Properties.ini\", unable to continue.");
		}
	}
	public static void loadGROUPSFILE() {
		File Groupfile = new File(GroupFile);
		if (Groupfile.exists()) {
			try {				
				Properties GroupREADME = new Properties();
				GroupREADME.load(new FileInputStream(Groupfile));
			}catch (Exception e) {
				log.log(Level.SEVERE, "[iExchange] Failed to LOAD: \"GroupPrice/GroupPricesREADME.txt\", unable to continue.");
			}
		}else{
			createGROUPSFILE();
		}
	}
	public static void createGROUPSFILE() {
		File Groupfile = new File(GroupFile);
		try {
			Groupfile.getParentFile().mkdirs();
			BufferedWriter outChannel = new BufferedWriter(new FileWriter(Groupfile));
			outChannel.write("Setting up your groups files"); outChannel.newLine();
			outChannel.write(""); outChannel.newLine();
			outChannel.write("Groups files should be name [groupname]buy.txt and [groupname]sell.txt"); outChannel.newLine();
			outChannel.write("Like this:"); outChannel.newLine();
			outChannel.write("adminssell.txt adminsbuy.txt defaultsell.txt defaultbuy.txt"); outChannel.newLine();
			outChannel.write("You will need a buy and sell file for each group you wish to have buying and selling"); outChannel.newLine();
			outChannel.write("Files otherwise have the same format as SellItemPrices.txt and BuyItemPrices.txt"); outChannel.newLine();
			outChannel.write("If you need help just message me (darkdiplomat) with your group names and I will create the files for you :) "); outChannel.newLine();
			outChannel.flush();
			outChannel.close();
		} catch (Exception e) {
			log.log(Level.SEVERE, "[iExchange] Failed to CREATE: \"GroupPrice/GroupPricesREADME.txt\", unable to continue.");
		}
	}
}