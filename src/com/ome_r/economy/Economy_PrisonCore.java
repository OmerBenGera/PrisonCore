package com.ome_r.economy;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

@SuppressWarnings("deprecation")
public class Economy_PrisonCore implements Economy {

	public static Map<UUID, BigDecimal> accounts = new HashMap<>();

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		if(player == null) return false;
		
		if(!accounts.containsKey(player.getUniqueId()))
			accounts.put(player.getUniqueId(), BigDecimal.valueOf(0));
		
		return true;
	}

	@Override
	public boolean createPlayerAccount(String playerName) {
		return createPlayerAccount(Bukkit.getOfflinePlayer(playerName));
	}

	@Override
	public boolean createPlayerAccount(String playerName, String world) {
		return createPlayerAccount(playerName);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String world) {
		return createPlayerAccount(player);
	}

	@Override
	public String currencyNamePlural() {
		return "$";
	}

	@Override
	public String currencyNameSingular() {
		return "$";
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		if(player == null)
			return new EconomyResponse(amount, 0, ResponseType.FAILURE, "�cInvalid player.");
		
		BigDecimal result = accounts.get(player.getUniqueId()).add(BigDecimal.valueOf(amount));
		String errorMessage = "";
		
		if(amount <= 0)
			errorMessage = "§cCouldn't gave " + amount + " dollars to " + player.getName() + ".";
		else if(result.toString().split("\\.")[0].length() >= 28)
			errorMessage = "§c" + player.getName() + " has reached the maximum amount of money.";
		
		if(errorMessage.equals("")){
			accounts.put(player.getUniqueId(), result);
			return new EconomyResponse(amount, accounts.get(player.getUniqueId()).doubleValue(), ResponseType.SUCCESS, null);
		}

		return new EconomyResponse(amount, accounts.get(player.getUniqueId()).doubleValue(), ResponseType.FAILURE, errorMessage);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		return depositPlayer(Bukkit.getOfflinePlayer(playerName), amount);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String world, double amount) {
		return depositPlayer(Bukkit.getOfflinePlayer(playerName), amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String world, double amount) {
		return depositPlayer(player, amount);
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		if(player == null) return 0;

		if(!hasAccount(player))
			createPlayerAccount(player);

		return accounts.get(player.getUniqueId()).doubleValue();
	}

	@Override
	public double getBalance(String playerName) {
		return getBalance(Bukkit.getOfflinePlayer(playerName));
	}

	@Override
	public double getBalance(String playerName, String world) {
		return getBalance(Bukkit.getOfflinePlayer(playerName));
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		return getBalance(player);
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		if(player == null) return false;

		if(!hasAccount(player))
			createPlayerAccount(player);

		
		
		return (accounts.get(player.getUniqueId()).doubleValue() >= amount);
	}

	@Override
	public boolean has(String playerName, double amount) {
		return has(Bukkit.getOfflinePlayer(playerName), amount);
	}

	@Override
	public boolean has(String playerName, String world, double amount) {
		return has(Bukkit.getOfflinePlayer(playerName), amount);
	}

	@Override
	public boolean has(OfflinePlayer player, String world, double amount) {
		return has(player, amount);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		if(player == null) return false;
		return accounts.containsKey(player.getUniqueId());
	}

	@Override
	public boolean hasAccount(String playerName) {
		return hasAccount(Bukkit.getOfflinePlayer(playerName));
	}

	@Override
	public boolean hasAccount(String playerName, String world) {
		return hasAccount(Bukkit.getOfflinePlayer(playerName));
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String world) {
		return hasAccount(player);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		if(player == null) return new EconomyResponse(amount, 0, ResponseType.FAILURE, "§cInvalid player.");
		
		
		if(accounts.get(player.getUniqueId()).doubleValue() < amount) 
			accounts.put(player.getUniqueId(), BigDecimal.valueOf(0));
		else accounts.put(player.getUniqueId(), accounts.get(player.getUniqueId()).subtract(BigDecimal.valueOf(amount)));

		return new EconomyResponse(amount, accounts.get(player.getUniqueId()).doubleValue(), ResponseType.SUCCESS, null);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		return withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String world, double amount) {
		return withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String world, double amount) {
		return withdrawPlayer(player, amount);
	}

	@Override
	public String format(double amount) {
		NumberFormat nFormat = NumberFormat.getInstance(Locale.ENGLISH);

		nFormat.setMaximumFractionDigits(2);

		nFormat.setMinimumFractionDigits(0);

		if (amount < 1000.0D)
			return nFormat.format(amount);
		
		else if (amount < 1000000.0D)
			return nFormat.format((double) amount / 1000.0) + "k";
		
		else if (amount < 1.0E9D)
			return nFormat.format((double) amount / 1000000.0) + "M";
		
		else if (amount < 1.0E12D)
			return nFormat.format((double) amount / 1.0E9) + "B";
		
		else if (amount < 1.0E15D)
			return nFormat.format((double) amount / 1.0E12) + "T";
		
		else if (amount < 1.0E18D)
			return nFormat.format((double) amount / 1.0E15) + "Quad";
		
		else if (amount < 1.0E21D)
			return nFormat.format((double) amount / 1.0E18) + "Quint";
		
		else if (amount < 1.0E24D)
			return nFormat.format((double) amount / 1.0E21) + "Sex";
		
		else return nFormat.format((double) amount / 1.0E24) + "Sep";
	}

	//----------------------------------//
	//     Don't use these methods!     //
	//----------------------------------//

	@Override
	public EconomyResponse bankBalance(String DISABLED) {
		return null;
	}

	@Override
	public EconomyResponse bankDeposit(String DISABLED, double arg1) {
		return null;
	}

	@Override
	public EconomyResponse bankHas(String DISABLED, double arg1) {
		return null;
	}

	@Override
	public EconomyResponse bankWithdraw(String DISABLED, double arg1) {
		return null;
	}

	@Override
	public EconomyResponse createBank(String DISABLED, String arg1) {
		return null;
	}

	@Override
	public EconomyResponse createBank(String DISABLED, OfflinePlayer arg1) {
		return null;
	}

	@Override
	public EconomyResponse deleteBank(String DISABLED) {
		return null;
	}

	@Override
	public int fractionalDigits() {
		return 0;
	}

	@Override
	public List<String> getBanks() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public EconomyResponse isBankMember(String DISABLED, String arg1) {
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String DISABLED, OfflinePlayer arg1) {
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String DISABLED, String arg1) {
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String DISABLED, OfflinePlayer arg1) {
		return null;
	}

}
