package me.solar.lunarlib.items;

import lombok.Data;
import me.solar.lunarlib.exceptions.NoFreeSpaceException;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

@Data
public class CustomItem {

    private ItemStack itemStack;
    private Material material;
    private Component name;

    public CustomItem(Material material, Component name) {
        this(new ItemStack(material), name);
        this.material = material;
    }

    public CustomItem(ItemStack itemStack, Component name) {
        this.itemStack = itemStack;
        this.name = name;
        this.material = itemStack.getType();

        itemStack.getItemMeta().displayName(name);
    }

    public <T, Z> void addData(JavaPlugin plugin, String namespace, PersistentDataType<T, Z> dataType, Z value) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = getContainer();
        NamespacedKey key = new NamespacedKey(plugin, namespace);
        container.set(key, dataType, value);
        itemStack.setItemMeta(meta);
    }

    public void removeData(JavaPlugin plugin, String namespace) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = getContainer();
        NamespacedKey key = new NamespacedKey(plugin, namespace);
        container.remove(key);
        itemStack.setItemMeta(meta);
    }

    public <T, Z> Z getData(JavaPlugin plugin, String namespace, PersistentDataType<T, Z> dataType) {
        PersistentDataContainer container = getContainer();
        NamespacedKey key = new NamespacedKey(plugin, namespace);
        return container.get(key, dataType);
    }

    public PersistentDataContainer getContainer() {
        ItemMeta meta = itemStack.getItemMeta();
        return meta.getPersistentDataContainer();
    }

    public void giveItem(InventoryHolder inventoryHolder, int slot, boolean force) throws NoFreeSpaceException {
        Inventory inventory = inventoryHolder.getInventory();
        if (slot >= inventory.getSize()) {
            throw new IndexOutOfBoundsException("Inventory holder does not have that many slots");
        }

        int index = slot;

        if (!force) {
            boolean finishedSearch = false;
            boolean firstPass = true;
            while (!finishedSearch) {
                if (index >= inventory.getSize()) {
                    if (firstPass) {
                        index = 0;
                        firstPass = false;
                    } else {
                        finishedSearch = true;
                    }
                }

                if (inventory.getItem(index) == null) {
                    finishedSearch = true;
                } else {
                    index++;
                }
            }
        }

        boolean isEmpty = inventory.getItem(index) == null;
        if (!isEmpty && !force) {
            throw new NoFreeSpaceException("No free space for item to be placed");
        } else if (isEmpty && !force) {
            inventory.setItem(index, itemStack);
        } else {
            inventory.setItem(slot, itemStack);
        }


    }
}
