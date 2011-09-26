import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class GroupSell {
	public static boolean onCommand(Player player, String[] split){
		String ItemID;
		String ItemsFilep1 = "plugins/config/iExchange/GroupPrices/";
		String ItemsFilep2 = "sell.txt";
		
		if (split.length == 2){
				player.sendMessage("§e[iExchange] §bUsage is /iex sell [Item] [Amount]");
				return true;
		}else{
			String Numberof = null;
			Inventory inventory = player.getInventory();
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
					return true;
				}
			}else{
				amount = 1;
			}
			if (amount <= 0){
				player.sendMessage("§e[iExchage] §cERROR 409: CONFLICT");
				player.sendMessage("§c(You entered a number less than 1)");
				return true;
			}
			ItemID = split[2].toUpperCase();
			ArrayList<String> ITEMPRICES = new ArrayList<String>();
			String[] group = player.getGroups();
			try {
				BufferedReader in = new BufferedReader(new FileReader(ItemsFilep1 + group[0] + ItemsFilep2));
				String str;
				while ((str = in.readLine()) != null){
					String Upped = str.toUpperCase();
					String[] CheckName = Upped.split(":");
					if (CheckName[0].equals(ItemID)){
						ITEMPRICES.add(str.toString());
					}
				}
				in.close();
			} catch (IOException e) {
				player.sendMessage("§e[iExchange] §cERROR 404: SellPriceList Not Found ");
				player.sendMessage("§c(the SellPriceList for your group does not exist)");
				player.sendMessage("§c(ask a ADMIN or the SERVER OWNER to correct the issue)");
				return true;
			}
			if (!(ITEMPRICES.isEmpty())){
				String ItemIDS = ITEMPRICES.toString();
				String[] Item = ItemIDS.split(":");
				String PricePrice = Item[2].replace("]", "");
				int ID = 0;
				int price = 0;
				int damage = 0;
				try{
					ID = Integer.parseInt(Item[1]);
				}catch (NumberFormatException localNumberFormatException) {
					player.sendMessage("§e[iExchange] §cERROR 404: ITEM NOT FOUND!");
					player.sendMessage("§c(Props file error! Yell at your ADMIN or SERVER OWNER)");
					ITEMPRICES.clear();
					return true;
				}
				Item itemd = new Item();
				if ((ID == 6) || (ID == 17) || (ID == 18) || (ID == 35) || (ID == 44) || (ID == 98) || (ID == 263) || (ID == 351) || (ID == 358)){
					damage = Integer.parseInt(Item[2]);
					if (Item[3].contains(",")){
						String[] unfuck = Item[3].split(",");
						PricePrice = unfuck[0];
						itemd = new Item(ID, amount, -1, damage);
					}else{
					PricePrice = Item[3].replace("]", "");
					itemd = new Item(ID, amount, -1, damage);
					}
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
					ITEMPRICES.clear();
					return true;
				}
				String ItemItem = Item[0].replace("[", "");
				String ItemGave = ItemItem.toUpperCase();
				if (inventory.hasItem(itemd.getItemId())){
					if ((ID == 6) || (ID == 17) || (ID == 18) || (ID == 35) || (ID == 44) || (ID == 98) || (ID == 263) || (ID == 351) || (ID == 358)){
						int i = -1;
						int scan = -1;
						int invamount = 0;
						while (scan < 37){
							scan++;
							if (inventory.getItemFromSlot(scan) != null){
								Item itemcheck = inventory.getItemFromSlot(scan);
								int id1 = itemd.getItemId();
								int id2 = itemcheck.getItemId();
								int d1 = itemd.getDamage();
								int d2 = itemcheck.getDamage();
								int icA = itemcheck.getAmount();
								if ((id1 == id2) && (d1 == d2)){
									invamount += icA;
								}
							}
							
						}
						if (invamount < amount){
							player.sendMessage("§e[iExchange] §cERROR 404: AMOUNT NOT FOUND!");
							player.sendMessage("§cYou don't have that much!");
							ITEMPRICES.clear();
							return true;
						}
						while (i <= 40){
							i++;
							if (inventory.getItemFromSlot(i) != null){
								Item item2 = inventory.getItemFromSlot(i);
								int id1 = itemd.getItemId();
								int id2 = item2.getItemId();
								int d1 = itemd.getDamage();
								int d2 = item2.getDamage();
								if ((id1 == id2) && (d1 == d2)){
									if (amount > 0){
										int oldamount = item2.getAmount();
										int newamount = (oldamount - amount);
										if (newamount <=0){
											inventory.removeItem(i);
											inventory.update();
											amount -= oldamount;
										}else{
											inventory.removeItem(i);
											inventory.setSlot(itemd.getItemId(), newamount, d1, i);
											inventory.update();
											amount -= oldamount;
										}
									}else{
										break;
									}
								}
							}
						}
						if (Numberof != null){
							amount = Integer.parseInt(Numberof);
						}else{
							amount = 1;
						}
						etc.getLoader().callCustomHook("iBalance", new Object[] { "deposit", player.getName(), (price*amount)});
						player.sendMessage("§e[iExchange] §2" + amount + " " + ItemGave + " §bsold for §2$" + price*amount);
						ITEMPRICES.clear();
						return true;
				}else if (ID !=0){
					int scan = -1;
					int invamount = 0;
					while (scan < 37){
						scan++;
						if (inventory.getItemFromSlot(scan) != null){
							Item itemcheck = inventory.getItemFromSlot(scan);
							int id1 = itemd.getItemId();
							int id2 = itemcheck.getItemId();
							int icA = itemcheck.getAmount();
							if (id1 == id2){
								invamount += icA;
							}
						}
						
					}
					if (invamount < amount){
						player.sendMessage("§e[iExchange] §cERROR 404: AMOUNT NOT FOUND!");
						player.sendMessage("§cYou don't have that much!");
						ITEMPRICES.clear();
						return true;
					}
					int i = -1;
					while (i <= 40){
						i++;
						if (inventory.getItemFromSlot(i) != null){
							Item item2 = inventory.getItemFromSlot(i);
							int id1 = itemd.getItemId();
							int id2 = item2.getItemId();
							if ((id1 == id2)){
								if (amount > 0){
									int oldamount = item2.getAmount();
									int newamount = (oldamount - amount);
									if (newamount <=0){
										inventory.removeItem(i);
										inventory.update();
										amount -= oldamount;
									}else{
										inventory.removeItem(i);
										inventory.setSlot(itemd.getItemId(), newamount, i);
										inventory.update();
										amount -= oldamount;
									}
								}else{
									break;
								}
							}
						}
						}
					if (Numberof != null){
						amount = Integer.parseInt(Numberof);
					}else{
						amount = 1;
					}
					etc.getLoader().callCustomHook("iBalance", new Object[] { "deposit", player.getName(), (price*amount)});
					player.sendMessage("§e[iExchange] §2" + amount + " " + ItemGave + " §bsold for §2$" + price*amount);
					ITEMPRICES.clear();
					return true;
					}
				}else{
					player.sendMessage("§e[iExchange] §cERROR 404: ITEM NOT FOUND!");
					player.sendMessage("§cYou don't have that item!");
					ITEMPRICES.clear();
					return true;
					}
			}else{
				player.sendMessage("§e[iExchange] §cError 400: ITEM NOT ON LIST!");
				player.sendMessage("§c(check your spelling or ask an ADMIN if item is on the list)");
				ITEMPRICES.clear();
				return true;
			}
		}
		return false;
	}
}