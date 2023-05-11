package de.funboyy.challenge.utils;

import java.util.*;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class RandomDrop {

    private static RandomDrop instance;

    public static RandomDrop getInstance() {
        if (instance == null) {
            instance = new RandomDrop();
        }
        return instance;
    }

    @SuppressWarnings("UnstableApiUsage")
    private static final List<Material> BLOCKED_DROPS = Arrays.asList(
            Material.BARRIER,
            Material.SPAWNER,
            Material.DEBUG_STICK,
            Material.JIGSAW,
            Material.DRAGON_EGG,
            Material.BEDROCK,
            Material.KNOWLEDGE_BOOK,
            Material.WRITTEN_BOOK,
            Material.FILLED_MAP,
            Material.ENCHANTED_BOOK,
            Material.TIPPED_ARROW,
            Material.END_PORTAL_FRAME,
            Material.LIGHT,
            Material.BUNDLE,
            Material.CHISELED_BOOKSHELF,
            Material.PIGLIN_HEAD,
            Material.PINK_PETALS,
            Material.BRUSH,
            Material.DECORATED_POT,
            Material.PAINTING
    );

    private static final List<String> BLOCKED_DROP_NAMES = Arrays.asList(
            "COMMAND_BLOCK",
            "SPAWN_EGG",
            "STRUCTURE",
            "POTION",
            "SMITHING_TEMPLATE",
            "HANGING_SIGN",
            "BAMBOO_",
            "CHERRY",
            "SUSPICIOUS",
            "POTTERY_SHARD",
            "TORCHFLOWER"
    );

    private static boolean isDrop(final Material material) {
        if (material.isAir()) {
            return false;
        }

        for (final String blockedName : BLOCKED_DROP_NAMES) {
            if (material.name().contains(blockedName)) {
                return false;
            }
        }

        return !BLOCKED_DROPS.contains(material);
    }

    private static final List<Material> DROPS = Arrays.stream(Material.values()).filter(Material::isItem)
            .filter(RandomDrop::isDrop).toList();


    private final Map<Material, Material> randomDrops;
    private final Random random;

    public RandomDrop() {
        this.randomDrops = new HashMap<>();
        this.random = new Random();
    }

    private Material getMaterial(final Material material) {
        if (this.randomDrops.containsKey(material)) {
            return this.randomDrops.get(material);
        }

        final List<Material> drops = DROPS.stream()
                .filter(drop -> !this.randomDrops.containsValue(drop))
                .toList();

        final Material drop = drops.get(this.random.nextInt(drops.size()));
        this.randomDrops.put(material, drop);

        return drop;
    }

    public ItemStack getDrop(final Item item) {
        final ItemStack itemStack = new ItemStack(getMaterial(item.getItemStack().getType()));
        itemStack.setAmount(item.getItemStack().getAmount());

        return itemStack;
    }

}
