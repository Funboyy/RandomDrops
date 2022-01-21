package de.funboyy.challenge.utils;

import java.util.*;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

    private static boolean isDrop(final Material material) {
        if (material.name().contains("COMMAND_BLOCK")) {
            return false;
        }

        if (material.name().contains("SPAWN_EGG")) {
            return false;
        }

        if (material.name().contains("STRUCTURE")) {
            return false;
        }

        if (material.name().contains("POTION")) {
            return false;
        }

        return material != Material.BARRIER &&
                material != Material.SPAWNER &&
                material != Material.DEBUG_STICK &&
                material != Material.JIGSAW &&
                material != Material.DRAGON_EGG &&
                material != Material.BEDROCK &&
                material != Material.KNOWLEDGE_BOOK &&
                material != Material.WRITTEN_BOOK &&
                material != Material.FILLED_MAP &&
                material != Material.ENCHANTED_BOOK &&
                material != Material.TIPPED_ARROW;
    }

    private static final List<Material> DROPS = Arrays.stream(Material.values()).filter(Material::isItem)
            .filter(RandomDrop::isDrop).collect(Collectors.toList());


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
                .collect(Collectors.toList());

        final Material drop = drops.get(this.random.nextInt(drops.size()));
        this.randomDrops.put(material, drop);

        return drop;
    }

    public ItemStack getDrop(final Item item) {
        final ItemStack itemStack = new ItemStack(getMaterial(item.getItemStack().getType()));
        itemStack.setAmount(item.getItemStack().getAmount());

        return itemStack;
    }

    public List<ItemStack> getDrops(final List<Item> items) {
        final List<ItemStack> drops = new ArrayList<>();
        items.forEach(item -> drops.add(getDrop(item)));

        return drops;
    }

    public List<ItemStack> getDropsByItemStacks(final List<ItemStack> items) {
        final List<ItemStack> drops = new ArrayList<>();

        items.forEach(item -> {
            final ItemStack itemStack = new ItemStack(getMaterial(item.getType()));
            itemStack.setAmount(item.getAmount());
            drops.add(itemStack);
        });

        return drops;
    }

}
