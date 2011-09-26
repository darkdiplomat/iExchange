import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BuyItems{
	public static void Buy(Player player, String[] split){
		String ItemID;
		String BuyFile = "plugins/config/iExchange/BuyItemPrices.txt";
		boolean damaged = false;
		
		if (split.length == 2){
			player.sendMessage("§e[iExchange] §bUsage is /iex buy [Item] [Amount]");
			return;
		}else{
			String Numberof = null;
			if (split.length > 3){
				Numberof = split[3];
			}
			int amount = 0;
			if (Numberof != null){
				try{
					amount = Integer.parseInt(Numberof);
				}catch (NumberFormatException localNumberFormatException) {
					player.sendMessage("§e[iExchage] §cERROR 409: CONFLICT");
					player.sendMessage("§c(You didn't enter a proper number)");
					return;
				}
			}else{
				amount = 1;
			}
			if (amount <= 0){
				player.sendMessage("§e[iExchage] §cERROR 409: CONFLICT");
				player.sendMessage("§c(You entered a number less than 1)");
				return;
			}
			ItemID = split[2].toUpperCase();
			ArrayList<String> BUYPRICES = new ArrayList<String>();
			try {
				BufferedReader in = new BufferedReader(new FileReader(BuyFile));
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
			}
			if (!(BUYPRICES.isEmpty())){
				String ItemIDS = BUYPRICES.toString();
				String[] Item = ItemIDS.split(":");
				int ID;
				int damage = 0;
				String PricePrice;
				int price;
				String ItemItem = Item[0].replace("[", "");
				String ItemGave = ItemItem.toUpperCase();
				
				try{
					ID = Integer.parseInt(Item[1]);
				}catch (NumberFormatException localNumberFormatException) {
					player.sendMessage("§e[iExchange] §cERROR 404: ITEM NOT FOUND!");
					player.sendMessage("(Props file error! Yell at your ADMIN or SERVER OWNER)");
					return;
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
					damaged = true;
				}else{
					if (Item[2].contains(",")){
						String[] unfuck = Item[2].split(",");
						PricePrice = unfuck[0];
					}else{
						PricePrice = Item[2].replace("]", "");
					}
				}
				try{
					price = Integer.parseInt(PricePrice);
				}catch (NumberFormatException localNumberFormatException) {
					player.sendMessage("§e[iExchange] §cERROR 404: PRICE NOT FOUND!");
					player.sendMessage("§c(Props file error! Yell at your ADMIN or SERVER OWNER)");
					return;
				}
				int balance = (Integer) etc.getLoader().callCustomHook("iBalance", new Object[] { "balance", player.getName() });
				if (balance > amount*price){
					while (amount > 64){
						Item itemnew = new Item (ID, 64);
						if (damaged == true){
							itemnew.setDamage(damage);
							player.giveItem(itemnew);
							amount -= 64;
							player.getInventory().update();
						}else{
							player.giveItem(itemnew);
							player.getInventory().update();
							amount -= 64;
						}
					}
					itemd = new Item(ID, amount);
					if (damaged == true){
						itemd.setDamage(damage);
					}
					player.giveItem(itemd);
					player.getInventory().update();
					if (Numberof != null){
						amount = Integer.parseInt(Numberof);
					}else{
						amount = 1;
					}
					etc.getLoader().callCustomHook("iBalance", new Object[] { "withdraw", player.getName(), (price*amount)});
					player.sendMessage("§e[iExchange] §2" + amount + " " + ItemGave + " §bpurchased for §2$" + price*amount);
					return;
				}else{
					player.sendMessage("§e[iExchange] §4ERROR 402: PAYMENT REQUIRED");
					player.sendMessage("§4(You don't have enough money to buy that item)");
					return;
				}
			}else{
				player.sendMessage("§e[iExchange] §cERROR 400: ITEM NOT ON LIST!");
				player.sendMessage("§c(check your spelling or ask an ADMIN if item is on the list)");
				return;
			}
		}
	}
}