import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class iExchangeListener extends PluginListener {
	static Logger log = Logger.getLogger("Minecraft");
	public boolean onCommand(Player player, String[] split) {
		if (split[0].equalsIgnoreCase("/iex")){
			if (split.length == 1){
				player.sendMessage("§e[iExchange] §bUsage is /iex [SELL or BUY] [Item] [Amount]");
				return true;
			}else if ((split.length > 1) && (!(split[1].equalsIgnoreCase("sell"))) && (!(split[1].equalsIgnoreCase("s"))) 
					&& (!(split[1].equalsIgnoreCase("buy"))) && (!(split[1].equalsIgnoreCase("b")))){
				String ItemID;
				if (iExchangeProps.getGROUPPRICESENABLED() == false){
					String SellItemPricesFile = "plugins/config/iExchange/SellItemPrices.txt";
					String BuyItemPricesFile = "plugins/config/iExchange/BuyItemPrices.txt";
					ItemID = split[1].toUpperCase();
					String SellPrice = "§cN/A";
					String BuyPrice = "§cN/A";
					ArrayList<String> SELLPRICES = new ArrayList<String>();
					ArrayList<String> BUYPRICES = new ArrayList<String>();
					try {
						BufferedReader in = new BufferedReader(new FileReader(SellItemPricesFile));
						String str;
						while ((str = in.readLine()) != null){
							String Upped = str.toUpperCase();
							String[] CheckName = Upped.split(":");
							if (CheckName[0].equals(ItemID)){
								SELLPRICES.add(str.toString());
							}
						}
						in.close();
					} catch (IOException e) {
						player.sendMessage("§e[iExchange] §cERROR 404: FILE NOT FOUND!");
						player.sendMessage("§c(SellItemPrices file error! Have an ADMIN verify the file)");
						log.log(Level.SEVERE, "[iExchange] Failed to LOAD: \"SellItemPrices.txt\", unable to continue.");
						return true;
					}
					try {
						BufferedReader in = new BufferedReader(new FileReader(BuyItemPricesFile));
						String str;
						while ((str = in.readLine()) != null){
							String Upped = str.toUpperCase();
							String[] CheckName = Upped.split(":");
							if (CheckName[0].equals(ItemID)){
								BUYPRICES.add(str.toString());
							}
						}
						in.close();
					} catch (IOException e) {
						player.sendMessage("§e[iExchange] §cERROR 404: FILE NOT FOUND!");
						player.sendMessage("§c(SellItemPrices file error! Have an ADMIN verify the file)");
						log.log(Level.SEVERE, "[iExchange] Failed to LOAD: \"SellItemPrices.txt\", unable to continue.");
						return true;
					}
					
					if (SELLPRICES != null){
						String SellPrices = SELLPRICES.toString();
						String[] SELLPRICESSPLIT = SellPrices.split(":");
						int ID = 0;
						if (SELLPRICESSPLIT.length > 1){
							try{
								ID = Integer.parseInt(SELLPRICESSPLIT[1]);
							}catch (NumberFormatException n1) {
								player.sendMessage("§e[iExchange] §cERROR 404: ITEM NOT FOUND!");
								player.sendMessage("§c(SellItemPrices file error! Have an ADMIN verify the file)");
								log.log(Level.INFO, "[iExchange] Failed to LOAD: " + SELLPRICESSPLIT[1] + " from \"SellItemPrices.txt\" (is the ID set correctly?)");
								return true;
							}
						}
						if (ID != 0){
							if ((ID == 6) || (ID == 17) || (ID == 18) || (ID == 35) || (ID == 44) || (ID == 263) || (ID == 351) || (ID == 358)){
								if (SELLPRICESSPLIT[3].contains(",")){
									String[] unfuck = SELLPRICESSPLIT[3].split(",");
									SellPrice = "§2$" + unfuck[0];
								}else{
									SellPrice = "§2$" + SELLPRICESSPLIT[3].replace("]", "");
								}
							}else{
								if (SELLPRICESSPLIT[2].contains(",")){
									String[] unfuck = SELLPRICESSPLIT[2].split(",");
									SellPrice = "§2$" + unfuck[0];
								}else{
									SellPrice = "§2$" + SELLPRICESSPLIT[2].replace("]", "");
								}
							}
						}
					}
					if (BUYPRICES != null){
						String BuyPrices = BUYPRICES.toString();
						String[] BUYPRICESSPLIT = BuyPrices.split(":");
						int ID = 0;
						if (BUYPRICESSPLIT.length > 1){
							try{
								ID = Integer.parseInt(BUYPRICESSPLIT[1]);
							}catch (NumberFormatException n1) {
								player.sendMessage("§e[iExchange] §cERROR 404: ITEM NOT FOUND!");
								player.sendMessage("§c(SellItemPrices file error! Have an ADMIN verify the file)");
								log.log(Level.INFO, "[iExchange] Failed to LOAD: " + BUYPRICESSPLIT[1] + " from \"SellItemPrices.txt\" (is the ID set correctly?)");
								return true;
							}
						}
						if (ID != 0){
							if ((ID == 6) || (ID == 17) || (ID == 18) || (ID == 35) || (ID == 44) || (ID == 263) || (ID == 351) || (ID == 358)){
								if (BUYPRICESSPLIT[3].contains(",")){
									String[] unfuck = BUYPRICESSPLIT[3].split(",");
									BuyPrice = "§2$" + unfuck[0];
								}else{
									BuyPrice = "§2$" + BUYPRICESSPLIT[3].replace("]", "");
								}
							}else{
								if (BUYPRICESSPLIT[2].contains(",")){
									String[] unfuck = BUYPRICESSPLIT[2].split(",");
									BuyPrice = "§2$" + unfuck[0];
								}else{
									BuyPrice = "§2$" + BUYPRICESSPLIT[2].replace("]", "");
								}
							}
						}
					}
					player.sendMessage("§bITEM: §A" + split[1].toUpperCase() + " §6Buy Price = " + BuyPrice + " §eSell Price = " + SellPrice);
					return true;
				}else{
					String[] group = player.getGroups();
					String SellItemPricesFile = "plugins/config/iExchange/GroupPrices/" + group[0] + "sell.txt";
					String BuyItemPricesFile = "plugins/config/iExchange/GroupPrices/" + group[0] + "buy.txt";
					ItemID = split[1].toUpperCase();
					String SellPrice = "§cN/A";
					String BuyPrice = "§cN/A";
					ArrayList<String> SELLPRICES = new ArrayList<String>();
					ArrayList<String> BUYPRICES = new ArrayList<String>();
					try {
						BufferedReader in = new BufferedReader(new FileReader(SellItemPricesFile));
						String str;
						while ((str = in.readLine()) != null){
							String Upped = str.toUpperCase();
							String[] CheckName = Upped.split(":");
							if (CheckName[0].equals(ItemID)){
								SELLPRICES.add(str.toString());
							}
						}
						in.close();
					} catch (IOException e) {
						player.sendMessage("§e[iExchange] §cERROR 404: FILE NOT FOUND!");
						player.sendMessage("§c(SellItemPrices file error! Have an ADMIN verify the file)");
						log.log(Level.SEVERE, "[iExchange] Failed to LOAD: \"" + player.getGroups() + "sell.txt\", unable to continue.");
						return true;
					}
					try {
						BufferedReader in = new BufferedReader(new FileReader(BuyItemPricesFile));
						String str;
						while ((str = in.readLine()) != null){
							String Upped = str.toUpperCase();
							String[] CheckName = Upped.split(":");
							if (CheckName[0].equals(ItemID)){
								BUYPRICES.add(str.toString());
							}
						}
						in.close();
					} catch (IOException e) {
						player.sendMessage("§e[iExchange] §cERROR 404: FILE NOT FOUND!");
						player.sendMessage("§c(SellItemPrices file error! Have an ADMIN verify the file)");
						log.log(Level.SEVERE, "[iExchange] Failed to LOAD: \"SellItemPrices.txt\", unable to continue.");
						return true;
					}
					
					if (SELLPRICES != null){
						String SellPrices = SELLPRICES.toString();
						String[] SELLPRICESSPLIT = SellPrices.split(":");
						int ID = 0;
						if (SELLPRICESSPLIT.length > 1){
							try{
								ID = Integer.parseInt(SELLPRICESSPLIT[1]);
							}catch (NumberFormatException n1) {
								player.sendMessage("§e[iExchange] §cERROR 404: ITEM NOT FOUND!");
								player.sendMessage("§c(SellItemPrices file error! Have an ADMIN verify the file)");
								log.log(Level.INFO, "[iExchange] Failed to LOAD: " + SELLPRICESSPLIT[1] + " from \"SellItemPrices.txt\" (is the ID set correctly?)");
								return true;
							}
						}
						if (ID != 0){
							if ((ID == 6) || (ID == 17) || (ID == 18) || (ID == 35) || (ID == 44) || (ID == 263) || (ID == 351) || (ID == 358)){
								if (SELLPRICESSPLIT[3].contains(",")){
									String[] unfuck = SELLPRICESSPLIT[3].split(",");
									SellPrice = "§2$" + unfuck[0];
								}else{
									SellPrice = "§2$" + SELLPRICESSPLIT[3].replace("]", "");
								}
							}else{
								if (SELLPRICESSPLIT[2].contains(",")){
									String[] unfuck = SELLPRICESSPLIT[2].split(",");
									SellPrice = "§2$" + unfuck[0];
								}else{
									SellPrice = "§2$" + SELLPRICESSPLIT[2].replace("]", "");
								}
							}
						}
					}
					if (BUYPRICES != null){
						String BuyPrices = BUYPRICES.toString();
						String[] BUYPRICESSPLIT = BuyPrices.split(":");
						int ID = 0;
						if (BUYPRICESSPLIT.length > 1){
							try{
								ID = Integer.parseInt(BUYPRICESSPLIT[1]);
							}catch (NumberFormatException n1) {
								player.sendMessage("§e[iExchange] §cERROR 404: ITEM NOT FOUND!");
								player.sendMessage("§c(SellItemPrices file error! Have an ADMIN verify the file)");
								log.log(Level.INFO, "[iExchange] Failed to LOAD: " + BUYPRICESSPLIT[1] + " from \"SellItemPrices.txt\" (is the ID set correctly?)");
								return true;
							}
						}
						if (ID != 0){
							if ((ID == 6) || (ID == 17) || (ID == 18) || (ID == 35) || (ID == 44) || (ID == 263) || (ID == 351) || (ID == 358)){
								if (BUYPRICESSPLIT[3].contains(",")){
									String[] unfuck = BUYPRICESSPLIT[3].split(",");
									BuyPrice = "§2$" + unfuck[0];
								}else{
									BuyPrice = "§2$" + BUYPRICESSPLIT[3].replace("]", "");
								}
							}else{
								if (BUYPRICESSPLIT[2].contains(",")){
									String[] unfuck = BUYPRICESSPLIT[2].split(",");
									BuyPrice = "§2$" + unfuck[0];
								}else{
									BuyPrice = "§2$" + BUYPRICESSPLIT[2].replace("]", "");
								}
							}
						}
					}
					player.sendMessage("§bITEM: §A" + split[1].toUpperCase() + " §6Buy Price = " + BuyPrice + " §eSell Price = " + SellPrice);
					return true;
				}
			}else if (iExchangeProps.getGROUPPRICESENABLED() == false){
				if ((split[1].equalsIgnoreCase("sell") || (split[1].equalsIgnoreCase("s")))){
					if (iExchangeProps.getSELLENABLED() == true){
						if (player.canUseCommand("/iex-sell")){
							SellItems.Sell(player, split);
							return true;
						}else{
							player.sendMessage("§e[iExchange] §cERROR 401: UNAUTHORIZED");
							player.sendMessage("§c(You do not have rights to this command");
							return true;
						}
					}else{
						player.sendMessage("§e[iExchange] §cERROR 403: FORBIDDEN");
						player.sendMessage("§c(Selling is not enabled)");
						return true;
					}
				}else if ((split[1].equalsIgnoreCase("buy") || (split[1].equalsIgnoreCase("b")))){
					if (iExchangeProps.getBUYENABLED() == true){
						if (player.canUseCommand("/iex-buy")){
							BuyItems.Buy(player, split);
							return true;
						}else{
							player.sendMessage("§e[iExchange] §cERROR 401: UNAUTHORIZED");
							player.sendMessage("§c(You do not have rights to this command");
							return true;
						}
					}else{
						player.sendMessage("§e[iExchange] §cERROR 403: FORBIDDEN");
						player.sendMessage("§c(Buying is not enabled)");
						return true;
					}
				}else if ((split[1].equals("BREW")) && (split[2].equals("COFFEE"))){
					player.sendMessage("§6[TEAPOT SERVER] §cERROR 418: I'M A TEA POT");
					player.sendMessage("§b I see you have found the secret command.");
					player.sendMessage("§b Let me know you found it on CanaryMod Fourms :D");
					return true;
					}
			}else{
				GroupBuySell.onCommand(player, split);
				return true;
			}
		}
	return false;
	}
}