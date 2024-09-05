// Fichier : KillStreakManager.java
package com.glownycorp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KillStreakManager implements Listener {

    private final Map<Player, Integer> killStreaks = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null) {
            // Incrémente la série de kills du tueur
            killStreaks.put(killer, killStreaks.getOrDefault(killer, 0) + 1);

            int killStreak = killStreaks.get(killer);

            // Récompense en fonction de la série de kills
            if (killStreak == 3) {
                killer.sendMessage("§6Vous êtes en série de 3 kills ! Vous recevez une pomme dorée.");
                killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
            } else if (killStreak == 5) {
                killer.sendMessage("§cVous êtes en série de 5 kills ! Vous recevez une épée en diamant.");
                killer.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD, 1));
            } else if (killStreak == 10) {
                killer.sendMessage("§bVous êtes en série de 10 kills ! Vous recevez un totem d'immortalité.");
                killer.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
            } else if (killStreak == 15) {
                killer.sendMessage("§4Vous êtes en série de 15 kills ! Vous recevez une lingot de Netherite.");
                killer.getInventory().addItem(new ItemStack(Material.NETHERITE_INGOT, 1));
            }

            // Envoyer un message "Haha Bozo!" à la victime lors de sa mort
            victim.sendMessage("§cHaha Bozo!");

            // Réinitialise la série de kills de la victime
            killStreaks.put(victim, 0);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        int streak = killStreaks.getOrDefault(player, 0);

        // Récompense de respawn si le joueur a été tué avec une série active
        if (streak > 0) {
            player.sendMessage("§eVous avez perdu une série de " + streak + " kills. Reprenez votre revanche !");
            killStreaks.put(player, 0); // Réinitialiser la série après le respawn
        }
    }
}
