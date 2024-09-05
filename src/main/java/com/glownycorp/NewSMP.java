package com.glownycorp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class NewSMP extends JavaPlugin implements Listener {

    private final Random random = new Random();

    @Override
    public void onEnable() {
        // Enregistrer les événements
        Bukkit.getPluginManager().registerEvents(this, this);
        // Enregistrer les événements du KillStreakManager
        Bukkit.getPluginManager().registerEvents(new KillStreakManager(), this);

        getLogger().info("NewSMP-plugin_byglowny est activé !");

        // Ajouter le message personnalisé au démarrage
        getLogger().info("------------------------------------------------------------");
        getLogger().info("Ce plugin a été codé par Glowny sur IntelliJ IDEA en 3 jours");
        getLogger().info("pour la release finale. Pour me contacter pour des commandes");
        getLogger().info("ou pour que je vous code un plugin, ajoutez-moi sur Discord :");
        getLogger().info("mon pseudo est glowiinoh. N'hésitez pas à me demander !");
        getLogger().info("------------------------------------------------------------");
    }

    @Override
    public void onDisable() {
        getLogger().info("NewSMP-plugin_byglowny est désactivé !");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity(); // Le joueur qui a été tué
        Player killer = victim.getKiller(); // Le joueur qui a tué

        if (killer != null) {
            // Génère un objet aléatoire
            Material randomMaterial = Material.values()[random.nextInt(Material.values().length)];
            ItemStack randomItemStack = new ItemStack(randomMaterial, 64);

            // Supprime tous les drops actuels et ajoute le nouvel item aléatoire
            event.getDrops().clear();
            event.getDrops().add(randomItemStack);

            // Sélectionne un seul effet positif aléatoire
            PotionEffectType[] positiveEffects = {
                    PotionEffectType.SPEED,
                    PotionEffectType.INSTANT_HEALTH,
                    PotionEffectType.REGENERATION,
                    PotionEffectType.STRENGTH,
                    PotionEffectType.RESISTANCE,
                    PotionEffectType.HEALTH_BOOST,
                    PotionEffectType.ABSORPTION,
                    PotionEffectType.LUCK,
                    PotionEffectType.FIRE_RESISTANCE,
                    PotionEffectType.WATER_BREATHING,
                    PotionEffectType.JUMP_BOOST,
                    PotionEffectType.NIGHT_VISION,
                    PotionEffectType.SATURATION
            };

            // Choisir un effet positif aléatoire
            PotionEffectType effectType = positiveEffects[random.nextInt(positiveEffects.length)];

            // Applique l'effet au tueur avec une durée de 5 minutes (6000 ticks) et niveau 1
            PotionEffect effect = new PotionEffect(effectType, 6000, 1);
            killer.addPotionEffect(effect);

            // Optionnel : Afficher un message au tueur
            killer.sendMessage("Vous avez reçu un effet positif : " + effectType.getName() + " !");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("wiki")) {
            if (sender instanceof Player player) {
                player.sendMessage("§b--- Guide d'Utilisation du plugin NewSMP-plugin_byglowny ---");
                player.sendMessage("§a1. Quand vous tuez un joueur, il droppe 64 objets aléatoires.");
                player.sendMessage("    - Cet objet peut être n'importe quoi dans Minecraft, de la terre à des diamants !");
                player.sendMessage("    - Utilisez cette fonctionnalité pour accumuler des ressources rapidement, mais rappelez-vous que les objets sont complètement aléatoires.");
                player.sendMessage("§a2. En tuant un joueur, vous recevez également un effet positif aléatoire pendant 5 minutes.");
                player.sendMessage("    - Les effets incluent des bonus comme la vitesse, la force, la régénération, et bien d'autres.");
                player.sendMessage("    - Pensez à engager des combats stratégiquement pour tirer parti des effets puissants qui peuvent améliorer vos capacités.");
                player.sendMessage("    - Exemples d'effets bénéfiques : Résistance au feu pour traverser la lave, Respiration aquatique pour échapper à des ennemis sous l'eau.");
                player.sendMessage("§a3. Utilisez le plugin avec stratégie pour tirer parti des effets positifs !");
                player.sendMessage("    - Profitez des effets pour explorer, miner, ou combattre d'autres joueurs.");
                player.sendMessage("    - Combinez les effets positifs avec votre équipement pour maximiser votre efficacité.");
                player.sendMessage("    - Soyez conscient que les effets sont temporaires, utilisez-les donc à bon escient.");
            } else {
                sender.sendMessage("Cette commande ne peut être utilisée que par des joueurs.");
            }
            return true;
        }
        return false;
    }
}
