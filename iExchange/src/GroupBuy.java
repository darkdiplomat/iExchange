import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class GroupBuy {
	public static boolean onCommand(Player player, String[] split){
		String ItemID;
		String BuyFilep1 = "plugins/config/iExchange/GroupPrices/";
		String BuyFilep2 = "buy.txt";
		boolean damaged = false;
		
		if (split.length == 2){
			player.sendMessage("§e[iExchange] §bUsage is /iex buy [Item] [Amount]");
			return true;
		}else{
			String Numberof = null;
			if (split.length  > 3){
				Numberof = split[3];
			}
			int amount = 0;
			if (Numberof != null){
				try{
					amount = Integer.parseInt(Numberof);
				}catch (NumberFormatException localNumberFormatException) {
					player.sendMessage("§e[iExchage] §cERROR 409: CONFLICT");
					player.sendMessage("§c(You didn't enter a proper number)");
					return true;
				}
			}else{
				amount = 1;
			}
			if (amount <= 0){
				player.sendMessage("§e[iExchage] §cERROR 409: CONFLICT");
				player.sendMessage("§c(You entered a number less than 1 or greater than 64)");
				return true;
			}
			ItemID = split[2].toUpperCase();
			String[] group = player.getGroups();
			ArrayList<String> BUYPRICES = new ArrayList<String>();
			try {
				BufferedReader in = new BufferedReader(new FileReader(BuyFilep1 + group[0] + BuyFilep2));
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
				player.sendMessage("§e[iExchange] §cERROR 404: BuyPriceList Not Found ");
				player.sendMessage("§c(the BuyPriceList for your group does not exist)");
				player.sendMessage("§c(ask a ADMIN or the SERVER OWNER to correct the issue)");
				return true;
			}
			if (!(BUYPRICES.isEmpty())){
				String ItemIDS = BUYPRICES.toString();
				String[] Item = ItemIDS.split(":");
				String PricePrice = Item[2].replace("]", "");
				int ID = 0;
				int price = 0;
				int damage = 0;
				String ItemItem = Item[0].replace("[", "");
				String ItemGave = ItemItem.toUpperCase();
				try{
					ID = Integer.parseInt(Item[1]);
				}catch (NumberFormatException localNumberFormatException) {
					player.sendMessage("§e[iExchange] §cERROR 404: ITEM NOT FOUND!");
					player.sendMessage("(Props file error! Yell at your ADMIN or SERVER OWNER)");
					return true;
				}
				Item itemd = new Item(ID, amount);
				if ((ID == 6) || (ID == 17) || (ID == 18) || (ID == 35) || (ID == 44) || (ID == 98) || (ID == 263) || (ID == 351) || (ID == 358)){
					damage = Integer.parseInt(Item[2]);
					if (Item[3].contains(",")){
						String[] unfuck = Item[3].split(",");
						PricePrice = unfuck[0];
					}else{
						PricePrice = Item[3].replace("]", "");
					}
					itemd = new Item(ID, amount);
					itemd.setDamage(damage);
				}else{
					if (Item[2].contains(",")){
						String[] unfuck = Item[2].split(",");
						PricePrice = unfuck[0];
					}else{
						PricePrice = Item[2].replace("]", "");
					}
					itemd = new Item(ID, amount);
				}
				try{
					price = Integer.parseInt(PricePrice);
				}catch (NumberFormatException localNumberFormatException) {
					player.sendMessage("§e[iExchange] §cERROR 404: PRICE NOT FOUND!");
					player.sendMessage("§c(Props file error! Yell at your ADMIN or SERVER OWNER)");
					return true;
				}
				int balance = (Integer) etc.getLoader().callCustomHook("iBalance", new Object[] { "balance", player.getName() });
				if (balance > amount*price){
					while (amount > 64){
						Item itemnew = new Item (ID, 64);
						if (damaged == true){
							itemnew.setDamage(damage);
							player.giveItem(itemnew);
							amount -= 64;
						}else{
							player.giveItem(itemnew);
							amount -= 64;
						}
					}
					itemd = new Item(ID, amount);
					if (damaged == true){
						itemd.setDamage(damage);
					}
					player.giveItem(itemd);
					if (Numberof != null){
						amount = Integer.parseInt(Numberof);
					}else{
						amount = 1;
					}
					etc.getLoader().callCustomHook("iBalance", new Object[] { "withdraw", player.getName(), (price*amount)});
					player.sendMessage("§e[iExchange] §2" + amount + " " + ItemGave + " §bpurchased for §2$" + price*amount);
					return true;
				}else{
					player.sendMessage("§e[iExchange] §4ERROR 402: PAYMENT REQUIRED");
					player.sendMessage("§4(You don't have enough money to buy that item)");
					return true;
				}
			}else{
				player.sendMessage("§e[iExchange] §cERROR 400: ITEM NOT ON LIST!");
				player.sendMessage("§c(check your spelling or ask an ADMIN if item is on the list)");
				return true;
			}
		}
	}
}
