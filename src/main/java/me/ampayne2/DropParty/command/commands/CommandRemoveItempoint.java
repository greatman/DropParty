package me.ampayne2.DropParty.command.commands;

import java.util.ArrayList;
import java.util.List;

import me.ampayne2.DropParty.command.interfaces.DropPartyCommand;
import me.ampayne2.DropParty.database.DatabaseManager;
import me.ampayne2.DropParty.database.tables.DropPartyItempointsTable;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRemoveItempoint implements DropPartyCommand {

	private final static ArrayList<String> playersRemoving = new ArrayList<String>();

	public static void toggleRemoving(String playerName, CommandSender sender) {
		if (isRemoving(playerName)) {
			playersRemoving.remove(playerName);
			sender.sendMessage(ChatColor.AQUA + "RemoveItemPoint mode deactivated.");
		} else {
			playersRemoving.add(playerName);
			sender.sendMessage(ChatColor.AQUA + "RemoveItemPoint mode activated.");
		}
	}

	public static boolean isRemoving(String playerName) {
		return playersRemoving.contains(playerName);
	}

	public static void deleteItempoint(Player player, String playerName, int x,
			int y, int z) {
		playersRemoving.remove(playerName);
		DatabaseManager.getDatabase().remove(
				DatabaseManager.getDatabase()
						.select(DropPartyItempointsTable.class).where()
						.equal("x", x).and().equal("y", y).and().equal("z", z)
						.execute().findOne());
		player.sendMessage(ChatColor.AQUA + "Drop Party Item Point Removed Successfully.");

	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String playerName = sender.getName();
		if (args.length == 1) {
			List<DropPartyItempointsTable> list = DatabaseManager.getDatabase()
					.select(DropPartyItempointsTable.class).execute().find();
			int itempointid = Integer.parseInt(args[0]);
			if (!(list.size() >= itempointid)) {
				sender.sendMessage(ChatColor.AQUA
						+ "Drop Party Item Point does not exist.");
				return;
			}
			DropPartyItempointsTable entry = list.get(itempointid - 1);
			DatabaseManager.getDatabase().remove(
					DatabaseManager.getDatabase()
							.select(DropPartyItempointsTable.class).where()
							.equal("x", entry.x).and().equal("y", entry.y)
							.and().equal("z", entry.z).execute().findOne());

			sender.sendMessage(ChatColor.AQUA + "Drop Party Item Point Removed Successfully.");
			return;

		}
		if (args.length == 0 && CommandSetItempoint.isSetting(playerName)) {
			CommandSetItempoint.toggleSetting(playerName, sender);
		}
		toggleRemoving(playerName, sender);
	}
}
