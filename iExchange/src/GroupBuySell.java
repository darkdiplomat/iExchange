
public class GroupBuySell {
	
	public static boolean onCommand(Player player, String[] split) {
		if ((split[1].equalsIgnoreCase("sell") || (split[1].equalsIgnoreCase("s")))){
			if (iExchangeProps.getSELLENABLED() == true){
				if (player.canUseCommand("/iex-sell")){
					GroupSell.onCommand(player, split);
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
					GroupBuy.onCommand(player, split);
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
	return false;
	}
}
